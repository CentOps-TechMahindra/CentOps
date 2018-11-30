package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.jenkins.bo.WorkflowBO;

public class WorkflowHistoryAJAX  extends HttpServlet {

	private Logger logger = Logger.getLogger(WorkflowHistoryAJAX.class);
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
			logger.info("Enters into WorkflowHistoryAJAX()!");
			JSONObject jsonObj 			= new JSONObject();
			WorkflowBO wBO 				= new WorkflowBO();
			//ReportBO   rBO				= new ReportBO();
			List<JSONObject> jsonList 	= new ArrayList<JSONObject>();
			String action 				= "";
			int workflowId 				= 0;
			int startFlowId				= 0;
			String result				= "FAIL";
			
			if(req.getParameter("action") != null) 			{action	= req.getParameter("action").toString();}
			if(req.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(req.getParameter("workflowId").toString());}
			if(req.getParameter("startFlowId") != null) 	{startFlowId		= Integer.parseInt(req.getParameter("startFlowId").toString());}
			
			logger.info("WORKFLOW HISTORY : action : "+action+", workflowId : "+workflowId);
	        try{
	        	if(action.equalsIgnoreCase("LAST_START_FLOW_HISTORY")){
	        		logger.info("LAST_START_FLOW_HISTORY");
	        		jsonList = wBO.getStartFlowHistoryJsonList(workflowId, action);
		        	if(jsonList.size()>0){
		        		result	= "SUCCESS";
			        	jsonObj.put("jsonList", jsonList);
		        	}
	        	}else if(action.equalsIgnoreCase("START_FLOW_HISTORY")){
	        		logger.info("Getting START_FLOW_HISTORY");
	        		jsonList = wBO.getStartFlowHistoryJsonList(workflowId, action);
		        	if(jsonList.size()>0){
		        		result	= "SUCCESS";
			        	jsonObj.put("jsonList", jsonList);
		        	}
	        	}else if(action.equalsIgnoreCase("JENKINS_HISTORY")){
	        		logger.info("Getting Details JENKINS_HISTORY");
	        		jsonList 		= wBO.getBuildsHistoryJsonList(workflowId, startFlowId);
		        	if(jsonList.size()>0){
		        		result	= "SUCCESS";
			        	jsonObj.put("jsonList", jsonList);
		        	}
	        	}else if(action.equalsIgnoreCase("REPORT_HISTORY")){
	        		logger.info("Getting Details REPORT_HISTORY");
	        		String resultStr= wBO.getBuildsReportHistoryJsonList(workflowId, startFlowId);
	        		result	= "SUCCESS";
		        	jsonObj.put("resultStr", resultStr);
		        }
	        	jsonObj.put("result", result);
		    	logger.info("Exit from WorkflowHistoryAJAX()...  : jsonList : "+jsonList.size());
	    	}catch(MalformedURLException me){
	    		logger.error("Exception in WorkflowHistoryAJAX... ", me);
	    		try {
					jsonObj.put("result", result);
				} catch (JSONException e) {
					logger.error("Exception in WorkflowHistoryAJAX... "+e.getMessage());
				}
    			logger.error("Error getting build history! "+me.getMessage());
	    	}catch(Exception e){
	    		logger.error("Exception in WorkflowHistoryAJAX... "+e.getMessage());
				try {
					throw new Exception(e.toString());
				} catch (Exception e1) {
					logger.error("Exception in WorkflowHistoryAJAX... "+e1.getMessage());
				}
			}finally{
				PrintWriter pw = resp.getWriter();
				pw.println(jsonObj);
			}
	    }
}
