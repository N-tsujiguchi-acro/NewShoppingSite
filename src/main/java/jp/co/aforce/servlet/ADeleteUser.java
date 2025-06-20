package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;


/**
 * Servlet implementation class ADeleteUser
 */
@WebServlet("/views/ADeleteUser")
public class ADeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			UsersDAO dao = new UsersDAO();
			boolean success = dao.delete(id);
			
			if(success){
				List<Users> allList = dao.findAll();   // ↓ DAO 新規メソッド
				request.getSession().setAttribute("alluser", allList);

				// ④ 一覧画面へリダイレクト
				response.sendRedirect(request.getContextPath() + "/views/admin-user-menu.jsp");
			}
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
