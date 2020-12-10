package com.sns.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sns.dto.FollowerTalkDTO;
import com.sns.dto.SearchDTO;

public class FollowDAO {
	
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	public FollowDAO(){
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

	public ArrayList<SearchDTO> memberSearch(String srchName) throws SQLException {
		ArrayList<SearchDTO> arrList = new ArrayList<SearchDTO>();
		String sql = "SELECT id,name FROM member where name=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, srchName);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			SearchDTO dto = new SearchDTO();
			dto.setId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			arrList.add(dto);
		}
		
		return arrList;
	}

	public boolean follow(String homephostId, String loginId) throws SQLException {
		boolean result = false;
		String sql = "INSERT INTO follow (id, followid) VALUES (?,?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, loginId);
		ps.setString(2, homephostId);
		if(0<ps.executeUpdate()) {
			result = true;
		}
		return result;
	}

	public boolean unfollow(String homephostId, String loginId) throws SQLException {
		boolean result = false;
		String sql = "DELETE FROM follow WHERE id=? and followid=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, loginId);
		ps.setString(2, homephostId);
		if(0<ps.executeUpdate()) {
			result = true;
		}
		return result;
	}

	public ArrayList<SearchDTO> loadFollowList(String loginId) throws SQLException {
		String sql = "select id,name from member where id in(SELECT followId FROM follow where id=?)";
		ArrayList<SearchDTO> arrList = new ArrayList<SearchDTO>();
		ps = conn.prepareStatement(sql);
		ps.setString(1, loginId);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			SearchDTO dto = new SearchDTO();
			dto.setId(rs.getString("Id"));
			dto.setName(rs.getString("name"));
			arrList.add(dto);
		}
		
		
		return arrList;
	}

	public ArrayList<SearchDTO> loadFollowerList(String loginId) throws SQLException {
		String sql = "select id,name from member where id in(SELECT Id FROM follow where followid=?)";
		ArrayList<SearchDTO> arrList = new ArrayList<SearchDTO>();
		ps = conn.prepareStatement(sql);
		ps.setString(1, loginId);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			SearchDTO dto = new SearchDTO();
			dto.setId(rs.getString("Id"));
			dto.setName(rs.getString("name"));
			arrList.add(dto);
		}
		
		return arrList;
	}

	public boolean followerTalkWrite(String id, String content, String followerTalkUser_name) throws SQLException {
		boolean result = false;
		String sql = "INSERT INTO followerTalk(followerTalkIdx, id, followerTalkUser_name, followerTalkContent)VALUES(followerTalk_seq.NEXTVAL, ?, ?, ?)";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		ps.setString(2, followerTalkUser_name);
		ps.setString(3, content);
		if(0<ps.executeUpdate()) {
			result = true;
		}
		return result;
	}

	public ArrayList<FollowerTalkDTO> getfollowerTalkList(String id) throws SQLException{
		ArrayList<FollowerTalkDTO> list = new ArrayList<FollowerTalkDTO>();
		String sql = "SELECT followerTalkIdx, id, followerTalkUser_name, followertalkcontent FROM followertalk where id=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, id);
		rs = ps.executeQuery();
		
		while(rs.next()) {
			FollowerTalkDTO dto = new FollowerTalkDTO();
			dto.setFollowerTalkIdx(rs.getString("followerTalkIdx"));
			dto.setId(rs.getString("id"));
			dto.setFollowerTalkUser_name(rs.getString("followerTalkUser_name"));
			dto.setFollowerTalkContent(rs.getString("followerTalkContent"));
			list.add(dto);
		}
		
		return list;
		
	}

	public boolean followerTalkDelete(String idx, String deleteId) {
		boolean result = false;
		try {
			String sql = "SELECT followerTalkUser_name FROM followertalk WHERE followerTalkIdx = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			if (rs.next()) {
				String followerTalkUser_name = rs.getString("followerTalkUser_name");
				if(deleteId.equals(followerTalkUser_name)) {
					sql = "DELETE FROM followertalk WHERE followerTalkIdx=? ";
					ps = conn.prepareStatement(sql);
					ps.setString(1, idx);
					if(0<ps.executeUpdate()) {
						result = true;
					}
				}else {
					sql = "SELECT id FROM followertalk WHERE followerTalkIdx = ?";
					ps = conn.prepareStatement(sql);
					ps.setString(1, idx);
					rs = ps.executeQuery();
					if(rs.next()) {
						String id = rs.getString("id");
						if(deleteId.equals(id)) {
							sql = "DELETE FROM followertalk WHERE guestBookIdx=? ";
							ps = conn.prepareStatement(sql);
							ps.setString(1, idx);
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



	

}
