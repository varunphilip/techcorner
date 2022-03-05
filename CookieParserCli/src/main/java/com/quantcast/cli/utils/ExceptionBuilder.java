package com.quantcast.cli.utils;

public class ExceptionBuilder implements IConstants {
	
	private String exception;

	public String buildException(String exception, StackTraceElement[] stackTraceElements) {
		StringBuilder builder = new StringBuilder("");
		builder.append(exception).append(SEPERATOR);
		int traceIndex = 0;
		
		for (StackTraceElement e : stackTraceElements) {			
			builder.append(e.toString()).append(SEPERATOR);
			traceIndex++;
			if (traceIndex == MAX_EXCEPTION_TRACE)
				break;
		}
		this.exception = builder.toString(); 
		return toString();
	}
	
	
	@Override
	public String toString() {
		return "Exception [moduleId=" + MODULE_ID + ", moduleName=" + MODULE_NAME + ", Exception=" + exception + "]";
	}
	

}
