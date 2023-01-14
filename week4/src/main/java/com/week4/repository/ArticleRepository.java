package com.week4.repository;

import com.week4.vo.BoardVO;
import com.week4.vo.BoardVO.ArticleVO;
import com.week4.vo.BoardVO.CommentVO;
import com.week4.vo.BoardVO.SearchVO;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * 게시글 도메인 테이블 (article, article_comment, article_category) 접근을 위한 @Repository
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepository {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession sqlSession;

	/**
	 * 대상 게시글 1개 조회
	 *
	 * @param articleId 대상 게시글 번호
	 * @return 게시글 객체
	 */
	public BoardVO.ArticleVO getArticle(int articleId) {
		return sqlSession.selectOne("mapper.article.selectArticle",articleId);
	}

	/**
	 * 검색 조건 기반 게시글 목록 반환 (PAGE_SIZE 만큼)
	 *
	 * @param validatedSearchVO 유효성 검증한 검색 조건
	 * @return 게시글 목록
	 */
	public List<BoardVO.ArticleVO> getArticleList(SearchVO validatedSearchVO) {
		return sqlSession.selectList("mapper.article.selectAllArticles", validatedSearchVO);
	}

	/**
	 * 전체 카테고리 반환
	 *
	 * @return 카테고리 목록
	 */
	public List<BoardVO.CategoryVO> getCategoryList() {
		return sqlSession.selectList("mapper.article.selectAllCategories");
	}

	/**
	 * 검색 조건 기반 총 게시글 수 조회
	 *
	 * @param validatedSearchVO 유효성 검증한 검색 조건
	 * @return 검색 대상 게시글 수
	 */
	public int getNumberOfArticles(SearchVO validatedSearchVO) {
		return sqlSession.selectOne("mapper.article.countArticles", validatedSearchVO);
	}

	/**
	 * 게시글 등록(삽입)
	 *
	 * @param articleVO 대상 게시글 객체
	 * @return 수행 결과
	 */
	public int registerArticle(ArticleVO articleVO){
		return sqlSession.insert("mapper.article.insertArticle", articleVO);
	}

	/**
	 * 게시글 삭제
	 *
	 * @param articleId 대상 게시글 번호
	 * @return 수행 결과
	 */
	public int deleteArticle(int articleId){
		return sqlSession.update("mapper.article.deleteArticle", articleId);
	}

	/**
	 * 게시글 수정
	 *
	 * @param articleVO 대상 게시글 정보
	 * @return 수행 결과
	 */
	public int updateArticle(ArticleVO articleVO){
		return sqlSession.update("mapper.article.updateArticle", articleVO);
	}

	/**
	 * 조회 대상 게시글 조회수 증가
	 *
	 * @param articleId 대상 게시글 번호
	 */
	public void increaseViews (int articleId) {
		sqlSession.update("mapper.article.increaseViews", articleId);
	}

	/**
	 * 댓글 등록 (삽입)
	 * 
	 * @param commentVO 대상 게시글 번호 및 댓글 내용
	 * @return 수행 결과
	 */
	public int insertComment(CommentVO commentVO) {
		return sqlSession.insert("mapper.article.insertComment", commentVO);
	}

	public String getArticlePassword(int articleId) {
		return sqlSession.selectOne("mapper.article.selectArticlePassword",articleId);
	}

	public void updateFileStatus(HashMap<String, Integer> articleFileStatus) {
		sqlSession.update("mapper.article.updateFileStatus", articleFileStatus);
	}
}
