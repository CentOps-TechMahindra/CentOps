package com.techm.psd.user.form;

import java.sql.Timestamp;
import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.UserLvlDTO;
import com.techm.psd.common.form.BaseForm;

public class UserForm extends BaseForm{

	private String 					userId;
	private String 					firstName;
	private String 					lastName;
	private String 					userName;
	private String 					userEmailId;
	private String 					userContactNo;
	private String 					userTeam;
	private List<UserLvlDTO> 		userLvlList;
	private int						userLvlId;
	private String					userLvlName;
	private List<ApplicationDTO> 	appList;
	private int						appId;
	private String					appName;
	private List<ProfileDTO> 		profileList;
	private int						profileId;
	private String					profileName;
	private Timestamp				accountCreatedDate;
	private String 					requestStatus;
	private Timestamp 				lastLogin;
	
	//
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
	
	private String 				approvedBy;
	private Timestamp 			approveDate;
	private String 				approverComment;
	
	private UserDTO				uDTO;
	
	private List<UserDTO>		userDtoList;
	
	private int[]			selRequests;
	
	public int[] getSelRequests() {
		return selRequests;
	}
	public void setSelRequests(int[] selRequests) {
		this.selRequests = selRequests;
	}
	public UserDTO getUDTO() {
		return uDTO;
	}
	public List<UserDTO> getUserDtoList() {
		return userDtoList;
	}
	public void setUserDtoList(List<UserDTO> userDtoList) {
		this.userDtoList = userDtoList;
	}
	public void setUDTO(UserDTO udto) {
		uDTO = udto;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public List<UserLvlDTO> getUserLvlList() {
		return userLvlList;
	}
	public void setUserLvlList(List<UserLvlDTO> userLvlList) {
		this.userLvlList = userLvlList;
	}
	public int getUserLvlId() {
		return userLvlId;
	}
	public void setUserLvlId(int userLvlId) {
		this.userLvlId = userLvlId;
	}
	public String getUserLvlName() {
		return userLvlName;
	}
	public void setUserLvlName(String userLvlName) {
		this.userLvlName = userLvlName;
	}
	public List<ApplicationDTO> getAppList() {
		return appList;
	}
	public void setAppList(List<ApplicationDTO> appList) {
		this.appList = appList;
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
	public List<ProfileDTO> getProfileList() {
		return profileList;
	}
	public void setProfileList(List<ProfileDTO> profileList) {
		this.profileList = profileList;
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
	public Timestamp getAccountCreatedDate() {
		return accountCreatedDate;
	}
	public void setAccountCreatedDate(Timestamp accountCreatedDate) {
		this.accountCreatedDate = accountCreatedDate;
	}
	public String getRequesterComment() {
		return requesterComment;
	}
	public void setRequesterComment(String requesterComment) {
		this.requesterComment = requesterComment;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
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
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public Timestamp getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Timestamp requestDate) {
		this.requestDate = requestDate;
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
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::UserDTO:::::::::::::::::::::::" +
			" \n userId			: "+userId+
			" \n firstName			: "+firstName+	
			" \n lastName			: "+lastName+	
			" \n userName			: "+userName+
			" \n userEmailId			: "+userEmailId+
			" \n userContactNo			: "+userContactNo+	
			" \n userTeam			: "+userTeam+
			" \n userLvlId			: "+userLvlId+
			" \n userLvlName			: "+userLvlName+
			" \n profileId			: "+profileId+
			" \n profileName			: "+profileName+
			" \n appId			: "+appId+
			" \n appName			: "+appName+
			" \n requesterComment			: "+requesterComment+
			" \n requestStatus			: "+requestStatus+
			" \n";
		return str;
	}
	
	
}
