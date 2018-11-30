jQuery.fn.extend({
	checkWorkflowType: function () {
		var workflowType 		= $('[name=workflowType]').val();
		var workflowTypeId 		= $('[name=workflowTypeId]').val();
		workflowType 			= $.trim(workflowType);
		$('[name=workflowType]').val(workflowType);
		
		$.ajax({
		    url: "WorkflowTypeValidationAJAX",
		    type: "POST",
		    data: {workflowType:workflowType, workflowTypeId:workflowTypeId},
		    dataType : 'json', 
		    beforeSend: function() {
		    	if( workflowType == ""){
					return false;
				}
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
		        $.fn.checkWorkflowTypeResponse(jsonObj);
		    },
		    error:function(msg){
		        alert("TODO ERROR MSG");							// On Fail showing alert with msg.
		    }
		});
	},
	
	checkWorkflowTypeResponse: function(jsonObj){
		if(jsonObj.result==false){
			$('#tickIcon').hide();
			$('#crossIcon').show();
			$("#appName").focus();
		}else{
			$('#tickIcon').show();
			$('#crossIcon').hide();
		}
		$('#isValidWorkflowTypeName').val(jsonObj.result);
		return this;
	}
});
//AJAX function: END