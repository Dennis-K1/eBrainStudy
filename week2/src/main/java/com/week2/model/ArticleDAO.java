package com.week2.model;

import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class ArticleDAO {

	/**
	 * MyBatis 설정 파일 경로
	 */
	private String resource = "SqlMapConfig.xml";

	/**
	 * 기본 생성자
	 */
	public ArticleDAO() {
	}


	/**
	 * 특정 게시글 한 개 조회
	 * @param articleId 대상 게시글 번호
	 * @return 게시글 VO
	 */
	public ArticleVO selectArticle(int articleId) {
		/**
		 * SqlSessionFactory / InputStream 선언 or Not?
		 */
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		ArticleVO article = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			article = session.selectOne("mapper.article.selectArticle",articleId);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return article;
	}

	/**
	 * 검색 조건 및 페이지 상태 기반 다수 게시글 조회 (조건 없을 시 전체 게시글 반환)
	 * @param searchVO 다수의 검색 조건 (HashMap)과 페이지 상태가 기록된 VO
	 * @return 결과 게시글 VO이 담긴 List
	 */
	public List selectArticles(SearchVO searchVO) {
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		List<ArticleVO> articleList = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			articleList = session.selectList("mapper.article.selectArticles",searchVO);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return articleList;
	}

	/**
	 * 검색 조건 기반 총 게시글 갯수 조회 (조건 없을 시 전체 갯수 반환)
	 * @param searchVO 검색 조건 (검색값 없을 경우 자동 제외 처리)
	 * @return 게시글 수
	 */
	public int countArticles(SearchVO searchVO) {
		int numberOfArticles = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			numberOfArticles = session.selectOne("mapper.article.countArticles", searchVO);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return numberOfArticles;
	}

	/**
	 * 게시글 등록
	 * @param article 등록할 게시글 VO
	 * @return 등록 결과 ( 성공 : 1, 실패 : 0)
	 */
	public int insertArticle(ArticleVO article){
		int result = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			result = session.insert("mapper.article.insertArticle", article);
			session.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 특정 게시글 삭제
	 * @param articleId 삭제 대상 게시글 번호
	 * @return 삭제 결과 ( 성공 : 1, 실패 : 0)
	 */
	public int deleteArticle(int articleId){
		int result = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			result = session.delete("mapper.article.deleteArticle", articleId);
			session.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 특정 게시글 수정
	 * @param article 수정 대상 게시글 (수정 내용 있을 시 반영된 VO)
	 * @return 수정 결과 ( 성공 : 1, 실패 : 0)
	 */
	public int updateArticle(ArticleVO article){
		int result = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			result = session.update("mapper.article.updateArticle", article);
			session.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

}
