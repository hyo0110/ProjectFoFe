package com.sns.dto;

public class MiniHomeDTO {
	
	private String id;
	private String name;
	private String backcolor; 
	private String minihname; 
	private String minihintro; 
	private String email; 
	private String profilephoto; 
	private String mp3;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMp3() {
		return mp3;
	}
	public void setMp3(String mp3) {
		this.mp3 = mp3;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBackcolor() {
		return backcolor;
	}
	public void setBackcolor(String backcolor) {
		this.backcolor = backcolor;
	}
	public String getMinihname() {
		return minihname;
	}
	public void setMinihname(String minihname) {
		this.minihname = minihname;
	}
	public String getMinihintro() {
		return minihintro;
	}
	public void setMinihintro(String minihintro) {
		this.minihintro = minihintro;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getProfilephoto() {
		return profilephoto;
	}
	public void setProfilephoto(String profilephoto) {
		this.profilephoto = profilephoto;
	}
}
