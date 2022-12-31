package com.week3.repository;

import com.week3.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileRepository {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession sqlSession;

	public int insertFile(FileVO fileVO){
		return sqlSession.insert("mapper.file.insertFile", fileVO);
	}

	public int countArticleFiles(int articleId) {
		return sqlSession.selectOne("mapper.file.countArticleFiles", articleId);

	}
}
