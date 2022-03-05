package com.quantcast.cli.commands;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.model.CookieVo;
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

@CommandLine.Command(name = "filter", aliases = {
		"filter" }, version = IConstants.VERSION, mixinStandardHelpOptions = true, requiredOptionMarker = '*', description = "Filter the inbound list with cookie Id or date", optionListHeading = "%nOptions are:%n")
public class Filter implements Runnable, IValidate {

	private static final Logger LOG = LogManager.getLogger(Filter.class);
	@Spec
	CommandSpec spec;

	@ArgGroup(exclusive = true, multiplicity = "1")
	Exclusive exclusive;

	ICliService service = new CliService();

	static class Exclusive {
		@Option(names = { "-d", "--date" }, required = false, description = "Cookie date in <yyyy-MM-dd> format")
		Date date;
		@Option(names = { "-c", "--cookie" }, required = false, description = "Cookie Id")
		String cookieId;

	}

	@Override
	public void validate() {
		if (CliFactory.results == null)
			throw new ParameterException(spec.commandLine(), String.format("Inbound object list expected"));
		if (CliFactory.results instanceof List<?> == false)
			throw new ParameterException(spec.commandLine(),
					String.format("Invalid inbound Object %n Expected :: List<?> %n Actual "
							+ CliFactory.results.getClass().getTypeName()));

	}

	@Override
	public void run() {
		try {
			if (exclusive.cookieId != null)
				service.filterCookies((List<CookieVo>) CliFactory.results, exclusive.cookieId);
			else
				service.filterCookies((List<CookieVo>) CliFactory.results, exclusive.date);
		} catch (Exception e) {
			LOG.error(new ExceptionBuilder().buildException(e.getMessage(), e.getStackTrace()));
			throw new ParameterException(spec.commandLine(), String.format(e.getMessage()));
		}
	}

}
