package com.techm.psd.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.techm.psd.common.dto.NodeDTO;
import com.techm.psd.common.dto.WorkflowConfigDTO;
import com.techm.psd.jenkins.bo.JenkinsConfigurationBO;
import com.techm.psd.jenkins.bo.JobData;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.jenkins.bo.WorkflowConfig;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class NodesDetailsAJAX extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(NodesDetailsAJAX.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.info("Enters into NodesDetailsAJAX()!");
		JSONObject 				jsonObj 	= new JSONObject();
		List<JSONObject> 		jList		= new ArrayList<JSONObject>();
		JenkinsConfigurationBO 	jBO 		= new JenkinsConfigurationBO();
		WorkflowBO 				wBO 		= new WorkflowBO();
		JobData 				jd			= new JobData();
		WorkflowConfig 			wConf		= new WorkflowConfig();
		WorkflowConfigDTO 		wConfDTO	= new WorkflowConfigDTO();
		WorkflowDAO 			wDAO 		= new WorkflowDAOImpl();
		List<NodeDTO> 			nList 		= null; 
		int workflowId 						= 0;
		boolean 				offline		= false;
		List<String> 			offlineNodeList = null;
		if(req.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(req.getParameter("workflowId").toString());}
		
		try {
			jd				= wBO.getJobDataByWorkflowId(workflowId);
			wConfDTO		= wConf.getJobConfig(jd);
			nList 			= jBO.getNodesDetailsList(jd);
			offlineNodeList = getAllOfflineNodes(jd); 
			//TO GET THE ALLOWED NODES LIST
			if(wConfDTO != null){
				Iterator<String> itr1 = wConfDTO.getAllowedSlaves().iterator();
				while(itr1.hasNext()){
					String allowedNodeName	= (String)itr1.next();
					JSONObject obj 			= new JSONObject();
					if(allowedNodeName.contains("ALL")){
						Iterator<NodeDTO> 	itr 	= nList.iterator();
						while(itr.hasNext()){
							NodeDTO 	nDTO 	= (NodeDTO)itr.next();
							JSONObject 	obj1	= new JSONObject();
							if(nDTO.isOffline()){
								obj1.put("displayName", 	nDTO.getDisplayName());
								obj1.put("offline", 		true);
							}else{
								obj1.put("displayName", 	nDTO.getDisplayName());
								obj1.put("offline", 		false);
							}
							jList.add(obj1);
						}
					}else{
						if(offlineNodeList.contains(allowedNodeName)){
							obj.put("displayName", 	allowedNodeName);
							obj.put("offline", 		true);
						}else{
							obj.put("displayName", 	allowedNodeName);
							obj.put("offline", 		false);	
						}
						jList.add(obj);
					}
				}
			}else{
				logger.info("In NodesDetailsAJAX()! wConfDTO is null!");
			}
			//
			jsonObj.put("jList", jList);
			//SETTING STRING PARAMETER LIST
			String[] paramList = wDAO.getWorkflowParamList(workflowId);
			jsonObj.put("paramList", paramList);
		} catch (Exception e) {
			logger.error("Error in NodesDetailsAJAX()!", e);
			e.printStackTrace();
		}
		logger.info("Exit from NodesDetailsAJAX()!");
		PrintWriter pw 		= resp.getWriter();
		pw.println(jsonObj);
	}
	
	private List<String> getAllOfflineNodes(JobData jd) throws Exception{
		List<NodeDTO> 			nList 		= null; 
		JenkinsConfigurationBO 	jBO 		= new JenkinsConfigurationBO();
		boolean 				offline		= false;
		
		nList 		= jBO.getNodesDetailsList(jd);
		
		List<String> onlineNodeList 		= new ArrayList<String>();
		List<String> offlineNodeList 	= new ArrayList<String>();
		Iterator<NodeDTO> itr = nList.iterator();
		while(itr.hasNext()){
			NodeDTO 	nDTO 	= (NodeDTO)itr.next();
			if(nDTO.isOffline()){
				offlineNodeList.add(nDTO.getDisplayName());
			}else{
				onlineNodeList.add(nDTO.getDisplayName());
			}
		}
		return offlineNodeList;
	}
}
