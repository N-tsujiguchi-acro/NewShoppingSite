package jp.co.aforce.servlet;


import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.aforce.beans.Product;
import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;
import jp.co.aforce.dao.productDAO;

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
	        	try {
					UsersDAO dao = new UsersDAO();
					 
					List<Users> list = dao.findAll();
					
					session.setAttribute("alluser", list);
					response.sendRedirect("admin-user-menu.jsp");
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	        } else {
	        	try {
					productDAO dao = new productDAO();
					 
					List<Product> list = dao.findAll();
					
					session.setAttribute("allproduct", list);
					response.sendRedirect("user-menu.jsp");
				} catch (IOException e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				} catch (Exception e) {
					// TODO 自動生成された catch ブロック
					e.printStackTrace();
				}
	        }

	    } else {
	        request.getRequestDispatcher("login-error.jsp").forward(request, response);
	    }

		
		
		
		
	}

}
