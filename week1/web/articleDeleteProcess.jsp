<%@ page import="classes.ArticleDAO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-18
  Time: 오전 1:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8") ;%>
<%
    int articleId = (Integer)session.getAttribute("articleId");
    session.removeAttribute("articleId");

    ArticleDAO articleDAO = new ArticleDAO();
    int result = articleDAO.deleteArticle(articleId);

    //db 등록 검증
    if (result == 1) {
        response.sendRedirect("/articleList.jsp");
    }
%>
