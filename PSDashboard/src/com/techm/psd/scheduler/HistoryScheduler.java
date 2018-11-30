package com.techm.psd.scheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.exception.PSDException;
import com.techm.psd.jenkins.bo.JobData;
import com.techm.psd.jenkins.bo.WorkflowBO;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class HistoryScheduler extends TimerTask{

	Logger logger = Logger.getLogger(HistoryScheduler.class);
	
	@Override
	public void run() {
		logger.info("Enter into HistoryScheduler.run()!!");
		try {
			updateWorkflowHistory();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exit from HistoryScheduler.run()!!");
	}

	public void updateWorkflowHistory()  throws Exception {

		WorkflowDAOImpl wDAO 			= new WorkflowDAOImpl();
		WorkflowBO wBO					= new WorkflowBO();
		int workflowId					= 0;
		List<Integer> workflowIdList	= null;
		List<Integer> buildIdList		= null;
		List<Integer> buildJenkinsIdList= null;
		JobData jd			= new JobData();
		try {
			jd					= wBO.getJobDataByWorkflowId(workflowId);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		workflowIdList	= wDAO.getWorkflowIDList();
		Iterator<Integer> itrWList	= workflowIdList.iterator();
		while(itrWList.hasNext()){
			workflowId = itrWList.next();
			
			buildIdList		= wDAO.getHistoryBuildIDList(workflowId);
			buildJenkinsIdList= wBO.getBuildHistoryIdList(jd);
			Collection result = CollectionUtils.disjunction(buildIdList, buildJenkinsIdList);
			List<Integer> pendingJobIdList = (List<Integer>) result;
			
			Iterator<Integer> itr = pendingJobIdList.iterator();
			while(itr.hasNext()){
				int jobId = itr.next();
				//wBO.saveWorkflowHistoryById(workflowId, jobId, null);
			}
		}
		
	
	}
	
	/*public static void main(String[] args){
		HistoryScheduler s = new HistoryScheduler();
		s.run();
		
	}*/
}
