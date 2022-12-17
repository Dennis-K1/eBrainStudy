<%@ page import="classes.ArticleCommentVO" %>
<%@ page import="classes.ArticleCommentDAO" %>
<%@ page import="classes.ArticleCommentDAO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-17
  Time: 오후 7:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8") ;%>
<%
  int articleId = Integer.parseInt(request.getParameter("articleId"));
  String content = request.getParameter("content");

  //값 유효성 검증 if or return to uploadform (데이터 유지)

  ArticleCommentVO comment = new ArticleCommentVO();
  comment.setArticleId(articleId);
  comment.setContent(content);

  //등록
  ArticleCommentDAO articleCommentDAO = new ArticleCommentDAO();
  int result = articleCommentDAO.uploadComment(comment);

  //db 등록 검증후 article 상세페이지로
  if (result == 1) {

    // 기존 검색값 유지하여 업데이트된 게시글 표시
    session.setAttribute("fromDate",request.getParameter("fromDate"));
    session.setAttribute("toDate",request.getParameter("toDate"));
    session.setAttribute("category",request.getParameter("category"));
    session.setAttribute("query",request.getParameter("query"));
    session.setAttribute("pageNum",request.getParameter("pageNum"));

    response.sendRedirect("/articleDetail.jsp?articleId=" + articleId);
  }
%>