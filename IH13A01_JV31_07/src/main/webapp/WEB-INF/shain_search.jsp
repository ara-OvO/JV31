<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.*, model.Shain" %>

<%!
  private static String h(Object o) {
    if (o == null) return "";
    String s = String.valueOf(o);
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#39;");
  }
%>

<%
  final String ctx = request.getContextPath();
  final String keyword = request.getParameter("keyword");
  @SuppressWarnings("unchecked")
  final List<Shain> results = (List<Shain>) request.getAttribute("searchResults");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>社員検索</title>
</head>
<body>

  <h1>社員検索</h1>

  <!-- 検索フォーム -->
<form action="<%= request.getContextPath() %>/maintenance" method="post">
  <input type="hidden" name="action" value="search">
  氏名：
  <input type="search" name="keyword" placeholder="氏名を入力" value="<%= h(keyword) %>">
  <button type="submit">検索</button>
  <a href="<%= request.getContextPath() %>/maintenance?action=list">一覧へ戻る</a>
</form>

  <hr>

  <%
    if (results == null) {
  %>
      <p>検索キーワードを入力して「検索」を押してください。</p>
  <%
    } else if (results.isEmpty()) {
  %>
      <p>対象データはありません（0件）。</p>
  <%
    } else {
  %>
      <p><%= results.size() %> 件ヒット</p>
      <table border="1">
        <tr>
          <th>ID</th><th>氏名</th><th>性別</th><th>備考</th><th>操作</th>
        </tr>
      <%
        for (Shain row : results) {
          String editUrl = ctx + "/maintenance?action=edit&id=" + row.getId();
          String delUrl  = ctx + "/maintenance?action=delete&id=" + row.getId();
      %>
        <tr>
          <td><%= row.getId() %></td>
          <td><%= h(row.getName()) %></td>
          <td><%= h(row.getGenderLabel()) %></td>
          <td><%= h(row.getNote()) %></td>
          <td>
            <a href="<%= editUrl %>">編集</a> |
            <a href="<%= delUrl %>">削除</a>
          </td>
        </tr>
      <%
        } // for
      %>
      </table>
  <%
    } // else
  %>

</body>
</html>
