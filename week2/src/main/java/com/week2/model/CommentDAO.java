package com.week2.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@NoArgsConstructor
public class CommentDAO {

	/**
	 * MyBatis 설정 파일 경로
	 */
	private String resource = "SqlMapConfig.xml";


	/**
	 * 특정 게시물의 전체 댓글 조회
	 * @param articleId 대상 게시물 번호
	 * @return 댓글 VO가 담긴 List
	 */
	public List selectComments(int articleId) {
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		List<CommentVO> commentList = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			commentList = session.selectList("mapper.comment.selectComments", articleId);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return commentList;
	}

	/**
	 * 특정 게시글에 댓글 등록
	 * @param comment 등록할 댓글 (객체 생성시 대상 게시글 번호 지정 필요)
	 * @return 등록 결과 ( 성공 : 1, 실패 : 0)
	 */
	public int insertComment(CommentVO comment){
		int result = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			result = session.insert("mapper.comment.insertComment", comment);
			session.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
