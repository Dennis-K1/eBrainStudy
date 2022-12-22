package com.week2.command;

import com.week2.model.CategoryDAO;
import com.week2.model.CategoryVO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InputArticleCommand implements Command {

	private HttpServletRequest request;
	private HttpServletResponse response;
	public String execute() throws ServletException, IOException {

		CategoryDAO categoryDAO = new CategoryDAO();
		List<CategoryVO> categoryList = categoryDAO.selectCategories();

		request.setAttribute("categoryList",categoryList);
		return "articleInputForm.jsp";
	}
}
