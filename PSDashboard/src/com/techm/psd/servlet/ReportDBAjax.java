package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.bo.ReportBO;

public class ReportDBAjax extends HttpServlet {

	private Logger logger = Logger.getLogger(ReportDBAjax.class);
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
			ReportBO rBO 	= new ReportBO();
        	DatabaseBean 	dbBean 	= new DatabaseBean();
			JSONObject 		jsonObj = new JSONObject();
			//List<String>	sList	= new ArrayList<String>();
			//JSONArray jArray = null;
			boolean result = false; 
				
			String db_type		= "";
			String db_username	= "";
			String db_password	= "";
			String db_hostname	= "";
			int	   db_port		= 0;
			String db_sid		= "";
			String db_table_name= "";
			
			if(req.getParameter("db_type") != null) 		{db_type		= req.getParameter("db_type").toString();		}
			if(req.getParameter("db_username") != null)	 	{db_username	= req.getParameter("db_username").toString();	}
			if(req.getParameter("db_password") != null) 	{db_password	= req.getParameter("db_password").toString();	}
			if(req.getParameter("db_hostname") != null) 	{db_hostname	= req.getParameter("db_hostname").toString();	}
			if(req.getParameter("db_port") != null) 		{db_port		= Integer.parseInt(req.getParameter("db_port").toString());}
			if(req.getParameter("db_sid") != null) 			{db_sid			= req.getParameter("db_sid").toString();		}
			if(req.getParameter("db_table_name") != null)	{db_table_name	= req.getParameter("db_table_name").toString();	}
			
			dbBean.setDatabaseType	(db_type);
			dbBean.setUsername		(db_username);
			dbBean.setPassword		(db_password);
			dbBean.setHostname		(db_hostname);
			dbBean.setPort			(db_port);
			dbBean.setSid			(db_sid);
			dbBean.setReportTable	(db_table_name);
			logger.info("WORKFLOW REPORT!!");
	        try{
	        	result = rBO.isValidConnection(dbBean);
	        	jsonObj.put("result", result);
				
	        	logger.info("Exit from WorkflowReportAJAX()...  : jsonList : "+jsonObj);
    			PrintWriter pw = resp.getWriter();
    			pw.println(jsonObj);
    		}catch(Exception e){
    			logger.info("Error in WorkflowReportAJAX()!", e);
    			try {
					throw new Exception(e.toString());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}	
		}
}
