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
 * Servlet implementation class CartDelete
 */
@WebServlet("/views/CartDelete")
public class CartDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartDelete() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) ログイン情報取得
        Users user = (Users) request.getSession().getAttribute("userInfo");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String memberId = user.getMEMBER_ID();

        // 2) パラメータ取得
        String pidStr = request.getParameter("product_id");
        if (pidStr == null) {
            response.sendRedirect("cart-error.jsp");
            return;
        }
        int productId = Integer.parseInt(pidStr);

        CartDAO dao = new CartDAO();

        try {
            // 3) オープンカート取得
            Cart cart = dao.getOpenCart(memberId);
            if (cart == null) {
                response.sendRedirect("cart-error.jsp");
                return;
            }

            // 4) cart_item から削除
            int cnt = dao.deleteCartItem(cart.getCart_id(), productId);

            // 5) 結果に応じて遷移
            if (cnt == 0) {                           // 該当行が無かった
                request.setAttribute("message", "該当の商品が見つかりませんでした");
                request.getRequestDispatcher("cart-error.jsp").forward(request, response);
            } else {
            	 List<Product> items = dao.getCartProductsWithAmount(cart.getCart_id());// 正常削除
            	 request.setAttribute("cartItems", items);
                 request.getRequestDispatcher("show-cart.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "削除処理でエラーが発生しました");
            request.getRequestDispatcher("cart-error.jsp").forward(request, response);
        }
    }
}
