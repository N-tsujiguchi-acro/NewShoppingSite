<%@page import="jp.co.aforce.beans.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

Users userName =(Users)session.getAttribute("userInfo");


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー修正画面</title>
</head>
<body>
<h2>会員情報編集画面</h2>
<form action="user-update2.jsp" method="post">
  <input type="hidden" name="id" value="<%=userName.getMEMBER_ID()%>"><br>
  <input type="text" name="last_name" value="<%=userName.getLAST_NAME()%>"><br>
  <input type="text" name="first_name" value="<%=userName.getFIRST_NAME() %>"><br>
  <input type="text" name="address" value="<%=userName.getADDRESS() %>"><br>
  <input type="text" name="mail_address" value="<%=userName.getMAIL_ADDRESS() %>">
  <input type="submit" value="登録する" class="register-submit-button">
</form>

</body>
</html>