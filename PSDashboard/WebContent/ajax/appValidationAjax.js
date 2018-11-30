jQuery.fn.extend({
	checkApp: function () {
		var appName 	= $('[name=appName]').val();
		var appId 		= $('[name=appId]').val();
		appName 		= $.trim(appName);
		$('[name=appName]').val(appName);
		
		$.ajax({
		    url: "AppValidationAJAX",
		    type: "POST",
		    data: {appName:appName, appId:appId},
		    dataType : 'json', 
		    beforeSend: function() {
		    	if( appName == ""){
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
		        $.fn.checkAppResponse(jsonObj);
		    },
		    error:function(msg){
		        alert("TODO ERROR MSG");							// On Fail showing alert with msg.
		    }
		});
	},
	
	checkAppResponse: function(jsonObj){
		if(jsonObj.result==false){
			$('#tickIcon').hide();
			$('#crossIcon').show();
			$("#appName").focus();
		}else{
			$('#tickIcon').show();
			$('#crossIcon').hide();
		}
		$('#isValidAppName').val(jsonObj.result);
		return this;
	}
});
//AJAX function: END