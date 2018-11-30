package com.techm.psd.workflow.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.exception.PSDException;

public interface WorkflowDAO {

	public StartFlowHistoryDTO				isWorkflowInUse(int workflowId) 													throws PSDException;
	public int 								saveStartFlowEntry(int workflowId, String username) 								throws PSDException;
	public boolean 							saveWorkflow(WorkflowDTO wDTO) 														throws PSDException;
	public boolean 							isValidateWorkflowName(String workflowName, int workflowId) 						throws PSDException;
	//public boolean 							saveWorkflowType(WorkflowTypeDTO wTypeDTO) 											throws PSDException;
	//public boolean 							saveApplication(ApplicationDTO aDTO) 												throws PSDException;
	
	public boolean 							updateWorkflow(WorkflowDTO wDTO) 													throws PSDException;
	public WorkflowDTO 						getWorkflowDetailsByID(int workflowId) 												throws PSDException;
	public WorkflowDTO 						getWorkflowDetailsByName(String workflowName) 										throws PSDException;
	
	public List<ProfileDTO> 				getWorkflowProfileList(int workflowId) 												throws PSDException;
	public String[] 						getWorkflowParamList(int workflowId) 												throws PSDException;
	//public String[] 						getWorkflowNameList() 																throws PSDException;
	public List<FixItDTO> 					getFixItDTOList(int workflowId) 													throws PSDException;
	
	public List<WorkflowDTO>				getWorkflowList(UserDTO user)														throws PSDException;
	public List<WorkflowDTO>				getWorkflowListByApp(int appId)														throws PSDException;
	public List<WorkflowDTO>				getAllWorkflowList(int profileId)													throws PSDException;
	//public Map<String, List<WorkflowDTO>>	getWorkflowMap(UserDTO user)														throws PSDException;
	
	public boolean 							saveWorkflowLog(int workflowId, String startBy, String status) 						throws PSDException;
	public boolean 							saveBuildsDetails(List<BuildDTO> bDTOList, int workflowId) 							throws PSDException;
	public boolean 							saveBuildDetailsById(BuildDTO bDTO, int workflowId, int startFlowId) 				throws PSDException;
	public List<BuildDTO>					getBuildsHistory(int workflowId, int startFlowId) 									throws PSDException;
	public List<Integer>					getBuilsNosByStartFlowId(int startFlowId) 											throws PSDException;
	//public void	 						getReportDataByBuildIds(int workflowId, List<Integer> buildNos) 					throws PSDException;
	public DatabaseBean 					getReportDBDetails(int workflowId) 													throws PSDException;
	public void 							getReportJSON(Connection con, String query, List<String> columns, JSONArray jArray) throws SQLException,JSONException,Exception;
	public List<Integer>					getWorkflowIDList() 																throws PSDException; 
	//public Map<Integer, List<BuildDTO>> 	getWorkflowHistoryMap() 															throws PSDException;
	public List<Integer>					getHistoryBuildIDList(int workflowId) 												throws PSDException;
	public boolean 							updateStartFlowHistory(int startFlowId, String jenkinsStatus, String reportStatus) 	throws PSDException;
	public int 								getMaxStartFlowIdByUser(int workflowId, String username)							throws PSDException;
}
