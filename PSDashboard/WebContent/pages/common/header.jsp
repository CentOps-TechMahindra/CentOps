<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Header</title>
	</head>
	
	<body>
		<!-- Header Start -->
		<header>
			<nav class="navbar navbar-default">
		    	<div class="container-fluid">
			      	<div class="brand-custom"> 
			      		<a href="#" title="CentOps" id="centOpsLogoId">
			      			<img alt="CentOps" src="images/CentOps_logo.gif" style="position: absolute; top: 50%;left: 3%; transform: translateX(-50%) translateY(-50%);max-width: 100%; max-height: 100%;" />
			      		</a> 
			      	</div>
			      	
			      	<div class="nav-top-middle"> 
			      		<h1 style="text-align:center">Centralized Operations</h1> 
			      	</div>
			      	
			      	
			      	<div class="nav-top">
			      		<!-- Logo and User Info -->
			      		<div style="width: 40%; float: right; padding-top:20px; padding-right: 10px; height: 70px;">
					    	<ul class="nav navbar-nav pull-right">
					        	<li class="dropdown"> 
					        		<img src="images/techM.png" alt="TechM" style="margin-bottom: 1px;"/> 
					        	</li>
					   		</ul>
					   	</div>
					   	<c:if test="${null!=USERSESSION}">
					   	<div style="width: 60%; float: left; height: 70px;">
					    	<ul class="nav navbar-nav pull-left">
					        	<li class="dropdown user" style=" height: 70px;"> 
					        		<a href="#" title="" class="dropdown-toggle pull-left" data-toggle="dropdown" style="padding-top:25px;"> 
					        			<span class="userName">
					        				<bean:write scope="session" name="USERSESSION" property="userName" />
					        			</span> 
					        			<i class="glyphicon glyphicon-cog"></i>
					        		</a>
					            	<ul class="dropdown-menu">
					              		<c:if test="${USERSESSION.levelId==10}">
							            	<li><a href="admin.do?method=appManager">	<i class="icon-user"></i> Application Manager</a></li>
					              		</c:if>
							            <li><a href="user.do?method=requestAccess">		<i class="icon-user"></i> Request Access</a></li>
							            <c:if test="${USERSESSION.levelId==10}">
							            	<li><a href="admin.do">						<i class="icon-cog"> </i> Review Access	</a></li>
							            </c:if>
							            	
							            <li><a href="feedback.do">						<i class="icon-cog"></i> View Feedback</a></li>
							            <c:if test="${USERSESSION.levelId==10}">
							            	<li><a href="feedback.do?method=reviewNewFeedback"><i class="icon-cog"> </i> Review Feedback</a></li>
							            </c:if>
							            <li><a href="logout.do">						<i class="icon-key"> </i> Log Out		</a></li> 
					            	</ul>
					          	</li>
					   		</ul>
					   	</div>
					   	</c:if>
					</div>
      	    	</div> 		
		  	</nav>
		</header>
		<!-- Header End -->
	</body>
	<script>
	$("#centOpsLogoId").confirm({
	    title:"Are you sure!",
	    text:"Please confirm if you want to go back to Home page?",
	    confirm: function(button) {
	    	window.location.href = 'home.do';
	    },
	    cancel: function(button) {},
	    confirmButton: "Ok",
	    cancelButton: "Cancel"
	});
	</script>
</html>
