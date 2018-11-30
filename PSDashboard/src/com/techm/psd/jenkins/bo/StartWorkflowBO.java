package com.techm.psd.jenkins.bo;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.bo.ReportBO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class StartWorkflowBO {

	private Logger logger = Logger.getLogger(StartWorkflowBO.class);
	private static final String _SUCCESS	= "SUCCESS";
	private static final String _FAILURE	= "FAILURE";
	private static final String _IN_USE		= "InUse";
	private static final String _DISABLED	= "DISABLED";
	private static final String _INPROGRESS	= "INPROGRESS";
	
	public JSONObject startWorkflow(int workflowId, UserDTO user, List<String> selNodesList, String selExecutionOn, String paramStr, String executedFrom){
		WorkflowDAO wDAO						= new WorkflowDAOImpl();
		JSONObject jsonObj 						= new JSONObject();
		StartFlowHistoryDTO sDTO 				= null; 
		String workflowUsedBy; 
		String combinedStatus					= _FAILURE;
		try{
			sDTO 		= wDAO.isWorkflowInUse(workflowId);
			if(sDTO.getJenkinsStatus() != null && sDTO.getJenkinsStatus().equalsIgnoreCase(_INPROGRESS)){
				workflowUsedBy	= sDTO.getStartBy();
				jsonObj.put("status", _IN_USE);
				jsonObj.put("username", workflowUsedBy);
			}else{
				combinedStatus = buildWorkflow(workflowId, user, selNodesList, selExecutionOn, paramStr, executedFrom);
				jsonObj.put("status", combinedStatus);
			}
			
			logger.info("Exit from WorkflowAJAX()...  : jsonObj : "+jsonObj);
		}catch(Exception e){
			logger.error("Error in StartWorkflowBO.startWorkflow... "+e.getMessage());
			try {
    			jsonObj.put("status", _FAILURE);
			} catch (JSONException je) {
				logger.error("Error starting build ... "+je.getMessage());
				je.printStackTrace();
			}
			e.printStackTrace();
			logger.info("Exit from WorkflowAJAX()...  : jsonObj : ExceptionExceptionExceptionException "+jsonObj);
		}finally{
			//SAVE LOG:
			try {
				wDAO.saveWorkflowLog(workflowId, user.getUserName(), jsonObj.getString("status"));
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return jsonObj;
	}
	
	private String buildWorkflow(int workflowId, UserDTO user, List<String> selNodesList, String selExecutionOn, String paramStr, String executedFrom){
		WorkflowDAO 	wDAO				= new WorkflowDAOImpl();
		JSONObject 		jsonObj 			= new JSONObject();
		WorkflowBO 		wBO 				= new WorkflowBO();
		ReportBO 		rBO 				= new ReportBO();
		DatabaseBean 	dbBean 				= null;
		JobData 		jd					= new JobData();
		int 			buildNo, 
						lastBuildNo, 
						startFlowId;	
		boolean 		buildResult			= false; 
		String 			jenkinsStatus, 
						combinedStatus		= _FAILURE;
		String 			reportStatus 		= "NA";
		List <String> 	jenkinsResultList 	= null;
		List <String> 	reportResultList	= null;
		List<Integer> 	buildNoList 		= new ArrayList<Integer>();
		String 			node 				= "";
		
		try{
			jd					= wBO.getJobDataByWorkflowId(workflowId);
			
			if(isDisabled(jd)){
				combinedStatus = _DISABLED;
			}else {
				if(selExecutionOn.equalsIgnoreCase("defaultNodes") || selExecutionOn.equalsIgnoreCase("") || selExecutionOn == null){
					startFlowId				= wDAO.saveStartFlowEntry(workflowId, user.getUserName());						// Insert entry into startFlowHistory table
					buildNo 				= wBO.getNextBuildNo(jd);														// Get next buildNo
			    	wBO.startWorkflow(jd, node, paramStr);																	// Start workflow on default nodes
			    	buildResult				= isBuilding(jd);																// Get build status
					
					//Update console DB, After Build is completed in jenkins
					lastBuildNo 			= wBO.getLastBuildNo(jd);
					
					// Get Jenkins Result List & Jenkins Result Status Average.
					for(int i = buildNo; i<=lastBuildNo; i++){	//i=buildNo;
						buildNoList.add(i);
					}
				}else{
					startFlowId				= wDAO.saveStartFlowEntry(workflowId, user.getUserName());						// Insert entry into startFlowHistory table
					Iterator<String> itr 	= selNodesList.iterator();
					while(itr.hasNext()){
						node = itr.next();
						buildNoList.add(wBO.getNextBuildNo(jd));															// Get next buildNo
			        	wBO.startWorkflow(jd, node, paramStr);
			        	buildResult				= isBuilding(jd);															// Get build status
					}	
			    }
				
				// Update workflow history
				jenkinsResultList 		= updateWorkflowHistory(jd, workflowId, buildNoList, startFlowId, user);
				// Get jenkins result average and save in StartFlow table
				jenkinsStatus 			= getAverageResultFromStringList(jenkinsResultList);				
				
				// Check If REPORT DB Details are available then fetch the ReportDB result and update execution status.: START
				// Get Jenkins Result List & Jenkins Result Status Average.
				if(executedFrom.equalsIgnoreCase("fixitWorkflow")){															//FOR FIXIT WORKFLOW JUST CHECKING JENKINS STATUS for RESULT (SUCCESS/FAILURE).
					combinedStatus 		= jenkinsStatus;
				}else{
					dbBean	= wDAO.getReportDBDetails(workflowId);
					if(rBO.isValidConnection(dbBean)){
						reportResultList 	= rBO.getReportBuildsResult(workflowId, buildNoList, dbBean);
						if(reportResultList.size()>0){
							reportStatus 	= getAverageResultFromStringList(reportResultList);								// Get jenkins result average and save in StartFlow table
							combinedStatus 	= getCombinedStatus(jenkinsStatus, reportStatus); 
						}else {
							reportStatus 	= _FAILURE;
							combinedStatus 	= _FAILURE;
						}
					}else{
						combinedStatus 		= jenkinsStatus;
					}
				}
				wDAO.updateStartFlowHistory(startFlowId, jenkinsStatus, reportStatus);
			}
		}catch(MalformedURLException me){
			logger.error("Error in StartWorkflowBO.buildWorkflow... "+me.getMessage());
			try {
				jsonObj.put("status", _FAILURE);
			} catch (JSONException je) {
				logger.error("Exception in WorkflowHistoryAJAX... "+je.getMessage());
			}
			logger.error("Error starting build ! MalformedURLExceptionMalformedURLException"+me.getMessage());
    	}catch(Exception e){
    		logger.error("Error in StartWorkflowBO.buildWorkflow... "+e.getMessage());
			try {
    			jsonObj.put("status", _FAILURE);
			} catch (JSONException je) {
				logger.error("Error starting build ... "+je.getMessage());
				je.printStackTrace();
			}
			e.printStackTrace();
			logger.error("Exit from WorkflowAJAX()...  : jsonObj : ExceptionExceptionExceptionException "+jsonObj);
		}
    	return combinedStatus;
	}
	
	
	private boolean isBuilding(JobData jd) throws Exception{
		WorkflowBO wBO 		= new WorkflowBO();
		String status 		= "";
		boolean building 	= true; 
		
		do{
			try {
			    TimeUnit.SECONDS.sleep(10);										// Passuing Code for 10 sec to check build status.
			} catch (InterruptedException e) {
			    logger.error("Exception in WorkflowStartAJAX @line # 148: TimeUnit pause 10 sec: "+e.getMessage());
			}

			status = wBO.getBuildColorStatus(jd);
			if(status.contains("_anime")){
    			status 		= _INPROGRESS;
    			building	= true;		
    		}else if(status.contains("blue")){
    			status 		= _SUCCESS;
    			building	= false;
    		}else if(status.contains("red")){
    			status 		= _FAILURE;
    			building	= false;
    		}
		}while(building);
		
		return building;
	}
	
	private boolean isDisabled(JobData jd) throws Exception{
		WorkflowBO 	wBO 	= new WorkflowBO();
		boolean 	result	= false;
		if(wBO.getBuildColorStatus(jd).contains("disabled"))
			result 	= true;
		return result;
	}
	private List<String> updateWorkflowHistory(JobData jd, int workflowId, List<Integer> buildNoList, int startFlowId, UserDTO user) throws Exception{
		WorkflowBO wBO 		= new WorkflowBO();
		List <String> resultList = new ArrayList<String>();
		Iterator<Integer> itr = buildNoList.iterator();
		while(itr.hasNext()){
			int buildNo = itr.next();
			String result	= wBO.saveWorkflowHistoryById(jd, workflowId, buildNo, user, startFlowId);
			resultList.add(result);
		}
		return resultList;
	}
	
	private String getAverageResultFromStringList(List<String> resultList){
		String statusAverage 	= _FAILURE;
    	int jenkinsSuccessPercentage 	= getPercentageFromStringList(resultList);
    	if(jenkinsSuccessPercentage == 100){
    		statusAverage = _SUCCESS;	
    	}else{
    		statusAverage = _FAILURE;
    	}
    	return statusAverage;
    }
	
	/*private String getReportAverageResult(List<String> reportResultList){
		String statusAverage 	= _FAILURE;
    	int reportSuccessPercentage 	= getPercentageFromStringList(reportResultList);
    	if(reportSuccessPercentage == 100){
    			statusAverage = _SUCCESS;	
		}else{
			statusAverage = _FAILURE;
		}
    	return statusAverage;
    }*/
	
	private String getCombinedStatus(String jenkinsStatus, String reportStatus){
		String status = "";
		
		if(jenkinsStatus.equalsIgnoreCase(_SUCCESS) & reportStatus.equalsIgnoreCase(_SUCCESS)){
			status = _SUCCESS;
		}else{
			status = _FAILURE;
		}
		return status;
	}
	
	private int getPercentageFromStringList(List<String> stringList){
		int successCount= Collections.frequency(stringList, _SUCCESS);
		int average 	= (int) CommonUtil.getPercentage(successCount, stringList.size());
		return average;
	}
	
}
