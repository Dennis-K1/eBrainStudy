package com.week3.controller;

import com.week3.dto.ArticleUpdateDTO;
import com.week3.service.ArticleDeleteService;
import com.week3.service.ArticleDetailService;
import com.week3.service.ArticleInsertService;
import com.week3.service.ArticleListService;
import com.week3.dto.SearchDTO;
import com.week3.service.ArticleUpdateService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleController {

	private final ArticleListService ARTICLE_LIST_SERVICE;
	private final ArticleDetailService ARTICLE_DETAIL_SERVICE;
	private final ArticleInsertService ARTICLE_INSERT_SERVICE; // 중복 코드
	private final ArticleUpdateService ARTICLE_UPDATE_SERVICE; // 중복 코드
	private final ArticleDeleteService ARTICLE_DELETE_SERVICE;

	@GetMapping("/")
	public String home(){
		return "redirect:articleList";
	}

	@GetMapping("/articleList")
	public String articleList(SearchDTO searchDTO, Model model) {
		model.addAttribute("articleListDTO", ARTICLE_LIST_SERVICE.service(searchDTO));
		return "articleList";
	}

	@GetMapping("/articleDetail")
	public String articleDetail(@RequestParam("articleId") int articleId, Model model) {
		model.addAttribute("articleDetailDTO", ARTICLE_DETAIL_SERVICE.service(articleId));
		return "articleDetail";
	}

	@GetMapping("/articleInput")
	public String articleInput(Model model) {
		model.addAttribute("categoryList",ARTICLE_INSERT_SERVICE.getCategoryList());
		return "articleInputForm";
	}

	@PostMapping("/articleInsert")
	public String articleInsert(@ModelAttribute ArticleUpdateDTO articleUpdateDTO) throws IOException {
		int result = ARTICLE_INSERT_SERVICE.service(articleUpdateDTO);
		return "redirect:articleList";
	}

	@PostMapping("/articleEdit")
	public String articleEdit(@RequestParam("articleId") int articleId, Model model) {
		model.addAttribute("articleDetailDTO", ARTICLE_DETAIL_SERVICE.service(articleId));
		return "articleEditForm";
	}
	@PostMapping("/articleUpdate")
	public String articleUpdate(@ModelAttribute ArticleUpdateDTO articleUpdateDTO) {
		int result = ARTICLE_UPDATE_SERVICE.service(articleUpdateDTO);
		int articleId = articleUpdateDTO.getArticleId();
		return "redirect:articleDetail?articleId=" + articleId;
	}

	@PostMapping("/articleDelete")
	public String articleDelete(@RequestParam("articleId") int articleId) {
		int result = ARTICLE_DELETE_SERVICE.service(articleId);
		return "redirect:articleList";
	}
}
