package com.techm.psd.workflow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.common.dto.ProfileDTO;
import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.constants.AppConstants;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;

public class WorkflowDAOImpl extends BaseDAO implements WorkflowDAO{

	Logger LOGGER = Logger.getLogger(WorkflowDAOImpl.class);

	CommonDAO cDAO = new CommonDAOImpl();
	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public StartFlowHistoryDTO 				isWorkflowInUse(int workflowId) 									throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.isWorkflowInUse()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		StartFlowHistoryDTO sDTO 	= new StartFlowHistoryDTO();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_START_FLOW_DETAILS);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				sDTO.setStartFlowId	(rs.getInt		("START_FLOW_ID")	);
				sDTO.setWorkflowId 	(rs.getInt		("WORKFLOW_ID")		);
				sDTO.setStartTime	(rs.getTimestamp("START_TIME")		);
				sDTO.setStartBy		(rs.getString	("START_BY")		);
				sDTO.setJenkinsStatus(rs.getString	("JENKINS_STATUS")	);
				sDTO.setReportStatus(rs.getString	("REPORT_STATUS	")	);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.isWorkflowInUse() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.isWorkflowInUse()!");
		return sDTO;
	}
	
	@Override
	public int 								saveStartFlowEntry(int workflowId, String username) 				throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveStartFlowEntry()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		int 		startFlowId 	= 0;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			String generatedColumns[] = { "START_FLOW_ID"};
			pstmt = conn.prepareStatement(SQLConstants.SAVE_START_FLOW_HISTORY, generatedColumns);
			
			pstmt.setInt	(1, workflowId	);
			pstmt.setString	(2, username	);
			pstmt.setString	(3, "INPROGRESS");
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				startFlowId = rs.getInt(1);
			}
		} 
		catch (SQLException e) {
			LOGGER.error("SQLException in WorkflowDAOImpl.saveStartFlowEntry()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveStartFlowEntry()!");
		return startFlowId;
	}
	
	@Override
	public boolean 							saveWorkflow(WorkflowDTO wDTO) 										throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveWorkflow()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			String generatedColumns[] = { "WORKFLOW_ID", "CREATED_DATE" };
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW, generatedColumns);
			
			pstmt.setString	(1, wDTO.getWorkflowName()		);
			pstmt.setInt	(2, wDTO.getAppId()				);
			pstmt.setString	(3, wDTO.getUsername()			);
			pstmt.setString	(4, wDTO.getPassword()			);
			pstmt.setString (5, wDTO.getAuthToken()			);
			pstmt.setString	(6, wDTO.getServer()			);
			pstmt.setInt	(7, wDTO.getPort()				);
			pstmt.setString	(8, wDTO.getJobName()			);
			pstmt.setString	(9, wDTO.getBuildType()			);
			pstmt.setString	(10, wDTO.getBuildParameter()	);
			pstmt.setString	(11, wDTO.getDesc()				);
			pstmt.setString	(12, wDTO.getCreatedBy()		);
			pstmt.setInt	(13, wDTO.getWorkflowTypeId()	);
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				wDTO.setWorkflowId(rs.getInt(1));
				wDTO.setCreateDate(rs.getTimestamp(2));
			}
			
			//SAVE_WORKFLOW_PROFILE_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_PROFILE_MAP);
			for (int i : wDTO.getSelProfileIds()) {
				pstmt.setInt(1, wDTO.getWorkflowId());
				pstmt.setInt(2, i);

				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
			//SAVE_WORKFLOW_PARAM_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_PARAM_MAP);
			for (String i : wDTO.getParametersName()) {
				if(i.equals("") || i.equals(null)){}else{
					pstmt.setInt(1, wDTO.getWorkflowId());
					pstmt.setString(2, i);
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			
			//SAVE_WORKFLOW_FIXIT_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_FIXIT_MAP);
			
			Iterator itr = wDTO.getFixItDTOList().iterator();
			while (itr.hasNext()){
				FixItDTO fDTO = (FixItDTO)itr.next();
				if(fDTO != null){
					pstmt.setInt	(1, wDTO.getWorkflowId());
					pstmt.setString	(2, fDTO.getErrorName());
					pstmt.setInt	(3, fDTO.getErrorCode());
					pstmt.setString	(4, fDTO.getFixitWorkflowName());
					pstmt.setInt	(5, fDTO.getFixitWorkflowId());
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			
			//SAVE_REPORT_DB_DETAILS
			pstmt = conn.prepareStatement(SQLConstants.SAVE_REPORT_DB_DETAILS);
			pstmt.setInt	(1, wDTO.getWorkflowId()	);
			pstmt.setString	(2, wDTO.getDb_username()	);
			pstmt.setString	(3, wDTO.getDb_password()	);
			pstmt.setString (4, wDTO.getDb_hostname()	);
			pstmt.setInt	(5, wDTO.getDb_port()		);
			pstmt.setString	(6, wDTO.getDb_sid()		);
			pstmt.setString	(7, wDTO.getDb_type()		);
			pstmt.setString	(8, wDTO.getDb_table_name()	);
			pstmt.setString	(9, wDTO.getDb_query()		);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			status = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LOGGER.error("SQLException in WorkflowDAOImpl.saveWorkflow()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveWorkflow()!");
		return status;
	}
	
	public boolean 							isValidateWorkflowName(String workflowName, int workflowId) 		throws PSDException{
		LOGGER.info("Enters into WorkflowDAOImpl.isValidateWorkflowName()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		boolean 			result	= true;
		String				query	= SQLConstants.VALIDATE_WORKFLOW_NAME;
			
		try{
			conn 		= getConnection();
			if(workflowId > 0){
				query+= " AND  WORKFLOW_ID != "+workflowId;
			}
			
			pstmt 		= conn.prepareStatement(query);
			pstmt.setString(1, workflowName);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				result = false;
			}
			LOGGER.info("Exits from WorkflowDAOImpl.isValidateWorkflowName()!");
		}catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.isValidateWorkflowName() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		return result;
	}
	
	/*@Override
	public boolean 							saveWorkflowType(WorkflowTypeDTO wTypeDTO) 							throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveWorkflowType()!");
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
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				wTypeDTO.setWorkflowTypeId	(rs.getInt(1)		);
				wTypeDTO.setAddedDate		(rs.getTimestamp(2)	);
			}
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.fatal("SQLException in WorkflowDAOImpl.saveWorkflowType()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveWorkflowType()!");
		return status;
	}
	
	@Override
	public boolean 							saveApplication(ApplicationDTO aDTO) 								throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveApplication()!");
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
			
			pstmt.executeUpdate();
			
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				aDTO.setAppId		(rs.getInt(1)		);
				aDTO.setCreateDate	(rs.getTimestamp(2)	);
			}
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.fatal("SQLException in WorkflowDAOImpl.saveApplication()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveApplication()!");
		return status;
	}*/
	
	@Override
	public boolean 							updateWorkflow(WorkflowDTO wDTO) 									throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.updateWorkflow()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_WORKFLOW);
			
			pstmt.setString	(1, wDTO.getWorkflowName()		);
			pstmt.setInt   	(2, wDTO.getWorkflowTypeId()	);
			pstmt.setInt	(3, wDTO.getAppId()				);
			pstmt.setString	(4, wDTO.getUsername()			);
			pstmt.setString	(5, wDTO.getPassword()			);
			pstmt.setString (6, wDTO.getAuthToken()			);
			pstmt.setString	(7, wDTO.getServer()			);
			pstmt.setInt	(8, wDTO.getPort()				);
			pstmt.setString	(9, wDTO.getJobName()			);
			pstmt.setString	(10, wDTO.getBuildType()		);
			pstmt.setString	(11, wDTO.getBuildParameter()	);
			pstmt.setString	(12, wDTO.getDesc()				);
			pstmt.setString	(13, wDTO.getLastUpdatedBy()	);
			pstmt.setInt   	(14, wDTO.getWorkflowId()		);
			
			pstmt.executeUpdate();
			
			//DEL_WORKFLOW_PROFILE_MAP
			pstmt = conn.prepareStatement(SQLConstants.DEL_WORKFLOW_PROFILE_MAP);
			pstmt.setInt	(1, wDTO.getWorkflowId()		);
			pstmt.executeUpdate();
			
			//SAVE_WORKFLOW_PROFILE_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_PROFILE_MAP);
			for (int i : wDTO.getSelProfileIds()) {
				pstmt.setInt(1, wDTO.getWorkflowId());
				pstmt.setInt(2, i);

				pstmt.addBatch();
			}
			pstmt.executeBatch();	
			
			//DEL_WORKFLOW_PARAM_MAP
			pstmt = conn.prepareStatement(SQLConstants.DEL_WORKFLOW_PARAM_MAP);
			pstmt.setInt	(1, wDTO.getWorkflowId()		);
			pstmt.executeUpdate();
			
			//SAVE_WORKFLOW_PARAM_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_PARAM_MAP);
			if(wDTO.getParametersName() != null){
				for (String i : wDTO.getParametersName()) {
					if(i.equals("") || i.equals(null)){}else{
						pstmt.setInt(1, wDTO.getWorkflowId());
						pstmt.setString(2, i);
						pstmt.addBatch();
					}
				}
				pstmt.executeBatch();
			}
			
			//DEL_WORKFLOW_FIXIT_MAP
			pstmt = conn.prepareStatement(SQLConstants.DEL_WORKFLOW_FIXIT_MAP);
			pstmt.setInt	(1, wDTO.getWorkflowId()		);
			pstmt.executeUpdate();
			
			//SAVE_WORKFLOW_FIXIT_MAP
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_FIXIT_MAP);
			Iterator itr = wDTO.getFixItDTOList().iterator();
			while (itr.hasNext()){
				FixItDTO fDTO = (FixItDTO)itr.next();
				if(fDTO != null){
					pstmt.setInt	(1, wDTO.getWorkflowId());
					pstmt.setString	(2, fDTO.getErrorName());
					pstmt.setInt	(3, fDTO.getErrorCode());
					pstmt.setString	(4, fDTO.getFixitWorkflowName());
					pstmt.setInt	(5, fDTO.getFixitWorkflowId());
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			
			//UPDATE DB_REPORT_DETAILS
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_REPORT_DB_DETAILS);
			pstmt.setString	(1, wDTO.getDb_username()	);
			pstmt.setString	(2, wDTO.getDb_password()	);
			pstmt.setString	(3, wDTO.getDb_hostname()	);
			pstmt.setInt	(4, wDTO.getDb_port()		);
			pstmt.setString (5, wDTO.getDb_sid()		);
			pstmt.setString	(6, wDTO.getDb_type()		);
			pstmt.setString	(7, wDTO.getDb_table_name()	);
			pstmt.setString	(8, wDTO.getDb_query()		);
			pstmt.setInt   	(9, wDTO.getWorkflowId()	);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in WorkflowDAOImpl.updateWorkflow()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.updateWorkflow()!");
		return status;
	}

	@Override
	public WorkflowDTO 						getWorkflowDetailsByID(int workflowId) 								throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowDetailsByID()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		WorkflowDTO wDTO = new WorkflowDTO();
		wDTO.setWorkflowId(workflowId);
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_BY_ID);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTO.setWorkflowTypeId	(rs.getInt		("WORKFLOW_TYPE_ID")	);
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")		);
				wDTO.setAppId			(rs.getInt		("APP_ID")				);
				wDTO.setAppName			(rs.getString	("APP_NAME")			);
				wDTO.setServer			(rs.getString	("SERVER")				);
				wDTO.setPort			(rs.getInt		("PORT")				);
				wDTO.setUsername		(rs.getString	("USERNAME")			);
				wDTO.setPassword		(rs.getString	("PASSWORD")			);
				wDTO.setAuthToken		(rs.getString	("AUTH_TOKEN")			);
				wDTO.setJobName			(rs.getString	("JOB_NAME")			);
				wDTO.setBuildType		(rs.getString	("BUILD_TYPE")			);
				wDTO.setBuildParameter	(rs.getString	("BUILD_PARAMETER")		);
				wDTO.setDesc			(rs.getString	("DESCRIPTION")			);
				wDTO.setCreatedBy		(rs.getString	("CREATED_BY")			);
				wDTO.setCreateDate		(rs.getTimestamp("CREATED_DATE")		);
				wDTO.setLastUpdatedBy	(rs.getString	("LAST_UPDATED_BY")		);
				wDTO.setLastUpdatedDate	(rs.getTimestamp("LAST_UPDATED_DATE")	);
				wDTO.setDb_username		(rs.getString	("db_USERNAME")			);
				wDTO.setDb_password		(rs.getString	("db_PASSWORD")			);
				wDTO.setDb_hostname		(rs.getString	("db_HOSTNAME")			);
				wDTO.setDb_port			(rs.getInt		("db_PORT")				);
				wDTO.setDb_sid			(rs.getString	("db_SID")				);
				wDTO.setDb_type			(rs.getString	("db_DATABASE_TYPE")	);
				wDTO.setDb_table_name	(rs.getString	("db_REPORT_TABLE")		);
				wDTO.setDb_query		(rs.getString	("db_QUERY")			);
				
			}
			
			//ADD Selected Workflow Proflie List
			List<ProfileDTO> pList =	getWorkflowProfileList(workflowId);
			wDTO.setProfileList(pList);
			
			//ADD Selected Workflow Profile IDs
			int[] selProfileIds = getSelWorkflowProfileIDs(workflowId);
			wDTO.setSelProfileIds(selProfileIds);
			
			//ADD Selected Workflow Parameter List
			String[] selParamList = getWorkflowParamList(workflowId);
			wDTO.setParametersName(selParamList);
			
			//ADD FixItDTO List
			List<FixItDTO> fList =	getFixItDTOList(workflowId);
			wDTO.setFixItDTOList(fList);
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowDetailsByID() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowDetailsByID()!");
		return wDTO;
	}

	@Override
	public WorkflowDTO 						getWorkflowDetailsByName(String workflowName) 						throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowDetailsByName()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		WorkflowDTO wDTO = new WorkflowDTO();
		wDTO.setWorkflowName(workflowName);
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_BY_NAME);
			pstmt.setString(1, workflowName);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTO.setWorkflowTypeId	(rs.getInt		("WORKFLOW_TYPE_ID")	);
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")		);
				wDTO.setAppId			(rs.getInt		("APP_ID")				);
				wDTO.setAppName			(rs.getString	("APP_NAME")			);
				wDTO.setServer			(rs.getString	("SERVER")				);
				wDTO.setPort			(rs.getInt		("PORT")				);
				wDTO.setUsername		(rs.getString	("USERNAME")			);
				wDTO.setPassword		(rs.getString	("PASSWORD")			);
				wDTO.setAuthToken		(rs.getString	("AUTH_TOKEN")			);
				wDTO.setJobName			(rs.getString	("JOB_NAME")			);
				wDTO.setBuildType		(rs.getString	("BUILD_TYPE")			);
				wDTO.setBuildParameter	(rs.getString	("BUILD_PARAMETER")		);
				wDTO.setDesc			(rs.getString	("DESCRIPTION")			);
				wDTO.setCreatedBy		(rs.getString	("CREATED_BY")			);
				wDTO.setCreateDate		(rs.getTimestamp("CREATED_DATE")		);
				wDTO.setLastUpdatedBy	(rs.getString	("LAST_UPDATED_BY")		);
				wDTO.setLastUpdatedDate	(rs.getTimestamp("LAST_UPDATED_DATE")	);
				wDTO.setDb_username		(rs.getString	("db_USERNAME")			);
				wDTO.setDb_password		(rs.getString	("db_PASSWORD")			);
				wDTO.setDb_hostname		(rs.getString	("db_HOSTNAME")			);
				wDTO.setDb_port			(rs.getInt		("db_PORT")				);
				wDTO.setDb_sid			(rs.getString	("db_SID")				);
				wDTO.setDb_type			(rs.getString	("db_DATABASE_TYPE")	);
				wDTO.setDb_table_name	(rs.getString	("db_REPORT_TABLE")		);
				wDTO.setDb_query		(rs.getString	("db_QUERY")			);
				
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowDetailsByName() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowDetailsByName()!");
		return wDTO;
	}
	
	public int[] 							getSelWorkflowProfileIDs(int workflowId) 							throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getSelWorkflowProfileIDs()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		int[] 		selProfileIds 	= null;
		List<Integer> 		sList 	= new ArrayList<Integer>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_SEL_WORKFLOW_PROFILE_IDS);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				sList.add(rs.getInt("PRF_ID"));
			}
			//Convert List to Array.
			selProfileIds = ArrayUtils.toPrimitive(sList.toArray(new Integer[sList.size()]));
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getSelWorkflowProfileIDs() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getSelWorkflowProfileIDs()!");
		return selProfileIds;
	}
	
	@Override
	public List<ProfileDTO> 				getWorkflowProfileList(int workflowId) 								throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowProfileList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<ProfileDTO> 	pDTOList= new ArrayList<ProfileDTO>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_PROFILE_LIST);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				ProfileDTO pDTO = new ProfileDTO();
				pDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")	);
				pDTO.setProfileId		(rs.getInt		("PRF_ID")		);
				pDTO.setProfileName		(rs.getString	("PRF_NAME")	);
				pDTOList.add(pDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowProfileList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowProfileList()!");
		return pDTOList;
	}
	
	public String[] 						getWorkflowParamList(int workflowId) 								throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowParamList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		String[] 	selParamList 	= null;
		List<String> 		sList 	= new ArrayList<String>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_SEL_WORKFLOW_PARAM_LIST);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				sList.add(rs.getString("PARAMETER_NAME"));
			}
			//Convert List to Array.
			selParamList = sList.toArray(new String[0]);
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowParamList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowParamList()!");
		return selParamList;
	}
	
	@Override
	public List<FixItDTO> 					getFixItDTOList(int workflowId) 									throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getFixItDTOList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<FixItDTO> 		fDTOList= new ArrayList<FixItDTO>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_FIXIT_LIST);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				FixItDTO fDTO = new FixItDTO();
				fDTO.setErrorName			(rs.getString	("ERROR_NAME")			);
				fDTO.setErrorCode			(rs.getInt		("ERROR_CODE")			);
				fDTO.setFixitWorkflowName	(rs.getString	("FIXIT_WORKFLOW_NAME")	);
				fDTO.setFixitWorkflowId		(rs.getInt		("FIXIT_WORKFLOW_ID")	);
				
				fDTOList.add(fDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getFixItDTOList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getFixItDTOList()!");
		return fDTOList;
	}
	
	/*@Override
	public String[] 						getWorkflowNameList() 								throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowNameList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		String[] 	workflowNameList= null;
		List<String> 		sList 	= new ArrayList<String>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_NAME_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				sList.add(rs.getString("WORKFLOW_NAME"));
			}
			//Convert List to Array.
			workflowNameList = sList.toArray(new String[0]);
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowNameList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowList()!");
		return workflowNameList;
	}*/
	
	@Override
	public List<WorkflowDTO> 				getWorkflowList(UserDTO user) 										throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<WorkflowDTO> 	wDTOList= new ArrayList<WorkflowDTO>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_LIST);
			pstmt.setInt(1, user.getProfileId());
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				WorkflowDTO wDTO = new WorkflowDTO();
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")		);
				wDTO.setAppId			(rs.getInt		("APP_ID")				);
				wDTO.setAppName			(rs.getString	("APP_NAME")			);
				wDTO.setServer			(rs.getString	("SERVER")				);
				wDTO.setPort			(rs.getInt		("PORT")				);
				wDTO.setUsername		(rs.getString	("USERNAME")			);
				wDTO.setPassword		(rs.getString	("PASSWORD")			);
				wDTO.setAuthToken		(rs.getString	("AUTH_TOKEN")			);
				wDTO.setJobName			(rs.getString	("JOB_NAME")			);
				wDTO.setBuildType		(rs.getString	("BUILD_TYPE")			);
				wDTO.setBuildParameter	(rs.getString	("BUILD_PARAMETER")		);
				wDTO.setDesc			(rs.getString	("DESCRIPTION")			);
				wDTO.setCreatedBy		(rs.getString	("CREATED_BY")			);
				wDTO.setCreateDate		(rs.getTimestamp("CREATED_DATE")		);
				wDTO.setLastUpdatedBy	(rs.getString	("LAST_UPDATED_BY")		);
				wDTO.setLastUpdatedDate	(rs.getTimestamp("LAST_UPDATED_DATE")	);
				wDTOList.add(wDTO);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowList()! wDTOList.size"+wDTOList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getWorkflowList()!... wDTOList.size() : "+wDTOList.size());
		return wDTOList;
	}
	
	@Override
	public List<WorkflowDTO>				getWorkflowListByApp(int appId)										throws PSDException{
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowListByApp()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<WorkflowDTO> 	wDTOList= new ArrayList<WorkflowDTO>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_LIST_BY_APP_ID);
			pstmt.setInt(1, appId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				WorkflowDTO wDTO = new WorkflowDTO();
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTOList.add(wDTO);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowListByApp()! wDTOList.size"+wDTOList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowListByApp() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getWorkflowListByApp()!... wDTOList.size() : "+wDTOList.size());
		return wDTOList;
	}
	
	@Override
	public List<WorkflowDTO>				getAllWorkflowList(int profileId)									throws PSDException{
		LOGGER.info("Enters into WorkflowDAOImpl.getAllWorkflowList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<WorkflowDTO> 	wDTOList= new ArrayList<WorkflowDTO>();
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_WORKFLOW_LIST);
			pstmt.setInt(1, profileId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				WorkflowDTO wDTO = new WorkflowDTO();
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTO.setAppId			(rs.getInt		("APP_ID")				);
				wDTOList.add(wDTO);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getAllWorkflowList()! wDTOList.size"+wDTOList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getAllWorkflowList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getAllWorkflowList()!... wDTOList.size() : "+wDTOList.size());
		return wDTOList;
	}
	/*@Override
	public Map<String, List<WorkflowDTO>> 	getWorkflowMap(UserDTO user) 										throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowMap()!");
		List<WorkflowDTO> 	wList 		= null;
		WorkflowDTO 		wDTO		= null;
		Map<String, List<WorkflowDTO>> workflowMap = new HashMap<String, List<WorkflowDTO>>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_APP_LIST);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wList = new ArrayList<WorkflowDTO>();
				workflowMap.put(rs.getString("APP_NAME"), wList);
			}
			LOGGER.info("In WorkflowDAOImpl.getWorkflowMap()...  workflowMap.size() : "+workflowMap.size());
			
			//conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_LIST);
			pstmt.setInt(1, user.getProfileId());
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wDTO 	= new WorkflowDTO();
				String	appName			= rs.getString("APP_NAME");
				wDTO.setAppName			(appName);
				wDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				wDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")		);
				wDTO.setWorkflowType	(rs.getString	("WORKFLOW_TYPE")		);
				wDTO.setAppId			(rs.getInt		("APP_ID")				);
				wDTO.setServer			(rs.getString	("SERVER")				);
				wDTO.setPort			(rs.getInt		("PORT")				);
				wDTO.setUsername		(rs.getString	("USERNAME")			);
				wDTO.setPassword		(rs.getString	("PASSWORD")			);
				wDTO.setAuthToken		(rs.getString	("AUTH_TOKEN")			);
				wDTO.setJobName			(rs.getString	("JOB_NAME")			);
				wDTO.setBuildType		(rs.getString	("BUILD_TYPE")			);
				wDTO.setBuildParameter	(rs.getString	("BUILD_PARAMETER")		);
				wDTO.setDesc			(rs.getString	("DESCRIPTION")			);
				wDTO.setCreatedBy		(rs.getString	("CREATED_BY")			);
				wDTO.setCreateDate		(rs.getTimestamp("CREATED_DATE")		);
				wDTO.setLastUpdatedBy	(rs.getString	("LAST_UPDATED_BY")		);
				wDTO.setLastUpdatedDate	(rs.getTimestamp("LAST_UPDATED_DATE")	);
				
				if(workflowMap.containsKey(appName)){
					wList 	= (List<WorkflowDTO>) workflowMap.get(appName);
				}else{
					wList = new ArrayList<WorkflowDTO>();
				}
				wList.add(wDTO);
				workflowMap.put(appName, wList);
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getWorkflowMap()!... workflowMap.size : "+workflowMap.size());
		
		return workflowMap;
	}*/

	/*public Map<Integer, List<BuildDTO>> 	getStartFlowMap(int workflowId) 									throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowMap()!");
		List<BuildDTO> 	wList 		= null;
		WorkflowDTO 				wDTO		= null;
		Map<Integer, List<BuildDTO>> workflowMap = new HashMap<Integer, List<BuildDTO>>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_START_FLOW_HISTORY);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				wList = new ArrayList<BuildDTO>();
				workflowMap.put(rs.getInt("START_FLOW_ID"), wList);
			}
			LOGGER.info("In WorkflowDAOImpl.getWorkflowMap()...  workflowMap.size() : "+workflowMap.size());
			
			//conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_BUILD_DETAILS);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				BuildDTO bDTO = new BuildDTO();
				bDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID"));
				bDTO.setBuildNo			(rs.getInt		("BUILD_NO"));
				bDTO.setId				(rs.getInt		("ID"));
				bDTO.setFullDisplayName	(rs.getString	("FULL_DISPLAY_NAME"));
				bDTO.setDisplayName		(rs.getString	("DISPLAY_NAME"));
				bDTO.setBuiltOn			(rs.getString	("BUILT_ON"));
				bDTO.setUrl				(rs.getString	("URL"));
				bDTO.setQueueId			(rs.getInt		("QUEUE_ID"));
				bDTO.setTimestamp		(rs.getTimestamp("TIMESTAMP"));
				bDTO.setEstimatedDuration(rs.getInt		("ESTIMATED_DURATION"));
				bDTO.setDuration		(rs.getInt		("DURATION"));
				bDTO.setShortDescription(rs.getString	("SHORT_DESCRIPTIOIN"));
				bDTO.setAddr			(rs.getString	("ADDR"));
				bDTO.setUserId			(rs.getString	("USER_ID"));
				bDTO.setUserName		(rs.getString	("USER_NAME"));
				bDTO.setUpstreamBuild	(rs.getInt		("UPSTREAM_BUILD_NO"));
				bDTO.setUpstreamUrl		(rs.getString	("UPSTREAM_BUILD_URL"));
				bDTO.setUpstreamProject	(rs.getString	("UPSTREAM_PROJECT"));
				bDTO.setResult			(rs.getString	("RESULT"));
				int startFlowId			= rs.getInt		("START_FLOW_ID");
				if(startFlowId > 0){
					bDTO.setStartFlowId		(startFlowId);
				
					if(workflowMap.containsKey(startFlowId)){
						wList 	= (List<BuildDTO>) workflowMap.get(startFlowId);
					}else{
						wList = new ArrayList<BuildDTO>();
					}
					wList.add(bDTO);
					workflowMap.put(startFlowId, wList);
				}
			}
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getWorkflowMap()!... workflowMap.size : "+workflowMap.size());
		
		return workflowMap;
	}*/
	
	@Override
	public boolean 							saveWorkflowLog(int workflowId, String startBy, String status)		throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveWorkflowLog()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		
		boolean 			result 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQLConstants.SAVE_WORKFLOW_LOG);
			pstmt.setInt	(1, workflowId	);
			pstmt.setString	(2, startBy		);
			pstmt.setString	(3, status		);
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			result = false;
			LOGGER.error("SQLException in WorkflowDAOImpl.saveWorkflowLog()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveWorkflowLog()!");
		return result;
	}
	
	@Override
	public boolean 							saveBuildsDetails(List<BuildDTO> bDTOList, int workflowId) 			throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveBuildsDetails()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQLConstants.SAVE_BUILD_DETAILS);
			
			for (BuildDTO bDTO : bDTOList) {
				pstmt.setInt	(1, workflowId					);
				pstmt.setInt	(2, bDTO.getBuildNo()			);
				pstmt.setInt	(3, bDTO.getId()				);
				pstmt.setString	(4, bDTO.getFullDisplayName()	);
				pstmt.setString	(5, bDTO.getDisplayName()		);
				pstmt.setString	(6, bDTO.getBuiltOn()			);
				pstmt.setString	(7, bDTO.getUrl()				);
				pstmt.setInt	(8, bDTO.getQueueId()			);
				pstmt.setTimestamp(9, bDTO.getTimestamp()		);
				pstmt.setLong	(10, bDTO.getEstimatedDuration());
				pstmt.setInt	(11, bDTO.getDuration()			);
				pstmt.setString	(12, bDTO.getShortDescription()	);
				pstmt.setString	(13, bDTO.getAddr()				);
				pstmt.setString	(14, bDTO.getUserId()			);
				pstmt.setString	(15, bDTO.getUserName()			);
				pstmt.setInt	(16, bDTO.getUpstreamBuild()	);
				pstmt.setString	(17, bDTO.getUpstreamUrl()		);
				pstmt.setString	(18, bDTO.getUpstreamProject()	);
				pstmt.setString	(19, bDTO.getResult()			);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in WorkflowDAOImpl.saveBuildsDetails()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveBuildsDetails()!");
		return status;
	}

	@Override
	public boolean 							saveBuildDetailsById(BuildDTO bDTO, int workflowId, int startFlowId)throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.saveBuildDetailsById()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		
		boolean 			status 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(SQLConstants.SAVE_BUILD_DETAILS_BY_ID);
			
			pstmt.setInt	(1, workflowId					);
			pstmt.setInt	(2, bDTO.getBuildNo()			);
			pstmt.setInt	(3, bDTO.getId()				);
			pstmt.setString	(4, bDTO.getFullDisplayName()	);
			pstmt.setString	(5, bDTO.getDisplayName()		);
			pstmt.setString	(6, bDTO.getBuiltOn()			);
			pstmt.setString	(7, bDTO.getUrl()				);
			pstmt.setInt	(8, bDTO.getQueueId()			);
			pstmt.setTimestamp(9, bDTO.getTimestamp()		);
			pstmt.setLong	(10, bDTO.getEstimatedDuration());
			pstmt.setInt	(11, bDTO.getDuration()			);
			pstmt.setString	(12, bDTO.getShortDescription()	);
			pstmt.setString	(13, bDTO.getAddr()				);
			pstmt.setString	(14, bDTO.getUserId()			);
			pstmt.setString	(15, bDTO.getUserName()			);
			pstmt.setInt	(16, bDTO.getUpstreamBuild()	);
			pstmt.setString	(17, bDTO.getUpstreamUrl()		);
			pstmt.setString	(18, bDTO.getUpstreamProject()	);
			pstmt.setString	(19, bDTO.getResult()			);
			pstmt.setInt	(20, startFlowId				);
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			status = false;
			LOGGER.error("SQLException in WorkflowDAOImpl.saveBuildDetailsById()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.saveBuildDetailsById()!");
		return status;
	}
	
	/*public List<Integer>					getStartFlowHistory(int workflowId) 								throws PSDException {
		logger.info("Enters into WorkflowDAOImpl.getBuildsDetails()!");
		List<Integer> flowHistroyList = new ArrayList<Integer>();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_START_FLOW_HISTORY);
			pstmt.setFetchSize(500);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				int id = rs.getInt		("START_FLOW_ID");
				logger.info(id);
				flowHistroyList.add(id);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getBuildsDetails()! bDTOList.size"+flowHistroyList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getBuildsDetails() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getBuildsDetails()!... bDTOList.size() : "+flowHistroyList.size());
		return flowHistroyList;
	}*/
	
	public List<StartFlowHistoryDTO>		getStartFlowHistory(int workflowId, String action) 					throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getStartFlowHistory()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		String 				query 	= "";
		List<StartFlowHistoryDTO> flowHistroyList = new ArrayList<StartFlowHistoryDTO>();
		
		try {
			conn 		= getConnection();
			if(action.equalsIgnoreCase("LAST_START_FLOW_HISTORY")){
				query = SQLConstants.GET_LAST_START_FLOW_HISTORY;
			}else{
				query = SQLConstants.GET_START_FLOW_HISTORY;
			}
			
			pstmt 		= conn.prepareStatement(query);
			pstmt.setFetchSize(500);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				StartFlowHistoryDTO sDTO = new StartFlowHistoryDTO();
				sDTO.setStartFlowId	(rs.getInt		("START_FLOW_ID")	);
				sDTO.setWorkflowId 	(rs.getInt		("WORKFLOW_ID")		);
				sDTO.setStartTime	(rs.getTimestamp("START_TIME")		);
				sDTO.setStartBy		(rs.getString	("START_BY")		);
				sDTO.setJenkinsStatus(rs.getString	("JENKINS_STATUS")	);
				sDTO.setReportStatus(rs.getString	("REPORT_STATUS")	);
				flowHistroyList.add(sDTO);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getStartFlowHistory()! bDTOList.size"+flowHistroyList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getStartFlowHistory() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getStartFlowHistory()!... bDTOList.size() : "+flowHistroyList.size());
		return flowHistroyList;
	}
	
	@Override
	public List<BuildDTO>					getBuildsHistory(int workflowId, int startFlowId) 					throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getBuildsHistory()! workflowId:"+workflowId+", startFlowId : "+startFlowId);
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<BuildDTO> 	bDTOList 	= new ArrayList<BuildDTO>();
		
		try {
			conn 		= getConnection();
			if(startFlowId == 0){
				pstmt 		= conn.prepareStatement(SQLConstants.GET_NULL_BUILD_DETAILS);
				pstmt.setFetchSize(500);
				pstmt.setInt(1, workflowId);
			}else{
				pstmt 		= conn.prepareStatement(SQLConstants.GET_BUILD_DETAILS);
				pstmt.setFetchSize(500);
				pstmt.setInt(1, workflowId);
				pstmt.setInt(2, startFlowId);
			}
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				BuildDTO bDTO = new BuildDTO();
				bDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")			);
				bDTO.setBuildNo			(rs.getInt		("BUILD_NO")			);
				bDTO.setId				(rs.getInt		("ID")					);
				bDTO.setFullDisplayName	(rs.getString	("FULL_DISPLAY_NAME")	);
				bDTO.setDisplayName		(rs.getString	("DISPLAY_NAME")		);
				bDTO.setBuiltOn			(rs.getString	("BUILT_ON")			);
				bDTO.setUrl				(rs.getString	("URL")					);
				bDTO.setQueueId			(rs.getInt		("QUEUE_ID")			);
				bDTO.setTimestamp		(rs.getTimestamp("TIMESTAMP")			);
				bDTO.setEstimatedDuration(rs.getInt		("ESTIMATED_DURATION")	);
				bDTO.setDuration		(rs.getInt		("DURATION")			);
				bDTO.setShortDescription(rs.getString	("SHORT_DESCRIPTIOIN")	);
				bDTO.setAddr			(rs.getString	("ADDR")				);
				bDTO.setUserId			(rs.getString	("USER_ID")				);
				bDTO.setUserName		(rs.getString	("USER_NAME")			);
				bDTO.setUpstreamBuild	(rs.getInt		("UPSTREAM_BUILD_NO")	);
				bDTO.setUpstreamUrl		(rs.getString	("UPSTREAM_BUILD_URL")	);
				bDTO.setUpstreamProject	(rs.getString	("UPSTREAM_PROJECT")	);
				bDTO.setResult			(rs.getString	("RESULT")				);
				bDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")		);
				bDTOList.add(bDTO);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getBuildsHistory()! bDTOList.size"+bDTOList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getBuildsHistory() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getBuildsHistory()!... bDTOList.size() : "+bDTOList.size());
		return bDTOList;
	}
	
	@Override
	public List<Integer>					getBuilsNosByStartFlowId(int startFlowId) 							throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getBuilsNosByStartFlowId()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<Integer> buildNoList 	= new ArrayList<Integer>();
		int					buildNo	= 0;
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_BUILD_NOS_FROM_HISTORY);
			pstmt.setInt(1, startFlowId);
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				buildNo		= rs.getInt("BUILD_NO");
				buildNoList.add(buildNo);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getBuilsNosByStartFlowId()!!!");
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getBuilsNosByStartFlowId() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getBuilsNosByStartFlowId()!!!..buildNoList.size()"+buildNoList.size());
		return buildNoList;
	}
	
	/*@Override
	public void 						getReportDataByBuildIds(int workflowId, List<Integer> buildNos) 		throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getReportDataByBuildIds()! ");
		DatabaseBean dbBean 	= null;
		Connection con 			= null;
		DBConnectionServices dbConnectionServices = new DBConnectionServices();
		String tableName		= "";
		JSONArray jArray 		= new JSONArray();
		List<String> columnList = new ArrayList<String>();
		
		try {
			dbBean		= getReportDBDetails(workflowId);
			tableName	= dbBean.getReportTable();
			con 		= dbConnectionServices.getDatabaseConnection(dbBean);
			
			String query = "SELECT * FROM "+tableName+" WHERE BUILD_NUMBER IN (107,108,109)"; 
			
			getReportJSON(con, query, columnList, jArray);
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getReportDataByBuildIds() while getting user from database. : " + sqlEx);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getReportDataByBuildIds()!!!");
		//return jArray;
	}*/
	
	public List<String>						getReportResultHistory(Connection con, String query) 				throws PSDException {
		LOGGER.info(AppConstants.LOGGING_ENTER_METHOD+ " getReportResultHistory");
		Connection 			conn 	= null;
		Statement 			stmt 	= null;
		ResultSet 			rs 		= null;
		
		List<String> resultList		= new ArrayList<String>();
		String 		buildResult 	= "";
		
		try {
			stmt 	= con.createStatement();
			rs		= stmt.executeQuery(query);

		    while(rs.next()) {
		    	buildResult = rs.getString("RESULT");
		        resultList.add(buildResult);
		    }
		}catch (Exception e ) {
	        LOGGER.error("Exception occurred while fetching data " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	    	close(rs,stmt,conn);
	        LOGGER.debug("Connection is closed!!");
	    }
	    LOGGER.info(AppConstants.LOGGING_EXIT_METHOD+ " getReportResultHistory");
	    return resultList;
	}
	
	@Override
	public DatabaseBean 					getReportDBDetails(int workflowId) 									throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getReportDBDetails()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		DatabaseBean 		dbBean 	= new DatabaseBean();
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_REPORT_DB_DETAILS);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				dbBean.setWorkflowId	(rs.getInt		("WORKFLOW_ID")		);
				dbBean.setUsername		(rs.getString	("USERNAME")		);
				dbBean.setPassword		(rs.getString	("PASSWORD")		);
				dbBean.setHostname		(rs.getString	("HOSTNAME")		);
				dbBean.setPort			(rs.getInt		("PORT")			);
				dbBean.setSid			(rs.getString	("SID")				);
				dbBean.setDatabaseType	(rs.getString	("DATABASE_TYPE")	);
				dbBean.setReportTable	(rs.getString	("REPORT_TABLE")	);
				dbBean.setQuery			(rs.getString	("QUERY")			);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getReportDBDetails() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from WorkflowDAOImpl.getReportDBDetails()!");
		return dbBean;
	}
	
	@Override
	public void 							getReportJSON(Connection con, String query, List<String> columns, JSONArray jArray) throws SQLException,JSONException,Exception {
		LOGGER.info(AppConstants.LOGGING_ENTER_METHOD+ " getReportJSON");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		//JSONArray json = new JSONArray();
		Statement 			stmt 	= null;
		
		try {
			stmt 	= con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			stmt.setFetchSize(10000);
			rs 		= stmt.executeQuery(query);

		    ResultSetMetaData rsmd 	= rs.getMetaData();
		    int numColumns 			= rsmd.getColumnCount();
		    rs.beforeFirst();
		      
		    //ArrayList<String> columns = new ArrayList<String>();
		    for(int i=1; i< numColumns+1; i++) {
		    	columns.add(rsmd.getColumnLabel(i));
	        }
		    while(rs.next()) {
		    	JSONObject obj = new JSONObject();
		        for(String temp : columns) {
		        	Object value = rs.getObject(temp);
		            obj.put(temp, value== null?" ":value);
		        }
		        jArray.put(obj);
		    }
		}catch (SQLException se ) {
	        LOGGER.error("Exception occurred while fetching data " + se.getMessage());
	        throw se;
	    }catch (Exception e ) {
	        LOGGER.error("Exception occurred while fetching data " + e.getMessage());
	        e.printStackTrace();
	    } finally {
	    	close(rs,pstmt,conn);
	        LOGGER.debug("Connection is closed!!");
	    }
	    LOGGER.info(AppConstants.LOGGING_EXIT_METHOD+ " getReportJSON");
	    //return json;
    }
	
	@Override
	public List<Integer>					getWorkflowIDList() 												throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getWorkflowIDList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<Integer> 		wList 	= new ArrayList<Integer>();
		int				workflowId	= 0;
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_WORKFLOW_ID_LIST);
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				workflowId		= rs.getInt("WORKFLOW_ID");
				wList.add(workflowId);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getWorkflowIDList()! bDTOList.size"+wList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getWorkflowIDList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getWorkflowIDList()!... bDTOList.size() : "+wList.size());
		return wList;
	}
	
	@Override
	public List<Integer>					getHistoryBuildIDList(int workflowId) 								throws PSDException {
		LOGGER.info("Enters into WorkflowDAOImpl.getHistoryBuildIDList()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		List<Integer> buildNoList 	= new ArrayList<Integer>();
		int buildNo;
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_BUILD_ID_LIST);
			pstmt.setInt(1, workflowId);
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				buildNo		= rs.getInt("BUILD_NO");
				buildNoList.add(buildNo);
			}
			LOGGER.info("Exits from WorkflowDAOImpl.getHistoryBuildIDList()! bDTOList.size"+buildNoList.size());
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in WorkflowDAOImpl.getHistoryBuildIDList() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getHistoryBuildIDList()!... bDTOList.size() : "+buildNoList.size());
		return buildNoList;
	}
	
	@Override
	public boolean 							updateStartFlowHistory(int startFlowId, String jenkinsStatus, String reportStatus) throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.updateStartFlowHistory()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt 	= null;
		
		boolean 			result 	= true;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_START_FLOW_HISTORY);
			
			pstmt.setString	(1, jenkinsStatus);
			pstmt.setString	(2, reportStatus);
			pstmt.setInt	(3, startFlowId);
			
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			result = false;
			LOGGER.error("SQLException in WorkflowDAOImpl.updateStartFlowHistory()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.updateStartFlowHistory()!");
		return result;
	}

	@Override
	public int 								getMaxStartFlowIdByUser(int workflowId, String username)			throws PSDException {
		LOGGER.info("Enter into WorkflowDAOImpl.getMaxStartFlowIdByUser()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		int 		startFlowId 	= 0;
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_MAX_START_FLOW_ID_BY_USER);
			pstmt.setInt	(1, workflowId);
			pstmt.setString	(2, username);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				startFlowId = rs.getInt(1);
			}
		} 
		catch (SQLException e) {
			LOGGER.error("SQLException in WorkflowDAOImpl.getMaxStartFlowIdByUser()>>"+e.getMessage());
			e.printStackTrace();
		}
		finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exit from WorkflowDAOImpl.getMaxStartFlowIdByUser()!");
		return startFlowId;
	}
}
