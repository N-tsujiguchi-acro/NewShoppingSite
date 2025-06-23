package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.aforce.beans.Comment;
import jp.co.aforce.dao.CommentDAO;

/**
 * Servlet implementation class DeletetComment
 */
@WebServlet("/views/DeletetComment")
public class DeletetComment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String   id    = request.getParameter("id");
			CommentDAO dao = new CommentDAO();
			boolean success = dao.DeleteComment(id);
			
			if(success){
				
				List<Comment> list = dao.findAll();
				
				HttpSession session = request.getSession();
				session.setAttribute("allcomment", list);
				response.sendRedirect("show-comment.jsp");
				
				
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
