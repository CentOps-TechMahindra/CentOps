package com.techm.psd.common.dto;

import java.util.List;

public class WorkflowConfigDTO {

	private String 	description;
	private boolean keepDependencies;
	private int 	appsId;
	private String 	nodeParamName;
	private List<String> allowedSlaves;
	private List<String> defaultSlaves;
	private String 	triggerIfResult; 
	private boolean allowMultiNodeSelection;
	private boolean	triggerConcurrentBuilds;
	private boolean ignoreOfflineNodes;
	private boolean canRoam;
	private boolean disabled;
	private boolean blockBuildWhenDownstreamBuilding;
	private boolean blockBuildWhenUpstreamBuilding;
	private String	authToken;
	private	boolean	concurrentBuild;
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isKeepDependencies() {
		return keepDependencies;
	}
	public void setKeepDependencies(boolean keepDependencies) {
		this.keepDependencies = keepDependencies;
	}
	public int getAppsId() {
		return appsId;
	}
	public void setAppsId(int appsId) {
		this.appsId = appsId;
	}
	public String getNodeParamName() {
		return nodeParamName;
	}
	public void setNodeParamName(String nodeParamName) {
		this.nodeParamName = nodeParamName;
	}
	public List<String> getAllowedSlaves() {
		return allowedSlaves;
	}
	public void setAllowedSlaves(List<String> allowedSlaves) {
		this.allowedSlaves = allowedSlaves;
	}
	public List<String> getDefaultSlaves() {
		return defaultSlaves;
	}
	public void setDefaultSlaves(List<String> defaultSlaves) {
		this.defaultSlaves = defaultSlaves;
	}
	public String getTriggerIfResult() {
		return triggerIfResult;
	}
	public void setTriggerIfResult(String triggerIfResult) {
		this.triggerIfResult = triggerIfResult;
	}
	public boolean isAllowMultiNodeSelection() {
		return allowMultiNodeSelection;
	}
	public void setAllowMultiNodeSelection(boolean allowMultiNodeSelection) {
		this.allowMultiNodeSelection = allowMultiNodeSelection;
	}
	public boolean isTriggerConcurrentBuilds() {
		return triggerConcurrentBuilds;
	}
	public void setTriggerConcurrentBuilds(boolean triggerConcurrentBuilds) {
		this.triggerConcurrentBuilds = triggerConcurrentBuilds;
	}
	public boolean isIgnoreOfflineNodes() {
		return ignoreOfflineNodes;
	}
	public void setIgnoreOfflineNodes(boolean ignoreOfflineNodes) {
		this.ignoreOfflineNodes = ignoreOfflineNodes;
	}
	public boolean isCanRoam() {
		return canRoam;
	}
	public void setCanRoam(boolean canRoam) {
		this.canRoam = canRoam;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public boolean isBlockBuildWhenDownstreamBuilding() {
		return blockBuildWhenDownstreamBuilding;
	}
	public void setBlockBuildWhenDownstreamBuilding(boolean blockBuildWhenDownstreamBuilding) {
		this.blockBuildWhenDownstreamBuilding = blockBuildWhenDownstreamBuilding;
	}
	public boolean isBlockBuildWhenUpstreamBuilding() {
		return blockBuildWhenUpstreamBuilding;
	}
	public void setBlockBuildWhenUpstreamBuilding(boolean blockBuildWhenUpstreamBuilding) {
		this.blockBuildWhenUpstreamBuilding = blockBuildWhenUpstreamBuilding;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public boolean isConcurrentBuild() {
		return concurrentBuild;
	}
	public void setConcurrentBuild(boolean concurrentBuild) {
		this.concurrentBuild = concurrentBuild;
	}
		
	@Override
	public String toString() {
		String str = 
				" :::::::::::::::::::WorkflowConfigDTO:::::::::::::::::::::::" +
				" \n description			: "+description+
				" \n keepDependencies			: "+keepDependencies+
				" \n appsId			: "+appsId+
				" \n nodeParamName			: "+nodeParamName+
				" \n allowedSlaves			: "+allowedSlaves+
				" \n defaultSlaves			: "+defaultSlaves+
				" \n triggerIfResult			: "+triggerIfResult+
				" \n allowMultiNodeSelection			: "+allowMultiNodeSelection+
				" \n triggerConcurrentBuilds			: "+triggerConcurrentBuilds+
				" \n ignoreOfflineNodes			: "+ignoreOfflineNodes+
				" \n canRoam			: "+canRoam+
				" \n disabled			: "+disabled+
				" \n blockBuildWhenDownstreamBuilding			: "+blockBuildWhenDownstreamBuilding+
				" \n blockBuildWhenUpstreamBuilding			: "+blockBuildWhenUpstreamBuilding+
				" \n authToken			: "+authToken+
				" \n concurrentBuild			: "+concurrentBuild+
				" \n";
			return str;
	}
}
