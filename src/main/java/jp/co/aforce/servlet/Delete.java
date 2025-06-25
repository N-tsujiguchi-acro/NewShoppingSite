package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.dao.UsersDAO;

/**
 * Servlet implementation class Delete
 */
@WebServlet(urlPatterns= {"/views/delete"})
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String id = request.getParameter("id");
    		UsersDAO dao = new UsersDAO();
    		boolean success = dao.delete(id);

    		if (success) {
    			// 成功時は通常の削除完了画面へ
    			request.getRequestDispatcher("user-delete.jsp").forward(request, response);
    		} else {
    			// 削除できなかった場合（このルートは通常到達しないが一応）
    			request.setAttribute("error", "このアカウントは、削除ができませんでした。");
    			request.getRequestDispatcher("user-delete.jsp").forward(request, response);
    		}

    	} catch (java.sql.SQLIntegrityConstraintViolationException e) {
    		// 外部キー制約違反のときだけメッセージ表示
    		request.setAttribute("error", "このアカウントは、関連する情報が存在するため削除できませんでした。");
    		request.getRequestDispatcher("user-delete.jsp").forward(request, response);

    	} catch (Exception e) {
    		// その他のエラー（ログのみ）
    		e.printStackTrace();
    		request.setAttribute("error", "削除中にエラーが発生しました。");
    		request.getRequestDispatcher("user-delete.jsp").forward(request, response);
    	}
    }


}
