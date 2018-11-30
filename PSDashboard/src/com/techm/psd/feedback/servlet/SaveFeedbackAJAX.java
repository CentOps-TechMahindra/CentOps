package com.techm.psd.feedback.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.techm.psd.feedback.dao.FeedbackDAO;
import com.techm.psd.feedback.dao.FeedbackDAOImpl;
import com.techm.psd.feedback.dto.FeedbackDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.email.FeedbackNotificationEmail;

public class SaveFeedbackAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long 	serialVersionUID 	= 1L;
	private static final String _NEW 				= "NEW";
	
	private Logger logger = Logger.getLogger(SaveFeedbackAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into SaveFeedbackAJAX()!");
		
		UserDTO user 				= UserSessionUtil.getUserSession(req);
		JSONObject 	jsonObj 		= new JSONObject();
		FeedbackDAO fDAO			= new FeedbackDAOImpl();
		boolean 	result 			= false;
		int			workflowId		= 0;
		int			startFlowId		= 0;
		String		feedback		= "";
		int			feedbackId		= 0;
		FeedbackDTO	fDTO;
		
		try {
			
			if(req.getParameter("workflowId") != null) 	{workflowId	= Integer.parseInt(req.getParameter("workflowId").toString());}
			if(StringUtils.isNotEmpty(req.getParameter("startFlowId")) && StringUtils.isNumeric(req.getParameter("startFlowId"))){
				startFlowId	= Integer.parseInt(req.getParameter("startFlowId").toString());
			}
			if(req.getParameter("feedback") != null) 	{feedback	= req.getParameter("feedback").toString();}
			
			feedbackId	= fDAO.saveFeedback(feedback, workflowId, startFlowId, user.getUserId());
			
			if(feedbackId > 0){
				result = true;
				fDTO 		= fDAO.getFeedbackDetailsById(feedbackId);
				FeedbackNotificationEmail email = new FeedbackNotificationEmail();
				email.sendFeedbaclEmail(user, fDTO, _NEW);
			}
			jsonObj.put("result", result);
		} catch (Exception e) {
			logger.error("Enters into SaveFeedbackAJAX()!"+e.getMessage());
			try {
				jsonObj.put("result", result);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			logger.info("Exit from SaveFeedbackAJAX()! -- : "+result);
			PrintWriter pw 		= resp.getWriter();
			pw.println(jsonObj);
		}
	}
}