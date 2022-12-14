<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="classes.ArticleDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="classes.ArticleVO" %>
<%@ page import="classes.ArticleCategoryVO" %>
<%@ page import="java.util.HashMap" %>

<%! ArticleDAO articleDAO = new ArticleDAO();%>
<% request.setCharacterEncoding("utf-8") ;%>
<%
    // 카테고리 값 호출
    List<ArticleCategoryVO> articleCategories = articleDAO.getArticleCategories();
%>

<%
    // 검색 조건 값 호출
    String fromDate = request.getParameter("fromDate");
    String toDate = request.getParameter("toDate");
    String category = request.getParameter("category");
    String query = request.getParameter("query");
%>
<%
    HashMap<String,String> queryStrings = new HashMap<>();
    if (fromDate != null){
        request.setAttribute("fromDate",fromDate);
        queryStrings.put("fromDate",fromDate);
    }
    if (toDate != null){
        request.setAttribute("toDate",toDate);
        queryStrings.put("toDate",toDate);
    }
    if (category != null){
        request.setAttribute("category",category);
        queryStrings.put("category",category);
    }
    if (query != null){
        request.setAttribute("query",query);
        if (query.equals("")){
            queryStrings.put("query","%");
        } else {
            queryStrings.put("query", "%" + query + "%");
        }
    }
    //request값이 null이라면 (초기 페이지라면) 디폴트 리스트 반환
    //아니라면 값에 맞추어 표시

%>
<%
    int pageSize = 10; // 한 페이지에 출력할 레코드 수
    int pageNum;
    // 페이지 링크를 클릭한 번호 / 현재 페이지
    String pageNumParam = request.getParameter("pageNum");
    if (pageNumParam == null || pageNumParam.equals("null")) {
        pageNum = 1;
    } else {
        pageNum = Integer.parseInt(pageNumParam);
    }

    // 연산을 하기 위한 pageNum 형변환 / 현재 페이지
    int currentPage = pageSize * (pageNum - 1);

    List<ArticleVO> articles;
    int numberOfArticles;

    // 처음 페이지에 들어와서 (==null), 페이지 이동할시 (equals("null") 자바스크립트 post값)
    if (fromDate == null || fromDate.equals("null")){
        articles = articleDAO.getArticles(pageSize,currentPage);
        numberOfArticles = articleDAO.getNumberOfArticles();
    } else {
        articles = articleDAO.getArticles(pageSize,currentPage,queryStrings);
        numberOfArticles = articleDAO.getNumberOfArticles(queryStrings);
    }
%>



<html>
<head>
    <meta charset="utf-8">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
    <title>Title</title>
</head>
<style>
    a { text-decoration: none; color: black; }
    a:visited { text-decoration: none; }
    a:hover { text-decoration: none; }
    a:focus { text-decoration: none; }
    a:hover, a:active { text-decoration: none; }
</style>
<body>
<div class="container-md mt-5 border border-dark ms-5">
    <div class="searchBar mt-3">
        <!--검색바-->
        <form action="articleList.jsp" method="post" class="w-100 border border-dark p-3 h-auto">
            등록일
            <input type="date" name="fromDate" style="width:10%" id="fromDate" onchange="dateLimit(this.id);" value="<%=request.getAttribute("fromDate")%>">
            ~
            <input type="date" name="toDate" style="width:10%" id="toDate" onchange="dateLimit(this.id);" value="<%=request.getAttribute("toDate")%>">
            <select  name="category" style="width:10%" class="ms-3">
                <option value="%">전체 카테고리</option>
                <% for (ArticleCategoryVO articleCategory : articleCategories) {%>
                <option value="<%=articleCategory.getName()%>"><%=articleCategory.getName()%></option>
                <%}%>
            </select>
            <input type="text" name="query" style="width:35%" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)">
            <input type="submit" value="검색">
        </form>
    </div>
    <div class="articleList">
        <div class="articleListHeader">

            <span class="fs-6">총<%=numberOfArticles%>건</span>
        </div>
        <div class="articleListMain">

            <table class="w-100 mt-3 text-center">
                <tr class="border-bottom border-secondary border-top" style="height:40px">
                    <td>카테고리</td>
                    <td>제목</td>
                    <td>작성자</td>
                    <td>조회수</td>
                    <td>등록 일시</td>
                    <td>수정 일시</td>
                </tr>
                <% for (ArticleVO article : articles) {%>
                <tr class="border-bottom border-secondary" style="height:40px">
                    <td><%=article.getArticleCategoryName()%></td>
                    <td><a class="text-decoration-underline" href="#" onclick="articleDetail('/articleDetail.jsp',<%=article.getId()%>,<%=pageNum%>);"><%=article.getTitle()%></a></td>
                    <td><%=article.getWriter()%></td>
                    <td><%=article.getViews()%></td>
                    <td><%=article.getDateCreated()%></td>
                    <td><%=article.getLastUpdated()%></td>
                </tr>
                <% }%>
            <div class="articleListPagination">
                <tr>
                    <td colspan="6" align="center">
                        <%	// 페이징  처리
                            // currentpage = pageSize * (pageNum - 1)
                            if(numberOfArticles > 0){
                                // 총 페이지의 수
                                int pageCount = numberOfArticles / pageSize + (numberOfArticles%pageSize == 0 ? 0 : 1);
                                // 한 페이지에 보여줄 페이지 블럭(링크) 수
                                int pageBlock = 10;
                                // 한 페이지에 보여줄 시작 및 끝 번호(예 : 1, 2, 3 ~ 10 / 11, 12, 13 ~ 20)
                                int startPage = ((currentPage/pageBlock)/pageBlock)*pageBlock+1;
                                int endPage = startPage + pageBlock - 1;
                                if(endPage > pageCount){
                                    endPage = pageCount;
                                }
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=1%>);"><<</a>
                        <%
                           if(pageNum > 1) {
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=pageNum - 1%>);"><</a>
                        <%
                            } else {
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=1%>);"><</a>
                        <%
                            }
                        %>
                        <%
                            for(int i=startPage; i <= endPage; i++){
                                if(i == pageNum){ // 현재 페이지에 색 표시
                        %>
                        <a style="color: red"><%=i %></a>
                        <%
                        } else { // 현재 페이지가 아닌 경우 링크 설정
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=i%>);"><%=i%></a>
                        <%
                                }
                            } // for end
                        %>
                        <%
                            if(pageNum != pageCount) {
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=pageNum + 1%>);">></a>
                        <%
                        } else {
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=pageCount%>);">></a>
                        <%
                            }
                        %>
                        <a href="#" onclick="pagination('/articleList.jsp',<%=pageCount%>);">>></a>
                        <%
                            } // last
                        %>
                    </td>
                </tr>
            </div>
            </table>
        </div>
        <div class="articleListFooter">
            <button onclick="location.href = '/articleUploadForm.jsp';">등록</button>
        </div>
    </div>
</div>

<!--목록-->
</body>

<script>
    //달력 날짜 제한
    const dateLimit = (dateId) => {
        let fromDate = document.querySelector('#fromDate');
        let toDate = document.querySelector('#toDate');

        if (dateId == 'fromDate') {
            toDate.min = fromDate.value;
        } else if (dateId == 'toDate') {
            fromDate.max = toDate.value;
        }
    }

    //카테고리의 옵션값과 세션의 옵션값이 일치하면 selected 추가
    let category = '<%=request.getAttribute("category")%>';
    let categoryOptions = document.querySelectorAll('[name=category]')[0];

    for (let i=0; i<categoryOptions.length; i++) {
        let value = categoryOptions.options[i].value;
        if (value == category) {
            categoryOptions.options[i].setAttribute('selected',true)
        }
    }


    //검색 input에 null 값일 시 pass 아니면 세션값 표시
    let requestQuery = '<%=request.getAttribute("query")%>';
    let queryInputBox = document.querySelectorAll('[name=query]')[0];

    if (requestQuery != 'null') {
        queryInputBox.setAttribute('value',requestQuery);
    }

    //페이징 함수
    const pagination = (url, pageNum) => {
        let form = document.createElement("form");
        let parm = new Array();
        let input = new Array();

        form.action = url;
        form.method = "post";

        parm.push( ['pageNum', pageNum]);

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

    //게시물 보기 함수
    const articleDetail = (url, articleId, pageNum) => {
        let form = document.createElement("form");
        let parm = new Array();
        let input = new Array();
        form.action = url + "?articleId=" + articleId;
        form.method = "post";

        parm.push( ['pageNum', pageNum] );

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
