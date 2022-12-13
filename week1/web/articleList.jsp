<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>

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
                <option value="java">JAVA</option>
                <option value="Javascript">Javascript</option>
                <option value="Database">Database</option>
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
            <table border="1" width="600">
                <tr>
                    <td>카테고리</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>조회수</td>
                    <td>등록 일시</td>
                    <td>수정 일시</td>
                </tr>
                <tr>
                    <td>JAVA</td>
                    <td>THIS IS SAMPLE TITLE1</td>
                    <td>TEST WRITER1</td>
                    <td>123</td>
                    <td>2022.04.08 16:32</td>
                    <td>2022.04.09 12:00</td>
                </tr>
                <tr>
                    <td>Javascript</td>
                    <td>THIS IS SAMPLE TITLE2</td>
                    <td>TEST WRITER2</td>
                    <td>0</td>
                    <td>2022.04.08 16:32</td>
                    <td>-</td>
                </tr>
            </table>
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
