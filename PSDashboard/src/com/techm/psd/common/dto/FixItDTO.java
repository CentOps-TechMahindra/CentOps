package com.techm.psd.common.dto;

public class FixItDTO {

	private String 	errorName;
	private int		errorCode;
	private String 	fixitWorkflowName;
	private int		fixitWorkflowId;
	
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getFixitWorkflowName() {
		return fixitWorkflowName;
	}
	public void setFixitWorkflowName(String fixitWorkflowName) {
		this.fixitWorkflowName = fixitWorkflowName;
	}
	public int getFixitWorkflowId() {
		return fixitWorkflowId;
	}
	public void setFixitWorkflowId(int fixitWorkflowId) {
		this.fixitWorkflowId = fixitWorkflowId;
	}
	
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::FixItDTO:::::::::::::::::::::::" +
			" \n errorName			: "+errorName+
			" \n errorCode			: "+errorCode+	
			" \n fixitWorkflowName			: "+fixitWorkflowName+	
			" \n fixitWorkflowId			: "+fixitWorkflowId+
			" \n";
		return str;
	}
}
