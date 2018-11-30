jQuery.fn.extend({
	checkWorkflowName: function () {
		var workflowName 	= $('[name=workflowName]').val();
		var workflowId 		= $('[name=workflowId]').val();
		
		$.ajax({
		    url: "WorkflowNameValidationAJAX",
		    type: "POST",
		    data: {workflowName:workflowName, workflowId:workflowId},
		    dataType : 'json', 
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
		        $.fn.checkWorkflowNameResponse(jsonObj);
		    },
		    error:function(msg){
		        alert("TODO ERROR MSG");							// On Fail showing alert with msg.
		    }
		});
	},
	
	checkWorkflowNameResponse: function(jsonObj){
		if(jsonObj.result==false){
			$('#tickIcon').hide();
			$('#crossIcon').show();
			$("#appName").focus();
		}else{
			$('#tickIcon').show();
			$('#crossIcon').hide();
		}
		$('#isValidWorkflowName').val(jsonObj.result);
		return this;
	}
});
//AJAX function: END