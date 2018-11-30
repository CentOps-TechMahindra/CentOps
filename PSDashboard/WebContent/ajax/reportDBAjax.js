jQuery.fn.extend({
	checkConnection: function () {
	var db_type 		= $('[name=db_type]').val();
	var db_username 	= $('[name=db_username]').val();
	var db_password 	= $('[name=db_password]').val();
	var db_hostname 	= $('[name=db_hostname]').val();
	var db_port 		= $('[name=db_port]').val();
	var db_sid 			= $('[name=db_sid]').val();
	var db_table_name 	= $('[name=db_table_name]').val();
	
	var action		= 'START';
		$.ajax({
		    url: "ReportDBAjax",
		    type: "POST",
		    data: {db_type:db_type, db_username:db_username, db_password:db_password, db_hostname:db_hostname,db_port:db_port, db_sid:db_sid, db_table_name:db_table_name},
		    dataType : 'json', // Returns json object.
		    success: function(jsonObj){
		        $.fn.checkConnectionResponse(jsonObj);
		    },
		    error:function(msg){
		        alert("TODO ERROR MSG");							// On Fail showing alert with msg.
		    }
		});
	},
	
	checkConnectionResponse: function(jsonObj){
		if(jsonObj.result == true){
			alert("Valid Connection!");
		}else{
			alert("Invalid Connection!");
		}
	}
});
//AJAX function: END