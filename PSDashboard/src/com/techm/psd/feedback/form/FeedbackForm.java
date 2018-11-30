package com.techm.psd.feedback.form;

import java.sql.Timestamp;
import java.util.List;
import com.techm.psd.common.form.BaseForm;
import com.techm.psd.feedback.dto.FeedbackDTO;

public class FeedbackForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int 		feedbackId;
	private String		feedback;
	private int 		workflowId;
	private String		workflowName;
	private int			startFlowId;
	private String		addedBy;
	private Timestamp	addedDate;
	private String 		updatedBy;
	private Timestamp	updatedDate;
	private String 		status;
	private List<FeedbackDTO> feedbackDTOList;
	private int[]		selFeedbacks;
	
	public int getFeedbackId() {
		return feedbackId;
	}
	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public int getStartFlowId() {
		return startFlowId;
	}
	public void setStartFlowId(int startFlowId) {
		this.startFlowId = startFlowId;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Timestamp getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<FeedbackDTO> getFeedbackDTOList() {
		return feedbackDTOList;
	}
	public void setFeedbackDTOList(List<FeedbackDTO> feedbackDTOList) {
		this.feedbackDTOList = feedbackDTOList;
	}
	public int[] getSelFeedbacks() {
		return selFeedbacks;
	}
	public void setSelFeedbacks(int[] selFeedbacks) {
		this.selFeedbacks = selFeedbacks;
	}

}