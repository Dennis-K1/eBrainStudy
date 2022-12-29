package com.week3.service;

import com.week3.dao.ArticleDAO;
import com.week3.dao.CommentDAO;
import com.week3.dto.ArticleDetailDTO;
import com.week3.vo.ArticleVO;
import com.week3.vo.CommentVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ArticleDetail 페이지 비즈니스 로직을 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class ArticleDetailService {

	/**
	 * ARTICLE_DAO		- 게시글 테이블 접근을 위한 @Repository
	 * COMMENT_DAO		- 게시글 댓글 테이블 접근을 위한 @Repository
	 */
	private final ArticleDAO ARTICLE_DAO;
	private final CommentDAO COMMENT_DAO;

	/**
	 * 조회수 증가시키며 ArticleDetail 페이지에 필요한 데이터 모음 생성 및 반환
	 * @param articleId 대상 게시글 번호
	 * @return ArticleDetailDTO (데이터 모음 객체)
	 */
	@Transactional
	public ArticleDetailDTO service(int articleId) {
		ARTICLE_DAO.increaseViews(articleId);
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
		return ARTICLE_DAO.selectArticle(articleId);
	}

	/**
	 * 대상 게시글 댓글 목록 반환
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 댓글 목록
	 */
	public List<CommentVO> getCommentList(int articleId) {
		return COMMENT_DAO.selectAllComments(articleId);
	}
}
