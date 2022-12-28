package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dto.ArticleListDTO;
import com.week3.vo.SearchVO;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleListService{

	private final ArticleDAO articleDAO;
	private final CategoryDAO categoryDAO;


	public ArticleListDTO selectAllArticles(SearchVO searchVO) {
		searchVO = checkSearchParameters(searchVO);
		System.out.println(searchVO.toString());
		ArticleListDTO articleListDTO = ArticleListDTO.builder()
			.articleList(articleDAO.selectArticles(searchVO))
			.numberOfArticles(articleDAO.countArticles(searchVO))
			.categoryList( categoryDAO.selectCategories())
			.searchVO(searchVO)
			.build();

		return articleListDTO;
	}

	private SearchVO checkSearchParameters(SearchVO searchVO) {
		int pageNumber = checkPageNumber(searchVO);

		searchVO.setFirstArticleIndex(pageNumber);

		String startDate = searchVO.getStartDate();
		String endDate = searchVO.getEndDate();
		String category = searchVO.getCategory();
		String keyword = searchVO.getKeyword();

		if ("".equals(startDate)) {
			searchVO.setStartDate(null);
		}
		if ("".equals(endDate)) {
			searchVO.setEndDate(null);
		}
		if ("".equals(category)) {
			searchVO.setCategory(null);
		}
		if ("".equals(keyword)) {
			searchVO.setKeyword(null);
		}

		return searchVO;
	}

	private int checkPageNumber(SearchVO searchVO) {
		int pageNumber = 1;
		Integer searchedPageNumber = searchVO.getPageNumber();
		if (!Objects.equals(searchedPageNumber, null)) {
			pageNumber = searchedPageNumber;
		}
		return pageNumber;
	}
}

