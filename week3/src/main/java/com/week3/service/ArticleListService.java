package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dto.ArticleListDTO;
import com.week3.support.Validate;
import com.week3.vo.ArticleVO;
import com.week3.vo.CategoryVO;
import com.week3.dto.SearchDTO;
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
	/*
	final 변수명 UPPER_CASE?
	service / repository 동일 기능 분리?
	DTO 생성 분리?
	참조 변수 선언후 대입 or 메소드 바로 대입
	 */

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 * CATEGORY_DAO		- 카테고리 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;
	private final CategoryDAO CATEGORY_DAO;

	/**
	 * 유저 검색값 유효성 검증 후, ArticleList 페이지에 필요한 데이터 모음 생성 및 반환
	 * @param searchDTO 유저 검색값 객체
	 * @return ArticleListDTO (데이터 모음 객체)
	 */
	public ArticleListDTO service(SearchDTO searchDTO) {
		SearchDTO validatedSearchDTO = validateSearchParameters(searchDTO);
		return createDTO(validatedSearchDTO);
	}

	/**
	 * 필요 데이터 생성 후 ArticleListDTO 객체에 통합
	 * @param validatedSearchDTO 유효성 검증한 유저 검색값 객체
	 * @return ArticleListDTO (데이터 모음 객체)
	 */
	private ArticleListDTO createDTO(SearchDTO validatedSearchDTO) {
		ArticleListDTO articleListDTO = ArticleListDTO.builder()
			.articleList(getArticleList(validatedSearchDTO))
			.numberOfArticles(getNumberOfArticles(validatedSearchDTO))
			.categoryList(getCategoryList())
			.searchDTO(validatedSearchDTO)
			.build();
		return articleListDTO;
	}

	/**
	 * 검색 대상 게시글 목록 반환
	 * @param validatedSearchDTO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 목록
	 */
	private List<ArticleVO> getArticleList(SearchDTO validatedSearchDTO) {
		return ARTICLE_DAO.selectAllArticles(validatedSearchDTO);
	}

	/**
	 * 검색 대상 총 게시글 수 반환
	 * @param validatedSearchDTO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 수
	 */
	private int getNumberOfArticles(SearchDTO validatedSearchDTO) {
		return ARTICLE_DAO.countArticles(validatedSearchDTO);
	}

	/**
	 * 검색창 카테고리 옵션 목록 반환
	 * @return 검색창 카테고리 옵션 목록
	 */
	private List<CategoryVO> getCategoryList() {
		return CATEGORY_DAO.selectCategories();
	}

	/**
	 * 유저 검색값 유효성 검증 및 정정하여 반환
	 * @param searchDTO 유저 검색값 객체
	 * @return 유효성 검증 후 수정된 검색값 객체
	 */
	private SearchDTO validateSearchParameters(SearchDTO searchDTO) {
		validateEmptyPageNumber(searchDTO);
		searchDTO.setFirstArticleIndex(searchDTO.getPageNumber());
		validateEmptySearchValues(searchDTO);
		return searchDTO;
	}

	/**
	 * 검색 옵션값 (startDate, endDate, Category, keyword) 유효성 검증 및 수정
	 * @param searchDTO 유저 검색값 객체
	 */
	private void validateEmptySearchValues(SearchDTO searchDTO) {
		if (Validate.isEmpty(searchDTO.getStartDate())) {
			searchDTO.setStartDate(null);
		}
		if (Validate.isEmpty(searchDTO.getEndDate())) {
			searchDTO.setEndDate(null);
		}
		if (Validate.isEmpty(searchDTO.getCategoryId())) {
			searchDTO.setCategoryId(null);
		}
		if (Validate.isEmpty(searchDTO.getKeyword())) {
			searchDTO.setKeyword(null);
		}
	}

	/**
	 * 검색 페이지 번호 유효성 검증 및 수정
	 * @param searchDTO 유저 검색값 객체
	 */
	private void validateEmptyPageNumber(SearchDTO searchDTO) {
		int pageNumber = 1;
		Integer searchedPageNumber = searchDTO.getPageNumber();
		if (!Objects.equals(searchedPageNumber, null)) {
			pageNumber = searchedPageNumber;
		}
		searchDTO.setPageNumber(pageNumber);
	}
}

