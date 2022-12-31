package com.week3.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession sqlSession;

	public List selectCategories() {
		return sqlSession.selectList("mapper.category.selectCategories");
	}
}
