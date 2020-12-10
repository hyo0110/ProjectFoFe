package com.sns.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sns.dao.FollowDAO;
import com.sns.dto.FollowerTalkDTO;
import com.sns.dto.SearchDTO;

public class FollowService {
	
	HttpServletRequest req = null;
	HttpServletResponse resp = null;

	public FollowService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void memberSearch() {
		String srchName = req.getParameter("srchName");
		System.out.println("검색 요청값 : "+srchName);
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		ArrayList<SearchDTO> arrList = null;
		FollowDAO dao = new FollowDAO();
		boolean result = true;
		try {
			arrList = dao.memberSearch(srchName);
			if(arrList.isEmpty()) {
				result=false;
			}
			map.put("result",result);
			map.put("arrList", arrList);
			String obj = gson.toJson(map);
			System.out.println(obj);
			resp.setContentType("text/html; charset=UTF-8"); 
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
		
		
	}

	public void follow() {
		String homephostId = req.getParameter("homephostId");
		String loginId = (String) req.getSession().getAttribute("id");
		System.out.println("로그인 된 아이디(팔로우 할 놈) : "+loginId);
		System.out.println("팔로우당할 아이디(홈피주인) : "+homephostId);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		FollowDAO dao = new FollowDAO();
		boolean result = false;
		
		try {
			if(loginId==null) {
				result = false;
			}else {
				result = dao.follow(homephostId,loginId);
			}
			
			map.put("result",result);
			String obj = gson.toJson(map);
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}

	public void unFollow() {
		String homephostId = req.getParameter("homephostId");
		String loginId = (String) req.getSession().getAttribute("id");
		System.out.println("로그인 된 아이디(언팔 할 놈) : "+loginId);
		System.out.println("언팔로우당할 아이디(홈피주인) : "+homephostId);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		FollowDAO dao = new FollowDAO();
		try {
			boolean result = dao.unfollow(homephostId,loginId);
			map.put("result",result);
			String obj = gson.toJson(map);
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}

	public void loadFollowList() {
		String loginId = req.getParameter("loginId");
		FollowDAO dao = new FollowDAO();
		
		ArrayList<SearchDTO> arrList = new ArrayList<SearchDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			arrList = dao.loadFollowList(loginId);
			map.put("arrList", arrList);
			String obj = gson.toJson(map);
			System.out.println(obj);
			resp.setContentType("text/html; charset=UTF-8"); 
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}

	public void loadFollowerList() {
		String loginId = req.getParameter("loginId");
		FollowDAO dao = new FollowDAO();
		
		ArrayList<SearchDTO> arrList = new ArrayList<SearchDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			arrList = dao.loadFollowerList(loginId);
			map.put("arrList", arrList);
			String obj = gson.toJson(map);
			System.out.println(obj);
			resp.setContentType("text/html; charset=UTF-8"); 
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}

	public void followerTalkWrite() throws UnsupportedEncodingException {
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("homephost");
		String content = req.getParameter("followerTalk");
		String followerTalkUser_name = (String) req.getSession().getAttribute("id");
		FollowDAO dao = new FollowDAO();
		if(followerTalkUser_name!=null) {
			try {
				if(content!="") {
					if(dao.followerTalkWrite(id,content,followerTalkUser_name)) {
						RequestDispatcher dis =  req.getRequestDispatcher("minihomeMain?homephost="+id);
						dis.forward(req, resp);
					}
				}else {
					req.setAttribute("msg", "내용을 입력해주세요");
					RequestDispatcher dis =  req.getRequestDispatcher("minihomeMain?homephost="+id);
					dis.forward(req, resp);
				}
				
			} catch (SQLException | ServletException | IOException e) {
				e.printStackTrace();
			}finally {
				dao.resClose();
			}
		}
		
	}

	public void getfollowerTalkList() {
		String Id = req.getParameter("homephostId");
		FollowDAO dao = new FollowDAO();
		ArrayList<FollowerTalkDTO> list = new ArrayList<FollowerTalkDTO>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		Gson gson = new Gson();
		
		try {
			list = dao.getfollowerTalkList(Id);
			map.put("list", list);
			String obj = gson.toJson(map);
			System.out.println(obj);
			resp.setContentType("text/html; charset=UTF-8"); 
			resp.getWriter().println(obj);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
		}
	}

	public void followerTalkDelete() throws ServletException, IOException {
		String idx = req.getParameter("followerTalkIdx");
		String homephostId = req.getParameter("homephostId");
		String deleteId = (String) req.getSession().getAttribute("id");
		boolean result = false;
		RequestDispatcher dis = null;
		if(deleteId!=null) {
			FollowDAO dao = new FollowDAO();
			result= dao.followerTalkDelete(idx, deleteId);
			if(result) {
				req.setAttribute("msg", "삭제 성공");
				dis=req.getRequestDispatcher("minihomeMain?homephost="+homephostId);
				dis.forward(req, resp);
			}else {
				req.setAttribute("msg", "작성자 혹은 미니홈피 주인만 삭제할 수 있습니다.");
				dis=req.getRequestDispatcher("minihomeMain?homephost="+homephostId);
				dis.forward(req, resp);
			}
		}else {
			req.setAttribute("msg", "작성자 혹은 미니홈피 주인만 삭제할 수 있습니다.");
			dis=req.getRequestDispatcher("minihomeMain?homephost="+homephostId);
			dis.forward(req, resp);
		}
		}



}
