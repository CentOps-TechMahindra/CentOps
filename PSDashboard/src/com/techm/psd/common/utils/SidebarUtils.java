package com.techm.psd.common.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.techm.psd.application.dao.ApplicationDAO;
import com.techm.psd.application.dao.ApplicationDAOImpl;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.ApplicationDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.exception.PSDException;
import com.techm.psd.workflow.action.WorkflowAction;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class SidebarUtils {

	private Logger logger = Logger.getLogger(WorkflowAction.class);
	
	public void setSidebarData(UserDTO user, HttpServletRequest request){
		logger.info("Enter into SidebarUtils.setSidebarData()...");
		CommonDAO 	cDAO = new CommonDAOImpl();
		ApplicationDAO aDAO = new ApplicationDAOImpl();
		WorkflowDAO wDAO = new WorkflowDAOImpl();
		try {
			List<ApplicationDTO> appList 	= aDAO.getApplicationList();
			List<WorkflowDTO> wDTOList 		= wDAO.getWorkflowList(user);
			request.setAttribute("appList", appList);
			request.setAttribute("wDTOList", wDTOList);
		} catch (PSDException e) {
			logger.error("Enter into SidebarUtils.setSidebarData()..."+e.getMessage());
			e.printStackTrace();
		}
		
		logger.info("Exit from SidebarUtils.setSidebarData()...");
	}
}
