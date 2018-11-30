package com.techm.psd.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.techm.psd.common.utils.AppConfig;

public class BaseDAO {

	static String _DRIVERNAME;
	static String _URL;
	static String _USERNAME;
	static String _PASSWORD;

	//DIAL
	static String _DIAL_DRIVER;
	static String _DIAL_URL;
	static String _DIAL_USERNAME;
	static String _DIAL_PASSWORD;
	
	//Line Graph
	static String _LN_GRAPH_DRIVER;
	static String _LN_GRAPH_URL;
	static String _LN_GRAPH_USERNAME;
	static String _LN_GRAPH_PASSWORD;
		
	//Table1
	static String _TBL1_DRIVER;
	static String _TBL1_URL;
	static String _TBL1_USERNAME;
	static String _TBL1_PASSWORD;
	
	//Table2
	static String _TBL2_DRIVER;
	static String _TBL2_URL;
	static String _TBL2_USERNAME;
	static String _TBL2_PASSWORD;
	
	//Table1
	static String _TBL3_DRIVER;
	static String _TBL3_URL;
	static String _TBL3_USERNAME;
	static String _TBL3_PASSWORD;
		
	
	private static Connection _connection = null;

	protected static String _REPORT_DRIVERNAME;
	protected static String _REPORT_URL;
	protected static String _REPORT_USERNAME;
	protected static String _REPORT_PASSWORD;
	protected static Connection _REPORT_connection = null;
	
	private static Logger LOGGER = Logger.getRootLogger();
	
	static {
		//try {
			_DRIVERNAME = AppConfig._DBPROPERTIES.getProperty("DRIVERNAME");
			_URL 		= AppConfig._DBPROPERTIES.getProperty("URL");
			_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("USERNAME");
			_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("PASSWORD");
			
			//DIAL
			_DIAL_DRIVER 	= AppConfig._DBPROPERTIES.getProperty("dial_driver");
			_DIAL_URL 		= AppConfig._DBPROPERTIES.getProperty("dial_url");
			_DIAL_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("dial_user");
			_DIAL_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("dial_password");
			
			//Line Graph
			_LN_GRAPH_DRIVER	= AppConfig._DBPROPERTIES.getProperty("lnGraph_driver");
			_LN_GRAPH_URL 		= AppConfig._DBPROPERTIES.getProperty("lnGraph_url");
			_LN_GRAPH_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("lnGraph_user");
			_LN_GRAPH_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("lnGraph_password");
			
			//Table1
			_TBL1_DRIVER 	= AppConfig._DBPROPERTIES.getProperty("tbl1_driver");
			_TBL1_URL 		= AppConfig._DBPROPERTIES.getProperty("tbl1_url");
			_TBL1_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("tbl1_user");
			_TBL1_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("tbl1_password");
			
			//Table2
			_TBL2_DRIVER 	= AppConfig._DBPROPERTIES.getProperty("tbl2_driver");
			_TBL2_URL 		= AppConfig._DBPROPERTIES.getProperty("tbl2_url");
			_TBL2_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("tbl2_user");
			_TBL2_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("tbl2_password");
			
			//Table3
			_TBL3_DRIVER 	= AppConfig._DBPROPERTIES.getProperty("tbl3_driver");
			_TBL3_URL 		= AppConfig._DBPROPERTIES.getProperty("tbl3_url");
			_TBL3_USERNAME 	= AppConfig._DBPROPERTIES.getProperty("tbl3_user");
			_TBL3_PASSWORD 	= AppConfig._DBPROPERTIES.getProperty("tbl3_password");
			
			
			// load once
			
			LOGGER.info("DB Conn properties initialized!");
		//} catch (ClassNotFoundException e) {
		//	LOGGER.fatal(e);
		//}
	}
	
	public BaseDAO() {
		// The properties are already initialized once in AppConfig Static block.
	}

	
	public static final Connection getConnection() throws SQLException {
		try {
			Class.forName(_DRIVERNAME);
			//Class.forName("org.mariadb.jdbc.Driver");					// FOR MariaDB
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		_connection = DriverManager.getConnection(_URL, _USERNAME, _PASSWORD);
		//_connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/mysql", "root","root");				// FOR MariaDB
		//_connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/test?user=root&password=root");		// FOR MariaDB
		LOGGER.info("DB Conn created! in getConnection()");
		return _connection;
	}
	
	public static final Connection getDialConnection() throws SQLException {
		try {
			Class.forName(_DIAL_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		_connection = DriverManager.getConnection(_DIAL_URL, _DIAL_USERNAME, _DIAL_PASSWORD);
		LOGGER.info("DB Conn created! in getPerfConnection()");
		return _connection;
	}
	
	public static final Connection getLnGraphConnection() throws SQLException {
		try {
			Class.forName(_LN_GRAPH_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		_connection = DriverManager.getConnection(_LN_GRAPH_URL, _LN_GRAPH_USERNAME, _LN_GRAPH_PASSWORD);
		LOGGER.info("DB Conn created! in getPerfConnection()");
		return _connection;
	}
	public static final Connection getTbl1Connection() throws SQLException {
		try {
			Class.forName(_TBL1_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		_connection = DriverManager.getConnection(_TBL1_URL, _TBL1_USERNAME, _TBL1_PASSWORD);
		LOGGER.info("DB Conn created! in getPerfConnection()");
		return _connection;
	}
	public static final Connection getTbl2Connection() throws SQLException {
		try {
			Class.forName(_TBL2_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		_connection = DriverManager.getConnection(_TBL2_URL, _TBL2_USERNAME, _TBL2_PASSWORD);
		LOGGER.info("DB Conn created! in getPerfConnection()");
		return _connection;
	}
	public static final Connection getTbl3Connection() throws SQLException {
		try {
			Class.forName(_TBL3_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		_connection = DriverManager.getConnection(_TBL3_URL, _TBL3_USERNAME, _TBL3_PASSWORD);
		LOGGER.info("DB Conn created! in getPerfConnection()");
		return _connection;
	}
	
	protected static final Connection getReportConnection() throws SQLException {
		try {
			Class.forName(_REPORT_DRIVERNAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		_REPORT_connection = DriverManager.getConnection(_REPORT_URL, _REPORT_USERNAME, _REPORT_PASSWORD);
		LOGGER.info("DB Conn created! in getReportConnection()");
		return _REPORT_connection;
	}

	/**
	 * Closing Database Operation
	 * 
	 * @param conn
	 * @param pstmt
	 * @throws SQLException
	 */
	protected static final void close(final PreparedStatement pstmt, final Connection conn){
		LOGGER.info("DB conn and pstmt closed!");
		try{
			if (pstmt != null) {
				pstmt.close();
			}
	
			if (conn != null) {
				conn.close();
			}
		}catch (Exception e) {
		}
	}

	/**
	 * Closing Database Operation
	 * 
	 * @param conn
	 * @param pstmt
	 * @throws SQLException
	 */
	protected static final void close( final ResultSet rs, final PreparedStatement pstmt, final Connection conn){
		LOGGER.info("DB conn and pstmt closed!");
		try{
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
	
			if (conn != null) {
				conn.close();
			}
		}catch (Exception e) {
		}
	}
	
	/**
	 * Closing Database Operation
	 * 
	 * @param conn
	 * @param pstmt
	 * @throws SQLException
	 */
	protected static final void close( final ResultSet rs, final Statement stmt, final Connection conn){
		LOGGER.info("DB conn and pstmt closed!");
		try{
			if (rs != null) {
				rs.close();
			}
			if (stmt != null) {
				stmt.close();
			}
	
			if (conn != null) {
				conn.close();
			}
		}catch (Exception e) {
		}
	}
	
	/**
	 * Closes the database result set.
	 * 
	 * @param rs
	 *            - result set
	 */
	protected static final void close(final ResultSet rs) {
		LOGGER.info("DB resultset closed!");
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Closes the result set and the statement.
	 * 
	 * @param rs
	 *            - result set
	 * @param pstmt
	 *            - prepare statement
	 */
	protected static final void close(final ResultSet rs, final Statement pstmt) {
		LOGGER.info("DB resultset and pstmt closed!");
		if (rs != null) {
			close(rs);
		}
		if (pstmt != null) {
			close(pstmt);
		}
	}

	/* *
	  *  Closing Database Operation
	  * 
	  * @param conn
	  * @param pstmt
	  * @throws SQLException
	  */
	protected static final void close( final CallableStatement clstmt, final Connection conn){
		LOGGER.info("DB conn and pstmt closed!");
		try{
			if (clstmt != null) {
				clstmt.close();
			}
	
			if (conn != null) {
				conn.close();
			}
		}catch (Exception e) {
		}
	}
	
	/**
	 * Closes the statement.
	 * 
	 * @param pstmt
	 *            - prepare statement
	 */
	protected static final void close(final Statement pstmt) {
		LOGGER.info("DB pstmt closed!");
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (Exception e) {
		}
	}
	
	/**
	 * Closes the connection.
	 * 
	 * @param conn
	 *            - connection
	 */
	protected static final void close(final Connection conn) {
		LOGGER.info("DB conn closed!");
		try {
			if (conn != null) {
				conn.close();
			}			
		} catch (Exception e) {
		}
	}

}
