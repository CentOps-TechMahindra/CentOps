package com.techm.psd.jenkins.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.common.utils.CommonUtil;

public class ParseJSON {

	private Logger logger = Logger.getLogger(ParseJSON.class);
	
	public List<BuildDTO> parseJSONList(String responseJson){
		List<BuildDTO> bDTOList = new ArrayList<BuildDTO>();
		JSONObject json;
		try {
			json = new JSONObject(responseJson);
			JSONArray jsonArray  = json.getJSONArray("allBuilds");
			
			for(int i = 0; i<jsonArray.length(); i++){
				JSONObject jObj = jsonArray.getJSONObject(i);
				bDTOList.add(parseJsonObject(jObj));
			}	
		}catch (JSONException e) {
			logger.error("Error in PareseJSON.parseJSONList"+e.getMessage());
		}
		return bDTOList;
	}
	
	public BuildDTO parseJsonObject(JSONObject jObj){
		BuildDTO bDTO = new BuildDTO();
		try{
			if(jObj.has("result")) { if(jObj.get("result") != null) bDTO.setResult(jObj.getString("result"));}
			
			//if(jObj.has("building")){if(jObj.get("building") != null)bDTO.setBuilding(jObj.getBoolean("building"));}
			
			if(jObj.has("queueId")){if(jObj.get("queueId")  != null)bDTO.setQueueId(jObj.getInt("queueId"));}
			
			if(jObj.has("executor")){
				//if(jObj.get("executor") != null)
				//bDTO.setExecutor(jObj.getString("executor"));
			}
			
			if(jObj.has("number"))				{if(jObj.get("number")  != null)			bDTO.setBuildNo(jObj.getInt("number"));}
					
			if(jObj.has("builtOn"))				{if(jObj.get("builtOn") != null)			bDTO.setBuiltOn(jObj.getString("builtOn"));}
			
			if(jObj.has("url"))					{if(jObj.get("url") != null)				bDTO.setUrl(jObj.getString("url"));}
			
			//if(jObj.has("timestamp"))			{if(jObj.get("timestamp") != null)			bDTO.setTimestamp(CommonUtil.convertToDate(jObj.getLong("timestamp")));}
			if(jObj.has("timestamp"))			{if(jObj.get("timestamp") != null)			bDTO.setTimestamp(CommonUtil.convertToDate(jObj.getLong("timestamp")));}
			if(jObj.has("estimatedDuration"))	{if(jObj.get("estimatedDuration")  != null)	bDTO.setEstimatedDuration(jObj.getLong("estimatedDuration"));}
			
			if(jObj.has("id"))					{if(jObj.get("id")  != null)				bDTO.setId(jObj.getInt("id"));}
			
			if(jObj.has("duration"))			{if(jObj.get("duration")  != null)			bDTO.setDuration(jObj.getInt("duration"));}
			
			if(jObj.has("fullDisplayName"))		{if(jObj.get("fullDisplayName") != null)	bDTO.setFullDisplayName(jObj.getString("fullDisplayName"));}
			
			//if(jObj.has("keepLog"))				{if(jObj.get("keepLog") != null)			bDTO.setKeepLog(jObj.getBoolean("keepLog"));}
			
			if(jObj.has("displayName"))			{if(jObj.get("displayName") != null)		bDTO.setDisplayName(jObj.getString("displayName"));}
			
			JSONArray jActionArray = jObj.getJSONArray("actions");
			for(int j = 0; j<jActionArray.length(); j++){
				JSONObject jActionObj = jActionArray.getJSONObject(j);
				
				if(jActionObj.has("causes")){
					JSONArray jCauseArray = jActionObj.getJSONArray("causes");
					for(int c = 0; c<jCauseArray.length(); c++){
						JSONObject jCauseObj=jCauseArray.getJSONObject(c); 
						
						if(jCauseObj.has("shortDescription"))	{if(jCauseObj.get("shortDescription") != null)	bDTO.setShortDescription(jCauseObj.getString("shortDescription"));}
						
						if(jCauseObj.has("addr"))				{if(jCauseObj.get("addr") != null)				bDTO.setAddr(jCauseObj.getString("addr"));}
						
						if(jCauseObj.has("userId"))				{if(jCauseObj.get("userId") != null)			bDTO.setUserId(jCauseObj.getString("userId"));}
						
						if(jCauseObj.has("userName"))			{if(jCauseObj.get("userName") != null)			bDTO.setUserName(jCauseObj.getString("userName"));}
						
						if(jCauseObj.has("upstreamBuild"))		{if(jCauseObj.get("upstreamBuild") != null)		bDTO.setUpstreamBuild(jCauseObj.getInt("upstreamBuild"));}
						
						if(jCauseObj.has("upstreamUrl"))		{if(jCauseObj.get("upstreamUrl") != null)		bDTO.setUpstreamUrl(jCauseObj.getString("upstreamUrl"));}
						
						if(jCauseObj.has("upstreamProject"))	{if(jCauseObj.get("upstreamProject") != null)	bDTO.setUpstreamProject(jCauseObj.getString("upstreamProject"));}
					}
				}
			}
		}catch (Exception e){
			logger.error("Error in PareseJSON.parseJsonObject"+e.getMessage());
		}
		return bDTO;
	}
	
	public String parseBuildColor(String responseJson){
		JSONObject json;
		String color = "";
		try {
			json 	= new JSONObject(responseJson);
			color 	= json.getString("color");
		}catch (JSONException e) {
			logger.error("Error in PareseJSON.parseBuildColor"+e.getMessage());
		}
		return color;
	}
	
	public boolean parseBuildingStatus(String responseJson){
		JSONObject json;
		boolean building = false;
		try {
			json 		= new JSONObject(responseJson);
			building 	= json.getBoolean("building");
		}catch (JSONException e) {
			logger.error("Error in PareseJSON.parseBuildingStatus"+e.getMessage());
		}
		return building;
	}
	
	public String parseBuildStatus(String responseJson){
		JSONObject jObj;
		String buildStatus = "INPROGRESS";
		try {
			jObj 		= new JSONObject(responseJson);
			if(jObj.has("result")){
				if(jObj.get("result")  != null)			
					buildStatus = jObj.getString("result");
			}
		}catch (JSONException e) {
			logger.error("Error in PareseJSON.parseBuildStatus"+e.getMessage());
		}
		return buildStatus;
	}
	
	public int parseNextBuildNumber(String responseJson) {
		int nextBuildNumber = 0;
		JSONObject json;
		try {
			json 			= new JSONObject(responseJson);
			nextBuildNumber = json.getInt("nextBuildNumber");
		}catch (JSONException e) {
			logger.error("Error in PareseJSON.parseNextBuildNumber"+e.getMessage());
		}
		return nextBuildNumber;
	}
}
