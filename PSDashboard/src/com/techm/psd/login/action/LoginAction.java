package com.techm.psd.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.techm.psd.application.action.ApplicationAction;
import com.techm.psd.auth.AuthFilterConstants;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.login.form.LoginForm;
import com.techm.psd.user.dao.UserDAOImpl;

public class LoginAction extends BaseAction{

	private Logger logger = Logger.getLogger(LoginAction.class);
	
	public ActionForward login(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into LoginAction.login()...");
		String forward 			= "login";
		HttpSession session 	= request.getSession();
		LoginForm loginForm 	= (LoginForm) form;
		
		String userId;
		String password;
		
		userId= loginForm.getUserName();
    	if(userId.equals(null) || userId.equals("")){
    		userId=request.getParameter("userId");
    	}
    	
    	password= loginForm.getPassword();
    	if(password.equals(null) || password.equals("")){
    		password=request.getParameter("password");
    	}
    	
    	String url= session.getAttribute("uri").toString();
    	
    	if(userId == "" || userId == null || password == "" || password == null ){
    		return mapping.findForward("login");
    	}else{
	    	UserDAOImpl userDAO = new UserDAOImpl();
			UserDTO 	user 	= userDAO.getUser(userId);
			if(user.getPassword()!= null && user.getPassword().equalsIgnoreCase(password)){
				session.setAttribute(AuthFilterConstants.USER_SESSION_KEY, user);
				
				session.setAttribute("user", userId);
				response.sendRedirect(url);
				logger.info("Exit from LoginAction.login()...");
				return mapping.findForward(null);	
			}else{
				return mapping.findForward("login");
			}
			
    	}
	}
	
	public ActionForward loginAuth(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into loginAuth.login()...");
		String forward 			= "login";
		HttpSession session 	= request.getSession(false);
		LoginForm loginForm 	= (LoginForm) form;
		logger.info("Exit from loginAuth.login()...");
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return login(mapping, form, request, response);
	}
}
