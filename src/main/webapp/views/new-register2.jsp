<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");
    String memberId = request.getParameter("member_id");
    String password = request.getParameter("password");
    String lastName = request.getParameter("last_name");
    String firstName = request.getParameter("first_name");
    String address = request.getParameter("address");
    String mailAddress = request.getParameter("mail_address");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登録確認画面</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
</head>
<body>
    <h2 class="register-title">登録内容確認</h2>
    
    <div class="container">
        <table class="register-table">
            <tr><td>会員ID:</td><td><%= memberId %></td></tr>
            <tr><td>パスワード:</td><td>********</td></tr>
            <tr><td>姓:</td><td><%= lastName %></td></tr>
            <tr><td>名:</td><td><%= firstName %></td></tr>
            <tr><td>住所:</td><td><%= address %></td></tr>
            <tr><td>メールアドレス:</td><td><%= mailAddress %></td></tr>
        </table>

        
        <form action="new-register.jsp" method="post" style="display:inline;">
            <input type="hidden" name="member_id" value="<%= memberId %>">
            <input type="hidden" name="password" value="<%= password %>">
            <input type="hidden" name="last_name" value="<%= lastName %>">
            <input type="hidden" name="first_name" value="<%= firstName %>">
            <input type="hidden" name="address" value="<%= address %>">
            <input type="hidden" name="mail_address" value="<%= mailAddress %>">
            <input type="submit" value="戻る" class="register-submit-button">
        </form>

       
        <form action="register" method="post" style="display:inline;">
            <input type="hidden" name="member_id" value="<%= memberId %>">
            <input type="hidden" name="password" value="<%= password %>">
            <input type="hidden" name="last_name" value="<%= lastName %>">
            <input type="hidden" name="first_name" value="<%= firstName %>">
            <input type="hidden" name="address" value="<%= address %>">
            <input type="hidden" name="mail_address" value="<%= mailAddress %>">
            <input type="submit" value="登録する" class="register-submit-button">
        </form>
    </div>
</body>
</html>



