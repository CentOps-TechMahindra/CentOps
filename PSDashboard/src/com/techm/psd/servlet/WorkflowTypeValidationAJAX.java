package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.workflowType.dao.WorkflowTypeDAO;
import com.techm.psd.workflowType.dao.WorkflowTypeDAOImpl;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class WorkflowTypeValidationAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(WorkflowTypeValidationAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into WorkflowTypeValidationAJAX()!");
		JSONObject 		jsonObj 		= new JSONObject();
		WorkflowTypeDAO	wDAO 			= new WorkflowTypeDAOImpl();
		boolean 		result 			= false;
		String 			workflowType 	= "";
		int 			workflowTypeId	= 0;
		
		if(req.getParameter("workflowTypeId") != null) 		{workflowTypeId		= Integer.parseInt(req.getParameter("workflowTypeId").toString());}
		if(req.getParameter("workflowType") != null) 		{workflowType		= req.getParameter("workflowType").toString();}
		
		try {
			if(!workflowType.equals("")){
				result	= wDAO.isValidateWorkflowTypeName(workflowType, workflowTypeId);
			}
			
			jsonObj.put("result", result);
		} catch (Exception e) {
			logger.error("Error in WorkflowTypeValidationAJAX()!", e);
			e.printStackTrace();
		}

		logger.info("Exit from WorkflowTypeValidationAJAX()! -- IS VALID Workflow Type NAME: "+result);
		PrintWriter pw 		= resp.getWriter();
		pw.println(jsonObj);
	}
}