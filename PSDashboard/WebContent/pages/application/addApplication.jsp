<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Add Application</title>
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
				        	<div class="panel-title pull-left">Add Application</div>
				        	<div class="clearfix"></div>
					    </div>
				      	<div style="min-height: 400px; margin-left: 20%">
				      		
					    	<table style="width: 100%;">
					    	
					      		<!-- Application Name: START -->
					      		<tr style="padding-left: 1px;">
					      			<td style="text-align:right">
					      				Application Name<b><em style="color: red">*</em> :</b>
					      			</td>
					      			<td style="padding-left: 2px;">
					      				
					      			</td> 
					      			<td style="padding-left: 5px;">
					      				<html:text tabindex="1" name="applicationForm" property="appName" styleId="appName" maxlength="100" onchange="$(this).checkApp();"></html:text><br />
					      				<div id="tickIcon" class="validUser"  style="display: none;"><a href="#"><span class="pickThis">&nbsp;</span></a>Application Name is available.</div>
          								<div id="crossIcon" class="validUser"  style="display: none;"><a href="#"><span class="removeThis">&nbsp;</span></a>Application Name is unavailable.</div>
          								<input type="hidden" name="isValidAppName" id="isValidAppName" />
					      			</td>
					      			<br />
						    	</tr>
						    	<!-- Application Name: END -->
						    	
						    	<!-- MPTS ID: START -->
						    	<tr style="padding-left: 1px;">
					      			<td style="text-align:right">
					      				<br />
						    			APP ID<b><em style="color: red">*</em> :</b>
					      			</td>
					      			<td style="padding-left: 2px;">
					      				
					      			</td> 
					      			<td style="padding-left: 5px;">
					      				<br />
						    			<html:text tabindex="2" name="applicationForm" property="appsId" styleId="appsId" maxlength="100"></html:text><br />
					      			</td>
					      			<br />
						    	</tr>
						    	<!-- MPTS ID: END -->
						    	
						    	<!-- Description: START -->
						    	<tr style="padding-left: 1px;">
						    		<td style="text-align:right">
						    			<br />
						    			Description<b><em style="color: red">*</em> :</b>
						    		</td>
						    		<td style="padding-left: 2px;">
					      				
					      			</td> 
					      			<td style="padding-left: 5px;">
						    			<br />
						    			<html:textarea tabindex="3" name="applicationForm" property="desc" styleId="desc" cols="50" rows="4" ></html:textarea><br />
						    		</td>
						    	</tr>
					    		<!-- Description: END -->
								
						    </table>
					    	
					    	<br />
					    	
					    	<div style="padding-left: 300px;">
						    	<html:submit tabindex="4" value="SAVE" />
						    	<html:reset	 tabindex="5" />
						    	<html:button tabindex="6" property="back" onclick="jsGoBack();">Back</html:button>
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
		<script type="text/javascript" src="js/application/applicationValidation.js"></script>
		<script type="text/javascript" src="ajax/appValidationAjax.js"></script>
		
	</body>
</html>