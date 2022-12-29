package com.week3.service;

import com.week3.dao.CommentDAO;
import com.week3.vo.CommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentInsertService {

	/**
	 * COMMENT_DAO		- 대상 게시글 댓글 테이블 접근을 위한 @Repository
	 */
	private final CommentDAO COMMENT_DAO;

	public int service(CommentVO commentVO) {
		return COMMENT_DAO.insertComment(commentVO);
	}
}
