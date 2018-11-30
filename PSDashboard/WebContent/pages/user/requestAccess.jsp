<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- Uncomment below meta tag if you want to refresh page in every 10 sec -->
		<!-- <meta http-equiv="refresh" content="10" />  -->
		<title>CentOps - Console</title>
	</head>
	
	<body>
		<html:form name="userForm"  method="post"  action="/user.do?method=saveAccessRequest" type="com.techm.psd.user.form.UserForm" enctype="multipart/form-data" onsubmit="return formValidate();">
			
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			<!-- Header START -->
				<jsp:include page="/pages/common/header.jsp" />
			<!-- Header End -->
			
			<!-- Header Start -->
		  		<nav class = "navbar navbar-default" role = "navigation" style="background-color: #f8f8f8; height:15px;">
		   	   		<div>
				    	<ul class = "nav navbar-nav">
							<h4 style="padding-left:10px; color: black;">
								Request Access
							</h4>
						</ul>
				   </div>
				</nav>
		  	<!-- Header End -->
		  	
			<!-- Container Start -->
			<div class="container-fluid">
				<c:if test="${requestScope.responseMsg != null}">
					<h4 class="page-header" style="margin: 10px 0 20px">
			    		<c:out value="${requestScope.responseMsg}"></c:out> 
			    	</h4>
		    	</c:if>
				<!-- Main Content Area Start -->
			  	<div class="main-container" style="width: 100%">
			  		
			    	<table align="center" style="margin-top: 3px;">
					      		<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">User ID</th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:text name="userForm" property="userId" styleId="userId" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
					      			<th style="right !important" scope="row">First Name</th>
					      			<td scope="row"></td>
					      			<td scope="row">
						    			<html:text name="userForm" property="firstName" styleId="firstName" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Last Name</th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:text name="userForm" property="lastName" styleId="lastName" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Email ID</th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:text name="userForm" property="userEmailId" styleId="userEmailId" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Contact Number</th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:text name="userForm" property="userContactNo" styleId="userContactNo" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">User Team<em style="color: red">*</em></th>
						    		<td scope="row"></td>
						    		<td scope="row"><html:text name="userForm" property="userTeam" styleId="userTeam" styleClass="textFld valid textbox"></html:text><br /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Current Role</th>
						    		<td scope="row"></td>
						    		<td scope="row"><html:text name="userForm" property="userLvlName" styleId="userLvlName" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">New Role<em style="color: red">*</em></th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:select tabindex="" name="userForm" property="requestedLevelId" title="Request Title" styleId="requestedLevelId" styleClass="textFld valid textbox">
				                        	<html:option value="">-- Select --</html:option>
				                        	<html:optionsCollection name="userForm" property="userLvlList" label="levelName" value="levelId"/>
				                        </html:select><br /><br />
						            </td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Current Application</th>
						    		<td scope="row"></td>
						    		<td scope="row"><html:text name="userForm" property="appName" styleId="appName" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">New Application<em style="color: red">*</em></th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:select tabindex="" name="userForm" property="requestedAppId" title="Request Title" styleId="requestedAppId" styleClass="textFld valid textbox">
				                        	<html:option value="">-- Select --</html:option>
				                        	<html:optionsCollection name="userForm" property="appList" label="appName" value="appId"/>
				                        </html:select><br /><br />
						            </td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Current Profile</th>
						    		<td scope="row"></td>
						    		<td scope="row"><html:text name="userForm" property="profileName" styleId="profileName" readonly="true" styleClass="textFld valid textbox"></html:text><br /><br /></td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">New Profile<em style="color: red">*</em></th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:select tabindex="" name="userForm" property="requestedProfileId" title="Request Title" styleId="requestedProfileId" styleClass="textFld valid textbox">
				                        	<html:option value="">-- Select --</html:option>
				                        	<html:optionsCollection name="userForm" property="profileList" label="profileName" value="profileId"/>
				                        </html:select><br /><br />
						            </td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px; color: #2891c7">
						    		<th style="right !important" scope="row">Comment<em style="color: red">*</em></th>
						    		<td scope="row"></td>
						    		<td scope="row">
						    			<html:textarea tabindex="15" name="userForm" property="requesterComment" styleId="requesterComment" styleClass="textFld valid textbox textArea"></html:textarea><br />
							    	</td>
						    	</tr>
						    	
						    </table>
					    	<br />
					    	
					    	<div style="padding-left: 10px;" align="center">
						    	<html:submit value="SAVE"></html:submit>
						    	<html:reset> </html:reset>
						    	<!--<html:button property="back" onclick="jsGoBack();">Back</html:button>-->
				        	</div>
					    	
			  	</div>
			  	<!-- Main Content Area End -->
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
		</html:form>
		<!--footer start-->
			<jsp:include page="/pages/common/footer.jsp" />
		<!--footer end-->
	</body>
	<script>
	//function formValidate(){
		
	//	return true;		
	//}
	$( "form" ).submit(function( event ) {
		var flag = true;
		var msg = "Below field(s) are mandatory:\n";
	
		//Workflow Fields
		var userTeam 		= $("#userTeam")			.val();
		var newRole 		= $("#requestedLevelId")	.val();
		var newApp 			= $("#requestedAppId")		.val();
		var newProfileId 	= $("#requestedProfileId")	.val();
		var comment 		= $("#requesterComment")	.val();
		
		if( userTeam == ""){
			msg += "-User Team.\n";
			flag = false;
		}
		if( newRole == ""){
			msg += "-New Role.\n";
			flag = false;
		}
		if( newApp == ""){
			msg += "-New Application.\n";
			flag = false;
		}
		if( newProfileId == ""){
			msg += "-New Profile.\n";
			flag = false;
		}
		if( comment == ""){
			msg += "-Comment.\n";
			flag = false;
		}
		
		//
		if (flag) {
			return
		}else{
			//alert(msg+db_msg);
			alert(msg);
			event.preventDefault();
		}
	});
	</script>
</html>