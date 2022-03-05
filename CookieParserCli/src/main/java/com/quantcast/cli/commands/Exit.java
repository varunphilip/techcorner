package com.quantcast.cli.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import picocli.CommandLine;

@CommandLine.Command(name = "exit", aliases = {"exit"}, version = "22.3.0.1", mixinStandardHelpOptions = true, requiredOptionMarker = '*', 
description = "exit the application safely", header = "Exit App", 
optionListHeading = "%nOptions are:%n")
public class Exit implements Runnable{

	private static final Logger LOG = LogManager.getLogger(ListCookies.class);
	
	@Override
	public void run() {
		LOG.info("Exiting application");
		System.exit(0);
		
	}

}
