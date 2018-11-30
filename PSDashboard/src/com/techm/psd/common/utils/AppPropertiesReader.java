package com.techm.psd.common.utils;

import org.apache.log4j.Logger;
import com.techm.psd.common.utils.AppConfig;

public class AppPropertiesReader {
	private static Logger LOGGER = Logger.getLogger(AppConfig.class);
	
	static String _GROUP_ID = "aj797er";					// Default id is 'gss27373'
	
	public static final String getGroupMechID() {
		return _GROUP_ID+"@techmahindra.com";
	}

}
