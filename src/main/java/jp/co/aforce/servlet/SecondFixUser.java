package jp.co.aforce.servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jp.co.aforce.beans.Users;
import jp.co.aforce.dao.UsersDAO;

/**
 * Servlet implementation class SecondFixUser
 */
@WebServlet("/views/SecondFixUser")
public class SecondFixUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SecondFixUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ① フォーム値を取得
		try {
			String id = request.getParameter("id");
			String last_name = request.getParameter("last_name");
			String first_name = request.getParameter("first_name");
			String address = request.getParameter("address");
			String mail_address = request.getParameter("mail_address");
			
			Users user = new Users();
			
			user.setMEMBER_ID(id);
			user.setFIRST_NAME(first_name);
			user.setLAST_NAME(last_name);
			user.setADDRESS(address);
			user.setMAIL_ADDRESS(mail_address);
			
			UsersDAO dao = new UsersDAO();
			Users success = dao.update(user);
			 

			// ③ 一覧を取り直してセッションに入れる
			List<Users> allList = dao.findAll();   // ↓ DAO 新規メソッド
			request.getSession().setAttribute("alluser", allList);

			// ④ 一覧画面へリダイレクト
			response.sendRedirect(request.getContextPath() + "/views/admin-user-menu.jsp");
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

}
