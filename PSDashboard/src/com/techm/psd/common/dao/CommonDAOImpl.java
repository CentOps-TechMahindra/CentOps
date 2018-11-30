package com.techm.psd.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class CommonDAOImpl extends BaseDAO implements CommonDAO {

	Logger LOGGER = Logger.getLogger(WorkflowDAOImpl.class);

	@Override
	public boolean saveUserLoginLog(String userId) throws PSDException {
		LOGGER.info("Enter into CommonDAOImpl.saveUserLoginLog()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		
		boolean 			result 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQLConstants.SAVE_USER_LOGIN_LOG);
			pstmt.setString	(1, userId);
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			result = false;
			LOGGER.error("SQLException in CommonDAOImpl.saveUserLoginLog()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from CommonDAOImpl.saveUserLoginLog()!");
		return result;
	}
	
	/*@Override
	public List<ApplicationDTO> 	getApplicationList() 					throws PSDException {
		LOGGER.info("Enters into CommonDAOImpl.getApplicationList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<ApplicationDTO> appList= new ArrayList<ApplicationDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_APP_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				ApplicationDTO aDTO = new ApplicationDTO();
				aDTO.setAppId		(rs.getInt		("APP_ID")		);
				aDTO.setAppName		(rs.getString	("APP_NAME")	);
				aDTO.setCreateBy	(rs.getString	("CREATE_BY")	);
				aDTO.setCreateDate	(rs.getTimestamp("CREATE_DATE")	);
				aDTO.setUpdateBy	(rs.getString	("UPDATE_BY")	);
				aDTO.setUdpateDate	(rs.getTimestamp("UPDATE_DATE")	);
				aDTO.setDesc		(rs.getString	("DESCRIPTION")	);
				appList.add(aDTO);
			}
			LOGGER.info("Exits from CommonDAOImpl.getApplicationList()! appList.size"+appList.size());
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in CommonDAOImpl.getApplicationList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return appList;
	}

	@Override
	public ApplicationDTO 			getApplicationDetailsById(int appId) 	throws PSDException {
		LOGGER.info("Enters into CommonDAOImpl.getApplicationDetailsById()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		ApplicationDTO 		aDTO 	= new ApplicationDTO();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_APP_DETAILS_BY_ID);
			pstmt.setInt(1, appId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				aDTO.setAppId		(rs.getInt		("APP_ID")		);
				aDTO.setAppName		(rs.getString	("APP_NAME")	);
				aDTO.setCreateBy	(rs.getString	("CREATE_BY")	);
				aDTO.setCreateDate	(rs.getTimestamp("CREATE_DATE")	);
				aDTO.setUpdateBy	(rs.getString	("UPDATE_BY")	);
				aDTO.setUdpateDate	(rs.getTimestamp("UPDATE_DATE")	);
			}
			LOGGER.info("Exits from CommonDAOImpl.getApplicationDetailsById()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in CommonDAOImpl.getApplicationDetailsById() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return aDTO;
	}*/
	
	/*@Override
	public List<WorkflowTypeDTO>	getWorkflowTypeList() 					throws PSDException {
		LOGGER.info("Enters into CommonDAOImpl.getWorkflowTypeList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<WorkflowTypeDTO> wTypeList = new ArrayList<WorkflowTypeDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_TYPE_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				WorkflowTypeDTO wTypeDTO = new WorkflowTypeDTO();
				wTypeDTO.setWorkflowTypeId	(rs.getInt		("WORKFLOW_TYPE_ID"));
				wTypeDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")	);
				wTypeDTO.setDescription		(rs.getString	("DESCRIPTION")		);
				wTypeDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				wTypeDTO.setAddedDate		(rs.getTimestamp("ADDED_DATE")		);
				wTypeDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				wTypeDTO.setUpdatedDate		(rs.getTimestamp("UPDATED_DATE")	);
				wTypeList.add(wTypeDTO);
			}
			LOGGER.info("Exits from CommonDAOImpl.getApplicationList()! wTypeList.size"+wTypeList.size());
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in CommonDAOImpl.getApplicationList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return wTypeList;
	}*/
}
