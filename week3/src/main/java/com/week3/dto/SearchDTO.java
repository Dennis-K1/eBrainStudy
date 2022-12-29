package com.week3.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 유저 검색(조회)값 모음
 */
@Getter
@Setter
@ToString
public class SearchDTO {

	/**
	 * 한 페이지 표시 게시글 수
	 */
	final private int PAGE_SIZE = 10;

	/**
	 * 페이징을 위한 특정 페이지 첫 행 인덱스
	 */
	private int firstArticleIndex;

	/**
	 * 검색 대상 게시글 등록일시 시작 범위
	 * ('yyyy-mm-dd')
	 */
	private String startDate;

	/**
	 * 검색 대상 게시글 등록일시 종료 범위
	 * ('yyyy-mm-dd')
	 */
	private String endDate;

	/**
	 * 검색 대상 게시글 카테고리
	 * (article_category_id)
	 */
	private Integer categoryId;

	/**
	 * 검색 키워드
	 * (any text)
	 */
	private String keyword;

	/**
	 * 조회 대상 페이지 번호
	 */
	private int pageNumber;

	/**
	 * 한 페이지 표시 게시글 수(pageSize)와 현재 페이지 번호 (pageNumber)를 이용해
	 * 페이지 첫 번째 게시글 인덱스 지정
	 * @param pageNumber 현재 페이지 번호
	 */
	public void setFirstArticleIndex(int pageNumber) {
		if (pageNumber == 0) {
			return;
		}
		this.firstArticleIndex = this.PAGE_SIZE * (pageNumber - 1);
	}
}
