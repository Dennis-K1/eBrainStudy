<%@ page import="java.util.List" %>
<%@ page import="com.week3.vo.ArticleVO" %>
<%@ page import="com.week3.vo.CommentVO" %>
<%@ page import="com.week3.dto.ArticleDetailDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-22
  Time: 오후 10:32
  To change this template use File | Settings | File Templates.
--%>
<%
    /*
    articleDetailDTO    - 본 페이지에 필요한 데이터 모음
    article             - 본 페이지 게시글 객체
    articleId           - 본 페이지 게시글 번호
    commentList         - 본 페이지 게시글 댓글 목록
     */
    ArticleDetailDTO articleDetailDTO = (ArticleDetailDTO) request.getAttribute("articleDetailDTO");
    ArticleVO article = articleDetailDTO.getArticleVO();
    int articleId = article.getId();
    List<CommentVO> commentList = articleDetailDTO.getCommentList();
%>

<html>
<head>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="css/articleDetail.css">
    <title>Title</title>
</head>
<body>

<div class="black-bg">
    <div class="white-bg">
        <div class="m-3">
            <p class="float-start">비밀번호</p>
            <p class="text-danger float-start">*</p>
            <input type="password" id="password" autocomplete="new-password" class="ms-3 w-75 float-start" placeholder=" 비밀번호를 입력해 주세요.">
        </div>
        <div style="clear: both" class="d-flex justify-content-center">
            <button name="modalCancel" class="m-1" onclick="hidePasswordModal()">취소</button>
            <button class="m-1" onclick="validatePassword()">확인</button>
        </div>
    </div>
</div>

<div class="container-fluid p-5">
    <div class="articleDetail w-80 p-5">
        <div class="articleHeader">
            <div class="writer w-60">
                <p class="date w-auto  mt-2"><%=article.getWriter()%></p>
            </div>
            <div class="dateCreated w-20">
                <p class="date mt-2 w-auto">등록일시</p> <p class="date w-auto ms-2 mt-2 text-center"><%=article.getDateCreated()%></p>
            </div>
            <div class="lastUpdated w-20 position-relative">
                <p class="date mt-2 w-auto">수정일시</p> <p class="date w-auto ms-2 mt-2 text-center"><%=article.getLastUpdated()%></p>
            </div>
        </div>
        <div class="articleTitle">
            <div class="category text-wrap">
                <p class="ms-1 text-break">[<%=article.getCategoryVO().getName()%>]</p>
            </div>
            <div class="title text-break">
                <p class="ms-1"><%=article.getTitle()%></p>
            </div>
            <div class="views">
                <p class="ms-1">조회수: <%=article.getViews()%></p>
            </div>
        </div>


        <div class="content">
            <p><%=article.getContent()%></p>
        </div>
        <div class="fileAttached mt-2">
            첨부파일
        </div>
        <div class="comments mt-5 p-1 pb-3">

            <%
                for (int commentOrder = 0; commentOrder < commentList.size(); commentOrder++) {
                    CommentVO comment = commentList.get(commentOrder);
            %>
            <div class="comment p-2">
                <div><%=comment.getDateCreated()%></div>
                <div><%=comment.getContent()%></div>
            </div>
            <%
                }
            %>
            <div class="commentUpload mt-2 ms-2 ">
                <form name="commentVO" method="post" action="commentInsert" onsubmit="return validateComment()">
                    <textarea name="content" placeholder=" 댓글을 입력해 주세요."></textarea>
                    <input type="hidden" name="articleId" value="<%=article.getId()%>">
                    <button type="submit" class="commentButton">등록</button>
                </form>
            </div>
        </div>
        <div class="articleFooter mt-2 ">
            <button onclick="backToList();">목록</button>
            <button id="Edit" onclick="showPasswordModal(this.id)">수정</button>
            <button id="Delete" onclick="showPasswordModal(this.id)">삭제</button>
        </div>
    </div>
</div>




</body>
<script>

  const validateComment = () => {
    let comment = document.forms['comment'];
    let content = comment['content'];

    if (content.value == "" || content.value.length > 500) {
      alert("댓글은 1글자 이상 500글자 미만으로 작성해주세요.")
      comment.focus();
      return false
    }

  }
  const backToList = () => {
    location.href = '/articleList'
  }

  const showPasswordModal = (buttonId) => {
    document.querySelector('.black-bg').classList.add('show-modal')
    document.querySelector('button[name="modalCancel"]').id = buttonId;
  }

  const hidePasswordModal = () => {
    document.querySelector('.black-bg').classList.remove('show-modal')
    document.querySelector('button[name="modalCancel"]').removeAttribute('id');

  }

  const validatePassword = () => {
    let articleId = '<%=articleId%>'
    let password = document.querySelector('#password').value;
    let articlePassword = '<%=article.getPassword()%>';
    let action = document.querySelector('button[name="modalCancel"]').id
    if (password === articlePassword){
      if (action == 'Delete') {
        let path = 'article' + action
        redirect(path, articleId, password);
      } else if (action == 'Edit') {
        let path = 'article' + action
        redirect(path, articleId, password);
      }
    } else {
      alert('비밀번호가 다릅니다.')
    }
  }

  const redirect = (path, articleId, password) => {
    let form = document.createElement("form");
    let parm = new Array();
    let input = new Array();
    form.action = path;
    form.method = "post";

    parm.push( ['articleId', articleId] );
    parm.push( ['password', password] );

    for (let i = 0; i < parm.length; i++) {
      input[i] = document.createElement("input");
      input[i].setAttribute("type", "hidden");
      input[i].setAttribute('name', parm[i][0]);
      input[i].setAttribute("value", parm[i][1]);
      form.appendChild(input[i]);
    }
    document.body.appendChild(form);
    form.submit();
  }
</script>
</html>
