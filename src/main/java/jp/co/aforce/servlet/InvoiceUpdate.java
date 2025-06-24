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
 * Servlet implementation class InvoiceUpdate
 */
@WebServlet("/views/InvoiceUpdate")
public class InvoiceUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String status = request.getParameter("status");
			String   id    = request.getParameter("id");
			InvoiceDAO dao = new InvoiceDAO();
			boolean success = dao.updateStatus(id,status);
			
			if(success){
				List<Invoices> list = dao.findAll();
				
				HttpSession session = request.getSession();
				session.setAttribute("allinvoices", list);
				session.setAttribute("flashMsg", "ステータスを更新しました！");
				session.setAttribute("updatedId", id);   // ← 行内で表示したい場合はIDも渡す
				response.sendRedirect("invoice.jsp");
			}
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
