package com.week2.model;

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

	public ArticleVO getArticles() {
		SqlSessionFactory sqlSessionFactory;
		SqlSession session = null;
		InputStream inputStream;
		ArticleVO article = null;

		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			article = session.selectOne("mapper.article.getAllArticles");
			System.out.println(article.getContent());
		} catch (IOException e) {
			System.out.println(e.getMessage());
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
			System.out.println(e.getMessage());
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
