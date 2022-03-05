package com.quantcast.cli.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.model.CookieVo;

public class CsvReader implements IFileReader {

	private static final Logger LOG = LogManager.getLogger(CsvReader.class);

	@Override
	public List<CookieVo> read(File file) throws Exception {
		BufferedReader csvReader = null;
		String row = null;
		List<CookieVo> cookiesList = new ArrayList<>();
		String[] data = null;
		String dateString = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			csvReader = new BufferedReader(new FileReader(file));
			CookieVo cookie = null;
			while ((row = csvReader.readLine()) != null) {
				cookie = new CookieVo();
				data = row.split(",");
				cookie.setCookie(data[0]);
				dateString = data[1].indexOf("+") == -1 ? data[1] : data[1].substring(0, data[1].indexOf("+"));
				cookie.setDate(format.parse(dateString));
				cookiesList.add(cookie);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (csvReader != null)
				csvReader.close();
		}
		return cookiesList;
	}

	@Override
	public List<CookieVo> read(File file, Date cookieDate) throws Exception {
		BufferedReader csvReader = null;
		String row = null;
		List<CookieVo> cookiesList = new ArrayList<>();
		String[] data = null;
		String dateString = "";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		boolean cookieStart = false;
		try {
			csvReader = new BufferedReader(new FileReader(file));
			CookieVo cookie = null;
			while ((row = csvReader.readLine()) != null) {
				data = row.split(",");
				dateString = data[1].indexOf("+") == -1 ? data[1] : data[1].substring(0, data[1].indexOf("+"));
				if (cookieStart == true && cookieDate.compareTo(dateFormat.parse(dateString)) != 0)
					break;
				if (cookieStart == false && cookieDate.compareTo(dateFormat.parse(dateString)) == 0)
					cookieStart = true;

				if (cookieStart) {
					cookie = new CookieVo();
					cookie.setCookie(data[0]);
					cookie.setDate(format.parse(dateString));
					cookiesList.add(cookie);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (csvReader != null)
				csvReader.close();
		}
		return cookiesList;
	}

	@Override
	public List<CookieVo> read(File file, String cookieId) throws Exception {
		BufferedReader csvReader = null;
		String row = null;
		List<CookieVo> cookiesList = new ArrayList<>();
		String[] data = null;
		String dateString = "";
		System.out.println("Reading file with cookieId filter ...");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		try {
			csvReader = new BufferedReader(new FileReader(file));
			CookieVo cookie = null;
			while ((row = csvReader.readLine()) != null) {
				data = row.split(",");
				if (data[0].trim().equals(cookieId)) {
					dateString = data[1].indexOf("+") == -1 ? data[1] : data[1].substring(0, data[1].indexOf("+"));
					cookie = new CookieVo();
					cookie.setCookie(data[0]);
					cookie.setDate(format.parse(dateString));
					cookiesList.add(cookie);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (csvReader != null)
				csvReader.close();
		}
		return cookiesList;
	}

	@Override
	public List<String> findActiveCookies(File file) throws Exception {
		BufferedReader csvReader = null;
		String row = null;
		String[] data = null;
		int maxSofar = 0;
		Map<String, Integer> cookieFrequency = new HashMap<>();
		List<String> mfuList = new ArrayList<>();
		String cookieId;
		int frequency = 0;

		try {
			csvReader = new BufferedReader(new FileReader(file));
			while ((row = csvReader.readLine()) != null) {
				data = row.split(",");
				cookieId = data[0];

				if (cookieFrequency.get(cookieId) == null)
					frequency = 1;
				else
					frequency = cookieFrequency.get(cookieId) + 1;

				cookieFrequency.put(cookieId, frequency);
				if (maxSofar == frequency)
					mfuList.add(cookieId);
				else if (maxSofar < frequency) {
					maxSofar = frequency;
					mfuList = new ArrayList<>();
					mfuList.add(cookieId);
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (csvReader != null)
				csvReader.close();
		}
		return mfuList;
	}

	@Override
	public List<String> findActiveCookies(File file, Date cookieDate) throws Exception {
		BufferedReader csvReader = null;
		String row = null;
		String[] data = null;
		int maxSofar = 0;
		Map<String, Integer> cookieFrequency = new HashMap<>();
		List<String> mfuList = new ArrayList<>();
		String cookieId;
		int frequency = 0;
		boolean cookieStart = false;
		String dateString = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			csvReader = new BufferedReader(new FileReader(file));
			while ((row = csvReader.readLine()) != null) {
				data = row.split(",");
				cookieId = data[0];

				dateString = data[1].indexOf("+") == -1 ? data[1] : data[1].substring(0, data[1].indexOf("+"));
				if (cookieStart == true && cookieDate.compareTo(dateFormat.parse(dateString)) != 0)
					break;
				if (cookieStart == false && cookieDate.compareTo(dateFormat.parse(dateString)) == 0)
					cookieStart = true;

				if (cookieStart) {
					if (cookieFrequency.get(cookieId) == null)
						frequency = 1;
					else
						frequency = cookieFrequency.get(cookieId) + 1;

					cookieFrequency.put(cookieId, frequency);
					if (maxSofar == frequency)
						mfuList.add(cookieId);
					else if (maxSofar < frequency) {
						maxSofar = frequency;
						mfuList = new ArrayList<>();
						mfuList.add(cookieId);
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (csvReader != null)
				csvReader.close();
		}
		return mfuList;
	}

}
