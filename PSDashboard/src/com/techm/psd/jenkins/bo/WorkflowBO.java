package com.techm.psd.jenkins.bo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.bo.ReportBO;
import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.exception.PSDException;
import com.techm.psd.jenkins.utils.ParseJSON;
import com.techm.psd.jenkins.utils.WorkflowUtils;
import com.techm.psd.services.JenkinsRequest;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class WorkflowBO {
	
	private Logger logger = Logger.getLogger(WorkflowBO.class);
	
	private final String _START_BUILD_NO_PARAM		= "build?delay=0sec&";
	private final String _START_BUILD_WITH_PARAM	= "buildWithParameters?delay=0sec&";
	private final String _TOKEN						= "token=";
	private final String _ALL_BUILD					= "api/json?depth=3";
	private final String _CURRENT_BUILD				= "api/json?pretty=true";
	private final String _LAST_BUILD_NUMBER			= "lastBuild/buildNumber";
	private final String _BUILD_DETAILS_BY_BUILD_ID	= "api/json";
	//private final String _LAST_BUILD 				= "lastBuild/api/xml";
	//private final String _FIRST_BUILD 			= "firstBuild/api/xml";
	
	public void 			startWorkflow			(JobData jd, String node, String paramStr) 			throws Exception{
		logger.info("Enter into WorkflowBO.startWorkflow()!");
		JenkinsRequest jr 		= new JenkinsRequest(); 
		String userpassStr 		= "";
		String url 				= "";
		
		url 					= String.format("%s:%s/jenkins/job/%s/", jd.getServer(), jd.getPort(), jd.getJob());
		String buildType 		= jd.getBuildType();
		
		if(buildType.equalsIgnoreCase("build")){
			url = url+_START_BUILD_NO_PARAM;	
		}else if(buildType.equalsIgnoreCase("buildWithParameters")){
			url = url+_START_BUILD_WITH_PARAM;
			if(paramStr.equalsIgnoreCase("")){}else{
				url = url+paramStr;
			}
			if(node.equalsIgnoreCase("")){}else{
				url = url+jd.getBuildParameter()+"="+node+"&";
			}
		}
		if(CommonUtil.isNotEmpty(jd.getToken())){
			url = url+_TOKEN+jd.getToken();
		}
		userpassStr = jd.getUsername() + ":" + jd.getPassword();
		jr.getResponse(url, userpassStr);
		logger.info("Enter into WorkflowBO.startWorkflow()!");
	}
	
	public List<BuildDTO> 	getBuildsHistoryList	(JobData jd)			throws Exception{
		logger.info("Enter into WorkflowBO.getBuildsHistoryList()!");
		List<BuildDTO> buildDTOList = new ArrayList<BuildDTO>();
		//JobData jd					= new JobData();
		JenkinsRequest jr 			= new JenkinsRequest(); 
		ParseJSON pj 				= new ParseJSON();
		String userpassStr 			= "";
		String responseJSON 		= "";
		String url 					= "";
		
		//jd 							= getJobDataByWorkflowId(workflowId);
		url	 			= String.format("%s:%s/jenkins/job/%s/%s", jd.getServer(), jd.getPort(), jd.getJob(), _ALL_BUILD);
		userpassStr 	= jd.getUsername() + ":" + jd.getPassword();
		responseJSON 	= jr.getResponse(url, userpassStr);
		
		buildDTOList 	= pj.parseJSONList(responseJSON);
	    logger.info("Enter into WorkflowBO.getBuildsHistoryList()!");
		return buildDTOList;
	}
	
	public BuildDTO			getBuildHistoryById		(JobData jd, int jobId)	throws Exception{
		logger.info("Enter into WorkflowBO.getBuildHistoryById()!");
		BuildDTO bDTO 			= new BuildDTO();
		ParseJSON pj 			= new ParseJSON();
		String responseJsonStr	= getBuildDetailsById(jd, jobId);
		JSONObject jsonObj 		= new JSONObject(responseJsonStr);
		bDTO 					= pj.parseJsonObject(jsonObj);
		logger.info("Enter into WorkflowBO.getBuildHistoryById()!");
		return bDTO;
	}
	
	public String 			getBuildDetailsById		(JobData jd, int jobId)	throws Exception{
		logger.info("Enter into WorkflowBO.getBuildDetailsById()!");
		JenkinsRequest jr 		= new JenkinsRequest(); 
		String userpassStr 		= "";
		String responseJsonStr 	= "";
		String url 				= "";
		//JobData jd				= new JobData();
		
		//jd						= getJobDataByWorkflowId(workflowId);;
		url 					= String.format("%s:%s/jenkins/job/%s/%s/%s", jd.getServer(), jd.getPort(), jd.getJob(), jobId, _BUILD_DETAILS_BY_BUILD_ID);
		userpassStr 			= jd.getUsername() + ":" + jd.getPassword();
		responseJsonStr 		= jr.getResponse(url, userpassStr);
		logger.info("Exit from WorkflowBO.getBuildDetailsById()!");
		return responseJsonStr;
		
	}
	
	public String			getBuildStatusById		(JobData jd, int jobId)	throws Exception{
		logger.info("Enter into WorkflowBO.getBuildStatusById()!");
		ParseJSON pj 			= new ParseJSON();
		String responseJsonStr	= getBuildDetailsById(jd, jobId);
		
		String result 			= pj.parseBuildStatus(responseJsonStr);
		logger.info("Enter into WorkflowBO.getBuildStatusById()! result : "+result);
		return result;
	}
	
	public String 			getBuildColorStatus		(JobData jd) 			throws Exception{
		logger.info("Enter into WorkflowBO.getJobColorStatus()!");
		JenkinsRequest jr 	= new JenkinsRequest(); 
		ParseJSON pj 		= new ParseJSON();
		String userpassStr 	= "";
		String responseJson = "";
		String url 			= "";
		//JobData jd			= new JobData();
		
		//jd					= getJobDataByWorkflowId(workflowId);;
		url 				= String.format("%s:%s/jenkins/job/%s/%s/", jd.getServer(), jd.getPort(), jd.getJob(), _CURRENT_BUILD);
		userpassStr 		= jd.getUsername() + ":" + jd.getPassword();
		responseJson 		= jr.getResponse(url, userpassStr);
		String buildColor 	= pj.parseBuildColor(responseJson);
		logger.info("Enter into WorkflowBO.getJobColorStatus()!");
		return buildColor;
		
	}
	
	public boolean 			getBuildingStatus		(JobData jd, int buildNo) 			throws Exception{
		logger.info("Enter into WorkflowBO.getJobColorStatus()!");
		ParseJSON pj 		= new ParseJSON();
		String str = getBuildDetailsById(jd, buildNo);
		
		boolean buildingStatus 	= pj.parseBuildingStatus(str);
		logger.info("Enter into WorkflowBO.buildingStatus()!"+buildingStatus);
		return buildingStatus;
	}
	
	public int 				getLastBuildNo			(JobData jd) 			throws Exception{
		logger.info("Enter into WorkflowBO.getLastBuildNo()!");
		JenkinsRequest jr 	= new JenkinsRequest(); 
		//JobData jd			= new JobData();
		String userpassStr 	= "";
		String response 	= "";
		String url 			= "";
		String lastBuildNo 	= "";
		
		//jd					= getJobDataByWorkflowId(workflowId);;
		url 				= String.format("%s:%s/jenkins/job/%s/%s/", jd.getServer(), jd.getPort(), jd.getJob(), _LAST_BUILD_NUMBER);
		
		userpassStr 		= jd.getUsername() + ":" + jd.getPassword();
		response 			= jr.getResponse(url, userpassStr);
		lastBuildNo 		= response.toString();
		
		logger.info("Exit from WorkflowBO.getLastBuildNo(),   lastBuildNo : "+lastBuildNo);
		return Integer.parseInt(lastBuildNo);
	}
	
	public int 				getNextBuildNo			(JobData jd) 			throws Exception{
		logger.info("Enter into WorkflowBO.getNextBuildNo()!");
		
		JenkinsRequest jr 	= new JenkinsRequest(); 
		ParseJSON pj 		= new ParseJSON();
		//JobData jd			= new JobData();
		String userpassStr 	= "";
		String responseXml 	= "";
		String url 			= "";
		int nextBuildNo 	= 0;
		
		//jd					= getJobDataByWorkflowId(workflowId);
		url 				= String.format("%s:%s/jenkins/job/%s/%s/", jd.getServer(), jd.getPort(), jd.getJob(), _CURRENT_BUILD);
		userpassStr 		= jd.getUsername() + ":" + jd.getPassword();
		responseXml 		= jr.getResponse(url, userpassStr);
		nextBuildNo 		= pj.parseNextBuildNumber(responseXml);
		
		logger.info("Exit from WorkflowBO.getNextBuildNo(), nextBuildNo : "+nextBuildNo);
		return nextBuildNo;
	}
	
	public JobData 			getJobDataByWorkflowId	(int workflowId) 			throws Exception{
		logger.info("Enter into WorkflowBO.getJobDataByWorkflowId()!");
		WorkflowDAO wDAO			= new WorkflowDAOImpl();
		JobData jd					= new JobData();
		WorkflowDTO wDTO 			= new WorkflowDTO(); 
		wDTO 						= wDAO.getWorkflowDetailsByID(workflowId);	//To GET THE JENKINS SERVER DETAILS
		jd 							= WorkflowUtils.createJobData(wDTO);
		logger.info("Exit from WorkflowBO.getJobDataByWorkflowId()!");
		return jd;
	}
	
	@SuppressWarnings("finally")
	public List<Integer>	getBuildHistoryIdList	(JobData jd)			{
		List<Integer> buildIdList 	= new ArrayList<Integer>();
		List<BuildDTO> bDTOList;
		try {
			bDTOList = getBuildsHistoryList(jd);
			Iterator<BuildDTO> itr 		= bDTOList.iterator();
			while(itr.hasNext()){
				BuildDTO b	= itr.next();
				buildIdList.add(b.getBuildNo());
			}
		} catch (Exception e) {
			logger.error("Error in WorkflowBO.getBuildHistoryIdList()!"+e.getMessage());
		}finally{
			return buildIdList;
		}
	}
	
	
	
	public boolean			saveWorkflowHistory		(JobData jd, int workflowId) 			throws Exception{
		logger.info("Enter into WorkflowBO.saveWorkflowHistory()!");
		boolean status				= false;
		List<BuildDTO> buildDTOList = getBuildsHistoryList(jd);
		WorkflowDAO wDAO			= new WorkflowDAOImpl();
		status						= wDAO.saveBuildsDetails(buildDTOList, workflowId);
		logger.info("Enter into WorkflowBO.saveWorkflowHistory()!");
		return status;
	}
	
	public String			saveWorkflowHistoryById	(JobData jd, int workflowId, int jobId, UserDTO user, int startFlowId) throws Exception{
		logger.info("Enter into WorkflowBO.saveWorkflowHistory()!");
		BuildDTO bDTO		= getBuildHistoryById(jd, jobId);
		WorkflowDAO wDAO	= new WorkflowDAOImpl();
		
		if(CommonUtil.isEmpty(bDTO.getUserId()) & user != null)	{
			bDTO.setUserId	(user.getUserId()); 
			bDTO.setUserName(user.getUserName());
		}
		
		wDAO.saveBuildDetailsById(bDTO, workflowId, startFlowId);
		
		logger.info("Enter into WorkflowBO.saveWorkflowHistory()!");
		return bDTO.getResult();
	}
	
	public List<JSONObject> getBuildsHistoryJsonList(int workflowId, int startFlowId)			throws Exception{
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		WorkflowUtils wUtil			= new WorkflowUtils();
		WorkflowDAO wDAO			= new WorkflowDAOImpl();
		List<BuildDTO> buildDTOList = wDAO.getBuildsHistory(workflowId, startFlowId);
		List<JSONObject> jsonList 	= wUtil.convertBuildDtoToJsonList(buildDTOList);
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		return jsonList;
	}
	
	public String 			getBuildsReportHistoryJsonList(int workflowId, int startFlowId){
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		WorkflowDAO wDAO			= new WorkflowDAOImpl();
		List<Integer> buildNos;
		String resultStr = "Report History Data is not available!";
		try {
			buildNos = wDAO.getBuilsNosByStartFlowId(startFlowId);
			logger.info("Get buildNumbers for WorkflowBO.getBuildsHistoryJsonList()! buildNos: "+buildNos);
			if(buildNos.size()>0){
				ReportBO rBO = new ReportBO();
				resultStr = rBO.getReportDataOverlayForBuildNos(workflowId, buildNos, startFlowId);
			}
		} catch (PSDException e) {
			e.printStackTrace();
		} 
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		return resultStr;
	}
	
	public List<JSONObject> getStartFlowHistoryJsonList(int workflowId, String action)			throws Exception{
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		WorkflowUtils wUtil						= new WorkflowUtils();
		WorkflowDAOImpl wDAO					= new WorkflowDAOImpl();
		List<StartFlowHistoryDTO> buildDTOList 	= wDAO.getStartFlowHistory(workflowId, action);
		List<JSONObject> jsonList 				= wUtil.convertStartFlowHistoryDTOToJsonList(buildDTOList, workflowId, action);
		logger.info("Enter into WorkflowBO.getBuildsHistoryJsonList()!");
		return jsonList;
	}
	
}
