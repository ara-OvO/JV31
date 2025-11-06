<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.Shain" %>
<%
    List<Shain> list = (List<Shain>) request.getAttribute("shainList");

	//検索した際に、検索条件リセットされないように保持する
	String name = (String) request.getAttribute("name");
	String gender =(String) request.getAttribute("gender");
	String note = (String) request.getAttribute("note");
	String sortOrder = (String) request.getAttribute("sortOrder");
%>
<html>
<head><title>社員一覧</title></head>
<body>
<h2>社員一覧</h2>
<p><a href="maintenance?action=add">新規追加</a></p>
  <!-- 検索フォーム -->
<h3>条件検索</h3>
<form action="maintenance" method="get">
	<input type="hidden" name="action" value="list"> 氏名：
	<input type="text" name="name" value="<%= name != null ? name : "" %>">
	
	性別：<select name="gender">
	  <option value="">-- 全て --</option>
	  <option value="1" <%= "1".equals(gender) ? "selected" : "" %>>男性</option>
	  <option value="2" <%= "2".equals(gender) ? "selected" : "" %>>女性</option>
	  <option value="3" <%= "3".equals(gender) ? "selected" : "" %>>その他</option>
	</select>
	
	備考：<input type="text" name="note"
	  value="<%= note != null ? note : "" %>">
	  
	並び順：<select name="sortOrder">
	  <option value="">指定なし（ID順）</option>
	  <option value="asc"  <%= "asc".equals(sortOrder)  ? "selected" : "" %>>氏名昇順</option>
	  <option value="desc" <%= "desc".equals(sortOrder) ? "selected" : "" %>>氏名降順</option>
	</select> <input type="submit" value="検索">
</form>

<h3>検索一覧</h3>
<% if (list == null || list.isEmpty()) { %>
    <p>データが登録されていません</p>
<% } else { %>
    <table border="1">
    <tr><th>ID</th><th>氏名</th><th>性別</th><th>備考</th></tr>
    <% for (Shain s : list) { %>
    <tr>
        <td><%= s.getId() %></td>
        <td><%= s.getName() %></td>
        <td><%= s.getGenderLabel() %></td>
        <td><%= s.getNote() %></td>
    </tr>
    <% } %>
    </table>
<% } %>
</body>
</html>
