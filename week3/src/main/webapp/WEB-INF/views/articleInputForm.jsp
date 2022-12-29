<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.List" %>
<%@ page import="com.week3.vo.CategoryVO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-22
  Time: 오후 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<CategoryVO> categoryList = (List<CategoryVO>) request.getAttribute("categoryList");
%>
<html>
<head>
    <title>Title</title>

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="css/articleUpload.css">
</head>
<body>
<div class="container-md mt-5 border border-dark ms-0">
    <form name="articleUpdateDTO" method="post"  enctype="multipart/form-data" action="articleInsert" onsubmit="return validateForm();">
        <table class="w-100 mt-3">
            <from id="articleVO" name="articleVO">
                <tr class="rowHeight border-bottom border-secondary border-top">
                    <td class="bg-secondary bg-opacity-50">
                        <span>카테고리</span>
                        <span class="text-danger">*</span>
                    </td>
                    <td class="p-2">
                        <select name="categoryId" class="w-25">
                            <option value="none">카테고리 선택</option>
                            <% for (CategoryVO category : categoryList) {%>
                            <option value="<%=category.getId()%>"><%=category.getName()%></option>
                            <%}%>
                        </select>
                    </td>
                </tr>
                <tr class="rowHeight p-1 border-bottom border-secondary">
                    <td class="bg-secondary bg-opacity-50">
                        <span>작성자</span>
                        <span class="text-danger">*</span>
                    </td>
                    <td class="p-2">
                        <input type="text" name="writer" autocomplete="username" class="w-25">
                    </td>
                </tr>
                <tr class="rowHeight p-1 border-bottom border-secondary">
                    <td class="bg-secondary bg-opacity-50">
                        <span>비밀번호</span>
                        <span class="text-danger">*</span>
                    </td>
                    <td class="p-2">
                        <input type="password" name="password" autocomplete="new-password" placeholder="비밀번호" class="w-25">
                        <input type="password" name="passwordValidation" autocomplete="new-password" placeholder="비밀번호 확인" class="w-25">
                    </td>
                </tr>
                <tr class="rowHeight p-1 border-bottom border-secondary">
                    <td class="bg-secondary bg-opacity-50">
                        <span>제목</span>
                        <span class="text-danger">*</span>
                    </td>
                    <td class="p-2">
                        <input type="text" name="title" class="w-100">
                    </td>
                </tr>
                <tr class="rowHeight p-1 border-bottom border-secondary">
                    <td class="bg-secondary bg-opacity-50">
                        <span>내용</span>
                        <span class="text-danger">*</span>
                    </td>
                    <td class="p-2">
                        <textarea name="content" class="w-100"></textarea>
                    </td>
                </tr>
            </from>
            <form id="fileList" name="fileList">
                <tr class="rowHeight p-1 border-bottom border-secondary">
                    <td class="bg-secondary bg-opacity-50">
                        <input type="file" name="fileVO">
                    </td>
                    <td class="p-2">
                        <div class="filePreview">
                            <p>                               </p>
                        </div>
                    </td>
                </tr>
            </form>
        </table>
        <div class="mt-5">
            <td>
                <button>취소</button>
                <button type="submit">저장</button>
            </td>
        </div>
    </form>
</div>

</body>
<script>

  const validateForm = () => {

    let article = document.forms['article'];
    let category = article['category'];
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
