package com.week2.command;

import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import com.week2.model.SearchVO;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SelectArticlesCommand implements Command{

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() throws ServletException, IOException {

		String viewName = "";
		ArticleDAO articleDAO = new ArticleDAO();

		HashMap<String,String> searchParameters = new HashMap<>();
		SearchVO searchVO = SearchVO.builder()
			.searchParameters(searchParameters)
			.build();
		List<ArticleVO> articleList = articleDAO.selectArticles(searchVO);

		request.setAttribute("articleList", articleList);
		viewName = "articleList.jsp";
		return viewName;
	}
}
