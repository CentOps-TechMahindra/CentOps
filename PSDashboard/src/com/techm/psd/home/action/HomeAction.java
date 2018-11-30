package com.techm.psd.home.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.home.form.HomeForm;

public class HomeAction extends BaseAction{

	private Logger logger = Logger.getLogger(HomeAction.class);
	
	public ActionForward home(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into HomeAction.home()...");
		String 			forward = "home";
		HomeForm 		hForm	= (HomeForm)form; 
		ApplicationDAO 	dao		= new ApplicationDAOImpl();
		
		hForm.setAppDTOList(dao.getApplicationList());
		logger.info("Exit from HomeAction.home()...");
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return home(mapping, form, request, response);
	}
}
