<%@page import="jp.co.aforce.beans.Users"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

Users userName =(Users)session.getAttribute("userInfo");


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザー修正画面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/style.css">

</head>
<body>
<div class="user-update">
	<h2>会員情報編集画面</h2>
</div>


<div class="container">
	<form action="user-update2.jsp" method="post">
	    <input type="hidden" name="id" value="<%=userName.getMEMBER_ID()%>"><br> 

		<input type="text" name="last_name" value="<%=userName.getLAST_NAME()%>" required class="form-input"><br>
		
		<input type="text" name="first_name" value="<%=userName.getFIRST_NAME() %>" required class="form-input"><br>
		
		<input type="text" name="address" value="<%=userName.getADDRESS() %>" required class="form-input"><br>
		
		<input type="email" name="mail_address" value="<%=userName.getMAIL_ADDRESS() %>" required class="form-input"><br>

	  <input type="submit" value="確認" class="register-submit-button">
	</form>
</div>
 <a href="user-menu.jsp" class="back-to-login">ひとつ前の画面へ戻る</a>
 
 <script>
	document.addEventListener("DOMContentLoaded", function() {
	  const form = document.querySelector("form");
	
	  form.addEventListener("submit", function(event) {
	    const lastName = document.querySelector("input[name='last_name']").value.trim();
	    const firstName = document.querySelector("input[name='first_name']").value.trim();
	    const address = document.querySelector("input[name='address']").value.trim();
	    const mailAddress = document.querySelector("input[name='mail_address']").value.trim();
	
	    let errorMessages = [];
	
	    if (lastName.length > 32) {
	      errorMessages.push("姓（last_name）は32文字以内で入力してください。");
	    }
	
	    if (firstName.length > 32) {
	      errorMessages.push("名（first_name）は32文字以内で入力してください。");
	    }
	
	    if (address.length > 128) {
	      errorMessages.push("住所は128文字以内で入力してください。");
	    }
	
	    if (mailAddress.length > 128) {
	      errorMessages.push("メールアドレスは128文字以内で入力してください。");
	    }
	
	    if (errorMessages.length > 0) {
	      alert(errorMessages.join("\n"));
	      event.preventDefault(); // フォーム送信をキャンセル
	    }
	  });
	});
</script>
 

</body>
</html>