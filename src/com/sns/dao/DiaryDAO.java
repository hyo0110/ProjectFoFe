package com.sns.dao;

import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sns.dto.DiaryDTO;


public class DiaryDAO {


	//생성자로 무조건적으로 먼저 실행해야하는 부분을 만들어준다.
	//connection , preparestatement, resultset는 전역변수로 설정해준다.
	//자원 전부반납해주는 메서드도 만들어주면 편하다.
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	public DiaryDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn =  ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		   }
	}
	
	
	
	public boolean write(String id, String subject, String content) {//db에 들어갔는지 확인하기... 디테일로 가야겠지? 일단 확인해보려면 근데 그냥 목록으로가도...되굿...
		//sql문 준비하기 -> INSERT INTO diary (diaryidx,id,diarysubject,diarycontent,diarybhit,diaryreg_date)VALUES();
		//preparestatement로 ..해주고
		//실행 -> 보내고.. 
		//자원반납
		
		String sql="INSERT INTO diary(DIARYIDX, id, DIARYSUBJECT,DIARYCONTENT,DIARYBHIT,DIARYREG_DATE)VALUES(diary_seq.NEXTVAL,?,?,?,0,sysdate)";
		//조회수는 처음엔 0으로 설정해주고 나중에 update문으로 따로 수를 올려준다.
		boolean success = false;//초기값을 false로 주고
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, subject);
			ps.setString(3, content);
			int a= ps.executeUpdate();
			if(a>0) {//넣어진게 성공이면 
				success=true;//true로 바꿔주고
			} /*
				 * else if(a==0) { String msg="게시글을 전부 입력해주세요.";
				 * 위에 ?를 널로주고 널값이면 else if로 간다고해야하나...이건나중에하자
				 * }
				 */
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		resClose();
		}return success;//성공했으면 success가 true로 바뀌어서 서비스로 갈거다.
		
	}
		//리스트 보여주기
		public ArrayList<DiaryDTO> list(String homephost, int page) {//반환하는 값이 arraylist인 list이고 list안에 넣은값이 dto라  반환타입이 ArrayList<DiaryDTO>이다.
			//리스트를 보여주기위해선... list가 있어야한다. 그다음에 view에서 list를 가져와서 보여준다. 
			//list를 만들기 위해선 dto를 이용해 많은 변수들을 한번에 묶고 그다음 arrayList를 객체화해서 리스트 전체를 가져온다.
			//순서,db를 연결하고서..... dto를 쓰고서 
			 //전체 컬럼 갯수 : 5개
			 int pagePerCnt = 10;//페이지당 보여줄 게시물의 수
			 int end = page*pagePerCnt; 
			 int start =(end-pagePerCnt)+1;
			 int endpage;
					
			String sql = "SELECT rnum, id,diaryidx, diarysubject, diarybhit,diaryreg_date FROM"
					+ " (SELECT ROW_NUMBER() over (ORDER BY diaryidx DESC) AS rnum, id, diaryidx, diarysubject, diarybhit, diaryreg_date FROM diary WHERE id = ? )"
					+ "WHERE RNUM BETWEEN ? AND ?";//diaryidx기준으로 내림차순
			
			//SELECT diaryidx,id,diarysubject,diarybhit,diaryreg_date FROM diary WHERE id=? ORDER BY diaryidx DESC;
			
			ArrayList<DiaryDTO> list = null;
			try {
				
				ps = conn.prepareStatement(sql);
				ps.setString(1, homephost);
				ps.setInt(2, start);
		        ps.setInt(3, end);
				
				rs = ps.executeQuery();
				list = new ArrayList<DiaryDTO>();//얠 여기서 만들어줘야 list가 제대로 생성될것같다.
				while(rs.next()) {//sql이 실행되서 성공했으면 
					//dto를 객체화해서 그 dto안에 내가 db에서 불러온 값들을 직접 넣어준다. 그리고 그 가져온 값들을 list에 싹다넣는다.
					
					DiaryDTO dto = new DiaryDTO();
					dto.setDiaryidx(rs.getString("diaryidx"));
					dto.setId(rs.getString("id"));
					dto.setDiarysubject(rs.getString("diarysubject"));
					dto.setDiarybhit(rs.getInt("diarybhit"));
					dto.setDiaryreg_date(rs.getString("diaryreg_date"));
					list.add(dto);//객체화한 arrayList에 dto에 넣은값(db에서 가져온 컬럼값들)을 더해준다.
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				resClose();
			}
			return list;
		}
		

public int limit(String homephost) { 
	String sql="SELECT count (*) FROM diary WHERE id=?"; 
	int count = 0 ;
	try {
	ps = conn.prepareStatement(sql);
	 ps.setString(1, homephost); 
	 rs = ps.executeQuery();
	
	 while(rs.next()) {		 
		 count = rs.getInt("count(*)");
		 System.out.println(count);
	 	} 
	 } catch (SQLException e) { 
		 e.printStackTrace(); 
	 }finally {
		resClose();
	}
	return count;
 }



      //상세보기
      public DiaryDTO detail(String idx) {
         //dto는 필요하지만 arraylist에 넣어줄필요는  없다.
         //글번호/날짜/조회수/제목/내용
         String sql = "SELECT id, diaryidx,diaryreg_date,diarybhit,diarysubject,diarycontent FROM diary WHERE diaryidx=?";//idx값으로 불러온다. 테이블내에있는 idㅌ...?표 대응해줄려면 파라미터 받아와야하는거아닌가..?
         DiaryDTO dto = null;//여기로 꺼내줘야 return할때 보낼수있다. 객체화를 if문 안에서 하면 객체화가 안된것이기 때문에 안된다.
         try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,idx);
            rs = ps.executeQuery();
            if(rs.next()) {
               dto = new DiaryDTO();//여러개정보를 한번에 불러오려면 dto를 써야한다.
               dto.setId(rs.getString("id"));
               dto.setDiaryidx(rs.getString("diaryidx"));
               dto.setDiaryreg_date(rs.getString("diaryreg_date"));//데이트도 그냥 스트링으로 받아도 되겠지?
               dto.setDiarybhit(rs.getInt("diarybhit"));
               dto.setDiarysubject(rs.getString("diarysubject"));
               dto.setDiarycontent(rs.getString("diarycontent"));//불러온값들을 dto에 다 넣어줬다.
               upHit(idx);
               //조회수올리는메서드는 나중에... 이게 전부다 불러져온다는건 글제목을클릭하고 그순간 파라미터를 보내서 받았다는 걸 의미하기때문에 여기 안에 넣어줘야한다.
            }
            
         } catch (SQLException e) {
            e.printStackTrace();
            return null;//실패하면 아무것도 안뜨게
         }finally {
            resClose();
         }
         return dto;//dto를 반환하기.
         
      }
      //수정하기-일단 가져와서 띄우기
      public DiaryDTO update(String idx) {
         String sql ="SELECT id, diarysubject,diarycontent,diaryidx FROM diary where diaryidx=? ";
         DiaryDTO dto = null;
         try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, idx);//물음표대응
            rs = ps.executeQuery();
            if(rs.next()) {//값이 있으면
               dto = new DiaryDTO();//dto객체화하고
               dto.setId(rs.getString("id"));//id를 띄울게 아니더라도 dto에넣어서 이용할거기때문에 넣어준다.
               dto.setDiarysubject(rs.getString("diarysubject"));
               dto.setDiarycontent(rs.getString("diarycontent"));
               dto.setDiaryidx(rs.getString("diaryidx"));
               
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }finally {
            resClose();
         }return dto;//dto를 Service로 return
      }
      
   
   
   //자원반납하기
   public void resClose() {
      try {
         if(conn!=null) {conn.close();}
         if(rs!=null) {rs.close();}
         if(ps!=null) {ps.close();}
         
      }catch(Exception e) {
      }
   }
   //글 수정완료
   public boolean complete(String idx, String subject, String content) {
      String sql="UPDATE diary SET diarysubject=?,diarycontent=? WHERE diaryidx=?";
         //"update diary set 컬럼명=' '    ";
      boolean result = false;//기본값설정
      try {
         ps = conn.prepareStatement(sql);
         //주말동안 잊지말자ㅠㅠ유ㅜ유유
         ps.setString(1, subject);
         ps.setString(2, content);
         ps.setString(3, idx);
         
         if(ps.executeUpdate()>0) {//값이 0보다 크다면 ->1 이라면 ->성공
            result = true;
            
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         resClose();
      }return result;
      
      
   }
   //조회수 올리기 메서드
   public void upHit(String idx) {
      //디테일 컨트롤러 타면 diarybhit 이거올려야되
      String sql = "UPDATE diary SET diarybhit = diarybhit+1 WHERE diaryidx=?";
      
      try {
         ps = conn.prepareStatement(sql);
         ps.setString(1, idx);
         
         ps.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         resClose();
      }
   }

   public int delete(String[] checkdel) {
      String sql = "DELETE FROM diary WHERE diaryidx=?";
      int delCount= 0;
      try {
            ps = conn.prepareStatement(sql);
            for (String del : checkdel) {
               System.out.println("삭제 글 번호 : " +del);
               ps.setString(1,del);
               delCount +=ps.executeUpdate();//delCount 는 execute성공시킨 수를 계속 넣는것... execute는 정말 실행하는것.
            }
            System.out.println("삭제 성공 개수 : " + delCount);
         } catch (SQLException e) {
            e.printStackTrace();
         }finally {
            resClose();
         }
         return delCount;//그 수를 리턴
   }
   //다이어리 디테일 삭제
   public boolean detaildelete(String idx) {
      boolean success = false;
      String sql="DELETE FROM diary WHERE diaryidx=?";
      try {
         ps = conn.prepareStatement(sql);
         ps.setString(1, idx);
         if(ps.executeUpdate()>0) {
            success = true;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }finally {
         resClose();
      }return success;
      
      
   }
   
   

}