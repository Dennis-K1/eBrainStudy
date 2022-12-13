<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-13
  Time: 오후 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  $END$
  <%
    System.out.println("자바 시작");
    //mariaDB 준비
    String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    String userName = "root";
    String password = "1234";
    // mariaDB 연결
    Connection conn = DriverManager.getConnection(url, userName, password);
    System.out.println(conn + "<-- conn");

    // 쿼리
    PreparedStatement stmt = conn.prepareStatement("select * from article");
    System.out.println(stmt + "<-- stmt");

    // 쿼리 실행
    ResultSet rs = stmt.executeQuery();

  %>
  <table border="1">
    <tr>
      <td>카테고리</td>
      <td>제목</td>
      <td>작성자</td>
      <td>조회수</td>
      <td>등록 일시</td>
      <td>수정 일시</td>
    </tr>

    <%
      while (rs.next()) {
    %>
    <tr>
      <td><%=rs.getString("article_category_id") %>
      </td>
      <td><%=rs.getString("title") %>
      </td>
      <td><%=rs.getString("writer") %>
      </td>
      <td><%=rs.getString("views") %>
      </td>
      <td><%=rs.getString("date_created") %>
      </td>
      <td><%=rs.getString("last_updated") %>
      </td>
    </tr>

    <%
      }
    %>
  </table>
  </body>
</html>
