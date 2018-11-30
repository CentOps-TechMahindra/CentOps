package model;

public class Availibility {
	
	private String appName;
	private int apps;
	private int ava;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getApps() {
		return apps;
	}
	public void setApps(int apps) {
		this.apps = apps;
	}
	public int getAva() {
		return ava;
	}
	public void setAva(int ava) {
		this.ava = ava;
	}
	
	public Availibility() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Availibility(String appName, int apps, int ava) {
		super();
		this.appName = appName;
		this.apps = apps;
		this.ava = ava;
	}
	
	@Override
	public String toString() {
		return "Availibility [appName=" + appName + ", apps=" + apps + ", ava="
				+ ava + "]";
	}
	
	

}
