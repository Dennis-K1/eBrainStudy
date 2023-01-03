package com.week4.repository;

import com.week3.vo.CategoryVO;
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

	/**
	 * 전체 카테고리 정보 반환
	 * @return 카테고리 객체가 담긴 리스트
	 */
	public List<CategoryVO> selectCategories() {
		return sqlSession.selectList("mapper.category.selectCategories");
	}
}
