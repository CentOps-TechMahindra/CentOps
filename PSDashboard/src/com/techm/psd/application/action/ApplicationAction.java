package com.techm.psd.application.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.application.form.ApplicationForm;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.UserSessionUtil;

public class ApplicationAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(ApplicationAction.class);
	
	public ActionForward addApplication(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.addApplication()...");
		String forward 			= "addApplication";
		HttpSession session 	= request.getSession(false);
		
		logger.info("Exit from ApplicationAction.addApplication()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward saveApplication(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.saveApplication()...");
		ApplicationForm aForm 	= (ApplicationForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		String	userId			= user.getUserId();
		boolean result 			= false; 
		String forward 			= "addApplication";
		ApplicationDTO aDTO 	= new ApplicationDTO();
		ApplicationDAO aDAO 	= new ApplicationDAOImpl();
		
		aForm.setCreateBy(userId);	//Adding Current User UID
		aForm.setUpdateBy(userId);
		
		aDTO.setAppName	(aForm.getAppName()	);
		aDTO.setCreateBy(aForm.getCreateBy());
		aDTO.setUpdateBy(aForm.getUpdateBy());
		aDTO.setDesc	(aForm.getDesc()	);
		aDTO.setAppsId	(aForm.getAppsId()	);
		
		result = aDAO.saveApplication(aDTO);
		
		//Setting appId & Created Date in aFORM 
		aForm.setAppId(aDTO.getAppId());
		aForm.setCreateDate(aDTO.getCreateDate());
		
		if(result){
			//return viewApplication(mapping, aForm, request, response);
			return applicationTracker(mapping, aForm, request, response);
		}

		logger.info("Exit from ApplicationAction.saveApplication()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward viewApplication(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.viewApplication()...");
		ApplicationForm aForm 	= (ApplicationForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		
		String forward 			= "viewApplication";
		ApplicationDTO aDTO 	= new ApplicationDTO();
		ApplicationDAO aDAO 	= new ApplicationDAOImpl();
		
		int appId				= 0;
		
		//GETTING APPLICATION_ID TO FETCH THE DATA: START
		if(request.getParameter("appId") != null){
			appId = Integer.parseInt(request.getParameter("appId"));
		}else if(aForm.getAppId() != 0){
			appId = aForm.getAppId();
		}else{
			logger.info("In ApplicationAction.viewApplication()... appId is not available!!!");
		}
		
		aDTO = aDAO.getApplicationDetailsById(appId);
		
		//ApplicationDTO to ApplicationForm
		aForm.setAppId		(aDTO.getAppId()		);
		aForm.setAppName	(aDTO.getAppName()		);
		aForm.setCreateBy	(aDTO.getCreateBy()		);
		aForm.setCreateDate	(aDTO.getCreateDate()	);
		aForm.setUpdateBy	(aDTO.getUpdateBy()		);
		aForm.setUpdateDate	(aDTO.getUpdateDate()	);
		aForm.setDesc		(aDTO.getDesc()			);
		aForm.setAppsId		(aDTO.getAppsId()		);
		
		//END
		
		//GETTING APPLICATION_ID TO FETCH THE DATA: END
		
		logger.info("Exit from ApplicationAction.viewApplication()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward editApplication(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.editApplication()...");
		ApplicationForm aForm 	= (ApplicationForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		
		String forward 			= "editApplication";
		ApplicationDTO aDTO 	= new ApplicationDTO();
		ApplicationDAO aDAO 	= new ApplicationDAOImpl();
		
		int appId				= 0;
		
		//GETTING APPLICATION_ID TO FETCH THE DATA: START
		if(request.getParameter("appId") != null){
			appId = Integer.parseInt(request.getParameter("appId"));
		}else if(aForm.getAppId() != 0){
			appId = aForm.getAppId();
		}else{
			logger.info("In ApplicationAction.editApplication()... appId is not available!!!");
		}
		
		aDTO = aDAO.getApplicationDetailsById(appId);
		
		//ApplicationDTO to ApplicationForm
		aForm.setAppId		(aDTO.getAppId()		);
		aForm.setAppName	(aDTO.getAppName()		);
		aForm.setCreateBy	(aDTO.getCreateBy()		);
		aForm.setCreateDate	(aDTO.getCreateDate()	);
		aForm.setUpdateBy	(aDTO.getUpdateBy()		);
		aForm.setUpdateDate	(aDTO.getUpdateDate()	);
		aForm.setDesc		(aDTO.getDesc()			);
		aForm.setAppsId		(aDTO.getAppsId()		);
		//END
		
		//GETTING APPLICATION_ID TO FETCH THE DATA: END
		
		logger.info("Exit from ApplicationAction.editApplication()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward updateApplication(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.updateApplication()...");
		ApplicationForm aForm 	= (ApplicationForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		String	userId			= user.getUserId();
		boolean result 			= false; 
		String forward 			= "addApplication";
		ApplicationDTO aDTO 	= new ApplicationDTO();
		ApplicationDAO aDAO 	= new ApplicationDAOImpl();
		
		//aForm.setCreateBy(userId);	//Adding Current User UID
		aForm.setUpdateBy(userId);
		aDTO.setAppId	(aForm.getAppId()	);
		aDTO.setAppName	(aForm.getAppName()	);
		aDTO.setUpdateBy(aForm.getUpdateBy());
		aDTO.setDesc	(aForm.getDesc()	);
		aDTO.setAppsId	(aForm.getAppsId()	);
		result = aDAO.updateApplication(aDTO);
		
		if(result){
			return viewApplication(mapping, aForm, request, response);
			//return applicationTracker(mapping, aForm, request, response);
		}

		logger.info("Exit from ApplicationAction.updateApplication()...");
		//return mapping.findForward(forward);
		return applicationTracker(mapping, aForm, request, response);
	}
	
	public ActionForward applicationTracker(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into ApplicationAction.applicationTracker()...");
		ApplicationForm aForm 	= (ApplicationForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		
		String forward 			= "appTracker";
		ApplicationDTO aDTO 	= new ApplicationDTO();
		ApplicationDAO aDAO 	= new ApplicationDAOImpl();
		aForm.setAppDTOList(aDAO.getApplicationList());
		
		logger.info("Exit from ApplicationAction.applicationTracker()...");
		return mapping.findForward(forward);
	}
		
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return addApplication(mapping, form, request, response);
	}
}
