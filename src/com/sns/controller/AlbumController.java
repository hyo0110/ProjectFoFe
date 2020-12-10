package com.sns.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.AlbumDAO;
import com.sns.dto.AlbumDTO;
import com.sns.dto.ReplyDTO;
import com.sns.service.AlbumService;


@WebServlet({"/albumupload","/albumlist","/albumdetail","/albumdel","/replyList","/albumReply","/AlbumReply","/replyDel","/replyUpdate", "/giveAlbumlist"})
public class AlbumController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req,resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}


	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String url = uri.substring(ctx.length());
		
		RequestDispatcher dis = null;//민식이형 스톼일. 컨트롤러에서
		req.getSession().setAttribute("loginId", "tester");//로그인 구현한게 아니여서 임의로 넣어준 것. 세션에 저장
		req.setCharacterEncoding("UTF-8");
		
		AlbumService service = new AlbumService(req, resp);
		AlbumDAO dao = null;
		
		String msg = "";
		String page = "";
		
		switch(url) {
			//사진 업로드
			case "/albumupload":
			String homeid = req.getParameter("homephost");
			System.out.println("홈피 아이디 :" +homeid);
			System.out.println("로그인아이디 : "+(String) req.getSession().getAttribute("id"));
			msg = "저장 실패";
			//String l ="?homephost=\"+homeid+\"&&page=1"
			if(homeid.equals((String) req.getSession().getAttribute("id"))) {
				page = "Albumlist.jsp";
				dao = new AlbumDAO();
				System.out.println("service upload 진입 전");
				AlbumDTO dto = service.upload();
				System.out.println("service upload 탈출후 dao write 진입 전");
				if(dao.write(dto)) {
					msg = "저장 성공";
				}
				/*req.setAttribute("msg", msg);
				dis=req.getRequestDispatcher(page);
				dis.forward(req, resp);*/
				req.setAttribute("homephost", homeid);
				req.setAttribute("page", page);
				dis = req.getRequestDispatcher(page);
				dis.forward(req, resp);
			}else {
				page ="Albumlist.jsp";
				msg = "홈피 주인만 작성 할 수 있습니다.";
				req.setAttribute("msg", msg);
				req.setAttribute("homephost", homeid);
				dis=req.getRequestDispatcher(page);
				dis.forward(req, resp);
			}
			
			break;
			
			case "/giveAlbumlist":
				String homephost = req.getParameter("homephost");
				req.setAttribute("homephost", homephost);
				dis = req.getRequestDispatcher("Albumlist.jsp");
				dis.forward(req, resp);
				break;
				
			case "/albumlist":
				service.list();
				break;
			
			case "/albumdetail":
				System.out.println("리스트 진입");
				service.detail();
				break;
			
			case "/albumdel":
				String id = (String) req.getSession().getAttribute("id");
				System.out.println("세션에 저장된 id : "+id);
				String homehost = req.getParameter("homephost");
				System.out.println("삭제 진입");
				boolean success = service.del();
				System.out.println("삭제후 보내는 homeid " + homehost);
				req.setAttribute("homephost", homehost);
				dis=req.getRequestDispatcher("Albumlist.jsp");
				dis.forward(req, resp);
				break;
				
			//사진첩 댓글달기
			case "/albumReply":
				System.out.println("albumReply 요청");
				dao = new AlbumDAO();
				msg = "저장 실패";
				page = "albumReply.jsp";
				ReplyDTO dto1 = service.reply();
				if(dto1 == null) {
					msg = "에러 발생";
				} else {
					if (dao.replyWrite(dto1)) {
						msg = "저장 성공";
					}
				}
				System.out.println("[댓글 작성 " + msg + "] " + dto1);
				
				// 댓글 보여줄 페이지에서 사용할 앨범 idx 정보 같이 넘겨줌
				req.setAttribute("albumIdx", dto1.getReplyIdx());
				req.setAttribute("msg", msg);
				dis=req.getRequestDispatcher(page);
				dis.forward(req, resp);
				break;
				
			//사진첩 댓글 불러오기
			case "/replyList":
				System.out.println("replyList 요청");
				page = "albumReply.jsp";
				dto1 = new ReplyDTO();
				dto1.setAlbumidx(Integer.parseInt(req.getParameter("albumIdx")));
				dao = new AlbumDAO();
				ArrayList<ReplyDTO> replyList = dao.replyList(dto1);
				System.out.println(replyList);
				req.setAttribute("replyList", replyList);
				
				// 댓글 보여줄 페이지에서 사용할 앨범 idx 정보 같이 넘겨줌
				req.setAttribute("albumIdx", dto1.getReplyIdx());
				req.setAttribute("msg", msg);
				dis=req.getRequestDispatcher(page);
				dis.forward(req, resp);
				break;
				
			//사진첩 댓글 삭제
			case "/replyDel":
				System.out.println("replyDel 요청");
				dao = new AlbumDAO();
				msg = "삭제 실패";
				page = "albumReply.jsp";
				dto1 = new ReplyDTO();
				dto1.setReplyIdx(Integer.parseInt(req.getParameter("replyIdx")));
				if (dao.replyDelete(dto1)) {
					msg = "삭제 성공";
				}
				
				req.setAttribute("msg", msg);
				dis=req.getRequestDispatcher(page);
				dis.forward(req, resp);
				break;
				
			//사진첩 댓글 수정
			case "/replyUpdate":
				System.out.println("replyDel 요청");
				msg = "수정 실패";
				page = "AlbumReply.jsp";
				break;
			}
	}
	
}
