package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Cart;
import jp.co.aforce.beans.Product;
import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.CartDAO;
import jp.co.aforce.dao.PurchaseDAO;

/**
 * Servlet implementation class PurchaseProduct
 */
@WebServlet("/views/PurchaseProduct")
public class PurchaseProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    Users user = (Users) request.getSession().getAttribute("userInfo");
	    String memberId = user.getMEMBER_ID();

        CartDAO     cartDao = new CartDAO();
        PurchaseDAO purDao  = new PurchaseDAO();

        try {
            Cart cart = cartDao.getOpenCart(memberId);
            if (cart == null) {
                request.setAttribute("message", "カートが空です。");
                request.getRequestDispatcher("cart-error.jsp").forward(request, response);
                return;
            }

            List<Product> items = cartDao.getCartProductsWithAmount(cart.getCart_id());
            if (items.isEmpty()) {
                request.setAttribute("message", "カートが空です。");
                request.getRequestDispatcher("cart-error.jsp").forward(request, response);
                return;
            }

            /* ---------- 3. フォームから数量変更を受け取る ---------- */
            String[] idArr  = request.getParameterValues("product_id");
            String[] amtArr = request.getParameterValues("amount");

            if (idArr != null && amtArr != null && idArr.length == amtArr.length) {
                // (product_id → qty) のマップを作成
                Map<Integer, Integer> reqQty = IntStream.range(0, idArr.length)
                    .boxed()
                    .collect(Collectors.toMap(
                        i -> safeParseInt(idArr[i], -1),
                        i -> safeParseInt(amtArr[i], 1),
                        (a, b) -> b));           // 重複キーは後勝ち

                // 商品リストへ反映
                for (Product p : items) {
                    Integer q = reqQty.get(p.getProduct_id());
                    if (q != null && q > 0) {
                        p.setAmount(q);
                    }
                }
            }

            /* ---------- 4. 合計金額を再計算 ---------- */
            int cartTotal = items.stream()
                                 .mapToInt(p -> p.getPrice() * p.getAmount())
                                 .sum();

            /* ---------- 5. 購入処理 (purchase へ登録 / invoices 更新 / cart 削除) ---------- */
            purDao.checkout(memberId, cart.getCart_id(), items, cartTotal);

            /* ---------- 6. 完了画面へ ---------- */
            request.getSession().setAttribute("purchaseItems", items);

            request.setAttribute("cartTotal", cartTotal);
            request.getRequestDispatcher("purchase-done.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            forwardError(request, response, "購入処理でエラーが発生しました");
        }
	}
	

    private int safeParseInt(String s, int def) {
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return def; }
    }

    private void forwardError(HttpServletRequest req, HttpServletResponse resp, String msg)
            throws ServletException, IOException {
        req.setAttribute("message", msg);
        req.getRequestDispatcher("cart-error.jsp").forward(req, resp);
    }

}
