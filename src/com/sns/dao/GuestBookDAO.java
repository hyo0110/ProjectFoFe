package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.DiaryDTO;
import com.sns.dto.GuestBookDTO;

public class GuestBookDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public GuestBookDAO(){
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
		try {
			if(rs!=null) {rs.close();}
			if(ps!=null) {ps.close();}
			if(conn!=null) {conn.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<GuestBookDTO> guestBookList(String homephost) {
		String sql = "SELECT guestbookidx, id, guestbookuser_name, guestbookcontent, guestbookreg_date FROM guestbook WHERE id = ? ORDER BY guestbookreg_date DESC";
		ArrayList<GuestBookDTO> list = new ArrayList<GuestBookDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, homephost);
			rs = ps.executeQuery();
			while(rs.next()) {
				GuestBookDTO dto = new GuestBookDTO();
				dto.setGuestBookIdx(rs.getString("guestbookidx"));
				dto.setId(rs.getString("id"));
				dto.setGuestBookUser_name(rs.getString("guestbookuser_name"));
				dto.setGuestBookContent(rs.getString("guestbookcontent"));
				dto.setGuestBookReg_date(rs.getString("guestbookreg_date"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return list;
	}

	public boolean guestbookWrite(String homephost, String guest, String guestbookWrightText) {
		boolean result = false;
		String sql = "INSERT INTO guestbook(guestbookidx, id, guestbookuser_name, guestbookcontent, guestbookreg_date)VALUES(guestBook_seq.NEXTVAL,?,?,?,sysdate)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, homephost);
			ps.setString(2, guest);
			ps.setString(3, guestbookWrightText);
			if(0<ps.executeUpdate()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return result;
	}

	public boolean guestbookDelete(String guestBookIdx, String deleteId) {
		boolean result = false;
		try {
			String sql = "SELECT guestbookuser_name FROM guestbook WHERE guestbookidx = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, guestBookIdx);
			rs = ps.executeQuery();
			if (rs.next()) {
				String guestbookuser_name = rs.getString("guestbookuser_name");
				if(deleteId.equals(guestbookuser_name)) {
					sql = "DELETE FROM guestbook WHERE guestBookIdx=? ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, guestBookIdx);
					if(0<ps.executeUpdate()) {
						result = true;
					}
				}else {
					sql = "SELECT id FROM guestbook WHERE guestbookidx = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, guestBookIdx);
					rs = ps.executeQuery();
					if(rs.next()) {
						String id = rs.getString("id");
						if(deleteId.equals(id)) {
							sql = "DELETE FROM guestbook WHERE guestBookIdx=? ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, guestBookIdx);
							if(0<ps.executeUpdate()) {
								result = true;
							}
						}
					}
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		
		return result;
	}

	public String guestBookUpdateCheck(String guestBookIdx) {
		String sql = "SELECT guestbookcontent FROM guestbook WHERE guestbookidx = ?";
		String guestbookcontent ="";
		try {
			ps =conn.prepareStatement(sql);
			ps.setString(1, guestBookIdx);
			rs = ps.executeQuery();
			if(rs.next()) {
				guestbookcontent = rs.getString("guestbookcontent");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return guestbookcontent;
	}

	public boolean guestBookUpdate(String guestBookIdx, String guestbookUpdateText) {
		boolean result = false;
		String sql = "update guestbook set guestbookcontent=? WHERE guestbookidx = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, guestbookUpdateText);
			ps.setString(2, guestBookIdx);
			if(0<ps.executeUpdate()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return result;
	}

}
