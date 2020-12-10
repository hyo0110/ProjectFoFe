package com.sns.dto;

public class DiaryDTO {
	
	//내가 불러올 컬럼명들을 private으로 잡아준다. 
	private String diaryidx;
	private String id;
	private String diarysubject;
	private String diarycontent;
	private int diarybhit;
	private String diaryreg_date;
	private String[] checkdel;
	private int count;
	
	
	
	//외부와 연결시킬거니까 getter,setter해준다.
	public String getDiaryidx() {
		return diaryidx;
	}
	public void setDiaryidx(String diaryidx) {
		this.diaryidx = diaryidx;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDiarysubject() {
		return diarysubject;
	}
	public void setDiarysubject(String diarysubject) {
		this.diarysubject = diarysubject;
	}
	public int getDiarybhit() {
		return diarybhit;
	}
	public void setDiarybhit(int diarybhit) {
		this.diarybhit = diarybhit;
	}
	public String getDiaryreg_date() {
		return diaryreg_date;
	}
	public void setDiaryreg_date(String diaryreg_date) {
		this.diaryreg_date = diaryreg_date;
	}
	public String getDiarycontent() {
		return diarycontent;
	}
	public void setDiarycontent(String diarycontent) {
		this.diarycontent = diarycontent;
	}
	
	public String[] getCheckdel() {
		return checkdel;
	}
	public void setCheckdel(String[] checkdel) {
		this.checkdel = checkdel;
	}
	

	
	
	
	
}
