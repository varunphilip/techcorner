package com.quantcast.cli.commands;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.CookieParserCliMain;
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

@CommandLine.Command(name = "listall", aliases = {
		"lsa" }, version = IConstants.VERSION, mixinStandardHelpOptions = true, requiredOptionMarker = '*', description = "list all command lists all the cookie details from the file", optionListHeading = "%nOptions are:%n")
public class ListAll implements Runnable, IConstants {

	private static final Logger LOG = LogManager.getLogger(ListAll.class);
	@Spec
	CommandSpec spec;

	@CommandLine.Option(names = { "-f", "--file" }, required = true, description = "Cookie file path")
	File file;

	@Override
	public void run() {
		ICliService service = new CliService();
				
		try {
			service.listAll(file);
		} catch (Exception e) {
			LOG.error(new ExceptionBuilder().buildException(e.getMessage(), e.getStackTrace()));
			throw new ParameterException(spec.commandLine(),
					String.format(e.getMessage()));						
		}
	}
}