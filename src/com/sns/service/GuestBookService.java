package com.sns.service;

import java.util.ArrayList;

import com.sns.dao.GuestBookDAO;
import com.sns.dto.GuestBookDTO;

public class GuestBookService {

	public ArrayList<GuestBookDTO> guestBookList(String homephost) {
		GuestBookDAO dao = new GuestBookDAO();
		return dao.guestBookList(homephost);
	}

	public boolean guestbookWrite(String homephost, String guest, String guestbookWrightText) {
		GuestBookDAO dao = new GuestBookDAO();
		return dao.guestbookWrite(homephost,guest,guestbookWrightText);
	}

	public boolean guestbookDelete(String guestBookIdx, String deleteId) {
		GuestBookDAO dao = new GuestBookDAO();
		return dao.guestbookDelete(guestBookIdx, deleteId);
	}

	public String guestBookUpdateCheck(String guestBookIdx) {
		GuestBookDAO dao = new GuestBookDAO();
		return dao.guestBookUpdateCheck(guestBookIdx);
	}

	public boolean guestBookUpdate(String guestBookIdx, String guestbookUpdateText) {
		GuestBookDAO dao = new GuestBookDAO();
		return dao.guestBookUpdate(guestBookIdx, guestbookUpdateText);
	}

}
