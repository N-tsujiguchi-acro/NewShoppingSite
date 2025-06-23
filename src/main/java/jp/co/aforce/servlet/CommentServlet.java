package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.CommentDAO;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/views/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		  // --- ① ログイン確認 & MEMBER_ID 取得 ---
	    try {
			Users user = (Users) req.getSession().getAttribute("userInfo");
			
			// --- ② 入力値取得 & 保存 ---
			String commentText = req.getParameter("comment");
			if (commentText != null && !commentText.trim().isEmpty()) {
			    CommentDAO dao = new CommentDAO();
			    dao.insertComment(user.getMEMBER_ID(), commentText.trim());
			}

			// --- ③ 完了ページへリダイレクト ---
			res.sendRedirect("comment.jsp?status=success");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
