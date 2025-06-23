<%@page import="jp.co.aforce.beans.Invoices"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>請求一覧画面</title>
</head>
<body>
<a href="admin-user-menu.jsp">会員一覧メニューへ戻る</a>
<%
String updatedId = (String) session.getAttribute("updatedId");
List<Invoices> allList = (List<Invoices>) session.getAttribute("allinvoices");

for (Invoices i : allList) {


%>


   <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
        <p><%= i.getMember_id()%></p>
        <p><%= i.getLast_name()%> </p>
        <p><%= i.getFirst_name()%></p>
        <p><%= i.getAddress()%></p>
        <p><%= i.getTotal()%></p>
        <% if (i.getMember_id().equals(updatedId)) { %>
            <p style="color:green;">ステータスを更新しました！</p>
        <% } %>
        <form action="InvoiceUpdate" method="post" style="display:inline;" 
		      onsubmit="return confirm('本当にこのユーザーのステータスを更新しますか？');">
		
		    <!-- メンバーIDなどを hidden で送る（更新対象の識別） -->
		    <input type="hidden" name="id" value="<%= i.getMember_id() %>">
		
		    <!-- ステータス選択用のプルダウン -->
		    <select name="status">
		        <option value="未請求"   <%= "未請求".equals(i.getStatus())   ? "selected" : "" %>>未請求</option>
		        <option value="請求済み" <%= "請求済み".equals(i.getStatus()) ? "selected" : "" %>>請求済み</option>
		        <option value="未払い"   <%= "未払い".equals(i.getStatus())   ? "selected" : "" %>>未払い</option>
		    </select>
		
		    <!-- 送信ボタン -->
		    <input type="submit" value="更新">
		</form>
        
    </div>
<%
 }

%>
<%
session.removeAttribute("updatedId");
session.removeAttribute("flashMsg");
%>

	
</body>
</html>