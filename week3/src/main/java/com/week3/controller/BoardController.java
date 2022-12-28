package com.week3.controller;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CategoryDAO;
import com.week3.dao.CommentDAO;
import com.week3.vo.SearchVO;
import java.util.HashMap;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	@Autowired
	ArticleDAO articleDAO;
	@Autowired
	CommentDAO commentDAO;
	@Autowired
	CategoryDAO categoryDAO;

	@GetMapping("/")
	public String home() {
		return "redirect:/articleList";
	}

	@GetMapping("/articleList")
	public ModelAndView articleList(ModelAndView modelAndView, SearchVO searchVO) {

		modelAndView.setViewName("articleList");
		modelAndView.addObject("articleList",articleDAO.selectArticles(searchVO));
		modelAndView.addObject("numberOfArticles",articleDAO.countArticles(searchVO));
		modelAndView.addObject("categoryList",categoryDAO.selectCategories());
		modelAndView.addObject("pageNumber",1);
		modelAndView.addObject("searchVO",searchVO);
		return modelAndView;
	}

	@GetMapping("/articleDetail")
	public ModelAndView articleDetail(ModelAndView modelAndView, @RequestParam String articleId) {
		int articleId_ = Integer.parseInt(articleId);
		modelAndView.setViewName("articleDetail");
		modelAndView.addObject("article",articleDAO.selectOneArticle(articleId_));
		modelAndView.addObject("commentList",commentDAO.selectComments(articleId_));
		modelAndView.addObject("articleId",articleId_);
		return modelAndView;
	}
}
