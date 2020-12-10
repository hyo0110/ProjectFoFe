package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.sns.dto.ManageDTO;
import com.sns.service.ManageService;


public class ManageDAO {
	
	Connection conn= null;
	PreparedStatement ps= null;
	ResultSet  rs =null;
	
	//connection 생성
	public ManageDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn  = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//connection 닫기
	public void Close() {
		try {
			if(rs != null) {rs.close();}
			if(ps != null) {ps.close();}
			if(conn != null) {conn.close();}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


	public HashMap<String, Object> profileselect(HttpServletRequest req) {
		ManageService service = new ManageService();
		String sql = "Select id, newfilename from PROFILEPHOTOUP where id =?";
		
		boolean com = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				map.put("filename", rs.getString("newfilename"));
				com=true;
				
			}
			map.put("success", com);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return map;
	}
	
	public HashMap<String, Object> mainPhotoselect(HttpServletRequest req) {
		ManageService service = new ManageService();
		String sql = "Select id, newfilename from MAINPHOTOUP where id =?";
		
		boolean com = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			while (rs.next()) {
				map.put("filename", rs.getString("newfilename"));
				com=true;
			}
			map.put("success", com);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return map;
	}
	
	public HashMap<String, Object> bgmselect(HttpServletRequest req) {
		ManageService service = new ManageService();
		String sql = "Select id, newfilename from MP3UP where id =?";
		
		boolean com = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			
			while (rs.next()) {
				map.put("filename", rs.getString("newfilename"));
				com=true;
			}
			map.put("success", com);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return map;
	}

	
	//삭제
	public void profiledelete(HttpServletRequest req) {
		String sql = "DELETE FROM PROFILEPHOTOUP WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			if(ps.executeUpdate()>0) {
				System.out.println("삭제 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
	}
	
	public void mainPhotodelete(HttpServletRequest req) {
		String sql = "DELETE FROM MAINPHOTOUP WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			if(ps.executeUpdate()>0) {
				System.out.println("삭제 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
	}
	
	public void bgmdelete(HttpServletRequest req) {
		String sql = "DELETE FROM MP3UP WHERE id = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			if(ps.executeUpdate()>0) {
				System.out.println("삭제 성공");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
	}
	

	public boolean profileUpload(HttpServletRequest req, ManageDTO dto) {//프로필 업로드
		String sql = "INSERT INTO PROFILEPHOTOUP(id, orifilename, newfilename) VALUES(?, ? ,?)";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getOriFileName());
			ps.setString(3, dto.getNewFileName());
			
			if(ps.executeUpdate()>0) {
				success  = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return success;
	}	
	
	public boolean mainPhotoUpload(HttpServletRequest req, ManageDTO dto) {//프로필 업로드
		String sql = "INSERT INTO MAINPHOTOUP(id, orifilename, newfilename) VALUES(?, ? ,?)";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getOriFileName());
			ps.setString(3, dto.getNewFileName());
			
			if(ps.executeUpdate()>0) {
				success  = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return success;
	}
	
	public boolean bgmUpload(HttpServletRequest req, ManageDTO dto) {//프로필 업로드
		String sql = "INSERT INTO MP3UP(id, orifilename, newfilename) VALUES(?, ? ,?)";
		boolean success = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getOriFileName());
			ps.setString(3, dto.getNewFileName());
			
			if(ps.executeUpdate()>0) {
				success  = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}
		return success;
	}

	public boolean profilecheck(HttpServletRequest req) {
		String sql = "SELECT ? FROM PROFILEPHOTOUP ";
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}return result;
		
		
	}

	public boolean bgmcheck(HttpServletRequest req) {
		String sql = "SELECT ? FROM MP3UP";
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			}
			System.out.println("파일이 없음");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}return result;
	}
	
	public boolean mainPhotocheck(HttpServletRequest req) {
		String sql = "SELECT ? FROM MAINPHOTOUP ";
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, (String) req.getSession().getAttribute("id"));
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
				System.out.println("파일이 없음");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}return result;
	}

	public boolean bgUpload(HttpServletRequest req) {
		ManageDTO dto = new ManageDTO();
		String sql = "UPDATE MINIHMAIN SET BACKCOLOR = ? WHERE id = ? ";
		boolean result = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, req.getParameter("background"));
			ps.setString(2, (String) req.getSession().getAttribute("id"));
			if (ps.executeUpdate()>0) {
				System.out.println("색이 변경되었습니다.");
				result =true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			Close();
		}return result;
		
		
	}


	
}
