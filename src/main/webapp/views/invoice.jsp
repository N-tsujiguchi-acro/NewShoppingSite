<%@page import="jp.co.aforce.beans.Invoices"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>請求一覧画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-style.css">
</head>
<body class="admin-body">

<a href="admin-user-menu.jsp" class="back-link">会員一覧メニューへ戻る</a>

<form action="MemberSearch" method="post" class="search-form">
	<p>ID検索　ID：<input type="text" name="id" pattern="[A-Za-z0-9]+" title="半角英数字のみ入力してください" class="text-input"></p>
	<input type="submit" value="検索" class="btn menu-btn">
</form>

<hr>

<%
    Invoices invoice = (Invoices) request.getAttribute("InvoiceUser");

    if (invoice != null) {
%>
    <h3 class="section-title">検索結果</h3>
    <table class="user-table">
        <tr>
            <th>会員ID</th>
            <th>姓</th>
            <th>名</th>
            <th>住所</th>
            <th>購入ID</th>
            <th>合計金額</th>
            <th>ステータス</th>
        </tr>
        <tr>
            <td><%= invoice.getMember_id() %></td>
            <td><%= invoice.getLast_name() %></td>
            <td><%= invoice.getFirst_name() %></td>
            <td><%= invoice.getAddress() %></td>
            <td><%= invoice.getPurchase_id() %></td>
            <td><%= invoice.getTotal() %> 円</td>
            <td><%= invoice.getStatus() %></td>
        </tr>
    </table>
<%
    } else {
%>
    <p class="no-result"></p>
<%
    }
%>

<%
    String updatedId = (String) session.getAttribute("updatedId");
    List<Invoices> allList = (List<Invoices>) session.getAttribute("allinvoices");

    if (allList != null) {
        for (Invoices i : allList) {
%>
    <div class="invoice-card">
        <p><strong>会員ID：</strong><%= i.getMember_id() %></p>
        <p><strong>名前：</strong><%= i.getLast_name() %> <%= i.getFirst_name() %></p>
        <p><strong>住所：</strong><%= i.getAddress() %></p>
        <p><strong>合計：</strong><%= i.getTotal() %> 円</p>
        <% if (i.getMember_id().equals(updatedId)) { %>
            <p class="success-message">ステータスを更新しました！</p>
        <% } %>
        <form action="InvoiceUpdate" method="post" class="inline-form"
              onsubmit="return confirm('本当にこのユーザーのステータスを更新しますか？');">
            <input type="hidden" name="id" value="<%= i.getMember_id() %>">
            <select name="status" class="select-box">
                <option value="未請求"   <%= "未請求".equals(i.getStatus())   ? "selected" : "" %>>未請求</option>
                <option value="請求済み" <%= "請求済み".equals(i.getStatus()) ? "selected" : "" %>>請求済み</option>
                <option value="未払い"   <%= "未払い".equals(i.getStatus())   ? "selected" : "" %>>未払い</option>
            </select>
            <input type="submit" value="更新" class="btn edit-btn">
        </form>
    </div>
<%
        }
    }
%>

<%-- フラッシュメッセージ消去 --%>
<%
session.removeAttribute("updatedId");
session.removeAttribute("flashMsg");
%>

</body>
</html>
