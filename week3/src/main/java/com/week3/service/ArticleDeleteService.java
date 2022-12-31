package com.week3.service;

import com.week3.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 삭제를 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleDeleteService {
	/*
	구현 필요
	- 비밀 번호 유효성 검증
	- 파일 삭제
	 */

	/**
	 * articleRepository		- 게시글 테이블 접근을 위한 @Repository
	 */
	private final ArticleRepository articleRepository;

	/**
	 * 게시글 정보 DB 에서 삭제 (article_deleted 값 변경) 후 결과 반환
	 * @param articleId 삭제 대상 게시글 번호
	 * @return 삭제 결과
	 */
	public int service(int articleId) {
		int result = articleRepository.deleteArticle(articleId);
		return result;
	}

	public void validatePassword() {

	}
}
