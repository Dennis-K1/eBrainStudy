package com.week3.dao;

import com.week3.vo.ArticleVO;
import com.week3.vo.SearchVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * 게시글 테이블 접근을 위한 @Repository
 */
@Repository
@RequiredArgsConstructor
public class ArticleDAO {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession SQL_SESSION;

	/**
	 * 대상 게시물 1개 조회
	 * @param articleId 대상 게시물 번호
	 * @return 게시물 객체
	 */
	public ArticleVO selectArticle(int articleId) {
		return SQL_SESSION.selectOne("mapper.article.selectArticle",articleId);
	}

	/**
	 * 검색 대상 게시물 목록 조회
	 * @param validatedSearchVO 유효성 검증한 유저 검색값 객체
	 * @return 게시글 목록
	 */
	public List selectAllArticles(SearchVO validatedSearchVO) {
		return SQL_SESSION.selectList("mapper.article.selectArticles",validatedSearchVO);
	}

	/**
	 * 검색 대상 총 게시물 수 조회
	 * @param validatedSearchVO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 수
	 */
	public int countArticles(SearchVO validatedSearchVO) {
		return SQL_SESSION.selectOne("mapper.article.countArticles", validatedSearchVO);
	}

	public int insertArticle(ArticleVO article){
		return SQL_SESSION.insert("mapper.article.insertArticle", article);
	}

	public int deleteArticle(int articleId){
		return SQL_SESSION.update("mapper.article.deleteArticle", articleId);
	}

	public int updateArticle(ArticleVO article){
		return SQL_SESSION.update("mapper.article.updateArticle", article);
	}

	public void increaseViews ( int articleId) {
		SQL_SESSION.update("mapper.article.increaseViews", articleId);

	}
}
