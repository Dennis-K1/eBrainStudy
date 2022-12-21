package com.week2.model;

import java.util.HashMap;
import lombok.Builder;
import lombok.Getter;

@Getter

/**
 * 게시글 조회 (selectArticles) : pageSize, currentPage 필수 생성
 * 게시글 갯수 조회 (countArticles) : pageSize, currentPage 불필요
 */
@Builder
public class SearchVO {

	/**
	 * 한 페이지 표시 게시글 수
	 * null 초기화를 위해 int 대신 Integer 사용 (selectArticles Mapper 참조)
	 */
	private Integer pageSize;

	/**
	 * 현재 페이지 번호
	 * null 초기화를 위해 int 대신 Integer 사용 (selectArticles Mapper 참조)
	 */
	private Integer currentPage;

	/**
	 * 검색값 정보
	 * Key (Value) :
	 * 		startDate ('yyyy-mm-dd')
	 * 		endDate ('yyyy-mm-dd')
	 * 		category (article_category_id)
	 * 		keyword (any text)
	 */
	private HashMap<String,String> searchParameters;


}
