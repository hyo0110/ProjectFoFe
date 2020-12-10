package com.sns.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.dao.DiaryDAO;
import com.sns.dto.DiaryDTO;



   
public class DiaryService {

	HttpServletRequest req = null;
	HttpServletResponse resp = null;
	
	
	public DiaryService(HttpServletRequest req,HttpServletResponse resp) {
		this.req= req; //이거랑 저거랑 연결시켜준다는 의미로 보자//컨트롤러는 서비스한테 일을 시켜야하는데 컨트롤러에서만 상속을 받았기때문에 
		this.resp= resp;
	}
	DiaryDAO dao = null;//dao객체화,전역변수
	//다이어리 쓰기 
	public void write() throws Exception {
		
		//서비스는 DB를 사용할지 생각한다.
		//다이어리는 파라미터를 입력받아서 DB에 넣어야하기때문에 DB를 연결해야한다.
		dao = new DiaryDAO();
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("homephost");
		String subject = req.getParameter("subject");//넣는애들만 넣을거고 sql에서 자동으로 들어가는 부분따로 그리고 걔네 다 채워서 나중에 글에 다 띄워준다.
		String content = req.getParameter("content");
		String page = "/diaryList";//성공해도 실패해도 여기로간다.
		String msg ="게시글 작성에 실패하였습니다.";
		if(dao.write(id, subject,content)) {//1.dao에게 일시키기//2. 받아온값이 true면 
			
			msg="게시글작성에 성공하였습니다.";
		}
		
		req.setAttribute("msg", msg);//msg변수에 msg내용을 실어 보낸다.
		RequestDispatcher dis =  req.getRequestDispatcher(page);
		dis.forward(req, resp);//dis에게 담아보낸다.
	}
	
	//페이징과 리스트
	public void list() throws ServletException, IOException {
		//DB를 사용할지
		//데이터를 넣는게아니라 불러오는 부분이라 파라미터를 보낼 필요가 없다.
		String pageParam = req.getParameter("page");//현재페이지
		System.out.println(pageParam);
		String msg = req.getParameter("msg");
		
		int page = 1;//페이지초기화
		if(pageParam !=null) {//현재페이지가있다면
			page = Integer.parseInt(pageParam);//페이지에 현재 페이지를 넣어라
			if(page==0) {
				page=page+1;
			}
		}
		
		String homephost = req.getParameter("homephost");//homephost는 어디서온애였더라?★
		System.out.println("homehost : " + homephost);
		dao = new DiaryDAO();
		ArrayList<DiaryDTO> list = dao.list(homephost,page);//1.db에 일 시키기 2. dao에서 컨트롤러로 보낸 (dto)list 받기
		dao = new DiaryDAO();
		int count = dao.limit(homephost);
		if(msg != null) {
			req.setAttribute("msg", msg);
		}
		req.setAttribute("count", count);
		req.setAttribute("list", list);//변수 list에 받은 list값을 넣어준다.
		req.setAttribute("homephost", homephost);//??★
		req.setAttribute("currPage", page);//현재 페이지를 넣어줄거다.
		RequestDispatcher dis = req.getRequestDispatcher("diarylist.jsp");//어딜 목표로 갈건지. dispatcher은 항해자라는 뜻  
		dis.forward(req, resp);//list라는 정보를 붙여서 보냈다. 그럼이제 그걸 띄워줘야함.
	}
	
	//글 상세보기
	public void detail() throws ServletException, IOException {
		//DB에서 값을 가져온다. 
		//1.db가 필요한 일인지 생각해본다.2. 파라미터를 받아서 보내야하는지 생각해본다. 
		String idx = req.getParameter("idx");//값자체를 주지를 않았는데 어떻게 파라미터를 불러오지...? sql문 ?대응 idx값.. 이걸 불러와서 dao한테 준비물줘야되는데.
		String homephost = req.getParameter("homephost");
		System.out.println("detail에서의 homephost : " + homephost);
		System.out.println("불러온 파라미터:"+idx);
		dao = new DiaryDAO();//db객체화
		req.setAttribute("homephost", homephost);
		req.setAttribute("diary", dao.detail(idx));//1.dao한테 보낸다. 2. 얘네를 담아서 view에 보낸다.-> view는 뽑아서 띄워준다.
		RequestDispatcher dis = req.getRequestDispatcher("diarydetail.jsp");//여기로보낼거야
		dis.forward(req, resp);
		
		
		
		
		
	}
	//글 수정
	public void update() throws ServletException, IOException {
		//diaryupdate.jsp에 DB에서 가져온 값들을 띄운다. 근데 inputtext태그안에 넣어야한다. 
		//(확인버튼을누르면 또 컨트롤러를타고 그 안에있던 받은값들을 보내고 컨트롤러를 또 탄다)
		String idx = req.getParameter("idx");
		System.out.println("불러온 파라미터:"+idx);
		dao = new DiaryDAO();//dao객체화
		req.setAttribute("diary", dao.update(idx));//diary라는 변수에 return받은 dto를 넣는다.
		RequestDispatcher dis = req.getRequestDispatcher("diaryupdate.jsp");//여기로보낸다.
		dis.forward(req, resp);
		
	}
	//글 수정완료
	public void complete() throws Exception {
		//글 수정완료는... db에 넣어주고 list로 간다. 
		//idx값을 그대로 보내서 그 idx에 insert하는거다.. 근데 유일한 값이라 그게 들어갈수있을지는 모르겠다... 그건 ?표에 원래가져온애.
		req.setCharacterEncoding("utf-8");
		String id = req.getParameter("homephost");
		
		String idx = req.getParameter("idx");
		String subject = req.getParameter("diarysubject");
		String content = req.getParameter("diarycontent");
		System.out.println("글수정완료 받아온 파라미터"+idx+subject+content);
		dao = new DiaryDAO();
		boolean result = dao.complete(idx,subject,content);
		String page = "diaryList?homephost="+id;//★정확한 의미를 모르겠다. diaryList라는컨트롤러를 탄다.diaryidx파라미터의 값은 우리가 수정한 글의 idx??그대상이 처음누른 글..
		String msg = "수정에 실패했습니다.";
		if(result) {
			msg="수정에 성공했습니다.";
		}
		req.setAttribute("msg", msg);
		RequestDispatcher dis = req.getRequestDispatcher(page);
		dis.forward(req, resp);
		
	}
	//글 삭제 메서드
	public void delete() throws ServletException, IOException {
		//보내준파라미터를 받아온다.-> 잘받아왔는지 확인한다.
		//한글변환이 필요한지 생각한다.
		//db가 필요한지 생각한다.
		String msg = null;
		String[] checkdel =req.getParameterValues("check");//여러개의 파라미터이므로 배열에 담는다.
		System.out.println(checkdel);
		if(checkdel == null) {
			msg = "삭제 할 게시물을 선택하세요";
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher("diaryList");
			dis.forward(req, resp);
		}else {
			System.out.println("length:"+checkdel.length);
			dao = new DiaryDAO();
			msg = "삭제에 실패하였습니다.";
			String page = "/diaryList";
			if(dao.delete(checkdel)==checkdel.length) {
				msg="삭제에 성공하였습니다.";
			}
			req.setAttribute("msg", msg);
			RequestDispatcher dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
		}
	}

   //디테일 창 안에서 삭제하기 
   public void detaildelete() throws ServletException, IOException {
      String idx = req.getParameter("idx");
      String homephost = req.getParameter("homephost");
      System.out.println("디테일 삭제파라미터:"+idx);
      System.out.println("detaildelete에서의 homephost : " + homephost);
      dao = new DiaryDAO();
      boolean result = dao.detaildelete(idx);
      //String page ="diaryList?homephost="+homephost;
      String page ="1";
      String msg="삭제에 실패했습니다.";
      if(result) {
         msg = "삭제에 성공했습니다.";
      }
      req.setAttribute("homephost", homephost);
      req.setAttribute("page", page);
      req.setAttribute("msg", msg);
      RequestDispatcher dis = req.getRequestDispatcher("/diaryList");
      dis.forward(req, resp);
   }

   public void writeForm() throws ServletException, IOException {
      String homephost = req.getParameter("homephost");
      System.out.println("다이어리 쓸 미니홈피 주인 아이디 : "+homephost);
      req.setAttribute("homephost", homephost);
      RequestDispatcher dis = req.getRequestDispatcher("diarywrite.jsp");
      dis.forward(req, resp);
   }

   
}