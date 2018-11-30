package com.techm.psd.feedback.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.feedback.dao.FeedbackDAO;
import com.techm.psd.feedback.dao.FeedbackDAOImpl;
import com.techm.psd.feedback.dto.FeedbackDTO;
import com.techm.psd.feedback.form.FeedbackForm;
import com.techm.psd.user.dao.UserDAO;
import com.techm.psd.user.dao.UserDAOImpl;
import com.techm.psd.user.form.UserForm;

public class FeedbackAction extends BaseAction{

	private Logger logger = Logger.getLogger(FeedbackAction.class);
	
	/*public ActionForward viewFeedback(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into FeedbackAction.viewFeedback()...");
		FeedbackForm fForm 	= (FeedbackForm)form;
		String forward 		= "viewFeedback";
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		int feedbackId		= 0;
		//GETTING APPLICATION_ID TO FETCH THE DATA: START
		if(request.getParameter("feedbackId") != null){
			feedbackId = Integer.parseInt(request.getParameter("feedbackId"));
		}else if(fForm.getFeedbackId() != 0){
			feedbackId = fForm.getFeedbackId();
		}else{
			logger.info("In FeedbackAction.viewFeedback()... feedbackId is not available!!!");
		}
		
		FeedbackDTO 	fDTO 	= new FeedbackDTO();
		FeedbackDAO 	fDAO	= new FeedbackDAOImpl();
		fDTO = fDAO.getFeedbackDetailsById(feedbackId);

		//FeedbackDTO to FeedbackForm
		fForm.setFeedbackId		(fDTO.getFeedbackId());
		fForm.setFeedback		(fDTO.getFeedback());
		fForm.setWorkflowId		(fDTO.getWorkflowId());
		fForm.setWorkflowName	(fDTO.getWorkflowName());
		fForm.setStartFlowId	(fDTO.getStartFlowId());
		fForm.setAddedBy		(fDTO.getAddedBy());
		fForm.setAddedDate		(fDTO.getAddedDate());
		fForm.setUpdatedBy		(fDTO.getUpdatedBy());
		fForm.setUpdatedDate	(fDTO.getUpdatedDate());
		fForm.setStatus			(fDTO.getStatus());
		
		
		logger.info("Exit from FeedbackAction.viewFeedback()...");
		return mapping.findForward(forward);
	}*/
	
	public ActionForward viewFeedback(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into FeedbackAction.viewFeedback()...");
		//FeedbackForm fForm 	= (FeedbackForm)form;
		String forward 		= "viewFeedback";
		/*HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		List<FeedbackDTO> 	fDTO 	= new ArrayList<FeedbackDTO>();
		FeedbackDAO 		fDAO	= new FeedbackDAOImpl();
		
		fDTO = fDAO.getAllClosedFeedback(10, 0, "FEEDBACK_ID", "DESC");
		fForm.setFeedbackDTOList(fDTO);*/
		
		logger.info("Exit from FeedbackAction.viewFeedback()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward reviewNewFeedback(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into FeedbackAction.reviewNewFeedback()...");
		//FeedbackForm fForm 	= (FeedbackForm)form;
		String forward 		= "reviewNewFeedback";
		//HttpSession session = request.getSession(false);
		//UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		/*List<FeedbackDTO> fDTO 	= new ArrayList<FeedbackDTO>();
		FeedbackDAO fDAO 	= new FeedbackDAOImpl();
		//fDTO = fDAO.getAllNewFeedback(10, 5);
		fForm.setFeedbackDTOList(fDTO);*/
		
		logger.info("Exit from FeedbackAction.reviewNewFeedback()...");
		return mapping.findForward(forward);
	}
	
	/*public ActionForward Approve(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into FeedbackAction.Approve()...");
		
		String forward 		= "reviewNewFeedback";
		FeedbackForm fForm 	= (FeedbackForm)form;
		HttpSession session = request.getSession(false);
		UserDTO 	user	= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		FeedbackDAO fDAO 	= new FeedbackDAOImpl();
		
		boolean 	result		= false;
		String 		approvedBy	= user.getUserId();
		String 		status		= "APPROVED";
		int[]		selFeedbacks= fForm.getSelFeedbacks();
		result 					= fDAO.approveRejectFeedbackList(selFeedbacks, approvedBy, status);
		
		request.setAttribute("success", "Approved! Selected Requests has been updated successfully!");
		logger.info("Exit from FeedbackAction.Approve()...");
		//return mapping.findForward(forward);
		return reviewNewFeedback(mapping, fForm, request, response);
	}*/
	
	/*public ActionForward Reject(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into FeedbackAction.Reject()...");
		String forward 		= "reviewNewFeedback";
		FeedbackForm fForm 	= (FeedbackForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO 	user		= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		FeedbackDAO fDAO 	= new FeedbackDAOImpl();
		
		boolean 	result		= false;
		String 		approvedBy	= user.getUserId();
		String 		status		= "REJECTED";
		int[]		selFeedbacks= fForm.getSelFeedbacks();
		
		result 					= fDAO.approveRejectFeedbackList(selFeedbacks, approvedBy, status);
		
		request.setAttribute("success", "Rejected! Selected Requests has been updated successfully!");
		logger.info("Exit from FeedbackAction.Reject()...");
		//return mapping.findForward(forward);
		return reviewNewFeedback(mapping, fForm, request, response);
	}*/
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return viewFeedback(mapping, form, request, response);
	}
}