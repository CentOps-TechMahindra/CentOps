package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class WorkflowReportAJAX extends HttpServlet {

	private Logger logger = Logger.getLogger(WorkflowReportAJAX.class);
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
			logger.info("Enters into WorkflowReportAJAX()!");
			UserDTO user 				= UserSessionUtil.getUserSession(req);
			WorkflowDAO	wDAO			= new WorkflowDAOImpl();
			WorkflowBO wBO 				= new WorkflowBO();
			List<JSONObject> jsonList 	= new ArrayList<JSONObject>();
			String str 					= "";
			int workflowId  			= 0;
			int maxStartFlowId 			= 0; 
			
			if(req.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(req.getParameter("workflowId").toString());}
			
			logger.info("WORKFLOW REPORT!!");
	        try{
	        	maxStartFlowId 	= wDAO.getMaxStartFlowIdByUser(workflowId, user.getUserName());
	        	str				= wBO.getBuildsReportHistoryJsonList(workflowId, maxStartFlowId);
	        	logger.info("Exit from WorkflowReportAJAX()...  : jsonList : "+jsonList.size());
    			PrintWriter pw = resp.getWriter();
    			pw.println(str);
    			
	    	}catch(Exception e){
	    		logger.error("Error in WorkflowReportAJAX()!", e);
				try {
					throw new Exception(e.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}	
		}
}