package jp.co.aforce.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import jp.co.aforce.beans.Product;
import jp.co.aforce.dao.productDAO;

/**
 * Servlet implementation class AddProduct
 */
@WebServlet(urlPatterns= {"/views/addproduct"})
@MultipartConfig
public class AddProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public AddProduct() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String categoryIdStr = request.getParameter("categoryId");  // "01" など
        String name          = request.getParameter("name");        // 商品名
        String priceStr      = request.getParameter("price");       // 価格
        String amountStr     = request.getParameter("amountNum");   // 数量

        Part imagePart = request.getPart("image");                // 画像ファイル
        
        // 4. ファイル名を取得
        String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();

        // 5. 保存先パスを決定
        String uploadPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();  // なければ作成

        // 6. 保存
        imagePart.write(uploadPath + File.separator + fileName);

       

        int categoryId = Integer.parseInt(categoryIdStr);   // 例: "01" → 1
        int price      = Integer.parseInt(priceStr);
        int quantity   = Integer.parseInt(amountStr);

      
        /* ---------- 3. Bean を組み立て ---------- */
        Product prod = new Product();
        prod.setCategory_id(categoryId);
        prod.setName(name);
        prod.setPrice(price);
        prod.setQuantity(quantity);
        prod.setImg(fileName);

        /* ---------- 4. DAO 呼び出し ---------- */
        boolean result;
        try {
            productDAO dao = new productDAO();
            result = dao.addProduct(prod);
        } catch (Exception e) {
            throw new ServletException(e);       
        }

        /* ---------- 5. 遷移 ---------- */
        if (result) {
        	try {
				productDAO dao = new productDAO();
				List<Product> allList = dao.findAll();   // ↓ DAO 新規メソッド
				request.getSession().setAttribute("allproduct", allList);

				// ④ 一覧画面へリダイレクト
				response.sendRedirect(request.getContextPath() + "/views/all-product.jsp");
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        } else {
            request.getSession().setAttribute("message", "登録に失敗しました。");
            response.sendRedirect("all-product.jsp");
        }

    }

   

}
