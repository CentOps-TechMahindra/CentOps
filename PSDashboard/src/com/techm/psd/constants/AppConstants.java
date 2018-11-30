package com.techm.psd.constants;

import java.util.LinkedHashMap;

/**
 * Title: AppConstants Description: AppConstants contains constants that are
 * used throughout all Applications
 */
@SuppressWarnings("unused")
public final class AppConstants {
	public static final String LOGGING_ENTER_METHOD = "Entering";
	public static final String LOGGING_EXIT_METHOD 	= "Exiting";
	
	public static final String JDBC_ORACLE  ="jdbc:oracle:thin:@";
	public static final String JDBC_MYSQL   ="jdbc:mysql://";
	
	public static LinkedHashMap<String, String> DATABASE_DRIVER_NAME = new LinkedHashMap<String, String>();
	
	static {

			AppConstants.DATABASE_DRIVER_NAME.put("ORACLE", "oracle.jdbc.OracleDriver");
			AppConstants.DATABASE_DRIVER_NAME.put("MYSQL", "com.mysql.jdbc.Driver");
			
	}

	public static LinkedHashMap<String, String> EXCEPTION_MESSAGE = new LinkedHashMap<String, String>();
	
	static {

			AppConstants.EXCEPTION_MESSAGE.put("ORA-01017", " Invalid username/password");
			AppConstants.EXCEPTION_MESSAGE.put("oracle.jdbc.OracleDriver", "Database type is incorrect");
			AppConstants.EXCEPTION_MESSAGE.put("com.mysql.jdbc.Driver", "Database type is incorrect");
			AppConstants.EXCEPTION_MESSAGE.put("Io exception", "Database host name OR port is incorrect");
			AppConstants.EXCEPTION_MESSAGE.put("Listener refused the connection with the following error", "Database name/SID is incorrect");
			
	}
}
