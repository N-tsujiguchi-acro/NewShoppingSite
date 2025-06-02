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
 * Servlet implementation class Login_In
 */
@WebServlet(urlPatterns= {"/views/login-in"})
public class LoginIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginIn() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		
		String id = request.getParameter("id");
		String pass = request.getParameter("password");
		UsersDAO u_dao = new UsersDAO();  // UsersDAO クラスのインスタンス化
	    Users userInfo = u_dao.getUserInfo(id, pass);  // 入力されたIDとPASSでユーザー検索

	    if (userInfo != null) {
	        HttpSession session = request.getSession();
	        session.setAttribute("userInfo", userInfo);  // ← オブジェクト名に合わせて "userInfo" に変更推奨

	        if (userInfo.isAdmin()) {
	            response.sendRedirect("admin-user-menu.jsp");
	        } else {
	            response.sendRedirect("user-menu.jsp");
	        }

	    } else {
	        request.getRequestDispatcher("login-error.jsp").forward(request, response);
	    }

		
		
		
		
	}

}
