<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

<%
    System.out.println("자바 시작");
    //mariaDB 준비
    String url = "jdbc:mariadb://localhost:3306/eBrain_week1";
    String userName = "root";
    String password = "1234";
    // mariaDB 연결
    Connection conn = DriverManager.getConnection(url, userName, password);
    System.out.println(conn + "<-- conn"); %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="board">
    <div class="searchBar">
        <!--검색바-->

        <form>
            등록일
            <input type="date" name="fromDate">
            ~
            <input type="date" name="toDate">
            <select name="articleCategory">
                <option value="all">전체 카테고리</option>
                <%
                    PreparedStatement pstmt = conn.prepareStatement("select * from article_category");
                    ResultSet rs = pstmt.executeQuery();
                %>
                <% while (rs.next()) {
                    String articleCategory = rs.getString("name");
                %>
                <option value="<%=articleCategory%>"><%=articleCategory%></option>
                <% }%>
            </select>
            <input type="text" name="query" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="articleList">
        <div class="articleListHeader">
            <h5>총512건</h5>
        </div>
        <div class="articleListMain">
            <%
                pstmt = conn.prepareStatement("select article_category.name, article.* from article_category, article where article_category.article_category_id = article.article_id");
                rs = pstmt.executeQuery();
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
                    <td><%=rs.getString("article_category.name") %>
                    </td>
                    <td><%=rs.getString("title") %>
                    </td>
                    <td><%=rs.getString("writer") %>
                    </td>
                    <td><%=rs.getString("views") %>
                    </td>
                    <td><%=rs.getString("date_created") %>
                    </td>
                    <td><% String lastUpdated = rs.getString("last_updated");
                        if (lastUpdated != null) { %>
                        <%=lastUpdated%>
                        <%} else {%>
                        -<%}%>
                    </td>
                </tr>

                <%
                    }
                %>
            </table>
        </div>
        <div class="articleListPagination">
            1,2,3
        </div>
        <div class="articleListFooter">
            <input type="submit" value="등록">
        </div>
    </div>
</div>

<!--목록-->
</body>
</html>
