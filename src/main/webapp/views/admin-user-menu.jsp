<%@page import="jp.co.aforce.beans.Users"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
</head>
<%
 Users userName =(Users)session.getAttribute("userInfo");
%>
<body>
	<p>こんにちは！<%=userName.getFullName() %>さん。こちらは管理者専用画面です</p>
	
</body>
</html>