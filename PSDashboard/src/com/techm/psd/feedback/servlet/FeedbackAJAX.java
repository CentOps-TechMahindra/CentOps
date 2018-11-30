package com.techm.psd.feedback.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.feedback.dao.FeedbackDAO;
import com.techm.psd.feedback.dao.FeedbackDAOImpl;
import com.techm.psd.feedback.dto.FeedbackDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FeedbackAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long 	serialVersionUID 	= 1L;
	private Logger 				logger 				= Logger.getLogger(FeedbackAJAX.class);
	SimpleDateFormat 			dateFormat 			= new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into FeedbackAJAX()!");
		
		//UserDTO 			user 		= UserSessionUtil.getUserSession(req);
		JSONObject 			jsonObj 	= new JSONObject();
		//FeedbackDAO 		fDAO		= new FeedbackDAOImpl();
		//List<JSONObject> 	jsonList 	= new ArrayList<JSONObject>();
		FeedbackDAO			fDao		= new FeedbackDAOImpl();
		List<FeedbackDTO> 	fDTOList	= new ArrayList<FeedbackDTO>();
		Gson 				gson 		= new GsonBuilder().setPrettyPrinting().create();
        
		String 				jsonArray 	= "";		// RESPONSE DATA
        int 				page 		= 0;		// Page Number
    	int 				rows 		= 0;		// Total number of rows to display
		int 				total 		= 0;		// Total Pages.
		int 				records 	= 0;		// 
		String 				sidx  		= null;		// SORT COLUMN
		String 				sord  		= null;		// SORT By ASCD/DESC
		
		if(req.getParameter("page") 	!= null) 	{page	= Integer.parseInt(req.getParameter("page").toString());}
		if(req.getParameter("rows") 	!= null) 	{rows	= Integer.parseInt(req.getParameter("rows").toString());}
		if(req.getParameter("total") 	!= null) 	{total	= Integer.parseInt(req.getParameter("total").toString());}
		if(req.getParameter("records") 	!= null) 	{records= Integer.parseInt(req.getParameter("records").toString());}
		if(req.getParameter("sidx") 	!= null) 	{sidx 	= req.getParameter("sidx").toString();}
		if(req.getParameter("sord") 	!= null) 	{sord 	= req.getParameter("sord").toString();}
		
		sidx = "FEEDBACK_ID";																		// TODO: SORT COLUMN 
		int upperLimit	= rows*page;																// Total number of records to fetch from database.
		int offset		= rows*(page-1);															// Start record number
		try {
			records		= fDao.getAllNewInprogressFeedbackCount();									//fDTOList.size();
			total		= (int) Math.ceil((double)records / rows);									//records/rows;
			fDTOList	= fDao.getAllNewInprogressFeedback(upperLimit, offset, sidx, sord);
			//jsonList 	= generateFeedbackJsonList(fDTOList);
			jsonArray 	= gson.toJson(fDTOList);
            
			jsonArray 	=   "{"
            				+ "\"page\"		:" + page
            				+",\"total\"	:" + total
            				+",\"records\"	:" + records 
            				+",\"rows\"		:" + jsonArray 
            				+"}";
            
        } catch (Exception e) {
        	logger.error("Enters into FeedbackAJAX()!"+e.getMessage());
    		try {
				jsonObj.put("result", "ERROR");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			logger.info("Exit from FeedbackAJAX()!");
			PrintWriter pw 		= resp.getWriter();
			pw.println(jsonArray);
		}
	}

	/*public List<JSONObject> generateFeedbackJsonList(List<FeedbackDTO> fDTOList) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		Iterator itr = fDTOList.iterator();
		while(itr.hasNext()){
			FeedbackDTO fObj 	= (FeedbackDTO)itr.next();
			JSONObject jsonObj 	= new JSONObject();
			
			jsonObj.put("feedbackId", 	fObj.getFeedbackId()	);
			jsonObj.put("feedback", 	fObj.getFeedback()		);
			jsonObj.put("workflowName",	fObj.getWorkflowName()	);
			jsonObj.put("startFlowId",	fObj.getStartFlowId()	);
			jsonObj.put("addedBy",		fObj.getAddedBy()		);
			jsonObj.put("addedDate",	fObj.getAddedDate()		);
			jsonObj.put("status",		fObj.getStatus()		);
			jsonObj.put("comment",		fObj.getComment()		);
			jsonList.add(jsonObj);
		}
		return jsonList;
	}*/
}
