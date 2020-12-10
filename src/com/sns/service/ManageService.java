package com.sns.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sns.dao.ManageDAO;
import com.sns.dto.ManageDTO;

public class ManageService {

	


	public void profiledelete(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		dao.profiledelete(req);
	}
	public void mainPhotodelete(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		dao.mainPhotodelete(req);
	}
	
	public void bgmdelete(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		dao.bgmdelete(req);
	}

	
	
	
	public ManageDTO profilePhoto(HttpServletRequest req) {//프로필 사진 파일 업로드
		//크기 지정
		int maxSize = 10*1024*1024;
		
		//저장 경로
		String root = "C:/";
		String uploadPath = root +"upload";
		System.out.println("저장 경로 " + uploadPath);
		//upload디렉토리 없으면 생성
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			System.out.println("해당 폴더가 없음, 생성");
			dir.mkdir();
		}

		String newFileName =null;
		ManageDTO dto = new ManageDTO();
		try {
			MultipartRequest multi
			= new MultipartRequest(req, uploadPath, maxSize, "UTF-8");
			
			//dto를 이용한 게시물 정보 저장
			dto.setId((String) req.getSession().getAttribute("id"));
			
			//5. 이름 변경
			String oriFileName = multi.getFilesystemName("profile");//원래 파일명 가져오기
			System.out.println("원래 파일 이름 : " + oriFileName);//확장자명을 항상 붙여줘야 본연의 모습을 잃지 않기때문에 확장자명 추출한다.
			newFileName = dto.getId() + "profilephoto" + ".jpg";;//파일명 새로 생성 + 확장자 붙이기
			System.out.println(oriFileName + " -> " + newFileName);
			
			//새 파일명으로 변경
			File oldName = new File(uploadPath + "/" + oriFileName);
			File newName = new File(uploadPath + "/" + newFileName);
			oldName.renameTo(newName);
			
			dto.setOriFileName(oriFileName);
			dto.setNewFileName(newFileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}	
	
	public ManageDTO MainPhoto(HttpServletRequest req) {//메인 사진 파일 업로드
		//크기 지정
		int maxSize = 10*1024*1024;
		
		//저장 경로
		String root = "C:/";
		String uploadPath = root +"upload";
		System.out.println("저장 경로 " + uploadPath);
		//upload디렉토리 없으면 생성
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			System.out.println("해당 폴더가 없음, 생성");
			dir.mkdir();
		}

		String newFileName =null;
		ManageDTO dto = new ManageDTO();
		try {
			MultipartRequest multi
			= new MultipartRequest(req, uploadPath, maxSize, "UTF-8");
			
			//dto를 이용한 게시물 정보 저장
			dto.setId((String) req.getSession().getAttribute("id"));
			
			//5. 이름 변경
			String oriFileName = multi.getFilesystemName("mainPhoto");//원래 파일명 가져오기
			System.out.println("원래 파일 이름 : " + oriFileName);//확장자명을 항상 붙여줘야 본연의 모습을 잃지 않기때문에 확장자명 추출한다.
			String ext = oriFileName.substring(oriFileName.lastIndexOf("."));//확장자만 추출(substring, split)
			System.out.println("확장자 : " + ext);
			
			newFileName = System.currentTimeMillis() + ext;;//파일명 새로 생성 + 확장자 붙이기
			System.out.println(oriFileName + " -> " + newFileName);
			
			//새 파일명으로 변경
			File oldName = new File(uploadPath + "/" + oriFileName);
			File newName = new File(uploadPath + "/" + newFileName);
			oldName.renameTo(newName);
			
			dto.setOriFileName(oriFileName);
			dto.setNewFileName(newFileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}	
	
	public ManageDTO bgm(HttpServletRequest req) {//bgm 파일 업로드
		//크기 지정
		int maxSize = 10*1024*1024;
		
		//저장 경로
		String root = "C:/";
		String uploadPath = root +"upload";
		System.out.println("저장 경로 " + uploadPath);
		//upload디렉토리 없으면 생성
		File dir = new File(uploadPath);
		if (!dir.exists()) {
			System.out.println("해당 폴더가 없음, 생성");
			dir.mkdir();
		}

		String newFileName =null;
		ManageDTO dto = new ManageDTO();
		try {
			MultipartRequest multi
			= new MultipartRequest(req, uploadPath, maxSize, "UTF-8");
			
			//dto를 이용한 게시물 정보 저장
			dto.setId((String) req.getSession().getAttribute("id"));
			
			//5. 이름 변경
			String oriFileName = multi.getFilesystemName("BGM");//원래 파일명 가져오기
			System.out.println("원래 파일 이름 : " + oriFileName);//확장자명을 항상 붙여줘야 본연의 모습을 잃지 않기때문에 확장자명 추출한다.
			String ext = oriFileName.substring(oriFileName.lastIndexOf("."));//확장자만 추출(substring, split)
			System.out.println("확장자 : " + ext);
			
			newFileName = System.currentTimeMillis() + ext;;//파일명 새로 생성 + 확장자 붙이기
			System.out.println(oriFileName + " -> " + newFileName);
			
			//새 파일명으로 변경
			File oldName = new File(uploadPath + "/" + oriFileName);
			File newName = new File(uploadPath + "/" + newFileName);
			oldName.renameTo(newName);
			
			dto.setOriFileName(oriFileName);
			dto.setNewFileName(newFileName);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dto;
	}	
	
	
	public boolean profileUpload(HttpServletRequest req, ManageDTO dto) {//프로필 내용 db에 저장
		ManageDAO dao = new ManageDAO();
		boolean success = dao.profileUpload(req,dto);
		return success;
	}
	
	public boolean mainPhotoUpload(HttpServletRequest req, ManageDTO dto) {//프로필 내용 db에 저장
		ManageDAO dao = new ManageDAO();
		boolean success = dao.mainPhotoUpload(req,dto);
		return success;
	}
	public boolean bgmUpload(HttpServletRequest req, ManageDTO dto) {//프로필 내용 db에 저장
		ManageDAO dao = new ManageDAO();
		boolean success = dao.bgmUpload(req,dto);
		return success;
	}
	
	//데이터 값 있는지 없는지 확인하는 메서드
	public boolean profilecheck(HttpServletRequest req) {
		ManageDAO dao =  new ManageDAO();
		return dao.profilecheck(req);
		
	}
	public boolean mainPhotocheck(HttpServletRequest req) {
		ManageDAO dao =  new ManageDAO();
		return  dao.mainPhotocheck(req);
	}
	public boolean bgmcheck(HttpServletRequest req) {
		ManageDAO dao =  new ManageDAO();
		return  dao.bgmcheck(req);
	}
	
	
	//select
	public HashMap<String, Object> profileselect(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		return dao.profileselect(req);
	}
	
	public HashMap<String, Object> mainPhotoselect(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		return dao.mainPhotoselect(req);
	}
	
	public HashMap<String, Object> bgmselect(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		return dao.bgmselect(req);
	}


	public boolean bgUpload(HttpServletRequest req) {
		ManageDAO dao = new ManageDAO();
		return dao.bgUpload(req);
		
	}


	
	
	
		
}
	
	

