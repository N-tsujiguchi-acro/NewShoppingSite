package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jp.co.aforce.beans.Product;

public class PurchaseDAO extends DAO {
	
	   // 1.cartテーブルに商品を追加する処理（カートに追加ボタンを押したとき）
	
	 private int getOrCreateActiveCart(String memberId, Connection con) throws SQLException {

	        // 1. 既存カート検索
	        String sqlSelect = "SELECT cart_id FROM cart WHERE member_id = ? AND status = 0";
	        try (PreparedStatement ps = con.prepareStatement(sqlSelect)) {
	            ps.setString(1, memberId);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) return rs.getInt("cart_id");
	            }
	        }

	        // 2. 新規 INSERT
	        String sqlInsert = "INSERT INTO cart (member_id, status) VALUES (?, 0)";
	        try (PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
	            ps.setString(1, memberId);
	            ps.executeUpdate();
	            try (ResultSet rs = ps.getGeneratedKeys()) {
	                if (rs.next()) return rs.getInt(1);
	            }
	        }
	        throw new SQLException("cart_id の生成に失敗しました");
	    }

	    /**
	     * カートに商品を追加（重複時は数量加算）
	     */
	    public void addProductToCart(String memberId, int productId, int amount) throws Exception {

	        try (Connection con = getConnection()) {
	            con.setAutoCommit(false);   // ---------- ① トランザクション開始

	            int cartId = getOrCreateActiveCart(memberId, con);

	            String sql =
	                "INSERT INTO cart_item (cart_id, product_id, amount) " +
	                "VALUES (?, ?, ?) " +
	                "ON DUPLICATE KEY UPDATE amount = amount + VALUES(amount)";

	            try (PreparedStatement ps = con.prepareStatement(sql)) {
	                ps.setInt(1, cartId);
	                ps.setInt(2, productId);
	                ps.setInt(3, amount);
	                ps.executeUpdate();
	            }

	            con.commit();               // ---------- ② 完了
	        }
	    }
	    
	    
	    /**
	     * 1. purchase 登録
	     * 2. invoices 更新（当月かどうかで UPDATE or INSERT）
	     * 3. cart / cart_item 削除
	     */
	    public void checkout(String memberId,
	                         int cartId,
	                         List<Product> items,
	                         int cartTotal) throws Exception {

	        LocalDate   today         = LocalDate.now();          // 例: 2025-06-20
	        LocalDate   billingPeriod = today.withDayOfMonth(1);  // 2025‑06‑01
	        LocalDateTime now         = LocalDateTime.now();

	        Connection con = null;
	        PreparedStatement ps = null;

	        try {
	            con = getConnection();
	            con.setAutoCommit(false);

	            /* ---------- 1. purchase_id を採番 ---------- */
	            int newPurchaseId;
	            try (Statement st = con.createStatement();
	                 ResultSet rs = st.executeQuery("SELECT IFNULL(MAX(purchase_id),0)+1 FROM purchase")) {
	                rs.next();
	                newPurchaseId = rs.getInt(1);
	            }

	            /* ---------- 2. purchase 明細を挿入 ---------- */
	            String sqlPurchase = """
	                INSERT INTO purchase
	                  (purchase_id, line_no, member_id, date,
	                   purchase_amount, billing_period,
	                   product_id, product_amount, create_time)
	                VALUES (?,?,?,?,?,?,?,?,?)
	            """;
	            ps = con.prepareStatement(sqlPurchase);

	            int line = 1;
	            for (Product p : items) {
	                ps.setInt   (1, newPurchaseId);
	                ps.setInt   (2, line++);
	                ps.setString(3, memberId);
	                ps.setDate  (4, Date.valueOf(today));
	                ps.setInt   (5, p.getPrice() * p.getAmount());
	                ps.setDate  (6, Date.valueOf(billingPeriod));
	                ps.setInt   (7, p.getProduct_id());
	                ps.setInt   (8, p.getAmount());
	                ps.setTimestamp(9, Timestamp.valueOf(now));
	                ps.addBatch();
	            }
	            ps.executeBatch();
	            ps.close();

	            /* ---------- 3. invoices 更新 or 挿入 ---------- */
	            // 現在 invoices には月情報が無い → 直近レコードと purchase.billing_period を比較
	            Integer invoiceId = null;
	            LocalDate latestPeriod = null;
	            String latestStatus = null;

	            String findInvoice = """
	                SELECT i.id, p.billing_period, i.status
	                FROM invoices i
	                JOIN purchase p ON i.purchase_id = p.purchase_id
	                WHERE i.member_id = ?
	                ORDER BY p.billing_period DESC, i.id DESC
	                LIMIT 1
	            """;
	            ps = con.prepareStatement(findInvoice);
	            ps.setString(1, memberId);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next()) {
	                    invoiceId    = rs.getInt("id");
	                    latestPeriod = rs.getDate("billing_period").toLocalDate();
	                    latestStatus = rs.getString("status");
	                }
	            }
	            ps.close();

	            if (invoiceId != null && latestPeriod.equals(billingPeriod)) {
	                // 同月なら UPDATE
	                String upd = "UPDATE invoices SET total = total + ? WHERE id = ?";
	                ps = con.prepareStatement(upd);
	                ps.setBigDecimal(1, new java.math.BigDecimal(cartTotal));
	                ps.setInt(2, invoiceId);
	                ps.executeUpdate();
	                ps.close();
	            } else {
	                // 異なる月 またはインボイスなし → INSERT
	                String ins = """
	                    INSERT INTO invoices (member_id, purchase_id, total, status)
	                    VALUES (?,?,?,?)
	                """;
	                ps = con.prepareStatement(ins);
	                ps.setString(1, memberId);
	                ps.setInt   (2, newPurchaseId);
	                ps.setBigDecimal(3, new java.math.BigDecimal(cartTotal));
	                ps.setString(4, "未請求");
	                ps.executeUpdate();
	                ps.close();
	            }

	            /* ---------- 4. cart_item & cart 削除 ---------- */
	            ps = con.prepareStatement("DELETE FROM cart_item WHERE cart_id = ?");
	            ps.setInt(1, cartId);
	            ps.executeUpdate();
	            ps.close();

	            ps = con.prepareStatement("DELETE FROM cart WHERE cart_id = ?");
	            ps.setInt(1, cartId);
	            ps.executeUpdate();
	            ps.close();

	            con.commit();
	        } catch (Exception e) {
	            if (con != null) con.rollback();
	            throw e;
	        } finally {
	            if (ps  != null) ps.close();
	            if (con != null) con.setAutoCommit(true);
	            if (con != null) con.close();
	        }
	    }
}
