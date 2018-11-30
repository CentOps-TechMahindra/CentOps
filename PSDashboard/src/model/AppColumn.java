package model;

public class AppColumn {
	
	private int tblId;
	private String tblColumn;
	private String tblName;
	
	public String getTblName() {
		return tblName;
	}
	public void setTblName(String tblName) {
		this.tblName = tblName;
	}
	public int getTblId() {
		return tblId;
	}
	public void setTblId(int tblId) {
		this.tblId = tblId;
	}
	public String getTblColumn() {
		return tblColumn;
	}
	public void setTblColumn(String tblColumn) {
		this.tblColumn = tblColumn;
	}
	
	public AppColumn() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AppColumn(int tblId, String tblColumn, String tblName) {
		super();
		this.tblId = tblId;
		this.tblColumn = tblColumn;
		this.tblName = tblName;
	}
	
	@Override
	public String toString() {
		return "AppColumn [tblId=" + tblId + ", tblColumn=" + tblColumn
				+ ", tblName=" + tblName + "]";
	}
	
	
	
	

}
