package com.techm.psd.login.form;

import com.techm.psd.common.form.BaseForm;

public class LoginForm extends BaseForm{

	private String userName = null;
    private String password = null;
    
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}