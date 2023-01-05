package com.week4.controller;

import com.week4.service.ArticleService;
import com.week4.util.Validate;
import com.week4.vo.BoardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class BoardController {

	private final ArticleService articleService;

	/**
	 * 메소드명 ????????????????????
	 * 게시판 정보 전체 조회
	 * @return 게시글 목록 (댓글 및 카테고리 정보 포함), 카테고리 목록
	 */
	@GetMapping("articles")
	public BoardVO getBoardVO(BoardVO.SearchVO searchVO) {
		BoardVO.SearchVO validatedSearchVO = Validate.validateSearchVO(searchVO);
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
	 * @param articleVO JSON 게시글 정보
	 * @return 게시글 목록
	 */
	@PostMapping("articles")
	public String insertArticle(@RequestBody BoardVO.ArticleVO articleVO) {
		int result = articleService.registerArticle(articleVO);
		return "";
	}

	@PostMapping("articles/form")
	public String getArticleInputForm() {
		return "";
	}

	/**
	 * 게시글 조회
	 * @param articleId 대상 게시글 번호
	 * @return 게시글 정보 및 댓글 목록
	 */
	@GetMapping("articles/{articleId}")
	public BoardVO.ArticleVO getArticle(@PathVariable("articleId") int articleId) {
		return articleService.getArticle(articleId);
	}

	@PutMapping("articles/{articleId}")
	public String updateArticle(@PathVariable("articleId") int articleId) {
		return "";
	}

	@DeleteMapping("articles/{articleId}")
	public String deleteArticle(@PathVariable("articleId") int articleId) {
		int result = articleService.deleteArticle(articleId);
		System.out.println(result);
		return "";
	}

	@PostMapping("articles/{articleId}/comment")
	public String insertComment(@PathVariable("articleId") int articleId) {
		return "";
	}

	@GetMapping("articles/{articleId}/file/{fileId}")
	public String downloadFile(@PathVariable("articleId") int articleId, @PathVariable("fileId") int fileId) {
		return "";
	}

}
