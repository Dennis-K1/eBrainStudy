package com.week3.repository;

import com.week3.vo.CommentVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession sqlSession;

	public List selectAllComments(int articleId) {
		return sqlSession.selectList("mapper.comment.selectComments", articleId);
	}

	public int insertComment(CommentVO commentVO){
		return sqlSession.insert("mapper.comment.insertComment", commentVO);
	}
}
