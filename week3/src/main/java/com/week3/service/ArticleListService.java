package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dto.ArticleListDTO;
import com.week3.support.Validate;
import com.week3.vo.ArticleVO;
import com.week3.vo.CategoryVO;
import com.week3.vo.SearchVO;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * ArticleList 페이지 비즈니스 로직을 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleListService{

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 * CATEGORY_DAO		- 카테고리 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;
	private final CategoryDAO CATEGORY_DAO;

	/**
	 * 유저 검색값 유효성 검증 후, ArticleList 페이지에 필요한 데이터 모음 생성 및 반환
	 * @param searchVO 유저 검색값 객체
	 * @return ArticleListDTO (데이터 모음 객체)
	 */
	public ArticleListDTO articleListService(SearchVO searchVO) {
		SearchVO validatedSearchVO = validateSearchParameters(searchVO);
		return createDTO(validatedSearchVO);
	}

	/**
	 * 필요 데이터 생성 후 ArticleListDTO 객체에 통합
	 * @param validatedSearchVO 유효성 검증한 유저 검색값 객체
	 * @return ArticleListDTO (데이터 모음 객체)
	 */
	private ArticleListDTO createDTO(SearchVO validatedSearchVO) {
		ArticleListDTO articleListDTO = ArticleListDTO.builder()
			.articleList(selectAllArticles(validatedSearchVO))
			.numberOfArticles(countArticles(validatedSearchVO))
			.categoryList(selectCategories())
			.searchVO(validatedSearchVO)
			.build();
		return articleListDTO;
	}

	/**
	 * 검색 대상 게시글 목록 반환
	 * @param validatedSearchVO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 목록
	 */
	private List<ArticleVO> selectAllArticles(SearchVO validatedSearchVO) {
		return ARTICLE_DAO.selectAllArticles(validatedSearchVO);
	}

	/**
	 * 검색 대상 총 게시글 수 반환
	 * @param validatedSearchVO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 수
	 */
	private int countArticles(SearchVO validatedSearchVO) {
		return ARTICLE_DAO.countArticles(validatedSearchVO);
	}

	/**
	 * 검색창 카테고리 옵션 목록 반환
	 * @return 검색창 카테고리 옵션 목록
	 */
	private List<CategoryVO> selectCategories() {
		return CATEGORY_DAO.selectCategories();
	}

	/**
	 * 유저 검색값 유효성 검증하여 반환
	 * @param searchVO 유저 검색값 객체
	 * @return 유효성 검증 후 수정된 검색값 객체
	 */
	private SearchVO validateSearchParameters(SearchVO searchVO) {
		validateEmptyPageNumber(searchVO);
		searchVO.setFirstArticleIndex(searchVO.getPageNumber());
		validateEmptySearchValues(searchVO);
		return searchVO;
	}

	/**
	 * 검색 옵션값 (startDate, endDate, Category, keyword) 유효성 검증 및 수정
	 * @param searchVO 유저 검색값 객체
	 */
	private void validateEmptySearchValues(SearchVO searchVO) {
		if (Validate.isStringEmpty(searchVO.getStartDate())) {
			searchVO.setStartDate(null);
		}
		if (Validate.isStringEmpty(searchVO.getEndDate())) {
			searchVO.setEndDate(null);
		}
		if (Validate.isStringEmpty(searchVO.getCategory())) {
			searchVO.setCategory(null);
		}
		if (Validate.isStringEmpty(searchVO.getKeyword())) {
			searchVO.setKeyword(null);
		}
	}

	/**
	 * 검색 페이지 번호 유효성 검증 및 수정
	 * @param searchVO 유저 검색값 객체
	 */
	private void validateEmptyPageNumber(SearchVO searchVO) {
		int pageNumber = 1;
		Integer searchedPageNumber = searchVO.getPageNumber();
		if (!Objects.equals(searchedPageNumber, null)) {
			pageNumber = searchedPageNumber;
		}
		searchVO.setPageNumber(pageNumber);
	}
}

