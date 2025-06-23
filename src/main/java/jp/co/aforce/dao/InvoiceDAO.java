package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

}
