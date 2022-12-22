package com.week2.command;

import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() throws ServletException, IOException {
		ArticleDAO articleDAO = new ArticleDAO();
		request.setCharacterEncoding("utf-8");

		int categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		String password = request.getParameter("password");

		ArticleVO article = ArticleVO.builder()
			.categoryId(categoryId)
			.title(title)
			.content(content)
			.writer(writer)
			.password(password)
			.build();

		int result = articleDAO.insertArticle(article);

		if (result == 1){
			return "articleInserted.jsp";
		} else {
			//request set attribute
			return "articleInputForm.jsp";
		}
	}

	public void validation (int categoryId) {}
}
