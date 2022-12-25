package com.week2.command;

import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import com.week2.model.CommentDAO;
import com.week2.model.CommentVO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

public class SelectArticleCommand implements Command {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArticleDAO articleDAO = new ArticleDAO();
		CommentDAO commentDAO = new CommentDAO();

		int articleId = Integer.parseInt(request.getParameter("articleId"));
		ArticleVO article = articleDAO.selectArticle(articleId);
		List<CommentVO> commentList = commentDAO.selectComments(articleId);

		request.setAttribute("article", article);
		request.setAttribute("commentList", commentList);

		return "articleDetail.jsp";
	}
}
