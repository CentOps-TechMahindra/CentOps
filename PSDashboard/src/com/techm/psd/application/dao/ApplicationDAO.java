package com.techm.psd.application.dao;

import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.exception.PSDException;

public interface ApplicationDAO {

	public boolean 					saveApplication(ApplicationDTO aDTO)			throws PSDException;
	public boolean 					updateApplication(ApplicationDTO aDTO)			throws PSDException;
	public List<ApplicationDTO> 	getApplicationList()							throws PSDException;
	public ApplicationDTO 			getApplicationDetailsById(int appId)			throws PSDException;
	public boolean 					isValidateAppName(String appName, int appId) 	throws PSDException; 
}
