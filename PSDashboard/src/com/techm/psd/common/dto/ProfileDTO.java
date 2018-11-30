package com.techm.psd.common.dto;

public class ProfileDTO {

	private int 	workflowId;
	private int 	profileId;
	private String 	profileName;
	private String 	profileScope;
	private String 	profileDesc;
	private String 	profileDefault;
	
	
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public String getProfileScope() {
		return profileScope;
	}
	public void setProfileScope(String profileScope) {
		this.profileScope = profileScope;
	}
	public String getProfileDesc() {
		return profileDesc;
	}
	public void setProfileDesc(String profileDesc) {
		this.profileDesc = profileDesc;
	}
	public String getProfileDefault() {
		return profileDefault;
	}
	public void setProfileDefault(String profileDefault) {
		this.profileDefault = profileDefault;
	}
}
