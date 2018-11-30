package com.techm.psd.jenkins.utils;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.techm.psd.common.dto.NodeDTO;

public class ParseJenkinsConfigJSON {

	private Logger logger = Logger.getLogger(ParseJenkinsConfigJSON.class);
	
	public List<NodeDTO> parseJSONList(String responseJson){
		List<NodeDTO> nDTOList = new ArrayList<NodeDTO>();
		JSONObject json;
		try {
			json = new JSONObject(responseJson);
			JSONArray jsonArray  = json.getJSONArray("computer");
			for(int i = 0; i<jsonArray.length(); i++){
				JSONObject jObj = jsonArray.getJSONObject(i);
				nDTOList.add(parseJsonObject(jObj));
			}	
		}catch (JSONException e) {
			logger.error("Error in ParseJenkinsConfigJSON.parseJSONList"+e.getMessage());
		}
		return nDTOList;
	}
	
	public NodeDTO parseJsonObject(JSONObject jObj){
		NodeDTO nDTO = new NodeDTO();
		try{
			if(jObj.has("displayName")) 		{ if(jObj.get("displayName") != null) 			nDTO.setDisplayName			(jObj.getString("displayName"));}
			if(jObj.has("idle")) 				{ if(jObj.get("idle") != null) 					nDTO.setIdle				(jObj.getBoolean("idle"));}
			if(jObj.has("jnlpAgent")) 			{ if(jObj.get("jnlpAgent") != null) 			nDTO.setJnlpAgent			(jObj.getBoolean("jnlpAgent"));}
			if(jObj.has("launchSupported")) 	{ if(jObj.get("launchSupported") != null) 		nDTO.setLaunchSupported		(jObj.getBoolean("launchSupported"));}
			if(jObj.has("manualLaunchAllowed")) { if(jObj.get("manualLaunchAllowed") != null) 	nDTO.setManualLaunchAllowed	(jObj.getBoolean("manualLaunchAllowed"));}
			if(jObj.has("numExecutors")) 		{ if(jObj.get("numExecutors") != null) 			nDTO.setNumExecutors		(jObj.getInt("numExecutors"));}
			if(jObj.has("offline")) 			{ if(jObj.get("offline") != null) 				nDTO.setOffline				(jObj.getBoolean("offline"));}
			if(jObj.has("offlineCauseReason")) 	{ if(jObj.get("offlineCauseReason") != null) 	nDTO.setOfflineCauseReason	(jObj.getString("offlineCauseReason"));}
			if(jObj.has("temporarilyOffline")) 	{ if(jObj.get("temporarilyOffline") != null) 	nDTO.setTemporarilyOffline	(jObj.getBoolean("temporarilyOffline"));}
			
		}catch (Exception e){
			logger.error("Error in ParseJenkinsConfigJSON.parseJsonObject"+e.getMessage());
		}
		return nDTO;
	}
	
}
