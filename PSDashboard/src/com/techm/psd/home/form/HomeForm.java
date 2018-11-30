package com.techm.psd.home.form;

import java.util.List;

import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.form.BaseForm;

public class HomeForm extends BaseForm{

	private List<ApplicationDTO> appDTOList;

	public List<ApplicationDTO> getAppDTOList() {
		return appDTOList;
	}

	public void setAppDTOList(List<ApplicationDTO> appDTOList) {
		this.appDTOList = appDTOList;
	}
}
