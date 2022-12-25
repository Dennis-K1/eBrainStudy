package com.week2.controller;

import com.week2.command.Command;
import com.week2.command.CommandFactory;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/")
public class Controller extends HttpServlet {

	/**
	 * request 경로 정보에 맞는 커맨드 가져온 후 execute
	 * @param request 클라이언트 요청 정보
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		/**
		 * userCommand  요청 경로에 담긴 클라이언트 요청 command 정보
		 * command 		클라이언트 userCommand 에 맞는 command 객체
		 * viewPage		request 객체 정보를 이용하여 command execute 를 한 후 돌려받는 반환 페이지 정보
		 */
		String userCommand = request.getServletPath();

		Command command = CommandFactory.createCommand(userCommand);

		String viewPage = command.execute(request,response);

		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		processRequest(request, response);
	}
}
