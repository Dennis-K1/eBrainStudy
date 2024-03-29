package com.week4.service;

import static com.week4.util.Validate.validateArticleInput;

import com.week4.repository.ArticleRepository;
import com.week4.repository.FileRepository;
import com.week4.vo.BoardVO;
import com.week4.vo.BoardVO.ArticleVO;
import com.week4.vo.BoardVO.CommentVO;
import com.week4.vo.BoardVO.SearchVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

	/**
	 * 게시글 도메인 테이블 (article, article_comment, article_category) 접근을 위한 @Repository
	 */
	private final ArticleRepository articleRepository;

	/**
	 * 파일 도메인 테이블 (file) 접근을 위한 @Repository
	 */
	private final FileRepository fileRepository;


	/**
	 * 게시글 조회수 증가 및 정보 반환
	 *
	 * @param articleId 대상 게시글 번호
	 * @return 대상 게시글 객체
	 */
	public BoardVO.ArticleVO getArticle(int articleId) {
		articleRepository.increaseViews(articleId);
		return articleRepository.getArticle(articleId);
	}

	/**
	 * 검색 조건 기반 게시글 목록 조회
	 *
	 * @param validatedSearchVO 유효성 검증한 검색 조건
	 * @return 목록 내 게시글 정보 및 댓글 목록
	 */
	public List<BoardVO.ArticleVO> getArticleList(SearchVO validatedSearchVO) {
		return articleRepository.getArticleList(validatedSearchVO);
	}

	/**
	 * 카테고리 전체 조회
	 *
	 * @return 카테고리 정보
	 */
	public List<BoardVO.CategoryVO> getCategoryList() {
		return articleRepository.getCategoryList();
	}

	/**
	 * 검색 조건 기반 총 게시글 수 조회
	 * 
	 * @param validatedSearchVO 유효성 검증한 검색 조건
	 * @return 게시글 수
	 */
	public int getNumberOfArticles(SearchVO validatedSearchVO) {
		return articleRepository.getNumberOfArticles(validatedSearchVO);
	}

	/**
	 * 게시글 등록(삽입)
	 * 
	 * @param articleVO 등록할 게시글 정보 객체
	 * @return 등록한 게시글 번호
	 */
	public int registerArticle(ArticleVO articleVO) {
		if(validateArticleInput(articleVO) == -1){
			return -1;
		}
		articleRepository.registerArticle(articleVO);
		return articleVO.getId();
	}

	/**
	 * 게시글 삭제
	 * 
	 * @param articleVO 대상 게시글 번호와 유저입력 비밀번호가 담긴 객체
	 * @return 수행 결과
	 */
	//TODO 비밀번호 유효성
	public int deleteArticle(ArticleVO articleVO) {
		int articleId = articleVO.getId();
		return articleRepository.deleteArticle(articleId);
	}

	/**
	 * 게시글 수정
	 * 
	 * @param articleVO 대상 게시글 정보
	 * @return 수정 결과
	 */
	public int updateArticle(ArticleVO articleVO) {
		return articleRepository.updateArticle(articleVO);
	}

	/**
	 * 댓글 등록(삽입)
	 * 
	 * @param commentVO 대상 게시글 번호 및 댓글 내용
	 * @return 수행 결과
	 */
	public int registerComment(CommentVO commentVO) {
		return articleRepository.insertComment(commentVO);
	}

	/**
	 * 유저 입력 비밀번호 인증
	 *
	 * @param articleVO 대상 게시글 번호와 유저 입력 비밀번호가 담긴 객체
	 */
	public Boolean isArticlePasswordCorrect(ArticleVO articleVO) {
		String articlePassword = articleRepository.getArticlePassword(articleVO.getId());
		if ((articlePassword).equals(articleVO.getPassword())){
			return true;
		}
		return false;
	}
}
