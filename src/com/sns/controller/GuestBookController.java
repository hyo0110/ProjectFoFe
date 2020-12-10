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
import com.sns.dto.GuestBookDTO;
import com.sns.service.GuestBookService;

@WebServlet({"/guestBookList","/guestbookWrite", "/guestbookDelete", "/guestBookUpdateCheck", "/guestBookUpdate"})
public class GuestBookController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();//URI 가져오기 (URI는 URL상위 개념으로 해당파일의 위치를 알려줌. 여기서는 모든 디렉토리 나타남)
		String context = req.getContextPath();//context 가져오기
		String reqAddr = uri.substring(context.length());
		
		GuestBookService guestBookService = new GuestBookService();
		
		RequestDispatcher dis = null;
		
		switch (reqAddr) {
		case "/guestBookList":
			String homephost = req.getParameter("homephost");
			ArrayList<GuestBookDTO> list = new ArrayList<GuestBookDTO>();
			list = guestBookService.guestBookList(homephost);
			req.setAttribute("list", list);
			req.setAttribute("homephost", homephost);
			dis=req.getRequestDispatcher("GuestBookList.jsp");
			dis.forward(req, resp);
			break;

		case "/guestbookWrite":
			req.setCharacterEncoding("utf-8");
			homephost = req.getParameter("homephost");
			String guestbookWrightText = req.getParameter("GuestbookWright");
			String guest = (String) req.getSession().getAttribute("id");
			boolean result = guestBookService.guestbookWrite(homephost, guest, guestbookWrightText);
			if(result) {
				dis=req.getRequestDispatcher("guestBookList?homephost="+homephost);
				dis.forward(req, resp);
			}
			break;
		
		case "/guestbookDelete":
			String guestBookIdx = req.getParameter("guestBookIdx");
			homephost = req.getParameter("homephost");
			String deleteId = (String) req.getSession().getAttribute("id");
			if(deleteId!=null) {
				result = guestBookService.guestbookDelete(guestBookIdx, deleteId);
				if(result) {
					req.setAttribute("msg", "방명록 삭제 성공");
					dis=req.getRequestDispatcher("guestBookList?homephost="+homephost);
					dis.forward(req, resp);
				}else {
					req.setAttribute("msg", "방명록 작성자 혹은 미니홈피 주인만 삭제할 수 있습니다.");
					dis=req.getRequestDispatcher("guestBookList?homephost="+homephost);
					dis.forward(req, resp);
				}
			}else {
				req.setAttribute("msg", "방명록 작성자혹은 미니홈피 주인만 삭제할 수 있습니다.");
				dis=req.getRequestDispatcher("guestBookList?homephost="+homephost);
				dis.forward(req, resp);
			}
			System.out.println(guestBookIdx+"/"+homephost);
			
			break;
			
		case "/guestBookUpdateCheck":
			String guestBookUpId = req.getParameter("guestBookUpId");
			guestBookIdx = req.getParameter("guestBookIdx");
			String updateId = (String) req.getSession().getAttribute("id");
			String guestbookcontent ="";
			result = false;
			if(guestBookUpId.equals(updateId)) {
				result=true;
				guestbookcontent = guestBookService.guestBookUpdateCheck(guestBookIdx);
			}else {
				result=false;
			}
			HashMap<String, Object> map = new HashMap<String, Object>();
			Gson gson = new Gson();
			resp.setContentType("text/html; charset=UTF-8"); 
			map.put("guestbookcontent",guestbookcontent);
			map.put("result",result);
			String obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
			
		case "/guestBookUpdate":
			guestBookIdx = req.getParameter("guestBookIdx");
			String guestbookUpdateText = req.getParameter("guestbookUpdateText");
			System.out.println(guestBookIdx+"/"+guestbookUpdateText);
			result = guestBookService.guestBookUpdate(guestBookIdx, guestbookUpdateText);
			map = new HashMap<String, Object>();
			gson = new Gson();
			map.put("result",result);
			obj = gson.toJson(map);
			resp.getWriter().println(obj);
			break;
		}
				
	}

}
