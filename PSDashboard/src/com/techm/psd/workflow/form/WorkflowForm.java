package com.techm.psd.workflow.form;

import java.sql.Timestamp;
import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.common.form.BaseForm;

public class WorkflowForm extends BaseForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String		copyWorkflow;
	private int 		workflowId;
	private String 		workflowName;
	private int 		selWTypeId;
	private String 		workflowType;
	private List<WorkflowTypeDTO> 	workflowTypeList;
	private int 		selAppId;
	private String 		appName;
	private List<ApplicationDTO> 	appList;
	private String 		server;
	private int 		port;
	private String 		username;
	private String 		password;
	private String		authToken;
	private String 		jobName;
	private String 		buildType;
	private String 		buildParameter;
	private String[]	parametersName;
	private String 		desc;
	private String 		createdBy;
	private Timestamp 	createDate;
	private String 		lastUpdatedBy;
	private Timestamp 	lastUpdatedDate;
	private String		newWorkflowTypeName;
	private String		newApplicationName;
	
	private List<ProfileDTO> profileList;
	private int[]			selProfileIds;
	private List<FixItDTO> 	fixItDTOList;
	
	//REPORT DB DETAILS
	private String 		db_type;
	private String 		db_username;
	private String 		db_password;
	private String		db_hostname;
	private int 		db_port;
	private String		db_sid;
	private String		db_table_name;
	private String		db_query;
	
	//FIXIT WORKFLOW
	private String 		jsonArr;
	private	String 		jsonWorkflowList;
	
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
	public int getSelWTypeId() {
		return selWTypeId;
	}
	public void setSelWTypeId(int selWTypeId) {
		this.selWTypeId = selWTypeId;
	}
	public String getWorkflowType() {
		return workflowType;
	}
	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}
	public List<WorkflowTypeDTO> getWorkflowTypeList() {
		return workflowTypeList;
	}
	public void setWorkflowTypeList(List<WorkflowTypeDTO> workflowTypeList) {
		this.workflowTypeList = workflowTypeList;
	}
	public int getSelAppId() {
		return selAppId;
	}
	public void setSelAppId(int selAppId) {
		this.selAppId = selAppId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public List<ApplicationDTO> getAppList() {
		return appList;
	}
	public void setAppList(List<ApplicationDTO> appList) {
		this.appList = appList;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getBuildType() {
		return buildType;
	}
	public void setBuildType(String buildType) {
		this.buildType = buildType;
	}
	public String getBuildParameter() {
		return buildParameter;
	}
	public void setBuildParameter(String buildParameter) {
		this.buildParameter = buildParameter;
	}
	public String[] getParametersName() {
		return parametersName;
	}
	public void setParametersName(String[] parametersName) {
		this.parametersName = parametersName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Timestamp getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	public List<ProfileDTO> getProfileList() {
		return profileList;
	}
	public void setProfileList(List<ProfileDTO> profileList) {
		this.profileList = profileList;
	}
	public int[] getSelProfileIds() {
		return selProfileIds;
	}
	public void setSelProfileIds(int[] selProfileIds) {
		this.selProfileIds = selProfileIds;
	}
	
	
	//
	public String getDb_type() {
		return db_type;
	}
	public void setDb_type(String db_type) {
		this.db_type = db_type;
	}
	public String getDb_username() {
		return db_username;
	}
	public void setDb_username(String db_username) {
		this.db_username = db_username;
	}
	public String getDb_password() {
		return db_password;
	}
	public void setDb_password(String db_password) {
		this.db_password = db_password;
	}
	public String getDb_hostname() {
		return db_hostname;
	}
	public void setDb_hostname(String db_hostname) {
		this.db_hostname = db_hostname;
	}
	public int getDb_port() {
		return db_port;
	}
	public void setDb_port(int db_port) {
		this.db_port = db_port;
	}
	public String getDb_sid() {
		return db_sid;
	}
	public void setDb_sid(String db_sid) {
		this.db_sid = db_sid;
	}
	public String getDb_table_name() {
		return db_table_name;
	}
	public void setDb_table_name(String db_table_name) {
		this.db_table_name = db_table_name;
	}
	public String getDb_query() {
		return db_query;
	}
	public void setDb_query(String db_query) {
		this.db_query = db_query;
	}
	public String getNewWorkflowTypeName() {
		return newWorkflowTypeName;
	}
	public void setNewWorkflowTypeName(String newWorkflowTypeName) {
		this.newWorkflowTypeName = newWorkflowTypeName;
	}
	public String getNewApplicationName() {
		return newApplicationName;
	}
	public void setNewApplicationName(String newApplicationName) {
		this.newApplicationName = newApplicationName;
	}
	public String getCopyWorkflow() {
		return copyWorkflow;
	}
	public void setCopyWorkflow(String copyWorkflow) {
		this.copyWorkflow = copyWorkflow;
	}
	public String getJsonArr() {
		return jsonArr;
	}
	public void setJsonArr(String jsonArr) {
		this.jsonArr = jsonArr;
	}
	public List<FixItDTO> getFixItDTOList() {
		return fixItDTOList;
	}
	public void setFixItDTOList(List<FixItDTO> fixItDTOList) {
		this.fixItDTOList = fixItDTOList;
	}
	public String getJsonWorkflowList() {
		return jsonWorkflowList;
	}
	public void setJsonWorkflowList(String jsonWorkflowList) {
		this.jsonWorkflowList = jsonWorkflowList;
	}
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::WorkflowForm:::::::::::::::::::::::" +
			" \n workflowId			: "+workflowId+
			" \n workflowName			: "+workflowName+	
			" \n selAppId			: "+selAppId+	
			" \n server			: "+server+
			" \n port			: "+port+
			" \n username			: "+username+	
			" \n password			: "+password+	
			" \n jobName			: "+jobName+
			" \n buildType			: "+buildType+
			" \n buildParameter			: "+buildParameter+
			" \n desc			: "+desc+
			" \n createdBy			: "+createdBy+
			" \n createDate			: "+createDate+
			" \n lastUpdatedBy			: "+lastUpdatedBy+
			" \n lastUpdatedDate			: "+lastUpdatedDate+
			" \n";
		return str;
	}
	
}
