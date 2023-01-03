package com.week4.repository;

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

	/**
	 * 대상 게시글 댓글 조회
	 * @param articleId 대상 게시글 번호
	 * @return 댓글 정보 객체가 담긴 리스트
	 */
	public List<CommentVO> selectAllComments(int articleId) {
		return sqlSession.selectList("mapper.comment.selectComments", articleId);
	}

	/**
	 * 댓글 삽입
	 * @param commentVO 댓글 정보 객체
	 * @return 수행 결과
	 */
	public int insertComment(CommentVO commentVO){
		return sqlSession.insert("mapper.comment.insertComment", commentVO);
	}
}
