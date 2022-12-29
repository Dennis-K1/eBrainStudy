package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dto.ArticleDetailDTO;
import com.week3.dto.ArticleUpdateDTO;
import com.week3.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleUpdateService {

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;

	public int service(ArticleUpdateDTO articleUpdateDTO) {
		ArticleVO articleVO = createArticleVO(articleUpdateDTO);
		int result = ARTICLE_DAO.updateArticle(articleVO);
		return result;
	}

	/**
	 * 유효성 검증 후 게시글 객체 생성
	 * @param articleUpdateDTO ArticleInputForm 페이지 사용자 입력값 모음
	 * @return 게시글 객체
	 */
	private ArticleVO createArticleVO(ArticleUpdateDTO articleUpdateDTO) {
		ArticleVO articleVO = ArticleVO.builder()
			.id(articleUpdateDTO.getArticleId())
			.title(articleUpdateDTO.getTitle())
			.content(articleUpdateDTO.getContent())
			.writer(articleUpdateDTO.getWriter())
			.build();
		return articleVO;
	}

	/**
	 * 사용자 입력값 서버 유효성 검증
	 * @param articleDetailDTO ArticleInputForm 페이지 사용자 입력값 모음
	 */

	private Boolean validateArticleInputValues(ArticleDetailDTO articleDetailDTO) {
		return true;
	}
	/**
	 * 검색창 카테고리 옵션 목록 반환
	 * @return 검색창 카테고리 옵션 목록
	 */
}
