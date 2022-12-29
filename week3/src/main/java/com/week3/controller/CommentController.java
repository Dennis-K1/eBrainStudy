package com.week3.controller;

import com.week3.service.CommentInsertService;
import com.week3.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CommentController {

	private final CommentInsertService COMMENT_INSERT_SERVICE;

	@PostMapping("/commentInsert")
	public String commentInsert(@ModelAttribute CommentVO commentVO, Model model) {
		COMMENT_INSERT_SERVICE.service(commentVO);
		int articleId = commentVO.getArticleId();
		model.addAttribute("articleId", articleId);
		return "redirect:articleDetail?articleId=" + articleId;
	}
}
