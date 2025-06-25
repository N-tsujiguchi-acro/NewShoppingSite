<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除完了画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<body>
<a href="admin-user-menu.jsp" class="back-link">会員一覧メニューへ戻る</a>
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
 
    <p style="color: red;"><%= error %></p>

<%
    }
%>


</body>
</html>