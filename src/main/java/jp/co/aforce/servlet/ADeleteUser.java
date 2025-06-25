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

    		if (success) {
    			List<Users> allList = dao.findAll();   // ↓ DAO 新規メソッド
    			request.getSession().setAttribute("alluser", allList);

    			// ④ 一覧画面へリダイレクト
    			response.sendRedirect(request.getContextPath() + "/views/admin-user-menu.jsp");
    			
    			
    		} else {
    			// 削除できなかった場合（このルートは通常到達しないが一応）s
    			request.setAttribute("error", "このアカウントは、削除ができませんでした。");
    			request.getRequestDispatcher("adelete-user.jsp").forward(request, response);
    		}

    	} catch (java.sql.SQLIntegrityConstraintViolationException e) {
    		// 外部キー制約違反のときだけメッセージ表示
    		request.setAttribute("error", "このアカウントは、関連する情報が存在するため削除できませんでした。");
    		request.getRequestDispatcher("adelete-user.jsp").forward(request, response);

    	} catch (Exception e) {
    		// その他のエラー（ログのみ）
    		e.printStackTrace();
    		request.setAttribute("error", "削除中にエラーが発生しました。");
    		request.getRequestDispatcher("adelete-user.jsp").forward(request, response);
    	}
	}

}


