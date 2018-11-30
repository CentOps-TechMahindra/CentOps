package com.techm.psd.console.action;

import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.techm.psd.appManager.dao.AppManagerDAOImpl;
import com.techm.psd.common.dto.UrlDTO;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.console.form.ConsoleForm;
import com.techm.psd.dashboard.action.DashboardAction;


public class ConsoleAction extends BaseAction{

	private Logger logger = Logger.getLogger(DashboardAction.class);
	
	/**Method that redirects user to console.jsp which displays report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author aj797k
	 */
	public ActionForward console(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		logger.info("Enters into console()...");
		String 				forward		= "console";
		ConsoleForm 		cForm		= (ConsoleForm)form;
		AppManagerDAOImpl 	dao 		= new AppManagerDAOImpl();
		ArrayList<UrlDTO> 	urlList 	= new ArrayList<UrlDTO>();
		urlList 						= (ArrayList<UrlDTO>) dao.getAllUrls();
		
		cForm.setUrlList(urlList);
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return console(mapping, form, request, response);
	}

}
