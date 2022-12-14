<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="classes.ArticleDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.ArticleVO" %>


<%
    ArticleDAO articleDAO = new ArticleDAO();
    List<ArticleVO> articles = articleDAO.getArticles();
%>
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
            </select>
            <input type="text" name="query" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="articleList">
        <div class="articleListHeader">

            <h5>총<%=articles.size()%>건</h5>
        </div>
        <div class="articleListMain">

            <table border="1">
                <tr>
                    <td>카테고리</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>조회수</td>
                    <td>등록 일시</td>
                    <td>수정 일시</td>
                </tr>
                <% for (ArticleVO article : articles) {%>
                <tr>
                    <td><%=article.getArticleCategoryName()%></td>
                    <td><%=article.getTitle()%></td>
                    <td><%=article.getWriter()%></td>
                    <td><%=article.getViews()%></td>
                    <td><%=article.getDateCreated()%></td>
                    <td><%=article.getLastUpdated()%></td>
                </tr>
                <% }%>
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
