package com.techm.psd.user.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import org.apache.log4j.Logger;

import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.UserLvlDTO;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;
import com.techm.psd.user.dao.UserDAO;
import com.techm.psd.user.dao.UserDAOImpl;

public class UserDAOImpl extends BaseDAO implements UserDAO{

	Logger LOGGER = Logger.getLogger(UserDAOImpl.class);

	/**
	 * @param 	UserId
	 * @return 	User
	 * @author 	aj797k
	 */
	@Override
	public UserDTO getUser(String userId) 									throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getUser()");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		UserDTO 			user 	= null;
		
		try
		{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_USER_BY_UID);
			pstmt.setString(1, userId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				user = new UserDTO();
				user.setUserId				(rs.getString	("USER_ID")				);
				user.setPassword			(rs.getString	("PASSWORD")			);
				user.setFirstName			(rs.getString	("USER_FNAME")			);
				user.setLastName			(rs.getString	("USER_LNAME")			);
				user.setUserName			(rs.getString	("USER_FNAME")+" "+rs.getString("USER_LNAME"));
				user.setUserEmailId			(rs.getString	("USER_EMAIL_ID")		);
				user.setUserContactNo		(rs.getString	("USER_CONTACT_NO")		);
				user.setUserTeam			(rs.getString	("USER_TEAM")			);
				user.setAccountCreatedDate	(rs.getTimestamp("ACCOUNT_CREATED_DATE"));
				user.setLevelId				(rs.getInt		("LVL_ID")				);
				user.setLevelName			(rs.getString	("LVL_NAME")			);
				user.setAppId				(rs.getInt		("APP_ID")				);
				user.setAppName				(rs.getString	("APP_NAME")			);
				user.setProfileId			(rs.getInt		("PRF_ID")				);
				user.setProfileName			(rs.getString	("PRF_NAME")			);
				user.setLastLogin			(rs.getTimestamp("LAST_LOGIN_DATE")		);
				
			}
			LOGGER.info("Exits from UserDAOImpl.getUser()");
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getUser() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		
		return user;
	}
	
	@Override
	public UserDTO saveNewUser(UserDTO user) 								throws PSDException {
		LOGGER.info("Enter into UserDAOImpl.saveNewUser()!");
		Connection 			conn 	= null;
		PreparedStatement 	pStmt	= null;
		
		try {
			
	        conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			pStmt = conn.prepareStatement(SQLConstants.SAVE_NEW_USER);
			
			pStmt.setString(1, user.getUserId());								// USER_ID Expected 	
			pStmt.setString(2, user.getFirstName());							// USER_FNAME				
			pStmt.setString(3, user.getLastName());								// USER_LNAME				
			pStmt.setString(4, user.getUserEmailId());							// USER_EMAIL_ID			
			pStmt.setString(5, user.getUserContactNo());						// USER_CONTACT_NO	
						
			pStmt.execute();
		} 
		catch (SQLException e) {
			user.setResponseMsg("UNABLE TO SAVE NEW USER");
			LOGGER.error("SQLException in UserDAOImpl.saveNewUser()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pStmt, conn);
		}
		LOGGER.info("Exit from UserDAOImpl.saveNewUser()!");
		return user;
	}

	@Override
	public List<UserLvlDTO> 	getUserLevelList() 							throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getUserLevelList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<UserLvlDTO> userLvlList = new ArrayList<UserLvlDTO>();
		
		try
		{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_USER_LEVEL_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				UserLvlDTO uLvlDTO = new UserLvlDTO();
				uLvlDTO.setLevelId		(rs.getInt		("LVL_ID")		);
				uLvlDTO.setLevelName	(rs.getString	("LVL_NAME")	);
				uLvlDTO.setLevelScope	(rs.getString	("LVL_SCOPE")	);
				uLvlDTO.setLevelDesc	(rs.getString	("LVL_DESC")	);
				uLvlDTO.setLevelDefault	(rs.getString	("LVL_DEFAULT")	);
				
				userLvlList.add(uLvlDTO);
			}
			LOGGER.info("Exits from UserDAOImpl.getUserLevelList()! appList.size"+userLvlList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getUserLevelList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		
		return userLvlList;
	}
	
	@Override
	public List<ProfileDTO> 	getProfileList() 							throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getProfileList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<ProfileDTO> userPrfList = new ArrayList<ProfileDTO>();
		
		try
		{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_USER_PROFILE_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				ProfileDTO uPrfDTO = new ProfileDTO();
				uPrfDTO.setProfileId		(rs.getInt		("PRF_ID")		);
				uPrfDTO.setProfileName		(rs.getString	("PRF_NAME")	);
				uPrfDTO.setProfileScope		(rs.getString	("PRF_SCOPE")	);
				uPrfDTO.setProfileDesc		(rs.getString	("PRF_DESC")	);
				uPrfDTO.setProfileDefault	(rs.getString	("PRF_DEFAULT")	);
				
				userPrfList.add(uPrfDTO);
			}
			LOGGER.info("Exits from UserDAOImpl.getProfileList()! appList.size"+userPrfList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getProfileList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		
		return userPrfList;
	}
	
	@Override
	public boolean 				saveUpdateUserAccessRequest(UserDTO uDTO)	throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.updateUserAccessRequestList()!");
		Connection 			conn 	= null;
		CallableStatement 	ocs 	= null;
		
		int 				result 	= -1;
		boolean 			response= false;
		
		try{
			conn 		= getConnection();
			ocs			= conn.prepareCall(
							"{call SP_SAVE_UPDATE_USER_REQUEST(?,?,?,?,?,?,?,?,?)}");
			
			ocs.setString	(1, uDTO.getUserId()			);						// USER_ID Expected 	
			ocs.setInt		(2, uDTO.getRequestedLevelId()	);						// REQUESTED_LVL_ID				
			ocs.setInt		(3, uDTO.getRequestedAppId()	);						// REQUESTED_APP_ID				
			ocs.setInt		(4, uDTO.getRequestedProfileId());						// REQUESTED_PRF_ID			
			ocs.setString	(5, uDTO.getUserTeam()			);						// USER_TEAM 	
			ocs.setString	(6, SQLConstants._PENDING		);						// STATUS
			ocs.setString	(7, uDTO.getRequesterComment()	);						// REQUESTER_COMMENT
			ocs.registerOutParameter(8, Types.INTEGER		);						//
			ocs.registerOutParameter(9, Types.VARCHAR		);						// 
			
			ocs.execute();
			result = ocs.getInt(8);
			if(result == 0){response = true;}else{response = false;}
		}
		catch(SQLException sqlEx){
			response = false;
			LOGGER.error("Exception in UserDAOImpl.updateUserAccessRequestList() while getting user from database. : " + sqlEx);
		}finally{
			close(ocs, conn);
		}
		LOGGER.info("Exits from UserDAOImpl.updateUserAccessRequestList()!");
		return response;
	}
	
	@Override
	public List<UserDTO> 		getAllPenidngAccessRequest() 				throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getUserRequestList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<UserDTO> 		uDTOList= new ArrayList<UserDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_USERS_PENDING_REQUEST_LIST);
			pstmt.setString(1, SQLConstants._PENDING);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				UserDTO uDTO = new UserDTO();
				uDTO.setRequestId			(rs.getInt		("REQUEST_ID")		);							//  REQUEST_ID
				uDTO.setUserId				(rs.getString	("USER_ID")			);							//  USER_ID
				uDTO.setFirstName			(rs.getString	("USER_FNAME")		);							//  USER_FNAME
				uDTO.setLastName			(rs.getString	("USER_LNAME")		);							//  USER_LNAME
				uDTO.setUserName			(rs.getString	("USER_FNAME")+" "+rs.getString("USER_LNAME"));	//  USER_NAME
				uDTO.setRequestedLevelId	(rs.getInt		("REQUESTED_LVL_ID"));							//	REQUESTED_LVL_ID
				uDTO.setRequestedLevelName	(rs.getString	("LVL_NAME")		);							//	LVL_NAME
				uDTO.setRequestedAppId		(rs.getInt		("REQUESTED_APP_ID"));							//	REQUESTED_APP_ID
				uDTO.setRequestedAppName	(rs.getString	("APP_NAME")		);							//	APP_NAME
				uDTO.setRequestedProfileId	(rs.getInt		("REQUESTED_PRF_ID"));							//	REQUESTED_PRF_ID
				uDTO.setRequestedProfileName(rs.getString	("PRF_NAME")		);							//	PRF_NAME
				uDTO.setStatus				(rs.getString	("STATUS")			);							// 	STATUS
				uDTO.setRequestDate			(rs.getTimestamp("REQUEST_DATE")	);							// 	REQUEST_DATE
				uDTO.setRequesterComment	(rs.getString	("REQUESTER_COMMENT"));							// 	REQUESTER_COMMENT
				uDTO.setApprovedBy			(rs.getString	("APPROVED_BY")		);							// 	APPROVED_BY
				uDTO.setApproveDate			(rs.getTimestamp("APPROVED_DATE")	);							// 	APPROVED_DATE
				uDTO.setApproverComment		(rs.getString	("APPROVER_COMMENT"));							//  APPROVER_COMMENT
				uDTOList.add(uDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getUserRequestList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from UserDAOImpl.getUserRequestList()!");
		return uDTOList;
	}
	
	@Override
	public boolean 				approveRejectUserAccessRequestList(int[] selRequests, String approvedBy, String status, String comment) 	throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.updateUserAccessRequestList()!");
		Connection 			conn 	= null;
		CallableStatement 	ocs 	= null;
		
		int 				result 	= -1;
		boolean 			response= false;
		
		try{
			conn 		= getConnection();
			ocs			= conn.prepareCall(
							"{call SP_APPROVE_REJECT_USER_REQUEST(?,?,?,?,?,?)}");
			
			ArrayDescriptor arrDesc = ArrayDescriptor.createDescriptor("TYP_REQUEST_ID", conn);
			Array array 			= new ARRAY(arrDesc, conn, selRequests);
			
			ocs.setArray			(1, array			);
			ocs.setString			(2, approvedBy		);
			ocs.setString			(3, status			);
			ocs.setString			(4, comment			);
			ocs.registerOutParameter(5, Types.INTEGER	);
			ocs.registerOutParameter(6, Types.VARCHAR	);
			
			ocs.execute();
			result = ocs.getInt(8);
			if(result == 0){response = true;}else{response = false;}
		}
		catch(SQLException sqlEx){
			response	= false;
			LOGGER.error("Exception in UserDAOImpl.updateUserAccessRequestList() while getting user from database. : " + sqlEx);
		}finally{
			close(ocs, conn);
		}
		LOGGER.info("Exits from UserDAOImpl.updateUserAccessRequestList()!");
		//TODO: Return value accourding to status
		return response;
		
	}
	
	/*@Override
	public UserDTO 				getPenidngUserRequest(UserDTO uDTO)			throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getPenidngUserRequest()!");
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_PENDING_USER_REQUEST);
			pstmt.setString(1, uDTO.getUserId());
			pstmt.setString(2, SQLConstants._PENDING);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				uDTO.setRequestId			(rs.getInt		("REQUEST_ID")		);							//  REQUEST_ID
				uDTO.setUserId				(rs.getString	("USER_ID")			);							//  USER_ID
				uDTO.setFirstName			(rs.getString	("USER_FNAME")		);							//  USER_FNAME
				uDTO.setLastName			(rs.getString	("USER_LNAME")		);							//  USER_LNAME
				uDTO.setUserName			(rs.getString	("USER_FNAME")+" "+rs.getString("USER_LNAME"));	//  USER_NAME
				uDTO.setRequestedLevelId	(rs.getInt		("REQUESTED_LVL_ID"));							//	REQUESTED_LVL_ID
				uDTO.setRequestedLevelName	(rs.getString	("LVL_NAME")		);							//	LVL_NAME
				uDTO.setRequestedAppId		(rs.getInt		("REQUESTED_APP_ID"));							//	REQUESTED_APP_ID
				uDTO.setRequestedAppName	(rs.getString	("APP_NAME")		);							//	APP_NAME
				uDTO.setRequestedProfileId	(rs.getInt		("REQUESTED_PRF_ID"));							//	REQUESTED_PRF_ID
				uDTO.setRequestedProfileName(rs.getString	("PRF_NAME")		);							//	PRF_NAME
				uDTO.setStatus				(rs.getString	("STATUS")			);							// 	STATUS
				uDTO.setRequestDate			(rs.getTimestamp("REQUEST_DATE")	);							// 	REQUEST_DATE
				uDTO.setRequesterComment	(rs.getString	("REQUESTER_COMMENT"));							// 	REQUESTER_COMMENT
				uDTO.setApprovedBy			(rs.getString	("APPROVED_BY")		);							// 	APPROVED_BY
				uDTO.setApproveDate			(rs.getTimestamp("APPROVED_DATE")	);							// 	APPROVED_DATE
				uDTO.setApproverComment		(rs.getString	("APPROVER_COMMENT"));							//  APPROVER_COMMENT
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getPenidngUserRequest() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from UserDAOImpl.getPenidngUserRequest()!");
		return uDTO;
	}*/
	
	/*@Override
	public boolean 				updateUserRequest(UserDTO uDTO) 	throws PSDException {
		LOGGER.info("Enter into UserDAOImpl.updateUserRequest()!");
		Connection 			conn 	= null;
		PreparedStatement 	pStmt 	= null;
		boolean 			result	= false;
		try {
			
	        conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pStmt = conn.prepareStatement(SQLConstants.UPDATE_USER_ACCESS_REQUEST_BY_ID);
			pStmt.setString	(1, uDTO.getStatus()			);						// APPROVED/REJECTED/CANCEL STATUS
			pStmt.setString	(2, uDTO.getApprovedBy()		);						// APPROVED BY
			pStmt.setString	(3, uDTO.getApproverComment()	);						// COOMENT
			pStmt.setInt	(4, uDTO.getRequestId()			);						// REQUEST ID
			
			if(pStmt.executeUpdate() == 1){
				result = true;
			}else{
				result = false;
			}
			
			// Committing transaction 
			conn.commit();
	        
		} 
		catch (SQLException e) {
			result = false;
			LOGGER.fatal("SQLException in UserDAOImpl.updateUserRequest()>>"+e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally{
			close(pStmt, conn);
		}
		LOGGER.info("Exit from UserDAOImpl.updateUserRequest()! result : "+result);
		return result;
	}*/
	
	/*@Override
	public List<UserDTO> 		getAllRequestByUserID(String userId)throws PSDException{
		LOGGER.info("Enters into UserDAOImpl.getUserRequestList()!");
		List<UserDTO> uDTOList = new ArrayList<UserDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_USER_REQUEST_LIST);
			pstmt.setString(1, userId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				UserDTO uDTO = new UserDTO();
				uDTO.setRequestId			(rs.getInt		("REQUEST_ID")		);							//  REQUEST_ID
				uDTO.setUserId				(rs.getString	("USER_ID")			);							//  USER_ID
				uDTO.setFirstName			(rs.getString	("USER_FNAME")		);							//  USER_FNAME
				uDTO.setLastName			(rs.getString	("USER_LNAME")		);							//  USER_LNAME
				uDTO.setUserName			(rs.getString	("USER_FNAME")+" "+rs.getString("USER_LNAME"));	//  USER_NAME
				uDTO.setLevelId				(rs.getInt		("REQUESTED_LVL_ID"));							//	REQUESTED_LVL_ID
				uDTO.setLevelName			(rs.getString	("LVL_NAME")		);							//	LVL_NAME
				uDTO.setAppId				(rs.getInt		("REQUESTED_APP_ID"));							//	REQUESTED_APP_ID
				uDTO.setAppName				(rs.getString	("APP_NAME")		);							//	APP_NAME
				uDTO.setProfileId			(rs.getInt		("REQUESTED_PRF_ID"));							//	REQUESTED_PRF_ID
				uDTO.setProfileName			(rs.getString	("PRF_NAME")		);							//	PRF_NAME
				uDTO.setStatus				(rs.getString	("STATUS")			);							// 	STATUS
				uDTO.setRequestDate			(rs.getTimestamp("REQUEST_DATE")	);							// 	REQUEST_DATE
				uDTO.setRequesterComment	(rs.getString	("REQUESTER_COMMENT"));							// 	REQUESTER_COMMENT
				uDTO.setApprovedBy			(rs.getString	("APPROVED_BY")		);							// 	APPROVED_BY
				uDTO.setApproveDate			(rs.getTimestamp("APPROVED_DATE")	);							// 	APPROVED_DATE
				uDTO.setApproverComment		(rs.getString	("APPROVER_COMMENT"));							//  APPROVER_COMMENT
				uDTOList.add(uDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getUserRequestList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from UserDAOImpl.getUserRequestList()!");
		return uDTOList;
	}*/
	
	/*@Override
	public List<UserDTO> 		getAllRequest() 					throws PSDException {
		LOGGER.info("Enters into UserDAOImpl.getUserRequestList()!");
		List<UserDTO> uDTOList = new ArrayList<UserDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_REQUEST_LIST);
			pstmt.setString(1, SQLConstants._PENDING);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				UserDTO uDTO = new UserDTO();
				uDTO.setRequestId			(rs.getInt		("REQUEST_ID")		);							//  REQUEST_ID
				uDTO.setUserId				(rs.getString	("USER_ID")			);							//  USER_ID
				uDTO.setFirstName			(rs.getString	("USER_FNAME")		);							//  USER_FNAME
				uDTO.setLastName			(rs.getString	("USER_LNAME")		);							//  USER_LNAME
				uDTO.setUserName			(rs.getString	("USER_FNAME")+" "+rs.getString("USER_LNAME"));	//  USER_NAME
				uDTO.setLevelId				(rs.getInt		("REQUESTED_LVL_ID"));							//	REQUESTED_LVL_ID
				uDTO.setLevelName			(rs.getString	("LVL_NAME")		);							//	LVL_NAME
				uDTO.setAppId				(rs.getInt		("REQUESTED_APP_ID"));							//	REQUESTED_APP_ID
				uDTO.setAppName				(rs.getString	("APP_NAME")		);							//	APP_NAME
				uDTO.setProfileId			(rs.getInt		("REQUESTED_PRF_ID"));							//	REQUESTED_PRF_ID
				uDTO.setProfileName			(rs.getString	("PRF_NAME")		);							//	PRF_NAME
				uDTO.setStatus				(rs.getString	("STATUS")			);							// 	STATUS
				uDTO.setRequestDate			(rs.getTimestamp("REQUEST_DATE")	);							// 	REQUEST_DATE
				uDTO.setRequesterComment	(rs.getString	("REQUESTER_COMMENT"));							// 	REQUESTER_COMMENT
				uDTO.setApprovedBy			(rs.getString	("APPROVED_BY")		);							// 	APPROVED_BY
				uDTO.setApproveDate			(rs.getTimestamp("APPROVED_DATE")	);							// 	APPROVED_DATE
				uDTO.setApproverComment		(rs.getString	("APPROVER_COMMENT"));							//  APPROVER_COMMENT
				uDTOList.add(uDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in UserDAOImpl.getUserRequestList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from UserDAOImpl.getUserRequestList()!");
		return uDTOList;
	}*/

	public static void main(String[] args) {
		
	}
}