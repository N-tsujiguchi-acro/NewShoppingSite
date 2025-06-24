
package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.PurchaseDAO;

/**
 * Servlet implementation class AddCart
 */
@WebServlet("/views/AddCart")
public class AddCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int    id    = Integer.parseInt(request.getParameter("productId"));
		Users userInfo = (Users) request.getSession().getAttribute("userInfo");
		String memberId = userInfo.getMEMBER_ID();
		PurchaseDAO dao = new PurchaseDAO();
        try {
            dao.addProductToCart(memberId, id, 1);  // ← 数量 1 を追加
            response.sendRedirect("user-menu.jsp");
        } catch (Exception e) {
            e.printStackTrace();            // ログだけ出す
            request.setAttribute("errorMessage", "カートに追加できませんでした。");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
		
	}

}
