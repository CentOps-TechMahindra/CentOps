package com.techm.psd.common.dto;

import java.sql.Timestamp;

public class StartFlowHistoryDTO {

	private int 		startFlowId;
	private int 		workflowId;
	private Timestamp 	startTime;
	private String		startBy;
	private String 		jenkinsStatus;
	private String 		reportStatus;
	
	
	public int getStartFlowId() {
		return startFlowId;
	}
	public void setStartFlowId(int startFlowId) {
		this.startFlowId = startFlowId;
	}
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public String getStartBy() {
		return startBy;
	}
	public void setStartBy(String startBy) {
		this.startBy = startBy;
	}
	
	public String getJenkinsStatus() {
		return jenkinsStatus;
	}
	public void setJenkinsStatus(String jenkinsStatus) {
		this.jenkinsStatus = jenkinsStatus;
	}
	public String getReportStatus() {
		return reportStatus;
	}
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::StartFlowHistoryDTO:::::::::::::::::::::::" +
			" \n startFlowId				: "+startFlowId+
			" \n workflowId				: "+workflowId+
			" \n startTime				: "+startTime+
			" \n startBy				: "+startBy+
			" \n jenkinsStatus					: "+jenkinsStatus+
			" \n reportStatus					: "+reportStatus+
			" \n";
		return str;
	}
}
