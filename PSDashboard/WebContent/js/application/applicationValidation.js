$( "form" ).submit(function( event ) {
	var appName 		= $('[name=appName]')	.val();
	var desc 			= $("#desc")			.val();
	var isValidAppName	= $("#isValidAppName")	.val();
	var appsId			= $("#appsId")			.val();
	
	var flag = true;
	var msg = "Below field(s) are mandatory:\n";
	
	if( appName == ""){
		msg += "-Application Name.\n";
		flag = false;
	}else if( isValidAppName != "true"){
		msg += "-Please enter Valid Application Name.\n";
		flag = false;
	}
	
	if( appsId != "" & isNaN(appsId)){
		msg += "-Please enter valid APP ID.\n";
		flag = false;
	}else if( appsId == 0){
		msg += "-APP ID.\n";
		flag = false;
	}
	
	if( desc == ""){
		msg += "-Description.\n";
		flag = false;
	}
	
	if (flag) {
		return true;
	}else{
		alert(msg);
		event.preventDefault();
	}
});