package jp.co.aforce.servlet;

import java.io.IOException;
import java.time.LocalDate;
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
 * Servlet implementation class MonthFilter
 */
@WebServlet("/views/MonthFilter")
public class MonthFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String monthStr = request.getParameter("month");

	        try {
	            int month = Integer.parseInt(monthStr);
	            int year = LocalDate.now().getYear(); // 今の年を使う（必要なら選択も可能）
	            LocalDate billingPeriod = LocalDate.of(year, month, 1);

	            InvoiceDAO dao = new InvoiceDAO();
	            List<Invoices> invoices = dao.findMonthlySummaryByBillingPeriod(billingPeriod);

	            HttpSession session = request.getSession();
	            session.setAttribute("allinvoices", invoices);
	            session.setAttribute("currentMonth", month); // 表示中の月を保存


	            if (invoices == null || invoices.isEmpty()) {
	                session.setAttribute("nodataMsg", "該当月のデータは入っていません。");
	            } else {
	                session.removeAttribute("nodataMsg");
	            }

	            response.sendRedirect("invoice.jsp");

	        } catch (Exception e) {
	            e.printStackTrace();
	            response.sendRedirect("admin-invoice.jsp");
	        }
	}

	

}
