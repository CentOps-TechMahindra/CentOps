package com.techm.psd.console.form;

import java.util.List;
import com.techm.psd.common.dto.UrlDTO;
import com.techm.psd.common.form.BaseForm;

public class ConsoleForm extends BaseForm{

	private int				srNo;
	private String 			url;
	private String 			desc;
	private String 			lic;
	private String 			selApp;
	private List<UrlDTO> 	urlList;
	
	public int getSrNo() {
		return srNo;
	}
	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLic() {
		return lic;
	}
	public void setLic(String lic) {
		this.lic = lic;
	}
	public String getSelApp() {
		return selApp;
	}
	public void setSelApp(String selApp) {
		this.selApp = selApp;
	}
	public List<UrlDTO> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<UrlDTO> urlList) {
		this.urlList = urlList;
	}
}
