package com.techm.psd.workflowType.dao;

import java.util.List;

import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.exception.PSDException;

public interface WorkflowTypeDAO {

	public boolean 					saveWorkflowType(WorkflowTypeDTO wDTO)							throws PSDException;
	public boolean 					updateWorkflowType(WorkflowTypeDTO wDTO)						throws PSDException;
	public List<WorkflowTypeDTO> 	getWorkflowTypeList()											throws PSDException;
	public WorkflowTypeDTO 			getWorkflowTypeDetailsById(int workflowTypeId)					throws PSDException;
	public boolean 					isValidateWorkflowTypeName(String appName, int workflowTypeId) 	throws PSDException; 
}
