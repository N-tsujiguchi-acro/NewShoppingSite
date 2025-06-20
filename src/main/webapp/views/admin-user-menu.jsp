<%@page import="java.util.List"%>
<%@page import="jp.co.aforce.beans.Users"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理者画面</title>
</head>
<%
 Users userName =(Users)session.getAttribute("userInfo");
%>
<body>
	<p>こんにちは！<%=userName.getFullName() %>さん。こちらは管理者専用画面です</p>
	
	<div>
		<form action="AllProduct" method="post">
		
			<p><input type="submit"  value="商品登録へ"></p>
		
		</form>
		<div>
			<p><input type="button" name="all" value="請求一覧へ"></p>
		</div>
		<div>
			<p><input type="button" name="all" value="コメント確認へ"></p>
		</div>
	</div>
	
<table>
	 <tr>
	    <th>メンバーID</th>
	    <th>LAST_NAME</th>
	    <th>FIRST_NAME</th>
	    <th>住所</th>
	    <th>メールアドレス</th>
	    <th>削除</th>
	    <th>編集</th>
	  </tr>

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
			        <input type="submit" value="削除">
			      </form>
		      </td>
		       <td>
		      	  <form action="FirstFixUser" method="get">
			        <input type="hidden" name="id" value="<%= u.getMEMBER_ID()%>">
			        <input type="submit" value="編集">
			      </form>
		      </td>
	     </tr>
	<%
	  }
	%>	
	
</table>
</body>
</html>