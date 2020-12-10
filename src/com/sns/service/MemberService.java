package com.sns.service;

import java.util.HashMap;

import com.google.gson.Gson;
import com.sns.dao.MemberDAO;

public class MemberService {

	public boolean join(String id, String pw, String name, String email, String phone) {
		MemberDAO dao=new MemberDAO();
		boolean success=dao.join(id,pw,name,email,phone);
		return success;
	}

	public boolean login(String id, String pw) {
		MemberDAO dao=new MemberDAO();
		boolean success=dao.login(id,pw);
		return success;
	}

	public String idCheck(String id) {
		boolean success=false;
		MemberDAO dao=new MemberDAO();
		success=dao.idCheck(id);//ID 중복 시 true 반환. 가입 불가/false 시 가입 가능
		
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("idCheck", success);
		Gson gson=new Gson();
		String obj=gson.toJson(map);
		System.out.println("service/가입 가능 여부(false면 가입 가능): "+obj+", "+gson);
		
		return obj;
	}

}
