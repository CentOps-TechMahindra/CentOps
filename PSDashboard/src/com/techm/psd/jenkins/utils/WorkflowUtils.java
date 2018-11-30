package com.techm.psd.jenkins.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.jenkins.bo.JobData;

public class WorkflowUtils {
	
	String format 		= "MM/dd/yyyy hh:mm:ss";
	public static JobData createJobData(WorkflowDTO wDTO){
		JobData jd = new JobData();
		
		jd.setUsername		(wDTO.getUsername());
		jd.setPassword		(wDTO.getPassword());
		jd.setServer		(wDTO.getServer());
        jd.setPort			(wDTO.getPort()+"");
        jd.setJob			(wDTO.getJobName());
        jd.setToken			(wDTO.getAuthToken());
        jd.setBuildType		(wDTO.getBuildType());
        jd.setBuildParameter(wDTO.getBuildParameter());
        
		return jd;
	}

	public List<JSONObject> convertBuildDtoToJsonList(List<BuildDTO> bDTOList) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		Iterator<BuildDTO> itr = bDTOList.iterator();
		while(itr.hasNext()){
			BuildDTO bDTO = (BuildDTO)itr.next();
			JSONObject jsonObj = new JSONObject();
			
			String userName		= bDTO.getUserName();
			//String userId		= bDTO.getUserId();
			String addr			= bDTO.getAddr();
			int upStreamBuildNo	= bDTO.getUpstreamBuild();
			
			if(userName != null){
				jsonObj.put("username", 		userName);
			}else if(upStreamBuildNo != 0){
				jsonObj.put("username", 		"Build on Upstream: "+upStreamBuildNo);
			}else if(upStreamBuildNo == 0){
				jsonObj.put("username", 		addr);
			}
			//jsonObj.put("username", 		bDTO.getUserName());
			jsonObj.put("workflowId", 		bDTO.getWorkflowId());
			jsonObj.put("displayName", 		bDTO.getDisplayName());
			jsonObj.put("duration", 		bDTO.getDuration());
			jsonObj.put("estimatedDuration",bDTO.getEstimatedDuration());
			jsonObj.put("fullDisplayName", 	bDTO.getFullDisplayName());
			jsonObj.put("builtOnNode", 		bDTO.getBuiltOn());
			jsonObj.put("id", 				bDTO.getId());
			//jsonObj.put("keepLog", 			bDTO.isKeepLog());
			jsonObj.put("buildNo", 			bDTO.getBuildNo());
			jsonObj.put("queueId", 			bDTO.getQueueId());
			jsonObj.put("result", 			bDTO.getResult());
			jsonObj.put("timestamp", 		CommonUtil.convertDateToString(bDTO.getTimestamp(), format));
			jsonObj.put("startFlowId", 		bDTO.getStartFlowId());
			jsonList.add(jsonObj);
		}
		return jsonList;
	}
	
	
	public List<JSONObject> convertStartFlowHistoryDTOToJsonList(List<StartFlowHistoryDTO> sDTOList, int wId, String action) throws JSONException{
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		
		Iterator<StartFlowHistoryDTO> itr = sDTOList.iterator();
		while(itr.hasNext()){
			StartFlowHistoryDTO sDTO = (StartFlowHistoryDTO)itr.next();
			JSONObject jsonObj = new JSONObject();
			
			int startFlowId		= sDTO.getStartFlowId();
			int workflowId		= sDTO.getWorkflowId();
			String startTime	= CommonUtil.convertDateToString(sDTO.getStartTime(), format);
			String startBy		= sDTO.getStartBy();
			String jenkinsStatus= sDTO.getJenkinsStatus();
			String reportStatus	= sDTO.getReportStatus();
			if(reportStatus == null) reportStatus = "NA";
			
			jsonObj.put("executedFrom", 	"CentOps Console");
			jsonObj.put("startFlowId", 		startFlowId);
			jsonObj.put("workflowId", 		workflowId);
			jsonObj.put("startTime",		startTime);
			jsonObj.put("startBy", 			startBy);
			jsonObj.put("jenkinsStatus", 	jenkinsStatus);
			jsonObj.put("reportStatus", 	reportStatus);
			
			jsonList.add(jsonObj);
		}
		//if(jsonList.size()>1){ //ADDING Below colum if data is available  
		if(!action.equalsIgnoreCase("LAST_START_FLOW_HISTORY")){
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("executedFrom", 	"Jenkins");
			jsonObj.put("startFlowId", 		0);
			jsonObj.put("workflowId", 		wId);
			jsonObj.put("startTime",		"-");
			jsonObj.put("startBy", 			"-");
			jsonObj.put("jenkinsStatus", 	"NA");
			jsonObj.put("reportStatus", 	"NA");
			
			jsonList.add(jsonObj);
		}
		return jsonList;
	}
	
}
