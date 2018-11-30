package com.techm.psd.dashboard.form;

import java.util.List;

import org.json.JSONObject;

import com.techm.psd.common.form.BaseForm;

public class DashboardForm extends BaseForm{

	/**
	 * 
	 */
	private static final long 		serialVersionUID = 1L;
	private List<JSONObject> 		jsonObject;
	private String 					filterAppList;
	private String 					filterWorkflowTypeJsonStr;
	
	public List<JSONObject> getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(List<JSONObject> jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getFilterAppList() {
		return filterAppList;
	}

	public void setFilterAppList(String filterAppList) {
		this.filterAppList = filterAppList;
	}
	public String getFilterWorkflowTypeJsonStr() {
		return filterWorkflowTypeJsonStr;
	}

	public void setFilterWorkflowTypeJsonStr(String filterWorkflowTypeJsonStr) {
		this.filterWorkflowTypeJsonStr = filterWorkflowTypeJsonStr;
	}
}
