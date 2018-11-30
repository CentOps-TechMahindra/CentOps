package com.techm.psd.user.dao;

import java.util.List;

import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.UserLvlDTO;
import com.techm.psd.exception.PSDException;

public interface UserDAO {
	
	public UserDTO 				getUser(String userId) 							throws PSDException;
	public UserDTO 				saveNewUser(UserDTO user) 						throws PSDException;
	
	public List<UserLvlDTO> 	getUserLevelList()								throws PSDException;
	public List<ProfileDTO> 	getProfileList()								throws PSDException;
	
	//
	public boolean 				saveUpdateUserAccessRequest(UserDTO uDTO) 		throws PSDException;
	//public UserDTO 			getPenidngUserRequest(UserDTO uDTO)				throws PSDException;
	
	//
	public List<UserDTO> 		getAllPenidngAccessRequest() 					throws PSDException;
	public boolean 				approveRejectUserAccessRequestList(int[] selRequests, String approvedBy, String status, String approvalComment) 	throws PSDException;
	
	
	//public boolean 			updateUserRequest(UserDTO uDTO) 				throws PSDException;
	
	//public List<UserDTO> 		getAllRequestByUserID(String userId)			throws PSDException;
	//public List<UserDTO> 		getAllRequest() 								throws PSDException;
	
}
