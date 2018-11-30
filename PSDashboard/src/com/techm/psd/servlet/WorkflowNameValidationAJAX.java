package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class WorkflowNameValidationAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(WorkflowNameValidationAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into WorkflowNameValidationAJAX()!");
		JSONObject 		jsonObj 		= new JSONObject();
		WorkflowDAO 	wDAO 			= new WorkflowDAOImpl();
		boolean 		result 			= false;
		String 			workflowName 	= "";
		int 			workflowId		= 0;
		
		if(req.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(req.getParameter("workflowId").toString());}
		if(req.getParameter("workflowName") != null) 	{workflowName	= req.getParameter("workflowName").toString();}
		
		try {
			if(!workflowName.equals("")){
				result	= wDAO.isValidateWorkflowName(workflowName, workflowId);
			}
			jsonObj.put("result", result);
		} catch (Exception e) {
			logger.error("Error in WorkflowNameValidationAJAX()!", e);
			e.printStackTrace();
		}

		logger.info("Exit from WorkflowNameValidationAJAX()! -- IS VALID APP NAME: "+result);
		PrintWriter pw 		= resp.getWriter();
		pw.println(jsonObj);
	}
}