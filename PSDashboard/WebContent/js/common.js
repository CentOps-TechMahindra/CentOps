function jsGoBack(){
	var r = confirm("Press 'OK' if you want to go back to previous page!");
	if (r == true) {
		window.history.go(-1);
	}
}

function jsGoBackToWorkflowHome(){//window.location.href = 'dashboard.do';
	var appName = document.getElementById('appName').value;
	document.workflowForm.action = "dashboard.do?appName="+appName;
    document.workflowForm.submit();
}