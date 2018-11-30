package com.techm.psd.user.action;

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
import com.techm.psd.user.dao.UserDAO;
import com.techm.psd.user.dao.UserDAOImpl;
import com.techm.psd.user.form.UserForm;

public class AdminUserAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(AdminUserAction.class);
	
	public ActionForward viewAccessRequest(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into AdminUserAction.viewAccessRequest()...");
		String forward 				= "viewAccessRequests";
		HttpSession session 		= request.getSession(false);
		UserDTO 	user			= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		UserForm 	uForm 		= (UserForm)form;
		UserDAO	 	uDAO		= new UserDAOImpl();
		
		uForm.setApproverComment("");
		if(user.getLevelId() >= 10){
			List<UserDTO> uDTOList	= uDAO.getAllPenidngAccessRequest();
			uForm.setUserDtoList(uDTOList);
		}else{
			forward	= "dashboard";
			request.setAttribute("unauthorized", "You are not authorized to Review Users Request!");
		}
		logger.info("Exit from AdminUserAction.viewAccessRequest()... forward : "+forward);
		return mapping.findForward(forward);
	}
	
	public ActionForward Approve(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into AdminUserAction.Approve()...");
		//String 		forward 	= "viewAccessRequests";
		UserForm 	uForm 		= (UserForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO 	user		= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		UserDAO		uDAO		= new UserDAOImpl();
		
		boolean 	result		= false;
		String 		approvedBy	= user.getUserId();
		String 		status		= "APPROVED";
		String 		comment		= uForm.getApproverComment();
		int[] 		selRequests	= uForm.getSelRequests();
		
		result 					= uDAO.approveRejectUserAccessRequestList(selRequests, approvedBy, status, comment);
		
		request.setAttribute("success", "Approved! Selected Requests has been updated successfully!");
		logger.info("Exit from AdminUserAction.Approve()...");
		//return mapping.findForward(forward);
		return viewAccessRequest(mapping, uForm, request, response);
	}
	
	public ActionForward Reject(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into AdminUserAction.Reject()...");
		//String 		forward 	= "viewAccessRequests";
		UserForm 	uForm 		= (UserForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO 	user		= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		UserDAO		uDAO		= new UserDAOImpl();
		
		boolean 	result		= false;
		String 		approvedBy	= user.getUserId();
		String 		status		= "REJECTED";
		String 		comment		= uForm.getApproverComment()+"";
		int[] 		selRequests	= uForm.getSelRequests();
		
		result 					= uDAO.approveRejectUserAccessRequestList(selRequests, approvedBy, status, comment);
		
		request.setAttribute("success", "Rejected! Selected Requests has been updated successfully!");
		logger.info("Exit from AdminUserAction.Reject()...");
		//return mapping.findForward(forward);
		return viewAccessRequest(mapping, uForm, request, response);
	}
	
	public ActionForward appManager(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception{
				
		logger.info("Enters into AdminUserAction.appManager()...");
		String forward 				= "appManager";
		HttpSession session 		= request.getSession(false);
		UserDTO 	user			= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		
		logger.info("Exit from AdminUserAction.appManager()... forward : "+forward);
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return viewAccessRequest(mapping, form, request, response);
	}
}
