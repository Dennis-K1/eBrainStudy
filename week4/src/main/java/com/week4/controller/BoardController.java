package com.week4.controller;

import com.week4.service.ArticleService;
import com.week4.vo.ArticleVO;
import com.week4.vo.BoardVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
@RequiredArgsConstructor
public class BoardController {

	private final ArticleService articleService;

	@GetMapping("articles")
	public List<BoardVO> getArticleList() {
		return articleService.getBoardVO();
	}

	@PostMapping("articles")
	public String insertArticle() {
		return "";
	}

	@PostMapping("articles/form")
	public String getArticleInputForm() {
		return "";
	}

	@GetMapping("articles/{articleId}")
	public ArticleVO getArticle(@PathVariable("articleId") int articleId) {
		return articleService.getArticle(articleId);
	}

	@PutMapping("articles/{articleId}")
	public String updateArticle(@PathVariable("articleId") int articleId) {
		return "";
	}

	@DeleteMapping("articles/{articleId}")
	public String deleteArticle(@PathVariable("articleId") int articleId) {
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
