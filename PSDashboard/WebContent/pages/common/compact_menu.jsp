<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<style>
			
		.dropdown-submenu {
		    position: relative;
		}
		.dropdown-submenu>.dropdown-menu {
		    top: 0;
		    left: 100%;
		    margin-top: -6px;
		    margin-left: -1px;
		    -webkit-border-radius: 0 6px 6px 6px;
		    -moz-border-radius: 0 6px 6px;
		    border-radius: 0 6px 6px 6px;
		}
		.dropdown-submenu:hover>.dropdown-menu {
		    display: block;
		}
		.dropdown-submenu>a:after {
		    display: block;
		    content: " ";
		    float: right;
		    width: 0;
		    height: 0;
		    border-color: transparent;
		    border-style: solid;
		    border-width: 5px 0 5px 5px;
		    border-left-color: #ccc;
		    margin-top: 5px;
		    margin-right: -10px;
		}
		.dropdown-submenu:hover>a:after {
		    border-left-color: #fff;
		}
		.dropdown-submenu.pull-left {
		    float: none;
		}
		.dropdown-submenu.pull-left>.dropdown-menu {
		    left: -100%;
		    margin-left: 10px;
		    -webkit-border-radius: 6px 0 6px 6px;
		    -moz-border-radius: 6px 0 6px 6px;
		    border-radius: 6px 0 6px 6px;
		}
		</style>	
	</head>
	
	<body>
		<nav class = "navbar navbar-default" role = "navigation" style="background-color: #f8f8f8; height:15px;">
   	   		<div>
		      <ul class = "nav navbar-nav">
				   	<li class = "" style="padding-top:5px;">
						<a href = "dashboard.do" class="indicator glyphicon glyphicon-home  pull-left" style="padding:10px 15px;" id="homeBtnId"></a>
					</li>
					
					<li class="dropdown" style="width: 10px; float: right; margin-right: 100px;">
			            <a id="dLabel" role="button" data-toggle="dropdown" class=" glyphicon glyphicon-align-justify" data-target="#" href="/page.html"></a>
			            <ul class="dropdown-menu multi-level" role="menu" aria-labelledby="dropdownMenu">
			              <li class = "dropdown-submenu">
								<a tabindex="-1" href="#">Workflow</a>
								<ul class = "dropdown-menu">
							   		<c:choose>
					        			<c:when test="${USERSESSION.levelId==10}">
					        				<li><a tabindex="-1" href = "workflow.do">Add Workflow</a></li>
					        			</c:when>
					        			<c:otherwise>
					        				<li><a tabindex="-1" href = "#" style="color: gray;" onclick="alert('Only Admin can add workflow!');">Add Workflow</a></li>
					        			</c:otherwise>
					        		</c:choose>
								</ul>
							</li>
							
							<li class="divider"></li>
			              	
			              	<li class = "dropdown-submenu">
								<a href = "#" class = "dropdown-toggle" data-toggle = "dropdown">Application</a>
								<ul class = "dropdown-menu">
								   <c:choose>
					        			<c:when test="${USERSESSION.levelId==10}">
					        				<li><a href = "application.do">Add Application</a></li>
					        				<li><a href = "application.do?method=applicationTracker">Application Tracker</a></li>
					        			</c:when>
					        			<c:otherwise>
					        				<li><a href = "#" style="color: gray;" onclick="alert('Only Admin can add application!');">Add Application</a></li>
					        			</c:otherwise>
					        		</c:choose>
								</ul>
							</li>
								
							<li class="divider"></li>
			              	
			              	<li class = "dropdown-submenu">
								<a href = "#" class = "dropdown-toggle" data-toggle = "dropdown">Workflow Type</a>
								<ul class = "dropdown-menu">
								   <c:choose>
					        			<c:when test="${USERSESSION.levelId==10}">
					        				<li><a href = "workflowType.do">Add Workflow Type</a></li>
					        				<li><a href = "workflowType.do?method=workflowTypeTracker">Workflow Type Tracker</a></li>
					        			</c:when>
					        			<c:otherwise>
					        				<li><a href = "#" style="color: gray;" onclick="alert('Only Admin can add workflow type!');">Add Workflow Type</a></li>
					        			</c:otherwise>
					        		</c:choose>
								</ul>
							</li>
			            </ul>
		        	</li>
				</ul>
		   </div>
	   
		</nav>
    	
    	
	</body>
	<script>
	$("#homeBtnId").confirm({
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
	<script>
	//$(document).ready(function(){
	//  $('.dropdown-submenu a.test').on("click", function(e){
	//    $(this).next('ul').toggle();
	//    e.stopPropagation();
	//    e.preventDefault();
	//  });
	//});
	</script>
</html>
