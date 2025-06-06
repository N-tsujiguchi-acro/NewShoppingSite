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
<form action="LogoutServlet" method="get" onsubmit="return confirmLogout();">
    <input type="submit" value="ログアウト">
</form>

<script>
    function confirmLogout() {
        return confirm("本当にログアウトしますか？");
    }
</script>
</body>
</html>