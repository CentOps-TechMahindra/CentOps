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

import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.email.FeedbackNotificationEmail;
import com.techm.psd.feedback.dao.FeedbackDAO;
import com.techm.psd.feedback.dao.FeedbackDAOImpl;
import com.techm.psd.feedback.dto.FeedbackDTO;

public class UpdateFeedbackAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long 	serialVersionUID 	= 1L;
	private static final String _UPDATE 		= "UPDATE";
	private Logger logger = Logger.getLogger(SaveFeedbackAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into UpdateFeedbackAJAX()!");
		
		UserDTO user 				= UserSessionUtil.getUserSession(req);
		JSONObject 	jsonObj 		= new JSONObject();
		FeedbackDAO fDAO			= new FeedbackDAOImpl();
		FeedbackDTO	fDTO;
		int 		result 			= 0;
		int			feedbackId		= 0;
		String 		status			= "";
		String 		comment			= "";
		try {
			
			if(StringUtils.isNotEmpty(req.getParameter("feedbackId")) && StringUtils.isNumeric(req.getParameter("feedbackId"))){
				feedbackId	= Integer.parseInt(req.getParameter("feedbackId").toString());
			}
			if(req.getParameter("status") != null) 	{status	= req.getParameter("status").toString();}
			if(req.getParameter("comment") != null) {comment	= req.getParameter("comment").toString();}
			
			result	= fDAO.updateFeedback(feedbackId, status, comment, user.getUserId());

			if(result > 0){
				fDTO 		= fDAO.getFeedbackDetailsById(feedbackId);
				FeedbackNotificationEmail email = new FeedbackNotificationEmail();
				email.sendFeedbaclEmail(user, fDTO, _UPDATE);
			}
			jsonObj.put("result", result);
		} catch (Exception e) {
			logger.info("Error in UpdateFeedbackAJAX()!"+e.getMessage());
			try {
				jsonObj.put("result", result);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			logger.info("Exit from UpdateFeedbackAJAX()! -- : "+result);
			PrintWriter pw 		= resp.getWriter();
			pw.println(jsonObj);
		}
	}
}