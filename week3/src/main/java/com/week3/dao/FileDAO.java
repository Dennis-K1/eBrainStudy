package com.week3.dao;

import com.week3.vo.FileVO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FileDAO {

	/**
	 * 데이터베이스 연결 세션
	 */
	private final SqlSession SQL_SESSION;

	public int insertFile(FileVO fileVO){
		return SQL_SESSION.insert("mapper.file.insertFile", fileVO);
	}

	public int countArticleFiles(int articleId) {
		return SQL_SESSION.selectOne("mapper.file.countArticleFiles", articleId);

	}
}
