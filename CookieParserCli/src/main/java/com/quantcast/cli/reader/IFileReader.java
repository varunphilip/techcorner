package com.quantcast.cli.reader;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.quantcast.cli.model.CookieVo;

public interface IFileReader {
	
	public List<CookieVo> read(File file) throws Exception;
	public List<CookieVo> read(File file, Date cookieDate) throws Exception;	
	public List<CookieVo> read(File file, String cookieId) throws Exception;
	public List<String> findActiveCookies(File file) throws Exception;
	public List<String> findActiveCookies(File file, Date cookieDate) throws Exception;

}
