<%@ page import="classes.ArticleDAO" %>
<%@ page import="classes.ArticleVO" %>
<%@ page import="classes.ArticleCommentDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.ArticleCommentVO" %>
<%@ page import="classes.ArticleCommentDAO" %><%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("utf-8") ;%>
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

<%
    //댓글 객체
    ArticleCommentDAO articleCommentDAO = new ArticleCommentDAO();
    List<ArticleCommentVO> comments = articleCommentDAO.getComments(article.getId());

%>

<%
    // 뒤로가기 시 검색값 유지를 위한 세팅

    //  commentUploadProcess에서 전달한 세션값이 하나라도 있을 경우 request에 저장
    if (session.getAttribute("fromDate") != null ||
        session.getAttribute("toDate") != null ||
        session.getAttribute("categoruy") != null ||
        session.getAttribute("query") != null ||
        session.getAttribute("pageNum") != null
    )
    {
        request.setAttribute("fromDate",session.getAttribute("fromDate"));
        request.setAttribute("toDate",session.getAttribute("toDate"));
        request.setAttribute("category",session.getAttribute("category"));
        request.setAttribute("query",session.getAttribute("query"));
        request.setAttribute("pageNum",session.getAttribute("pageNum"));

        session.removeAttribute("fromDate");
        session.removeAttribute("toDate");
        session.removeAttribute("category");
        session.removeAttribute("query");
        session.removeAttribute("pageNum");
    }


    if (request.getAttribute("fromDate") != null ||
            request.getAttribute("toDate") != null ||
            request.getAttribute("categoruy") != null ||
            request.getAttribute("query") != null ||
            request.getAttribute("pageNum") != null)
    {
        request.setAttribute("fromDate",request.getAttribute("fromDate"));
        request.setAttribute("toDate",request.getAttribute("toDate"));
        request.setAttribute("category",request.getAttribute("category"));
        request.setAttribute("query",request.getAttribute("query"));
        request.setAttribute("pageNum",request.getAttribute("pageNum"));
    } else {
        request.setAttribute("fromDate", request.getParameter("fromDate"));
        request.setAttribute("toDate", request.getParameter("toDate"));
        request.setAttribute("category", request.getParameter("category"));
        request.setAttribute("query", request.getParameter("query"));
        request.setAttribute("pageNum", request.getParameter("pageNum"));
    }
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
            <%
                for (int commentOrder = 0; commentOrder < comments.size(); commentOrder++) {
                    ArticleCommentVO comment = comments.get(commentOrder);
            %>
            <div class="comment p-2">
                <div><%=comment.getDateCreated()%></div>
                <div><%=comment.getContent()%></div>
            </div>
            <%
                }
            %>
            <div class="commentUpload mt-2 ms-2 ">
                <form name="comment" method="post" action="commentUploadAProcess.jsp" onsubmit="return validateComment()">
                    <textarea name="content"></textarea>
                    <input type="hidden" name="articleId" value="<%=article.getId()%>">
                    <input type="hidden" name="fromDate" value="<%=request.getAttribute("fromDate")%>">
                    <input type="hidden" name="toDate" value="<%=request.getAttribute("toDate")%>">
                    <input type="hidden" name="category" value="<%=request.getAttribute("category")%>">
                    <input type="hidden" name="query" value="<%=request.getAttribute("query")%>">
                    <input type="hidden" name="pageNum" value="<%=request.getAttribute("pageNum")%>">
                    <button type="submit" class="commentButton">등록</button>
                </form>
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
        let form = document.createElement("form");
        let parm = new Array();
        let input = new Array();

        form.action = 'articleList.jsp';
        form.method = "post";

        parm.push( ['pageNum',<%=request.getAttribute("pageNum")%>]);
        parm.push( ['fromDate', '<%=request.getAttribute("fromDate")%>'] );
        parm.push( ['toDate', '<%=request.getAttribute("toDate")%>'] );
        parm.push( ['category', '<%=request.getAttribute("category")%>'] );
        parm.push( ['query', '<%=request.getAttribute("query")%>'] );


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
