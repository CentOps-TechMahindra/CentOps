package com.techm.psd.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;

public class ApplicationDAOImpl extends BaseDAO implements ApplicationDAO{
	Logger LOGGER = Logger.getLogger(ApplicationDAOImpl.class);

	@Override
	public boolean saveApplication(ApplicationDTO aDTO) throws PSDException{
		LOGGER.info("Enter into ApplicationDAOImpl.saveApplication()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			String generatedColumns[] = { "APP_ID", "CREATE_DATE" };
			pstmt = conn.prepareStatement(SQLConstants.SAVE_APPLICATION, generatedColumns);
			
			pstmt.setString	(1, aDTO.getAppName()	);
			pstmt.setString	(2, aDTO.getCreateBy()	);
			pstmt.setString	(3, aDTO.getUpdateBy()	);
			pstmt.setString	(4, aDTO.getDesc()		);
			pstmt.setInt	(5, aDTO.getAppsId()	);
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				aDTO.setAppId		(rs.getInt(1)		);
				aDTO.setCreateDate	(rs.getTimestamp(2)	);
			}
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in ApplicationDAOImpl.saveApplication()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from ApplicationDAOImpl.saveApplication()!");
		return status;
	}
	
	@Override
	public boolean updateApplication(ApplicationDTO aDTO) throws PSDException{
		LOGGER.info("Enter into ApplicationDAOImpl.updateApplication()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_APPLICATION);
			
			pstmt.setString	(1, aDTO.getAppName()	);
			pstmt.setString (2, aDTO.getUpdateBy()	);
			pstmt.setString (3, aDTO.getDesc()		);
			pstmt.setInt	(4, aDTO.getAppsId()	);
			pstmt.setInt	(5, aDTO.getAppId()		);
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in ApplicationDAOImpl.updateApplication()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from ApplicationDAOImpl.updateApplication()!");
		return status;
	}
	
	@Override
	public List<ApplicationDTO> 	getApplicationList() 					throws PSDException {
		LOGGER.info("Enters into ApplicationDAOImpl.getApplicationList()!");
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
				aDTO.setUpdateDate	(rs.getTimestamp("UPDATE_DATE")	);
				aDTO.setDesc		(rs.getString	("DESCRIPTION")	);
				aDTO.setAppsId		(rs.getInt		("APPS_ID")		);
				appList.add(aDTO);
			}
			LOGGER.info("Exits from ApplicationDAOImpl.getApplicationList()! appList.size"+appList.size());
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in ApplicationDAOImpl.getApplicationList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return appList;
	}

	@Override
	public ApplicationDTO 			getApplicationDetailsById(int appId) 	throws PSDException {
		LOGGER.info("Enters into ApplicationDAOImpl.getApplicationDetailsById()!");
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
				aDTO.setUpdateDate	(rs.getTimestamp("UPDATE_DATE")	);
				aDTO.setDesc		(rs.getString	("DESCRIPTION")	);
				aDTO.setAppsId		(rs.getInt		("APPS_ID")		);
			}
			LOGGER.info("Exits from ApplicationDAOImpl.getApplicationDetailsById()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in ApplicationDAOImpl.getApplicationDetailsById() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return aDTO;
	}
	
	@Override
	public boolean 					isValidateAppName(String appName, int appId) 		throws PSDException {
		LOGGER.info("Enters into ApplicationDAOImpl.isValidateAppName()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		boolean 			result	= true;
		String				query	= SQLConstants.VALIDATE_APP_NAME;
		
		try{
			conn 		= getConnection();
			if(appId > 0){
				query+= " AND  APP_ID != "+appId;
			}
			
			pstmt 		= conn.prepareStatement(query);
			pstmt.setString(1, appName);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				result = false;
			}
			LOGGER.info("Exits from ApplicationDAOImpl.isValidateAppName()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in ApplicationDAOImpl.isValidateAppName() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return result;
	}
}
