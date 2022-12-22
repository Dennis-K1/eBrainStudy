package com.week2.command;

import com.week2.model.CommentDAO;
import com.week2.model.CommentVO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsertCommentCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;
	public String execute() throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		CommentDAO commentDAO = new CommentDAO();
		int result = 0;
		int articleId = Integer.parseInt(request.getParameter("articleId"));
		String content = request.getParameter("content");

		//content validation

		CommentVO comment = CommentVO.builder()
			.articleId(articleId)
			.content(content)
			.build();

		result = commentDAO.insertComment(comment);
		request.setAttribute("articleId",articleId);

		if (result == 1){
			return "commentInserted.jsp";
		} else {
			return "commentNotInserted.jsp";
		}
	}
}
