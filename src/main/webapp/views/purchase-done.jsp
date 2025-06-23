<%@page import="jp.co.aforce.beans.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    int cartTotal = 0;
    if (request.getAttribute("cartTotal") != null) {
        cartTotal = (Integer) request.getAttribute("cartTotal");
    }

    @SuppressWarnings("unchecked")
    List<Product> purchaseItems = (List<Product>) session.getAttribute("purchaseItems");
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>購入完了画面</title>
    <style>
        body {
            font-family: sans-serif;
            background-color: #f9f9f9;
            margin: 20px;
        }
        .container {
            text-align: center;
            padding: 30px;
        }
        table {
            border-collapse: collapse;
            margin: 0 auto;
            width: 90%;
            background-color: white;
        }
        th, td {
            border: 1px solid #ccc;
            padding: 10px;
        }
        th {
            background-color: #eee;
        }
        .total {
            font-size: 20px;
            font-weight: bold;
            margin-top: 20px;
        }
        .notice-box {
            margin: 30px auto;
            padding: 20px;
            background-color: #fff3cd;
            border: 1px solid #ffeeba;
            color: #856404;
            width: 80%;
            text-align: left;
            border-radius: 6px;
        }
    </style>
</head>
<body>

<%@ include file="globalMenu.jsp" %>

<div class="container">
    <h2>ご購入ありがとうございます！</h2>
    <p>ご購入の商品一覧と合計金額は以下の通りです。</p>

    <% if (purchaseItems != null && !purchaseItems.isEmpty()) { %>
        <table>
            <tr>
                <th>商品名</th>
                <th>価格</th>
                <th>数量</th>
                <th>小計</th>
                <th>画像</th>
            </tr>
            <% for (Product item : purchaseItems) { %>
                <tr>
                    <td><%= item.getName() %></td>
                    <td>¥<%= item.getPrice() %></td>
                    <td><%= item.getAmount() %></td>
                    <td>¥<%= item.getPrice() * item.getAmount() %></td>
                    <td>
                        <img src="<%= request.getContextPath() %>/uploads/<%= item.getImg() %>" 
                             alt="商品画像" width="120" height="160"/>
                    </td>
                </tr>
            <% } %>
        </table>
    <% } else { %>
        <p>購入商品が見つかりませんでした。</p>
    <% } %>

    <div class="total">
        合計金額：¥<%= cartTotal %>
    </div>

    <!-- ✔️ 購入後の案内メッセージ -->
    <div class="notice-box">
        <p>お客様の購入が完了しました！到着期日の目安は、<strong>購入日から３日前後</strong>です。</p>
        <p>締め切りは、<strong>今月末</strong>、お支払期日は、<strong>翌月10日まで</strong>です。</p>
        <p>請求書が届き次第、記載してある振込用銀行口座まで、<strong>お振込をお願いいたします</strong>。</p>
    </div>
</div>

</body>
</html>
