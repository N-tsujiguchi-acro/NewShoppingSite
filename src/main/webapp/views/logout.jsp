<%@page import="jp.co.aforce.beans.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
    // セッションを取得して破棄
  Users userName =(Users)session.getAttribute("userInfo");
    if (session != null) {
        session.invalidate();
    }

    // ログインページにリダイレクト
    response.sendRedirect("login-in.jsp");
%>
</body>
</html>