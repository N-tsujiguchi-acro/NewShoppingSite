<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>コメント入力</title>
<style>
    body{font-family:sans-serif;background:#f5f6f8;margin:0}
    .wrapper{max-width:600px;margin:60px auto;padding:30px;background:#fff;
             border-radius:8px;box-shadow:0 2px 8px rgba(0,0,0,.1)}
    h2{text-align:center;margin-bottom:25px}
    textarea{width:100%;height:140px;padding:10px;font-size:14px;
             border:1px solid #ccc;border-radius:4px;resize:vertical}
    .btn{display:block;margin:20px auto 0;background:rgb(255, 128, 128);color:#fff;
         border:none;padding:10px 40px;font-size:16px;border-radius:4px;
         cursor:pointer}
    .btn:hover{opacity:.9}
    /* ✅ 送信完了メッセージ */
    .success-box{background:#d4edda;border:1px solid #c3e6cb;color:#155724;
                 padding:15px;border-radius:6px;margin-bottom:20px;text-align:center}
</style>
</head>
<body>

<%@ include file="globalMenu.jsp" %>

<div class="wrapper">

    <!-- ===== 送信が完了していたら表示 ===== -->
    <%
        // リダイレクト時に ?status=success が付いている想定
        String status = request.getParameter("status");
        if ("success".equals(status)) {
    %>
        <div class="success-box">コメント送信が完了しました！</div>
    <%
        }
    %>

    <h2>コメントを投稿する</h2>
    <form action="CommentServlet" method="post">
        <textarea name="comment" required
                  placeholder="ご意見・ご感想をご自由にどうぞ"></textarea>
        <button type="submit" class="btn">送信</button>
    </form>
</div>

</body>
</html>
