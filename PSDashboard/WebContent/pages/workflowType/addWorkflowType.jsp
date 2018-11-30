<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Add Workflow Type</title>
	</head>
	
	<body>
		<html:form name="workflowTypeForm"  method="post"  action="/workflowType.do?method=saveWorkflowType" type="com.techm.psd.workflowType.form.WorkflowTypeForm" enctype="multipart/form-data">
			
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
				        	<div class="panel-title pull-left">Add Workflow Type</div>
				        	<div class="clearfix"></div>
					    </div>
				      	<div style="min-height: 400px; margin-left: 20%">
				      		<br />
					    	
					    	<table style="width: 100%;">
					      		<tr style="padding-left: 1px;">
					      			<td style="text-align:right">
					      				Workflow Type Name<b><em style="color: red">*</em> :</b>
					      			</td>
					      			<td style="padding-left: 2px;">
					      				
					      			</td> 
					      			<td style="padding-left: 5px;">
					      				<html:text tabindex="1" name="workflowTypeForm" property="workflowType" styleId="workflowType" maxlength="100" onchange="$(this).checkWorkflowType();"></html:text><br />
					      				<div id="tickIcon" class=""  style="display: none;"><a href="#"><span class="pickThis">&nbsp;</span></a>Workflow Type Name is available.</div>
          								<div id="crossIcon" class=""  style="display: none;"><a href="#"><span class="removeThis">&nbsp;</span></a>Workflow Type Name is unavailable.</div>
          								<input type="hidden" name="isValidWorkflowTypeName" id="isValidWorkflowTypeName" />
					      			</td>
					      			
					      			<br />
						    	
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right">
						    			<br />
						    			Description<b><em style="color: red">*</em> :</b>
						    		</td>
						    		<td style="padding-left: 2px;">
					      				
					      			</td> 
					      			<td style="padding-left: 5px;">
						    			<br />
						    			<html:textarea tabindex="2" name="workflowTypeForm" property="description" styleId="description" cols="50" rows="4" ></html:textarea><br />
						    		</td>
						    	</tr>
					    	
								
						    </table>
					    	
					    	<br />
					    	
					    	<div style="padding-left: 300px;">
						    	<html:submit tabindex="3" value="SAVE"></html:submit>
						    	<html:reset  tabindex="4" 	/>
						    	<html:button tabindex="5" property="back" onclick="jsGoBack();">Back</html:button>
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