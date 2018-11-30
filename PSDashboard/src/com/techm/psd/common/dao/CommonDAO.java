package com.techm.psd.common.dao;

import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.exception.PSDException;

public interface CommonDAO {

	public boolean 					saveUserLoginLog(String userId) 				throws PSDException;
//	public List<ApplicationDTO> 	getApplicationList()							throws PSDException;
//	public ApplicationDTO 			getApplicationDetailsById(int appId)			throws PSDException;
//	public List<WorkflowTypeDTO>	getWorkflowTypeList()							throws PSDException;
	//public WorkflowTypeDTO 			getWorkflowTypeDetailsById(int workflowTypeId)	throws PSDException;
}
