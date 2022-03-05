package com.quantcast.cli.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.quantcast.cli.model.CookieVo;

public interface ICliService {
	public void listAll(File file) throws Exception;
	
	public void list(File file, Date cookieDate) throws Exception;
	
	public void list(File file, String cookieId) throws Exception;
	
	public void filterCookies(List<CookieVo> cookies, String cookieId) throws Exception;
	
	public void filterCookies(List<CookieVo> cookies, Date cookieDate) throws Exception;
	
	public void findActiveCookies(File file, Date cookieDate) throws Exception;
	
	public void findActiveCookies(Date cookieDate) throws Exception;
	
}
