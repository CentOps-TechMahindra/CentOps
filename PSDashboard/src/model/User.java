package model;

import java.util.Date;

public class User {

    private int cnt;
    private String priority;
    private String critical;
 
    public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	@Override
    public String toString() {
        return "User [userid=" + cnt + ", firstName=" + priority
                + ", lastName=" + critical +  ", email="
                + "" + "]";
    }    
}
