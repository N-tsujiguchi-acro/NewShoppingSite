package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;

@WebServlet(urlPatterns= {"/views/register"})
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		try {
			String id = request.getParameter("member_id");
			String pass = request.getParameter("password");
			String last_name = request.getParameter("last_name");
			String first_name = request.getParameter("first_name");
			String address = request.getParameter("address");
			String mail_address = request.getParameter("mail_address");
			
			
			
			Users newuser = new Users();
			
			newuser.setMEMBER_ID(id);
			newuser.setPASSWORD(pass);
			newuser.setLAST_NAME(last_name);
			newuser.setFIRST_NAME(first_name);
			newuser.setADDRESS(address);
			newuser.setMAIL_ADDRESS(mail_address);
			
			
			UsersDAO u_dao = new UsersDAO(); 
			boolean isadd = u_dao.addUser(newuser); 
			
			if( isadd == true) {
				
				request.getRequestDispatcher("new-register3..jsp").forward(request, response);
					
			}else{
					
				request.getRequestDispatcher("new-register-error.jsp").forward(request, response);
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
			
	
		
		
		
		
	}
}

