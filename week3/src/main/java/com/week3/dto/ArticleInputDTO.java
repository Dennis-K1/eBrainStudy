package com.week3.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * ArticleInputForm 에서 전달되어
 * ArticleInsert 를 실행할 데이터 객체 모음
 */
@Getter
@Builder
public class ArticleInputDTO {

	/**
	 * 등록 대상 게시글 객체
	 */
	private int categoryId;

	/**
	 * 게시글 작성자
	 */
	private String writer;

	/**
	 * 게시글 비밀번호
	 */
	private String password;

	/**
	 * 게시글 제목
	 */
	private String title;

	/**
	 * 게시글 내용
	 */
	private String content;

//	/**
//	 * 등록 대상 게시글 파일 모음
//	 */
//	private List<FileVO> fileList;

}
