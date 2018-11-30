package com.techm.psd.common.dto;

import java.sql.Timestamp;
import java.util.List;

public class ApplicationDTO {

	private int 				appId;
	private String				appName;
	private String				createBy;
	private Timestamp			createDate;
	private String				updateBy;
	private Timestamp			updateDate;
	private String				desc;
	private int					appsId;
	private List<WorkflowDTO>	wDTOList;
	
	
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
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public List<WorkflowDTO> getWDTOList() {
		return wDTOList;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public void setWDTOList(List<WorkflowDTO> list) {
		wDTOList = list;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getAppsId() {
		return appsId;
	}
	public void setAppsId(int appsId) {
		this.appsId = appsId;
	}
}
