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
<%@ page import="classes.ArticleCategoryVO" %>

<%! ArticleDAO articleDAO = new ArticleDAO(); %>
<%

    List<ArticleCategoryVO> articleCategories = articleDAO.getArticleCategories();
%>
<%


    int pageSize = 10; // 한 페이지에 출력할 레코드 수
    int pageNum;
    // 페이지 링크를 클릭한 번호 / 현재 페이지
    String pageNumParam = request.getParameter("pageNum");
    if (pageNumParam == null) {
        pageNum = 1;
    } else {
        pageNum = Integer.parseInt(pageNumParam);
    }



    // 연산을 하기 위한 pageNum 형변환 / 현재 페이지
    int currentPage = pageSize * (pageNum - 1);

    List<ArticleVO> articles = articleDAO.getArticles(pageSize,currentPage);
    int numberOfArticles = articleDAO.getNumberOfArticles();
%>



<html>
<head>
    <title>Title</title>
</head>
<style>
    a { text-decoration: none; color: black; }
    a:visited { text-decoration: none; }
    a:hover { text-decoration: none; }
    a:focus { text-decoration: none; }
    a:hover, a:active { text-decoration: none; }
</style>
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
                <% for (ArticleCategoryVO articleCategory : articleCategories) {%>
                <option value="<%=articleCategory.getName()%>>"><%=articleCategory.getName()%></option>
                <%}%>
            </select>
            <input type="text" name="query" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="articleList">
        <div class="articleListHeader">

            <h5>총<%=numberOfArticles%>건</h5>
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
            <div class="articleListPagination">
                <tr>
                    <td colspan="6" align="center">
                        <%	// 페이징  처리
                            if(numberOfArticles > 0){
                                // 총 페이지의 수
                                int pageCount = numberOfArticles / pageSize + (numberOfArticles%pageSize == 0 ? 0 : 1);
                                // 한 페이지에 보여줄 페이지 블럭(링크) 수
                                int pageBlock = 10;
                                // 한 페이지에 보여줄 시작 및 끝 번호(예 : 1, 2, 3 ~ 10 / 11, 12, 13 ~ 20)
                                int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
                                int endPage = startPage + pageBlock - 1;
                                if(endPage > pageCount){
                                    endPage = pageCount;
                                }
                        %>
                        <a href="articleList.jsp?pageNum=<%=1%>"><<</a>
                        <%
                           if(pageNum > 1) {
                        %>
                        <a href="articleList.jsp?pageNum=<%=pageNum - 1%>"><</a>
                        <%
                            } else {
                        %>
                        <a href="articleList.jsp?pageNum=<%=1%>"><</a>
                        <%
                            }
                        %>
                        <%
                            for(int i=startPage; i <= endPage; i++){
                                if(i == pageNum){ // 현재 페이지에 색 표시
                        %>
                        <a style="color: red"><%=i %></a>
                        <%
                        } else { // 현재 페이지가 아닌 경우 링크 설정
                        %>
                        <a href="articleList.jsp?pageNum=<%=i%>"><%=i %></a>
                        <%
                                }
                            } // for end
                        %>
                        <%
                            if(pageNum != pageCount) {
                        %>
                        <a href="articleList.jsp?pageNum=<%=pageNum + 1%>">></a>
                        <%
                        } else {
                        %>
                        <a href="articleList.jsp?pageNum=<%=pageCount%>">></a>
                        <%
                            }
                        %>
                        <a href="articleList.jsp?pageNum=<%=pageCount%>">>></a>
                        <%
                            } // last
                        %>
                    </td>
                </tr>
            </div>
            </table>
        </div>
        <div class="articleListFooter">
            <input type="submit" value="등록">
        </div>
    </div>
</div>

<!--목록-->
</body>
</html>
