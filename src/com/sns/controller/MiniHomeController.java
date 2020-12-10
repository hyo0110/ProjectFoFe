package com.sns.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sns.dto.DiaryDTO;
import com.sns.dto.MiniHomeDTO;
import com.sns.service.MiniHomeService;

@WebServlet({"/loadMinihome", "/minihomeCheck" , "/followCheck", "/minihomeNameEdit", "/profileMessageEdit", "/getNewDiaryList", "/minihomeMain"})
public class MiniHomeController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String uri = req.getRequestURI();
		String context = req.getContextPath();
		String reqAddr = uri.substring(context.length());
		
		MiniHomeService miniHomeService = new MiniHomeService();
		
		RequestDispatcher dis = null;
		
		switch (reqAddr) {
		case "/loadMinihome":
			String id = req.getParameter("id");
			System.out.println("이동할 미니홈피 아이디 : "+id);
			MiniHomeDTO dto = null;
			dto = miniHomeService.loadMinihome(id);
			req.setAttribute("minihome", dto);
			dis = req.getRequestDispatcher("minihome.jsp");
			dis.forward(req, resp);
			break;
			
		case "/minihomeCheck":
			String homephostId = req.getParameter("homephostId");
			String loginId = (String) req.getSession().getAttribute("id");
			System.out.println("확인 할 미니홈피 주인아이디 : "+homephostId+"/접속 한 아이디 : "+loginId);
			boolean result = false;
			if(homephostId.equals(loginId)) {
				result=true;
			}else {
				result=false;
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			Gson gson = new Gson();
			map.put("result",result);
			String obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
			
		case "/followCheck":
			homephostId = req.getParameter("homephostId");
			loginId = (String) req.getSession().getAttribute("id");
			System.out.println("팔로우 체크 주인 : "+homephostId+" / 방문자 : "+loginId);
			result = miniHomeService.followCheck(homephostId, loginId);
			map = new HashMap<String, Object>();
			gson = new Gson();
			map.put("result",result);
			obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
			
		case "/minihomeNameEdit":
			homephostId = req.getParameter("homephostId");
			String minihome_nameEdit = req.getParameter("minihome_nameEdit");
			result = miniHomeService.minihomeNameEdit(homephostId,minihome_nameEdit);
			map = new HashMap<String, Object>();
			gson = new Gson();
			map.put("result",result);
			obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
		
		case "/profileMessageEdit":
			homephostId = req.getParameter("homephostId");
			String profile_messageEdit = req.getParameter("profile_messageEdit");
			result = miniHomeService.profileMessageEdit(homephostId,profile_messageEdit);
			map = new HashMap<String, Object>();
			gson = new Gson();
			map.put("result",result);
			obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
		case "/minihomeMain":
			id = req.getParameter("homephost");
			String mainphoto = miniHomeService.minihomeMain(id);
			req.setAttribute("mainphoto", mainphoto);
			req.setAttribute("homephostId", id);
			dis = req.getRequestDispatcher("minihomeMain.jsp");
			dis.forward(req, resp);
			break;
		case "/getNewDiaryList":
			homephostId = req.getParameter("homephostId");
			ArrayList<DiaryDTO> list = new ArrayList<DiaryDTO>();
			list = miniHomeService.getNewDiaryList(homephostId);
			map = new HashMap<String, Object>();
			gson = new Gson();
			map.put("list", list);
			obj = gson.toJson(map);
			resp.setContentType("text/html; charset=UTF-8");
			resp.getWriter().println(obj);
			break;
		};
	
	
	}
}
	
