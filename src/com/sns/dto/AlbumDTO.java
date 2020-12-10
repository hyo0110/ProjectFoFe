package com.sns.dto;

import java.sql.Date;


public class AlbumDTO {
	//사진첩 게시물 컬럼
	private int albumidx;
	private String id;
	private String albumcontent;
	private Date albumreg_date;
	
	//사진첩 사진파일 컬럼
	private int photoidx;
	private String albumOriFileName;
	private String albumNewFileName;
	
	//댓글 컬럼
		private int replyIdx;
		private int replyLevel;
		private int replyRef;
		private String replyCont;
		private String replyUser_id;

		public int getAlbumidx() {
			return albumidx;
		}
		public void setAlbumidx(int albumidx) {
			this.albumidx = albumidx;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAlbumcontent() {
			return albumcontent;
		}
		public void setAlbumcontent(String albumcontent) {
			this.albumcontent = albumcontent;
		}
		public Date getAlbumreg_date() {
			return albumreg_date;
		}
		public void setAlbumreg_date(Date albumreg_date) {
			this.albumreg_date = albumreg_date;
		}
		public int getPhotoidx() {
			return photoidx;
		}
		public void setPhotoidx(int photoidx) {
			this.photoidx = photoidx;
		}
		public String getAlbumOriFileName() {
			return albumOriFileName;
		}
		public void setAlbumOriFileName(String albumOriFileName) {
			this.albumOriFileName = albumOriFileName;
		}
		public String getAlbumNewFileName() {
			return albumNewFileName;
		}
		public void setAlbumNewFileName(String albumNewFileName) {
			this.albumNewFileName = albumNewFileName;
		}
		public int getReplyIdx() {
			return replyIdx;
		}
		public void setReplyIdx(int replyIdx) {
			this.replyIdx = replyIdx;
		}
		public int getReplyLevel() {
			return replyLevel;
		}
		public void setReplyLevel(int replyLevel) {
			this.replyLevel = replyLevel;
		}
		public void setReplyLevel(String replyLevel) {
			if(replyLevel == null || replyLevel.equals("")) this.replyLevel = -1;
			else this.replyLevel = Integer.parseInt(replyLevel);
		}
		public int getReplyRef() {
			return replyRef;
		}
		public void setReplyRef(int replyRef) {
			this.replyRef = replyRef;
		}
		public void setReplyRef(String replyRef) {
			if(replyRef == null || replyRef.equals("")) this.replyRef = -1;
			else this.replyRef = Integer.parseInt(replyRef);
		}
		public String getReplyCont() {
			return replyCont;
		}
		public void setReplyCont(String replyCont) {
			this.replyCont = replyCont;
		}
		public String getReplyUser_id() {
			return replyUser_id;
		}
		public void setReplyUser_id(String replyUser_id) {
			this.replyUser_id = replyUser_id;
		}
		@Override
		public String toString() {
			return "AlbumDTO [albumidx=" + albumidx + ", id=" + id + ", albumcontent=" + albumcontent + ", albumreg_date="
					+ albumreg_date + ", photoidx=" + photoidx + ", albumOriFileName=" + albumOriFileName
					+ ", albumNewFileName=" + albumNewFileName + ", replyIdx=" + replyIdx + ", replyLevel=" + replyLevel
					+ ", replyRef=" + replyRef + ", replyCont=" + replyCont + ", replyUser_id=" + replyUser_id + "]";
		}
	
	
	
}
