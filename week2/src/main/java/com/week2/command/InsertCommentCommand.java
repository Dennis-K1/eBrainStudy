package com.week2.command;

import com.week2.model.CommentDAO;
import com.week2.model.CommentVO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertCommentCommand implements Command {

	public String execute(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		/*
		request 정보 기반 댓글 객체 생성
		 */
		CommentDAO commentDAO = new CommentDAO();
		CommentVO comment = createCommentVO(request);

		/*
		댓글 insert 후 결과 반환
		 */
		int result = commentDAO.insertComment(comment);

		request.setAttribute("articleId", comment.getArticleId());

		if (result != 1) {
			return "commentInsertError";
		}
		return "commentInserted.jsp";
	}

	/**
	 * 댓글 객체 생성 함수
	 * @param request 클라이언트 요청 정보 (게시글 번호, 댓글 내용)
	 * @return 댓글 객체
	 */
	private CommentVO createCommentVO(HttpServletRequest request) {

		int articleId = Integer.parseInt(request.getParameter("articleId"));
		String content = request.getParameter("content");

		CommentVO comment = CommentVO.builder()
			.articleId(articleId)
			.content(content)
			.build();

		return comment;
	}
}
