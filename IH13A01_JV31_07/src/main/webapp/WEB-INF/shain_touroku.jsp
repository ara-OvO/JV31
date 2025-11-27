<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員新規登録</title>
</head>
<body>

<h3>社員新規登録</h3>

<form action="maintenance" method="post">
    <input type="hidden" name="action" value="add">
    氏名: <input type="text" name="shimei" required><br>
    性別:
    <input type="radio" name="seibetsu" value="1" required>男性
    <input type="radio" name="seibetsu" value="2">女性
    <input type="radio" name="seibetsu" value="3">その他<br>
    備考: <textarea id="bikou" name="bikou" rows="4"></textarea><br>
    <input type="submit" value="登録">
</form>
<p><a href="maintenance">一覧に戻る</a></p>
</form>

</body>
</html>
