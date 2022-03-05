package com.quantcast.cli.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CookieVo {
	
	private String cookie;
	private Date date;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	
	public CookieVo() {
		
	}
	public CookieVo(CookieVo vo) {
		cookie = vo.getCookie();
		date = vo.getDate();
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return cookie + "\t" + format.format(date);
	}

}
