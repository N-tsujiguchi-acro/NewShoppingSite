package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Product;
import jp.co.aforce.dao.productDAO;

/**
 * Servlet implementation class SecondFixProduct
 */
@WebServlet("/views/SecondFixProduct")
public class SecondFixProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecondFixProduct() {
        super();
        // TODO Auto-generated constructor stub
    }

	
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

  

        try {
			// ① フォーム値を取得
			int    id    = Integer.parseInt(req.getParameter("id"));
			String name  = req.getParameter("name");
			int    price = Integer.parseInt(req.getParameter("price"));

			// ② 更新実行
			Product p = new Product();
			p.setProduct_id(id);
			p.setName(name);
			p.setPrice(price);

			productDAO dao = new productDAO();
			dao.updateProduct(p);          // ← UPDATE 文を実装済みとして

			// ③ 一覧を取り直してセッションに入れる
			List<Product> allList = dao.findAll();   // ↓ DAO 新規メソッド
			req.getSession().setAttribute("allproduct", allList);

			// ④ 一覧画面へリダイレクト
			resp.sendRedirect(req.getContextPath() + "/views/all-product.jsp");
		} catch (NumberFormatException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    }

}
