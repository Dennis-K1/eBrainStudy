package com.week2.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommandFactory {
	public static Command createCommand(HttpServletRequest request, HttpServletResponse response) {
		Command command = null;
		String userCommand = request.getParameter("command");

		if (userCommand == null) {
			command = new SelectArticlesCommand(request, response);
		}
		return command;
	}

}
