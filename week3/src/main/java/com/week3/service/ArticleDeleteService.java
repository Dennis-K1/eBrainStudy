package com.week3.service;

import com.week3.dao.ArticleDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleDeleteService {

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;

	public int service(int articleId) {
//		validatePassword();
		int result = ARTICLE_DAO.deleteArticle(articleId);
		return result;
	}

	public void validatePassword() {

	}
}
