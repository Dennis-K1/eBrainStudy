<%@ page import="classes.ArticleDAO" %>
<%@ page import="classes.ArticleVO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%! ArticleDAO articleDAO = new ArticleDAO();%>
<%--
1. url에 있는 파라미터로 게시글 번호 식별하여 게시글 표시
   - getArticle(int articleId) 메소드
   - articleId 로 게시글 query
     -- view + 1로 저장
     -- DB에 view 업데이트
2. 뒤로가기 버튼 클릭시 attribute 재 지정하여 post반환
--%>

<%  int articleId = Integer.parseInt(request.getParameter("articleId"));
    ArticleVO article = articleDAO.getArticle(articleId);
%>



<html>
<head>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="templates/css/articleDetail.css">
    <title>Title</title>
</head>
<body>
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
                <p class="ms-1 text-break">[<%=article.getArticleCategoryName()%>]</p>
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
            <div class="comment p-2">
                <div>2022.05.14 16:32</div>
                <div>blah blah</div>
            </div>
            <div class="comment p-2">
                <div>2022.05.14 16:32</div>
                <div>blah blah</div>
            </div>
            <div class="comment p-2">
                <div>2022.05.14 16:32</div>
                <div>blah blah</div>
            </div>
            <div class="commentUpload mt-2 ms-2 ">
                <textarea></textarea>
                <button type="button" class="commentButton">등록</button>
            </div>
        </div>
        <div class="articleFooter mt-2 ">
            <button onclick="backToList();">목록</button>
            <input type="submit" value="수정">
            <input type="submit" value="삭제">
        </div>
    </div>

</div>


</body>
<script>

    const backToList = () => {
        let form = document.createElement("form");
        let parm = new Array();
        let input = new Array();

        form.action = 'articleList.jsp';
        form.method = "post";

        parm.push( ['pageNum',<%=request.getParameter("pageNum")%>]);
        parm.push( ['fromDate', '<%=request.getParameter("fromDate")%>'] );
        parm.push( ['toDate', '<%=request.getParameter("toDate")%>'] );
        parm.push( ['category', '<%=request.getParameter("category")%>'] );
        parm.push( ['query', '<%=request.getParameter("query")%>'] );

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
