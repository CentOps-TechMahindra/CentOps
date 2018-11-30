jQuery.fn.extend({
	saveFeedback: function (workflowId, startFlowId) {
		$("#feedbackComment").val("");
		var title = "Feedback!";
		$( "#feedbackDialog" ).dialog({
			width: "400px",
            height: "auto",
            title: title,
            modal: true,
            buttons: {
		        "Save": function (e) {
					var feedback =$("#feedbackComment").val();
					if(feedback.trim() == ""){
    		    		alert("Please enter feedback.");
    		    		e.preventDefault();
    		    		return;
    		    	}else{
				        $.ajax({
			    		    url: "SaveFeedbackAJAX",
			    		    type: "POST",	
			    		    data: {workflowId:workflowId, startFlowId: startFlowId, feedback:feedback},
			    		    dataType : 'json', // Returns json object.
			    		    beforeSend: function() {
			    		    	$("#saving").show();			// Befor send showing the loading image...
			    		    	$('html, body').css({							// Block scrolling!
			    		    	    'overflow': 'hidden',
			    		    	    'height': '100%'
			    		    	});
			    		  	},
			    		  	success: function(jsonObj){
			    		  		$("#saving").hide();			// After complete hiding the loading image...
			    		  		$('html, body').css({							// Unblock scrolling!
			    		  		    'overflow': 'auto',
			    		  		    'height': 'auto'
			    		  		});
			    		    	$.fn.feedbackCallBack(jsonObj);
			    		    },
			    		    error:function(msg){
			    		        alert("Unable to save feedback at this time, Please try again after some time");							// On Fail showing alert with msg.
			    		    }
			    		});
    		    	}
		        },
		        "Close": function () {
		            $(this).dialog("close");
		            $("#saving").hide();
		        }
		    }
		
		});
		
	},
	feedbackCallBack: function (jsonObj) {
		if(jsonObj.result){
			$("#feedbackDialog").dialog("close");
			alert("Thanks! Your feedback has been saved successfully!");
		}else{
			alert("Unable to save feedback at this time, Please try again after some time");
		}
	}
});