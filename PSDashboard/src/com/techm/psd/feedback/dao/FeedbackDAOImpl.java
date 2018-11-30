package com.techm.psd.feedback.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.exception.PSDException;
import com.techm.psd.feedback.dto.FeedbackDTO;

public class FeedbackDAOImpl extends BaseDAO implements FeedbackDAO{

	Logger 				LOGGER 		= Logger.getLogger(FeedbackDAOImpl.class);
	SimpleDateFormat 	dateFormat 	= new SimpleDateFormat("MM/dd/yyyy");
	
	@Override
	public int saveFeedback(String feedback, int workflowId, int startFlowId, String userId) 						throws PSDException {
		LOGGER.info("Enter into FeedbackDAOImpl.saveFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		//boolean 			result 	= true;
		ResultSet 			rs 		= null;
		String				status	= "New";
		int 			feedbackId 	= 0;
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			//"INSERT INTO "+ _TB_FEEDBACK+" (FEEDBACK, WORKGLOW_ID, START_FLOW_ID, ADDED_BY, ADDED_DATE, STATUS) values (?, ?, ?, ?, SYSDATE, ?)"
			
			String generatedColumns[] = { "FEEDBACK_ID"};
			pstmt = conn.prepareStatement(SQLConstants.SAVE_FEEDBACK, generatedColumns);
			pstmt.setString	(1, feedback);
			pstmt.setInt	(2, workflowId);
			pstmt.setInt	(3, startFlowId);
			pstmt.setString	(4, userId);
			pstmt.setString	(5, status);
			
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()){
				feedbackId = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			//result = false;
			LOGGER.error("SQLException in FeedbackDAOImpl.saveFeedback()>>"+e.getMessage());
			e.printStackTrace();
		} finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from FeedbackDAOImpl.saveFeedback()!");
		return feedbackId;
	}
	
	@Override
	public int updateFeedback(int feedbackId, String status, String comment, String userId) 						throws PSDException {
		LOGGER.info("Enter into FeedbackDAOImpl.updateFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		int 				result 	= 0;
		ResultSet 			rs 		= null;
		
		try {
			conn = getConnection();
			conn.setReadOnly(false);
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(SQLConstants.UPDATE_FEEDBACK);
			pstmt.setString	(1, status);
			pstmt.setString	(2, userId);
			pstmt.setString	(3, comment);
			pstmt.setInt	(4, feedbackId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			LOGGER.error("SQLException in FeedbackDAOImpl.updateFeedback()>>"+e.getMessage());
			e.printStackTrace();
		} finally{
			close(pstmt, conn);
		}
		LOGGER.info("Exit from FeedbackDAOImpl.updateFeedback()!");
		return result;
	}
	
	@Override
	public FeedbackDTO 			getFeedbackDetailsById(int feedbackId) 												throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getFeedbackDetailsById()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		
		FeedbackDTO fDTO = new FeedbackDTO();
		fDTO.setFeedbackId(feedbackId);
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_FEEDBACK_DETAILS_BY_ID);
			pstmt.setInt(1, feedbackId);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				fDTO.setFeedbackId		(rs.getInt		("FEEDBACK_ID")		);
				fDTO.setFeedback		(rs.getString	("FEEDBACK")		);
				fDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")		);
				fDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")	);
				fDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")	);
				fDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				fDTO.setAddedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("ADDED_DATE")));
				fDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				fDTO.setUpdatedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("UPDATED_DATE")));
				fDTO.setStatus			(rs.getString	("STATUS")			);
				fDTO.setComment			(rs.getString	("ADMIN_COMMENT")	);
			}
			
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getFeedbackDetailsById() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getFeedbackDetailsById()!");
		return fDTO;
	}
	
	/*@Override
	public List<FeedbackDTO> 	getAllNewFeedback(int upperLimit, int offset) 			throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllNewFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		List<FeedbackDTO> fDTOList		= new ArrayList<FeedbackDTO>();
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_NEW_FEEDBACK);
			//pstmt.setInt(1, workflowId);
			pstmt.setInt(1, upperLimit);
			pstmt.setInt(2, offset);
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				FeedbackDTO fDTO = new FeedbackDTO();
				fDTO.setFeedbackId		(rs.getInt		("FEEDBACK_ID")		);
				fDTO.setFeedback		(rs.getString	("FEEDBACK")		);
				fDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")		);
				fDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")	);
				fDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")	);
				fDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				fDTO.setAddedDate		(rs.getTimestamp("ADDED_DATE")		);
				fDTO.setApprovedBy		(rs.getString	("UPDATED_BY")		);
				fDTO.setApprovedDate	(rs.getTimestamp("UPDATED_DATE")	);
				fDTO.setStatus			(rs.getString	("STATUS")			);
				fDTOList.add(fDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllNewFeedback() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllNewFeedback()! fDTOList.size() = "+fDTOList.size());
		return fDTOList;
	}*/
	
	@Override
	public int 					getAllNewInprogressFeedbackCount() 													throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllNewInprogressFeedbackCount()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		int					count	= 0;
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_NEW_INPROGRESS_FEEDBACK_COUNT);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				count 	= rs.getInt("COUNT");
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllNewInprogressFeedbackCount() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllNewInprogressFeedbackCount()! count = "+count);
		return count;
	}
	
	@Override
	public List<FeedbackDTO> 	getAllNewInprogressFeedback(int upperLimit, int offset, String sidx, String sord) 	throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllNewInprogressFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		List<FeedbackDTO> 	fDTOList= new ArrayList<FeedbackDTO>();
		String 				query	= "SELECT * FROM ( "+
										    "SELECT rownum offset, a.* "+ 
										    "FROM( "+
										       "SELECT f.*, w.WORKFLOW_NAME FROM PS_FEEDBACK f "+ 
										            "LEFT JOIN PS_WORKFLOW w on f.WORKFLOW_ID = w.WORKFLOW_ID "+ 
										            "WHERE UPPER(f.STATUS) <> UPPER('CLOSED') ORDER BY "+sidx+" "+sord+
										    " ) a  "+
										    "WHERE rownum <= "+upperLimit+
										" ) "+
										"WHERE offset > "+offset;
		
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(query);
			//pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_NEW_FEEDBACK);
			//pstmt.setInt(1, workflowId);
			/*pstmt.setString(1, sidx);
			pstmt.setString(2, sord);
			pstmt.setInt(3, upperLimit);
			pstmt.setInt(4, offset);*/
			rs 			= pstmt.executeQuery();
			while (rs.next()){
				FeedbackDTO fDTO = new FeedbackDTO();
				fDTO.setFeedbackId		(rs.getInt		("FEEDBACK_ID")		);
				fDTO.setFeedback		(rs.getString	("FEEDBACK")		);
				fDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")		);
				fDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")	);
				fDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")	);
				fDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				fDTO.setAddedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("ADDED_DATE")));
				fDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				fDTO.setUpdatedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("UPDATED_DATE")));
				fDTO.setStatus			(rs.getString	("STATUS")			);
				fDTO.setComment			(rs.getString	("ADMIN_COMMENT")	);
				fDTOList.add(fDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllNewInprogressFeedback() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllNewInprogressFeedback()! fDTOList.size() = "+fDTOList.size());
		return fDTOList;
	}
	
	@Override
	public int 					getAllClosedFeedbackCount() 						throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllClosedFeedbackCount()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		int					count	= 0;
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_CLOSED_FEEDBACK_COUNT);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				count 	= rs.getInt("COUNT");
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllClosedFeedbackCount() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllClosedFeedbackCount()! count = "+count);
		return count;
	}
	
	@Override
	public List<FeedbackDTO> 	getAllClosedFeedback(int upperLimit, int offset, String sidx, String sord) 			throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllClosedFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		List<FeedbackDTO> fDTOList		= new ArrayList<FeedbackDTO>();
		String 				query	= "SELECT * FROM ( "+
									    "SELECT rownum offset, a.* "+ 
									    "FROM( "+
									       "SELECT f.*, w.WORKFLOW_NAME FROM PS_FEEDBACK f "+ 
									            "LEFT JOIN PS_WORKFLOW w on f.WORKFLOW_ID = w.WORKFLOW_ID "+ 
									            "WHERE UPPER(f.STATUS) in ('CLOSED') ORDER BY "+sidx+" "+sord+
									    " ) a  "+
									    "WHERE rownum <= "+upperLimit+
									" ) "+
									"WHERE offset > "+offset;

		try {
		
		conn 		= getConnection();
		pstmt 		= conn.prepareStatement(query);
		rs 			= pstmt.executeQuery();
			while (rs.next()){
				FeedbackDTO fDTO = new FeedbackDTO();
				fDTO.setFeedbackId		(rs.getInt		("FEEDBACK_ID")		);
				fDTO.setFeedback		(rs.getString	("FEEDBACK")		);
				fDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")		);
				fDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")	);
				fDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")	);
				fDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				fDTO.setAddedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("ADDED_DATE")));
				fDTO.setUpdatedBy		(rs.getString	("UPDATED_BY")		);
				fDTO.setUpdatedDate		(CommonUtil.convertToText(dateFormat, rs.getTimestamp("UPDATED_DATE")));
				fDTO.setStatus			(rs.getString	("STATUS")			);
				fDTO.setComment			(rs.getString	("ADMIN_COMMENT")	);
				fDTOList.add(fDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllClosedFeedback() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllClosedFeedback()! fDTOList.size() = "+fDTOList.size());
		return fDTOList;
	}
	
	/*@Override
	public List<FeedbackDTO> 	getAllRejectedFeedback() 			throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.getAllRejectedFeedback()!");
		Connection 			conn 	= null;
		PreparedStatement 	pstmt	= null;
		ResultSet 			rs 		= null;
		List<FeedbackDTO> fDTOList	= new ArrayList<FeedbackDTO>();
		try {
			conn 		= getConnection();
			pstmt 		= conn.prepareStatement(SQLConstants.GET_ALL_REJECTED_FEEDBACK);
			rs 			= pstmt.executeQuery();
			
			while (rs.next()){
				FeedbackDTO fDTO = new FeedbackDTO();
				fDTO.setFeedbackId		(rs.getInt		("FEEDBACK_ID")		);
				fDTO.setFeedback		(rs.getString	("FEEDBACK")		);
				fDTO.setWorkflowId		(rs.getInt		("WORKFLOW_ID")		);
				fDTO.setWorkflowName	(rs.getString	("WORKFLOW_NAME")	);
				fDTO.setStartFlowId		(rs.getInt		("START_FLOW_ID")	);
				fDTO.setAddedBy			(rs.getString	("ADDED_BY")		);
				fDTO.setAddedDate		(rs.getTimestamp("ADDED_DATE")		);
				fDTO.setApprovedBy		(rs.getString	("UPDATED_BY")		);
				fDTO.setApprovedDate	(rs.getTimestamp("UPDATED_DATE")	);
				fDTO.setStatus			(rs.getString	("STATUS")			);
				fDTOList.add(fDTO);
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAllRejectedFeedback() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAllRejectedFeedback()! fDTOList.size() = "+fDTOList.size());
		return fDTOList;
	}*/
	
	/*@Override
	public boolean 				approveRejectFeedbackList(int[] selFeedbacks, String approvedBy, String status) 	throws PSDException {
		LOGGER.info("Enters into FeedbackDAOImpl.approveRejectFeedbackList()!");
		Connection 			conn 	= null;
		CallableStatement 	ocs 	= null;
		
		int 				result 	= -1;
		boolean 			response= false;
		
		try{
			conn 		= getConnection();
			ocs			= conn.prepareCall(
							"{call SP_APPROVE_REJECT_FEEDBACK(?,?,?,?,?)}");
			
			ArrayDescriptor arrDesc = ArrayDescriptor.createDescriptor("TYP_FEEDBACK_ID", conn);
			Array array 			= new ARRAY(arrDesc, conn, selFeedbacks);
			
			ocs.setArray			(1, array			);
			ocs.setString			(2, approvedBy		);
			ocs.setString			(3, status			);
			ocs.registerOutParameter(4, Types.INTEGER	);
			ocs.registerOutParameter(5, Types.VARCHAR	);
			
			ocs.execute();
			result = ocs.getInt(8);
			if(result == 0){response = true;}else{response = false;}
		}
		catch(SQLException sqlEx){
			response	= false;
			LOGGER.error("Exception in FeedbackDAOImpl.approveRejectFeedbackList() while getting user from database. : " + sqlEx);
		}finally{
			close(ocs, conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.approveRejectFeedbackList()! response::::::: "+response);
		return response;
	}*/

	@Override
	public List<String>		getAdminsEmailIdsByApplication(int appId)												throws PSDException{
		LOGGER.info("Enters into FeedbackDAOImpl.getAdminsEmailIdsByApplication()! appId = "+appId);
		Connection 			conn 			= null;
		PreparedStatement 	pstmt			= null;
		ResultSet 			rs 				= null;
		List<String> 		adminEmailIds	= new ArrayList<String>();
		try {
		
		conn 		= getConnection();
		pstmt 		= conn.prepareStatement(SQLConstants.GET_ADMIN_EMAIL_IDS);
		pstmt.setInt(1, appId);
		rs 			= pstmt.executeQuery();
			while (rs.next()){
				adminEmailIds.add(rs.getString("USER_EMAIL_ID"));
			}
		}
		catch(SQLException sqlEx){
			LOGGER.error("Exception in FeedbackDAOImpl.getAdminsEmailIdsByApplication() while getting user from database. : " + sqlEx);
		}finally{
			close(rs,pstmt,conn);
		}
		LOGGER.info("Exits from FeedbackDAOImpl.getAdminsEmailIdsByApplication()! adminEmailIds.size() = "+adminEmailIds.size());
		return adminEmailIds;
	}
}