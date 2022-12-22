<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-23
  Time: 오전 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int articleId = (int) request.getAttribute("articleId");
    String path = "articleDetail?articleId=" + articleId;
    response.sendRedirect(path);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
