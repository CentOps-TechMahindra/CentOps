/**
 * START FLOW AJAX function: START
 */
jQuery.fn.extend({
	startFlow: function (rowId, workflowId, workflowName, buildType) {
		if(buildType== "build"){
			$("#runDiv"+rowId).confirm({
			    title:"Execution Confirmation Box!",
			    text:"Please confirm if you want to Execute the <strong>"+workflowName+"</strong> ?",
			    confirm: function(button) {
					//$.fn.getNodesDetailsAJAX(rowId, workflowId);
					$.fn.startFlowAjax(rowId, workflowId);
			    },
			    cancel: function(button) {},
			    confirmButton: "Execute",
			    cancelButton: "Cancel"
			});
		}else{
			$.fn.getNodesDetailsAJAX(rowId, workflowId, workflowName);
		}
	},
	
	getNodesDetailsAJAX: function(rowId, workflowId, workflowName){
		$.ajax({
		    url: "NodesDetailsAJAX",
		    type: "POST",
		    data: {workflowId:workflowId},
		    dataType : 'json', // Returns json object.
		    beforeSend: function() {
		    	$("#loading").css("display","block");			// Befor send showing the loading image...
		    	$('html, body').css({							// Block scrolling!
		    	    'overflow': 'hidden',
		    	    'height': '100%'
		    	})
		  	},
		  	complete: function(){
		  		$("#loading").css("display","none");			// After complete hiding the loading image...
		  		$('html, body').css({							// Unblock scrolling!
		  		    'overflow': 'auto',
		  		    'height': 'auto'
		  		})
		  	},
		  	success: function(jsonObj){
		  		var jsonObjList = jsonObj.jList;
		  		var paramList 	= jsonObj.paramList;
		  		
		  		//ADD WORKFLOW NAME IN OVERLAY HEADER: START
		  		$('#overlayHeader').text(workflowName);
		  		//ADD WORKFLOW NAME IN OVERLAY HEADER: END
		  		
		  		//ITERATE JSON LIST AND CREATE ONLINE NODES <tr> & OFFLINE <tr> : START 
				var onlineNodesTR = "";
				var offlineNodesTR="";
				
				var data = jsonObjList;
				for(var i in data){
					var displayName = jsonObjList[i].displayName;
					var offline		= jsonObjList[i].offline;
					if(offline == false){
						onlineNodesTR+=	  
							'<tr>'+
								'<td><input type="checkbox" name="selRequests" value="'+displayName+'" /></td>'+
								'<td>'+displayName+'</td>'+
							'</tr>';
					}else{
						offlineNodesTR+=	  
							'<tr>'+
								'<td>'+displayName+'</td>'+
							'</tr>';
					}
				}
				//ITERATE JSON LIST AND CREATE ONLINE NODES <tr> & OFFLINE <tr> : END 
				
				//ONLINE NODES TABLE: START
				var onlineText='<table class="table table-bordered">'+
						'<thead>'+
						  '<tr>'+
							'<th><input type="checkbox" name="selRequests" value="" /></th>'+
							'<th>Online Nodes</th>'+
						  '</tr>'+
						'</thead>'+
						'<tbody>'+
						onlineNodesTR+
						'</tbody>'+
					'</table>';
				$('#onlineNodesTableDiv').html(onlineText);
				//ONLINE NODES TABLE: END
				
				//OFFLINE NODES TABLE: START
				var offlineText='<table class="table table-bordered">'+
						'<thead>'+
						  '<tr>'+
							'<th>Offline Nodes</th>'+
						  '</tr>'+
						'</thead>'+
						'<tbody>'+
						offlineNodesTR+
						'</tbody>'+
					'</table>';
				$('#offlineNodesTableDiv').html(offlineText);
				//OFFLINE NODES TABLE: END
				
				
				//ADD Parameter List: START
				var paramText=	'<table class="table table-bordered">'+
									'<thead>'+
								  		'<tr>'+
											'<th></th>'+
											'<th>Parameter Name</th>'+
											'<th>Parameter Value</th>'+
										'</tr>'+
									'</thead>'
									'<tbody>';
				
					for(var i = 0; i < paramList.length; i++) {
			  		    var obj = paramList[i];
										
						paramText +=	'<tr>'+
											'<td><input type="checkbox" name="selParam" value="'+obj+'" /></td>'+
											'<td>'+obj+'</td>'+
											'<td><input type="text" id="val'+obj+'"></td>'+
										'</tr>';
					
			  		}
		  				paramText +='</tbody>'+
								'</table>';
		  		if(paramList.length > 0){
		  			$('#paramTableDiv').html(paramText);
		  		}
				//ADD Parameter List: END
		  		
				//ADD BUTTON
				var exeOverlayButton="";
					exeOverlayButton +=	"<button type='button' class='btn btn-default' data-dismiss='modal'>Close</button>"+
										"<button type='button' class='btn btn-primary' onclick=\"jQuery('#theGrid').startFlowAjax('"+rowId+"','"+workflowId+"');\">Execute</button>";
				$('#exeOverlayButton').html(exeOverlayButton);
				
				//SHOW executionOverlay.jsp
				$("#executionOverlay").modal('show');
				
		    }
		});
	},
	
	startFlowAjax: function(rowId, workflowId){
		$("#executionOverlay").modal('hide');			//Hide executionOverlay.jsp
		
		//Select Execution Type
		var selExecutionOn = $('input[name=selExecutionOn]:checked', '#executionOverlay').val(); //Default or Selected Nodes.
		
		//FETCH SELECTED NODES
		var selNodes = [];
		$.each($("input[name='selRequests']:checked"), function(){            
        	selNodes.push($(this).val());
        });
		
		//FETCH SELECTED PARAMETERS VALUES
		var paramStr = "";
		$.each($("input[name='selParam']:checked"), function(){            
			var key = $(this).val();
			var val = $('#val'+key).val();
			paramStr +=	key+'='+val+'&';
		});
		
		//CALL START WORKFLOW AJAX
		$.ajax({
		    url: "WorkflowStartAJAX",
		    type: "POST",
		    data: {workflowId:workflowId, selNodes:selNodes+"", paramStr:paramStr+"", selExecutionOn:selExecutionOn, executedFrom:"dashboard"},
		    dataType : 'json', // Returns json object.
		    beforeSend: function() {
	    	    $("#statusOffline"+rowId).removeAttr("class");
				$("#statusOffline"+rowId).addClass("away");
				
				//DISABLE START BUTTON UNTIL FLOW EXECUTION COMPLETE
				$("#runDiv"+rowId).hide();
				$("#runHideDiv"+rowId).show();
				
		    },
		  	complete: function(){},
		  	success: function(jsonObj){
		  		
		  		//ENABLE START FLOW EXECUTION BUTTON AFTER FLOW EXECUTION COMPLETE
		  		$("#runDiv"+rowId).show();
				$("#runHideDiv"+rowId).hide();
				//
				
				var buildStatus	= jsonObj.status;
				if(buildStatus == "SUCCESS"){
					$("#statusOffline"+rowId).removeAttr("class");
					$("#statusOffline"+rowId).addClass("online");
				}else if(buildStatus == "FAILURE"){
					$("#statusOffline"+rowId).removeAttr("class");
					$("#statusOffline"+rowId).addClass("busy");
				}else if(buildStatus == "INPROGRESS"){
					$("#statusOffline"+rowId).removeAttr("class");
					$("#statusOffline"+rowId).addClass("away");
				}else if(buildStatus == "InUse"){
					$("#statusOffline"+rowId).removeAttr("class");
					$("#statusOffline"+rowId).addClass("offline");
					alert("Workflow is in use by "+jsonObj.username+" please try again later!");
				}else if(buildStatus == "DISABLED"){
					$("#statusOffline"+rowId).removeAttr("class");
					$("#statusOffline"+rowId).addClass("offline");
					alert("Workflow is disabled. please contact support team.");
				}
		    },
		    error:function(msg){
		        alert("Workflow can't be executed at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	},
	
	workflowHistory: function (rowId, workflowId) {
		var action		= 'START_FLOW_HISTORY';
		$.ajax({
		    url: "WorkflowHistoryAJAX",
		    type: "POST",
		    data: {action:action, workflowId:workflowId},
		    dataType : 'json', // Returns json object.
		    beforeSend: function() {
		    	$("#loading").css("display","block");			// Befor send showing the loading image...
		    	$('html, body').css({							// Block scrolling!
		    	    'overflow': 'hidden',
		    	    'height': '100%'
		    	})
		  	},
		  	complete: function(){
		  		$("#loading").css("display","none");			// After complete hiding the loading image...
		  		$('html, body').css({							// Unblock scrolling!
		  		    'overflow': 'auto',
		  		    'height': 'auto'
		  		})
		  	},
		  	success: function(jsonObj){
		        $.fn.showHistoryOverlay(jsonObj,rowId, action);						// On Success: Calling loadLastReleaseDataCallbackFunction function
		    },
		    error:function(msg){
		        alert("Unable to show history at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	},
	
	jenkinsDetailHistory: function (workflowId, startFlowId, historyId) {
		var text = "";
		var action		= 'JENKINS_HISTORY';
		$.ajax({
		    url: "WorkflowHistoryAJAX",
		    type: "POST",
		    data: {action:action, workflowId:workflowId, startFlowId:startFlowId},
		    dataType : 'json', // Returns json object.
		    success: function(jsonObj){
		        text = $.fn.getDetailHistory(jsonObj);						
		  		document.getElementById(historyId).innerHTML = text;
		  		//document.getElementById(reportHistoryId).innerHTML = jsonObj.resultStr;
		  		return text;
		  	},
		    error:function(msg){
		        alert("Unable to show history at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	},
	
	reportDetailHistory: function (workflowId, startFlowId, historyId) {
		var text = "";
		var action		= 'REPORT_HISTORY';
		$.ajax({
		    url: "WorkflowHistoryAJAX",
		    type: "POST",
		    data: {action:action, workflowId:workflowId, startFlowId:startFlowId},
		    dataType : 'json', // Returns json object.
		    success: function(jsonObj){
		        //text = $.fn.getDetailHistory(jsonObj);						
		  		//document.getElementById(historyId).innerHTML = text;
		  		document.getElementById(historyId).innerHTML = jsonObj.resultStr;
		  		//return text;
		  	},
		    error:function(msg){
		        alert("Unable to show history at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	},
	
	workflowEdit: function (workflowId) {
		window.location.href = 'workflow.do?method=editWorkflow&workflowId='+workflowId;
	}
	
});
//AJAX function: END

/**
 * AJAX Callback Function : START
 */
(function( $ ){
	
	//HISTORY OVARLAY AND DETAILS HISTORY: START
	$.fn.showHistoryOverlay = function(jsonObj,rowId, action) {
		var text = ""; 
		if(jsonObj.result == "FAIL"){
			text = "Workflow History is not available at this time, please try again later!";
		}else if(jsonObj.result == "SUCCESS"){
			var jsonObjList = jsonObj.jsonList; 
			
			text =  "<div class='exportTools'>" +
						"<a href = '#' onClick = 'exportToExcelHistory("+jsonObjList[0].workflowId+", \""+action+"\");' class='exportExcel' title='Export to Excel'></a>" +
						"<a href = '#' onClick = 'exportToPdfHistory("+jsonObjList[0].workflowId+", \""+action+"\");' class='exportPDF' title='Export to PDF'></a>" +
						//"<a href='javascript:demoFromHTML();' class='exportCSV'></a>" +
						//"<a href = 'javascript:void(0);' onClick = $('#overlay').tableExport({type:'excel',escape:'false'}); class='exportExcel'></a>" +
						//"<a href='javascript:demoFromHTML();' class='exportPDF'></a>" +
						//"<a href='javascript:demoFromHTML();' class='exportGraph'></a>" +
					"</div> " +
					"<div id='content' style='padding-top:25px; width:1290px'>" +
						"<ul style='list-style-type:none;'>"+
							"<li class='current'>" +
								"<table style='width:1250px;'>" +
									"<tbody style='border: 1px solid black; border-collapse: collapse;'>" +
										"<tr>" +
											"<th style='width:200px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>Executed From	</th>" +
											"<th style='width:200px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>CentOps Console ID	</th>" +
											"<th style='width:200px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>Executed By	</th>" +
											"<th style='width:250px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>Date & Time	</th>" +
											"<th style='width:200px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>Jenkins Status	</th>" +
											"<th style='width:200px; padding-left: 10px;background-color: #e35b5a;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:#FFF'>Report Status	</th>" +
										"</tr>" +
									"</tbody>" +
								"</table>" +
							"</li>";
							var data = jsonObjList;
							for(var i in data){
								var executedFrom 	= jsonObjList[i].executedFrom;
								var startFlowId 	= jsonObjList[i].startFlowId;
								var workflowId		= jsonObjList[i].workflowId;
							    var startTime		= jsonObjList[i].startTime;
							    var startBy 		= jsonObjList[i].startBy;
							    var jenkinsStatus	= jsonObjList[i].jenkinsStatus;
							    var reportStatus	= jsonObjList[i].reportStatus;
							    
							    var viewStartFlowId = "-";
								if(startFlowId != 0){viewStartFlowId = startFlowId;}
								
							text = text+
							"<li class='current'>" +
								"<table style='width:1250px;'>" +
									"<tbody style='border: 1px solid black; border-collapse: collapse;'>" +
										"<tr>" +
											"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'>"+executedFrom		+"</td>" +
											"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'>"+viewStartFlowId	+"</td>" +
											"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'> "+startBy			+"</td>" +
											"<td style='width:250px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'> "+startTime		+"</td>" +
											"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'> "+
												"<a href='#' onclick='toggleJenkinsHistory(jenkinsHistory"+startFlowId+", "+workflowId+", "+startFlowId+");'>"+jenkinsStatus+"<b class = 'caret'></b></a>" +
											"</td>";
											if(reportStatus != "NA"){
												text =  text+"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'><a href='#' onclick='toggleReportHistory(reportHistory"+startFlowId+", "+workflowId+", "+startFlowId+");'>"+reportStatus+"<b class = 'caret'></b></a></td>";
											}else{
												text =  text+"<td style='width:200px; padding-left: 10px;padding: 5px;border: 1px solid #ccc;'>"+reportStatus+" </td>";
											};
							text =  text+"</tr>" +
									"</tbody>" +
								"</table>" +
								"<div class='sub_cat_box' id='jenkinsHistory"+startFlowId+"' style='display: none; margin: 0 5px 5px 5px; text-align: center; background-color: #eee;'>" +
									"Loading..." +
								"</div>" +
								
								"<div class='sub_cat_box' id='reportHistory"+startFlowId+"' style='display: none; margin: 0 5px 5px 5px; text-align: center; background-color: #eee;'>" +
									"Loading..." +
								"</div>" +
							
							"</li>";
							}
						
							text =  text+"</ul>" +
					"</div>";
		}
		$('#msg').html(text);
		
		var title = "Workflow Execution History!";
		if(action == "LAST_START_FLOW_HISTORY"){
			title="Workflow Report!";
		}
		
		$( "#dialog" ).dialog({
			width: "90%",
            height: "auto",
            title: title,
            modal: true,
            buttons: [
                      {
			              text: "OK",
			              click: function() {
			                $( this ).dialog( "close" );
			              }
			           }
			          ]
		});
		return this;
	},
	
	// TOGGEL between childs and parent using ul and li: START
	//Commenting below code and li toggle code is added in menu.jsp which is included in dashboard.jsp page.
	//$('li.dropdown').click(function() {
	//   $('li.dropdown').not(this).find('ul').hide();
	//   $(this).find('ul').toggle();
	//});
	// TOGGEL between childs and parent using ul and li: END
	
	//Get and appand the detail history: START
	$.fn.getDetailHistory = function(jsonObj) {
		var text = "Test"; 
		if(jsonObj.result == "FAIL"){
			text = "<div style='background-color:#efe;'>Workflow History is not available at this time, please try again later!</div>";
		}else if(jsonObj.result == "SUCCESS"){
			var jsonObjList = jsonObj.jsonList; 
			
			text =  "<div id='detailsHistoryDiv' style='border:1px solid;'> Jenkins Execution Status" +
						"<div class='exportTools'>" +
							"<a href = '#' onClick = 'exportToExcelDetailHistory("+jsonObjList[0].workflowId+", "+jsonObjList[0].startFlowId+");' class='exportExcel' title='Export to Excel' style='margin-left:-250px; margin-top:-15px; padding-bottom:20px;'></a>" +
							"<a href = '#' onClick = 'exportToPdfDetailHistory  ("+jsonObjList[0].workflowId+", "+jsonObjList[0].startFlowId+");' class='exportPDF'   title='Export to PDF'   style='margin-left:0px;    margin-top:-15px; padding-bottom:20px;'></a>" +
						"</div>" +
						
						"<table style='width:100%; border: 1px solid black; border-collapse: collapse; padding-bottom:10px;'>" +
							"<tbody style='border: 1px solid black; border-collapse: collapse;'> " +
								"<tr>" +
									"<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'> USER 		</th>" +
									"<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'> Built On Node</th>" +
									"<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'> Jenkins ID 	</th>" +
									"<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'> Result 		</th>" +
									"<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'> Time 		</th>" +
								"</tr>";
			
								var data = jsonObjList;
								for(var i in data){
									var workflowId 			= jsonObjList[i].workflowId;
									var username 			= jsonObjList[i].username;
								    var displayName			= jsonObjList[i].displayName;
								    var duration			= jsonObjList[i].duration;
								    var estimatedDuration 	= jsonObjList[i].estimatedDuration;
								    var fullDisplayName		= jsonObjList[i].fullDisplayName;
								    var id					= jsonObjList[i].id;
								    var keepLog				= jsonObjList[i].keepLog;
								    var buildNo				= jsonObjList[i].buildNo;
								    var queueId				= jsonObjList[i].queueId;
								    var result				= jsonObjList[i].result;
								    var timestamp			= jsonObjList[i].timestamp;
								    var builtOnNode			= jsonObjList[i].builtOnNode;
								    var startFlowId			= jsonObjList[i].startFlowId;
								    text = text+
					    		
								"<tr style='background-color:#efe;'>" +
					    			"<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>"+ username 			+"</td>" +
					    			"<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>"+ builtOnNode 		+"</td>" +
					    			"<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>"+ id 				+"</td>" +
					    			"<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>"+ result 			+"</td>" +
					    			"<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>"+ timestamp 			+"</td>" +
					    		"</tr>";
							}
							text = text+"" +
							"</tbody>" +
						"</table>" +
					"</div>";
		}
		return text;
	};
	//Get and appand the detail history: START
	//jsonObj.resultStr;
	
})( jQuery );


function toggleJenkinsHistory(selHistoryDiv, workflowId, startFlowId) {
	var historyId = selHistoryDiv.id;
	//var reportHistoryId = selReportHistory.id;
	$.fn.jenkinsDetailHistory(workflowId, startFlowId, historyId);
	
	var elem = document.getElementById(historyId);
	if(!elem){
	    alert("error: not found!");
	} else {
	   //$('#'+id).toggle(500);
	   $('#'+historyId).slideToggle();
	   //$('#'+reportHistoryId).slideToggle();
	}
}

function toggleReportHistory(selHistoryDiv, workflowId, startFlowId) {
	var historyId = selHistoryDiv.id;
	//var reportHistoryId = selReportHistory.id;
	$.fn.reportDetailHistory(workflowId, startFlowId, historyId);
	
	var elem = document.getElementById(historyId);
	if(!elem){
	    alert("error: not found!");
	} else {
	   //$('#'+id).toggle(500);
	   $('#'+historyId).slideToggle();
	   $('#'+reportHistoryId).slideToggle();
	}
}
//HISTORY OVARLAY AND DETAILS HISTORY: END

/**
 * Report AJAX: START
 */
(function( $ ){
	$.fn.showReport = function(rowId, workflowId) {
		var action		= 'LAST_START_FLOW_HISTORY';
		$.ajax({
		    url: "WorkflowHistoryAJAX",
		    type: "POST",
		    data: {action:action, workflowId:workflowId},
		    dataType : 'json', // Returns json object.
		    beforeSend: function() {
		    	$("#loading").css("display","block");			// Befor send showing the loading image...
		    	$('html, body').css({							// Block scrolling!
		    	    'overflow': 'hidden',
		    	    'height': '100%'
		    	})
		  	},
		  	complete: function(){
		  		$("#loading").css("display","none");			// After complete hiding the loading image...
		  		$('html, body').css({							// Unblock scrolling!
		  		    'overflow': 'auto',
		  		    'height': 'auto'
		  		})
		  	},
		  	success: function(jsonObj){
		        $.fn.showHistoryOverlay(jsonObj,rowId, action);						// On Success: Calling loadLastReleaseDataCallbackFunction function
		    },
		    error:function(msg){
		        alert("Unable to show history at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	}; 
	
	$.fn.showReportOverlay = function(text,rowId) {
		$('#msg').html(text);
		$( "#dialog" ).dialog({
			width: "90%",
            height: "auto",
            title: "Report",
            buttons: [
			            {
			              text: "OK",
			              click: function() {
			                $( this ).dialog( "close" );
			              }
			           }
			         ]
		});
		return this;
	}; 
})( jQuery );
//Report AJAX : END

//EXPORT TO EXCEL : START
function exportDetailHistory(){
	var pdf = new jsPDF('p', 'pt', 'letter');
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    source = $('#detailsHistoryDiv')[0];

    // we support special element handlers. Register them with jQuery-style 
    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
    // There is no support for any other type of selectors 
    // (class, of compound) at this time.
    specialElementHandlers = {
        // element with id of "bypass" - jQuery style selector
        '#bypassme': function (element, renderer) {
            // true = "handled elsewhere, bypass text extraction"
            return true
        }
    };
    margins = {
        top: 30,
        bottom: 30,
        left: 80,
        width: 922
    };
    // all coords and widths are in jsPDF instance's declared units
    // 'inches' in this case
    pdf.fromHTML(
    source, // HTML string or DOM elem ref.
    margins.left, // x coord
    margins.top, { // y coord
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        // dispose: object with X, Y of the last line add to the PDF 
        //          this allow the insertion of new lines after html
        pdf.save('Test.pdf');
    }, margins);
}


function demoFromHTML() {
    var pdf = new jsPDF('p', 'pt', 'letter');
    // source can be HTML-formatted string, or a reference
    // to an actual DOM element from which the text will be scraped.
    source = $('#content')[0];

    // we support special element handlers. Register them with jQuery-style 
    // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
    // There is no support for any other type of selectors 
    // (class, of compound) at this time.
    specialElementHandlers = {
        // element with id of "bypass" - jQuery style selector
        '#bypassme': function (element, renderer) {
            // true = "handled elsewhere, bypass text extraction"
            return true
        }
    };
    margins = {
        top: 30,
        bottom: 30,
        left: 80,
        width: 922
    };
    // all coords and widths are in jsPDF instance's declared units
    // 'inches' in this case
    pdf.fromHTML(
    source, // HTML string or DOM elem ref.
    margins.left, // x coord
    margins.top, { // y coord
        'elementHandlers': specialElementHandlers
    },

    function (dispose) {
        // dispose: object with X, Y of the last line add to the PDF 
        //          this allow the insertion of new lines after html
        pdf.save('Test.pdf');
    }, margins);
}
//EXPORT TO EXCEL : END

function exportToExcelHistory(workflowId, action){
	document.forms[0].action ="exportToExcelHistory.do?method=exportToExcelHistory&workflowId="+workflowId+"&action="+action;
	document.forms[0].method="post";
	document.forms[0].submit();	
}

function exportToExcelDetailHistory(workflowId, startFlowId){
	document.forms[0].action ="exportToExcelDetailHistory.do?method=exportToExcelDetailHistory&workflowId="+workflowId+"&startFlowId="+startFlowId;
	document.forms[0].method="post";
	document.forms[0].submit();	
}

function exportToPdfHistory(workflowId, action){
	document.forms[0].action ="exportToPdfHistory.do?method=exportToPdfHistory&workflowId="+workflowId+"&action="+action;
	document.forms[0].method="post";
	document.forms[0].submit();	
}

function exportToPdfDetailHistory(workflowId, startFlowId){
	document.forms[0].action ="exportToPdfDetailHistory.do?method=exportToPdfDetailHistory&workflowId="+workflowId+"&startFlowId="+startFlowId;
	document.forms[0].method="post";
	document.forms[0].submit();	
}