package com.techm.psd.feedback.dao;

import java.util.List;

import com.techm.psd.feedback.dto.FeedbackDTO;
import com.techm.psd.exception.PSDException;

public interface FeedbackDAO {

	public int 					saveFeedback				(String feedback, int workflowId, int startFlowId, String userId)	throws PSDException;
	public int 					updateFeedback				(int feedbackId, String status, String comment, String userId)		throws PSDException;
	public FeedbackDTO 			getFeedbackDetailsById		(int feedbackId)													throws PSDException;
	//public List<FeedbackDTO> 	getAllNewFeedback			(int upperLimit, int offset)										throws PSDException;
	public int 					getAllNewInprogressFeedbackCount()																throws PSDException;
	public List<FeedbackDTO> 	getAllNewInprogressFeedback	(int upperLimit, int offset, String sidx, String sord)				throws PSDException;
	public int 					getAllClosedFeedbackCount()																		throws PSDException;
	public List<FeedbackDTO> 	getAllClosedFeedback		(int upperLimit, int offset, String sidx, String sord)				throws PSDException;
	//public List<FeedbackDTO> 	getAllRejectedFeedback		()																	throws PSDException;
	//public List<FeedbackDTO> 	getAllFeedback				()																	throws PSDException;
	
	//public boolean 				approveRejectFeedbackList	(int[] selFeedbacks, String approvedBy, String status) 			throws PSDException;
	
	public List<String>		getAdminsEmailIdsByApplication	(int appId)															throws PSDException;
}
