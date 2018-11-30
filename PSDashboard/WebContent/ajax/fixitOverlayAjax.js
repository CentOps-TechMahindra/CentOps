//WorkflowHistoryAJAX --> WorkflowBO --> ReportBO
jQuery.fn.extend({
	fixitOverlay: function (workflowId) {
		$.ajax({
		    url: "FixitWorkflowAJAX",
		    type: "POST",	
		    data: {workflowId:workflowId},
		    dataType : 'json', 									// Returns json object.
		    beforeSend: function() {
		    	$("#loading").show();							// Befor send showing the loading image...
		    	$('html, body').css({							// Block scrolling!
		    	    'overflow': 'hidden',
		    	    'height': '100%'
		    	});
		  	},
		  	success: function(jsonObj){
		  		$("#loading").hide();							// After complete hiding the loading image...
		  		$('html, body').css({							// Unblock scrolling!
		  		    'overflow': 'auto',
		  		    'height': 'auto'
		  		}); 
		  		$.fn.fixitDialog(workflowId, jsonObj);
		    },
		    error:function(msg){
		        alert("Unable to save feedback at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
		
	},
	
	fixitDialog: function (workflowId, jsonObj){
		var text = ""; 
		if(jsonObj.result == "FAIL"){
			text = "Fixit Workflow's are not configured!";
		}else if(jsonObj.result == true){
			var jsonObjList = jsonObj.jsonList;
			
			text =  "<table>" +
						"<thead>" +
							"<tr>" +
								"<th></th>" +
								"<th>Error Name</th>" +
								"<th>Error Code</th>" +
								"<th>Fixit! Workflow Name</th>" +
							"</tr>" +
						"</thead>" +
					"<tbody>";
						var data = jsonObjList;
						for(var i in data){
							var errorName 			= jsonObjList[i].errorName;
							var errorCode 			= jsonObjList[i].errorCode;
							var fixitWorkflowName	= jsonObjList[i].fixitWorkflowName;
							var fixitWorkflowId		= jsonObjList[i].fixitWorkflowId;
							
							text = text+
									"<tr>" +
										"<td><input type='radio' name='fixitWorkflowId' value='"+fixitWorkflowId+"'></td>" +
										"<td>"+errorName		+"</td>" +
										"<td>"+errorCode		+"</td>" +
										"<td>"+fixitWorkflowName+"</td>" +
									"</tr>";
							}
						text = text+"</tbody>" +
					"</table>";
		}
		
		$('#fixitMsg').html(text);
		
		
		var title = "Fixit! Workflow List.";
		$( "#fixitDialog" ).dialog({
			width: "500px",
            height: "auto",
            title: title,
            modal: true,
            buttons: {
		        "Select": function (e) {
					var fixitWorkflowId = $('input[name=fixitWorkflowId]:checked').val();
					$.fn.filterWorkflow(fixitWorkflowId);
				},
		        "Close": function () {
		            $(this).dialog("close");
		            $("#loading").hide();
		        }
		    }
		});
	},
	
	filterWorkflow: function (fixitWorkflowId){								// filter value
		var mygrid = $("#theGrid");											// grid id
	    //$("#theGrid").jqGrid('showCol',['status']);           
	    $(".ui-search-toolbar #gs_workflowId").val(fixitWorkflowId);			// #gs_workflowName: Workflow Name
	    mygrid[0].triggerToolbar();
		
	    //Below line of code is to make filter header visible.
	    $(".ui-search-toolbar").css("display", "table-row");
	    
	    //Below code is to start workflow by workflowId
	    //$('#theGrid').startFlow('1',workflowId,workflowName,'build');
	    //$.fn.startFlowAjax('1', workflowId);
	    
	    //$('#theGrid').jqGrid('setSelection','8');
	    
	    //Below code is to close the overlay/dialog box
	    $("#fixitDialog").dialog("close");
	    $("#dialog").dialog("close");
	},
	
	underConsDialog: function (){
		var title = "Under Construction!";
		$( "#underConsId" ).dialog({
			width: "500px",
            height: "auto",
            title: title,
            modal: true,
       });
		//$(".ui-dialog-titlebar").hide();
	}
}); 





////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * START FLOW AJAX function: START
 */
jQuery.fn.extend({
	startFixitFlow: function (workflowName, workflowId, buildType) {
		if(buildType == 'build'){
			$.confirm({
			    title:"Execution Confirmation Box!",
			    text:"Please confirm if you want to Execute the <strong>"+workflowName+"</strong> ?",
			    confirm: function(button) {
					$.fn.startFixitFlowAjax(workflowId);
			    },
			    cancel: function(button) {},
			    confirmButton: "Execute",
			    cancelButton: "Cancel"
			});
		}else{
			$.fn.getFixitNodesDetailsAJAX(workflowId, workflowName);	//TODO: Need to test buildWithParameter
			//alert("Fixit buildWithParameters is not supported yet!");
		}
	},
	
	startFixitFlowAjax: function(workflowId){
		
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
		    data: {workflowId:workflowId, selNodes:selNodes+"", paramStr:paramStr+"", selExecutionOn:selExecutionOn, executedFrom:"fixitWorkflow"},
		    dataType : 'json', // Returns json object.
		    beforeSend: function() {
		    	$("#loading").show();							// Befor send showing the loading image...
		    	$('html, body').css({							// Block scrolling!
		    	    'overflow': 'hidden',
		    	    'height': '100%'
		    	});
	    	},
		  	complete: function(){
		  		$("#loading").hide();							// After complete hiding the loading image...
		  		$('html, body').css({							// Unblock scrolling!
		  		    'overflow': 'auto',
		  		    'height': 'auto'
		  		}); 
		  	},
		  	success: function(jsonObj){
		  		
		  		
				var buildStatus	= jsonObj.status;
				if(buildStatus == "SUCCESS"){
					alert("Fixit Workflow Executed successfully!");
				}else if(buildStatus == "FAILURE"){
					alert("Fixit Workflow was not executed successfully!");
				}else if(buildStatus == "INPROGRESS"){
					
				}else if(buildStatus == "InUse"){
					alert("Workflow is in use by "+jsonObj.username+" please try again later!");
				}
		    },
		    error:function(msg){
		        alert("Workflow can't be executed at this time, Please try again after some time");							// On Fail showing alert with msg.
		    }
		});
	},
	

	getFixitNodesDetailsAJAX: function(workflowId, workflowName){
		$.ajax({
		    url: "NodesDetailsAJAX",
		    type: "POST",
		    data: {workflowId:workflowId},
		    dataType : 'json', 									// Returns json object.
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
										"<button type='button' class='btn btn-primary' onclick=\"jQuery('#theGrid').startFixitFlowAjax('"+workflowId+"');\">Execute</button>";
				$('#exeOverlayButton').html(exeOverlayButton);
				
				//SHOW executionOverlay.jsp
				$("#executionOverlay").modal('show');
				
		    }
		});
	}
});
//AJAX function: END