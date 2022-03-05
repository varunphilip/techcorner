package com.quantcast.cli.commands;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.CookieParserCliMain;
import com.quantcast.cli.model.CookieVo;
import com.quantcast.cli.reader.IFileReader;
import com.quantcast.cli.service.CliFactory;
import com.quantcast.cli.service.CliService;
import com.quantcast.cli.service.ICliService;
import com.quantcast.cli.utils.ExceptionBuilder;
import com.quantcast.cli.utils.IConstants;
import com.quantcast.cli.utils.IValidate;

import picocli.CommandLine;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@CommandLine.Command(name = "list", aliases = {
		"ls" }, version = IConstants.VERSION, mixinStandardHelpOptions = true, requiredOptionMarker = '*', description = "list command lists cookie details ", optionListHeading = "%nOptions are:%n")
public class ListCookies implements Runnable, IConstants {

	private static final Logger LOG = LogManager.getLogger(ListCookies.class);
	@Spec
	CommandSpec spec;

	ICliService service = new CliService();

	@CommandLine.Option(names = { "-f", "--file" }, required = true, description = "Cookie file path")
	File file;

	@ArgGroup(exclusive = true, multiplicity = "1")
	Exclusive exclusive;

	/*
	 * @CommandLine.Option( names = {"-d", "--date"}, required = false, description
	 * = "Cookie date in <yyyy-MM-dd> format") Date date;
	 */

	static class Exclusive {
		@Option(names = { "-d", "--date" }, required = false, description = "Cookie date in <yyyy-MM-dd> format")
		Date date;
		@Option(names = { "-c", "--cookie" }, required = false, description = "Cookie Id")
		String cookieId;

	}

	@Override
	public void run() {
		ICliService service = new CliService();
		try {

			if (exclusive.date == null && exclusive.cookieId == null)
				service.listAll(file);
			else if (exclusive.cookieId != null)
				service.list(file, exclusive.cookieId);
			else
				service.list(file, exclusive.date);

		} catch (Exception e) {
			LOG.error(new ExceptionBuilder().buildException(e.getMessage(), e.getStackTrace()));
			throw new ParameterException(spec.commandLine(), String.format(e.getMessage()));
		}
	}

}
