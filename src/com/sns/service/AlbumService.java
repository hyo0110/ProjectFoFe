package com.sns.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.sns.dao.AlbumDAO;
import com.sns.dto.AlbumDTO;
import com.sns.dto.ReplyDTO;

public class AlbumService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	
	public AlbumService(HttpServletRequest req,HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	/* 사진 업로드 */
	public AlbumDTO upload() {
		System.out.println("upload 진입");
		int maxSize = 10*1024*1024;//사진 용량 제한
		
		String root = "C:/albumupload";
		String newFileName = "";
		System.out.println(root);
		System.out.println(System.getProperty("user.dir"));
		File dir = new File(root);
		if(!dir.exists()) {
			System.out.println("해당 폴더가 없음, 생성");
			dir.mkdir();
		}
		
		AlbumDTO dto = new AlbumDTO();
		try {

			//파일 업로드할 때 form action
			//MultipartRequest 는 예외처리가 필요해서 try=catch문을 사용함.
			MultipartRequest multi = new MultipartRequest(req, root, maxSize, "utf-8");
			
			//dto를 이용하여 게시물 정보 저장
			dto.setId((String) req.getSession().getAttribute("id"));
			dto.setAlbumcontent(multi.getParameter("content"));
			String ext = null;
			String oriFileName = multi.getFilesystemName("uploadFile");
			if(oriFileName == null) {
				oriFileName = "noneimage.png";
				newFileName = "noneimage.png";
			}else {
				System.out.println("원본 파일 이름 : " + oriFileName);
				ext = oriFileName.substring(oriFileName.lastIndexOf("."));
				System.out.println("확장자 확인" + ext);
				newFileName = System.currentTimeMillis() + ext;
				System.out.println(oriFileName +"- > " + newFileName);
			}
			
			File oldName = new File(root+"/"+oriFileName);
			File newName = new File(root+"/"+newFileName);
			oldName.renameTo(newName);
			dto.setAlbumOriFileName(oriFileName);
			System.out.println(dto.getAlbumOriFileName());
			dto.setAlbumNewFileName(newFileName);
			System.out.println(dto.getAlbumNewFileName());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return dto;
	}


	public void list() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String spage = req.getParameter("page");//현재 페이지
		System.out.println("받아온 값 : "+spage);
		int page = Integer.parseInt(spage);
		System.out.println("변환값 : " + page);
		String id = req.getParameter("homephost");
		System.out.println("앨범 아이디:"+id);
		Gson gson = new Gson();
		ArrayList<AlbumDTO> list = null;
		AlbumDAO dao = new AlbumDAO();
		int allcnt = dao.listcnt(id);
		System.out.println("게시물 갯수 : "+allcnt);
		dao = new AlbumDAO();
		list = dao.list(page, id);
		
		int pagecnt = 5;
		int startpage = 0;
		int endpage=0;
		
		if(page%pagecnt == 1) {
			startpage = page;
			endpage = startpage + 4;
		}else if (page % pagecnt == 0) {
			endpage = page;
			startpage = page-4;
		}
		
		if(page > allcnt) {
			endpage = allcnt;
		}
		
		map.put("list", list);
		map.put("allcnt", allcnt);//총페이지수
		map.put("curpage", page);
		String obj = gson.toJson(map);
		System.out.println(obj);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(obj);
	}

	public void detail() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<AlbumDTO> list = new ArrayList<AlbumDTO>();
		System.out.println("받아온 idx : "+req.getParameter("albumidx"));
		int albumidx = Integer.parseInt(req.getParameter("albumidx"));
		System.out.println(albumidx);
		AlbumDAO dao = new AlbumDAO();
		list = dao.detail(albumidx);
		map.put("list",list);
		Gson gson = new Gson();
		String obj = gson.toJson(map);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(obj);
		
	}
	
	
	/* 댓글 작성 */
	public ReplyDTO reply() {
		ReplyDTO dto = new ReplyDTO();
		try {
			//dto를 이용하여 댓글 정보 저장
			dto.setAlbumidx(Integer.parseInt(req.getParameter("albumIdx")));
			System.out.println("넘겨받은 albumidx : "+Integer.parseInt(req.getParameter("albumIdx")));
			dto.setReplyLevel(req.getParameter("replyLevel"));
			dto.setReplyRef(req.getParameter("replyRef"));
			dto.setReplyCont(req.getParameter("replyCont"));
			System.out.println("넘겨받은 content : "+req.getParameter("replyCont"));
			// 테스트용 : dto.setReplyUser_id("dbckdgur12");
			dto.setReplyUser_id((String) req.getSession().getAttribute("id"));
			
			System.out.println(dto);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return dto;
	}

	public boolean del() {
		AlbumDAO dao = new AlbumDAO();
		int albumidx = Integer.parseInt(req.getParameter("albumidx"));
		System.out.println("detail에서 받아온 idx 값 : "+ albumidx);
		String newfilename = dao.getfilename(albumidx);
		System.out.println(newfilename);
		dao = new AlbumDAO();
		int replycom = dao.albumreplydel(albumidx);
		int albumfilecom = 0;
		albumfilecom = dao.albumfiledel(albumidx);
		if(!newfilename.equals("noneimage.png")) {
			boolean filedel = false;
			String path = "C:/albumupload";
			File file = new File(path+newfilename);
			if(file.exists()) {
				filedel = file.delete();
				System.out.println(filedel);
			}
		}
		int albumcom = dao.albumdel(albumidx);
		boolean delsuccess = false;
		
		if(replycom >=0 && albumcom>0 && albumfilecom>0) {
			delsuccess = true;
		}
		return delsuccess;
	}

	
	
	
	
}
