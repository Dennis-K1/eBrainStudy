package com.week3.service;

import com.week3.repository.CommentRepository;
import com.week3.vo.CommentVO;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시물 댓글 등록을 위한 @Service
 */
@Service
@RequiredArgsConstructor
public class CommentInsertService {

	/**
	 * commentRepository		- 대상 게시글 댓글 테이블 접근을 위한 @Repository
	 */
	private final CommentRepository commentRepository;

	/**
	 * 댓글 저장후 결과 반환
	 * @param commentVO 댓글 객체
	 * @return 수행 결과
	 */
	@Transactional
	public int service(CommentVO commentVO) throws IOException {
		int result  = commentRepository.insertComment(commentVO);
		if (result == 1) {
			return commentVO.getArticleId();
		} else {
			throw new IOException();
		}
	}
}
