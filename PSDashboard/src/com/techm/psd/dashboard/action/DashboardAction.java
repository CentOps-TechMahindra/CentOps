package com.techm.psd.dashboard.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.common.utils.SidebarUtils;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.dashboard.form.DashboardForm;
import com.techm.psd.exception.PSDException;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;
import com.techm.psd.workflowType.dao.WorkflowTypeDAO;
import com.techm.psd.workflowType.dao.WorkflowTypeDAOImpl;

public class DashboardAction extends BaseAction{

	private Logger logger = Logger.getLogger(DashboardAction.class);
	
	/**Method that redirects user to dashboard.jsp which displays report
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author aj797k
	 */
	public ActionForward dashboard(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into dashboard()...");
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		DashboardForm dForm = (DashboardForm)form;
		String forward = "dashboard";
		
		WorkflowDAO wDAO = new WorkflowDAOImpl();
		List<WorkflowDTO> wDTOList 		= new ArrayList<WorkflowDTO>();
		
		try {
			wDTOList 	= wDAO.getWorkflowList(user);
		} catch (PSDException e) {
			logger.error("Enters into dashboard()..."+e.getMessage());
			e.printStackTrace();
		}
		// JSON Object
		List<JSONObject> jsonObjList = new ArrayList<JSONObject>();
		getWorkflowJsonObjectList(wDTOList, jsonObjList);
		
		dForm.setJsonObject(jsonObjList);
		
		//Setting Application and Workflow JSON String for dropdown Grid filter. 
		dForm.setFilterAppList(createApplicationListForGridFilter());
		dForm.setFilterWorkflowTypeJsonStr(createWorkflowTypeListForGridFilter());
		
		//Setting below values for sidebar.jsp
		SidebarUtils sUtil = new SidebarUtils();
		sUtil.setSidebarData(user, request);
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return dashboard(mapping, form, request, response);
	}
	
	public void getWorkflowJsonObjectList(List<WorkflowDTO> wDTOList, List<JSONObject> jsonObjList) throws JSONException{
		logger.info("Enters into DashboardAction.getWorkflowJsonObjectList()...");
		//Creating JSON object from workflowDTO
		//int srno	= 0;
		Iterator<WorkflowDTO> itr = wDTOList.iterator();
		while(itr.hasNext()){
			JSONObject jsonObj = new JSONObject();
			WorkflowDTO wDTO = (WorkflowDTO)itr.next();
			
			//jsonObj.put("sno", ++srno);
			jsonObj.put("workflowId", wDTO.getWorkflowId());
			jsonObj.put("workflowName", wDTO.getWorkflowName());
			jsonObj.put("workflowType", wDTO.getWorkflowType());
			jsonObj.put("appId", wDTO.getAppId());
			jsonObj.put("appName", wDTO.getAppName());
			jsonObj.put("buildType", wDTO.getBuildType());
			/*jsonObj.put("server", wDTO.getServer());
			jsonObj.put("port", wDTO.getPort());
			jsonObj.put("username", wDTO.getUsername());
			jsonObj.put("password", wDTO.getPassword());
			if(wDTO.getAuthToken() == null){
				jsonObj.put("authToken", "");
			}else{
				jsonObj.put("authToken", wDTO.getAuthToken());
			}
			jsonObj.put("jobName", wDTO.getJobName());
			jsonObj.put("buildType", wDTO.getBuildType());
			jsonObj.put("buildParameter", wDTO.getBuildParameter());
			
			//String workflowUrl = generateUrl(wDTO);
			//jsonObj.put("workflowUrl", workflowUrl);
			jsonObj.put("status", "STATUS");
			jsonObj.put("report", "REPORT");*/
			jsonObjList.add(jsonObj);
		}
		logger.info("Exit from DashboardAction.getWorkflowJsonObjectList()...");
	}
	
	private String createApplicationListForGridFilter(){
		String filterAppList = "";
		//CommonDAO cDAO  = new CommonDAOImpl();
		ApplicationDAO aDAO = new ApplicationDAOImpl();
		try {
			List<ApplicationDTO> appList = aDAO.getApplicationList();
			
			Iterator<ApplicationDTO> itr = appList.iterator();
			while(itr.hasNext()){
				ApplicationDTO app = (ApplicationDTO)itr.next();
				filterAppList = filterAppList+";"+app.getAppName()+":"+app.getAppName();
			}
		} catch (PSDException e) {
			logger.error("Error in DashboardAction.createApplicationListForGridFilter()..."+e.getMessage());
			e.printStackTrace();
		}
		
		return filterAppList;
	}
	
	private String createWorkflowTypeListForGridFilter(){
		String workflowJsonStr = "";
		WorkflowTypeDAO wDAO = new WorkflowTypeDAOImpl();
		try {
			List<WorkflowTypeDTO> wtList = wDAO.getWorkflowTypeList();
			
			Iterator<WorkflowTypeDTO> itr = wtList.iterator();
			while(itr.hasNext()){
				WorkflowTypeDTO wt = (WorkflowTypeDTO)itr.next();
				workflowJsonStr = workflowJsonStr+";"+wt.getWorkflowType()+":"+wt.getWorkflowType();
			}
		} catch (PSDException e) {
			logger.error("Error in DashboardAction.createWorkflowTypeListForGridFilter()..."+e.getMessage());
			e.printStackTrace();
		}
		
		//Removing last ';'
		//if (workflowJsonStr != null && workflowJsonStr.length() > 0 && workflowJsonStr.charAt(workflowJsonStr.length()-1)==';') {
		//	workflowJsonStr = workflowJsonStr.substring(0, workflowJsonStr.length()-1);
		//}
		
		return workflowJsonStr;
	}
	
}
