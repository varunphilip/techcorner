package com.quantcast.cli.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.quantcast.cli.model.CookieVo;
import com.quantcast.cli.service.CliFactory;

public class CliUtils {

	public Queue<String> queueCommands(String commands) throws Exception {
		Queue<String> commandQueue = new LinkedList<>();
		for (String command : commands.split("\\|"))
			commandQueue.add(command.trim());

		return commandQueue;
	}
	
	public void printResult() throws Exception {
		if (CliFactory.results == null) {
			return;
		} else if (CliFactory.results instanceof String)
			System.out.println(CliFactory.results);
		else if (CliFactory.results instanceof Integer)
			System.out.println(CliFactory.results);
		else if (CliFactory.results instanceof List<?>)
			for (Object cookie : (List<?>) CliFactory.results) {
					System.out.println(cookie.toString());
			}
		//else {
		//	for (CookieVo cookie : (List<CookieVo>) Controller.results)
		//		System.out.println(cookie.toString());
		//}
	}

	public List<String> findActiveCookies() throws Exception {
		int maxSofar = 0;
		Map<String, Integer> cookieFrequency = new HashMap<>();
		List<String> mfuList = new ArrayList<>();
		int frequency = 0;
		for (CookieVo vo : (List<CookieVo>) CliFactory.results) {
			if (cookieFrequency.get(vo.getCookie()) == null)
				frequency = 1;
			else
				frequency = cookieFrequency.get(vo.getCookie()) + 1;

			cookieFrequency.put(vo.getCookie(), frequency);
			if (maxSofar == frequency)
				mfuList.add(vo.getCookie());
			else if (maxSofar < frequency) {
				maxSofar = frequency;
				mfuList = new ArrayList<>();
				mfuList.add(vo.getCookie());
			}
		}
		return mfuList;
	}

	public List<String> findActiveCookies(Date cookieDate) throws Exception {
		boolean cookieStart = false;
		int maxSofar = 0;
		Map<String, Integer> cookieFrequency = new HashMap<>();
		List<String> mfuList = new ArrayList<>();
		String cookieId;
		int frequency = 0;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (CookieVo vo : (List<CookieVo>) CliFactory.results) {
			if (cookieStart == true && cookieDate.compareTo(dateFormat.parse(dateFormat.format(vo.getDate()))) != 0)
				break;

			if (cookieStart == false && cookieDate.compareTo(dateFormat.parse(dateFormat.format(vo.getDate()))) == 0)
				cookieStart = true;

			if (cookieStart) {
				if (cookieFrequency.get(vo.getCookie()) == null)
					frequency = 1;
				else
					frequency = cookieFrequency.get(vo.getCookie()) + 1;

				cookieFrequency.put(vo.getCookie(), frequency);
				if (maxSofar == frequency)
					mfuList.add(vo.getCookie());
				else if (maxSofar < frequency) {
					maxSofar = frequency;
					mfuList = new ArrayList<>();
					mfuList.add(vo.getCookie());
				}
			}
		}
		return mfuList;
	}
	
	public List<CookieVo> filterCookies(List<CookieVo> cookies, Date cookieDate) throws Exception {
		boolean cookieStart = false;
		List<CookieVo> cookiesList = new ArrayList<>();		
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		for (CookieVo vo : cookies) {
			if (cookieStart == true && cookieDate.compareTo(dateFormat.parse(dateFormat.format(vo.getDate()))) != 0)
				break;

			if (cookieStart == false && cookieDate.compareTo(dateFormat.parse(dateFormat.format(vo.getDate()))) == 0)
				cookieStart = true;

			if (cookieStart) {
				cookiesList.add(new CookieVo(vo));
			}
		}
		return cookiesList;
	}

}
