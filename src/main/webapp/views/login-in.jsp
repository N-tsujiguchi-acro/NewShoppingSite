<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログインページ</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

    
</head>
<body>
    <div class="container">
        <h1 class="title">📚 Book to Book</h1>
        <p class="subtitle">ようこそ！こちらはログインページです！</p>

        <form action="login-in" method="POST" class="login-form">
            <label for="id">ID:</label>
            <input type="text" id="id" name="id" minlength="7" maxlength="10" required>

            <label for="password">PassWord:</label>
            <input type="password" id="password" name="password" minlength="7" maxlength="32" required>

            <input type="submit" value="ログイン" class="login-button">
        </form>

        <p class="register-link">
            <a href="new-register.jsp">新規会員登録へ</a>
        </p>
    </div>
</body>
</html>
