package jp.co.aforce.servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Invoices;
import jp.co.aforce.dao.InvoiceDAO;


/**
 * Servlet implementation class MemberSearch
 */
@WebServlet("/views/MemberSearch")
public class MemberSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		
		try {
			InvoiceDAO dao = new InvoiceDAO();
			
			Invoices list = dao.search(id);
			request.setAttribute("InvoiceUser", list);
			 RequestDispatcher rd =
			            request.getRequestDispatcher("invoice.jsp");
			        rd.forward(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

}
