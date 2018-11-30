package com.techm.psd.workflow.action;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.bo.FixitBO;
import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.common.utils.SidebarUtils;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.exception.PSDException;
import com.techm.psd.jenkins.bo.JobData;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.user.dao.UserDAO;
import com.techm.psd.user.dao.UserDAOImpl;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;
import com.techm.psd.workflow.form.WorkflowForm;
import com.techm.psd.workflowType.dao.WorkflowTypeDAO;
import com.techm.psd.workflowType.dao.WorkflowTypeDAOImpl;

public class WorkflowAction extends BaseAction{

	private Logger logger = Logger.getLogger(WorkflowAction.class);
	//private static String _SAVE		= "SAVE";
	//private static String _UPDATE	= "UPDATE";
	
	public ActionForward addWorkflow(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowAction.addWorkflow()...");
		WorkflowForm wForm 	= (WorkflowForm)form;
		String forward 		= "addWorkflow";
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		setInitialValues(wForm, user, request);
		
		if(wForm.getCopyWorkflow() != null ){
			if(wForm.getCopyWorkflow().equals("")){
			}else{
				copyWorkflow(wForm);
			}
		}
		logger.info("Exit from WorkflowAction.addWorkflow()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward saveWorkflow(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowAction.saveWorkflow()...");
		WorkflowForm wForm 	= (WorkflowForm)form;
		String forward 		= "viewWorkflow";
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		String userId		= user.getUserId();
		
		WorkflowDTO 	wDTO 		= new WorkflowDTO();
		WorkflowDAO 	wDAO 		= new WorkflowDAOImpl();
		WorkflowTypeDTO wTypeDTO 	= new WorkflowTypeDTO();
		ApplicationDTO  appDTO		= new ApplicationDTO();
		
		wForm.setCreatedBy(userId);		//SETTING CREATEBY
		
		//ADD NEW WORKFLOW TYPE and APPLICATION : START
		/*if(wForm.getSelWTypeId() == -1){
			wTypeDTO.setWorkflowType(wForm.getNewWorkflowTypeName());
			wTypeDTO.setAddedBy(userId);
			
			wDAO.saveWorkflowType(wTypeDTO);
			logger.info("Newly Added workflow type id :"+wTypeDTO.getWorkflowTypeId());
			
			wForm.setSelWTypeId(wTypeDTO.getWorkflowTypeId());
		}
		if(wForm.getSelAppId() == -1){
			appDTO.setAppName(wForm.getNewApplicationName());
			appDTO.setCreateBy(userId);
			
			wDAO.saveApplication(appDTO);
			logger.info("Newly Added Application id :"+appDTO.getAppId());
			
			wForm.setSelAppId(appDTO.getAppId());
		}*/
		//ADD NEW WORKFLOW TYPE and APPLICATION : END
		
		//Build workflowDTO to pass the data to save workflowDAO.
		buildWorkflowDTO(wForm, wDTO);
		
		//If Workflow added successfully then forward to viewWorkflow.jsp with success messgage else forward to addWorkflow.jsp with error message.
		if (wDAO.saveWorkflow(wDTO)){
			//Get WorkflowID after Saving the flow to show on view page(viewWorkflow.jsp).
			boolean status	= false;
			int workflowId	= wDTO.getWorkflowId();
			//FETCH and SAVE WORKFLOW HISTORY: START
			WorkflowBO wBO 	= new WorkflowBO();
			JobData jd			= new JobData();
			try {
				jd			= wBO.getJobDataByWorkflowId(workflowId);
				status		= wBO.saveWorkflowHistory(jd, workflowId);
			} catch (Exception e) {
				logger.info("Error fetching workflow History!!!"+e.getMessage());
			}
			logger.info("WorkflowAction.saveWorkflow()!!! workflow history saved status: "+status+" for workflowId: "+workflowId);
			//FETCH and SAVE WORKFLOW HISTORY: END
			wForm.setWorkflowId(workflowId);
			return viewWorkflow (mapping, form, request, response);
		}else{
			//TODO: ADD ERROR MESSAGE
			forward		= "addWorkflow";
			setInitialValues(wForm, user, request);
		}
		
		//JSONArray arr = new JSONArray(wForm.getJsonArr());
		//ArrayList<FixItDTO> fArrlist = generateFixItDTO(arr);
		
		logger.info("Exit from WorkflowAction.saveWorkflow()...");
		return mapping.findForward(forward);
	}
	
	//TODO: Call the viewWorkflow from tracker and dashboard pages.
	public ActionForward viewWorkflow(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowAction.viewWorkflow()...");
		WorkflowForm wForm 	= (WorkflowForm)form;
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		String forward		= "viewWorkflow";
		WorkflowDAO wDAO 	= new WorkflowDAOImpl();
		WorkflowDTO wDTO 	= new WorkflowDTO();
		
		setInitialValues(wForm, user, request);
		
		int workflowId = 0;
		
		//GETTING WORKFLOW_ID TO FETCH THE DATA: START
		if(request.getParameter("workflowId") != null){
			workflowId = Integer.parseInt(request.getParameter("workflowId"));
		}else if(wForm.getWorkflowId() != 0){
			workflowId = wForm.getWorkflowId();
		}else{
			logger.info("In WorkflowAction.viewWorkflow()... workflowId is not available!!!");
		}
		//GETTING WORKFLOW_ID TO FETCH THE DATA: END
		
		logger.info("In WorkflowAction.viewWorkflow()... workflowId !!! "+workflowId);
		
		wDTO = wDAO.getWorkflowDetailsByID(workflowId);
		buildWorkflowFORM(wForm, wDTO);
		
		logger.info("Exit from WorkflowAction.viewWorkflow()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward editWorkflow(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowAction.editWorkflow()...");
		WorkflowForm wForm 	= (WorkflowForm)form;
		String forward 		= "editWorkflow";
		HttpSession session = request.getSession(false);
		UserDTO user	 	= UserSessionUtil.getUserSession(session);
		
		WorkflowDAO wDAO 	= new WorkflowDAOImpl();
		WorkflowDTO wDTO 	= new WorkflowDTO();
		
		wDTO = wDAO.getWorkflowDetailsByID(wForm.getWorkflowId());
		buildWorkflowFORM(wForm, wDTO);
		
		setInitialValues(wForm, user, request);
		logger.info("Exit from WorkflowAction.editWorkflow()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward updateWorkflow(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowAction.updateWorkflow()...");
		WorkflowForm wForm 	= (WorkflowForm)form;
		String forward 		= "viewWorkflow";
		HttpSession session = request.getSession(false);
		UserDTO user 		= UserSessionUtil.getUserSession(session);
		
		WorkflowDTO wDTO 	= new WorkflowDTO();
		WorkflowDAO wDAO 	= new WorkflowDAOImpl();
		wForm.setLastUpdatedBy(user.getUserId());		//SETTING UPDATEBY
		
		//Build workflowDTO to pass the data to save workflowDAO.
		buildWorkflowDTO(wForm, wDTO);
		
		if (wDAO.updateWorkflow(wDTO)){
			return viewWorkflow (mapping, form, request, response);
		}else{
			forward		= "editWorkflow";
		}
		logger.info("Exit from WorkflowAction.updateWorkflow()...");
		return mapping.findForward(forward);
	}
	
	public void buildWorkflowDTO(WorkflowForm wForm, WorkflowDTO wDTO){
		wDTO.setWorkflowId(wForm.getWorkflowId());
		wDTO.setWorkflowName(wForm.getWorkflowName());
		wDTO.setWorkflowTypeId(wForm.getSelWTypeId());
		wDTO.setWorkflowType(wForm.getWorkflowType());
		wDTO.setAppId(wForm.getSelAppId());
		wDTO.setServer(wForm.getServer());
		wDTO.setPort(wForm.getPort());
		wDTO.setUsername(wForm.getUsername());
		wDTO.setPassword(wForm.getPassword());
		wDTO.setAuthToken(wForm.getAuthToken());
		wDTO.setJobName(wForm.getJobName());
		wDTO.setBuildType(wForm.getBuildType());
		wDTO.setBuildParameter(wForm.getBuildParameter());
		wDTO.setDesc(wForm.getDesc());
		wDTO.setCreatedBy(wForm.getCreatedBy());
		wDTO.setLastUpdatedBy(wForm.getLastUpdatedBy());
		wDTO.setSelProfileIds(wForm.getSelProfileIds());
		wDTO.setParametersName(wForm.getParametersName());
		//REPORT DB DETAILS
		wDTO.setDb_type(wForm.getDb_type());
		wDTO.setDb_username(wForm.getDb_username());
		wDTO.setDb_password(wForm.getDb_password());
		wDTO.setDb_hostname(wForm.getDb_hostname());
		wDTO.setDb_port(wForm.getDb_port());
		wDTO.setDb_sid(wForm.getDb_sid());
		wDTO.setDb_table_name(wForm.getDb_table_name());
		wDTO.setDb_type(wForm.getDb_type());
		wDTO.setDb_query(wForm.getDb_query());
		
		//FIXIT JSON LIST
		JSONArray arr = null;
		List<FixItDTO> fixItList = null;
		if(wForm.getJsonArr() != null){
			try {
				arr 		= new JSONArray(wForm.getJsonArr());
				fixItList 	= generateFixItDTO(arr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		//wDTO.setJsonArr(arr.toString());
		wDTO.setFixItDTOList(fixItList);
	}
	
	public void buildWorkflowFORM(WorkflowForm wForm, WorkflowDTO wDTO) throws JSONException{
		wForm.setWorkflowId		(wDTO.getWorkflowId());
		wForm.setWorkflowName	(wDTO.getWorkflowName());
		wForm.setSelWTypeId		(wDTO.getWorkflowTypeId());
		wForm.setWorkflowType	(wDTO.getWorkflowType());
		wForm.setSelAppId		(wDTO.getAppId());
		wForm.setAppName		(wDTO.getAppName());
		wForm.setServer			(wDTO.getServer());
		wForm.setPort			(wDTO.getPort());
		wForm.setUsername		(wDTO.getUsername());
		wForm.setPassword		(wDTO.getPassword());
		wForm.setAuthToken		(wDTO.getAuthToken());
		wForm.setJobName		(wDTO.getJobName());
		wForm.setBuildType		(wDTO.getBuildType());
		wForm.setBuildParameter	(wDTO.getBuildParameter());
		wForm.setParametersName (wDTO.getParametersName());
		wForm.setDesc			(wDTO.getDesc());
		wForm.setCreatedBy		(wDTO.getCreatedBy());
		wForm.setCreateDate		(wDTO.getCreateDate());
		wForm.setLastUpdatedBy	(wDTO.getLastUpdatedBy());
		wForm.setLastUpdatedDate(wDTO.getLastUpdatedDate());
		wForm.setProfileList	(wDTO.getProfileList());
		wForm.setSelProfileIds	(wDTO.getSelProfileIds());
		wForm.setFixItDTOList	(wDTO.getFixItDTOList());
		//JSON Array
		wForm.setJsonArr		(convertFixitListToFixItString(wDTO.getFixItDTOList()));
	
		//REPORT DB DETAILS
		wForm.setDb_type		(wDTO.getDb_type());
		wForm.setDb_username	(wDTO.getDb_username());
		wForm.setDb_password	(wDTO.getDb_password());
		wForm.setDb_hostname	(wDTO.getDb_hostname());
		wForm.setDb_port		(wDTO.getDb_port());
		wForm.setDb_sid			(wDTO.getDb_sid());
		wForm.setDb_table_name	(wDTO.getDb_table_name());
		wForm.setDb_type		(wDTO.getDb_type());
		wForm.setDb_query		(wDTO.getDb_query());
		
		//
		/*FixitBO		fBO				= new FixitBO();
		List<JSONObject> jsonList 	= fBO.getFixitWorkflowJSONList(wForm.getSelAppId());
		wForm.setJsonList(jsonList);*/
	}
	
	
	void setInitialValues(WorkflowForm wForm, UserDTO user, HttpServletRequest request) throws JSONException, PSDException{
		LOGGER.info("Enter into WorkflowAction.setInitialValues()...");
		//CommonDAO cDAO  = new CommonDAOImpl();
		ApplicationDAO 	aDAO = new ApplicationDAOImpl();
		WorkflowTypeDAO wDAO = new WorkflowTypeDAOImpl();
		UserDAO			uDAO = new UserDAOImpl();
		FixitBO			fBO	 = new FixitBO();
		try {
			wForm.setAppList			(aDAO.getApplicationList()	);
			wForm.setProfileList		(uDAO.getProfileList()		);
			wForm.setWorkflowTypeList	(wDAO.getWorkflowTypeList()	);
			//
			List<JSONObject> jsonList 	= fBO.getFixitWorkflowJSONList(user.getProfileId());
			wForm.setJsonWorkflowList(jsonList.toString());
		} catch (PSDException e) {
			LOGGER.error("Exception in WorkflowAction.setInitialValues()..."+e.getMessage());
			e.printStackTrace();
		}
		
		//Setting below values for sidebar.jsp
		SidebarUtils sUtil = new SidebarUtils();
		sUtil.setSidebarData(user, request);
		LOGGER.info("Exit from WorkflowAction.setInitialValeues()...");
	}
	
	public void copyWorkflow(WorkflowForm wForm){
		String workflowName = wForm.getCopyWorkflow();
		WorkflowDAO wDAO 	= new WorkflowDAOImpl();
		WorkflowDTO	wDTO	= new WorkflowDTO();
		
		try {
			wDTO 			= wDAO.getWorkflowDetailsByName(workflowName);

			wForm.setSelWTypeId(wDTO.getWorkflowTypeId());
			wForm.setSelAppId(wDTO.getAppId());
			wForm.setUsername(wDTO.getUsername());
			wForm.setPassword(wDTO.getPassword());
			wForm.setAuthToken(wDTO.getAuthToken());
			wForm.setServer(wDTO.getServer());
			wForm.setPort(wDTO.getPort());
			wForm.setJobName(wDTO.getJobName());
			wForm.setBuildType(wDTO.getBuildType());
			wForm.setBuildParameter(wDTO.getBuildParameter());
			wForm.setDb_type(wDTO.getDb_type());
			wForm.setDb_username(wDTO.getDb_username());
			wForm.setDb_password(wDTO.getDb_password());
			wForm.setDb_hostname(wDTO.getDb_hostname());
			wForm.setDb_port(wDTO.getDb_port());
			wForm.setDb_sid(wDTO.getDb_sid());
			wForm.setDb_table_name(wDTO.getDb_table_name());
			wForm.setDb_query(wDTO.getDb_query());
		} catch (PSDException e) {
			LOGGER.error("Exception in WorkflowAction.copyWorkflow()..."+e.getMessage());
			e.printStackTrace();
		}
		
	}
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return addWorkflow(mapping, form, request, response);
	}

	private static ArrayList<FixItDTO> generateFixItDTO(JSONArray arr) throws JSONException{
		ArrayList<FixItDTO> fArrlist = new ArrayList<FixItDTO>();
		for(int i = 0; i<arr.length(); i++){
			JSONObject o = (JSONObject) arr.get(i);
			if(o.length() >0 ){
				FixItDTO fDto	= new FixItDTO();
				if(o.has("errorName")){
					fDto.setErrorName(o.get("errorName").toString());
					fDto.setErrorCode(Integer.parseInt(o.get("errorCode").toString()));
					fDto.setFixitWorkflowId(Integer.parseInt(o.get("fixitWorkflowId").toString()));
					fDto.setFixitWorkflowName(o.get("fixitWorkflowName").toString());
					fArrlist.add(fDto);
				}
			}
		}
		return fArrlist;
	}
	
	private static String convertFixitListToFixItString(List<FixItDTO> fixItList) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		Iterator<FixItDTO> itr   = fixItList.iterator();
		while(itr.hasNext()){
			FixItDTO fDTO = (FixItDTO)itr.next();
			JSONObject jsonObj = new JSONObject();
			
			String 	errorName			= fDTO.getErrorName();
			int 	errorCode			= fDTO.getErrorCode();
			String 	fixitWorkflowName	= fDTO.getFixitWorkflowName();
			int 	fixitWorkflowId		= fDTO.getFixitWorkflowId();
			
			jsonObj.put("errorName", 		errorName);
			jsonObj.put("errorCode", 		errorCode);
			jsonObj.put("fixitWorkflowName",fixitWorkflowName);
			jsonObj.put("fixitWorkflowId",	fixitWorkflowId);
			jsonList.add(jsonObj);
		}
		return jsonList.toString();
	}
}
