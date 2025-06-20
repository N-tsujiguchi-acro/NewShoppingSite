<%@page import="jp.co.aforce.beans.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品修正画面</title>
</head>
<body>
<%

Product pro = (Product) request.getAttribute("pro");

%>
<div class="container">
	<form action="SecondFixProduct" method="post">
	  <input type="hidden" name="id" value="<%=pro.getProduct_id()%>"><br> 
	  <input type="text" name="name" value="<%=pro.getName()%>"><br>
	  <input type="text" name="price" value="<%=pro.getPrice()%>"><br>
	  <input type="submit" value="登録する" class="register-submit-button">
	</form>
</div>
<a href="all-product.jsp">戻る</a>


</body>
</html>