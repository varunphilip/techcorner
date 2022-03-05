package com.quantcast.cli.service;

import com.quantcast.cli.reader.CsvReader;
import com.quantcast.cli.reader.IFileReader;

public class CliFactory {

	public static Object results = null;
	
	public IFileReader getFileReader() {
		return new CsvReader();
	}
	
	
}
