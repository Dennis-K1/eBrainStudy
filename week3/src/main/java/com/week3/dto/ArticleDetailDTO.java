package com.week3.dto;

import com.week3.vo.ArticleVO;
import com.week3.vo.CommentVO;
import java.util.List;
import lombok.Builder;
import lombok.Getter;


/**
 * ArticleDetail 페이지에 표시할 데이터 객체 모음
 */
@Getter
@Builder
public class ArticleDetailDTO {

	/**
	 * 대상 게시글 객체
	 */
	private ArticleVO articleVO;

	/**
	 * 대상 게시글 댓글 목록
	 */
	private List<CommentVO> commentList;
}
