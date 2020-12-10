package com.sns.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sns.service.DiaryService;

@WebServlet({"/diaryWrite","/diaryList","/diaryDetail","/diaryUpdate","/diaryDelete","/diaryComplete","/diaryDetailDelete","/diaryWriteForm"})//글쓰기,목록,상세보기,글수정,삭제
public class DiaryController extends HttpServlet {

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      try {
         process(req,resp);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }



   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      try {
         process(req,resp);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

      //컨트롤러에서 해야 할 일
      //webservelet (v)
      //get/post방식 오버라이드 해오기. (v)
      //uri에서 contextpath빼고 Addr만들어서 
      //switch문에 넣어서 적용하기 
   
   
   private void process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
      String uri = req.getRequestURI();//uri
      String ctx = req.getContextPath();//context
      String addr = uri.substring(ctx.length());//uri에서 ctx길이만큼 빼기 
      
      
      DiaryService service = new DiaryService(req, resp);//객체생성
      switch(addr) {
      case"/diaryWrite":
         System.out.println("다이어리쓰기 컨트롤러");
         service.write();//서비스한테 일을 시킨다.
         break;
      case"/diaryWriteForm":
         System.out.println("다이어리쓰기 중간단계");
         service.writeForm();//서비스한테 일을 시킨다.
         break;
      case"/diaryList":
         System.out.println("글 리스트 보기 컨트롤러");
         service.list();
         break;
      case"/diaryDetail":
         System.out.println("글 자세히보기 컨트롤러");//이거타짐
         service.detail();
         break;
      case"/diaryUpdate":
         System.out.println("글 수정 컨트롤러");
         service.update();
         break;
      case"/diaryComplete":
         System.out.println("글 수정 완료 컨트롤러");
         service.complete();
         break;
      case"/diaryDelete":
         System.out.println("글 삭제 컨트롤러");
         service.delete();
         break;
      case"/diaryDetailDelete":
         System.out.println("상세보기 글 삭제");
         service.detaildelete();
      
      }
      
   }
      
   
   
   
}