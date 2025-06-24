<%@page import="jp.co.aforce.beans.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品修正画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<body class="admin-body">

<%
Product pro = (Product) request.getAttribute("pro");
%>

<div class="form-container">
    <h2 class="section-title">商品修正</h2>
	<form action="SecondFixProduct" method="post" class="product-form">
	    <input type="hidden" name="id" value="<%= pro.getProduct_id() %>">

	    <label for="name">商品名:</label>
	    <input type="text" name="name" id="name" value="<%= pro.getName() %>" class="text-input"><br>

	    <label for="price">価格:</label>
	    <input type="text" name="price" id="price" value="<%= pro.getPrice() %>" class="text-input"><br>

	    <input type="submit" value="登録する" class="btn menu-btn">
	</form>

    <a href="all-product.jsp" class="back-link">← 戻る</a>
