$(document).ready(function () {
	$("button").click(function(e) {
		if(document.forms['frmAddUrl'].urlId.value === "" || document.forms['frmAddUrl'].descripId.value === ""){
			alert("URL and URL Description cannot be empty. Please enter value");
      	    return false;
		}
		e.preventDefault();
			$.ajax({
				type: "POST",
                url: "AppManagerServlet?action=create",
                data: { 
                	url:$("#urlId").val(),
                	desc:$("#descripId").val(),
                	lic:$("#inputemail").val()
                },
                success: function(result) {
                	$('#PersonTableContainer').jtable('load');
                	$("#urlId").val("");
                	$("#descripId").val("");
                	$("#inputemail").val("");
                },
                error: function(result) {
                    alert('Error in data insert');
                }
         });
	});
        
	$.ajax({
		url: "LoadImage?action=load",
	    type: "GET",
	    dataType : 'text', // Returns json object.
	  	success: function(jsonObj){	  		
	    	var str="<table  class='makeChange' >";
		  	var arr=jsonObj.split(",");
		  	for(var i=0;i<arr.length-1;i++){
	      		var name=arr[i];
	      		var ing=name.substr(0,name.length-1);
	      		str=str+"<tr>" +
	      					"<td id=\"mainrow\">" +
	      						"<table > " +
	      							"<tr>" +
	      								"<td rowspan='2' >" +
	      									"<img src='images/"+ing+".png' width='150px' height='45px'/>" +
	      								"</td>" +
	      								"<td class=\"docenter\" width='150px'>" +
	      									"<span id='"+ing+"1'>" +
	      										"<font color=\"green\"><b>Enabled</b></font>" +
	      									"</span>" +
	      								"</td>" +
	      							"</tr>"+
	      							"<tr>" +
	      								"<td class=\"docenter\" width='150px'>" +
	      									"<a onclick=\"disableTool('"+ing+"');\">" +
	      										"<span id='"+ing+"'>" +
	      											"<font color=\"red\"><b>Click To Disable</b></font>" +
	      										"</span>" +
	      									"</a>" +
	      								"</td>" +
	      							"</tr>" +
	      						"</table>" +
	      					"</td>" +
	      				"</tr>";
	      		
		  	} 
		  	str=str+"</table>"; 
		  	$("#mainData").html(str);
		  	for(var i=0;i<arr.length;i++){
		  		var name=arr[i];
	    	  	var ing=name.substr(0,name.length-1);
	    	  	if(name.substr(name.length-1,name.length)=='1'){
	    	  		$("#"+ing).html('<font color="green"><b>Click to Enable</b></font>');
	    	  		$("#"+ing+"1").html('<font color="red"><b>Disabled</b></font>');
	    	  	}
		  	}	
	  	}
    });	
      
    $('#PersonTableContainer').jtable({
    	title: 'Table of URL\'s',
        paging: true, //Enable paging
        pageSize: 10, //Set page size
        sorting: true, //Enable sorting
        defaultSorting: 'srn DESC', //Set default sorting
        actions: {
            listAction: 'AppManagerServlet?action=list',
            createAction:'AppManagerServlet?action=create',
            updateAction: 'AppManagerServlet?action=update',
            deleteAction: 'AppManagerServlet?action=delete'
        },
        fields: {
        	srn: {
        		title: 'SrNo',
                key:true,
                width: '7%',
                visibility: 'hidden',
                bgcolor: '#e35b5a',
                edit:false
        	},
            url: {
                title: 'Application URL',
                width: '50%',
                edit:true
            },
            desc: {
                title: 'Description',
                width: '38%',
                edit:true
            },
            lic: {
                title: 'LIC',
                width: '5%',
                input: function (data) {
                     { $("#lic").val(data.record.lic);
                    	 if(data.record.lic=='Yes')
                    		
                        return '<p> <input type="hidden" name="lic" id="lic" value="Yes"/>&nbsp;Yes: <input type="radio" name="lic1" id="lic1" value="Yes" checked="true" onclick="changeRadio(\'Yes\')"/> No: <input type="radio" id="lic1" value="No" name="lic1"  onclick="changeRadio(\'No\')"/></p>';
                        else
                        	return '<p>  <input type="hidden" name="lic" id="lic" value="No"/>&nbsp;Yes: <input type="radio" value="Yes" name="lic1" id="lic1" onclick="changeRadio(\'Yes\')" /> No: <input type="radio" name="lic1" id="lic1" value="No" checked="true" onclick="changeRadio(\'No\')"/></p>';
                    }                          
                },edit: true
            }                
        }
	});
        $('#PersonTableContainer').jtable('load');
});

function changeRadio(param){
	$("#lic").val(param);
}
    
function disableTool(param){
	if($("#"+param).html().indexOf("Disable") !== -1){
    	$.ajax({
    		url: "LoadImage?action=disable",
    	    type: "POST",
    	    data: {"imgName":param},
    	    dataType : 'text', // Returns json object.
    	  	success: function(jsonObj){	  		
    	  		$("#"+param).html('<font color="green"><b>'+jsonObj+'</b></font>');
    	  		$("#"+param+"1").html('<font color="red"><b>Disabled</b></font>');
    	    }
    	});
	}else{
		$.ajax({
			url: "LoadImage?action=enable",
    	    type: "POST",
    	    data: {"imgName":param},
    	    dataType : 'text', // Returns json object.
    	  	success: function(jsonObj){	  		
    	  		$("#"+param).html('<font color="red"><b>'+jsonObj+'</b></font>');
    	  		$("#"+param+"1").html('<font color="green"><b>Enabled</b></font>');
    	    }
    	});
	}
	
}
    
function setRadioValue(){
	for(var i=0;i<document.frmAddUrl.inputemail1.length;i++){
		if(document.frmAddUrl.inputemail1[i].checked){
			document.frmAddUrl.inputemail.value=document.frmAddUrl.inputemail1[i].value;
	  	}
	} 
}