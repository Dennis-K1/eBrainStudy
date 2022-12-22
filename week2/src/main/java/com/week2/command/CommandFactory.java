package com.week2.command;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommandFactory {
	public static Command createCommand(HttpServletRequest request, HttpServletResponse response) {
		Command command = null;
		/*
		커맨드를 파라미터가 아닌 경로로 전달
		 */
		String userCommand = request.getServletPath();

		if (userCommand.equals("/") || userCommand.equals("/articleList")) {
			command = new SelectArticlesCommand(request, response);
		} else if (userCommand.equals("/articleDetail")) {
			command = new SelectArticleCommand(request, response);
		} else if (userCommand.equals("/articleInput")) {
			command = new InputArticleCommand(request, response);
		}else if (userCommand.equals("/articleInsert")) {
			command = new InsertArticleCommand(request, response);
		} else if (userCommand.equals("/articleEdit")) {
			command = new EditArticleCommand(request, response);
		} else if (userCommand.equals("/articleUpdate")) {
			command = new UpdateArticleCommand(request, response);
		} else if (userCommand.equals("/insertComment")) {
 			command = new InsertCommentCommand(request, response);
		}
		return command;
	}

}
