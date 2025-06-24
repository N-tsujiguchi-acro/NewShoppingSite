package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import jp.co.aforce.beans.Invoices;
import jp.co.aforce.dao.InvoiceDAO;

/**
 * Servlet implementation class AInvoice
 */
@WebServlet("/views/AInvoice")
public class AInvoice extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			InvoiceDAO dao = new InvoiceDAO();
			 
			List<Invoices> list = dao.findAll();
			
			HttpSession session = request.getSession();
			session.setAttribute("allinvoices", list);
			response.sendRedirect("invoice.jsp");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
