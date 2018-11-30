package com.techm.psd.common.action;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class ErrorHandler extends ExceptionHandler {
	private static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);
	
	public ActionForward execute(Exception exception, ExceptionConfig config, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws ServletException {
		LOGGER.info("Enters in ErrorHandler...");
		
		request.setAttribute("exception", exception);
				
		return super.execute(exception, config, mapping, form, request, response);
	}
}
