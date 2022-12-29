package com.week3.dto;

import com.week3.vo.ArticleVO;
import com.week3.vo.CategoryVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * ArticleList 페이지에 표시할 데이터 객체 모음
 */
@Getter
@Builder
public class ArticleListDTO {

	/**
	 * 조회된 게시글 목록
	 */
	private List<ArticleVO> articleList;

	/**
	 * 조회된 게시글 갯수
	 */
	private int numberOfArticles;

	/**
	 * 검색창 카테고리 옵션 목록
	 */
	private List<CategoryVO> categoryList;

	/**
	 * 유저 검색 값 모음
	 */
	private SearchDTO searchDTO;

}
