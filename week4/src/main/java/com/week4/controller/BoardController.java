package com.week4.controller;

import com.week4.service.ArticleService;
import com.week4.util.Validate;
import com.week4.vo.BoardVO;
import com.week4.vo.BoardVO.ArticleVO;
import com.week4.vo.BoardVO.CommentVO;
import com.week4.vo.BoardVO.SearchVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/")
@RequiredArgsConstructor
public class BoardController {

	/**
	 * 게시글 도메인 (게시글, 카테고리, 댓글) @Service
	 */
	private final ArticleService articleService;

	/**
	 * 메소드명 ????????????????????
	 * 게시판 정보 전체 조회
	 *
	 * @return 게시글 목록 (댓글 및 카테고리 정보 포함), 카테고리 목록
	 */
	@GetMapping("articles")
	public BoardVO getBoardVO(SearchVO searchVO) {
		SearchVO validatedSearchVO = Validate.validateSearchVO(searchVO);
		BoardVO boardVO = BoardVO.builder()
			.articleList(articleService.getArticleList(validatedSearchVO))
			.categoryList(articleService.getCategoryList())
			.searchVO(validatedSearchVO)
			.numberOfArticles(articleService.getNumberOfArticles(validatedSearchVO))
			.build();
		return boardVO;
	}

	/**
	 * 프론트에서 JSON 타입의 게시글 정보를 받아 DB 삽입
	 *
	 * @param articleVO JSON 게시글 정보
	 * @return 게시글 목록
	 */
	@PostMapping("articles")
	public String insertArticle(@RequestBody ArticleVO articleVO) {
		int result = articleService.registerArticle(articleVO);
		return "";
	}

	/**
	 * @return 게시글 작성 페이지 반환
	 */
	@PostMapping("articles/form")
	public String getArticleInputForm() {
		return "";
	}


	/**
	 * 게시글 조회
	 *
	 * @param articleId 대상 게시글 번호
	 * @return 게시글 정보 및 댓글 목록
	 */
	@GetMapping("articles/{articleId}")
	public BoardVO.ArticleVO getArticle(@PathVariable("articleId") int articleId) {
		return articleService.getArticle(articleId);
	}

	/**
	 * 게시글 수정
	 *
	 * @param articleVO 수정 내용이 담긴 게시글 객체
	 * @return 수정된 게시글
	 */
	@PutMapping("articles")
	public String updateArticle(@RequestBody ArticleVO articleVO) {
		int result = articleService.updateArticle(articleVO);
		return "";
	}

	/**
	 * 대상 게시글 삭제
	 *
	 * @param articleVO 대상 게시글 번호 및 유효성 검사를 위한 정보가 담긴 게시글 객체
	 * @return 게시글 목록
	 */
	/*
	articleId가 아닌 비밀번호가 담긴 articleVO를 객체로 받아서 서버 유효성 검증후 삭제 필요
	 */
	@DeleteMapping("articles")
	public String deleteArticle(@RequestBody ArticleVO articleVO) {
		System.out.println(articleVO.toString());
		int result = articleService.deleteArticle(articleVO);
		return "";
	}

	/**
	 * 대상 게시글 댓글 등록(삽입)
	 *
	 * @param commentVO 대상 게시글 번호 및 댓글 내용
	 * @return 수행 결과
	 */
	@PostMapping("articles/comment")
	public String insertComment(@RequestBody CommentVO commentVO) {
		int result = articleService.registerComment(commentVO);
		return "";
	}


	@GetMapping("articles/{articleId}/file/{fileId}")
	public String downloadFile(@PathVariable("articleId") int articleId,
		@PathVariable("fileId") int fileId) {
		return "";
	}

}
