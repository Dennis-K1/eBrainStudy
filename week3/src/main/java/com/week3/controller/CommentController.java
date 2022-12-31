package com.week3.controller;

import com.week3.service.CommentInsertService;
import com.week3.vo.CommentVO;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Comment 관련 요청을 처리하는 @Controller
 */
@Controller
@RequiredArgsConstructor
public class CommentController {

	/**
	 * CommentInsertService		- 댓글 등록을 위한 @Service
	 */
	private final CommentInsertService commentInsertService;

	/**
	 * 댓글 등록
	 * @param commentVO 댓글 정보 객체
	 * @param model view 페이지 데이터 전달 객체
	 * @return 댓글 등록 후 게시글 상세 JSP 페이지
	 */
	@PostMapping("/commentInsert")
	public String commentInsert(@ModelAttribute CommentVO commentVO, Model model)
		throws IOException {
		int articleId = commentInsertService.service(commentVO);
		model.addAttribute("articleId", articleId);
		return "redirect:articleDetail?articleId=" + articleId;
	}
}
