package com.techm.psd.common.dto;

public class UserLvlDTO {

	private int 	levelId;
	private String 	levelName;
	private String 	levelScope;
	private String 	levelDesc;
	private String 	levelDefault;
	
	public int getLevelId() {
		return levelId;
	}
	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelScope() {
		return levelScope;
	}
	public void setLevelScope(String levelScope) {
		this.levelScope = levelScope;
	}
	public String getLevelDesc() {
		return levelDesc;
	}
	public void setLevelDesc(String levelDesc) {
		this.levelDesc = levelDesc;
	}
	public String getLevelDefault() {
		return levelDefault;
	}
	public void setLevelDefault(String levelDefault) {
		this.levelDefault = levelDefault;
	}
}
