<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Add Application</title>
		<link rel="stylesheet" type="text/css" href="css/trackerTable.css"/>
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
				        	<div class="panel-title pull-left">Workflow Type Tracker</div>
				        	<div class="clearfix"></div>
					    </div>
					    <!-- main content start -->
						<div id="bodyContainer">
							<div class="clear"></div>
							<div class="homeTableScroll000">
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="trackerTable">
									<colgroup>
							        	<col width="5%" />
								        <col width="20%" />
								        <col width="10%" />
								        <col width="10%" />
								        <col width="10%" />
								        <col width="10%" />
								        <col width="25%" />
								        <col width="10%" />
								    </colgroup>
							        <tbody>
							        
								        <tr>
											<th align="center">ID</th>
											<th align="center">Workflow Type NAME</th>
											<th align="center">CREATE BY</th>
											<th align="center">CREATE DATE</th>
											<th align="center">UPDATE BY</th>
											<th align="center">UPDATE DATE</th>
											<th align="center">Description</th>
											<th align="center"><b class = "glyphicon glyphicon-cog" style="width: 35px;"></b></th>
										</tr>
										
										
										<logic:iterate id="obj" name="workflowTypeForm" property="workflowTypeDTOList" indexId="index">
											<tr id="<%=index%>">
												<td>
													<a href="workflowType.do?method=viewWorkflowType&workflowTypeId=<bean:write name="obj" property="workflowTypeId"/>">
														<bean:write name="obj" property="workflowTypeId" />
													</a>
												</td> 
												<td><bean:write name="obj" property="workflowType" /></td>
												<td><bean:write name="obj" property="addedBy"/></td>
												<td><bean:write name="obj" property="addedDate" format="MM/dd/yyyy"/></td>
												<td><bean:write name="obj" property="updatedBy" /></td>
												<td><bean:write name="obj" property="updatedDate" format="MM/dd/yyyy"/></td>
												<td><bean:write name="obj" property="description" /></td>
												<td>
													<a href="workflowType.do?method=editWorkflowType&workflowTypeId=<bean:write name="obj" property="workflowTypeId"/>">
														<b class = "glyphicon glyphicon-edit"></b>
													</a>
													<a href="#">
														<b class = "glyphicon glyphicon-remove-circle" style="color: Red;"></b>
													</a>
												</td>
											</tr>
										</logic:iterate>
											
									</tbody>
								</table>					
								
							</div>
						</div>
						<!-- main content end -->
		
						<div class="clearfix"></div>
					</div>
				</div>
				<!-- Main Content Area END -->
			</div>
			<!-- Container End -->
			
		</html:form>
		
	</body>
</html>