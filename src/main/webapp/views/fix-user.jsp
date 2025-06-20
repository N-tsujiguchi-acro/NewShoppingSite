<%@page import="jp.co.aforce.beans.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員編集</title>
</head>
<body>
<%
Users u = (Users) request.getAttribute("user");

%>

	<div class="user-update">
		<p>会員編集画面</p>
	</div>
		<div class="container">
	       <form action="SecondFixUser" method="post" style="display:inline;">
	            <input type="hidden" name="id" value="<%= u.getMEMBER_ID() %>">
	            <input type="text" name="last_name" value="<%= u.getLAST_NAME() %>">
	            <input type="text" name="first_name" value="<%= u.getFIRST_NAME() %>">
	            <input type="text" name="address" value="<%= u.getADDRESS() %>">
	            <input type="text" name="mail_address" value="<%= u.getMAIL_ADDRESS() %>">
	            <input type="submit" value="登録する" class="register-submit-button">
	        </form>
		</div>

</body>
</html>