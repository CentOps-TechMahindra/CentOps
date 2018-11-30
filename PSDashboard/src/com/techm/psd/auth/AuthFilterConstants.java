package com.techm.psd.auth;

import com.techm.psd.common.utils.AppConfig;

public class AuthFilterConstants {

	public static final String AUTH_LOGIN_URL 				= AppConfig._AUTH_LOGIN_URL;
	
	
	public static final String FORWARD_SLASH 					= "/";
	public static final char 	EQUALS 							= '=';
	public static final char 	AMPERSAND 						= '&';
	public static final char 	QUESTION_MARK 					= '?';
	public static final String ENCODED_QUESTION_MARK 			= "%3F";
	public static final String	ENCODED_AMPERSAND 				= "%26";
	public static final String DELIMITER 						= "\\|";
	
	public static final String USER_SESSION_KEY 				= "USERSESSION";
	
	public static final String AUTH_SECURE_COOKIE_NAME 			= "authCCKc";
	public static final String AUTH_HR_COOKIE_NAME 				= "authCCkr";
	public static final String AUTH_SYSTEM_PARAM 				= "sysName";
	public static final String AUTH_SYSTEM_VALUE 				= "MMIPS";
	public static final String AUTH_LEVEL 						= "PROD";
	public static final String AUTH_RETURN_URL_PARAM 			= "retURL";
	public static final int    AUTH_COOKIE_SUITS_ID_INDEX 		= 5;
	public static final String AUTH_COOKIE_ENCODING 				= "UTF-8";
	
	public static final int   	AUTH_HR_FIRST_NAME_INDEX 		= 0;
	public static final int 	AUTH_HR_LAST_NAME_INDEX 			= 1;
	public static final int 	AUTH_HR_EMAIL_INDEX 				= 2;
	public static final int 	AUTH_HR_TN_INDEX 				= 3;
	public static String 		WEB_APP_ROOT;
	public static long 			sessionTimeout 					= 720;//12 hour = 12*60 minutes 
	
	public static String 		_ON								= "ON";
}
