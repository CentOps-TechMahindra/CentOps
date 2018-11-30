package com.techm.psd.workflowType.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;

public class WorkflowTypeDAOImpl extends BaseDAO implements WorkflowTypeDAO{
	Logger LOGGER = Logger.getLogger(WorkflowTypeDAOImpl.class);

	@Override
	public boolean 							saveWorkflowType(WorkflowTypeDTO wTypeDTO) 							throws PSDException {
		LOGGER.info("Enter into WorkflowTypeDAOImpl.saveWorkflowType()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			String generatedColumns[] = { "WORKFLOW_TYPE_ID", "ADDED_DATE" };
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_TYPE, generatedColumns);
			
			pstmt.setString	(1, wTypeDTO.getWorkflowType()	);
			pstmt.setString	(2, wTypeDTO.getAddedBy()		);
			pstmt.setString	(3, wTypeDTO.getUpdatedBy()		);
			pstmt.setString	(4, wTypeDTO.getDescription()	);
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				wTypeDTO.setWorkflowTypeId	(rs.getInt(1)		);
				wTypeDTO.setAddedDate		(rs.getTimestamp(2)	);
			}
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in WorkflowTypeDAOImpl.saveWorkflowType()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowTypeDAOImpl.saveWorkflowType()!");
		return status;
	}
	
	@Override
	public boolean updateWorkflowType(WorkflowTypeDTO wDTO) throws PSDException{
		LOGGER.info("Enter into WorkflowTypeDAOImpl.updateWorkflowType()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_WORKFLOW_TYPE);
			
			pstmt.setString	(1, wDTO.getWorkflowType()	);
			pstmt.setString (2, wDTO.getUpdatedBy()		);
			pstmt.setString (3, wDTO.getDescription()	);
			pstmt.setInt	(4, wDTO.getWorkflowTypeId());
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in WorkflowTypeDAOImpl.updateWorkflowType()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowTypeDAOImpl.updateWorkflowType()!");
		return status;
	}
	
	@Override
	public List<WorkflowTypeDTO> 	getWorkflowTypeList() 					throws PSDException {
		LOGGER.info("Enters into WorkflowTypeDAOImpl.getWorkflowTypeList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<WorkflowTypeDTO> wTypeList= new ArrayList<WorkflowTypeDTO>();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_TYPE_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				WorkflowTypeDTO wDTO = new WorkflowTypeDTO();
				wDTO.setWorkflowTypeId	(rs.getInt		("WORKFLOW_TYPE_ID"));
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")	);
				wDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				wDTO.setAddedDate		(rs.getTimestamp("ADDED_DATE")		);
				wDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				wDTO.setUpdatedDate		(rs.getTimestamp("UPDATED_DATE")	);
				wDTO.setDescription		(rs.getString	("DESCRIPTION")		);
				wTypeList.add(wDTO);
			}
			LOGGER.info("Exits from WorkflowTypeDAOImpl.getWorkflowTypeList()! wTypeList.size"+wTypeList.size());
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowTypeDAOImpl.getWorkflowTypeList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return wTypeList;
	}

	@Override
	public WorkflowTypeDTO 			getWorkflowTypeDetailsById(int workflowTypeId) 	throws PSDException {
		LOGGER.info("Enters into WorkflowTypeDAOImpl.getWorkflowTypeDetailsById()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		WorkflowTypeDTO 		wDTO 	= new WorkflowTypeDTO();
		
		try{
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_TYPE_DETAILS_BY_ID);
			pstmt.setInt(1, workflowTypeId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wDTO.setWorkflowTypeId	(rs.getInt		("WORKFLOW_TYPE_ID"));
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")	);
				wDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				wDTO.setAddedDate		(rs.getTimestamp("ADDED_DATE")		);
				wDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				wDTO.setUpdatedDate		(rs.getTimestamp("UPDATED_DATE")	);
				wDTO.setDescription		(rs.getString	("DESCRIPTION")		);
			}
			LOGGER.info("Exits from WorkflowTypeDAOImpl.getWorkflowTypeDetailsById()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowTypeDAOImpl.getWorkflowTypeDetailsById() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return wDTO;
	}
	
	@Override
	public boolean 					isValidateWorkflowTypeName(String workflowTypeName, int workflowTypeId) 		throws PSDException{
		LOGGER.info("Enters into WorkflowTypeDAOImpl.isValidateWorkflowTypeName()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		boolean 			result	= true;
		String				query	= SQLConstants.VALIDATE_WORKFLOW_TYPE_NAME;
		
		try{
			conn 		= getConnection();
			if(workflowTypeId > 0){
				query+= " AND  WORKFLOW_TYPE_ID != "+workflowTypeId;
			}
			
			pstmt 		= conn.prepareStatement(query);
			pstmt.setString(1, workflowTypeName);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				result = false;
			}
			LOGGER.info("Exits from WorkflowTypeDAOImpl.isValidateWorkflowTypeName()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowTypeDAOImpl.isValidateWorkflowTypeName() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return result;
	}
}
