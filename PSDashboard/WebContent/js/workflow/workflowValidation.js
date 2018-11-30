//RELEASE Form Submit Validation  : START
$( "form" ).submit(function( event ) {
	var flag = true;
	var msg = "Below field(s) are mandatory:\n";

	//Workflow Fields
	var workflowName 	= $("#workflowName")	.val();
	var wTypeId 		= $("#wTypeId")			.val();
	var appId 			= $("#appId")			.val();
	var username 		= $("#username")		.val();
	var password 		= $("#password")		.val();
	var authToken 		= $("#authToken")		.val();
	var server 			= $("#server")			.val();
	var port 			= $("#port")			.val();
	var jobName 		= $("#jobName")			.val();
	var buildType 		= $('input[name=buildType]:checked').val();
	var buildParameter 	= $("#buildParameter")	.val();
	var profileIds 		= $("#profileIds")		.val();
	var desc 			= $("#desc")			.val();
	
	var isValidWorkflowName	= $("#isValidWorkflowName")	.val();
	
	// DB Fields
//	var db_type 		= $("#db_type")			.val();
//	var db_username 	= $("#db_username")		.val();
//	var db_password 	= $("#db_password")		.val();
//	var db_hostname 	= $("#db_hostname")		.val();
//	var db_port 		= $("#db_port")			.val();
//	var db_sid 			= $("#db_sid")			.val();
//	var db_table_name 	= $("#db_table_name")	.val();
//	var db_query 		= $("#db_query")		.val();
	
	if( workflowName == ""){
		msg += "-Workflow Name.\n";
		flag = false;
	}else if( isValidWorkflowName != "true"){
		msg += "-Please Enter Valid Workflow Name.\n";
		flag = false;
	}
	
	if( wTypeId == ""){
		msg += "-Workflow Type.\n";
		flag = false;
	}
	if( appId == ""){
		msg += "-Application.\n";
		flag = false;
	}
	if( username == ""){
		msg += "-Username.\n";
		flag = false;
	}
	if( password == ""){
		msg += "-Password.\n";
		flag = false;
	}
	if( authToken == ""){
		msg += "-Authentication Token.\n";
		flag = false;
	}
	if( server == ""){
		msg += "-Server.\n";
		flag = false;
	}
	if( port == 0){
		msg += "-Port.\n";
		flag = false;
	}
	if( jobName == ""){
		msg += "-Job Name.\n";
		flag = false;
	}
	if( buildType == undefined){
		msg += "-Build Type.\n";
		flag = false;
	}
	if(buildType == "buildWithParameters"){
		if( buildParameter == ""){
			msg += "-Build Parameter.\n";
			flag = false;
		}
	}
	if(profileIds == null || profileIds == 'null'){
		msg += "-Profile.\n";
		flag = false;
	}
	if( desc == ""){
		msg += "-Description.\n";
		flag = false;
	}
	
	var jsonArr = createFixItJsonArray();	// Generating the FIXIT JSON ARRAY from table.
	$("#jsonArr").val(jsonArr);				// Setting FIXIT JSON ARRAY.
	
	if (flag) {
		return
	}else{
		//alert(msg+db_msg);
		alert(msg);
		event.preventDefault();
	}
});
//RELEASE Form Submit Validation  : END

//USER DETAILS Form Reset : START
	//$('#clear').click(function (){
	//	$('#workflowForm').clear();
	//});
//USER DETAILS Form Reset : END

//Text Counter : START
$('#desc').textcounter({
	'type'                      : "character",              // "character" or "word"
	'min'                       : 0,                        // minimum number of characters/words
	'max'                       : 500,                     // maximum number of characters/words, -1 for unlimited, 'auto' to use maxlength attribute
	'countSpaces'               : true,                    	// count spaces as character (only for "character" type)
	'countDown'                 : true,                    	// if the counter should deduct from maximum characters/words rather than counting up
	'countDownText'             : "Remaining: ",            // count down text
	'countExtendedCharacters'   : false 
});
//Text Counter : END