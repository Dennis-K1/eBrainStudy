package com.week2.controller;

import com.week2.command.Command;
import com.week2.command.CommandFactory;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/")
public class Controller extends HttpServlet {

	protected void processRequest (HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		Command command = CommandFactory.createCommand(request, response);

		String viewPage = command.execute();

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
