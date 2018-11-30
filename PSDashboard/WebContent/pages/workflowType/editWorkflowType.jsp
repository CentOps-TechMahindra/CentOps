<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - View Application</title>
	</head>
	
	<body>
		<html:form name="workflowTypeForm"  method="post"  action="/workflowType.do?method=updateWorkflowType" type="com.techm.psd.workflowType.form.WorkflowTypeForm" enctype="multipart/form-data">
			<html:hidden name="workflowTypeForm" property="workflowTypeId" />
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			
			<!-- Menu Start -->
		  		<jsp:include page="/pages/common/menu.jsp" />
		  	<!-- Menu End -->
		  	
		  	<!-- Container Start -->
			<div class="container-fluid" style=" min-height: 78vh;">
				<!-- Main Content Area Start -->
			  	<div class="main-container" style="width:100%;">
			    	
			    	<div class="panel panel-danger" style="">
				      	<div class="panel-heading">
				        	<div class="panel-title pull-left">Edit Workflow Type</div>
				        	<div class="clearfix"></div>
					    </div>
				      	<div style="min-height: 400px; margin-left: 20%">
				      		
					    	<table style="width: 100%;">
					      		<tr style="padding-left: 1px; padding-left:220px;">
					      			<td style="text-align:right;">Workflow Type ID<b>:</b></td>
					      			<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><bean:write name="workflowTypeForm" property="workflowTypeId" /><br /></td>
					      			<br />
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
					      			<td style="text-align:right"><br />Workflow Type<b>:</b></td>
					      			<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br />
					      				<html:text tabindex="1" name="workflowTypeForm" property="workflowType" styleId="workflowType" maxlength="100" onchange="$(this).checkWorkflowType();"></html:text><br />
					      				<div id="tickIcon" class=""  style="display: none;"><a href="#"><span class="pickThis">&nbsp;</span></a>Workflow Type Name is available.</div>
          								<div id="crossIcon" class=""  style="display: none;"><a href="#"><span class="removeThis">&nbsp;</span></a>Workflow Type Name is unavailable.</div>
          								<input type="hidden" name="isValidWorkflowTypeName" id="isValidWorkflowTypeName" value="true"/>
					      			</td>
					      			<br />
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right"><br />Created By<b>:</b></td>
						    		<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br /><bean:write name="workflowTypeForm" property="addedBy" /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right"><br />Create Date<b>:</b></td>
						    		<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br /><bean:write name="workflowTypeForm" property="addedDate" format="MM/dd/yyyy"/><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right"><br />Update By<b>:</b></td>
						    		<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br /><bean:write name="workflowTypeForm" property="updatedBy" /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right"><br />Update Date<b>:</b></td>
						    		<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br /><bean:write name="workflowTypeForm" property="updatedDate" format="MM/dd/yyyy"/><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right"><br />Description<b>:</b></td>
						    		<td style="padding-left: 2px;"></td> 
					      			<td style="padding-left: 5px;"><br /><html:textarea tabindex="2" name="workflowTypeForm" property="description" styleId="description" cols="50" rows="4" styleClass="textArea"></html:textarea><br /></td>
						    	</tr>
					    	
								
						    </table>
					    	
					    	<br />
					    	
					    	<div style="padding-left: 300px;">
						    	<html:submit tabindex="3" value="UPDATE"></html:submit>
						    	<html:button tabindex="4" property="back" onclick="jsGoBack();">Back</html:button>
				        	</div>
				      	</div>
			    	</div>
				</div>
		  		<!-- Main Content Area End -->
			  
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
			<jsp:include page="/pages/common/loading.jsp" />
			
		</html:form>
		<script type="text/javascript" src="js/workflowType/workflowTypeValidation.js"></script>
		<script type="text/javascript" src="ajax/workflowTypeValidationAjax.js"></script>
		
	</body>
</html>