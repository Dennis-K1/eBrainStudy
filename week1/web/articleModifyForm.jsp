<%@ page import="classes.ArticleDAO" %>
<%@ page import="classes.ArticleCategoryVO" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.ArticleVO" %><%--
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

<%
  int articleId = (Integer)session.getAttribute("articleId");
  session.removeAttribute("articleId");

  ArticleVO article = articleDAO.getArticle(articleId);
  String category = article.getArticleCategoryName();
  String writer = article.getWriter();
  String actualPassword = article.getPassword();
  String title = article.getTitle();
  String content = article.getContent();
  String dateCreated = article.getDateCreated();
  String lastUpdated = article.getLastUpdated();
  int views = article.getViews();

%>

<html>
<head>
  <title>Title</title>

  <!-- CSS only -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
  <!-- JavaScript Bundle with Popper -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
  <link rel="stylesheet" type="text/css" href="templates/css/articleUpload.css">
</head>
<body>
<div class="container-md mt-5 border border-dark ms-0">
  <form name="article" method="post" action="articleModifyProcess.jsp" onsubmit="return validateForm();">
    <table class="w-100 mt-3">
      <tr class="rowHeight border-bottom border-secondary border-top">
        <td class="bg-secondary bg-opacity-50">
          <span>카테고리</span>
        </td>
        <td class="p-2">
          <span><%=category%></span>
        </td>
      </tr>
      <tr class="rowHeight border-bottom border-secondary border-top">
        <td class="bg-secondary bg-opacity-50">
          <span>등록 일시</span>
        </td>
        <td class="p-2">
          <span><%=dateCreated%></span>
        </td>
      </tr>
      <tr class="rowHeight border-bottom border-secondary border-top">
        <td class="bg-secondary bg-opacity-50">
          <span>수정 일시</span>
        </td>
        <td class="p-2">
          <span><%=lastUpdated%></span>
        </td>
      </tr>
      <tr class="rowHeight border-bottom border-secondary border-top">
        <td class="bg-secondary bg-opacity-50">
          <span>조회수</span>
        </td>
        <td class="p-2">
          <span><%=views%></span>
        </td>
      </tr>
      <tr class="rowHeight p-1 border-bottom border-secondary">
        <td class="bg-secondary bg-opacity-50">
          <span>작성자</span>
          <span class="text-danger">*</span>
        </td>
        <td class="p-2">
          <input type="text" name="writer" autocomplete="username" class="w-25" value="<%=writer%>">
        </td>
      </tr>
      <tr class="rowHeight p-1 border-bottom border-secondary">
        <td class="bg-secondary bg-opacity-50">
          <span>비밀번호</span>
          <span class="text-danger">*</span>
        </td>
        <td class="p-2">
          <input type="password" name="password" autocomplete="new-password" placeholder="비밀번호" class="w-25">
        </td>
      </tr>
      <tr class="rowHeight p-1 border-bottom border-secondary">
        <td class="bg-secondary bg-opacity-50">
          <span>제목</span>
          <span class="text-danger">*</span>
        </td>
        <td class="p-2">
          <input type="text" name="title" class="w-100" value="<%=title%>">
        </td>
      </tr>
      <tr class="rowHeight p-1 border-bottom border-secondary">
        <td class="bg-secondary bg-opacity-50">
          <span>내용</span>
        </td>
        <td class="p-2">
          <textarea name="content" class="w-100"><%=content%></textarea>
        </td>
      </tr>
      <tr class="rowHeight p-1 border-bottom border-secondary">
        <td class="bg-secondary bg-opacity-50">
          <span>파일첨부</span>
        </td>
        <td class="p-2">
          <div class="filePreview">
            <p>                               </p>
          </div>
          <button>파일 찾기</button>
        </td>
      </tr>
    </table>
    <div class="mt-5">
      <td>
        <button>취소</button>
        <button type="submit">저장</button>
      </td>
    </div>
    <input type="hidden" name="articleId" value="<%=articleId%>">
  </form>
</div>

</body>
<script>

  const validateForm = () => {

    let article = document.forms['article'];
    let writer = article['writer'];
    let password = article['password'];
    let passwordValidation = '<%=actualPassword%>';
    let title = article['title'];
    let content = article['content'];

    console.log(passwordValidation);

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
    } else if (!password.value === passwordValidation.value) {
      alert("잘못된 비밀번호입니다.")
      password.focus();
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
