package com.techm.psd.common.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ReportDTO {

	private String 		cmInstId;
	private String 		collName;
	private String 		collSubDir;
	private int    		fileCount;
	private Timestamp 	oldestTs;
	private Timestamp 	latestTs;
	private Date 		insertDate;
	private String 		result;
	
	
	public String getCmInstId() {
		return cmInstId;
	}
	public void setCmInstId(String cmInstId) {
		this.cmInstId = cmInstId;
	}
	public String getCollName() {
		return collName;
	}
	public void setCollName(String collName) {
		this.collName = collName;
	}
	public String getCollSubDir() {
		return collSubDir;
	}
	public void setCollSubDir(String collSubDir) {
		this.collSubDir = collSubDir;
	}
	public int getFileCount() {
		return fileCount;
	}
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
	public Timestamp getOldestTs() {
		return oldestTs;
	}
	public void setOldestTs(Timestamp oldestTs) {
		this.oldestTs = oldestTs;
	}
	public Timestamp getLatestTs() {
		return latestTs;
	}
	public void setLatestTs(Timestamp latestTs) {
		this.latestTs = latestTs;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
