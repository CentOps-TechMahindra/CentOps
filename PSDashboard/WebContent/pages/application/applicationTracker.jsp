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
		<html:form name="applicationForm"  method="post"  action="/application.do?method=saveApplication" type="com.techm.psd.application.form.ApplicationForm" enctype="multipart/form-data">
			
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
				        	<div class="panel-title pull-left">Application Tracker</div>
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
								        <col width="5%" />
								        <col width="10%" />
								        <col width="8%" />
								        <col width="8%" />
								        <col width="9%" />
								        <col width="25%" />
								        <col width="10%" />
								    </colgroup>
							        <tbody>
							        
								        <tr>
											<th align="center">ID</th>
											<th align="center">Application NAME</th>
											<th align="center">APP ID</th>
											<th align="center">CREATE BY</th>
											<th align="center">CREATE DATE</th>
											<th align="center">UPDATE BY</th>
											<th align="center">UPDATE DATE</th>
											<th align="center">Description</th>
											<th align="center"><b class = "glyphicon glyphicon-cog" style="width: 35px;"></b></th>
										</tr>
										
										
										<logic:iterate id="obj" name="applicationForm" property="appDTOList" indexId="index">
											<tr id="<%=index%>">
												<td>
													<a href="application.do?method=viewApplication&appId=<bean:write name="obj" property="appId"/>">
														<bean:write name="obj" property="appId" />
													</a>
												</td> 
												<td><bean:write name="obj" property="appName" /></td>
												<td><bean:write name="obj" property="appsId" /></td>
												<td><bean:write name="obj" property="createBy"/></td>
												<td><bean:write name="obj" property="createDate" format="MM/dd/yyyy"/></td>
												<td><bean:write name="obj" property="updateBy" /></td>
												<td><bean:write name="obj" property="updateDate" format="MM/dd/yyyy"/></td>
												<td><bean:write name="obj" property="desc" /></td>
												<td>
													<a href="application.do?method=editApplication&appId=<bean:write name="obj" property="appId"/>">
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
					  	  	<div class="clearfix"></div>
						</div>
						<!-- main content end -->
					</div>
				</div>
				<!-- Main Content Area END -->
			</div>
			<!-- Container End -->
			
		</html:form>
		
	</body>
</html>