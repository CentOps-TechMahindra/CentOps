$( "form" ).submit(function( event ) {
	var workflowType 			= $('[name=workflowType]')		.val();
	var desc 					= $("#description")				.val();
	var isValidWorkflowTypeName	= $("#isValidWorkflowTypeName")	.val();
	
	var flag = true;
	var msg = "Below field(s) are mandatory:\n";
	
	if( workflowType == ""){
		msg += "-Workflow Type Name.\n";
		flag = false;
	}else if( isValidWorkflowTypeName != "true"){
		msg += "-Please Enter Valid Workflow Type Name.\n";
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