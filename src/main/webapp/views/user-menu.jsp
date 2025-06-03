<%@page import="jp.co.aforce.beans.Users"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
</head>
<body>
<%@ include file="globalMenu.jsp" %>
<div class="main">
	<%
	
	 Users userName =(Users)session.getAttribute("userInfo");
	
	%>
	<h2>ようこそ！<%=userName.getFullName() %>さん</h2>
	<div class="button">
		<form action="user-update.jsp" method="get" style="display: inline;">
		    <button type="submit">修正</button>
		</form>

		
		<form action="delete" method="post" style="display: inline;" onsubmit="return confirm('本当に削除しますか？');">
		    <input type="hidden" name="id" value="<%= userName.getMEMBER_ID()%>">
		    <button type="submit">削除</button>
		</form>

		
		<form action="logout.jsp" method="get" style="display: inline;">
		    <button type="submit">ログアウト</button>
		</form>

	
	</div>


</div>
</body>
</html>