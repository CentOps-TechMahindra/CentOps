package com.techm.psd.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.exception.PSDException;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class FixitBO {
	private Logger logger = Logger.getLogger(FixitBO.class);

	public List<JSONObject> getFixitWorkflowJSONList(int profileId) throws PSDException, JSONException{
		WorkflowDAO wDAO					= new WorkflowDAOImpl();
		List<WorkflowDTO> fixItWorkflowList = null;
		fixItWorkflowList 					= wDAO.getAllWorkflowList(profileId);
		List<JSONObject> jsonList 			= generateFixitWorkflowList(fixItWorkflowList);
		return jsonList;
	}
	
	public List<JSONObject> generateFixitWorkflowList(List<WorkflowDTO> fixItWorkflowList) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		Iterator<WorkflowDTO> itr = fixItWorkflowList.iterator();
		while(itr.hasNext()){
			WorkflowDTO wDTO 			= (WorkflowDTO)itr.next();
			JSONObject jsonObj 			= new JSONObject();
			int 	appId				= wDTO.getAppId();
			int 	workflowId			= wDTO.getWorkflowId();
			String 	workflowName		= wDTO.getWorkflowName();
			
			jsonObj.put("appId", 		appId);
			jsonObj.put("workflowId", 	workflowId);
			jsonObj.put("workflowName", workflowName);
			jsonList.add(jsonObj);
		}
		return jsonList;
	}
}
