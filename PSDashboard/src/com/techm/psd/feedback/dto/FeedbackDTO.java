package com.techm.psd.feedback.dto;

import java.sql.Timestamp;

public class FeedbackDTO {
	
	private int 		feedbackId;
	private String		feedback;
	private int 		workflowId;
	private String		workflowName;
	private int			startFlowId;
	private String		addedBy;
	private String		addedDate;
	private String 		updatedBy;
	private String		updatedDate;
	private String 		status;
	private int[]		selFeedbacks;
	private String		comment	= "";
	
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
	public String getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(String addedDate) {
		this.addedDate = addedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int[] getSelFeedbacks() {
		return selFeedbacks;
	}
	public void setSelFeedbacks(int[] selFeedbacks) {
		this.selFeedbacks = selFeedbacks;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
