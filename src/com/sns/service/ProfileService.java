package com.sns.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.ProfileDAO;
import com.sns.dto.ProfileDTO;

public class ProfileService {
	
	HttpServletRequest req=null;
	HttpServletResponse resp=null;
	public ProfileService(HttpServletRequest req, HttpServletResponse resp) {
		this.req=req;
		this.resp=resp;
	}

	//프로필 상세보기
	public void profileDetail() throws ServletException, IOException {
		String id= req.getParameter("homephost");
		System.out.println("디테일. 세션 아이디: "+id);
		ProfileDAO dao=new ProfileDAO();
		req.setAttribute("Profile", dao.profileDetail(id));
		RequestDispatcher rd=req.getRequestDispatcher("Profile.jsp");
		rd.forward(req, resp);
	}

	public void profileUpdateForm() throws ServletException, IOException {
		ProfileDAO dao=new ProfileDAO();
		String id=req.getParameter("id");
		System.out.println("업데이트폼. 아이디: "+id);
		ProfileDTO dto=dao.profileDetail(id);
		req.setAttribute("Profile", dto);
		RequestDispatcher rd=req.getRequestDispatcher("ProfileUpdate.jsp");
		rd.forward(req, resp);
	}

	public void profileUpdate() throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String id=req.getParameter("homephost");
		String nickname=req.getParameter("nickname");
		String myBirth=req.getParameter("myBirth");
		String blood=req.getParameter("blood");
		String addr=req.getParameter("addr");
		String major=req.getParameter("major");
		String seduWay=req.getParameter("seduWay");
		String motto=req.getParameter("motto");
		String fMovie=req.getParameter("fMovie");
		System.out.println("수정폼 입력값: 아이디="+id+", 영화="+fMovie+" 여기가 문제였네");
		
		ProfileDAO dao=new ProfileDAO();
		String page="profileDetail?homephost="+id;
		String msg="수정에 실패했습니다.";
		if(dao.profileUpdate(id, nickname, myBirth, blood, addr, major, seduWay, motto, fMovie)) {
			msg="수정에 성공했습니다.";
		}
		req.setAttribute("msg", msg);
		RequestDispatcher rd=req.getRequestDispatcher(page);
		rd.forward(req, resp);
	}

	

}
