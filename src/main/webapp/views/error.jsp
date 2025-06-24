<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>エラーが発生しました</title>
    <style>
        body {
            font-family: system-ui, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 40px 0;
            display: flex;
            justify-content: center;
        }
        .error-box {
            background: #fff;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            width: 400px;
            padding: 32px;
            text-align: center;
            box-shadow: 0 2px 6px rgba(0,0,0,.1);
        }
        .error-box h2 {
            margin-top: 0;
            margin-bottom: 16px;
            font-size: 20px;
            color: #d32f2f;
        }
        .error-box p {
            margin: 12px 0 24px;
            font-size: 14px;
        }
        .error-box a {
            display: inline-block;
            padding: 8px 24px;
            border-radius: 4px;
            background: #1976d2;
            color: #fff;
            text-decoration: none;
        }
        .error-box a:hover {
            background: #145a9e;
        }
    </style>
</head>
<body>
    <div class="error-box">
        <h2>エラーが発生しました</h2>

        <!-- リクエスト属性 errorMessage が渡されていれば表示 -->
        <p>
            <c:choose>
                <c:when test="${not empty errorMessage}">
                    <c:out value="${errorMessage}" />
                </c:when>
                <c:otherwise>
                    システムエラーが発生しました。時間をおいて再度お試しください。
                </c:otherwise>
            </c:choose>
        </p>

        <!-- ユーザー用メニューへ戻る（適宜パスを変更） -->
        <a href="<c:url value='user-menu.jsp' />">メニューに戻る</a>
    </div>
</body>
</html>