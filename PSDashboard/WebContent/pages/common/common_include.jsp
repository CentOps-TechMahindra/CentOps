<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Header</title>
		<!-- Bootstrap Core CSS -->
		<link rel="stylesheet" type="text/css" href="css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css" href="jquery-ui-1.11.4.custom/jquery-ui.css"/>
		<link rel="stylesheet" type="text/css" href="jquery-ui-1.11.4.custom/jquery-ui.min.css"/>
		<!-- <link rel="stylesheet" type="text/css" href="jqGrid_JS/css/ui.jqgrid.css"/> -->
		<link rel="stylesheet" type="text/css" href="jqGrid_JS/css/ui.jqgrid-bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="css/ps-dashboard.css"/>
		
		<script type="text/javascript" src="jquery-ui-1.11.4.custom/external/jquery/jquery.js"></script>
		<script type="text/javascript" src="js/jquery.confirm.js"></script>
		<script type="text/javascript" src="js/bootstrap.min.js"></script>
		<script type="text/javascript" src="jquery-ui-1.11.4.custom/jquery-ui.js"></script>
		<script type="text/javascript" src="jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
		<script type="text/javascript" src="jqGrid_JS/js/jquery.jqGrid.min.js"></script>
		<script type="text/javascript" src="jqGrid_JS/js/i18n/grid.locale-en.js"></script>
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/textcounter.min.js"></script>
		<script type="text/javascript" src="js/speech.js"></script>
		<script type="text/javascript" src="js/responsivevoice.js"></script>
		
	</head>
	
	<body>
		
	</body>
	<script>
	$("#centOpsLogoId").confirm({
	    title:"Are you sure!",
	    text:"Please confirm if you want to go back to Home page?",
	    confirm: function(button) {
	    	window.location.href = 'dashboard.do';
	    },
	    cancel: function(button) {},
	    confirmButton: "Ok",
	    cancelButton: "Cancel"
	});
	</script>
</html>
