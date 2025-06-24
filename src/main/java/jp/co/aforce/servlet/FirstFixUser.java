package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;

/**
 * Servlet implementation class FirstFixUser
 */
@WebServlet("/views/FirstFixUser")
public class FirstFixUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public FirstFixUser() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String id = request.getParameter("id");
        if (id == null || id.isBlank()) {          
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "productId パラメータがありません");
            return;
        }

        
        try {
            UsersDAO dao = new UsersDAO();
            Users user = dao.findUsers(id);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                                   "指定 ID のユーザが見つかりません");
                return;
            }

            request.setAttribute("user", user);
            System.out.println("user"+user.getMEMBER_ID());
            
            RequestDispatcher rd =
                request.getRequestDispatcher("/views/fix-user.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);   // まとめて投げる方が楽
        }
	}

	
}
