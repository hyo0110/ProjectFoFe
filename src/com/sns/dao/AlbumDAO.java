package com.sns.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


import com.sns.dto.AlbumDTO;
import com.sns.dto.ReplyDTO;

public class AlbumDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	DataSource ds = null;
	
	public AlbumDAO() {
		//생성자 이용해 DB연결
		try {
			System.out.println("dao 생성자 진입");
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			System.out.println("conn 생성 완료");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			if(rs != null) {rs.close();}
			if(ps != null) {ps.close();}
			if(conn != null) {conn.close();}
			System.out.println("자원반납");					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean write(AlbumDTO dto) {
		System.out.println("write 진입");
		String sql = "insert into album(albumidx, id, albumcontent) values(Album_seq.NEXTVAL,?,?)";
		long pk=0;
		boolean com = false;
		try {
			ps = conn.prepareStatement(sql, new String[] {"albumidx"});
			ps.setString(1, dto.getId());
			ps.setString(2, dto.getAlbumcontent());
			int success = ps.executeUpdate();
			System.out.println("album insert 성공? 실패? : "+success);
			rs=ps.getGeneratedKeys();
			if(rs.next()) {
				pk = rs.getLong(1);
				System.out.println("album idx : " + pk);
				if(success>0) {
					sql = "insert into albumupfile(albumidx, photoidx, albumorifilename, albumnewfilename) values(?,photoidx_seq.NEXTVAL,?,?)";
					ps=conn.prepareStatement(sql);
					ps.setLong(1, pk);
					ps.setString(2, dto.getAlbumOriFileName());
					ps.setString(3, dto.getAlbumNewFileName());
				}
				
				
				com = (ps.executeUpdate()>0)?true:false;
				System.out.println(com);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return com;
	}

	public ArrayList<AlbumDTO> list(int page, String id) {
		
		int pagecnt = 9;//페이지 내의 게시물 갯수
		int end = page * pagecnt;
		int start = (end-pagecnt)+1;
		String sql = "select  rnum,albumidx, albumnewfilename from "+
				"(select row_number() over(order by albumidx desc) as rnum,albumidx, albumnewfilename from albumupfile where albumidx in(select albumidx from album where id=?)) where rnum between ? and ?";
		ArrayList<AlbumDTO> list = new ArrayList<AlbumDTO>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				AlbumDTO dto = new AlbumDTO();
				dto.setAlbumidx(rs.getInt("albumidx"));
				dto.setAlbumNewFileName(rs.getString("albumnewfilename"));
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}

	
	/* 사진첩 댓글 작성 */
	public boolean replyWrite(ReplyDTO dto) {
		// 길어서 자름
		String sql = 	"insert into albumreply(replyidx, albumidx, replylevel, replyref, replycont, replyUser_id) ";
		sql += 			" values(replyIdx_seq.NEXTVAL,?,||REPLYLEVEL||,||REPLYREF||,?,?)";
		boolean com = false;
		try {
			// replyref, replylevel이 존재하지 않을 경우를 대비하여 replace 해줌
			// -1 이라면 존재하지 않은 것이므로 기본 값으로 넣어줘야 함 (ref의 경우 자기 자신의 번호, level의 경우 1)
			// 값이 -1이 아니라면 존재하는 값으로 치환해주면 됨.
			if (dto.getReplyRef() == -1) {
				sql = sql.replace("||REPLYREF||", "replyIdx_seq.NEXTVAL");
			} else {
				sql = sql.replace("||REPLYREF||", dto.getReplyRef() + "");
			}
			
			if (dto.getReplyLevel() == -1) {
				sql = sql.replace("||REPLYLEVEL||", "1");
			} else {
				sql = sql.replace("||REPLYLEVEL||", dto.getReplyLevel() + "");
			}
			
			ps = conn.prepareStatement(sql);
			// 테스트를 위해 하드코딩 : ps.setInt(1, 1);
			ps.setInt(1, dto.getAlbumidx());
			
			ps.setString(2, dto.getReplyCont());
			
			// 테스트를 위해 하드코딩 : ps.setString(3, "dbckdgur12");
			ps.setString(3, dto.getReplyUser_id());
			
			com = ps.executeUpdate() > 0;
			System.out.println("albumreply insert 성공? 실패? : " + com);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return com;
	}
	
	// 댓글 리스트
	public ArrayList<ReplyDTO> replyList(ReplyDTO dto1) {
		String sql = "select * from albumreply where albumidx = ? order by replyref desc, replylevel asc";
		ArrayList<ReplyDTO> list = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto1.getAlbumidx());
			rs = ps.executeQuery();
			while(rs.next()) {
				ReplyDTO album = new ReplyDTO();
				album.setAlbumidx(rs.getInt("albumIdx"));
				album.setReplyIdx(rs.getInt("replyIdx"));
				album.setReplyCont(rs.getString("replyCont"));
				album.setReplyLevel(rs.getInt("replyLevel"));
				album.setReplyRef(rs.getInt("replyRef"));
				album.setReplyUser_id(rs.getString("replyUser_id"));
				
				list.add(album);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}

	// 댓글 삭제
	public boolean replyDelete(ReplyDTO dto1) {
		String sql = 	"delete from albumreply where replyidx = ?";
		boolean com = false;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, dto1.getReplyIdx());
			com = ps.executeUpdate() > 0;
			System.out.println("albumreply delete 성공? 실패? : " + com);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return com;
	}
	
	public int listcnt(String id) {
		String sql = "select count(albumidx) from album where id=?";
		int pagecnt = 9;
		int allcnt = 0;
		try {
			int p=0;
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				p = (int) rs.getLong(1);
			}
			if((p%pagecnt)>0 && (p%pagecnt)<=8) {
				allcnt = (p/pagecnt)+1;
			}else {
				allcnt = p/pagecnt;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		System.out.println("총 페이지 갯수  : " + allcnt);
		return allcnt;
	}

	public ArrayList<AlbumDTO> detail(int albumidx) {
		String sql = "select a.albumcontent, b.albumnewfilename from album a, albumupfile b where a.albumidx=? and b.albumidx=?";
		AlbumDTO dto = new AlbumDTO();
		ArrayList<AlbumDTO> list = new ArrayList<AlbumDTO>();
		try {
			System.out.println(sql);
			System.out.println(albumidx);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, albumidx);
			ps.setInt(2, albumidx);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				dto.setAlbumcontent(rs.getString("albumcontent"));
				dto.setAlbumNewFileName(rs.getString("albumnewfilename"));
				dto.setAlbumidx(albumidx);
				list.add(dto);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
		
	}

	public String getfilename(int albumidx) {
		String sql = "select albumnewfilename from albumupfile where albumidx=?";
		String newfilename = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, albumidx);
			rs = ps.executeQuery();
			if(rs.next()) {
				newfilename = rs.getString("albumnewfilename");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return newfilename;
	}

	public int albumreplydel(int albumidx) {
		String sql = "delete from albumreply where albumidx=?";
		int success =0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, albumidx);
			success = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public int albumdel(int albumidx) {
		String sql = "delete from album where albumidx=?";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, albumidx);
			success = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return success;
	}

	public int albumfiledel(int albumidx) {
		String sql = "delete from albumupfile where albumidx=?";
		int success = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, albumidx);
			success = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
