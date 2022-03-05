package com.quantcast.cli.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.quantcast.cli.model.CookieVo;
import com.quantcast.cli.reader.CsvReader;
import com.quantcast.cli.reader.IFileReader;
import com.quantcast.cli.service.CliFactory;

class CliUtilsTest {

	@Test
	void testFindActiveCookies() throws Exception {
		List<String> expected = new ArrayList<>();
		expected.add("AtY0laUfhglK3lC7");
		expected.add("SAZuXPGUrfbcn5UA");
		expected.add("4sMM2LxV07bPJzwf");
		Collections.sort(expected);
		IFileReader reader = new CsvReader();
		CliFactory.results = reader.read(new File("./test-files/cookies.txt"));
		List<String> actuals = new CliUtils().findActiveCookies();
		Collections.sort(actuals);
		assertEquals(expected, actuals);
	}

	@Test
	void testFindActiveCookiesDate() throws Exception {
		List<String> expected = new ArrayList<>();
		expected.add("AtY0laUfhglK3lC7");			
		IFileReader reader = new CsvReader();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CliFactory.results = reader.read(new File("./test-files/cookies.txt"), dateFormat.parse("2018-12-09"));
		List<String> actuals = new CliUtils().findActiveCookies();	
		assertEquals(expected, actuals);
	}

	//@Test
	void testFilterCookies() throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<CookieVo> expected = new ArrayList<>();
		CookieVo vo = null;
		vo = new CookieVo();
		vo.setCookie("AtY0laUfhglK3lC7");
		vo.setDate(format.parse("2018-12-09T14:19:00"));
		expected.add(vo);
		vo = new CookieVo();
		vo.setCookie("SAZuXPGUrfbcn5UA");
		vo.setDate(format.parse("2018-12-09T10:13:00"));
		expected.add(vo);
		vo = new CookieVo();
		vo.setCookie("5UAVanZf6UtGyKVS");
		vo.setDate(format.parse("2018-12-09T07:25:00"));
		expected.add(vo);
		vo = new CookieVo();
		vo.setCookie("AtY0laUfhglK3lC7");
		vo.setDate(format.parse("2018-12-09T06:19:00"));
		expected.add(vo);
		Collections.sort(expected, new CustomComparator());
		System.out.println(expected);
		IFileReader reader = new CsvReader();
		CliFactory.results = reader.read(new File("./test-files/cookies.txt"));
		List<CookieVo> actuals = new CliUtils().filterCookies((List<CookieVo>)CliFactory.results, dateFormat.parse("2018-12-09"));
		Collections.sort(actuals, new CustomComparator());
		System.out.println(actuals);
		assertEquals(expected, actuals);
	}

	public class CustomComparator implements Comparator<CookieVo> {
	    @Override
	    public int compare(CookieVo o1, CookieVo o2) {
	        return o1.getDate().compareTo(o2.getDate());
	    }
	}
	
}
