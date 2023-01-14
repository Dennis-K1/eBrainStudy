package com.week4.repository;

import com.week4.vo.FileVO;
import java.util.List;
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

	/**
	 * 대상 게시글 파일 조회
	 * @param articleId 조회 대상 게시글 번호
	 * @return 수행 결과
	 */
	public List<FileVO> getArticleFileList (int articleId){
		return sqlSession.selectList("mapper.file.selectArticleFiles", articleId);
	}

	/**
	 * 파일 DB에 삽입
	 * @param fileVO 파일 정보 객체
	 * @return 수행 결과
	 */
	public int insertFile(FileVO fileVO){
		return sqlSession.insert("mapper.file.insertFile", fileVO);
	}

	/**
	 * 대상 게시글에 첨부된 파일 갯수 확인
	 * @param articleId 대상 게시글 번호
	 * @return 파일 갯수
	 */
	public int countArticleFiles(int articleId) {
		return sqlSession.selectOne("mapper.file.countArticleFiles", articleId);
	}

	/**
	 * 파일 삭제
	 * @param fileId 대상 파일 번호
	 */
	public int deleteFile(int fileId) {
		return sqlSession.delete("mapper.file.deleteFile", fileId);
	}

	public FileVO getFile(Integer fileId) {
		return sqlSession.selectOne("mapper.file.selectFile", fileId);
	}
}
