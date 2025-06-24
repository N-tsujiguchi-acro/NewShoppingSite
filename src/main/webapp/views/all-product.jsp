<%@page import="java.util.List"%>
<%@page import="jp.co.aforce.beans.Product"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録メニュー</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<body class="admin-body">

<a href="admin-user-menu.jsp" class="back-link">会員一覧メニューへ戻る</a>

<form action="ProductSearch" method="post" class="search-form">
	<p>商品検索　名前：<input type="text" name="keyword" class="text-input" required></p>
	<input type="submit" value="検索" class="btn menu-btn">
</form>

<%
    List<Product> productList = (List<Product>) request.getAttribute("productList");
    if (productList == null) {
%>
    <p class="no-result"></p>
<%
    } else {
%>
    <table class="user-table">
      <thead>
        <tr>
          <th>商品名</th>
          <th>価格</th>
          <th>画像</th>
        </tr>
      </thead>
      <tbody>
<%
     for (Product p : productList) {
%>
        <tr>
          <td><%= p.getName() %></td>
          <td><%= p.getPrice() %>円</td>
          <td>
            <img src="<%= request.getContextPath() %>/img/<%= p.getImg() %>" alt="商品画像" width="120" height="160"/>
          </td>
        </tr>
<%
     }
%>
      </tbody>
    </table>
<%
    }
%>

<h2 class="section-title">商品登録</h2>

<form action="addproduct" method="post" enctype="multipart/form-data" class="product-form">
	<label for="category">カテゴリー:</label>
	<select name="categoryId" id="category" class="select-box">
	  <option value="01">少女漫画</option>
	  <option value="02">少年漫画</option>
	  <option value="03">雑誌</option>
	  <option value="04">小説</option>
	</select>
	<p>名前：<input type="text" name="name" class="text-input" required></p>
	<p>価格：<input type="text" name="price" pattern="[0-9]+" title="数字のみ入力してください" class="text-input"></p>
	<label for="amount">数量:</label>
	<select name="amountNum" id="amount" class="select-box">
	  <% for(int i = 1; i <= 10; i++) { %>
	    <option value="<%= i %>"><%= i %></option>
	  <% } %>
	</select><br><br>

	<label for="image">画像:</label><br>
	<input type="file" name="image" id="image" accept="image/*" required><br><br>

	<input type="submit" value="登録する" class="btn menu-btn">
</form>

<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
%>
    <p class="success-message"><%= message %></p>
<%
        session.removeAttribute("message");
    }
%>

<h2 class="section-title">商品一覧</h2>

<%
 List<Product> allList = (List<Product>) session.getAttribute("allproduct");
 for (Product p1 : allList) {
%>
	 <div class="product-card">
          <p><%= p1.getName() %></p>
          <p><%= p1.getPrice() %> 円</p>
          <p><img src="<%= request.getContextPath() %>/img/<%= p1.getImg() %>" alt="商品画像" width="120" height="160"/></p>
	
          <form action="DeleteProduct" method="post" style="display:inline;" 
                onsubmit="return confirm('本当にこの商品を削除しますか？');">
              <input type="hidden" name="productId" value="<%= p1.getProduct_id() %>">
              <input type="submit" value="削除" class="btn delete-btn">
          </form>

          <form action="FirstFixProduct" method="get" style="display:inline;">
              <input type="hidden" name="productId" value="<%= p1.getProduct_id() %>">
              <input type="submit" value="修正" class="btn edit-btn">
          </form>
      </div>
<%
 }
%>

</body>
</html>
