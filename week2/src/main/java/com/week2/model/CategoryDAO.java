package com.week2.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CategoryDAO {

	/**
	 * MyBatis 설정 파일 경로
	 */
	private String resource = "SqlMapConfig.xml";

	/**
	 * 기본 생성자
	 */
	public CategoryDAO() {
	}

	/**
	 * 모든 카테고리 조회
	 * @return 카테고리 번호 및 이름이 담긴 List
	 */
	public List selectCategories() {
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		List<CategoryVO> categoryList = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			categoryList = session.selectList("mapper.category.selectCategories");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return categoryList;
	}
}
