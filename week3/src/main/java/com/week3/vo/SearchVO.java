package com.week3.vo;

import java.util.HashMap;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchVO {

	/**
	 * 한 페이지 표시 게시글 수
	 */
	final private int pageSize = 10;

	/**
	 * 페이징을 위한 특정 페이지 첫 행 인덱스
	 */
	private int firstArticleIndex;

	/**
	 * 검색값 정보
	 * Key (Value) :
	 * 		startDate ('yyyy-mm-dd')
	 * 		endDate ('yyyy-mm-dd')
	 * 		category (article_category_id)
	 * 		keyword (any text)
	 */
	private HashMap<String,String> searchParameters;


	/**
	 * 한 페이지 표시 게시글 수(pageSize)와 현재 페이지 번호 (pageNumber)를 이용해
	 * 페이지 첫 번째 게시글 인덱스 지정
	 * @param pageNumber 현재 페이지 번호
	 */
	public void setFirstArticleIndex(int pageNumber) {
		this.firstArticleIndex = this.pageSize * (pageNumber - 1);
	}

	/**
	 * 검색 조건이 든 HashMap 받아 저장
	 * @param searchParameters null 값이 아닌 검색 조건이 담긴 HashMap
	 */
	public void setSearchParameters(HashMap<String, String> searchParameters) {
		this.searchParameters = searchParameters;
	}
}
