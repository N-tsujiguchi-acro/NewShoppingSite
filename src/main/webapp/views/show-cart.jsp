<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="jp.co.aforce.beans.Product" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログインページ</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

    
</head>
<body>
	<%@ include file="globalMenu.jsp" %>
	<main>
		<h2>カートの中身</h2>
		<form id="purchaseForm" action="PurchaseProduct" method="post">

			<table border="1" cellpadding="8" id="cart-table">
				<tr>
					<th>商品名</th>
					<th>価格</th>
					<th>数量</th>
					<th>小計</th>
					<th>画像</th>
					<th>削除</th>
				</tr>

				<%
				List<Product> cartItems = (List<Product>) request.getAttribute("cartItems");
				int cartTotal = 0;
				if (cartItems != null) {
					for (Product item : cartItems) {
						int subtotal = item.getPrice() * item.getAmount();
						cartTotal += subtotal;
				%>
				<tr>
					<td class="item-name"><%=item.getName()%></td>

					<td class="item-price" data-price="<%=item.getPrice()%>"><%=item.getPrice()%>
						円</td>

					<td>
						<!-- 数量セレクトは購入フォームに属する --> <select name="amount"
						class="amount-select" data-price="<%=item.getPrice()%>"
						onchange="updateRow(this)">
							<%
							for (int q = 1; q <= 10; q++) {
							%>
							<option value="<%=q%>" <%=q == item.getAmount() ? "selected" : ""%>>
								<%=q%>
							</option>
							<%
							}
							%>
					</select>
					</td>

					<td class="subtotal" data-raw="<%=subtotal%>"><%=subtotal%>
						円</td>

					<td><img
						src="<%=request.getContextPath()%>/img/<%=item.getImg()%>"
						width="120" height="160"></td>

					<!-- 削除ボタン用フォームは行の外側に置く（入れ子しない） -->
					<td>
						  <a href="CartDelete?product_id=<%= item.getProduct_id() %>"
						     onclick="return confirm('本当に削除しますか？');">
						     削除
						  </a>
					</td>

					<!-- 商品ID (購入用) hidden -->
					<input type="hidden" name="product_id"
						value="<%=item.getProduct_id()%>">
				</tr>
				<%
				}
				}
				%>

				<!-- 合計行 -->
				<tr>
					<td colspan="3" style="text-align: right; font-weight: bold;">合計</td>
					<td id="cart-total" data-raw="<%=cartTotal%>"
						style="font-weight: bold;"><%=cartTotal%> 円</td>
					<td colspan="2" style="text-align: center;"><input
						type="hidden" id="cartTotalHidden" name="cart_total"
						value="<%=cartTotal%>">
						<button type="submit" onclick="return confirm('本当に購入しますか？');">購入する</button></td>
				</tr>
			</table>
		</form>
		<%-- ▲ 購入フォーム終了 ------------------------------------------- --%>

		<a href="user-menu.jsp">ひとつ前へ戻る</a>

		<!-- ④ JS：数量変更で小計・合計を自動再計算 -->
		
	</main>
	
	<script>
	/*----------------------------------------------------
	  数量プルダウンを変更したら小計と合計を更新
	----------------------------------------------------*/
	function updateRow(sel){
	  /* 1) 単価と数量を取得 */
	  const row   = sel.closest('tr');
	  const price = parseInt(sel.dataset.price, 10);            // data-price はセレクトに付与
	  const qty   = parseInt(sel.value,       10);
	
	  /* 2) 小計セルを書き換え */
	  const subCell    = row.querySelector('.subtotal');
	  const newSub     = price * qty;
	  subCell.dataset.raw = newSub;                             // ← 必ず入れる
	  subCell.textContent = newSub.toLocaleString('ja-JP') + ' 円';
	
	  /* 3) 全行を回して合計を再計算 */
	  let total = 0;
	  document.querySelectorAll('.subtotal').forEach(td=>{
	    const raw = parseInt(td.dataset.raw || '0', 10);        // 無い時は 0 扱い
	    total += raw;
	  });
	
	  /* 4) 合計セルと hidden を更新 */
	  const totalCell = document.getElementById('cart-total');
	  totalCell.dataset.raw = total;
	  totalCell.textContent = total.toLocaleString('ja-JP') + ' 円';
	
	  const hiddenTotal = document.getElementById('cartTotalHidden');
	  if (hiddenTotal) hiddenTotal.value = total;               // サーブレット送信用
	}
	</script>

	

</body>
</html>


