package com.techm.psd.workflowType.form;

import java.sql.Timestamp;
import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.common.form.BaseForm;

public class WorkflowTypeForm extends BaseForm{

	private int			workflowTypeId;
	private String		workflowType;
	private	String		addedBy;
	private Timestamp	addedDate;
	private	String		updatedBy;
	private Timestamp	updatedDate;
	private	String		description;
	private List<WorkflowTypeDTO> workflowTypeDTOList;
	
	public int getWorkflowTypeId() {
		return workflowTypeId;
	}
	public void setWorkflowTypeId(int workflowTypeId) {
		this.workflowTypeId = workflowTypeId;
	}
	public String getWorkflowType() {
		return workflowType;
	}
	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Timestamp getAddedDate() {
		return addedDate;
	}
	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<WorkflowTypeDTO> getWorkflowTypeDTOList() {
		return workflowTypeDTOList;
	}
	public void setWorkflowTypeDTOList(List<WorkflowTypeDTO> workflowTypeDTOList) {
		this.workflowTypeDTOList = workflowTypeDTOList;
	}
}
