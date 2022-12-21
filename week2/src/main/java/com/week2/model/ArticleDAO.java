package com.week2.model;

import java.util.HashMap;
import java.util.SimpleTimeZone;
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

	public ArticleVO selectArticle(int articleId) {
		SqlSessionFactory sqlSessionFactory;
		SqlSession session = null;
		InputStream inputStream;
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

	public int insertArticle(ArticleVO article){
		int result = 0;

		SqlSessionFactory sqlSessionFactory;
		SqlSession session = null;
		InputStream inputStream;

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

	public int deleteArticle(int articleId){
		int result = 0;

		SqlSessionFactory sqlSessionFactory;
		SqlSession session = null;
		InputStream inputStream;

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

	public int updateArticle(ArticleVO article){
		int result = 0;

		SqlSessionFactory sqlSessionFactory;
		SqlSession session = null;
		InputStream inputStream;

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

	public int test01() {
		return 1;
	}
}
