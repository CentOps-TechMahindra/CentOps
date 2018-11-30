package com.techm.psd.common.dto;

public class UrlDTO {
	private int srn;
	private String url;
	private String desc;	
	private String lic;
	
	public int getSrn() {
		return srn;
	}
	public void setSrn(int srn) {
		this.srn = srn;
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
	
	@Override
	public String toString() {
		String msg = ""+
						"srn		:"+srn+
						"url		:"+url+
						"desc		:"+desc+
						"lic		:"+lic;
		return msg;
	}
}
