<%@ page import="java.util.List" %>
<%@ page import="com.week3.vo.ArticleVO" %>
<%@ page import="com.week3.vo.CategoryVO" %>
<%@ page import="com.week3.vo.SearchVO" %>
<%@ page import="com.week3.dto.ArticleListDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArticleListDTO articleListDTO = (ArticleListDTO)request.getAttribute("articleListDTO");
    List<ArticleVO> articleList = articleListDTO.getArticleList();
    List<CategoryVO> categoryList = articleListDTO.getCategoryList();
	int numberOfArticles = articleListDTO.getNumberOfArticles();
	SearchVO searchVO = articleListDTO.getSearchVO();
	int pageNumber = searchVO.getPageNumber();
	int pageSize = searchVO.getPageSize();
	int firstArticleIndex = searchVO.getFirstArticleIndex();
	String startDate = searchVO.getStartDate();
	String endDate = searchVO.getEndDate();
	String category = searchVO.getCategory();
	String keyword = searchVO.getKeyword();
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
        <form action="articleList" method="get" class="w-100 border border-dark p-3 h-auto">
            등록일
            <input type="date" name="startDate" style="width:10%" id="startDate" onchange="dateLimit(this.id);" value="<%=startDate%>">
            ~
            <input type="date" name="endDate" style="width:10%" id="endDate" onchange="dateLimit(this.id);" value="<%=endDate%>">
            <select  name="category" style="width:10%" class="ms-3">
                <option value="%">전체 카테고리</option>
                <% for (CategoryVO articleCategory : categoryList) {%>
                <option value="<%=articleCategory.getId()%>"><%=articleCategory.getName()%></option>
                <%}%>
            </select>
            <input type="text" name="keyword" style="width:35%" placeholder="검색어를 입력해 주세요. (제목 + 작성자 + 내용)">
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
                <% for (ArticleVO article : articleList) {%>
                <tr class="border-bottom border-secondary" style="height:40px">
                    <td><%=article.getCategoryVO().getName()%></td>
                    <td><a class="text-decoration-underline" href="/articleDetail?articleId=<%=article.getId()%>"><%=article.getTitle()%></a></td>
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
                                // currentpage = pageSize * (pageNumber - 1)
                                if(numberOfArticles > 0){
                                    // 총 페이지의 수
                                    int pageCount = numberOfArticles / pageSize + (numberOfArticles%pageSize == 0 ? 0 : 1);
                                    // 한 페이지에 보여줄 페이지 블럭(링크) 수
                                    int pageBlock = 10;
                                    // 한 페이지에 보여줄 시작 및 끝 번호(예 : 1, 2, 3 ~ 10 / 11, 12, 13 ~ 20)
                                    int startPage = ((firstArticleIndex/pageBlock)/pageBlock)*pageBlock+1;
                                    int endPage = startPage + pageBlock - 1;
                                    if(endPage > pageCount){
                                        endPage = pageCount;
                                    }
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=1%>);"><<</a>
                            <%
                                if(pageNumber > 1) {
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=pageNumber - 1%>);"><</a>
                            <%
                            } else {
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=1%>);"><</a>
                            <%
                                }
                            %>
                            <%
                                for(int i=startPage; i <= endPage; i++){
                                    if(i == pageNumber){ // 현재 페이지에 색 표시
                            %>
                            <a style="color: red"><%=i %></a>
                            <%
                            } else { // 현재 페이지가 아닌 경우 링크 설정
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=i%>);"><%=i%></a>
                            <%
                                    }
                                } // for end
                            %>
                            <%
                                if(pageNumber != pageCount) {
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=pageNumber + 1%>);">></a>
                            <%
                            } else {
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=pageCount%>);">></a>
                            <%
                                }
                            %>
                            <a href="#" onclick="pagination('/articleList',<%=pageCount%>);">>></a>
                            <%
                                } // last
                            %>
                        </td>
                    </tr>
                </div>
            </table>
        </div>
        <div class="articleListFooter">
            <button onclick="location.href = '/articleInput';">등록</button>
        </div>
    </div>
</div>

<!--목록-->
</body>

<script>

  let startDate = '<%=startDate%>'
  let endDate = '<%=endDate%>'
  let category = '<%=category%>';
  let keyword = '<%=keyword%>';
  //달력 날짜 제한
  const dateLimit = (dateId) => {
    let startDate = document.querySelector('#startDate');
    let endDate = document.querySelector('#endDate');

    if (dateId == 'startDate') {
      endDate.min = startDate.value;
    } else if (dateId == 'endDate') {
      startDate.max = endDate.value;
    }
  }

  //카테고리의 옵션값과 세션의 옵션값이 일치하면 selected 추가

  let categoryOptions = document.querySelectorAll('[name=category]')[0];

  for (let i=0; i<categoryOptions.length; i++) {
    let value = categoryOptions.options[i].value;
    if (value == category) {
      categoryOptions.options[i].setAttribute('selected',true)
    }
  }


  //검색 input에 null 값일 시 pass 아니면 세션값 표시

  let keywordInputBox = document.querySelectorAll('[name=keyword]')[0];

  if (keyword != 'null' && keyword != "") {
    keywordInputBox.setAttribute('value',keyword);
  }

  //페이징 함수
  const pagination = (url, pageNumber) => {
    let form = document.createElement("form");
    let parm = new Array();
    let input = new Array();

    form.action = url;
    form.method = "get";

    parm.push( ['pageNumber', pageNumber]);

    if (startDate !== 'null') {
      parm.push( ['startDate', '<%=startDate%>'] );
    }

    if (endDate !== 'null') {
        parm.push( ['endDate', '<%=request.getParameter("endDate")%>'] );
    }
    if (category !== 'null') {
        parm.push( ['category', '<%=request.getParameter("category")%>'] );
    }
    if (keyword !== 'null') {
        parm.push( ['keyword', '<%=request.getParameter("keyword")%>'] );
    }

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
  const articleDetail = (url, articleId, pageNumber) => {
    let form = document.createElement("form");
    let parm = new Array();
    let input = new Array();
    form.action = url + "?articleId=" + articleId;
    form.method = "get";

    parm.push( ['pageNumber', pageNumber] );

    parm.push( ['startDate', '<%=request.getAttribute("startDate")%>'] );
    parm.push( ['endDate', '<%=request.getAttribute("endDate")%>'] );
    parm.push( ['category', '<%=request.getAttribute("category")%>'] );
    parm.push( ['keyword', '<%=request.getAttribute("keyword")%>'] );

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
