package com.techm.psd.application.form;

import java.sql.Timestamp;
import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.form.BaseForm;

public class ApplicationForm extends BaseForm{

	private int 				appId;
	private String				appName;
	private String				createBy;
	private Timestamp			createDate;
	private String				updateBy;
	private Timestamp			updateDate;
	private String				desc;
	private int					appsId;
	
	private List<ApplicationDTO> appDTOList;
	
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
	public String getDesc() {
		return desc;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<ApplicationDTO> getAppDTOList() {
		return appDTOList;
	}
	public void setAppDTOList(List<ApplicationDTO> appDTOList) {
		this.appDTOList = appDTOList;
	}
	public int getAppsId() {
		return appsId;
	}
	public void setAppsId(int appsId) {
		this.appsId = appsId;
	}
}
