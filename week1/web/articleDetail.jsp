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
    <title>Title</title>
</head>
<body>
    <div class="articleDetail">
        <div class="articleHeader">
            <div class="writer">
                <%=article.getWriter()%>
            </div>
            <div class="dateCreated">
                등록일시 <%=article.getDateCreated()%>
            </div>
            <div class="lastUpdated">
                수정일시 <%=article.getLastUpdated()%>
            </div>
            <div class="views">
                조회수: <%=article.getViews()%>
            </div>
        </div>
        <div class="content">
            <p><%=article.getContent()%></p>
        </div>
        <div class="fileAttached">
            첨부파일
        </div>
        <div class="comments">
            <div class="comment">
                <div class="commentDate">
                    2022.05.14 16:32
                </div>
                <div class="commentContent">
                    blah blah
                </div>
            </div>
        </div>
        <div class="articleFooter">
            <button onclick="backToList();">목록</button>
            <input type="submit" value="수정">
            <input type="submit" value="삭제">
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
