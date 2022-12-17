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
        <form name="article" method="post" action="articleUploadProcess.jsp" onsubmit="return validateForm();">
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
                        <input type="text" name="writer" autocomplete="username">
                    </td>
                </tr>
                <tr>
                    <td>비밀번호 *</td>
                    <td>
                        <input type="password" name="password" autocomplete="new-password" placeholder="비밀번호">
                        <input type="password" name="passwordValidation" autocomplete="new-password" placeholder="비밀번호 확인">
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
<script>

    const validateForm = () => {

        let article = document.forms['article'];
        let category = article['articleCategory'];
        let writer = article['writer'];
        let password = article['password'];
        let passwordValidation = article['passwordValidation'];
        let title = article['title'];
        let content = article['content'];

        console.log(article);
        //카테고리
        if (category.value == "none") {
            alert("카테고리를 선택해주세요.")
            category.focus();
            return false;
        }

        //작성자
        if (writer.value == "") {
            alert("작성자를 입력해주세요.")
            writer.focus();
            return false;
        } else if (writer.value.length < 2 || writer.value.length > 4) {
            alert("작성자를 3글자 이상 5글자 미만으로 입력해주세요.")
            writer.focus();
            return false;
        }

        //비밀번호
        if (password.value == "") {
            alert("비밀번호를 입력해주세요.")
            password.focus();
            return false;
        } else if (password.value.length < 4 || password.value.length > 16) {
            alert("비밀번호는 특수문자( '!,@,$,%,^,&,*' 만 허용), 영문 대소문자, 숫자를 포함하여 " +
                "4글자 이상 16글자 미만으로 입력해주세요.")
            password.focus();
            return false;
        } else if (!password.value.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)) {
            alert("비밀번호는 특수문자( '!,@,$,%,^,&,*' 만 허용), 영문 대소문자, 숫자를 포함하여 " +
                    "4글자 이상 16글자 미만으로 입력해주세요.")
            password.focus();
            return false;
        } else if (password.value != passwordValidation.value) {
            alert("비밀번호 확인란에 동일한 비밀번호를 입력해주세요.")
            passwordValidation.focus();
            return false;
        }

        //제목
        if (title.value == "") {
            alert("제목을 입력해주세요.")
            title.focus();
            return false;
        } else if (title.value.length < 4 || title.value.length > 100) {
            alert("제목을 4글자 이상 100글자 미만으로 입력해주세요.")
            title.focus();
            return false;
        }

        //내용
        if (content.value == "") {
            alert("내용을 입력해주세요.")
            content.focus();
            return false;
        } else if (content.value.length < 4 || content.value.length > 2000) {
            alert("내용을 4글자 이상 2000글자 미만으로 입력해주세요.")
            content.focus();
            return false;
        }

    }

</script>
</html>
