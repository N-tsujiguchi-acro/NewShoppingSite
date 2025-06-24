package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Product;
import jp.co.aforce.dao.productDAO;

/**
 * Servlet implementation class FirstFixProduct
 */
@WebServlet("/views/FirstFixProduct")
public class FirstFixProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FirstFixProduct() {
        super();
        // TODO Auto-generated constructor stub
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("productId");
        if (idStr == null || idStr.isBlank()) {          // ← null ガード
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                               "productId パラメータがありません");
            return;
        }

        int id = Integer.parseInt(idStr);

        try {
            productDAO dao = new productDAO();
            Product product = dao.findProduct(id);

            if (product == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND,
                                   "指定 ID の商品が見つかりません");
                return;
            }

            request.setAttribute("pro", product);
            RequestDispatcher rd =
                request.getRequestDispatcher("/views/fix-product.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            throw new ServletException(e);   // まとめて投げる方が楽
        }
    }


	

}
