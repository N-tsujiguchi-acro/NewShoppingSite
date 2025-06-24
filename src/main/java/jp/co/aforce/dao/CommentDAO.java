package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.aforce.beans.Comment;



public class CommentDAO extends DAO{
	
	public void insertComment(String memberId, String comment) throws Exception {
	    String sql = "INSERT INTO comments (member_id, comment) VALUES (?,?)";
	    try (Connection con = getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setString(1, memberId);
	        ps.setString(2, comment);
	        ps.executeUpdate();
	    }
	}
	
	
	public List<Comment> findAll() throws Exception {
	    List<Comment> list = new ArrayList<>();

	    Connection con = getConnection();

	    // ▼ users と結合して mail_address も取得
	    String sql = """
	        SELECT c.member_id,
	               u.mail_address,
	               c.comment,
	               c.created_time
	        FROM   comments  c
	        JOIN   users     u ON c.member_id = u.member_id
	        ORDER  BY c.created_time DESC
	    """;

	    PreparedStatement st = con.prepareStatement(sql);
	    ResultSet rs = st.executeQuery();

	    while (rs.next()) {
	        Comment c = new Comment();
	        c.setMember_id(rs.getString("member_id"));
	        c.setMail_address(rs.getString("mail_address"));   // ★ 追加
	        c.setComment(rs.getString("comment"));
	        c.setCreate_time(rs.getTimestamp("created_time"));
	        list.add(c);
	    }
	    st.close();
	    con.close();

	    return list;
	}
	
	public boolean DeleteComment(String id) throws Exception {
	    Connection con = getConnection();

	    PreparedStatement st = con.prepareStatement(
	        "delete from comments where member_id = ?"
	    );
	    st.setString(1, id);

	    int rowsDeleted = st.executeUpdate();

	    st.close();

	    return rowsDeleted > 0;
	}



}
