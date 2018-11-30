package com.techm.psd.constants;

public class SQLConstants {

	public static int		DEFAULT_LVL						= 1;
	public static String	DEFAULT_LVL_NAME				= "Read Only";
	public static String	_YES							= "Y";
	public static String	_NO								= "N";
	public static String	_PENDING						= "PENDING";
	//public static String	_APPROVED						= "APPROVED";
	//public static String	_REJECTED						= "REJECTED";
	public static String	_CANCEL							= "CANCEL";
	
	
	/*TABLES: START*/
	public static String	_TB_USERS						= "PS_USERS";
	public static String	_TB_USER_LOGIN_LOG				= "PS_USER_LOGIN_LOG";
	public static String	_TB_USER_ACCESS_REQUEST			= "PS_USER_ACCESS_REQUEST";
	public static String	_TB_USR_MGMT_LEVEL				= "PS_USR_MGMT_LEVEL";
	public static String	_TB_USR_PROFILE					= "PS_USR_PROFILE";
	public static String	_TB_APPLICATION					= "PS_APPLICATION";

	public static String	_TB_WORKFLOW					= "PS_WORKFLOW";
	public static String	_TB_WORKFLOW_LOG				= "PS_WORKFLOW_LOG";
	public static String	_TB_REPORT_DB_DETAILS			= "PS_REPORT_DB_DETAILS";
	public static String	_TB_BUILD_HISTORY				= "PS_BUILD_HISTORY";
	public static String	_TB_WORKFLOW_TYPE				= "PS_WORKFLOW_TYPE";
	public static String	_TB_START_FLOW_HISTORY			= "PS_START_FLOW_HISTORY";
	public static String	_TB_WORKFLOW_PROFILE_MAP		= "PS_WORKFLOW_PROFILE_MAP";
	public static String	_TB_WORKFLOW_PARAM_MAP 			= "PS_WORKFLOW_PARAM_MAP";
	public static String	_TB_WORKFLOW_FIXIT_MAP			= "PS_WORKFLOW_FIXIT_MAP";
	public static String	_TB_FEEDBACK 					= "PS_FEEDBACK";
	public static String	_TB_SERVER_STATE				= "PS_SERVER_STATE";
	/*TABLES: END*/
	
	
	/*SAVE UPDATE USER */
	public static String 	GET_USER_BY_UID 				= "SELECT u.*,l.LVL_NAME, app.APP_NAME, p.PRF_NAME FROM "+ _TB_USERS +" u " +
																"LEFT JOIN "+ _TB_USR_MGMT_LEVEL 	+" l 	on u.LVL_ID = l.LVL_ID " +
																"LEFT JOIN "+ _TB_APPLICATION 		+" app 	on u.APP_ID = app.app_ID " +
																"LEFT JOIN "+ _TB_USR_PROFILE		+" p 	on u.PRF_ID = p.PRF_ID " +
																"WHERE UPPER(u.USER_ID) = UPPER(?)";

	public static String 	SAVE_NEW_USER	 				= "INSERT INTO "+ _TB_USERS +" (USER_ID, USER_FNAME, USER_LNAME, USER_EMAIL_ID, USER_CONTACT_NO, ACCOUNT_CREATED_DATE) values(?, ?, ?, ?, ?, SYSDATE)";
	
	/*SAVE USER LOGIN LOG*/
	public static String	SAVE_USER_LOGIN_LOG				= "INSERT INTO "+ _TB_USER_LOGIN_LOG+" (USER_ID, LOG_IN_DATE) values (?, SYSDATE)";
	
	/*GET ADMINS EMAIL IDs*/
	public static String	GET_ADMIN_EMAIL_IDS				= "SELECT USER_EMAIL_ID FROM "+ _TB_USERS +" WHERE LVL_ID = 10 AND APP_ID = ? ";
	
	/*GET USER LEVEL LIST*/
	public static String 	GET_USER_LEVEL_LIST 			= "SELECT * FROM "+ _TB_USR_MGMT_LEVEL +" ORDER BY LVL_NAME";
	
	/*GET PROFILE LIST*/
	public static String 	GET_USER_PROFILE_LIST 			= "SELECT * FROM "+ _TB_USR_PROFILE +" ORDER BY PRF_NAME";
	
	/*APPLICATION */
	public static String	SAVE_APPLICATION				= "INSERT INTO "+ _TB_APPLICATION+" (APP_NAME, CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, DESCRIPTION, APPS_ID) values (?, ?, SYSDATE, ?, SYSDATE, ?, ?)";
	public static String	UPDATE_APPLICATION				= "UPDATE "+ _TB_APPLICATION+" SET APP_NAME = ?, UPDATE_BY = ?, UPDATE_DATE = SYSDATE, DESCRIPTION = ?, APPS_ID = ? WHERE APP_ID = ?";
	public static String 	GET_APP_LIST 					= "SELECT * FROM "+ _TB_APPLICATION +" ORDER BY APP_ID";
	public static String 	GET_APP_DETAILS_BY_ID			= "SELECT * FROM "+ _TB_APPLICATION +" WHERE APP_ID = ?";
	public static String 	VALIDATE_APP_NAME				= "SELECT APP_NAME FROM "+ _TB_APPLICATION +" WHERE UPPER(APP_NAME) = UPPER(?)";
	
	/*WORKFLOW TYPE*/
	public static String	SAVE_WORKFLOW_TYPE				= "INSERT INTO "+ _TB_WORKFLOW_TYPE+" (WORKFLOW_TYPE, ADDED_BY, ADDED_DATE, UPDATED_BY, UPDATED_DATE, DESCRIPTION) values (?, ?, SYSDATE, ?, SYSDATE, ?)";
	public static String	UPDATE_WORKFLOW_TYPE			= "UPDATE "+ _TB_WORKFLOW_TYPE+" SET WORKFLOW_TYPE = ?, UPDATED_BY = ?, UPDATED_DATE = SYSDATE, DESCRIPTION = ? WHERE WORKFLOW_TYPE_ID = ?";
	public static String 	GET_WORKFLOW_TYPE_LIST 			= "SELECT * FROM "+ _TB_WORKFLOW_TYPE +" ORDER BY WORKFLOW_TYPE_ID";
	public static String 	GET_WORKFLOW_TYPE_DETAILS_BY_ID	= "SELECT * FROM "+ _TB_WORKFLOW_TYPE +" WHERE WORKFLOW_TYPE_ID = ?";
	public static String 	VALIDATE_WORKFLOW_TYPE_NAME		= "SELECT WORKFLOW_TYPE FROM PS_WORKFLOW_TYPE WHERE UPPER(WORKFLOW_TYPE) = UPPER(?)";
	
	/*USER ACCESS REQUEST*/
	/*ALL PENDING REQUEST*/
	public static String	GET_ALL_USERS_PENDING_REQUEST_LIST= "SELECT r.*, L.LVL_NAME, a.APP_NAME, p.PRF_NAME, u.USER_FNAME, u.USER_LNAME" +
																" FROM "+ _TB_USER_ACCESS_REQUEST 	+" r " +
																" LEFT JOIN "+ _TB_USR_MGMT_LEVEL 	+" l on r.REQUESTED_LVL_ID = l.LVL_ID " +
																" LEFT JOIN "+ _TB_APPLICATION 		+" a on r.REQUESTED_APP_ID = a.APP_ID " + 
																" LEFT JOIN "+ _TB_USR_PROFILE		+" p on r.REQUESTED_PRF_ID = p.PRF_ID " +
																" LEFT JOIN "+ _TB_USERS			+" u on r.USER_ID 		   = u.USER_ID" +
																" WHERE r.STATUS = ? ORDER BY r.REQUEST_ID";

	
	
//	public static String 	SAVE_USER_ACCESS_REQUEST	 	= "INSERT INTO "+ _TB_USER_ACCESS_REQUEST +" (USER_ID, REQUESTED_LVL_ID, REQUESTED_APP_ID, REQUESTED_PRF_ID, STATUS, REQUESTER_COMMENT) values (?, ?, ?, ?, ?, ?)";
//	
//	public static String 	UPDATE_USER_LEVEL				= "UPDATE "+ _TB_USERS +" SET LVL_ID = ?, USER_TEAM = ?, PRF_ID = ?, APP_ID = ? WHERE USER_ID = ?";
//	
//	public static String 	UPDATE_USER_ACCESS_REQUEST_BY_ID= "UPDATE "+ _TB_USER_ACCESS_REQUEST +" SET STATUS = ?, APPROVED_BY = ?, APPROVER_COMMENT = ?, APPROVED_DATE = SYSDATE WHERE REQUEST_ID = ?";
//	
//	public static String	GET_PENDING_USER_REQUEST		= "SELECT r.*, L.LVL_NAME, a.APP_NAME, p.PRF_NAME, u.USER_FNAME, u.USER_LNAME" +
//																" FROM "+ _TB_USER_ACCESS_REQUEST 	+" r " +
//																" LEFT JOIN "+ _TB_USR_MGMT_LEVEL 	+" l on r.REQUESTED_LVL_ID = l.LVL_ID " +
//																" LEFT JOIN "+ _TB_APPLICATION 		+" a on r.REQUESTED_APP_ID = a.APP_ID " + 
//																" LEFT JOIN "+ _TB_USR_PROFILE		+" p on r.REQUESTED_PRF_ID = p.PRF_ID " +
//																" LEFT JOIN "+ _TB_USERS			+" u on r.USER_ID 		   = u.USER_ID" +
//																" WHERE r.USER_ID = ? AND r.STATUS = ? ORDER BY r.REQUEST_ID";

//	public static String	GET_ALL_REQUEST_LIST			= "SELECT r.*, L.LVL_NAME, a.APP_NAME, p.PRF_NAME, u.USER_FNAME, u.USER_LNAME" +
//																" FROM "+ _TB_USER_ACCESS_REQUEST 	+" r " +
//																" LEFT JOIN "+ _TB_USR_MGMT_LEVEL 	+" l on r.REQUESTED_LVL_ID = l.LVL_ID " +
//																" LEFT JOIN "+ _TB_APPLICATION 		+" a on r.REQUESTED_APP_ID = a.APP_ID " + 
//																" LEFT JOIN "+ _TB_USR_PROFILE		+" p on r.REQUESTED_PRF_ID = p.PRF_ID " +
//																" ORDER BY r.REQUEST_ID";
//	
//	public static String 	GET_USER_REQUEST_LIST			= "SELECT r.*, L.LVL_NAME, a.APP_NAME, p.PRF_NAME, u.USER_FNAME, u.USER_LNAME" +
//																" FROM "+ _TB_USER_ACCESS_REQUEST 	+" r " +
//																" LEFT JOIN "+ _TB_USR_MGMT_LEVEL 	+" l on r.REQUESTED_LVL_ID = l.LVL_ID " +
//																" LEFT JOIN "+ _TB_APPLICATION 		+" a on r.REQUESTED_APP_ID = a.APP_ID " + 
//																" LEFT JOIN "+ _TB_USR_PROFILE		+" p on r.REQUESTED_PRF_ID = p.PRF_ID " +
//																" LEFT JOIN "+ _TB_USERS			+" u on r.USER_ID 		   = u.USER_ID" +
//																" LEFT JOIN "+ _TB_USR_PROFILE		+" p on r.REQUESTED_PRF_ID = p.PRF_ID " +
//																" WHERE r.USER_ID = ? ORDER BY r.REQUEST_ID";
	
	
	
	
	
	/*ADD UPDATE WORKFLOW */
	public static String	SAVE_WORKFLOW					= "INSERT INTO "+ _TB_WORKFLOW+" (WORKFLOW_NAME, APP_ID, USERNAME, PASSWORD, AUTH_TOKEN, SERVER, PORT, JOB_NAME, BUILD_TYPE, BUILD_PARAMETER, DESCRIPTION, CREATED_BY, WORKFLOW_TYPE_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String	SAVE_WORKFLOW_PROFILE_MAP		= "INSERT INTO "+ _TB_WORKFLOW_PROFILE_MAP+" (WORKFLOW_ID, PRF_ID) values (?, ?)";
	public static String	DEL_WORKFLOW_PROFILE_MAP		= "DELETE FROM "+ _TB_WORKFLOW_PROFILE_MAP+" WHERE WORKFLOW_ID = ?";
	public static String 	SAVE_WORKFLOW_PARAM_MAP			= "INSERT INTO "+ _TB_WORKFLOW_PARAM_MAP+" (WORKFLOW_ID, PARAMETER_NAME) values (?, ?)";
	public static String	DEL_WORKFLOW_PARAM_MAP			= "DELETE FROM "+ _TB_WORKFLOW_PARAM_MAP+" WHERE WORKFLOW_ID = ?";
	public static String 	SAVE_WORKFLOW_FIXIT_MAP			= "INSERT INTO "+ _TB_WORKFLOW_FIXIT_MAP+" (WORKFLOW_ID, ERROR_NAME, ERROR_CODE, FIXIT_WORKFLOW_NAME, FIXIT_WORKFLOW_ID) values (?, ?, ?, ?, ?)";
	public static String	DEL_WORKFLOW_FIXIT_MAP			= "DELETE FROM "+ _TB_WORKFLOW_FIXIT_MAP+" WHERE WORKFLOW_ID = ?";
	
	public static String 	VALIDATE_WORKFLOW_NAME			= "SELECT WORKFLOW_NAME FROM "+ _TB_WORKFLOW +" WHERE UPPER(WORKFLOW_NAME) = UPPER(?)";
	
	/*ADD UPDATE VIEW START WORKFLOW HISTORY*/
	public static String	SAVE_START_FLOW_HISTORY			= "INSERT INTO "+ _TB_START_FLOW_HISTORY+" (WORKFLOW_ID, START_TIME, START_BY, JENKINS_STATUS) values (?, SYSDATE, ?, ?)";
	public static String	UPDATE_START_FLOW_HISTORY		= "UPDATE "+ _TB_START_FLOW_HISTORY+" SET JENKINS_STATUS = ?, REPORT_STATUS = ? WHERE START_FLOW_ID = ?";
	public static String	GET_START_FLOW_DETAILS			= "SELECT * FROM "+ _TB_START_FLOW_HISTORY+" WHERE WORKFLOW_ID = ? AND JENKINS_STATUS = 'INPROGRESS' ";
	public static String	GET_MAX_START_FLOW_ID_BY_USER	= "SELECT MAX(START_FLOW_ID) FROM PS_START_FLOW_HISTORY WHERE WORKFLOW_ID = ? AND START_BY = ? ";
	
	//public static String 	GET_WORKFLOW_BY_ID 				= "SELECT * FROM "+ _TB_WORKFLOW+" w INNER JOIN "+ _TB_APPLICATION +" a on w.APP_ID = a.APP_ID WHERE WORKFLOW_ID = ? ";
	public static String 	GET_WORKFLOW_BY_ID 				= "SELECT w.*, wt.WORKFLOW_TYPE, a.app_name, r.USERNAME as db_USERNAME, r.PASSWORD as db_PASSWORD, r.HOSTNAME as db_HOSTNAME, r.PORT as db_PORT, r.SID as db_SID, r.DATABASE_TYPE as db_DATABASE_TYPE, r.REPORT_TABLE as db_REPORT_TABLE, r.QUERY as db_QUERY " +
																" FROM PS_WORKFLOW w " +
																" LEFT JOIN "+_TB_APPLICATION+" a on w.APP_ID = a.APP_ID " +
																" LEFT JOIN "+_TB_REPORT_DB_DETAILS+" r on w.WORKFLOW_ID = r.WORKFLOW_ID " +
																" LEFT JOIN "+_TB_WORKFLOW_TYPE+" wt on w.WORKFLOW_TYPE_ID = wt.WORKFLOW_TYPE_ID " + 
																" WHERE w.WORKFLOW_ID = ?";
	
	public static String	GET_WORKFLOW_PROFILE_LIST		= "SELECT m.WORKFLOW_ID, m.PRF_ID, p.PRF_NAME FROM "+_TB_WORKFLOW_PROFILE_MAP+" m INNER JOIN "+_TB_USR_PROFILE+" p on m.PRF_ID = p.PRF_ID WHERE WORKFLOW_ID = ?";
	public static String	GET_SEL_WORKFLOW_PROFILE_IDS	= "SELECT PRF_ID FROM "+_TB_WORKFLOW_PROFILE_MAP+" WHERE WORKFLOW_ID = ?";
	public static String	GET_SEL_WORKFLOW_PARAM_LIST		= "SELECT PARAMETER_NAME FROM "+_TB_WORKFLOW_PARAM_MAP+" WHERE WORKFLOW_ID = ?";
	public static String	GET_WORKFLOW_FIXIT_LIST			= "SELECT WORKFLOW_ID, ERROR_NAME, ERROR_CODE, FIXIT_WORKFLOW_NAME, FIXIT_WORKFLOW_ID FROM "+_TB_WORKFLOW_FIXIT_MAP+" WHERE WORKFLOW_ID = ? ORDER BY ERROR_NAME";
	//public static String	GET_WORKFLOW_NAME_LIST			= "SELECT WORKFLOW_ID, WORKFLOW_NAME FROM "+_TB_WORKFLOW;
	
	
	public static String 	GET_WORKFLOW_BY_NAME 			= "SELECT w.*, wt.WORKFLOW_TYPE, a.app_name, r.USERNAME as db_USERNAME, r.PASSWORD as db_PASSWORD, r.HOSTNAME as db_HOSTNAME, r.PORT as db_PORT, r.SID as db_SID, r.DATABASE_TYPE as db_DATABASE_TYPE, r.REPORT_TABLE as db_REPORT_TABLE, r.QUERY as db_QUERY " +
																" FROM PS_WORKFLOW w " +
																" LEFT JOIN "+_TB_APPLICATION+" a on w.APP_ID = a.APP_ID " +
																" LEFT JOIN "+_TB_REPORT_DB_DETAILS+" r on w.WORKFLOW_ID = r.WORKFLOW_ID " +
																" LEFT JOIN "+_TB_WORKFLOW_TYPE+" wt on w.WORKFLOW_TYPE_ID = wt.WORKFLOW_TYPE_ID " + 
																" WHERE w.WORKFLOW_NAME = ?";
	public static String	UPDATE_WORKFLOW					= "UPDATE "+ _TB_WORKFLOW+" SET WORKFLOW_NAME = ?, WORKFLOW_TYPE_ID = ?, APP_ID = ?, USERNAME = ?, PASSWORD = ?, AUTH_TOKEN = ?, SERVER = ?, PORT = ?, JOB_NAME = ?, BUILD_TYPE = ?, BUILD_PARAMETER = ?, DESCRIPTION = ?, LAST_UPDATED_BY = ? WHERE WORKFLOW_ID = ?";
	
	/*GET WORKFLOW LIST*/
	public static String 	GET_WORKFLOW_LIST 				= "SELECT w.*, a.APP_NAME, wt.WORKFLOW_TYPE FROM "+ _TB_WORKFLOW+" w " +
																"LEFT JOIN "+ _TB_APPLICATION+" a on w.APP_ID = a.APP_ID " +
																"LEFT JOIN "+ _TB_WORKFLOW_TYPE+" wt on w.WORKFLOW_TYPE_ID = wt.WORKFLOW_TYPE_ID " +
																"WHERE w.WORKFLOW_ID in (SELECT p.WORKFLOW_ID FROM PS_WORKFLOW_PROFILE_MAP p WHERE p.PRF_ID = ?)" +
																"ORDER BY w.WORKFLOW_ID";
	
	public static String 	GET_WORKFLOW_LIST_BY_APP_ID		= "SELECT w.WORKFLOW_ID, w.WORKFLOW_NAME FROM "+ _TB_WORKFLOW+" w WHERE APP_ID = ? ORDER BY w.WORKFLOW_NAME";
	
	public static String 	GET_ALL_WORKFLOW_LIST			= "SELECT w.WORKFLOW_ID, w.WORKFLOW_NAME, w.APP_ID FROM "+ _TB_WORKFLOW+" w "
																	+ "WHERE w.WORKFLOW_ID in (SELECT p.WORKFLOW_ID FROM PS_WORKFLOW_PROFILE_MAP p WHERE p.PRF_ID = ? )"
																	+ "ORDER BY w.WORKFLOW_NAME";
	
	/*SAVE WORKFLOW LOG*/
	public static String	SAVE_WORKFLOW_LOG				= "INSERT INTO "+ _TB_WORKFLOW_LOG+" (WORKFLOW_ID, START_BY, START_DATE, STATUS) values (?, ?, SYSDATE, ?)";
	
	/*SAVE UPDATE REPORT DB DETAILS*/
	public static String	SAVE_REPORT_DB_DETAILS			= "INSERT INTO "+ _TB_REPORT_DB_DETAILS+" (WORKFLOW_ID, USERNAME, PASSWORD, HOSTNAME, PORT, SID, DATABASE_TYPE, REPORT_TABLE, QUERY) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String	UPDATE_REPORT_DB_DETAILS		= "UPDATE "+ _TB_REPORT_DB_DETAILS+" SET USERNAME = ?, PASSWORD = ?, HOSTNAME = ?, PORT = ?, SID = ?, DATABASE_TYPE = ?, REPORT_TABLE = ?, QUERY = ? WHERE WORKFLOW_ID = ?";
	
	public static String 	GET_REPORT_DB_DETAILS			= "SELECT * FROM "+ _TB_REPORT_DB_DETAILS+" WHERE WORKFLOW_ID = ?";
	//public static String 	GET_WORKFLOW_REPORT 			= "SELECT * FROM FILE_COUNT WHERE INSERT_DATE in( select max(INSERT_DATE) from FILE_COUNT)";
	
	/*ADD UPDATE BUILD HISTORY */
	public static String	SAVE_BUILD_DETAILS				= "INSERT INTO "+ _TB_BUILD_HISTORY+" (WORKFLOW_ID, BUILD_NO, ID, FULL_DISPLAY_NAME, DISPLAY_NAME, BUILT_ON, URL, QUEUE_ID, TIMESTAMP, ESTIMATED_DURATION, DURATION, SHORT_DESCRIPTIOIN, ADDR, USER_ID, USER_NAME, UPSTREAM_BUILD_NO, UPSTREAM_BUILD_URL, UPSTREAM_PROJECT, RESULT) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String	SAVE_BUILD_DETAILS_BY_ID		= "INSERT INTO "+ _TB_BUILD_HISTORY+" (WORKFLOW_ID, BUILD_NO, ID, FULL_DISPLAY_NAME, DISPLAY_NAME, BUILT_ON, URL, QUEUE_ID, TIMESTAMP, ESTIMATED_DURATION, DURATION, SHORT_DESCRIPTIOIN, ADDR, USER_ID, USER_NAME, UPSTREAM_BUILD_NO, UPSTREAM_BUILD_URL, UPSTREAM_PROJECT, RESULT, START_FLOW_ID) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static String 	GET_START_FLOW_HISTORY			= "SELECT * FROM "+ _TB_START_FLOW_HISTORY+" WHERE WORKFLOW_ID = ?  ORDER BY START_FLOW_ID DESC";
	public static String 	GET_LAST_START_FLOW_HISTORY		= "SELECT * FROM "+ _TB_START_FLOW_HISTORY+" WHERE START_FLOW_ID in (SELECT MAX(START_FLOW_ID) FROM "+ _TB_START_FLOW_HISTORY+" WHERE WORKFLOW_ID = ?)";
	public static String 	GET_BUILD_NOS_FROM_HISTORY 		= "SELECT BUILD_NO FROM "+ _TB_BUILD_HISTORY+"  WHERE START_FLOW_ID = ?";
	public static String 	GET_BUILD_DETAILS				= "SELECT * FROM "+ _TB_BUILD_HISTORY+" WHERE WORKFLOW_ID = ? AND START_FLOW_ID = ?  ORDER BY RESULT, BUILD_NO DESC";
	public static String 	GET_NULL_BUILD_DETAILS			= "SELECT * FROM "+ _TB_BUILD_HISTORY+" WHERE WORKFLOW_ID = ? AND START_FLOW_ID IS NULL  ORDER BY RESULT, BUILD_NO DESC";
	public static String 	GET_WORKFLOW_ID_LIST			= "SELECT WORKFLOW_ID FROM "+ _TB_WORKFLOW;
	public static String 	GET_BUILD_ID_LIST				= "SELECT BUILD_NO FROM "+ _TB_BUILD_HISTORY + " WHERE WORKFLOW_ID = ?";
	
	
	/*FEEDBACK*/
	public static String	SAVE_FEEDBACK					= "INSERT INTO "+ _TB_FEEDBACK+" (FEEDBACK, WORKFLOW_ID, START_FLOW_ID, ADDED_BY, ADDED_DATE, STATUS) values (?, ?, ?, ?, SYSDATE, ?)";
	public static String	UPDATE_FEEDBACK					= "UPDATE "+ _TB_FEEDBACK+" SET STATUS = ?, UPDATED_BY = ?, UPDATED_DATE = SYSDATE, ADMIN_COMMENT = ? WHERE FEEDBACK_ID = ?";
	
	public static String	GET_FEEDBACK_DETAILS_BY_ID		= "SELECT f.*, w.WORKFLOW_NAME FROM PS_FEEDBACK f "+
																"LEFT JOIN "+_TB_WORKFLOW+" w on f.WORKFLOW_ID = w.WORKFLOW_ID "+
																"WHERE f.FEEDBACK_ID = ? ORDER BY f.FEEDBACK_ID";

	public static String	GET_ALL_NEW_INPROGRESS_FEEDBACK_COUNT	= "SELECT COUNT(FEEDBACK_ID) as COUNT FROM "+ _TB_FEEDBACK+" WHERE UPPER(STATUS) <> UPPER('CLOSED')";
	
	/*public static String	GET_ALL_NEW_INPROGRESS_FEEDBACK	= "SELECT * FROM ( "+
																    "SELECT rownum offset, a.* "+ 
																    "FROM( "+
																       "SELECT f.*, w.WORKFLOW_NAME FROM "+ _TB_FEEDBACK+"  f "+ 
																            "LEFT JOIN "+_TB_WORKFLOW+" w on f.WORKFLOW_ID = w.WORKFLOW_ID "+ 
																            "WHERE UPPER(f.STATUS) <> UPPER('CLOSED') ORDER BY ? ?"+
																    ") a  "+
																    "WHERE rownum <= ? "+
																") "+
																"WHERE offset > ? ";*/
															
	/*public static String	GET_ALL_NEW_FEEDBACK			= "SELECT * FROM ( "+
																    "SELECT rownum offset, a.* "+ 
																    "FROM( "+
																       "SELECT f.*, w.WORKFLOW_NAME FROM "+ _TB_FEEDBACK+"  f "+ 
																            "LEFT JOIN PS_WORKFLOW w on f.WORKFLOW_ID = w.WORKFLOW_ID "+ 
																            "WHERE f.STATUS = 'NEW' ORDER BY f.FEEDBACK_ID   "+
																    ") a  "+
																    "WHERE rownum <= ? "+
																") "+
																"WHERE offset > ? ";*/
	
	public static String	GET_ALL_CLOSED_FEEDBACK_COUNT	= "SELECT COUNT(FEEDBACK_ID) as COUNT FROM "+ _TB_FEEDBACK+" WHERE UPPER(STATUS) in ('CLOSED')";
	
	public static String	GET_ALL_CLOSED_FEEDBACK			= "SELECT * FROM ( "+
																    "SELECT rownum offset, a.* "+ 
																    "FROM( "+
																       "SELECT f.*, w.WORKFLOW_NAME FROM PS_FEEDBACK f "+ 
																            "LEFT JOIN PS_WORKFLOW w on f.WORKFLOW_ID = w.WORKFLOW_ID "+ 
																            "WHERE UPPER(f.STATUS) in ('CLOSED') ORDER BY ? ? "+
																    " ) a  "+
																    "WHERE rownum <= ?"+
																" ) "+
																"WHERE offset > ?";
																
																/*"SELECT f.*, w.WORKFLOW_NAME FROM "+ _TB_FEEDBACK+"  f "+
																"LEFT JOIN "+_TB_WORKFLOW+" w on f.WORKFLOW_ID = w.WORKFLOW_ID "+
																"WHERE f.STATUS in ('Closed') ORDER BY f.FEEDBACK_ID";*/
	
	/*public static String	GET_ALL_REJECTED_FEEDBACK		= "SELECT f.*, w.WORKFLOW_NAME FROM "+ _TB_FEEDBACK+"  f "+
																"LEFT JOIN PS_WORKFLOW w on f.WORKFLOW_ID = w.WORKFLOW_ID "+
																"WHERE f.STATUS = 'REJECTED' ORDER BY f.FEEDBACK_ID";
*/
	
}
