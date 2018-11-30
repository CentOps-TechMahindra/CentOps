package com.techm.psd.jenkins.bo;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.techm.psd.common.dto.NodeDTO;
import com.techm.psd.jenkins.utils.ParseJenkinsConfigJSON;
import com.techm.psd.services.JenkinsRequest;

public class JenkinsConfigurationBO {

	private Logger logger = Logger.getLogger(JenkinsConfigurationBO.class);
	
	private final String nodesDetails	= "computer/api/json";
	
	public List<NodeDTO> 	getNodesDetailsList	(JobData jd)			throws Exception{
		logger.info("Enter into JenkinsConfigurationBO.getNodesDetailsList()!");
		
		List<NodeDTO> nodeDTOList 	= new ArrayList<NodeDTO>();
		JenkinsRequest jr 			= new JenkinsRequest(); 
		ParseJenkinsConfigJSON p 	= new ParseJenkinsConfigJSON();
		String userpassStr 			= "";
		String responseJSON 		= "";
		String url 					= "";
		WorkflowBO wBO				= new WorkflowBO();
		
		//jd 							= wBO.getJobDataByWorkflowId(1);
		url	 						= String.format("%s:%s/jenkins/%s", jd.getServer(), jd.getPort(), nodesDetails);
		userpassStr 				= jd.getUsername() + ":" + jd.getPassword();
		
		responseJSON 				= jr.getResponse(url, userpassStr);
		nodeDTOList 				= p.parseJSONList(responseJSON);
		
		logger.info("Enter into JenkinsConfigurationBO.getNodesDetailsList()! nodeDTOList.size() :-->"+nodeDTOList.size());
		return nodeDTOList;
	}
}
