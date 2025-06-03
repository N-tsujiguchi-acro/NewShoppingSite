package jp.co.aforce.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.aforce.beans.Users;

public class UsersDAO  extends DAO{
	public Users getUserInfo(String id, String pass) {
		Users user = null;
	    try {
	        Connection con = getConnection();

	        PreparedStatement st = con.prepareStatement(
	            "SELECT MEMBER_ID, PASSWORD ,CONCAT(LAST_NAME, ' ', FIRST_NAME) AS full_name, is_admin, LAST_NAME,FIRST_NAME,ADDRESS,MAIL_ADDRESS FROM USERS WHERE MEMBER_ID = ? AND PASSWORD = ?"
	        );
	        st.setString(1, id);
	        st.setString(2, pass);

	        ResultSet rs = st.executeQuery();

	        if (rs.next()) {
	        	user = new Users();
	        	user.setMEMBER_ID(rs.getString("MEMBER_ID")); // MEMBER_IDはSQLに含めていないが、引数から設定
	            user.setPASSWORD(rs.getString("PASSWORD"));  // 同上
	            user.setLAST_NAME(rs.getString("LAST_NAME"));
	            user.setFIRST_NAME(rs.getString("FIRST_NAME"));
	            user.setADDRESS(rs.getString("ADDRESS"));
	            user.setMAIL_ADDRESS(rs.getString("MAIL_ADDRESS"));
	            user.setFullName(rs.getString("full_name"));
	            user.setIs_admin(rs.getInt("is_admin"));
	            
	            // booleanも更新（必要に応じて）
	            user.setIsAdmin(rs.getInt("is_admin") == 1);

	            
	        }
	        rs.close();
	        st.close();
	        con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return user;
	}
	
	public boolean addUser(Users user) throws Exception {
		boolean result;
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"SELECT EXISTS (  SELECT  1 FROM USERS WHERE MEMBER_ID = ?  AND PASSWORD = ? AND LAST_NAME = ?  AND FIRST_NAME = ?  AND ADDRESS = ?  AND MAIL_ADDRESS = ? )");
		
		st.setString(1, user.getMEMBER_ID());
		st.setString(2, user.getPASSWORD());
		st.setString(3, user.getLAST_NAME());
		st.setString(4, user.getFIRST_NAME());
		st.setString(5, user.getADDRESS());
		st.setString(6, user.getMAIL_ADDRESS());
		
		ResultSet rs = st.executeQuery();
	    boolean exists = false;
	    if (rs.next()) {
	        exists = rs.getBoolean(1);
	    }
	    rs.close();
	    st.close();
	    
		if(exists) {
			 result = false;
		}else {
			PreparedStatement st1=con.prepareStatement(
					"INSERT INTO USERS (MEMBER_ID, PASSWORD, LAST_NAME, FIRST_NAME, ADDRESS, MAIL_ADDRESS) VALUES (?, ?, ?, ?, ?, ?)");
			st1.setString(1, user.getMEMBER_ID());
			st1.setString(2, user.getPASSWORD());
			st1.setString(3, user.getLAST_NAME());
			st1.setString(4, user.getFIRST_NAME());
			st1.setString(5, user.getADDRESS());
			st1.setString(6, user.getMAIL_ADDRESS());
			
			 st1.executeUpdate();
			
			 result = true;
		}
		
	
		con.close();
		st.close();

		 return result;
	}
	
	public Users update(Users user) throws Exception {
	    Connection con = getConnection();

	    PreparedStatement st = con.prepareStatement(
	        "update USERS set LAST_NAME = ?, FIRST_NAME = ? , ADDRESS = ? , MAIL_ADDRESS = ?  where MEMBER_ID = ?"
	    );
	    st.setString(1, user.getLAST_NAME());
	    st.setString(2, user.getFIRST_NAME());
	    st.setString(3, user.getADDRESS());
	    st.setString(4, user.getMAIL_ADDRESS());
	    st.setString(5, user.getMEMBER_ID());

	    int rowsUpdated = st.executeUpdate();

	    st.close();

	    if (rowsUpdated > 0) {
	        // 更新成功した場合、ユーザー情報を再取得
	        PreparedStatement selectSt = con.prepareStatement(
	            "SELECT MEMBER_ID, PASSWORD, CONCAT(LAST_NAME, ' ', FIRST_NAME) AS full_name, is_admin, LAST_NAME, FIRST_NAME, ADDRESS, MAIL_ADDRESS FROM USERS WHERE MEMBER_ID = ? "
	        );
	        selectSt.setString(1, user.getMEMBER_ID());
	        

	        ResultSet rs = selectSt.executeQuery();
	        Users updatedUser = null;

	        if (rs.next()) {
	            updatedUser = new Users();
	            updatedUser.setMEMBER_ID(rs.getString("MEMBER_ID"));
	            updatedUser.setPASSWORD(rs.getString("PASSWORD"));
	            updatedUser.setFullName(rs.getString("full_name")); // フルネーム用にsetterが必要
	            updatedUser.setIsAdmin(rs.getBoolean("is_admin"));
	            updatedUser.setLAST_NAME(rs.getString("LAST_NAME"));
	            updatedUser.setFIRST_NAME(rs.getString("FIRST_NAME"));
	            updatedUser.setADDRESS(rs.getString("ADDRESS"));
	            updatedUser.setMAIL_ADDRESS(rs.getString("MAIL_ADDRESS"));
	        }

	        rs.close();
	        selectSt.close();

	        return updatedUser; // 成功した場合は更新後のユーザー情報を返す
	    }

	    return null; // 更新されなかった場合
	}
	
	public boolean delete(String id) throws Exception {
	    Connection con = getConnection();

	    PreparedStatement st = con.prepareStatement(
	        "delete from USERS where MEMBER_ID = ?"
	    );
	    st.setString(1, id);

	    int rowsDeleted = st.executeUpdate();

	    st.close();

	    return rowsDeleted > 0;
	}

	public List<Users> findAll(String all)throws Exception {
		List<Users> list=new ArrayList<>();
		
		Connection con=getConnection();

		PreparedStatement st=con.prepareStatement(
			"select * from USERS"
		);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			Users e=new Users();
			e.setMEMBER_ID(rs.getString("MEMBER_ID"));
			e.setPASSWORD(rs.getString("PASSWORD"));
			e.setLAST_NAME(rs.getString("LAST_NAME"));
			e.setFIRST_NAME(rs.getString("FIRST_NAME"));
			e.setADDRESS(rs.getString("ADDRESS"));
			e.setMAIL_ADDRESS(rs.getString("MAIL_ADDRESS"));
			list.add(e);
		}
		st.close();
		con.close();
		
		return list;
		
		
	}
	
	
}
