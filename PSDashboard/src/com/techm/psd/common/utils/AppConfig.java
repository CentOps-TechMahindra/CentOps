package com.techm.psd.common.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.techm.psd.scheduler.HistoryScheduler;

public class AppConfig {
	
	private static Logger LOGGER = Logger.getLogger(AppConfig.class);

	private static String password = "wednesday";
	public static Properties _SYSPROPERTIES;
	public static Properties _DBPROPERTIES;	
	public static Properties _LDAPPROPERTIES;
	public static Properties _APP_PROPERTIES;
	
	public static String _SERVERNAME; 		//either IP address with port number or the dns name 
	public static String _ENV; 				//CentOps Environment like DEV, TEST or PROD
	public static String _APPNAME; 			//Application name
	public static String _AUTH_LOGIN_URL;
	public static String _AUTH_LOGON_SWITCH = "ON";
	
	static String _GROUP_ID;
	
	private static String _CENTOPS_HOME = System.getenv("CENTOPS_HOME");
	//private static String _CENTOPS_HOME = "C:/Users/ov5144/Downloads/PSDashboard";
	private static String propertyFilePath = _CENTOPS_HOME+"/Config/SYSTEM.properties";
	
	/**
	 * Initializes the application configuration with the property file. If
	 * fails to read the property file, it uses the PropertyFile variable
	 * specified in the deployment descriptor.
	 * 
	 * This is static block and will be called as soon as the application gets
	 * loaded on the server.
	 * 
	 *  
	 */
	static {
		LOGGER.info("***********PATH*************: "+System.getenv("Path"));
		LOGGER.info("*********CLASSPATH**********: "+System.getenv("CLASSPATH"));
		initProps();
	}
	
	/**
	 * Initialize System, DB and Logging properties from propertyFilePath variable.
	 */
	private static void initProps(){
		if (StringUtils.isEmpty(propertyFilePath)) {
			throw new IllegalStateException("Environment variable [CentOpsPropertyPath] is not set. Application cannot be initialized.");
		}
		
		LOGGER.info("===Loading System Propertyfiles from "+ propertyFilePath + " ===");
		try {			
			_SYSPROPERTIES = new Properties();
			_SYSPROPERTIES.load(new FileInputStream(propertyFilePath));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.fatal(e.toString());			
			LOGGER.error("**********************************************************************");
			LOGGER.error("System property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fatal(e.toString());
			LOGGER.error("**********************************************************************");
			LOGGER.error("System property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch(Exception e) {
			LOGGER.error("Error while getting ENV variable: " + propertyFilePath);
		}
		LOGGER.info("===Loaded===");
		
		
		LOGGER.info("===Loading DB Propertyfile===");
		try {			
			_DBPROPERTIES = new Properties();
			_DBPROPERTIES.load(new FileInputStream(_CENTOPS_HOME+"/Config/CENTOPS_DB.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOGGER.fatal(e.toString());
			LOGGER.error("**********************************************************************");
			LOGGER.error("System property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.fatal(e.toString());
			LOGGER.error("**********************************************************************");
			LOGGER.error("System property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.fatal(e.toString());
			LOGGER.error("**********************************************************************");
			LOGGER.error("System property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		}
		LOGGER.info("===Loaded===");
	
		LOGGER.info("===Loading Logging Propertyfile===");
		
		System.setProperty("log",_CENTOPS_HOME);												// Setting logs path before initializing log4j configuration.
		String log4jConfig = System.getProperty("log4j.configuration");
		if(StringUtils.isEmpty(log4jConfig)){
			PropertyConfigurator.configure(_CENTOPS_HOME+"/Config/CENTOPS_log4j.properties");
		}else{
			LOGGER.info("Log4j preconfigured by Jboss, need to overwrite. code pending---");
		}
		LOGGER.info("===Loaded===");
		
		//For AUTH FILTER
		_AUTH_LOGIN_URL =  _SYSPROPERTIES.getProperty("AUTH_LOGIN_URL");
		LOGGER.info("====AUTH_LOGIN_URL found to be: " + _AUTH_LOGIN_URL + "=====");

		//For AUTH LOGON SWITCH
		_AUTH_LOGON_SWITCH =  _SYSPROPERTIES.getProperty("AUTH_LOGON_SWITCH");
		LOGGER.info("====AUTH_LOGON_SWITCH found to be: " + _AUTH_LOGON_SWITCH + "=====");

		//Loading server name
		_SERVERNAME = _SYSPROPERTIES.getProperty("ServerAddress");
		LOGGER.info("====Server address found to be: " + _SERVERNAME + "=====");
		
		// Loading Application Name
		_APPNAME = _SYSPROPERTIES.getProperty("APPNAME");
		LOGGER.info("====APPLICATION NAME: " + _APPNAME + "=====");
		
		//Loading ENV
		_ENV = _SYSPROPERTIES.getProperty("ENV");
		LOGGER.info("====CentOps Environment found to be: " + _ENV + "=====");
		
		//Loading ENV
		_GROUP_ID = _SYSPROPERTIES.getProperty("GROUP_ID");
		LOGGER.info("====CentOps GROUP_ID found to be: " + _GROUP_ID + "=====");
				
		try {
			_LDAPPROPERTIES = new Properties();
			_LDAPPROPERTIES.load(new FileInputStream(_CENTOPS_HOME+"/Config/LDAP.properties"));
			
		} catch (FileNotFoundException e) {			
			LOGGER.error("Error while loading LDAP  properties!!", e);
			LOGGER.error("**********************************************************************");
			LOGGER.error("LDAP property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch (IOException e) {
			LOGGER.error("Error while loading LDAP properties!!", e);
			LOGGER.error("**********************************************************************");
			LOGGER.error("LDAP property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		} catch (Exception e) {
			LOGGER.error("Error while loading LDAP properties!!", e);
			LOGGER.error("**********************************************************************");
			LOGGER.error("LDAP property file not found, application could not start propertly!"+e.getMessage());
			LOGGER.error("**********************************************************************");
		}
		
		//Scheduler#1 HistoryScheduler 
		Timer timer = new Timer();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		timer.schedule(new HistoryScheduler() , 7*24*60*60*1000);
		
	}
}
