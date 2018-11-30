package com.techm.psd.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.techm.psd.common.dto.UserDTO;

public class CommonUtil {

	private static Logger logger = Logger.getLogger(CommonUtil.class);

	/**
	 * Method to return boolean value 
	 *  
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(String arg) {
		if ((arg == null) || (arg.trim().length() <= 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(Collection arg) {
		if ((arg == null) || (arg.size() <= 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(Collection arg) {
		return !isEmpty(arg);
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(String[] arg) {
		if ((arg == null) || (arg.length <= 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(UserDTO arg) {
		if ((arg == null) || (arg.getUserId() == null)
				|| (arg.getUserId().length() <= 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(UserDTO arg) {
		return !isEmpty(arg);
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg) {
		return !isEmpty(arg);
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String[] arg) {
		return !isEmpty(arg);
	}
	
	/**
	 * Method to convert into String value pertaining to parameter
	 * 
	 * @param arg
	 * @param seperator
	 * @return
	 */
	public static String convertString(String[] arg, String seperator) {
		String str = "";

		for (int i = 0; i < (arg.length - 1); i++) {
			str += (arg[i] + seperator);
		}

		str += arg[arg.length - 1];

		return str;
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(Date arg) {
		if (arg == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(Date arg) {
		return !isEmpty(arg);
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(int arg) {
		return arg != 0;
	}
	
	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(int arg) {
		if (arg == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(long arg) {
		return arg != 0;
	}
	
	/**
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmpty(long arg) {
		if (arg == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to return Timestamp
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getSqlTimestamp(Date date) {
		Timestamp timestamp = null;

		if (date != null) {
			timestamp = new Timestamp(date.getTime());
		}

		return timestamp;
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param oldText
	 * @param newText
	 * @return
	 */
	public static boolean notEquals(String oldText, String newText) {
		boolean notEquals = true;

		if ((null == oldText) && (null == newText)) {
			notEquals = false;
		} else if ((null == oldText) && (null != newText)) {
			if (newText.trim().equals("")) {
				notEquals = false;
			} else {
				notEquals = true;
			}
		} else if ((null != oldText) && (null == newText)) {
			if (oldText.trim().equals("")) {
				notEquals = false;
			} else {
				notEquals = true;
			}
		} else {
			if (oldText.trim().equals(newText.trim())) {
				notEquals = false;
			} else {
				notEquals = true;
			}
		}

		return notEquals;
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param oldText
	 * @param newText
	 * @return
	 */
	public static boolean equals(String oldText, String newText) {
		return !notEquals(oldText, newText);
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param oldText
	 * @param newText
	 * @return
	 */
	public static boolean notEquals(int oldText, int newText) {
		return oldText != newText;
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param oldUser
	 * @param newUser
	 * @return
	 */
	public static boolean notEquals(UserDTO oldUser, UserDTO newUser) {
		boolean notEquals = true;

		if ((null == oldUser) && (null == newUser)) {
			notEquals = false;
		} else if ((null == oldUser) && (null != newUser)) {
			notEquals = true;
		} else if ((null != oldUser) && (null == newUser)) {
			notEquals = true;
		} else {
			String oldUserId = oldUser.getUserId();
			String newUserId = newUser.getUserId();

			notEquals = notEquals(oldUserId, newUserId);
		}

		return notEquals;
	}
	
	/**
	 * Method to return Timestamp
	 * 
	 * @param dateFormat
	 * @param strDate
	 * @return
	 */
	public static Timestamp convertToDate(SimpleDateFormat dateFormat,
			String strDate) {
		// Date date = null;
		Timestamp timestamp = null;

		try {
			if (isNotEmpty(strDate) && (dateFormat != null)) {
				Date date = dateFormat.parse(strDate);
				timestamp = new Timestamp(date.getTime());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return timestamp;
	}
	
	/**
	 * Method to return Timestamp
	 * 
	 * @param strDate
	 * @param strTime
	 * @return
	 */
	public static Timestamp convertToDate(String strDate, String strTime) {
		SimpleDateFormat dateFormat = null;
		String strDateTime = null;

		if (isNotEmpty(strTime)) {
			dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm aaa");
			strDateTime = strDate + " " + strTime;
		} else {
			dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			strDateTime = strDate;
		}

		Timestamp timestamp = null;

		if (isNotEmpty(strDateTime)) {
			timestamp = convertToDate(dateFormat, strDateTime);
		}

		return timestamp;
	}
	
	/**
	 * Method to return Timestamp
	 * 
	 * @param strDate
	 * @return
	 */
	public static Timestamp convertToDate(String strDate) {
		Timestamp dt = null;

		if (isNotEmpty(strDate)) {
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
					"MM/dd/yyyy");
			dt = convertToDate(defaultDateFormat, strDate);
		}

		return dt;
	}
	
	/**
     * Return date in specified format.
     * @param milliSeconds Date in milliseconds
     * @param dateFormat Date format 
     * @return String representing date in specified format
     */
    public static String getStrDate(long milliSeconds, String dateFormat){
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date. 
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(milliSeconds);
         return formatter.format(calendar.getTime());
    }
    
    public static Timestamp convertToDate(long milliSeconds){
    	Timestamp dt = null;
    	Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
		if (isNotEmpty(milliSeconds)) {
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
					"MM/dd/yyyy hh:mm aaa");
			dt = convertToDate(defaultDateFormat, defaultDateFormat.format(calendar.getTime()));
		}
    	return dt;
    }
    
	/**
	 * Method to return String
	 * 
	 * @param dateFormat
	 * @param date
	 * @return
	 */
	public static String convertToText(SimpleDateFormat dateFormat, Date date) {
		String strDate = "";

		if (isNotEmpty(date) && (dateFormat != null)) {
			strDate = dateFormat.format(date);
		}

		return strDate;
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthDate(Timestamp date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

		return convertToText(dateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getMonthDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");

		return convertToText(dateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateText(Timestamp date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		return convertToText(defaultDateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTimeText(Timestamp date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
				"MM/dd/yyyy hh:mm aaa");

		return convertToText(defaultDateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateText(Date date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("MM/dd/yyyy");

		return convertToText(defaultDateFormat, date);
	}	
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getDateTextAlsoNull(Timestamp date){
		if(date == null){
			logger.debug("Date is Null!!");
			return "";
		}else{
			return getDateTimeText(date);
		}
	}
	
	/**
	 * Method to return String
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getDateText(String format, Timestamp date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat(format);

		return convertToText(defaultDateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getTimeText(Timestamp date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("hh:mm aaa");

		return convertToText(defaultDateFormat, date);
	}
	
	/**
	 * Method to return String
	 * 
	 * @param date
	 * @return
	 */
	public static String getHourText(Timestamp date) {
		SimpleDateFormat defaultDateFormat = new SimpleDateFormat("hh:00 aaa");

		return convertToText(defaultDateFormat, date);
	}
	
	/**
	 * Method to return Date
	 * 
	 * @return
	 */
	public static Date getThisWeekStartDay() {
		Date startDay = null;
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DAY_OF_WEEK);
		int daysPassed = 0;

		startDay = rightNow.getTime();

		if (day != Calendar.SUNDAY) {
			daysPassed = day - Calendar.SUNDAY;

			// if (daysPassed == -1) { daysPassed = 6; }
			startDay.setTime(startDay.getTime()
					- (1000 * 60 * 60 * 24 * (daysPassed)));
		}

		return startDay;
	}
	
	/**
	 * Method to return Date
	 * 
	 * @return
	 */
	public static Date getThisWeekEndDay() {
		Date startDay = null;
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DAY_OF_WEEK);
		int remainingDays = 0;

		startDay = rightNow.getTime();

		if (day != Calendar.SATURDAY) {
			remainingDays = Calendar.SATURDAY - day;

			// if (distance == -1) { distance = 6; }
			startDay.setTime(startDay.getTime()
					+ (1000 * 60 * 60 * 24 * (remainingDays)));
		}

		return startDay;
	}
	
	/**
	 * Method to return Date
	 * 
	 * @return
	 */
	public static Date getLastWeekStartDay() {
		Date startDay = null;
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DAY_OF_WEEK);
		int daysPassed = 0;

		startDay = rightNow.getTime();

		if (day != Calendar.SUNDAY) {
			daysPassed = day - Calendar.SUNDAY;
			daysPassed = daysPassed + 7;
			// if (daysPassed == -1) { daysPassed = 6; }
			startDay.setTime(startDay.getTime()
					- (1000 * 60 * 60 * 24 * (daysPassed)));
		}

		return startDay;
	}
	
	/**
	 * Method to return Date
	 * 
	 * @return
	 */
	public static Date getLastWeekEndDay() {
		Date startDay = null;
		Calendar rightNow = Calendar.getInstance();
		int day = rightNow.get(Calendar.DAY_OF_WEEK);
		int remainingDays = 0;

		startDay = rightNow.getTime();

		if (day != Calendar.SATURDAY) {
			remainingDays = Calendar.SATURDAY - day;
			remainingDays = remainingDays - 7;
			// if (distance == -1) { distance = 6; }
			startDay.setTime(startDay.getTime()
					+ (1000 * 60 * 60 * 24 * (remainingDays)));
		}

		return startDay;
	}
	
	/**
	 * Method to return boolean value
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isOpenStatus(String arg) {
		boolean isOpen = false;

		if ((arg != null)) {
			if ("Pending".equals(arg) || "Active".equals(arg)) {
				isOpen = true;
			}
		}

		return isOpen;
	}
	
	/**
	 * Method to return int value
	 * 
	 * @param arg
	 * @return
	 */
	public static int getInt(String arg) {
		int intvalue = 0;
		try {
			if (arg != null && arg.trim().length() > 0) {
				arg = arg.trim();
				intvalue = Integer.parseInt(arg);
			}
			
		} catch (Exception e) {
			logger.error("Error while converting string to integer!", e);
		}
		return intvalue;
	}
	
	/**
	 * Return char value
	 * 
	 * @param arg
	 * @return
	 */
	public static char convertChar(String arg) {
		char argChar = 'N';
		if (arg != null && arg.length() >= 1) {
			argChar = arg.charAt(0);
		}
		return argChar;

	}
	
	/**
	 * Method to return String Array
	 * 
	 * @param text
	 * @param seperator
	 * @return
	 */
	public static String[] splitString(String text, String seperator) {
		ArrayList<String> list = new ArrayList<String>();
		StringTokenizer sz = new StringTokenizer(text, seperator);

		while (sz.hasMoreTokens()) {
			String token = sz.nextToken();
			token = token.trim();
			if (CommonUtil.isNotEmpty(token))
				list.add(token);
		}

		return (String[]) list.toArray(new String[0]);
	}
	
	/**
	 * Method to validate the user id pertaining to uid format
	 * 
	 * @param userName
	 * @return
	 */
	public static boolean isValidUserId(String userName) {
		if ((userName != null) && (userName.trim().length() == 6)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Method to take mail id as String and return actual id(name) out of it
	 * 
	 * @param mailId
	 * @return
	 */
	public static String convertMailIdToName(String mailId) {
		String name = null;
		if (mailId != null && mailId.indexOf('@') != -1) {
			int a = mailId.indexOf('@');
			name = mailId.substring(0, a);
		}
		return name;
	}
	
	public static java.sql.Date getSqlDate(java.util.Date jDate){
		if(jDate == null)
			return null;
		java.sql.Date sqlDate = new java.sql.Date(jDate.getTime());
		return sqlDate;
	}
	
	public static Date convertStringToDate(String strDate) {
		Date dt = null;
		try{
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat("yyyy/MM/dd");
			dt = defaultDateFormat.parse(strDate);
		}catch(Exception e){
			logger.error("Error while parsing string date!!", e);
		}
		return dt;
	}
	
	public static Date convertStringToDate(String strDate, String format) {
		Date dt = null;
		try{
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat(format);
			dt = defaultDateFormat.parse(strDate);
		}catch(Exception e){
			logger.error("Error while parsing string date!!", e);
		}
		return dt;
	}
	
	public static String convertDateToString(Date dt, String format) {
		String strDate = null;
		try{
			SimpleDateFormat defaultDateFormat = new SimpleDateFormat(format);
			strDate = defaultDateFormat.format(dt);
		}catch(Exception e){
			logger.error("Error while parsing string date!!", e);
		}
		return strDate;
	}
	
	public static boolean existIn(String dataToBeChecked, String[] data){
		boolean flag = false;
		for(int i=0; i<data.length; i++){
			if(data[i].equalsIgnoreCase(dataToBeChecked)){
				flag = true;
				break;
			}
		}		
		return flag;
	}

	public static String replaceAllEscapeSequence(String name){
		if(name!=null && !("").equals(name)){
			CharSequence lessthan ="<";
			if(name.contains(lessthan)){
				name = name.replaceAll("<", "&lt;");		
			}			
			CharSequence greaterthan =">";
			if(name.contains(greaterthan)){
				name = name.replaceAll(">", "&gt;");		
			}			
			CharSequence ampersand ="&";
			if(name.contains(ampersand)){
				name = name.replaceAll("&", "&amp;");		
			}
			CharSequence apostrophe ="'";
			if(name.contains(apostrophe)){
				name = name.replaceAll("'", "&apos;");		
			}
			CharSequence quote ="\"";
			if(name.contains(quote)){
				name = name.replaceAll("\"", "&quot;");		
			}
		}
		return name;
		
	}
	
	public static boolean isInteger( String input ){
	   try{
	      Integer.parseInt( input );
	      return true;
	   }catch( Exception e){
	      return false;
	   }
	}
	
	public static float getPercentage(int n, int total) {
	    float proportion = ((float) n) / ((float) total);
	    return proportion * 100;
	}
}
