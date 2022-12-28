package com.week3.dao;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDAO {

	@Autowired
	SqlSession sqlSession;

	public List selectCategories() {
		return sqlSession.selectList("mapper.category.selectCategories");
	}
}
