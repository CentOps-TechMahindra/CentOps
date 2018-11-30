<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Header</title>
		<!-- Bootstrap Core CSS -->
		<link rel="stylesheet" type="text/css" href="../../css/bootstrap.min.css"/>
		<link rel="stylesheet" type="text/css" href="../../jqGrid_JS/css/ui.jqgrid.css"/>
		<link rel="stylesheet" type="text/css" href="../../css/ps-dashboard.css"/>
		<link rel="stylesheet" type="text/css" href="../../css/footer.css"/>
		
		<script type="text/javascript" src="../../jquery-ui-1.11.4.custom/external/jquery/jquery.js"></script>
		<script type="text/javascript" src="../../js/bootstrap.min.js"></script>
		<script type="text/javascript">
		window.close();
		</script>
	</head>
	
	<body>
		  	<!-- Header Start -->
			<header>
				<nav class="navbar navbar-default">
			    	<div class="container-fluid">
				      	<div class="brand-custom"> 
				      		<a href="/PSDashboard/home.do" title=CentOps>
				      			<img alt="CentOps" src="images/CentOpsLogo.gif" style="position: absolute; top: 50%;left: 5%; transform: translateX(-50%) translateY(-50%);max-width: 100%; max-height: 100%;" />
				      		</a> 
				      	</div>
				      	
				      	<div class="nav-top-middle"> 
				      		<h1 style="text-align:center">Centralized Operations</h1> 
				      	</div>
				      	
				      	
				      	<div class="nav-top">
					    	<ul class="nav navbar-nav pull-right">
					        	<li class="dropdown" style=""> 
					        		<a href="#" title="" class="dropdown-toggle" data-toggle="dropdown"> 
					        			<img src="images/techM.png" alt="" /> 
					        		</a>
					            </li>
					   		</ul>
						</div>
	      	    	</div> 		
			  	</nav>
			</header>
			<!-- Header End -->
			
			<!-- Container Start -->
			<div class="container-fluid" style="min-height: 84.5vh;">
				<!-- Main Content Area Start -->
			  	<div class="main-container" style="width:100%; text-align: center; margin-top: 10%;">
			    	
			    	<h2>You have successfully Logout!</h2>
			    	<br />

					<h3><a href="/PSDashboard/home.do" title="Console">Click here</a> to login again.</h3>
			    	
				</div>
		  		<!-- Main Content Area End -->
			  
			  	<div class="clearfix"></div>
			</div>
			<!-- Container End -->
			
		<!--footer start-->
		<jsp:include page="/pages/common/footer.jsp" />
		<!--footer end-->
	</body>
</html>