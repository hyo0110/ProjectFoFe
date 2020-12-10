package com.sns.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDAO {
	
	Connection conn=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public MemberDAO() {
		try {
			Context ctx=new InitialContext();
			DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn=ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean join(String id, String pw, String name, String email, String phone) {
		boolean result=false;
		String sql="insert into member(id,pw,name,email,phone) values(?,?,?,?,?)";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			ps.setString(3, name);
			ps.setString(4, email);
			ps.setString(5, phone);
			int success=ps.executeUpdate();
			if(success>0) {
				result=true;

				sql="insert into minihmain(id, backcolor, minihname, minihintro) values(?,?,?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, "green");
				ps.setString(3, name+"의 미니홈피 입니다.");
				ps.setString(4, name+"의 미니홈피에 오신것을 환영합니다.");
				ps.executeUpdate();
				sql="insert into profile(id) values(?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.executeUpdate();
				
				//회원가입시 기본사진 부여
				sql="INSERT INTO PROFILEPHOTOUP(id, newfilename) VALUES(?,?)";
				ps=conn.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, id+"profilephoto.jpg");
				ps.executeUpdate();
				String oriFilePath = "C:/upload/admindefaultProfile.jpg";
		        String copyFilePath = "C:/upload/"+id+"profilephoto.jpg";
		        File oriFile = new File(oriFilePath);
		        File copyFile = new File(copyFilePath);
				try {
					FileInputStream fis = new FileInputStream(oriFile);
					FileOutputStream fos = new FileOutputStream(copyFile);
		            int fileByte = 0; 
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            fis.close();
		            fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            
	            
			}else {
				result=false;
			}			
			
		} catch (SQLException e) {
			e.printStackTrace();
			result=false;
		}finally {
			resClose();
		}		
		return result;
	}

	private void resClose() {
		try {
			if(conn!=null) {conn.close();}
			if(ps!=null) {ps.close();}
			if(rs!=null) {rs.close();}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String id, String pw) {
		String sql="select id from member where id=? and pw=?";
		boolean result=false;
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, pw);
			rs=ps.executeQuery();
			result=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		return result;
	}

	public boolean idCheck(String id) {
		boolean success=false;
		String sql="select id from member where id=?";
		try {
			ps=conn.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			success=rs.next();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}		
		System.out.println("dao/id: "+id+", id 중복 여부: "+success);//ID 중복 시 true 반환
		return success;
	}

}
