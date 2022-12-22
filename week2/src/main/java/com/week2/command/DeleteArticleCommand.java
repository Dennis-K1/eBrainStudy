package com.week2.command;

import com.week2.model.ArticleDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;
	public String execute() throws ServletException, IOException {

		int result = 0;
		ArticleDAO articleDAO = new ArticleDAO();

		//password validation
		String password = request.getParameter("password");

		int articleId = Integer.parseInt(request.getParameter("articleId"));
		result = articleDAO.deleteArticle(articleId);

		if (result == 1){
			return "articleDeleted.jsp";
		} else {
			return "articleNotDeleted.jsp";
		}
	}
}
