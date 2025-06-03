package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;

/**
 * Servlet implementation class UserUpdate
 */
@WebServlet(urlPatterns= {"/views/user-update-servlet"})
public class UserUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			String last_name = request.getParameter("last_name");
			String first_name = request.getParameter("first_name");
			String address = request.getParameter("address");
			String mail_address = request.getParameter("mail_address");
			
			Users user = new Users();
			
			user.setMEMBER_ID(id);
			user.setFIRST_NAME(first_name);
			user.setLAST_NAME(last_name);
			user.setADDRESS(address);
			user.setMAIL_ADDRESS(mail_address);
			
			UsersDAO dao = new UsersDAO();
			Users success = dao.update(user);
			 
			 
			if(success != null ) {
				HttpSession session = request.getSession();
				session.setAttribute("userInfo", success);

				response.sendRedirect("user-update3.jsp");
			}else {
				request.getRequestDispatcher("user-update-error.jsp").forward(request, response);
			}
			 
			} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
