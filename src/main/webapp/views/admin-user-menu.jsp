<%@page import="java.util.List"%>
<%@page import="jp.co.aforce.beans.Users"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<%
 Users userName =(Users)session.getAttribute("userInfo");
%>
<body class="admin-body">
	<p class="greeting">こんにちは！<%=userName.getFullName() %>さん。こちらは管理者専用画面です</p>

	<div class="admin-menu">
		<form action="LogoutServlet" method="get" onsubmit="return confirmLogout();">
		    <input type="submit" value="ログアウト" class="btn logout-btn">
		</form>

		<form action="AllProduct" method="post">
			<p><input type="submit" value="商品登録へ" class="btn menu-btn"></p>
		</form>

		<form method="post" action="AInvoice">
			<p><input type="submit" value="請求一覧へ" class="btn menu-btn"></p>
		</form>

		<form method="post" action="AComment">
			<p><input type="submit" value="コメント確認へ" class="btn menu-btn"></p>
		</form>
	</div>
	
	<table class="user-table">
	 <thead>
	  <tr>
	    <th>メンバーID</th>
	    <th>姓</th>
	    <th>名</th>
	    <th>住所</th>
	    <th>メールアドレス</th>
	    <th>削除</th>
	    <th>編集</th>
	  </tr>
	 </thead>
	 <tbody>
	<%
	  List<Users> allList = (List<Users>) session.getAttribute("alluser");
	  for (Users u : allList) {
	%>
		<tr>
		      <td><%= u.getMEMBER_ID()%></td>
		      <td><%= u.getLAST_NAME()%></td>
		      <td><%= u.getFIRST_NAME()%></td>
		      <td><%= u.getADDRESS()%></td>
		      <td><%= u.getMAIL_ADDRESS()%></td>
		      <td>
		      	  <form action="ADeleteUser" method="post" style="display:inline;" 
	                onsubmit="return confirm('本当にこのユーザーを削除しますか？');">
			        <input type="hidden" name="id" value="<%= u.getMEMBER_ID()%>">
			        <input type="submit" value="削除" class="btn delete-btn">
			      </form>
		      </td>
		       <td>
		      	  <form action="FirstFixUser" method="get">
			        <input type="hidden" name="id" value="<%= u.getMEMBER_ID()%>">
			        <input type="submit" value="編集" class="btn edit-btn">
			      </form>
		      </td>
	     </tr>
	<%
	  }
	%>	
	</tbody>
</table>

<script>
    function confirmLogout() {
        return confirm("本当にログアウトしますか？");
    }
</script>
</body>
</html>
