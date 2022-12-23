package com.week2.model;

import java.io.IOException;
import java.io.InputStream;
import lombok.NoArgsConstructor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

@NoArgsConstructor
public class FileDAO {

	/**
	 * MyBatis 설정 파일 경로
	 */
	private String resource = "SqlMapConfig.xml";


	/**
	 * 게시글 등록시 첨부된 파일 정보 저장
	 * @param file 대상 게시글 번호, 파일명 및 경로가 저장된 객체
	 * @return 등록 결과 (성공 : 1, 실패 : 0)
	 */
	public int insertFile(FileVO file){
		int result = 0;
		SqlSessionFactory sqlSessionFactory;
		InputStream inputStream;
		SqlSession session = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			session = sqlSessionFactory.openSession();
			result = session.insert("mapper.file.insertFile", file);
			session.commit();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}
}
