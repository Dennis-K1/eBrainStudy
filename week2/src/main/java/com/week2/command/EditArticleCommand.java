package com.week2.command;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EditArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;
	public String execute() throws ServletException, IOException {
		//password validation
		String password = request.getParameter("password");

		//set article attribute
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		request.setAttribute("articleId",articleId);

		return "articleEditForm.jsp";
	}
}
