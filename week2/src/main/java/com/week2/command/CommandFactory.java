package com.week2.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommandFactory {

	/**
	 * 클라이언트 요청을 전달 받아 Command 및 요청에 기반하여 페이지 반환
	 * @param request 클라이언트 요청 및 경로 정보
	 * @param response
	 * @return	경로명 or 대상 jsp 파일명
	 */
	public static Command createCommand(HttpServletRequest request, HttpServletResponse response) {
		Command command = null;

		/**
		 * 경로로 전달 받는 클라이언트 Command
		 */
		String userCommand = request.getServletPath();

		/**
		 * /				- 전체 게시글 목록 반환
		 * articleList		- 검색값 기반 게시글 목록 반환
		 * articleDetail	- 조회 대상 게시글 상세 페이지 반환
		 * articleInput		- 게시글 등록 Form 반환
		 * articleInsert	- 게시글 등록 Form 내용 DB에 반영 후 게시글 목록 반환
		 * articleEdit		- 게시글 수정 Form 반환
		 * articleUpdate	- 게시글 수정 Form 내용 DB에 반영 후 게시글 상세 페이지 반환
		 * articleDelete	- 삭제 대상 게시글 삭제 후 게시글 목록 반환
		 * insertComment	- 댓글 등록 내용 DB에 반영후 해당 대상 게시글 반환
		 */
		if (userCommand.equals("/") || userCommand.equals("/articleList")) {
			command = new SelectArticlesCommand(request, response);
		} else if (userCommand.equals("/articleDetail")) {
			command = new SelectArticleCommand(request, response);
		} else if (userCommand.equals("/articleInput")) {
			command = new InputArticleCommand(request, response);
		} else if (userCommand.equals("/articleInsert")) {
			command = new InsertArticleCommand(request, response);
		} else if (userCommand.equals("/articleEdit")) {
			command = new EditArticleCommand(request, response);
		} else if (userCommand.equals("/articleUpdate")) {
			command = new UpdateArticleCommand(request, response);
		} else if (userCommand.equals("/articleDelete")) {
			command = new DeleteArticleCommand(request, response);
		} else if (userCommand.equals("/insertComment")) {
 			command = new InsertCommentCommand(request, response);
		}
		return command;
	}
}
