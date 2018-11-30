package com.techm.psd.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.techm.psd.common.dto.UserDTO;

public class UserSessionUtil {
	
private Logger LOGGER = Logger.getLogger(UserSessionUtil.class);
	
	/**
	 * Method to return User ID obtaining from User Session object using HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	public static String getSessionUserId(HttpServletRequest request) {
		String userId = "";

		try {
			HttpSession session = request.getSession();
			UserDTO user = (UserDTO) session.getAttribute("USERSESSION");
			userId = user.getUserId();
		} catch (ClassCastException ccex) {
			ccex.printStackTrace();
		}

		return userId;
	}

	/**
	 * Method to return User Session object using HttpSession
	 * 
	 * @param session
	 * @return
	 */

	/**
	 * Method to return User Session object using HttpServletRequest
	 * 
	 * @param request
	 * @return
	 */
	public static UserDTO getUserSession(HttpServletRequest request) {
		UserDTO user = null;

		try {
			HttpSession session = request.getSession();
			user = (UserDTO) session.getAttribute("USERSESSION");
		} catch (ClassCastException ccex) {
			ccex.printStackTrace();
		}

		return user;
	}

	/*public static User getUserSession(HttpSession session) {
		User user = null;

		try {
			user = (User) session.getAttribute("USERSESSION");
		} catch (ClassCastException ccex) {
			ccex.printStackTrace();
		}

		return user;
	}*/

	public static UserDTO getUserSession(HttpSession session) {
		UserDTO user = null;

		try {
			user = (UserDTO) session.getAttribute("USERSESSION");
		} catch (ClassCastException ccex) {
			ccex.printStackTrace();
		}

		return user;
	}

}