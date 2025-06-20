<%@page import="jp.co.aforce.beans.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品登録メニュー</title>
</head>
<body>

<a href="admin-user-menu.jsp">会員一覧メニューへ戻る</a>

<form action="ProductSearch" method="post">
	<P>商品検索　名前：<input type="text" name="keyword"></P>
	<input type="submit" value="検索">
</form>
<%
    // リクエストスコープから受け取る
    List<Product> productList = (List<Product>) request.getAttribute("productList");
    if (productList == null) {
%>
    <p style="text-align:center;">該当する商品はありませんでした。</p>
<%
    } else {
     for (Product p : productList) {
%>
      <tr>
        <td><%= p.getName() %></td>
        <td><%= p.getPrice() %>円</td>
        <td>
		    <img src="<%= request.getContextPath() %>/uploads/<%= p.getImg() %>" alt="商品画像" width="120" height="160"/>
	  	</td>
      </tr>
       
<%
     }
%>
    </table>
<%
    }
%>
<h2>商品登録</h2>
<form action="addproduct" method="post" enctype="multipart/form-data">
	<label for="category">カテゴリー:</label>
	<select name="categoryId" id="category">
	  <option value="01">少女漫画</option>
	  <option value="02">少年漫画</option>
	  <option value="03">雑誌</option>
	  <option value="04">小説</option>
	</select>
	<p>名前：<input type="text" name="name" ></p>
	<p>価格：<input type="text" name="price" ></p>
	<label for="amount">数量:</label>
	<select name="amountNum" id="amount">
	  <option value="1">1</option>
	  <option value="2">2</option>
	  <option value="3">3</option>
	  <option value="4">4</option>
	  <option value="5">5</option>
	  <option value="6">6</option>
	  <option value="7">7</option>
	  <option value="8">8</option>
	  <option value="9">9</option>
	  <option value="10">10</option>
	</select><br>
	 <label for="image">画像</label><br>
	 <input type="file" name="image" id="image" accept="image/*" required><br><br>
	 
	<input type="submit" value="登録する">
</form>
<%
    String message = (String) session.getAttribute("message");
    if (message != null) {
%>
    <p style="color: green;"><%= message %></p>
<%
        session.removeAttribute("message"); // 一度表示したら消す
    }
%>




<h2>商品一覧</h2>

<%

 List<Product> allList = (List<Product>) session.getAttribute("allproduct");

 for (Product p1 : allList) {

%>
	 <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
          <p><%= p1.getName() %></p>
          <p><%= p1.getPrice() %> 円</p>
          <p><img src="<%= request.getContextPath() %>/uploads/<%= p1.getImg() %>" alt="商品画像" width="120" height="160"/></p>
	
       
          <form action="DeleteProduct" method="post" style="display:inline;" 
                onsubmit="return confirm('本当にこの商品を削除しますか？');">
              <input type="hidden" name="productId" value="<%= p1.getProduct_id() %>">
              <input type="submit" value="削除">
          </form>

         
          <form action="FirstFixProduct" method="get" style="display:inline;">
              <input type="hidden" name="productId" value="<%= p1.getProduct_id() %>">
              <input type="submit" value="修正">
          </form>
      </div>
<%
 }

%>



</body>
</html>