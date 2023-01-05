package com.week4.service;

import com.week4.repository.ArticleRepository;
import com.week4.repository.FileRepository;
import com.week4.vo.BoardVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final FileRepository fileRepository;


	/**
	 * 대상 게시글 객체 반환
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 객체
	 */
	public BoardVO.ArticleVO getArticle(int articleId) {
		return articleRepository.getArticle(articleId);
	}

	/**
	 * 게시글 목록 조회
	 * @return 목록 내 게시글 정보 및 댓글 목록
	 */
	public List<BoardVO.ArticleVO> getArticleList(BoardVO.SearchVO validatedSearchVO) {
		return articleRepository.getArticleList(validatedSearchVO);
	}

	/**
	 * 카테고리 전체 조회
	 * @return 카테고리 정보
	 */
	public List<BoardVO.CategoryVO> getCategoryList() {
		return articleRepository.getCategoryList();
	}

	public int getNumberOfArticles(BoardVO.SearchVO validatedSearchVO) {
		return articleRepository.getNumberOfArticles(validatedSearchVO);
	}

	public int registerArticle(BoardVO.ArticleVO articleVO) {
		return articleRepository.registerArticle(articleVO);
	}

	public int deleteArticle(int articleId) {
		return articleRepository.deleteArticle(articleId);
	}

}
