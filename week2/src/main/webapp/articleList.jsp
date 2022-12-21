<%@ page import="com.week2.model.ArticleVO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-22
  Time: 오전 1:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    List<ArticleVO> articleList = (List<ArticleVO>) request.getAttribute("articleList");
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    articleList
    <%=articleList.get(1).getContent()%>
</body>
</html>
