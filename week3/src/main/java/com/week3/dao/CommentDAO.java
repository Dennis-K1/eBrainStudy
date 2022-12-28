package com.week3.dao;

import com.week3.vo.CommentVO;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDAO {

	@Autowired
	SqlSession sqlSession;

	public List selectComments(int articleId) {
		return sqlSession.selectList("mapper.comment.selectComments", articleId);
	}

	public int insertComment(CommentVO comment){
		return sqlSession.insert("mapper.comment.insertComment", comment);
	}
}
