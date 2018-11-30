<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Edit Workflow</title>
	</head>
	
	<body onload="loadFixitWorkflow();">
		<html:form name="workflowForm"  method="post"  action="/workflow.do?method=updateWorkflow" type="com.techm.psd.workflow.form.WorkflowForm" enctype="multipart/form-data">
			<html:hidden property="workflowId"	name="workflowForm" />
			<html:hidden name="workflowForm" property="appName" styleId="appName"/>
			<html:hidden property="jsonArr" 	name="workflowForm" styleId="jsonArr"/>
			<html:hidden property="jsonWorkflowList" 	name="workflowForm" styleId="jsonWorkflowList"/>
			
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			
			<!-- Sidebar Tree Structure and Search Start -->
			<jsp:include page="/pages/common/sidebar.jsp" />
			<!-- Sidebar Tree Structure and Search End -->
				
			<!-- Container Start -->
			<div class="container-fluid">
			  	<!-- Main Content Area Start -->
			  	<div class="main-container">
		    	
		    	<!-- Main Content Area Start -->
			  	<h3 class="page-header" style="display: none;">Edit Workflow</h3>
			  	
			    	<div class="panel panel-danger">
				      	<div class="panel-heading">
				        	<div class="panel-title pull-left">Edit Workflow</div>
				        	<div class="clearfix"></div>
					        
				      	</div>
				      	
				      	<div>
				      		<br />
					    	
					    	<table>
					      		<tr style="padding-left: 1px;">
					      			<td class="clear paddingLable">
								     	Workflow Id   	
								    </td> 
					      			<td>
					      				<bean:write name="workflowForm" property="workflowId" /><br />
								    </td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    	    	Workflow Name<b><em style="color: red">*</em></b>   
						    	    </td> 
					      			<td>
					      				<html:text tabindex="10" name="workflowForm" property="workflowName" styleId="workflowName" maxlength="100" onchange="$(this).checkWorkflowName();" styleClass="textFld valid textbox"></html:text><br />
										<div id="tickIcon" class=""  style="display: none;"><a href="#"><span class="pickThis">&nbsp;</span></a>Valid Workflow Type Name.</div>
          								<div id="crossIcon" class=""  style="display: none;"><a href="#"><span class="removeThis">&nbsp;</span></a>Workflow Type Name already exist.</div>
         								<input type="hidden" name="isValidWorkflowName" id="isValidWorkflowName" value="true"/>
						      		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Workflow Type<b><em style="color: red">*</em></b>     
						    		</td> 
					      			<td>
					      				<html:select tabindex="2" name="workflowForm" property="selWTypeId" title="Request Title" styleId="wTypeId" styleClass="textFld valid textbox">
				                        	<html:option value="">-- Select --</html:option>
				                        	<html:optionsCollection name="workflowForm" property="workflowTypeList" label="workflowType" value="workflowTypeId"/>
				                      	</html:select><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Application<b><em style="color: red">*</em></b>     
						    		</td> 
					      			<td>
					      				<html:select tabindex="3" name="workflowForm" property="selAppId" title="Request Title" styleId="appId" styleClass="textFld valid textbox">
				                        	<html:option value="">-- Select Application --</html:option>
				                        	<html:optionsCollection name="workflowForm" property="appList" label="appName" value="appId"/>
				                      	</html:select><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Username<b><em style="color: red">*</em></b> 		
						    		</td> 
					      			<td>
					      				<html:text tabindex="4" name="workflowForm" property="username" styleId="username" maxlength="50" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Password<b><em style="color: red">*</em></b> 	
						    		</td> 
					      			<td>
					      				<html:password tabindex="5" name="workflowForm" property="password" styleId="password" maxlength="50" styleClass="textFld valid textbox"/><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Authentication Token<b><em style="color: red">*</em></b>
						    		</td> 
					      			<td>
					      				<html:text tabindex="6" name="workflowForm" property="authToken" styleId="authToken" maxlength="50" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Server<b><em style="color: red">*</em></b>	        
						    		</td> 
					      			<td>
					      				<html:text tabindex="7" name="workflowForm" property="server" styleId="server" maxlength="200" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Port<b><em style="color: red">*</em></b>	       
						    		</td> 
					      			<td>
					      				<html:text tabindex="8" name="workflowForm" property="port" styleId="port" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Job Name<b><em style="color: red">*</em></b>        
						    		</td> 
					      			<td>
					      				<html:text tabindex="9" name="workflowForm" property="jobName" styleId="jobName" maxlength="100" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Build Type<b><em style="color: red">*</em></b>     
						    		</td> 
					      			<td>
					      				<html:radio tabindex="10" name="workflowForm" property="buildType" value="build" >Non-Paramerterized</html:radio>
								    	<html:radio tabindex="11" name="workflowForm" property="buildType" value="buildWithParameters">Paramerterized</html:radio><br /> 
									</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Node Name 
						    		</td> 
					      			<td>
					      				<html:text tabindex="12" name="workflowForm" property="buildParameter" styleId="buildParameter" maxlength="200" styleClass="textFld valid textbox"></html:text><br />
									</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			String Parameter Name
						    		</td>
						    		<td align="left" style="">
						    		</td>
						    	</tr>
					    		<logic:iterate id="param" name="workflowForm" property="parametersName" >
					    			<tr style="padding-left: 1px;">
							    		<td class="clear paddingLable"></td>
							    		<td align="left">
							    			<input tabindex="13" value="<bean:write name="param"/>" name="parametersName" class="textFld valid textbox"/>
							    			<a class = "glyphicon glyphicon-minus" onclick="window.deleteParentTr(this);"></a>
							    			<!-- <a class = "glyphicon glyphicon-plus" onclick='window.addRow(this);'></a> -->
							    		</td>
							    	</tr>
							    </logic:iterate>
							    <tr style="padding-left: 1px;">
						    		<td class="clear paddingLable"></td>
						    		<td align="left">
						    			<input tabindex="14" name="parametersName" class="textFld valid textbox"/>
						    			<!-- <a class = "glyphicon glyphicon-minus" onclick="window.deleteParentTr(this);"></a>-->
						    			<a class = "glyphicon glyphicon-plus" onclick='window.addRow(this);'></a>
						    			<br />
						    		</td>
						    	</tr>
							    <br />
						    	
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Profile<b><em style="color: red">*</em></b>     
						    		</td> 
						    		<td>
						    			<html:select tabindex="15" name="workflowForm" multiple="true" property="selProfileIds" title="Request Title" styleId="profileIds" styleClass="textFld valid textbox">
				                        	<html:optionsCollection name="workflowForm" property="profileList" label="profileName" value="profileId"/>
				                        </html:select><br />
						              	<em style="font: italic;">* You have <b><bean:write scope="session" name="USERSESSION" property="profileName" /></b> Access</em>
						           </td>
						        </tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    		    	Description<b><em style="color: red">*</em></b>    
						    		</td> 
					      			<td>
					      				<html:textarea tabindex="16" name="workflowForm" property="desc" styleId="desc" styleClass="textFld valid textbox textArea"></html:textarea><br />
					    			</td>
					    		</tr>
					    	
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			<div style="padding-left:50Px; padding-top:20px; padding-bottom:10px; font-weight: bold">Edit Report DB Details</div>      
						    		</td>
						    	</tr>
						    	
					    		<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Database Type	        
						    		</td>
						    		<td>
						    			<html:text tabindex="17" name="workflowForm" property="db_type" styleId="db_type" maxlength="50" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
					      			<td class="clear paddingLable">
					      				Username    
					      			</td> 
					      			<td>
					      				<html:text tabindex="18" name="workflowForm" property="db_username" styleId="db_username" maxlength="100" styleClass="textFld valid textbox"></html:text><br />
					      			</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Password     
						    		</td> 
						    		<td>
						    				<html:password tabindex="19" name="workflowForm" property="db_password" styleId="db_password" maxlength="100" styleClass="textFld valid textbox"/><br />
						            </td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Hostname 		 
						    		</td>	 
						    		<td>
						    			<html:text tabindex="20" name="workflowForm" property="db_hostname" styleId="db_hostname" maxlength="200" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Port 		 
						    		</td>
						    		<td>
						    			<html:text tabindex="21" name="workflowForm" property="db_port" styleId="db_port" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			SID	
						    		</td>
						    		<td>
						    			<html:text tabindex="22" name="workflowForm" property="db_sid" styleId="db_sid" maxlength="100" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Report Table	
						    		</td>
						    		<td>
						    			<html:text tabindex="23" name="workflowForm" property="db_table_name" styleId="db_table_name" maxlength="100" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			Query	
						    		</td>
						    		<td>
						    			<html:text tabindex="24" name="workflowForm" property="db_query" styleId="db_query" maxlength="500" styleClass="textFld valid textbox"></html:text><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			<a href="javascript:void(0);" onclick="$(this).checkConnection();" styleClass="textFld valid textbox">Check DB Connection</a>
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
						    		<td class="clear paddingLable">
						    			<div style="padding-left:50Px; padding-top:20px; padding-bottom:10px; font-weight: bold">
						    				Fix IT: Known Error's 
						    			</div>      
						    		</td>
						    		<td>
						    			<button type="button" class='addmore glyphicon glyphicon-plus'></button>
						    			<button type="button" class='delete glyphicon glyphicon-minus'></button>
										
						    		</td>
						    	</tr>
						    </table>
					    	<table border="1" cellspacing="0" id="tableId" class="table table-bordered" style="width: 60%; margin-left: 18%;">
						  		<thead>
							  		<tr>
							    		<th><input class='check_all' type='checkbox' onclick="select_all()"/></th>
							    		<th>S. No</th>
							    		<th>Error Name</th>
									    <th>Error Code</th>
									    <th>Fix IT! Workflow Name</th>
									</tr>
								</thead>
								<tbody></tbody>
							</table>
					    	
					    	<br />
					    	
					    	<div style="padding-left: 10px;">
							    <html:submit tabindex="25" value="Update" />
						    	<html:button tabindex="26" property="back" onclick="jsGoBack();">Back</html:button>
						    	<html:button tabindex="27" property="home" onclick="jsGoBackToWorkflowHome();">Home</html:button>
					    	</div>
				  		</div>
				  	</div>
				</div>
		  		<!-- Main Content Area End -->
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
			
			<jsp:include page="/pages/common/loading.jsp" />
			
			<!-- Javascript: START -->
			<script type="text/javascript" src="ajax/reportDBAjax.js"></script>
			<script type="text/javascript" src="js/workflow/fixitWorkflow.js"></script>
			<script type="text/javascript" src="js/workflow/workflowValidation.js"></script>
			<script type="text/javascript" src="ajax/workflowNameValidationAjax.js"></script>
		
			<!-- Javascript: END -->
		</html:form>
		
	</body>
	<script>
		$(function() {
		    window.deleteParentTr = function(theThis) {
		        $(theThis).closest('tr').remove();
		    };
		    window.addRow = function(theThis) {
		        var
		            newrow = $(	'<tr style="padding-left: 1px;">'+
				            		'<td align="left" style="">'+
					            		'<input value="" name="parametersName" class="textFld valid textbox"/>'+
					            		'<a class = "glyphicon glyphicon-minus" onclick="window.deleteParentTr(this);"></a>'+
						            '</td>'+
					            '</tr>');
		        $(theThis).last('tr').after(newrow);
		     };

		    
		});
	</script>
</html>