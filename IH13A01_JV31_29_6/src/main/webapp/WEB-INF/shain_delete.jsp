<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Shain" %>
<%
    Shain s = (Shain) request.getAttribute("shain");
%>
<html>
<head>
    <title>削除確認</title>
</head>
<body>
<h2>削除確認</h2>
<p>以下のデータを削除してよろしいですか？</p>
<ul>
    <li>氏名: <%= s.getName() %></li>
    <li>性別: <%= s.getGenderLabel() %></li>
    <li>備考:
        <%= (s.getNote() == null || s.getNote().trim().isEmpty())
                ? "(登録されていません)"
                : s.getNote() %>
    </li>
</ul>
<form action="maintenance" method="post">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="id" value="<%= s.getId() %>">
    <input type="submit" value="削除">
</form>
<p><a href="maintenance">キャンセル</a></p>
</body>
</html>
