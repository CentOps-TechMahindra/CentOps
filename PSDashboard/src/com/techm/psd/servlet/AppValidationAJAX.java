package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.common.dto.NodeDTO;
import com.techm.psd.jenkins.bo.JenkinsConfigurationBO;
import com.techm.psd.jenkins.bo.JobData;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class AppValidationAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(AppValidationAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into AppValidationAJAX()!");
		JSONObject 		jsonObj 	= new JSONObject();
		ApplicationDAO 	aDAO 		= new ApplicationDAOImpl();
		boolean 		result 		= false;
		String 			appName 	= "";
		int 			appId		= 0;
		
		if(req.getParameter("appId") != null) 		{appId		= Integer.parseInt(req.getParameter("appId").toString());}
		if(req.getParameter("appName") != null) 	{appName	= req.getParameter("appName").toString();}
		
		try {
			if(!appName.equals("")){
				result	= aDAO.isValidateAppName(appName, appId);
			}
			jsonObj.put("result", result);
		} catch (Exception e) {
			logger.error("Error in AppValidationAJAX()!",e);
			e.printStackTrace();
		}

		logger.info("Exit from AppValidationAJAX()! -- IS VALID APP NAME: "+result);
		PrintWriter pw 		= resp.getWriter();
		pw.println(jsonObj);
	}
}