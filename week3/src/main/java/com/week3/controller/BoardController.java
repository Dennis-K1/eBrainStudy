package com.week3.controller;

import com.week3.service.ArticleDetailService;
import com.week3.service.ArticleListService;
import com.week3.vo.SearchVO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final ArticleListService ARTICLE_LIST_SERVICE;
	private final ArticleDetailService ARTICLE_DETAIL_SERVICE;
//	private final ArticleInsertService articleInsertService;
//	private final ArticleUpdateService articleUpdateService;
//	private final ArticleDeleteService articleDeleteService;
//	private final CommentInsertService commentInsertService;


	@GetMapping("/test")
	public String home(SearchVO searchVO) {
		System.out.println(searchVO.getKeyword());
		System.out.println(searchVO.toString());
		return searchVO.toString();
	}

	@GetMapping("/")
	public String home(){
		return "redirect:articleList";
	}

	@GetMapping("/articleList")
	public String articleList(SearchVO searchVO, Model model) {
		model.addAttribute("articleListDTO", ARTICLE_LIST_SERVICE.service(searchVO));
		return "articleList";
	}


	@GetMapping("/articleDetail")
	public String articleDetail(@RequestParam("articleId") int articleId, Model model) {
		model.addAttribute("articleDetailDTO", ARTICLE_DETAIL_SERVICE.service(articleId));
		return "articleDetail";
	}

//	@GetMapping("articleInput")
//	public String (, Model model) {
//
//		return "articleInput";
//	}
//
//	@GetMapping("")
//	public String (, Model model) {
//		return "";
//	}
//
//	@GetMapping("")
//	public String (, Model model) {
//		return "";
//	}
//
//	@GetMapping("")
//	public String (, Model model) {
//		return "";
//	}
//
//	@GetMapping("")
//	public String (, Model model) {
//		return "";
//	}

}
