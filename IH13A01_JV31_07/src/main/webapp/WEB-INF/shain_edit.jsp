<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Shain" %>
<%
    Shain s = (Shain) request.getAttribute("shain");
%>
<html>
<head>
    <title>社員編集</title>
    <script>
        function validateForm() {
            const name = document.forms["shainForm"]["name"].value;
            const gender = document.forms["shainForm"]["gender"].value;
            if (name === "" || gender === "") {
                alert("氏名と性別は必須です。");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<h2>社員編集</h2>
<form name="shainForm" action="maintenance" method="post" onsubmit="return validateForm()">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="<%= s.getId() %>">
    氏名: <input type="text" name="shimei" value="<%= s.getName() %>" ><br>
    性別:
    <input type="radio" name="seibetsu" value="1" <%= s.getGender().equals("1") ? "checked" : "" %>>男性
    <input type="radio" name="seibetsu" value="2" <%= s.getGender().equals("2") ? "checked" : "" %>>女性
    <input type="radio" name="seibetsu" value="3" <%= s.getGender().equals("3") ? "checked" : "" %>>その他<br>
    備考: <textarea id="bikou" name="bikou" rows="4"><%= s.getNote() %></textarea><br>
    <input type="submit" value="更新">
</form>
<p><a href="maintenance">一覧に戻る</a></p>
</body>
</html>
