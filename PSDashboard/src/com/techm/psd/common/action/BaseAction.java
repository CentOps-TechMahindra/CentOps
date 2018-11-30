package com.techm.psd.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.actions.DispatchAction;

import com.techm.psd.common.dto.UserDTO;

public class BaseAction extends DispatchAction {
	
	protected static final Logger LOGGER = Logger.getLogger(BaseAction.class);
	
	protected String getUserID(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserDTO userSession = (UserDTO) session.getAttribute("USERSESSION");
		if (userSession == null) {
			throw new Exception("User must be logged in to save EMP requests.");
		}
		return userSession.getUserId();
	}

}
