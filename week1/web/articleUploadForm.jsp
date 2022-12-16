<%@ page import="classes.ArticleDAO" %>
<%@ page import="classes.ArticleCategoryVO" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8") ;%>
<%! ArticleDAO articleDAO = new ArticleDAO();%>
<%
    // 카테고리 값 호출
    List<ArticleCategoryVO> articleCategories = articleDAO.getArticleCategories();
%>

<%
    // 뒤로가기 시 검색값 유지를 위한 세팅
    request.setAttribute("fromDate",request.getAttribute("fromDate"));
    request.setAttribute("toDate",request.getAttribute("toDate"));
    request.setAttribute("category",request.getAttribute("category"));
    request.setAttribute("query",request.getAttribute("query"));
%>


<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="articleUploadForm">
        <% //  onsubmit 유효성 검증 추가 필요 %>
        <form name="article" method="post" action="articleUploadProcess.jsp">
            <table>
                <tr>
                    <td>카테고리 *</td>
                    <td>
                        <select name="articleCategory">
                            <option value="none">카테고리 선택</option>
                            <% for (ArticleCategoryVO articleCategory : articleCategories) {%>
                            <option value="<%=articleCategory.getId()%>"><%=articleCategory.getName()%></option>
                            <%}%>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>작성자 *</td>
                    <td>
                        <input type="text" name="writer">
                    </td>
                </tr>
                <tr>
                    <td>비밀번호 *</td>
                    <td>
                        <input type="password" name="password" placeholder="비밀번호">
                        <input type="password" name="passwordValidation" placeholder="비밀번호 확인">
                    </td>
                </tr>
                <tr>
                    <td>제목 *</td>
                    <td>
                        <input type="text" name="title">
                    </td>
                </tr>
                <tr>
                    <td>내용 *</td>
                    <td>
                        <textarea name="content"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>파일첨부</td>
                    <td>
                        <div class="filePreview">
                            <p>                               </p>
                        </div>
                        <button>파일 찾기</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button>취소</button>
                        <button type="submit">저장</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>

</body>
</html>
