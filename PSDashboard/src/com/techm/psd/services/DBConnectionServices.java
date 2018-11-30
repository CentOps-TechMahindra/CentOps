package com.techm.psd.services;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.common.dao.BaseDAO;
import com.techm.psd.constants.AppConstants;

public class DBConnectionServices extends BaseDAO{

	private Logger log = Logger.getLogger(DBConnectionServices.class);

	/**
	 * Getting different data base connection
	 *
	 * @return Connection-Object
	 * 
	 * */

	public Connection getDatabaseConnection(DatabaseBean dbBean) throws Exception   {
		log.info(AppConstants.LOGGING_ENTER_METHOD+ " getDatabaseConnection");
		Connection connection = null;
		//Getting driver name based on DB type
		String driverName 	= AppConstants.DATABASE_DRIVER_NAME.get(dbBean.getDatabaseType().toUpperCase());
		_REPORT_DRIVERNAME 	= driverName;
		_REPORT_URL 		= dbBean.getDbUrl();
		_REPORT_USERNAME 	= dbBean.getUsername();
		_REPORT_PASSWORD 	= dbBean.getPassword();
		
		try{
			connection 		= getReportConnection();
			log.debug("Connection object: "+ connection );
		}catch(SQLException se){
			log.error("Connection.getDatabaseConnection() Failed! Check output the given details "+se.getMessage());
			throw se;
			
		}catch(Exception e){
			log.error("Connection Failed! Check output the given details "+e.getMessage());
			throw e;
		}
		log.info(AppConstants.LOGGING_EXIT_METHOD+ " getDatabaseConnection");
		return connection;
	}
}
