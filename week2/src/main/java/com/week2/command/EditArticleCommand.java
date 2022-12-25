package com.week2.command;

import static com.week2.support.Validation.isPasswordCorrect;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditArticleCommand implements Command {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/**
		 * 비밀번호가 일치하지 않을 경우 이전 페이지 반환
		 * 일치할 경우 수정 페이지 반환
		 */
		String password = request.getParameter("password");
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		request.setAttribute("articleId",articleId);

		if (!isPasswordCorrect(password, articleId)) {
			response.sendRedirect(request.getHeader("referer") + "?error=passwordError");
		}
		return "articleEditForm.jsp";
	}

}
