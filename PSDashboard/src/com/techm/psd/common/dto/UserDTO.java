package com.techm.psd.common.dto;

import java.sql.Timestamp;
import java.util.Date;

public class UserDTO {
	
	private String 				userId;
	private String				password;
	private String 				firstName;
	private String 				lastName;
	private String 				userName;
	private String 				userEmailId;
	private String 				userContactNo;
	private String 				userTeam;
	private Timestamp   		accountCreatedDate;
	private int    				levelId;
	private String 				levelName;
	private int    				appId;
	private String 				appName;
	private int    				profileId;
	private String 				profileName;
	private Timestamp 			lastLogin;
	
	private int    				requestId;
	private int    				requestedLevelId;
	private String 				requestedLevelName;
	private int    				requestedAppId;
	private String 				requestedAppName;
	private int    				requestedProfileId;
	private String 				requestedProfileName;
	private String 				status;
	private Timestamp 			requestDate;
	private String 				requesterComment;
	
	private String				approvedBy;
	private Timestamp 			approveDate;
	private String 				approverComment;
	
	private String 				responseMsg;
	private String 				ldapStatus;
	private boolean 			isNewUser = false;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmailId() {
		return userEmailId;
	}
	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}
	public String getUserContactNo() {
		return userContactNo;
	}
	public void setUserContactNo(String userContactNo) {
		this.userContactNo = userContactNo;
	}
	public String getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(String userTeam) {
		this.userTeam = userTeam;
	}
	public Timestamp getAccountCreatedDate() {
		return accountCreatedDate;
	}
	public void setAccountCreatedDate(Timestamp accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
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
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getRequestedLevelId() {
		return requestedLevelId;
	}
	public void setRequestedLevelId(int requestedLevelId) {
		this.requestedLevelId = requestedLevelId;
	}
	public String getRequestedLevelName() {
		return requestedLevelName;
	}
	public void setRequestedLevelName(String requestedLevelName) {
		this.requestedLevelName = requestedLevelName;
	}
	public int getRequestedAppId() {
		return requestedAppId;
	}
	public void setRequestedAppId(int requestedAppId) {
		this.requestedAppId = requestedAppId;
	}
	public String getRequestedAppName() {
		return requestedAppName;
	}
	public void setRequestedAppName(String requestedAppName) {
		this.requestedAppName = requestedAppName;
	}
	public int getRequestedProfileId() {
		return requestedProfileId;
	}
	public void setRequestedProfileId(int requestedProfileId) {
		this.requestedProfileId = requestedProfileId;
	}
	public String getRequestedProfileName() {
		return requestedProfileName;
	}
	public void setRequestedProfileName(String requestedProfileName) {
		this.requestedProfileName = requestedProfileName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequesterComment() {
		return requesterComment;
	}
	public void setRequesterComment(String requesterComment) {
		this.requesterComment = requesterComment;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Timestamp getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(Timestamp approveDate) {
		this.approveDate = approveDate;
	}
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public String getLdapStatus() {
		return ldapStatus;
	}
	public void setLdapStatus(String ldapStatus) {
		this.ldapStatus = ldapStatus;
	}
	public boolean isNewUser() {
		return isNewUser;
	}
	public void setNewUser(boolean isNewUser) {
		this.isNewUser = isNewUser;
	}
	
	
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::UserDTO:::::::::::::::::::::::" +
			" \n userId				: "+userId+
			" \n firstName			: "+firstName+	
			" \n lastName			: "+lastName+	
			" \n userName			: "+userName+
			" \n userEmailId			: "+userEmailId+
			" \n userContactNo			: "+userContactNo+	
			" \n userTeam			: "+userTeam+
			" \n accountCreatedDate		: "+accountCreatedDate+	
			" \n levelId			: "+levelId+
			" \n levelName			: "+levelName+
			" \n appId				: "+appId+
			" \n appName			: "+appName+
			" \n profileId			: "+profileId+
			" \n profileName			: "+profileName+
			" \n lastLogin			: "+lastLogin+
			" \n requestId			: "+requestId+
			" \n requestedLevelId			: "+requestedLevelId+
			" \n requestedLevelName			: "+requestedLevelName+
			" \n requestedAppId			: "+requestedAppId+
			" \n requestedAppName			: "+requestedAppName+
			" \n requestedProfileId			: "+requestedProfileId+
			" \n requestedProfileName			: "+requestedProfileName+
			" \n requestedProfileName			: "+requestedProfileName+
			" \n status			: "+status+
			" \n requestDate			: "+requestDate+
			" \n requesterComment			: "+requesterComment+
			" \n approvedBy			: "+approvedBy+
			" \n approveDate			: "+approveDate+
			" \n approverComment			: "+approverComment+
			" \n responseMsg			: "+responseMsg+
			" \n ldapStatus			: "+ldapStatus+
			" \n isNewUser			: "+isNewUser+
			" \n";
		return str;
	}
	
}
