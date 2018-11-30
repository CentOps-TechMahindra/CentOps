package com.techm.psd.bean;

import com.techm.psd.constants.AppConstants;

public class DatabaseBean {

	private int		workflowId;
	private String 	username;
	private String 	password;
	private String 	hostname;
	private int		port;
	private String 	sid;
	private String 	databaseType;
	private String 	dbUrl;
	private String	reportTable;
	private String	query;
	
	public int getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(int workflowId) {
		this.workflowId = workflowId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getDbUrl() {
		return AppConstants.JDBC_ORACLE+hostname+":"+port+":"+sid;
	}
	
	public String getReportTable() {
		return reportTable;
	}
	public void setReportTable(String reportTable) {
		this.reportTable = reportTable;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::DatabaseBean:::::::::::::::::::::::" +
			" \n workflowId			: "+workflowId+
			" \n username			: "+username+	
			" \n password			: "+password+
			" \n hostname			: "+hostname+
			" \n port			: "+port+	
			" \n sid			: "+sid+
			" \n databaseType			: "+databaseType+
			" \n dbUrl			: "+dbUrl+
			" \n reportTable			: "+reportTable+
			" \n query			: "+query+
			" \n";
		return str;
	}
}
