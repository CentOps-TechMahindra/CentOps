package com.techm.psd.jenkins.bo;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;

public class JobData {
    private String username;
    private String password;
    private String token;
    private String server;
    private String port;
    private String job;
    private List<NameValuePair> parameters;
    private String 		buildType;
	private String 		buildParameter;
	
    public JobData() {
        parameters = new ArrayList<NameValuePair>();
    }

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}
    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}
    public String getServer() {return server;}
    public void setServer(String server) {this.server = server;}
    public String getPort() {return port;}
    public void setPort(String port) {this.port = port;}
    public String getJob() {return job;}
    public void setJob(String job) {this.job = job;}
    public List<NameValuePair> getParameters() {return parameters;}
	public String getBuildType() {return buildType;}
	public void setBuildType(String buildType) {this.buildType = buildType;}
	public String getBuildParameter() {return buildParameter;}
	public void setBuildParameter(String buildParameter) {this.buildParameter = buildParameter;}
}
