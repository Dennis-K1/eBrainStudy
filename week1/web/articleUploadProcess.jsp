<%@ page import="classes.ArticleVO" %>
<%@ page import="classes.ArticleDAO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-16
  Time: 오전 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8") ;%>
<%
    String articleCategory = request.getParameter("articleCategory");
    String writer = request.getParameter("writer");
    String password = request.getParameter("password");
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    //값 유효성 검증 if or return to uploadform (데이터 유지)

    ArticleVO article = new ArticleVO();
    int articleCategoryId = Integer.parseInt(articleCategory);
    article.setArticleCategoryId(articleCategoryId);
    article.setWriter(writer);
    article.setPassword(password);
    article.setTitle(title);
    article.setContent(content);

    //등록
    ArticleDAO articleDAO = new ArticleDAO();
    int result = articleDAO.uploadArticle(article);

    //db 등록 검증
    if (result == 1) {
        response.sendRedirect("/articleList.jsp");
    }
%>

