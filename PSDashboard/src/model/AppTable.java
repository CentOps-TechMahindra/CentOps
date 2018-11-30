package model;

public class AppTable {
	
	private String appName;
	private int tblId;
	private String tblName;
	private String tblQuery;
	
	
	
	public String getTblQuery() {
		return tblQuery;
	}
	public void setTblQuery(String tblQuery) {
		this.tblQuery = tblQuery;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getTblId() {
		return tblId;
	}
	public void setTblId(int tblId) {
		this.tblId = tblId;
	}
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	
	public AppTable() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppTable(String appName, int tblId, String tblName, String tblQuery) {
		super();
		this.appName = appName;
		this.tblId = tblId;
		this.tblName = tblName;
		this.tblQuery = tblQuery;
	}
	@Override
	public String toString() {
		return "AppTable [appName=" + appName + ", tblId=" + tblId
				+ ", tblName=" + tblName + ", tblQuery=" + tblQuery + "]";
	}
	
	
	
	

}
