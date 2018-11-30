<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Review Access</title>
		<link rel="stylesheet" type="text/css" href="css/trackerTable.css"/>
	</head>
	
	<body>
		<%--
			<html:form name="userForm"  method="post"  action="/admin.do?method=approveRejectRequest" type="com.techm.psd.user.form.UserForm" enctype="multipart/form-data">
		--%>
		<html:form action="/admin.do" onsubmit="return formValidate();">
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			<!-- Header START -->
				<jsp:include page="/pages/common/header.jsp" />
			<!-- Header End -->
		  	
		  	<!-- Container Start -->
			<div class="container-fluid" style=" min-height: 78vh;">
			<!-- Main Content Area Start -->
			  	<div class="main-container" style="width:100%;">
			    	<div class="panel panel-danger" style="">
				      	<div class="panel-heading">
				        	<div class="panel-title pull-left">Review Request(s)</div>
				        	<div class="clearfix"></div>
					    </div>
					    <!-- main content start -->
						<div id="bodyContainer">
							<div class="clear"></div>
							<div class="homeTableScroll000" id="reviewRequest" >
								
								<table width="100%" border="0" cellspacing="0" cellpadding="0" class="trackerTable">
									<colgroup>
							        	<col width="20" />
							            <col width="100" />
							            <col width="100" />
							            <col width="200" />
							            <col width="200" />
							            <col width="200" />
							            <col width="200" />
							            <col width="300" />
								    </colgroup>
						            <tbody>
									   	<tr>
											<th style="border:1px solid #90bfd7; border-width:1px 1px 1px 1px;" align="center">
												<input type="checkbox" name="checkbox" id="checkbox" onclick="SetAllCheckBoxes('userForm','reviewRequest',this.checked);"/>
											</th>
											<th align="center">Request ID				</th>
											<th align="center">User ID					</th>
											<th align="center">User Name				</th>
											<th align="center">Role			</th>
											<th align="center">Application	</th>
											<th align="center">Profile		</th>
											<th align="center">Comment		</th>
										</tr>
										
										<logic:iterate id="uDTO" name="userForm" property="userDtoList" indexId="index">
											<tr id="<%=index%>">
												<td style="border:1px solid #90bfd7; border-width:1px 1px 1px 1px; padding-left: 2px; text-align:center">
													<input type="checkbox" name="selRequests" value="<bean:write name='uDTO' property='requestId'/>" />
												</td>
												<td><bean:write name="uDTO" property="requestId"/>				</td>
												<td><bean:write name="uDTO" property="userId"/>					</td>
												<td><bean:write name="uDTO" property="userName"/>				</td>
												<td><bean:write name="uDTO" property="requestedLevelName"/>		</td>
												<td><bean:write name="uDTO" property="requestedAppName"/>		</td>
												<td><bean:write name="uDTO" property="requestedProfileName"/>	</td>
												<td><bean:write name="uDTO" property="requesterComment"/>		</td>
											</tr>
										</logic:iterate>
									</tbody>
								</table>
								<logic:empty name="userForm" property="userDtoList"> 
									<div style="padding-top: 25px; text-align: center; font-size: medium; text-decoration: blink">
										No pending request!
									</div>
								</logic:empty>					
							</div>
							
							<!-- Button & Comment START-->
							<div style="width:1301px; margin:0 auto; padding:15px; height: 20vh;">
					      		<div style="margin: 0 28%;">
					      			<label>Comment<em>*</em></label>
					      			<html:textarea property="approverComment" name="userForm" styleId="approverComment" rows="5" cols="50" styleClass="textArea"> </html:textarea>
					      		</div>
						    </div>
					    	<div style="width:1301px; margin:0 auto; padding:15px; height: 10vh;">
					      		<div style="margin: 0 40%;">
						      		<logic:empty name="userForm" property="userDtoList"> 
										<html:submit property="method" value="Approve" disabled="true"/>
							      		<html:submit property="method" value="Reject"  disabled="true"/>
							      	</logic:empty>
									<logic:notEmpty name="userForm" property="userDtoList"> 
										<html:submit property="method" value="Approve" styleId="approve"/>
							      		<html:submit property="method" value="Reject"  styleId="reject" />
							      	</logic:notEmpty>
							      	
						      		<html:reset> </html:reset>
							    	<!--<html:button property="back" onclick="jsGoBack();">Back</html:button>-->
								</div>
				        	</div>
				        	<!-- Button & Comment END -->
						</div>
						<!-- main content end -->
					</div>
				</div>
				<!-- Main Content Area END -->
			</div>
			<!-- Container End -->
			
		</html:form>
		<!--footer start-->
		<jsp:include page="/pages/common/footer.jsp" />
		<!-- footer end-->
	</body>
	<script>
	function SetAllCheckBoxes(FormName, AreaID, CheckValue) {
		var objCheckBoxes = document.getElementById(AreaID).getElementsByTagName('input');
	    var countCheckBoxes = objCheckBoxes.length;
	    if (!countCheckBoxes)
	        objCheckBoxes.checked = CheckValue;
	    else
	        for (var i = 0; i < countCheckBoxes; i++)
	            objCheckBoxes[i].checked = CheckValue;
	}

	function formValidate(){

		// TODO: Validation on form submit!
		//var objCheckBoxes = document.getElementById("reviewRequest").getElementsByTagName('input');
		//var countCheckBoxes = objCheckBoxes.length;
		//for (var i = 0; i < countCheckBoxes; i++)
	    //	alert(objCheckBoxes[i].checked);
	}

	$( "form" ).submit(function( event ) {
		var flag = true;
		var msg = "Below field(s) are mandatory:\n";
		var comment 		= $("#approverComment")	.val();
		
		if( comment == ""){
			msg += "-Comment.\n";
			flag = false;
		}
		//
		if (flag) {
			return true;
		}else{
			//alert(msg+db_msg);
			alert(msg);
			event.preventDefault();
		}
	});
	</script>
</html>