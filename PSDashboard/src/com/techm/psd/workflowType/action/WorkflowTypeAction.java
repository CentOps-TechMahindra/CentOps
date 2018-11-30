package com.techm.psd.workflowType.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.techm.psd.common.action.BaseAction;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.dto.WorkflowTypeDTO;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.workflowType.dao.WorkflowTypeDAO;
import com.techm.psd.workflowType.dao.WorkflowTypeDAOImpl;
import com.techm.psd.workflowType.form.WorkflowTypeForm;

public class WorkflowTypeAction extends BaseAction{
	
	private Logger logger = Logger.getLogger(WorkflowTypeAction.class);

	public ActionForward addWorkflowType(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.addWorkflowType()...");
		String forward 			= "addWorkflowType";
		logger.info("Exit from WorkflowTypeAction.addWorkflowType()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward saveWorkflowType(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.saveWorkflowType()...");
		WorkflowTypeForm wForm 	= (WorkflowTypeForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		String	userId			= user.getUserId();
		boolean result 			= false; 
		String forward 			= "addWorkflowType";
		WorkflowTypeDTO wDTO 	= new WorkflowTypeDTO();
		WorkflowTypeDAO wDAO 	= new WorkflowTypeDAOImpl();
		
		wForm.setAddedBy(userId);	//Adding Current User UID
		wForm.setUpdatedBy(userId);
		
		wDTO.setWorkflowType(wForm.getWorkflowType());
		wDTO.setAddedBy		(wForm.getAddedBy()		);
		wDTO.setUpdatedBy	(wForm.getUpdatedBy()	);
		wDTO.setDescription	(wForm.getDescription()	);
		
		result = wDAO.saveWorkflowType(wDTO);
		
		//Setting WorkflowType Id & Created Date in wFORM 
		wForm.setWorkflowTypeId(wDTO.getWorkflowTypeId());
		wForm.setAddedDate(wDTO.getAddedDate());
		
		if(result){
			//return viewWorkflowType(mapping, aForm, request, response);
			return workflowTypeTracker(mapping, wForm, request, response);
		}

		logger.info("Exit from WorkflowTypeAction.saveWorkflowType()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward viewWorkflowType(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.viewWorkflowType()...");
		WorkflowTypeForm wForm 	= (WorkflowTypeForm)form;
		
		String forward 			= "viewWorkflowType";
		WorkflowTypeDTO wDTO 	= new WorkflowTypeDTO();
		WorkflowTypeDAO wDAO 	= new WorkflowTypeDAOImpl();
		
		int workflowTypeId		= 0;
		
		//GETTING workflowTypeId TO FETCH THE DATA: START
		if(request.getParameter("workflowTypeId") != null){
			workflowTypeId = Integer.parseInt(request.getParameter("workflowTypeId"));
		}else if(wForm.getWorkflowTypeId() != 0){
			workflowTypeId = wForm.getWorkflowTypeId();
		}else{
			logger.info("In WorkflowTypeAction.viewWorkflowType()... appId is not available!!!");
		}
		
		wDTO = wDAO.getWorkflowTypeDetailsById(workflowTypeId);
		
		//WorkflowTypeDTO to WorkflowTypeForm
		wForm.setWorkflowTypeId	(wDTO.getWorkflowTypeId());
		wForm.setWorkflowType	(wDTO.getWorkflowType()  );
		wForm.setAddedBy		(wDTO.getAddedBy()		 );
		wForm.setAddedDate		(wDTO.getAddedDate()	 );
		wForm.setUpdatedBy		(wDTO.getUpdatedBy()	 );
		wForm.setUpdatedDate	(wDTO.getUpdatedDate()	 );
		wForm.setDescription	(wDTO.getDescription()	 );
		//END
		
		//GETTING WorkflowTypeId TO FETCH THE DATA: END
		
		logger.info("Exit from WorkflowTypeAction.viewWorkflowType()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward editWorkflowType(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.editWorkflowType()...");
		WorkflowTypeForm wForm 	= (WorkflowTypeForm)form;
		
		String forward 			= "editWorkflowType";
		WorkflowTypeDTO wDTO 	= new WorkflowTypeDTO();
		WorkflowTypeDAO wDAO 	= new WorkflowTypeDAOImpl();
		
		int workflowTypeId		= 0;
		
		//GETTING workflowTypeId TO FETCH THE DATA: START
		if(request.getParameter("appId") != null){
			workflowTypeId = Integer.parseInt(request.getParameter("workflowTypeId"));
		}else if(wForm.getWorkflowTypeId() != 0){
			workflowTypeId = wForm.getWorkflowTypeId();
		}else{
			logger.info("In WorkflowTypeAction.editWorkflowType()... workflowTypeId is not available!!!");
		}
		
		wDTO = wDAO.getWorkflowTypeDetailsById(workflowTypeId);
		
		//WorkflowTypeDTO to WorkflowTypeForm
		wForm.setWorkflowTypeId	(wDTO.getWorkflowTypeId());
		wForm.setWorkflowType	(wDTO.getWorkflowType()  );
		wForm.setAddedBy		(wDTO.getAddedBy()		 );
		wForm.setAddedDate		(wDTO.getAddedDate()	 );
		wForm.setUpdatedBy		(wDTO.getUpdatedBy()	 );
		wForm.setUpdatedDate	(wDTO.getUpdatedDate()	 );
		wForm.setDescription	(wDTO.getDescription()	 );
		//END
		
		//GETTING WorkflowTypeId TO FETCH THE DATA: END
		
		logger.info("Exit from WorkflowTypeAction.editWorkflowType()...");
		return mapping.findForward(forward);
	}
	
	public ActionForward updateWorkflowType(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.updateWorkflowType()...");
		WorkflowTypeForm wForm 	= (WorkflowTypeForm)form;
		HttpSession session 	= request.getSession(false);
		UserDTO user	 		= UserSessionUtil.getUserSession(session);
		String	userId			= user.getUserId();
		boolean result 			= false; 
		String forward 			= "addWorkflowType";
		WorkflowTypeDTO wDTO 	= new WorkflowTypeDTO();
		WorkflowTypeDAO wDAO 	= new WorkflowTypeDAOImpl();
		
		//aForm.setCreateBy(userId);	//Adding Current User UID
		wForm.setUpdatedBy(userId);
		wDTO.setWorkflowTypeId(wForm.getWorkflowTypeId());
		
		wDTO.setWorkflowType(wForm.getWorkflowType());
		wDTO.setUpdatedBy	(wForm.getUpdatedBy()	);
		wDTO.setDescription	(wForm.getDescription()	);
		
		result = wDAO.updateWorkflowType(wDTO);
		
		if(result){
			return viewWorkflowType(mapping, wForm, request, response);
			//return workflowTypeTracker(mapping, wForm, request, response);
		}

		logger.info("Exit from WorkflowTypeAction.updateWorkflowType()...");
		//return mapping.findForward(forward);
		return workflowTypeTracker(mapping, wForm, request, response);
	}
	
	public ActionForward workflowTypeTracker(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		
		logger.info("Enters into WorkflowTypeAction.workflowTypeTracker()...");
		WorkflowTypeForm wForm 	= (WorkflowTypeForm)form;
		String forward 			= "workflowTypeTracker";
		WorkflowTypeDAO wDAO 	= new WorkflowTypeDAOImpl();
		
		wForm.setWorkflowTypeDTOList(wDAO.getWorkflowTypeList());
		
		logger.info("Exit from WorkflowTypeAction.workflowTypeTracker()...");
		return mapping.findForward(forward);
	}
	
	/*
	 * Override in case User types in portal.do in the URL. Default method is 'home' which will get called
	 * by below method. 
	 */
	protected ActionForward unspecified(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		return addWorkflowType(mapping, form, request, response);
	}
}
