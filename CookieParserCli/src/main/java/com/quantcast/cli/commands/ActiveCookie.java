package com.quantcast.cli.commands;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.service.CliFactory;
import com.quantcast.cli.service.CliService;
import com.quantcast.cli.service.ICliService;
import com.quantcast.cli.utils.ExceptionBuilder;
import com.quantcast.cli.utils.IConstants;
import com.quantcast.cli.utils.IValidate;

import picocli.CommandLine;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Spec;

@CommandLine.Command(name = "active", aliases = {
		"active","mac" }, version = IConstants.VERSION, mixinStandardHelpOptions = true, requiredOptionMarker = '*', description = "Find the most active cookies", optionListHeading = "%nOptions are:%n")
public class ActiveCookie implements Runnable, IValidate {

	private static final Logger LOG = LogManager.getLogger(ActiveCookie.class);
	@Spec
	CommandSpec spec;

	@Option(names = { "-d", "--date" }, required = false, description = "Cookie date in <yyyy-MM-dd> format")
	Date cookieDate;

	@CommandLine.Option(names = { "-f", "--file" }, required = false, description = "Cookie file path")
	File file;

	@Override
	public void run() {
		ICliService service = new CliService();
		validate();
		try {
			if (CliFactory.results != null) {
				service.findActiveCookies(cookieDate);
			} else {
				if (file == null)
					throw new ParameterException(spec.commandLine(), String.format("File Path required"));

				service.findActiveCookies(file, cookieDate);
			}
		} catch (Exception e) {
			LOG.error(new ExceptionBuilder().buildException(e.getMessage(), e.getStackTrace()));
			throw new ParameterException(spec.commandLine(), String.format(e.getMessage()));
		}
	}

	@Override
	public void validate() {
		if (CliFactory.results == null)
			return;
		if (CliFactory.results instanceof List<?> == false)
			throw new ParameterException(spec.commandLine(),
					String.format("Invalid inbound Object %n Expected :: List<?> %n Actual "
							+ CliFactory.results.getClass().getTypeName()));

	}

}
