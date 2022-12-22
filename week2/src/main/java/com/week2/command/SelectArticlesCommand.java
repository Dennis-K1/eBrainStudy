package com.week2.command;

import com.week2.model.ArticleDAO;
import com.week2.model.ArticleVO;
import com.week2.model.CategoryDAO;
import com.week2.model.CategoryVO;
import com.week2.model.SearchVO;
import java.awt.PrintGraphics;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SelectArticlesCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;

	public String execute() throws ServletException, IOException {

		ArticleDAO articleDAO = new ArticleDAO();
		CategoryDAO categoryDAO = new CategoryDAO();

		int pageNumber = checkPageNumber(request);
		SearchVO searchVO = checkSearchValues(request, pageNumber);

		List<ArticleVO> articleList = articleDAO.selectArticles(searchVO);
		List<CategoryVO> categoryList = categoryDAO.selectCategories();

		int numberOfArticles = articleDAO.countArticles(searchVO);

		request.setAttribute("articleList", articleList);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("numberOfArticles", numberOfArticles);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("searchVO", searchVO);

		return "articleList.jsp";
	}

	public SearchVO checkSearchValues(HttpServletRequest request, int pageNumber) {
		SearchVO searchVO = new SearchVO();
		HashMap<String, String> searchParameters = new HashMap<>();
		searchVO.setFirstArticleIndex(pageNumber);
		if (request.getParameter("startDate") != null && !Objects.equals(request.getParameter("startDate"),"")) {
			searchParameters.put("startDate", request.getParameter("startDate"));
		}
		if (request.getParameter("endDate") != null && !Objects.equals(request.getParameter("endDate"),"")) {
			searchParameters.put("endDate", request.getParameter("endDate"));
		}
		if (request.getParameter("category") != null && !Objects.equals(request.getParameter("category"),"")) {
			searchParameters.put("category", request.getParameter("category"));
		}
		if (request.getParameter("keyword") != null && !Objects.equals(request.getParameter("keyword"),"")) {
			searchParameters.put("keyword", request.getParameter("keyword"));
		}
		searchVO.setSearchParameters(searchParameters);
		return searchVO;
	}

	public int checkPageNumber(HttpServletRequest request) {
		int pageNumber = 1;
		if (!Objects.equals(request.getParameter("pageNumber"), null)) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		return pageNumber;
	}
}
