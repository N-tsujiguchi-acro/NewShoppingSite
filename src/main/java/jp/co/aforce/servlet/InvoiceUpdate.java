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
 * Servlet implementation class InvoiceUpdate
 */
@WebServlet("/views/InvoiceUpdate")
public class InvoiceUpdate extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String status = request.getParameter("status");
        String id     = request.getParameter("id");
        int purchaseId  = Integer.parseInt(request.getParameter("purchase_id"));
        
        System.out.println("memberId: " + id);
        System.out.println("purchaseId: " + purchaseId);
        System.out.println("status: " + status);


        try {
            InvoiceDAO dao = new InvoiceDAO();
            dao.updateStatus(id, purchaseId, status);

            HttpSession session = request.getSession();
            session.setAttribute("updatedId", id);
            session.setAttribute("flashMsg", "ステータスを更新しました！");

            // ✅ 表示中の月を取得（なければ今月にする）
            Integer month = (Integer) session.getAttribute("currentMonth");
            if (month == null) {
                month = java.time.LocalDate.now().getMonthValue();
            }
            int year = java.time.LocalDate.now().getYear(); // 今の年（必要ならセッションで保持）

            LocalDate billingPeriod = LocalDate.of(year, month, 1);
            List<Invoices> filteredList = dao.findMonthlyInvoiceWithStatus(billingPeriod);

            session.setAttribute("allinvoices", filteredList);

            response.sendRedirect("invoice.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("invoice.jsp");
        }
    }
}
