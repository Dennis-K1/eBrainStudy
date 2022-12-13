<%--
  Created by IntelliJ IDEA.
  User: bw120
  Date: 2022-12-12
  Time: 오후 6:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div class="articleUploadForm">
        <table>
            <tr>
                <td>카테고리</td>
                <td>
                    <select name="articleCategory">
                        <option value="none">카테고리 선택</option>
                        <option value="java">JAVA</option>
                        <option value="javascript">javascript</option>
                        <option value="database">Database</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>작성자</td>
                <td>
                    <input type="text" name="writer">
                </td>
            </tr>
            <tr>
                <td>비밀번호</td>
                <td>
                    <input type="password" name="password" placeholder="비밀번호">
                    <input type="password" name="passwordValidation" placeholder="비밀번호 확인">
                </td>
            </tr>
            <tr>
                <td>제목</td>
                <td>
                    <input type="text" name="title">
                </td>
            </tr>
            <tr>
                <td>내용</td>
                <td>
                    <input type="text" name="content">
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
        </table>
    </div>
    <button>취소</button>
    <button>찾기</button>

</body>
</html>
