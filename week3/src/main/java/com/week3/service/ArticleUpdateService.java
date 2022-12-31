package com.week3.service;

import com.week3.repository.ArticleRepository;
import com.week3.dto.ArticleDetailDTO;
import com.week3.dto.ArticleUpdateDTO;
import com.week3.vo.ArticleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 게시글 수정을 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleUpdateService {
	/*
	구현 필요
	- 서버 유효성 검증
	- 파일 업데이트
	 */

	/**
	 * articleRepository		- 게시글 테이블 접근을 위한 @Repository
	 */
	private final ArticleRepository articleRepository;

	/**
	 * 클라이언트가 수정하여 전송한 게시글 정보 DTO 수취후 새로운 VO 객체 생성하여 업데이트
	 * @param articleUpdateDTO 게시글 정보 DTO
	 * @return 수행 결과
	 */
	public int service(ArticleUpdateDTO articleUpdateDTO) {
		ArticleVO articleVO = createArticleVO(articleUpdateDTO);
		int result = articleRepository.updateArticle(articleVO);
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
}
