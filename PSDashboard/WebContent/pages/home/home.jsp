<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Centops Console</title>
		
		<script src="js/jquery-1.8.2.js" type="text/javascript"></script>
		<script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
		<script src="js/jquery.jtable.js" type="text/javascript"></script>
		
		<script>
		var tableNamesArray = {};
		var tableColumnArray = {};
		var table_1 = '';
		var table_2 = '';
		var table_3 = '';
		var i =0;
		
		
		$(document).ready(function () {
			$.ajax({
				    type: "GET",
				    url: "UserController?action=getTableNames",
				    dataType: "json",
					async:false,
				    //if received a response from the server
		         		success: function(response) {
						tableNamesArray = response;
				 },
				 complete: function(jqXHR, textStatus){
					                }
					      
				});
			
		})
	
		$(document).ready(function () {
		for (i = 0; i < tableNamesArray.length; i++) {
					$.ajax({
						    type: "GET",
						    url: "UserController?action=getTableColumn",
						    data: {tableId: tableNamesArray[i].tblId, tableName: tableNamesArray[i].tblName},
						    dataType: "json",
							async:false,
						    //if received a response from the server
			         		success: function(response) {
				            tableColumnArray = response;
				            if(tableColumnArray[0].tblId == 1){
				            	table_1 = '<table style=\"border: 1px solid black\"">';
				            	table_1 += '<thead><tr><th style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >';
				            	table_1 += '<font size="4"><a href="console.do?text='+ tableColumnArray[0].tblName +'" style="text-decoration:none; color:white;">' + tableColumnArray[0].tblName + '</a></font></th></tr><tr>';
				            for(var j = 0; j < tableColumnArray.length; j++){
						        	table_1 += '<th style=\"border: 1px solid black; color:#400040\">'+tableColumnArray[j].tblColumn +'</th>';
						        }
				            table_1 +='</tr></thead>';
				            }
				            
				            
				            if(tableColumnArray[0].tblId == 2){
				            	table_2 = '<table style=\"border: 1px solid black\"">';
				            	table_2 += '<thead><tr><th style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >';
				            	table_2 += '<font size="4"><a href="console.do?text='+ tableColumnArray[0].tblName +'" style="text-decoration:none; color:white;">' + tableColumnArray[0].tblName + '</a></font></th></tr><tr>';
				            for(var j = 0; j < tableColumnArray.length; j++){
						        	table_2 += '<th style=\"border: 1px solid black; color:#400040\">'+tableColumnArray[j].tblColumn +'</th>';
						        }
				            table_2 +='</tr></thead>';
				            }
				            
				            
				            if(tableColumnArray[0].tblId == 3){
				            	table_3 = '<table style=\"border: 1px solid black\"">';
				            	table_3 += '<thead><tr><th style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >';
				            	table_3 += '<font size="4"><a href="console.do?text='+ tableColumnArray[0].tblName +'" style="text-decoration:none; color:white;">' + tableColumnArray[0].tblName + '</a></font></th></tr><tr>';
				            for(var j = 0; j < tableColumnArray.length; j++){
						        	table_3 += '<th style=\"border: 1px solid black; color:#400040\">'+tableColumnArray[j].tblColumn +'</th>';
						        }
				            table_3 +='</tr></thead>';
				            }
				            
						 },
						 complete: function(jqXHR, textStatus){
							                }
							      
						});
					
					}
		})
		
		$(document).ready(function () {
				$.ajax({
					    type: "GET",
					    url: "UserController?action=listUser",
					    data: {tableQuery: tableNamesArray[0].tblQuery},
					    dataType: "json",
					    //if received a response from the server
		         		success: function(response) {
			
						/* var table = '<table style=\"border: 1px solid black\"">';
						table += '<thead><tr><th style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >';
				        table += '<font size="4">Current Open Incidents </font></th></tr><tr><th style=\"border: 1px solid black; color:#400040\">Incidents</th>';
		                table  += '<th style=\"border: 1px solid black; color:#400040\" >No Of Incidents</th><th style=\"border: 1px solid black; color:#400040\">Critical</th></tr></thead>'; */
		                var table = table_1;
				            for (var i = 0; i < response.length; i++) {
							table += '<tr style=\"border: 1px solid black\">';
							table+= "<td width =\"50%\" style=\"border: 1px solid black\">"  + response[i].priority+ "</td>"
							table+= "<td width =\"50%\" style=\"border: 1px solid black\">" + response[i].cnt+"</td>"
							/* table+= "<td style=\"border: 1px solid black\">" + response[i].critical+ "</td>" */
							table += '</tr>';
		
						}
						table += '</table>';
						$("#ajaxResponse").html(""); 
						$("#ajaxResponse").append(table); 
					 },
					 complete: function(jqXHR, textStatus){
						                    //enable the button 
						                    $('#myButton').attr("disabled", false);
						                }
						      
					}); 
			
		
		})
		
		
		$(document).ready(function () {
				$.ajax({
					    type: "GET",
					    url: "UserController?action=availibility",
					    data: {tableQuery: tableNamesArray[2].tblQuery},
					    dataType: "json",
					    //if received a response from the server
		         		success: function(response) {
			
						/* var table = '<table style=\"border: 1px solid black\"">';
						table += '<thead><tr><th style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >';
				        table += '<font size="4">Current Open Incidents </font></th></tr><tr><th style=\"border: 1px solid black; color:#400040\">Incidents</th>';
		                table  += '<th style=\"border: 1px solid black; color:#400040\" >No Of Incidents</th><th style=\"border: 1px solid black; color:#400040\">Critical</th></tr></thead>'; */
		                var table = table_3;
				            for (var i = 0; i < response.length; i++) {
							table += '<tr style=\"border: 1px solid black\">';
							table+= "<td style=\"border: 1px solid black\">"  + response[i].appName+ "</td>"
							table+= "<td style=\"border: 1px solid black\">" + response[i].apps+"</td>"
							table+= "<td style=\"border: 1px solid black\">" + response[i].ava+ "</td>"
							table += '</tr>';
		
						}
						table += '</table>';
						$("#Availibility").html(""); 
						$("#Availibility").append(table); 
					 },
					 complete: function(jqXHR, textStatus){
						                    //enable the button 
						                    $('#myButton').attr("disabled", false);
						                }
						      
					}); 
			
		
		})
		</script>
	
		<script>
		$(document).ready(function () {
				$.ajax({
					    type: "GET",
					    url: "UserController?action=getComp",
					    data: {tableQuery: tableNamesArray[1].tblQuery},
					    dataType: "json",
					    //if received a response from the server
		         		success: function(response) {
			
						/* var table = '<table style=\"border: 1px solid black\"">';
						table +='<thead>    <tr>       <th  style=\"border: 1px solid black\" colspan =\"3\" align= \"center\" >    <font size=\"4\">Compliance Tracker';
						table +='</font></th>    </tr>        <tr>            <th style=\"border: 1px solid black; color:#400040\"> Name</th>';
						table +=     '<th style=\"border: 1px solid black; color:#400040\">Q2 Requirement</th> <th style=\"border: 1px solid black; color:#400040\">Completed</th></tr>    </thead>    <tbody>'; */
						var table = table_2;
						for (var i = 0; i < response.length; i++) {
							table += '<tr style=\"border: 1px solid black\">';
							table+= "<td style=\"border: 1px solid black\">" + response[i].Name+ "</td>"
							table+= "<td style=\"border: 1px solid black\">" + response[i].Total+ "</td>"
							table+= "<td style=\"border: 1px solid black\">" + response[i].Total_Complaint+ "</td>"					
							table += '</tr>';
		
						}
						/* table +='<tr style=\"border: 1px solid black\"> <td style=\"border: 1px solid black\">DB Patches</td> <td style=\"border: 1px solid black\">791</td> <td style=\"border: 1px solid black\">268</td> </tr> ';
						table +='<tr style=\"border: 1px solid black\"> <td style=\"border: 1px solid black\">ASPR</td> <td style=\"border: 1px solid black\">2</td> <td style=\"border: 1px solid black\">5</td> </tr>';
						table +='<tr style=\"border: 1px solid black\"> <td style=\"border: 1px solid black\">Others</td> <td style=\"border: 1px solid black\">0</td> <td style=\"border: 1px solid black\">0</td> </tr> </tbody>'; */
						table += '</table>';
						$("#compResponse").html(""); 
						$("#compResponse").append(table); 
					 },
					 
						      
					}); 
					
		})
		</script>
	
	
	</head>
	
	<body style="background: linear-gradient(#B0B0B0 , #F0F0F0 ); overflow-x: hidden ">
		<html:form name="homeForm"  method="post"  action="/home.do" type="com.techm.psd.home.form.HomeForm" enctype="multipart/form-data">
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			<!-- Header START -->
				<jsp:include page="/pages/common/header.jsp" />
			<!-- Header End -->
			<hr />
	
	    	<!-- Page Content -->
	    	<div class="container" style="height: 79vh;"> 
	 			<div class="col-md-12" >
					<div class="panel-group" style = "border: none; opacity: 1; " >
	    				<div class="panel panel-default" style ="background-color: rgba(128,128,128, 0); border: 0px; opacity: 1">
	      					<div class="panel-heading" style ="background-color: rgba(128,128,128, 0); opacity: 1"  >
	      						<div class = "col-xs-50 col-sm-0" style = "float: right">
	 								<div style="float: right;">
	 									<a style = " font-family: 'Arial', cursive; font-weight:400; font-weight: bold; font-size:16px;" href="console.do">Launch Centops</a>
	 								</div>
	 							</div>
	        					<div style="">	
		        					<h4 class="panel-title" style ="background-color: rgba(128,128,128, 0)">
		          						<a style = " font-family: 'Times New Roman', cursive; font-weight:400; font-weight: bold; color:#566573; font-size:16px; text-decoration: none" data-toggle="collapse"  href="#collapse1">  Applications Configured in Console <span class="caret" style = "border-width: 6px"> </span> </a>
		        					</h4>
		        				</div>
	       					</div>
       					  	<div id="collapse1" class="panel-collapse collapse " >
					        	<div class=" panel panel-default" style ="background-color: rgba(128,128,128, 0); opacity: 1; border: 0px" >  
					        		<table style ="background-color: rgba(128,128,128, 0); opacity: 1;" >
					        			<tr>
					        				<logic:iterate id="appDTOList" name="homeForm" property="appDTOList" >
					                			<td> 
					                				<bean:write name="appDTOList" property="appName" />
					                			</td>
					        				</logic:iterate>
					        			</tr>
					        		</table>
					        	</div>
					      	</div>
						</div>
	  				</div>
	 			</div>
	        	
	        
	
	
	
	        <div class="row"> <div class="col-md-12" > &nbsp; <br/> </div> </div>
	
	        <!-- Call to Action Well -->
	        
	
	        <!-- Content Row -->
	                    
	            <table style="background-color: rgba(192,192,192, 0);  width: 98%;">
	            <tr>
	            
	            <td width ="30%" valign="top">
	            
	            
	         
	    <div class = "PersonTableContainer">
	    <div id="ajaxResponse"></div>
	</div>
	            
	            </td>
	            
	            <td width ="30%" valign="top">
	            <!-- /.col-md-4 -->
	            
	            
	 <div class = "PersonTableContainer">
	<div id="compResponse"></div>
	</div>            
	            </td>
	            <!-- /.col-md-4 -->
	            <td width ="30%" valign="top">
	            
	               
	    <div class = "PersonTableContainer">
	    <div id="Availibility"></div>
	       </div>
	</td>
	            <!-- /.col-md-4 -->
	            </tr>
	            </table>
	        <!-- /.row -->
	
	        
		        <br/>
			</div>
				<!--footer start-->
				<jsp:include page="/pages/common/footer.jsp" />
				<!--footer end-->
			
		</html:form>
	</body>
	<link href="css/landingPage.css" rel="stylesheet" />
	<script>
		function openConsole(){
			$('#mic').attr('src', 'images/mic-disable.png');
			if (window.hasOwnProperty('webkitSpeechRecognition')) {
		        var recognition = new webkitSpeechRecognition();
		        recognition.continuous = false;
		        recognition.interimResults = false;
		        recognition.lang = "en-US";
		        recognition.start();
		        
		        recognition.onresult = function(e) {
		            var value = e.results[0][0].transcript;
		            $('#mic').attr('src', 'images/mic-enable.png');
		            window.location.href = "console.do?text="+value.toLowerCase();
		        };
		        recognition.onerror = function(e) {
		            recognition.stop();
		            $('#mic').attr('src', 'images/mic-enable.png');
		        }
		    }else{
		        alert("Your browser does not support 'Speech Recognition'");
		        $('#mic').attr('src', 'images/mic-enable.png');
		    }
		}

	</script>
</html>