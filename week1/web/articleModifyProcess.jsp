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
    String writer = request.getParameter("writer");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    int articleId = Integer.parseInt(request.getParameter("articleId"));

    //값 유효성 검증 if or return to uploadform (데이터 유지)

    //등록
    ArticleDAO articleDAO = new ArticleDAO();
    int result = articleDAO.modifyArticle(writer,title,content,articleId);

    //db 등록 검증
    if (result == 1) {
        response.sendRedirect("/articleDetail.jsp?articleId=" + articleId);
    }
%>

