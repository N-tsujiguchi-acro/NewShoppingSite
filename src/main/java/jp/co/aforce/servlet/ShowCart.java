package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Cart;
import jp.co.aforce.beans.Product;
import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.CartDAO;

/**
 * Servlet implementation class ShowCart
 */
@WebServlet("/views/ShowCart")
public class ShowCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowCart() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users user = (Users) request.getSession().getAttribute("userInfo");
		 
		String memberId = user.getMEMBER_ID();
        CartDAO dao = new CartDAO();

        try {
            // カート本体
            Cart cart = dao.getOpenCart(memberId);
            if (cart == null) {
                request.getRequestDispatcher("cart-error.jsp").forward(request, response);
                return;
            }

            // 商品＋数量一覧
            List<Product> items = dao.getCartProductsWithAmount(cart.getCart_id());
            if (items.isEmpty()) {
                request.getRequestDispatcher("cart-error.jsp").forward(request, response);
                return;
            }

            request.setAttribute("cart", cart);
            request.setAttribute("cartItems", items);
            request.getRequestDispatcher("show-cart.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
           
        }
   }

	   
}


