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

<!-- 月選択フォーム -->
<form action="MonthFilter" method="get" class="search-form">
    <label>月選択：</label>
    <select name="month" class="select-box">
        <%
            java.time.Month currentMonth = java.time.LocalDate.now().getMonth();
            for (int m = 1; m <= 12; m++) {
        %>
        <option value="<%= m %>" <%= (m == currentMonth.getValue()) ? "selected" : "" %>>
            <%= m %>月
        </option>
        <% } %>
    </select>
    <input type="submit" value="月で絞り込む" class="btn menu-btn">
</form>

<hr>

<%
    List<Invoices> allList = (List<Invoices>) session.getAttribute("allinvoices");
    String nodataMsg = (String) session.getAttribute("nodataMsg");

    if (nodataMsg != null) {
%>
    <p class="no-result"><%= nodataMsg %></p>
<%
    } else if (allList != null && !allList.isEmpty()) {
%>
    <h3 class="section-title">会員別 月次請求合計</h3>
    <table class="user-table">
        <tr>
            <th>会員ID</th>
            <th>姓</th>
            <th>名</th>
            <th>住所</th>
            <th>合計金額</th>
        </tr>
        <%
            for (Invoices i : allList) {
        %>
        <tr>
            <td><%= i.getMember_id() %></td>
            <td><%= i.getLast_name() %></td>
            <td><%= i.getFirst_name() %></td>
            <td><%= i.getAddress() %></td>
            <td><%= i.getTotal() %> 円</td>
        </tr>
        <% } %>
    </table>
<%
    }
    session.removeAttribute("allinvoices");
    session.removeAttribute("nodataMsg");
%>

</body>
</html>
