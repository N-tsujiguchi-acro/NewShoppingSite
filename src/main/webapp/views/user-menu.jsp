<%@page import="jp.co.aforce.beans.Product"%>
<%@page import="java.util.List"%>
<%@page import="jp.co.aforce.beans.Users"%>
<%@ page import="java.util.Collections,java.util.Comparator" %>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
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
		    <button type="submit">ログアウトへ</button>
		</form>

	
	</div>
	

	<dv class="main_content">
		
		<div class="manga_product">
			<form action="UProductSearch" method="post">
				<p>検索：<input type="text" name="keyword"></p>
				<input type="submit" value="検索">
			</form>
			<%
			    // リクエストスコープから受け取る
			    List<Product> productList = (List<Product>) request.getAttribute("productList");
			    if (productList == null) {
			%>
			    <p style="text-align:center;">商品を検索してください。</p>
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
			
		</div>
	
		<div class="contents">
			<h2>最新のおすすめ漫画！！</h2>
			  <ul class="slideshow">
			    <li class="slide item">
			        <div>
			            <h2>王道をいけ！名作バトル漫画！</h2>
			            <img src="../img/battle_manga.png" alt="manga1">
			        </div>
			    </li>
			    <li class="slide item">
			        <div>
			            <h2>漫画フェア～あなたも出会える最高の漫画たち～</h2>
			            <img src="../img/gati_fea.png" alt="manga2">
			        </div>
			    </li>
			    <li class="slide item">
			        <div>
			            <h2>ジャンプ漫画～とべ！ジャンプの世界へ～</h2>
			            <img src="../img/main_jumpe.jpg" alt="manga3">
			        </div>
			    </li>
			    <li class="slide item">
			        <div>
			            <h2>忍者たちの世界へあなたもご一緒に♡</h2>
			            <img src="../img/ninnjya.jpg" alt="manga4">
			        </div>
			    </li>
			</ul>
			
		</div>
	</dv>
	<%
	  List<Product> allList = (List<Product>) session.getAttribute("allproduct");
	
	  // ----- 並び替え処理 -----
	  String order = request.getParameter("order");   // "asc" / "desc" / null
	  if (allList != null && order != null) {
	      // 昇順ソート
	      allList.sort(Comparator.comparingInt(Product::getPrice));
	
	      // 降順指定なら逆順に
	      if ("desc".equals(order)) {
	          Collections.reverse(allList);
	      }
	  }
	%>

	<div class="main_content2">
	  <h2>商品一覧</h2>
	  <form id="sortForm" method="get" action="user-menu.jsp" >
		  <h2 for="order">価格順 : </h2><br>
		  <select name="order" id="order" onchange="document.getElementById('sortForm').submit();">
		    <option value="">-- 選択 --</option>
		    <option value="asc"  <%= "asc".equals(request.getParameter("order"))  ? "selected" : "" %>>安い順</option>
		    <option value="desc" <%= "desc".equals(request.getParameter("order")) ? "selected" : "" %>>高い順</option>
		  </select>
	  </form>
	
	  <%
	  
	    for (Product p1 : allList) {
	  %>
	    <div class="product-card">
	      <p><%= p1.getName() %></p>
	      <p><%= p1.getPrice() %> 円</p>
	      <p>
	        <img src="<%= request.getContextPath() %>/uploads/<%= p1.getImg() %>" 
	             alt="商品画像" />
	      </p>
	
	      <form action="AddCart" method="post" 
	            onsubmit="return confirm('この商品をカートに追加しますか？');">
	        <input type="hidden" name="productId" value="<%= p1.getProduct_id() %>">
	        <input type="submit" value="カートに追加する">
	      </form>
	    </div>
	  <%
	    }
	  %>
	</div>


</div>

<script>
	
	
	const slides = document.querySelectorAll('.slide');
	let currentIndex = 0;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === index);
        });
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    }

    // 初期表示
    showSlide(currentIndex);

    // 5秒ごとに次のスライドへ
    setInterval(nextSlide, 5000);
</script>
</body>
</html>