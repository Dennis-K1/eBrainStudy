package com.week2.command;

import java.util.HashMap;

public abstract class CommandFactory {

	/**
	 * 클라이언트 요청을 전달 받아 Command 및 요청에 기반하여 페이지 반환
	 * @param userCommand 클라이언트 요청 Command 명
	 * @return	Command 객체
	 */
	public static Command createCommand(String userCommand) {


		HashMap<String,Command> userCommandMap = createUserCommandMap();

		Command command = userCommandMap.get(userCommand);

		return command;
	}

	/**
	 * userCommand 이름 및 Command 객체 모음
	 * @return
	 */
	private static HashMap<String,Command> createUserCommandMap() {

		HashMap<String,Command> userCommandMap = new HashMap<>();
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
		userCommandMap.put("/", new SelectArticlesCommand());
		userCommandMap.put("/articleList", new SelectArticlesCommand());
		userCommandMap.put("/articleDetail", new SelectArticleCommand());
		userCommandMap.put("/articleInput", new InputArticleCommand());
		userCommandMap.put("/articleInsert", new InsertArticleCommand());
		userCommandMap.put("/articleEdit", new EditArticleCommand());
		userCommandMap.put("/articleUpdate", new UpdateArticleCommand());
		userCommandMap.put("/articleDelete", new DeleteArticleCommand());
		userCommandMap.put("/insertComment", new InsertCommentCommand());

		return userCommandMap;
	}
}
