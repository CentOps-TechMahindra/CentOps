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
import org.json.JSONException;
import org.json.JSONObject;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class FixitWorkflowAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(FixitWorkflowAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into FixitWorkflowAJAX()!");
		
		//UserDTO user 				= UserSessionUtil.getUserSession(req);
		JSONObject 	jsonObj 		= new JSONObject();
		WorkflowDAO wDAO			= new WorkflowDAOImpl();
		boolean 	result 			= false;
		int			workflowId		= 0;
		List<FixItDTO> fixItList 	= null;
		
		try {
			if(req.getParameter("workflowId") != null) 	{workflowId	= Integer.parseInt(req.getParameter("workflowId").toString());}
			fixItList 		= wDAO.getFixItDTOList(workflowId);
			List<JSONObject> jsonList = generateFixitDTOJsonList(fixItList);
			jsonObj.put("result", true);
			jsonObj.put("jsonList", jsonList);
		} catch (Exception e) {
			logger.error("Error in FixitWorkflowAJAX()!", e);
			try {
				jsonObj.put("result", result);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			logger.info("Exit from FixitWorkflowAJAX()! -- : "+result);
			PrintWriter pw 		= resp.getWriter();
			pw.println(jsonObj);
		}
	}
	
	public List<JSONObject> generateFixitDTOJsonList(List<FixItDTO> fDTOList) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		Iterator<FixItDTO> itr = fDTOList.iterator();
		while(itr.hasNext()){
			FixItDTO fDTO = (FixItDTO)itr.next();
			JSONObject jsonObj = new JSONObject();
			
			String 	errorName			= fDTO.getErrorName();
			int 	errorCode			= fDTO.getErrorCode();
			String 	fixitWorkflowName	= fDTO.getFixitWorkflowName();
			int 	fixitWorkflowId		= fDTO.getFixitWorkflowId();
			
			jsonObj.put("errorName", 		errorName);
			jsonObj.put("errorCode", 		errorCode);
			jsonObj.put("fixitWorkflowName",fixitWorkflowName);
			jsonObj.put("fixitWorkflowId",	fixitWorkflowId);
			jsonList.add(jsonObj);
		}
		return jsonList;
	}
}