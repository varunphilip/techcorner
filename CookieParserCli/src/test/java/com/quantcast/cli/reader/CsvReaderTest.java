package com.quantcast.cli.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.quantcast.cli.service.CliFactory;
import com.quantcast.cli.utils.CliUtils;

class CsvReaderTest {



	@Test
	void testFindActiveCookiesFile() throws Exception {
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
	void testFindActiveCookiesFileDate() throws Exception {
		List<String> expected = new ArrayList<>();
		expected.add("AtY0laUfhglK3lC7");			
		IFileReader reader = new CsvReader();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CliFactory.results = reader.read(new File("./test-files/cookies.txt"), dateFormat.parse("2018-12-09"));
		List<String> actuals = new CliUtils().findActiveCookies();	
		assertEquals(expected, actuals);
	}

}
