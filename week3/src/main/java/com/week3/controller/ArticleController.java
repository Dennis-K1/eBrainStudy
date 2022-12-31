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

/**
 * Article 관련 요청을 처리하는 @Controller
 */
@Controller
@RequiredArgsConstructor
public class ArticleController {

	/**
	 * ArticleListService		- 게시글 목록 페이지를 위한 @Service
	 * ArticleDetailService		- 게시글 상세 페이지를 위한 @Service
	 * ArticleInsertService		- 게시글 등록을 위한 @Service
	 * ArticleUpdateService		- 게시글 수정을 위한 @Service
	 * ArticleDeleteService		- 게시글 삭제를 위한 @Service
	 */
	private final ArticleListService ArticleListService;
	private final ArticleDetailService ArticleDetailService;
	private final ArticleInsertService ArticleInsertService; // 중복 코드
	private final ArticleUpdateService ArticleUpdateService; // 중복 코드
	private final ArticleDeleteService ArticleDeleteService;

	/**
	 * 랜딩 페이지
	 * @return 요청값 없는 게시글 목록 JSP 페이지
	 */
	@GetMapping("/")
	public String home(){
		return "redirect:articleList";
	}

	/**
	 * 게시글 목록
	 * @param searchDTO 유저 검색값
	 * @param model view 페이지 데이터 전달 객체
	 * @return 게시글 목록 JSP 페이지
	 */
	@GetMapping("/articleList")
	public String articleList(SearchDTO searchDTO, Model model) {
		model.addAttribute("articleListDTO", ArticleListService.service(searchDTO));
		return "articleList";
	}

	/**
	 * 게시글 상세
	 * @param articleId 대상 게시글 번호
	 * @param model view 페이지 데이터 전달 객체
	 * @return 게시글 상세 JSP 페이지
	 */
	@GetMapping("/articleDetail")
	public String articleDetail(@RequestParam("articleId") int articleId, Model model) {
		model.addAttribute("articleDetailDTO", ArticleDetailService.service(articleId));
		return "articleDetail";
	}

	/**
	 * 게시글 등록 (Form)
	 * @param model view 페이지 데이터 전달 객체
	 * @return 게시글 등록 폼 JSP 페이지
	 */
	@GetMapping("/articleInput")
	public String articleInput(Model model) {
		model.addAttribute("categoryList",ArticleInsertService.getCategoryList());
		return "articleInputForm";
	}

	/**
	 * 게시글 등록 (DB INSERT)
	 * @param articleUpdateDTO 게시글 등록 폼에서 전달된 정보 객체
	 * @return 요청값 없는 게시글 목록 JSP 페이지
	 */
	@PostMapping("/articleInsert")
	public String articleInsert(@ModelAttribute ArticleUpdateDTO articleUpdateDTO) throws IOException {
		int result = ArticleInsertService.service(articleUpdateDTO);
		return "redirect:articleList";
	}

	/**
	 * 게시글 수정 (Form)
	 * @param articleId 대상 게시글 번호
	 * @param model view 페이지 데이터 전달 객체
	 * @return 게시글 수정 폼 JSP 페이지
	 */
	@PostMapping("/articleEdit")
	public String articleEdit(@RequestParam("articleId") int articleId, Model model) {
		model.addAttribute("articleDetailDTO", ArticleDetailService.service(articleId));
		return "articleEditForm";
	}

	/**
	 * 게시글 수정 (DB UPDATE)
	 * @param articleUpdateDTO 게시글 등록 폼에서 전달된 정보 객체
	 * @return 수정후 게시글 상세 JSP 페이지
	 */
	@PostMapping("/articleUpdate")
	public String articleUpdate(@ModelAttribute ArticleUpdateDTO articleUpdateDTO) {
		int result = ArticleUpdateService.service(articleUpdateDTO);
		int articleId = articleUpdateDTO.getArticleId();
		return "redirect:articleDetail?articleId=" + articleId;
	}

	/**
	 * 게시글 삭제
	 * @param articleId 대상 게시글 번호
	 * @return 요청값 없는 게시글 목록 JSP 페이지
	 */
	@PostMapping("/articleDelete")
	public String articleDelete(@RequestParam("articleId") int articleId) {
		int result = ArticleDeleteService.service(articleId);
		return "redirect:articleList";
	}
}
