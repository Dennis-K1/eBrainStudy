package com.week3.repository;

import com.week3.vo.ArticleVO;
import com.week3.dto.SearchDTO;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

/**
 * 게시글 테이블 접근을 위한 @Repository
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepository {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession sqlSession;

	/**
	 * 대상 게시물 1개 조회
	 * @param articleId 대상 게시물 번호
	 * @return 게시물 객체
	 */
	public ArticleVO selectArticle(int articleId) {
		return sqlSession.selectOne("mapper.article.selectArticle",articleId);
	}

	/**
	 * 검색 대상 게시물 목록 조회
	 * @param validatedSearchDTO 유효성 검증한 유저 검색값 객체
	 * @return 게시글 목록
	 */
	public List selectAllArticles(SearchDTO validatedSearchDTO) {
		return sqlSession.selectList("mapper.article.selectArticles", validatedSearchDTO);
	}

	/**
	 * 검색 대상 총 게시물 수 조회
	 * @param validatedSearchDTO 유효성 검증한 유저 검색값 객체
	 * @return 검색 대상 게시글 수
	 */
	public int countArticles(SearchDTO validatedSearchDTO) {
		return sqlSession.selectOne("mapper.article.countArticles", validatedSearchDTO);
	}

	public int insertArticle(ArticleVO articleVO){
		return sqlSession.insert("mapper.article.insertArticle", articleVO);
	}

	public int deleteArticle(int articleId){
		return sqlSession.update("mapper.article.deleteArticle", articleId);
	}

	public int updateArticle(ArticleVO articleVO){
		return sqlSession.update("mapper.article.updateArticle", articleVO);
	}

	public void increaseViews (int articleId) {
		sqlSession.update("mapper.article.increaseViews", articleId);
	}

	public void updateFileStatus (HashMap articleFileStatus) {
		sqlSession.update("mapper.article.updateFileStatus", articleFileStatus);

	}
}
