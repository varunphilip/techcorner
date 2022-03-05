package com.quantcast.cli;

import java.util.Queue;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.quantcast.cli.commands.ActiveCookie;
import com.quantcast.cli.commands.Exit;
import com.quantcast.cli.commands.Filter;
import com.quantcast.cli.commands.ListAll;
import com.quantcast.cli.commands.ListCookies;
import com.quantcast.cli.service.CliFactory;
import com.quantcast.cli.utils.CliUtils;
import com.quantcast.cli.utils.ExceptionBuilder;
import com.quantcast.cli.utils.IConstants;

import picocli.CommandLine;

@CommandLine.Command(name = "cli", version = IConstants.VERSION, mixinStandardHelpOptions = true, requiredOptionMarker = '*', description = "This is a CLI Tool which will help us to work on Cookie log", header = "Cookie CLI", optionListHeading = "%nOptions are:%n",
//footerHeading = "%nCopyright", footer = "%nDeveloped by Varun Philip", 
		subcommandsRepeatable = true, commandListHeading = "%nSubCommands are: %n", subcommands = {ListAll.class, ListCookies.class,
				  Exit.class, Filter.class, ActiveCookie.class })
public class CookieParserCliMain {

	private static final Logger LOG = LogManager.getLogger(CookieParserCliMain.class);
	
	
	

	public static void main(String[] args) throws Exception {
		LOG.info("Application Started ...");
		CliUtils utils = new CliUtils();		
		Scanner sc = new Scanner(System.in);
		String cmd = null;
		try {
			while (true) {
				System.out.print("$");
				cmd = sc.nextLine();
				if (cmd != null && !cmd.trim().isEmpty()) {
					Queue<String> commandQueue = utils.queueCommands(cmd);
					while (commandQueue.size() > 0) {
						new CommandLine(new CookieParserCliMain()).setCaseInsensitiveEnumValuesAllowed(true)
								.execute(commandQueue.poll().split(" "));
					}
						utils.printResult();
					CliFactory.results = null;					
				}
			}
		} catch (Exception e) {
			CliFactory.results = null;			
			LOG.error(new ExceptionBuilder().buildException(e.getMessage(), e.getStackTrace()));
		}
	}
}
