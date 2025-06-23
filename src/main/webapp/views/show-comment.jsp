<%@page import="jp.co.aforce.beans.Comment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント一覧画面</title>
</head>
<body>
<a href="admin-user-menu.jsp">会員一覧メニューへ戻る</a>
<%

List<Comment> allList = (List<Comment>) session.getAttribute("allcomment");

for (Comment c : allList) {


%>


   <div style="border:1px solid #ccc; padding:10px; margin-bottom:10px;">
        <p><%= c.getMember_id()%></p>
        <p><%= c.getComment()%> </p>
        <p><%= new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(c.getCreate_time()) %></p>
        <p><%= c.getMail_address() %></p>

     
        <form action="DeletetComment" method="post" style="display:inline;" 
              onsubmit="return confirm('本当にこのコメントを削除しますか？');">
            <input type="hidden" name="id" value="<%= c.getMember_id() %>">
            <input type="submit" value="削除">
        </form>

    </div>
<%
 }

%>
</body>
</html>