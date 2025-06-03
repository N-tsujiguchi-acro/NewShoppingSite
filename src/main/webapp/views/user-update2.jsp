<%@page import="jp.co.aforce.beans.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

String memberId = request.getParameter("id");
String lastName = request.getParameter("last_name");
String firstName = request.getParameter("first_name");
String address = request.getParameter("address");
String mailAddress = request.getParameter("mail_address");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>更新確認画面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">

</head>
<body>
<div class="user-update">
	<h2>更新確認画面</h2>
	<p>登録内容はこちらでよろしいですか？</p>
</div>
	<div class="container">
        <table class="register-table">
            <tr><td>姓:</td><td><%= lastName %></td></tr>
            <tr><td>名:</td><td><%= firstName %></td></tr>
            <tr><td>住所:</td><td><%= address %></td></tr>
            <tr><td>メールアドレス:</td><td><%= mailAddress %></td></tr>
        </table>
          
        <form action="user-update.jsp" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= memberId %>">
            <input type="hidden" name="last_name" value="<%= lastName %>">
            <input type="hidden" name="first_name" value="<%= firstName %>">
            <input type="hidden" name="address" value="<%= address %>">
            <input type="hidden" name="mail_address" value="<%= mailAddress %>">
            <input type="submit" value="戻る" class="register-submit-button">
        </form>

       
        <form action="user-update-servlet" method="post" style="display:inline;">
            <input type="hidden" name="id" value="<%= memberId %>">
            <input type="hidden" name="last_name" value="<%= lastName %>">
            <input type="hidden" name="first_name" value="<%= firstName %>">
            <input type="hidden" name="address" value="<%= address %>">
            <input type="hidden" name="mail_address" value="<%= mailAddress %>">
            <input type="submit" value="登録する" class="register-submit-button">
        </form>
	</div>

</body>
</html>