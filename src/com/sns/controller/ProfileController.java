package com.sns.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.ProfileService;

@WebServlet({"/profileDetail","/profileUpdateForm","/profileUpdate"})
public class ProfileController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String uri=req.getRequestURI();
		String path=req.getContextPath();
		String reqAddr=uri.substring(path.length());
		
		ProfileService service=new ProfileService(req,resp);
		
		switch(reqAddr) {
			case "/profileDetail":
				service.profileDetail();
				break;
				
			case "/profileUpdateForm":
				service.profileUpdateForm();
				break;
				
			case "/profileUpdate":
				service.profileUpdate();
				break;
		}
	}

}
