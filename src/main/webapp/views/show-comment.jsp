<%@page import="jp.co.aforce.beans.Comment"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント一覧画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<body class="admin-body">

<a href="admin-user-menu.jsp" class="back-link">会員一覧メニューへ戻る</a>

<%
    List<Comment> allList = (List<Comment>) session.getAttribute("allcomment");

    if (allList == null || allList.isEmpty()) {
%>
    <p class="no-result">現在コメントはありません。</p>
<%
    } else {
        for (Comment c : allList) {
%>
   <div class="comment-card">
        <p class="comment-member"><strong>会員ID：</strong><%= c.getMember_id() %></p>
        <p class="comment-text"><strong>コメント：</strong><%= c.getComment() %></p>
        <p class="comment-time"><strong>日時：</strong><%= new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm").format(c.getCreate_time()) %></p>
        <p class="comment-email"><strong>メール：</strong><%= c.getMail_address() %></p>

        <form action="DeletetComment" method="post" style="display:inline;" 
              onsubmit="return confirm('本当にこのコメントを削除しますか？');">
            <input type="hidden" name="id" value="<%= c.getMember_id() %>">
            <input type="submit" value="削除" class="btn delete-btn">
        </form>
    </div>
<%
        } // for
    } // else
%>

</body>
</html>
