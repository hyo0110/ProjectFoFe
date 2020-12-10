package com.sns.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.ManageDAO;
import com.sns.dto.ManageDTO;
import com.sns.service.ManageService;

@WebServlet({"/profileUpload","/mainPhotoUpload","/BgmUpload","/backgrounduplolad"})

public class ManageController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String url = uri.substring(ctx.length());
		RequestDispatcher dis = null;
		req.getSession().setAttribute("loginId", "tester");
		req.setCharacterEncoding("UTF-8");
		
		ManageService service = new ManageService();
		String msg = "저장 실패";
		String page = "manage.jsp";
		
		switch (url) {
		case "/profileUpload":
			System.out.println("프로필 사진 업로드 요청");
			
			boolean result = service.profilecheck(req);//사진이 있는지 없는 확인
			if(result) {//사진이 있다면 파일밑 데이터 베이스에서 삭제
				HashMap<String, Object> map = service.profileselect(req);
				String filename = (String) map.get("filename");
				File file = new File("C:/upload/"+filename);
				
				if(file.exists()) {
					if(file.delete()){System.out.println("파일삭제 성공");
					}else{System.out.println("파일삭제 실패");}
					
				}else{System.out.println("파일이 존재하지 않습니다.");}
				service.profiledelete(req);
			}
			
			ManageDTO dto = service.profilePhoto(req);//프로필 사진 파일 업로드
			boolean success = service.profileUpload(req,dto);//프로필 내용 db에 저장
			
			if(success) {
				msg = "저장 성공";
			}
			System.out.println(msg);
			req.setAttribute("newfilename", dto.getNewFileName());
			req.setAttribute("msg", msg);
			dis=req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
			break;
			
		case "/mainPhotoUpload":
			System.out.println("메인 사진 업로드 요청");
			
			result = service.mainPhotocheck(req);//사진이 있는지 없는 확인
			if(result) {//사진이 있다면 파일밑 데이터 베이스에서 삭제
				HashMap<String, Object> map = service.mainPhotoselect(req);
				String filename = (String) map.get("filename");
				File file = new File("C:/upload/"+filename);
				
				if(file.exists()) {
					if(file.delete()){System.out.println("파일삭제 성공");
					}else{System.out.println("파일삭제 실패");}
					
				}else{System.out.println("파일이 존재하지 않습니다.");}
				service.mainPhotodelete(req);
			}
			
			dto = service.MainPhoto(req);//프로필 사진 파일 업로드
			success = service.mainPhotoUpload(req,dto);//프로필 내용 db에 저장
			
			if(success) {
				msg = "저장 성공";
			}
			System.out.println(msg);
			req.setAttribute("newfilename", dto.getNewFileName());
			req.setAttribute("msg", msg);
			dis=req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
			break;
			
		case "/BgmUpload":
			System.out.println("BGM 업로드 요청");
			
			result = service.bgmcheck(req);//사진이 있는지 없는 확인
			if(result) {//사진이 있다면 파일밑 데이터 베이스에서 삭제
				HashMap<String, Object> map = service.bgmselect(req);
				String filename = (String) map.get("filename");
				File file = new File("C:/upload/"+filename);
				
				if(file.exists()) {
					if(file.delete()){System.out.println("파일삭제 성공");
					}else{System.out.println("파일삭제 실패");}
					
				}else{System.out.println("파일이 존재하지 않습니다.");}
				service.bgmdelete(req);
			}
			
			dto = service.bgm(req);//프로필 사진 파일 업로드
			success = service.bgmUpload(req,dto);//프로필 내용 db에 저장
			
			if(success) {
				msg = "저장 성공";
			}
			System.out.println(msg);
			req.setAttribute("newfilename", dto.getNewFileName());
			req.setAttribute("msg", msg);
			dis=req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
			break;
			
		case "/backgrounduplolad":
			System.out.println("배경색 바꾸기 요청");
			System.out.println(req.getParameter("background"));
			if(service.bgUpload(req)) {
				msg = "저장 성공";
				System.out.println(msg);
			}
			req.setAttribute("msg", msg);
			dis=req.getRequestDispatcher(page);
			dis.forward(req, resp);			
			break;
		
		}
		
		
	}
			
	
	
		
}

	
	
	
