package com.techm.psd.common.dto;

import java.sql.Timestamp;


public class BuildDTO {

	private int			workflowId;
	private int			buildNo;
	private int			id;
	private String		fullDisplayName;
	private String 		displayName;		
	private String		builtOn;
	private String		url;
	private int			queueId;
	private Timestamp	timestamp;
	private long		estimatedDuration;
	private int 		duration;
	private String		shortDescription;
	private String		addr;
	private String		userId;
	private String		userName;
	private int			upstreamBuild;
	private String		upstreamUrl;
	private String		upstreamProject;
	private String		result;
	private int			startFlowId;
	
	
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public int getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(int buildNo) {
		this.buildNo = buildNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullDisplayName() {
		return fullDisplayName;
	}
	public void setFullDisplayName(String fullDisplayName) {
		this.fullDisplayName = fullDisplayName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getBuiltOn() {
		return builtOn;
	}
	public void setBuiltOn(String builtOn) {
		this.builtOn = builtOn;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getQueueId() {
		return queueId;
	}
	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public long getEstimatedDuration() {
		return estimatedDuration;
	}
	public void setEstimatedDuration(long estimatedDuration) {
		this.estimatedDuration = estimatedDuration;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUpstreamBuild() {
		return upstreamBuild;
	}
	public void setUpstreamBuild(int upstreamBuild) {
		this.upstreamBuild = upstreamBuild;
	}
	public String getUpstreamUrl() {
		return upstreamUrl;
	}
	public void setUpstreamUrl(String upstreamUrl) {
		this.upstreamUrl = upstreamUrl;
	}
	public String getUpstreamProject() {
		return upstreamProject;
	}
	public void setUpstreamProject(String upstreamProject) {
		this.upstreamProject = upstreamProject;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getStartFlowId() {
		return startFlowId;
	}
	public void setStartFlowId(int startFlowId) {
		this.startFlowId = startFlowId;
	}
	
	
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::BuildDTO:::::::::::::::::::::::" +
			" \n workflowId				: "+workflowId+
			" \n buildNo				: "+buildNo+
			" \n id				: "+id+
			" \n fullDisplayName				: "+fullDisplayName+
			" \n displayName				: "+displayName+
			" \n builtOn				: "+builtOn+
			" \n url				: "+url+
			" \n queueId				: "+queueId+
			" \n timestamp				: "+timestamp+
			" \n estimatedDuration				: "+estimatedDuration+
			" \n duration				: "+duration+
			" \n shortDescription				: "+shortDescription+
			" \n addr				: "+addr+
			" \n userName				: "+userName+
			" \n userId				: "+userId+
			" \n upstreamBuild				: "+upstreamBuild+
			" \n upstreamUrl				: "+upstreamUrl+
			" \n upstreamProject				: "+upstreamProject+
			" \n result				: "+result+
			" \n startFlowId				: "+startFlowId+
			" \n";
		return str;
	}
	
}
