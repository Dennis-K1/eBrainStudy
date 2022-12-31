package com.week3.service;

import com.week3.repository.ArticleRepository;
import com.week3.repository.CommentRepository;
import com.week3.dto.ArticleDetailDTO;
import com.week3.vo.ArticleVO;
import com.week3.vo.CommentVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 상세 페이지를 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleDetailService {

	/**
	 * articleRepository		- 게시글 테이블 접근을 위한 @Repository
	 * commentRepository		- 게시글 댓글 테이블 접근을 위한 @Repository
	 */
	private final ArticleRepository articleRepository;
	private final CommentRepository commentRepository;

	/**
	 * 조회수 증가시키며 ArticleDetail 페이지에 필요한 데이터 모음 생성 및 반환
	 * @param articleId 대상 게시글 번호
	 * @return ArticleDetailDTO (데이터 모음 객체)
	 */
	@Transactional(rollbackFor = {RuntimeException.class, Exception.class})
	public ArticleDetailDTO service(int articleId) {
		articleRepository.increaseViews(articleId);
		return createDTO(articleId);
	}

	/**
	 * 필요 데이터 생성 후 ArticleDetailDTO 객체에 통합
	 * @param articleId 대상 게시글 번호
	 * @return ArticleDetailDTO (데이터 모음 객체)
	 */
	public ArticleDetailDTO createDTO(int articleId) {
		ArticleDetailDTO articleDetailDTO = ArticleDetailDTO.builder()
			.articleVO(getArticle(articleId))
			.commentList(getCommentList(articleId))
			.build();
		return articleDetailDTO;
	}

	/**
	 * 대상 게시글 객체 반환
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 객체
	 */
	public ArticleVO getArticle(int articleId) {
		return articleRepository.selectArticle(articleId);
	}

	/**
	 * 대상 게시글 댓글 목록 반환
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 댓글 목록
	 */
	public List<CommentVO> getCommentList(int articleId) {
		return commentRepository.selectAllComments(articleId);
	}
}
