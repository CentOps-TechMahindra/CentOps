<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - View Workflow</title>
	</head>
	
	<body>
		<html:form name="workflowForm" method="post" action="/workflow.do?method=editWorkflow" type="com.techm.psd.workflow.form.WorkflowForm" enctype="multipart/form-data">
			<html:hidden name="workflowForm" property="workflowId"/>
			<html:hidden name="workflowForm" property="appName" styleId="appName"/>
			
			<input type="hidden" id="levelId" value="${USERSESSION.levelId}" name="levelId"/>
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			
			<!-- Sidebar Tree Structure and Search Start -->
			<jsp:include page="/pages/common/sidebar.jsp" />
			<!-- Sidebar Tree Structure and Search End -->
				
			<!-- Container Start -->
			<div class="container-fluid">
			  	<div class="main-container">
			    	
			    	<!-- Main Content Area Start -->
				  	<h3 class="page-header" style="display: none;">View Workflow</h3>
				  	<!-- Main Content Area Start -->
				  	
			    	<div class="panel panel-danger">
				      	<div class="panel-heading">
				        	<div class="panel-title pull-left">View Workflow</div>
				        	<div class="clearfix"></div>
					        
				      	</div>
			  	
			  			<div>
					  		<br />
					    	
					    	<table>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Workflow Id   	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="workflowId" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Workflow Name   
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="workflowName" /><br />  
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Workflow Type   	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="workflowType" /><br />
							    	</td>
							    </tr>
							    <tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Application   	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="appName" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Username   		
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="username" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Password   		
							    	</td>
							    	<td>
							    		<logic:present name="workflowForm" property="password">●●●●●</logic:present>
							    		<logic:notPresent name="workflowForm" property="password">-</logic:notPresent>
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Authentication Token	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="authToken" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Server        	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="server" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Port        	
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="port" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Job Name        
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="jobName" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Build Type      
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="buildType" /><br />
							    	</td>
							    </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Node Name
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="buildParameter" /><br />
							    	</td>
							    </tr>
							    <tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				String Parameter Name
							    	</td>
							    	<td>
							    		<logic:iterate id="param" name="workflowForm" property="parametersName" >
							    			<bean:write name="param"/><br />
							    		</logic:iterate>
							    	</td>
							    </tr>
							    <tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Profile     
						    		</td> 
						    		<td>
						    			<logic:iterate id="profileList" name="workflowForm" property="profileList">
								               <bean:write name="profileList" property="profileName"/><br />
								        </logic:iterate>
									</td>
						        </tr>
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Description     
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="desc" /><br />
						    		</td>
							    </tr>
						    
								<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			<div style="padding-left:50Px; padding-top:20px; padding-bottom:10px; font-weight: bold">View Report DB Details</div>      
						    		</td>
						    	</tr>
						    	
					      		<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
					      				Database Type     
							    	</td>
							    	<td>
							    		<bean:write name="workflowForm" property="db_type" /><br />
						    		</td>
						    	</tr>
						    	
						    	<tr style="padding-left: 1px;">
					      			<td style="padding-left: 10px;">
					      				Username    
					      			</td> 
					      			<td>
							    		<bean:write name="workflowForm" property="db_username" /><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Password     
						    		</td> 
						    		<td>
							    		<logic:present name="workflowForm" property="db_password">●●●●●</logic:present>
							    		<logic:notPresent name="workflowForm" property="db_password">-</logic:notPresent>
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Hostname 		 
						    		</td>	 
						    		<td>
							    		<bean:write name="workflowForm" property="db_hostname" /><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Port 		 
						    		</td>
						    		<td>
							    		<bean:write name="workflowForm" property="db_port" /><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			SID	
						    		</td>
						    		<td>
							    		<bean:write name="workflowForm" property="db_sid" /><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Table Name	
						    		</td>
						    		<td>
							    		<bean:write name="workflowForm" property="db_table_name" /><br />
						    		</td>
						    	</tr>
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			Query	
						    		</td>
						    		<td>
							    		<bean:write name="workflowForm" property="db_query" /><br />
						    		</td>
						    	</tr>
						    	
						    	
						    	
						    	<!--  -->
						    	<tr style="padding-left: 1px;">
						    		<td style="padding-left: 10px;">
						    			<div style="padding-left:50Px; padding-top:20px; padding-bottom:10px; font-weight: bold">
						    				Fix IT: Known Error's 
						    			</div>      
						    		</td>
						    	</tr>
						    	<!--  -->
						    </table>
					    	<table border="1" cellspacing="0" id="tableId" class="table table-bordered" style="width: 60%; margin-left: 18%;">
						  		<thead>
							  		<tr>
							    		<th>Error Name				</th>
									    <th>Error Code				</th>
									    <th>Fix IT! Workflow Name	</th>
									</tr>
								</thead>
								<tbody>	
							  		<logic:iterate id="obj" name="workflowForm" property="fixItDTOList" indexId="index">
										<tr>
										    <td><bean:write name="obj" property="errorName" />			</td>
										    <td><bean:write name="obj" property="errorCode" />			</td>
										    <td><bean:write name="obj" property="fixitWorkflowName" />	</td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
					    	
					    	<br />
					    	
					    	<div style="padding-left: 10px;">
							    <!--<button name="Add Workflow" onclick="addWorkflow();">Add Workflow</button>--> 
						    	<html:submit value="EDIT" ></html:submit>
						    	<html:button property="back" onclick="jsGoBackToWorkflowHome();">Back</html:button>
						    	<html:button property="home" onclick="jsGoBackToWorkflowHome();">Home</html:button>
						    </div>
					    </div>
				  	</div>
				</div>
				<!-- Main Content Area End -->
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
			
			
			<!-- Javascript: START -->
			<script type="text/javascript" src="js/workflow/viewValidation.js"></script>
			<!-- Javascript: END -->
		</html:form>
		
		<script>
			function addWorkflow(){
				document.workflowForm.action = "workflow.do?method=addWorkflow";
		        document.workflowForm.submit();
		    }
			$( "form" ).submit(function( event ) {
				var userLevelId = document.getElementById("levelId").value;
				
				if (userLevelId == 10) {
					return
				}else{
					alert('You are not authorized!');
					event.preventDefault();
				}
			});
		</script>
	</body>
</html>