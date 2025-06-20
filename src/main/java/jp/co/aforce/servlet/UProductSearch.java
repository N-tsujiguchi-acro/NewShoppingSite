package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Product;
import jp.co.aforce.dao.productDAO;

/**
 * Servlet implementation class UProductSearch
 */
@WebServlet("/views/UProductSearch")
public class UProductSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UProductSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		
		try {
			productDAO dao = new productDAO();
			
			List<Product> list = dao.search(keyword);
			request.setAttribute("productList", list);
			 RequestDispatcher rd =
			            request.getRequestDispatcher("user-menu.jsp");
			        rd.forward(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

}
