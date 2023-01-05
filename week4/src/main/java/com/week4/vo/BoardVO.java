package com.week4.vo;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class BoardVO {

	private List<ArticleVO> articleList;
	private List<CategoryVO> categoryList;

	/*
		내부 클래스 CategoryVO와 ArticleVO 의 CategoryVO가 충돌을 일으켜 ArticleVO의 CategoryVO는 "id", "name" 분리하여 표기.
		(ArticleVO의 CategoryVO를 별도 생성하고 풀패키지명으로 제네릭을 명시하면 해결 되지만 파일 한 개로 관리하기 위해 채택 X)
 	*/

	@Builder
	public BoardVO (List<ArticleVO> articleList, List<CategoryVO> categoryList) {
		this.articleList = articleList;
		this.categoryList = categoryList;
	}
	@Getter
	@NoArgsConstructor
	public static class ArticleVO {


		/**
		 * 게시글 번호
		 */
		private int id;

		/**
		 * 게시글 제목
		 */
		private String title;

		/**
		 * 게시글 내용
		 */
		private String content;

		/**
		 * 게시글 조회수
		 */
		private int views;

		/**
		 * 게시글 작성자
		 */
		private String writer;

		/**
		 * 게시글 비밀번호
		 */
		private String password;

		/**
		 * 게시글 등록 일시
		 */
		private String dateCreated;

		/**
		 * 게시글 수정 일시
		 */
		private String lastUpdated;

		/**
		 * 게시글 파일 첨부 여부
		 */
		private int fileAttached;

		/**
		 * 게시글 삭제 여부
		 */
		private int articleDeleted;


		/**
		 * 게시글 카테고리 번호
		 */
		private int categoryId;

		/**
		 * 게시글 카테고리 이름
		 */
		private String categoryName;

		/**
		 *  게시글 댓글 목록
		 */
		private List<CommentVO> commentList;

	}

	@Getter
	@NoArgsConstructor
	public static class CategoryVO{

		/**
		 * 카테고리 번호
		 */
		private int id;

		/**
		 * 카테고리 이름
		 */
		private String name;
	}


	@Getter
	@NoArgsConstructor
	public static class CommentVO {

		/**
		 * 댓글 번호
		 */
		private Integer id;

		/**
		 * 댓글이 등록된 게시글 번호
		 */
		private Integer articleId;

		/**
		 * 댓글 내용
		 */
		private String content;

		/**
		 * 댓글 등록 일시
		 */
		private String dateCreated;
	}
}


