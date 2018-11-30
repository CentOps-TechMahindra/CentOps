var i=1;

//ADD NEW ROWS
$(".addmore").on('click',function(){
	var appId = $("#appId").val();
	if(appId == ""){
		alert("Please select application!");
	}else{
		count=$('#tableId tr').length;
	    var data="<tr>"+
 					"<td>"+
 						"<input type='checkbox' class='case'/>"+
 					"</td>"+
 					"<td>"+
 						"<span id='snum"+i+"'>"+count+".</span>"+
 					"</td>"+
					"<td>"+
			    		"<input type='text' id='errorName"+i+"' name='errorName'/>"+
			    	"</td>"+
			    	"<td>"+
			    		"<input type='text' id='errorCode"+i+"' name='errorCode'/>"+
			    	"</td>"+
			    	"<td>"+
			    		"<select name='fixitWorkflowName' id='fixitWorkflowId"+i+"'>"+
			    			"<option value=''>-- Select--</option>"+
			    		"</select>"+
			    	"</td>"+
	    		"</tr>";
		$('#tableId').append(data);

		loadFixitWorkflowDropdown('fixitWorkflowId'+i);										//ADDING FIXIT WORKFLOW DROPDOWN LIST TO NEWLY ADDED ROW.
		i++;
	}
});

//LOAD FIXIT WORKFLOW LIST FOR SELECTED APPLICATION 
function loadFixitWorkflowDropdown(fixitSelectId){											//fixit workflow '<SELECT>' Id
	var selAppId = $("#appId").val();
	if(selAppId == ""){
		alert("Please select application!");
	}else{
		var jsonList 	= $('#jsonWorkflowList').val();										
		var data 		= JSON.parse(jsonList);
		$('#'+fixitSelectId).empty();														//TO REMOVE DROPDOWN VALUES
		$('#'+fixitSelectId).append($("<option></option>").val("").text("-- Select --"));	
		for(var i in data){
			var appId = data[i].appId;
			if(appId == selAppId){
				var workflowName 	= data[i].workflowName;
				var workflowId 		= data[i].workflowId;
				$('#'+fixitSelectId).append($("<option></option>").val(workflowId).text(workflowName));
			}
		}
	}
}

//DELETE CHECKED ROWS
$(".delete").on('click', function() {
	$('.case:checkbox:checked').parents("tr").remove();
  $('.check_all').prop("checked", false); 
	check();
});

//SELECT and UNSELECT ALL ROWS
function select_all() {
	$('input[class=case]:checkbox').each(function(){ 
		if($('input[class=check_all]:checkbox:checked').length == 0){ 
			$(this).prop("checked", false); 
		} else {
			$(this).prop("checked", true); 
		} 
	});
}

//RESET ROW Numbers.
function check(){
	obj=$('#tableId tr').find('span');
	$.each( obj, function( key, value ) {
		id=value.id;
		$('#'+id).html(key+1);
	});
}

//GENERATE FIXIT JSON ARRAY and SETTING IN HIDDEN jsonArr VARIBALE after validation onSubmit. 
function createFixItJsonArray(){
	var arrayList = [];
	var rowCount = $('#tableId >tbody >tr').length;
	var obj;
	for (j = 1; j < i; j++) {												// i -- is declared before adding rows as GLOBAL variable
		var errorName		= $('#errorName'+j).val();
		var errorCode		= $('#errorCode'+j).val();
		var selWorkflowId 	= $('#fixitWorkflowId'+j).val();
		var selWorkflowName = $('#fixitWorkflowId'+j+" option:selected").text();
	
		obj = new fixItObj(errorName, errorCode, selWorkflowId, selWorkflowName);
		arrayList.push(obj);
	}
	
	var jsonData 			= JSON.stringify(arrayList);
	var javascriptObject 	= JSON.parse(jsonData);
	var jsonStr				= JSON.stringify(javascriptObject);
	return jsonStr;
}

function fixItObj(errorName, errorCode, selWorkflowId, selWorkflowName) {
    this.errorName 			= errorName;
    this.errorCode			= errorCode;
    this.fixitWorkflowId	= selWorkflowId;
    this.fixitWorkflowName	= selWorkflowName;
}	


//EDIT PAGE: LOAD SAVED FIXIT WORFKLOW LIST 
function loadFixitWorkflow(){
	var array		= $('#jsonArr').val();
	var jsonArr 	= JSON.parse(array);
	var a = 1;
	$.each(jsonArr, function () {
		$(".addmore").trigger("click");
		$("#errorName"+a).val(this.errorName);
		$("#errorCode"+a).val(this.errorCode);
		var fixitWorkflowId = this.fixitWorkflowId;   //  Select any options containing this text
		$("#fixitWorkflowId"+a).val(this.fixitWorkflowId);
		a = a+1;
	});
}