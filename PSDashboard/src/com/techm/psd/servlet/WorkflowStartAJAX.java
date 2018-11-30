package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.bo.ReportBO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.jenkins.bo.JobData;
import com.techm.psd.jenkins.bo.StartWorkflowBO;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class WorkflowStartAJAX extends HttpServlet {

private Logger logger = Logger.getLogger(WorkflowStartAJAX.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into WorkflowStartAJAX()!");
		JSONObject 		jsonObj 		= new JSONObject();
		UserDTO 		user 			= UserSessionUtil.getUserSession(req);
		StartWorkflowBO	startWorkflowBO	= new StartWorkflowBO();
		int 			workflowId 		= 0;
		String 			selNodes		= "";
		String 			paramStr		= "";
		String 			selExecutionOn	= "";
		String			executedFrom	= "";
		List<String> 	selNodesList 	= null;
		
		//Retrieve Request Parameters.
		if(req.getParameter("workflowId") != null) 		{workflowId			= Integer.parseInt(req.getParameter("workflowId").toString());}
		if(req.getParameter("selNodes") != null) 		{selNodes			= req.getParameter("selNodes").toString();}
		if(req.getParameter("paramStr") != null) 		{paramStr			= req.getParameter("paramStr").toString();}
		if(req.getParameter("selExecutionOn") != null)	{selExecutionOn		= req.getParameter("selExecutionOn").toString();}
		if(req.getParameter("executedFrom") != null)	{executedFrom		= req.getParameter("executedFrom").toString();}
		
		selNodesList = new ArrayList<String>(Arrays.asList(selNodes.split(",")));
		
		jsonObj 	= startWorkflowBO.startWorkflow(workflowId, user, selNodesList, selExecutionOn, paramStr, executedFrom);
		logger.info("Exit from WorkflowStartAJAX()!");
		PrintWriter pw 		= resp.getWriter();
		pw.println(jsonObj);
	}
	
	
}
