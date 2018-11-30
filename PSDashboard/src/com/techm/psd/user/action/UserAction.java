package com.techm.psd.user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.exception.PSDException;
import com.techm.psd.user.dao.UserDAO;
import com.techm.psd.user.dao.UserDAOImpl;
import com.techm.psd.user.form.UserForm;

public class UserAction extends BaseAction{

	private Logger logger = Logger.getLogger(UserAction.class);
	
	public ActionForward requestAccess(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into UserAction.requestAccess()...");
		UserForm uForm 		= (UserForm)form;
		String forward 		= "requestAccess";
		HttpSession session = request.getSession(false);
		UserDTO uDTO	 	= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
		
		{
			setInitialValues(uForm);
			buildUserForm(uForm, uDTO);										// Build userForm using UserDTO from session.
		}
		logger.info("Exit from UserAction.requestAccess()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward saveAccessRequest(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		logger.info("Enters into UserAction.saveRequest()...");
		
		UserForm uForm 	= (UserForm)form;
		String forward 	= "requestAccess";
		UserDAO uDAO 	= new UserDAOImpl();
		
		UserDTO uDTO 	= buildUserDTO(uForm);
		boolean result  = uDAO.saveUpdateUserAccessRequest(uDTO);
		
		//TODO: ADD Message after saving the request
		setInitialValues(uForm);
		
		//IF USER REQUEST ADDED/UPDATED SUCCESSFULLY THEN UPDATE USER_SESSION_KEY
		if(result){
			
			//uncomment below code if wants to update requester session values.
			
			/*
			HttpSession session 	= request.getSession(false);
			UserDTO 	userSession	= UserSessionUtil.getUserSession(session);	// Getting the userDTO from session
			
			//UPDATE THE USER_TEAM, Change the USER LEVEL TO READ_ONLY, Remove PROFILE_ID and APPLICATION_ID 
			userSession.setUserTeam		(uDTO.getUserTeam());
			userSession.setLevelId		(1);
			userSession.setLevelName	("ReadOnly");
			userSession.setProfileId	(0);
			userSession.setProfileName	("");
			userSession.setAppId		(0);
			userSession.setAppName		("");
			
			if (session.getAttribute(AuthFilterConstants.USER_SESSION_KEY) != null) { // First time login today
				session.setAttribute(AuthFilterConstants.USER_SESSION_KEY, userSession);
				LOGGER.debug("USER_SESSION_KEY UPDATED.");
			}*/
			
			//TODO: ADD Response message after request save successfully.
			request.setAttribute("responseMsg", "Your Access Requests has been saved successfully!");
		}else{
			request.setAttribute("responseMsg", "Unable to save your Access Request!");
		}
		
		logger.info("Exit from UserAction.saveRequest()...");
		return mapping.findForward(forward);
	}
	
	void setInitialValues(UserForm uForm){
		LOGGER.info("Enter into UserAction.setInitialValues()...");
		//CommonDAO cDAO  = new CommonDAOImpl();
		ApplicationDAO aDAO = new ApplicationDAOImpl();
		UserDAO 	uDAO = new UserDAOImpl();
		try {
			uForm.setAppList	(aDAO.getApplicationList()	);
			uForm.setUserLvlList(uDAO.getUserLevelList()	);
			uForm.setProfileList(uDAO.getProfileList()		);
		} catch (PSDException e) {
			LOGGER.error("Error in UserAction.setInitialValues()...", e);
			e.printStackTrace();
		}
		LOGGER.info("Exit from UserAction.setInitialValeues()...");
	}
	
	void buildUserForm(UserForm uForm, UserDTO uDTO){
		
		uForm.setUserId			(uDTO.getUserId()		);
		uForm.setFirstName		(uDTO.getFirstName()	);
		uForm.setLastName		(uDTO.getLastName()		);
		uForm.setUserName		(uDTO.getUserName()		);
		uForm.setUserEmailId	(uDTO.getUserEmailId()	);
		uForm.setUserContactNo	(uDTO.getUserContactNo());
		uForm.setUserTeam		(uDTO.getUserTeam()		);
		uForm.setUserLvlId		(uDTO.getLevelId()		);
		uForm.setUserLvlName	(uDTO.getLevelName()	);
		uForm.setAppId			(uDTO.getAppId()		);
		uForm.setAppName		(uDTO.getAppName()		);
		uForm.setProfileId		(uDTO.getProfileId()	);
		uForm.setProfileName	(uDTO.getProfileName()	);
		//uForm.setComment		(uDTO.getComment()		);
	}
	
	private UserDTO buildUserDTO(UserForm uForm){
		UserDTO uDTO 		= 	new UserDTO();
		
		uDTO.setUserId			(uForm.getUserId()			);
		uDTO.setFirstName		(uForm.getFirstName()		);
		uDTO.setLastName		(uForm.getLastName()		);
		uDTO.setUserName		(uForm.getFirstName()+uForm.getLastName());
		uDTO.setUserEmailId		(uForm.getUserEmailId()		);
		uDTO.setUserContactNo	(uForm.getUserContactNo()	);
		uDTO.setUserTeam		(uForm.getUserTeam()		);
		uDTO.setLevelId			(uForm.getUserLvlId()		);
		uDTO.setLevelName		(uForm.getUserLvlName()		);
		uDTO.setAppId			(uForm.getAppId()			);
		uDTO.setAppName			(uForm.getAppName()			);
		uDTO.setProfileId		(uForm.getProfileId()		);
		uDTO.setProfileName		(uForm.getProfileName()		);
		
		uDTO.setRequestedLevelId(uForm.getRequestedLevelId());
		uDTO.setRequestedAppId 	(uForm.getRequestedAppId() 	);
		uDTO.setRequestedProfileId(uForm.getRequestedProfileId());
		uDTO.setRequesterComment(uForm.getRequesterComment());
		
		return uDTO;
	}

	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return requestAccess(mapping, form, request, response);
	}
}
