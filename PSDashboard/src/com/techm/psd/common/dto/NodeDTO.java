package com.techm.psd.common.dto;

public class NodeDTO {

	private String 	displayName;
	private boolean idle;
	private boolean jnlpAgent;
	private boolean launchSupported;
	private boolean manualLaunchAllowed;
	private int 	numExecutors; 
	private boolean offline;
	private String 	offlineCauseReason;
	private boolean temporarilyOffline;
	
	
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public boolean isIdle() {
		return idle;
	}
	public void setIdle(boolean idle) {
		this.idle = idle;
	}
	public boolean isJnlpAgent() {
		return jnlpAgent;
	}
	public void setJnlpAgent(boolean jnlpAgent) {
		this.jnlpAgent = jnlpAgent;
	}
	public boolean isLaunchSupported() {
		return launchSupported;
	}
	public void setLaunchSupported(boolean launchSupported) {
		this.launchSupported = launchSupported;
	}
	public boolean isManualLaunchAllowed() {
		return manualLaunchAllowed;
	}
	public void setManualLaunchAllowed(boolean manualLaunchAllowed) {
		this.manualLaunchAllowed = manualLaunchAllowed;
	}
	public int getNumExecutors() {
		return numExecutors;
	}
	public void setNumExecutors(int numExecutors) {
		this.numExecutors = numExecutors;
	}
	public boolean isOffline() {
		return offline;
	}
	public void setOffline(boolean offline) {
		this.offline = offline;
	}
	public String getOfflineCauseReason() {
		return offlineCauseReason;
	}
	public void setOfflineCauseReason(String offlineCauseReason) {
		this.offlineCauseReason = offlineCauseReason;
	}
	public boolean isTemporarilyOffline() {
		return temporarilyOffline;
	}
	public void setTemporarilyOffline(boolean temporarilyOffline) {
		this.temporarilyOffline = temporarilyOffline;
	}
	
	@Override
	public String toString() {
		String str = 
			" :::::::::::::::::::NodeDTO:::::::::::::::::::::::" +
			" \n manualLaunchAllowed			: "+manualLaunchAllowed+
			" \n displayName			: "+displayName+
			" \n idle			: "+idle+
			" \n jnlpAgent			: "+jnlpAgent+
			" \n launchSupported			: "+launchSupported+
			" \n numExecutors			: "+numExecutors+
			" \n offline			: "+offline+
			" \n offlineCauseReason			: "+offlineCauseReason+
			" \n temporarilyOffline			: "+temporarilyOffline+
			" \n";
		return str;
	}
	
}
