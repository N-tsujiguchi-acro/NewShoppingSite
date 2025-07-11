package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jp.co.aforce.beans.Invoices;



public class InvoiceDAO extends DAO {
	
	public List<Invoices> findAll() throws Exception {
	    List<Invoices> list = new ArrayList<>();

	    Connection con = getConnection();

	    // ▼ users と結合し、ネームと住所ぼ情報 も取得
	    String sql = """
	    	    SELECT i.member_id,
	    	           u.address,
	    	           u.last_name,
	    	           u.first_name,
	    	           i.total,
	    	           i.status
	    	    FROM   invoices i
	    	    JOIN   users u ON i.member_id = u.member_id
	    	""";


	    PreparedStatement st = con.prepareStatement(sql);
	    ResultSet rs = st.executeQuery();

	    while (rs.next()) {
	        Invoices i = new Invoices();
	        i.setMember_id(rs.getString("member_id"));
	        i.setAddress(rs.getString("address"));   // ★ 追加
	        i.setLast_name(rs.getString("last_name"));
	        i.setFirst_name(rs.getString("first_name"));
	        i.setTotal(rs.getInt("total"));
	        i.setStatus(rs.getString("status"));
	        list.add(i);
	    }
	    st.close();
	    con.close();

	    return list;
	}
	
	/**
	 * invoices テーブルの status を更新する。
	 *
	 * @param memberId 更新対象ユーザの member_id
	 * @param newStatus 設定したいステータス
	 *                  ("未請求" / "請求済み" / "未払い" のいずれか)
	 * @return 少なくとも 1 行更新できたら true
	 * @throws Exception 
	 * @throws IllegalArgumentException 渡されたステータスが不正
	 */
	
	public boolean updateStatus(String memberId, String newStatus) throws Exception {

	    // ―― CHECK 1: ステータス値のバリデーション ―――――――――――――――――――――――――――――――
	    if (!newStatus.equals("未請求") &&
	        !newStatus.equals("請求済み") &&
	        !newStatus.equals("未払い")) {
	        throw new IllegalArgumentException("不正なステータス値: " + newStatus);
	    }

	    // ―― UPDATE 文（status と member_id だけ） ―――――――――――――――――――――――――――
	    final String sql = "UPDATE invoices SET status = ? WHERE member_id = ?";

	    try (Connection con = getConnection();
	         PreparedStatement st = con.prepareStatement(sql)) {

	        st.setString(1, newStatus);  // ① status = ?
	        st.setString(2, memberId);   // ② WHERE member_id = ?

	        int affectedRows = st.executeUpdate();
	        return affectedRows > 0;     // 1 行以上なら成功とみなす
	    }
	}
	
	
	public Invoices search(String id) throws Exception {
		 Invoices invoice = null;

		    Connection con = getConnection();  // DB接続

		    String sql = """
		        SELECT 
		            i.member_id,
		            i.purchase_id,
		            i.total,
		            i.status,
		            u.last_name,
		            u.first_name,
		            u.address
		        FROM 
		            invoices i
		        INNER JOIN 
		            users u
		        ON 
		            i.member_id = u.member_id
		        WHERE 
		            i.member_id = ?
		        LIMIT 1
		    """;

		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setString(1, id);
		    ResultSet rs = ps.executeQuery();

		    if (rs.next()) {
		        invoice = new Invoices();
		        invoice.setMember_id(rs.getString("member_id"));
		        invoice.setPurchase_id(rs.getInt("purchase_id"));
		        invoice.setTotal(rs.getInt("total"));
		        invoice.setStatus(rs.getString("status"));
		        invoice.setLast_name(rs.getString("last_name"));
		        invoice.setFirst_name(rs.getString("first_name"));
		        invoice.setAddress(rs.getString("address"));
		    }

		    rs.close();
		    ps.close();
		    con.close();

		    return invoice;
	}
	
	public List<Invoices> findMonthlySummaryByBillingPeriod(LocalDate billingPeriod) throws Exception {
	    List<Invoices> list = new ArrayList<>();
	    Connection con = getConnection();

	    String sql = """
	        SELECT p.member_id,
	               u.last_name,
	               u.first_name,
	               u.address,
	               SUM(p.purchase_amount) AS total
	        FROM purchase p
	        JOIN users u ON p.member_id = u.member_id
	        WHERE p.billing_period = ?
	        GROUP BY p.member_id, u.last_name, u.first_name, u.address
	        ORDER BY p.member_id
	    """;

	    PreparedStatement st = con.prepareStatement(sql);
	    st.setDate(1, java.sql.Date.valueOf(billingPeriod));
	    ResultSet rs = st.executeQuery();

	    while (rs.next()) {
	        Invoices invoice = new Invoices();  // 汎用的に使用
	        invoice.setMember_id(rs.getString("member_id"));
	        invoice.setLast_name(rs.getString("last_name"));
	        invoice.setFirst_name(rs.getString("first_name"));
	        invoice.setAddress(rs.getString("address"));
	        invoice.setTotal(rs.getInt("total"));
	        list.add(invoice);
	    }

	    rs.close();
	    st.close();
	    con.close();

	    return list;
	}
	
	public List<Invoices> findMonthlyInvoiceWithStatus(LocalDate billingPeriod) throws Exception {
	    List<Invoices> list = new ArrayList<>();
	    Connection con = getConnection();

	    String sql = """
	        SELECT p.member_id,
	               u.last_name,
	               u.first_name,
	               u.address,
	               SUM(p.purchase_amount) AS total,
	               i.status
	        FROM purchase p
	        JOIN users u ON p.member_id = u.member_id
	        JOIN invoices i ON p.member_id = i.member_id
	        WHERE p.billing_period = ?
	        GROUP BY p.member_id, u.last_name, u.first_name, u.address, i.status
	        ORDER BY p.member_id
	    """;

	    PreparedStatement st = con.prepareStatement(sql);
	    st.setDate(1, java.sql.Date.valueOf(billingPeriod));
	    ResultSet rs = st.executeQuery();

	    while (rs.next()) {
	        Invoices invoice = new Invoices();
	        invoice.setMember_id(rs.getString("member_id"));
	        invoice.setLast_name(rs.getString("last_name"));
	        invoice.setFirst_name(rs.getString("first_name"));
	        invoice.setAddress(rs.getString("address"));
	        invoice.setTotal(rs.getInt("total"));
	        invoice.setStatus(rs.getString("status"));
	        list.add(invoice);
	    }

	    rs.close();
	    st.close();
	    con.close();

	    return list;
	}



}
