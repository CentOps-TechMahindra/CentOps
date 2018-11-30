<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<!-- Uncomment below meta tag if you want to refresh page in every 10 sec -->
		<!-- <meta http-equiv="refresh" content="10" />  -->
		<title>CentOps - Console</title>
		<link rel="stylesheet" type="text/css" href="jqGrid_JS/css/ui.jqgrid.css"/>
	</head>
	
	<body>
		<html:form name="dashboardForm"  method="post"  action="/dashboard.do?method=dashboard" type="com.techm.psd.dashboard.form.DashboardForm" enctype="multipart/form-data">
			<html:hidden property="jsonObject" 					name="dashboardForm" styleId="jsonObject"/>
			<html:hidden property="filterAppList" 				name="dashboardForm" styleId="filterAppList"/>
			<html:hidden property="filterWorkflowTypeJsonStr" 	name="dashboardForm" styleId="filterWorkflowTypeJsonStrId"/>
			
			<input type="hidden" id="levelId" value="${USERSESSION.levelId}" name="levelId"/>
			<input type="hidden" id="appName" value="${param.appName}" name="appName"/>
			
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			
			<!-- Container Start -->
			<div class="container-fluid">
				<!-- Sidebar Tree Structure and Search Start -->
			  	<jsp:include page="/pages/common/sidebar.jsp" />
			  	<!-- Sidebar Tree Structure and Search End -->
			  	
			  	<!-- Main Content Area Start -->
			  	<div class="main-container">
			  		
			    	<h3 class="page-header" style="display: none;">
			    		<c:out value="${requestScope.unauthorized}"></c:out> 
			    	</h3>
			    	<div class="panel panel-danger">
			      		<div class="panel-heading">
			        		<div class="panel-title pull-left" id="appDiv"></div>
			        		<div class="clearfix"></div>
			      		</div>
			      		<!-- Table Data Content Start -->
			      		<div class="" id="gridDiv">
			        		<table id="theGrid"></table>
			        		<div class="" id="noDataDiv" style="display: none; text-align: center; padding-top:11%; padding-bottom:11%; font-size: 40px;">
				        		No Data Found!
				        	</div>
				        	<div id="gridPager" style="z-index:1;"></div>
			        	</div>
			        	<!-- Table Data Content End -->
			      		<div class="clearfix"></div>
			    	</div>
			  	</div>
			  	<!-- Main Content Area End -->
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
			<!-- Include loading image START -->
			<jsp:include page="/pages/common/dialog.jsp" />
			<jsp:include page="/pages/common/loading.jsp" />
			<jsp:include page="/pages/common/progressbar.jsp" />
			<jsp:include page="/pages/psd/executionOverlay.jsp" />
			<jsp:include page="/pages/common/underCons.jsp" />
			<!-- Include loading image END -->
	  		
			<!-- Javascript: START -->
			<!-- Grid (#theGrid) is populate using dashbard.js --> 
			<script type="text/javascript" src="js/dashboard.js"></script>
			<script type="text/javascript" src="ajax/dashboardAjax.js"></script>
			<script type="text/javascript" src="ajax/feedbackAjax.js"></script>
			<script type="text/javascript" src="ajax/fixitOverlayAjax.js"></script>
			<!-- JavaScript: END -->
			
			<!-- CSS: START-->
			<link rel="stylesheet" type="text/css" href="css/fixit.css"/>
			<style type="text/css">
				.ui-widget-content a{
					color: #337ab7;
				    font-size:14px;
				}
				#theGrid_workflowId, #theGrid_appName, #theGrid_workflowType, #theGrid_workflowName, #theGrid_action, #theGrid_report, #theGrid_status{
					font-weight:bold;
				}
			</style>
			<!-- CSS: END-->
		</html:form>
		
	</body>

</html>