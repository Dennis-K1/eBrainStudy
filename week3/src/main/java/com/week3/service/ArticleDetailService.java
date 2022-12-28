package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CommentDAO;
import com.week3.dto.ArticleDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleDetailService {

	private final ArticleDAO articleDAO;
	private final CommentDAO commentDAO;

	public ArticleDetailDTO selectArticle(int articleId) {

		ArticleDetailDTO articleDetailDTO = ArticleDetailDTO.builder()
			.articleId(articleId)
			.article(articleDAO.selectOneArticle(articleId))
			.commentList(commentDAO.selectComments(articleId))
			.build();

		return articleDetailDTO;
	}


}
