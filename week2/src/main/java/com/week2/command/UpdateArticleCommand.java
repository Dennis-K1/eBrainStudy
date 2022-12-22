package com.week2.command;

import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;
	public String execute() throws ServletException, IOException {
		ArticleDAO articleDAO = new ArticleDAO();
		request.setCharacterEncoding("utf-8");

		//password validation
		String password = request.getParameter("password");

		int articleId = Integer.parseInt(request.getParameter("articleId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");


		ArticleVO article = ArticleVO.builder()
			.id(articleId)
			.title(title)
			.content(content)
			.writer(writer)
			.build();

		int result = articleDAO.updateArticle(article);

		/*
		게시글 수정후 게시글 화면 유지를 위한 게시글 번호 전달
		 */
		request.setAttribute("articleId",articleId);
		if (result == 1){
			return "articleUpdated.jsp";
		} else {
			//request set attribute
			return "articleEditForm.jsp";
		}
	}
}
