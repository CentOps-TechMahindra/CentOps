<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Sidebar</title>
		<script>
			$(function() {
				$( "#accordion" ).accordion({
					active: 0,
			        collapsible: true,
			        heightStyle: "content",
			        alwaysOpen: false,
			        autoHeight: false,
			        defaultOpe: false,
			        navigation: true
			    });
			});
		</script>
		
		<script>	// below variables are used to select the applicaiton in left pannel accordian.
			var counter = 0;
			var selAccordianIdx = 0;
		</script>
	</head>
	
	<body onload="filterOnLoad();">
		<!-- Sidebar Tree Structure and Search Start -->
	  	<div class="page-sidebar">
	    	<!-- Menu Start -->
		  		<jsp:include page="/pages/common/menu.jsp" />
		  	<!-- Menu End -->
			<!--  
	    	<form id="sidebar-search" method="post">
	      		<input type="text" placeholder="Search" id="sidebar-text-search" name="sidebar-search-term" value="">
	      		<button class="sidebar-search-icon"></button>
	    	</form>
	    	-->
	    	<div class="workflowlist" id="accordion">
	    		<c:forEach items="${requestScope.appList}" var="appDTO">
		           	<h3 class="applicationTree" onclick="filterOnSideBar('<c:out value="${appDTO.appName}"/>');"> 
						<a href="#" title="Application"> 
							<c:out value="${appDTO.appName}"/>
	               		</a>
	               		<script>
	               			//Below code is used to select the applicaiton in left pannel accordian on load. 
		               		var app = '<c:out value="${appDTO.appName}"/>';				// getting the appliaction name from appDTO Loop. 
		               		var selApp = document.getElementById('appName').value;		// getting the applicaiton from selected workflow view and edit page hidden field. 
							if(app == selApp){											
								selAccordianIdx = counter;								// setting the accoriding id to select application name on onload (Used below on filterOnLoad() funciton).
							}
							counter = counter+1;
	               		</script>
		          	</h3>
				    <div class="treeActive" style="border: 0px solid #aaa; height: 50vh; overflow: scroll;">
				    	<ul style="list-style-type: square;">
					        <c:forEach items="${requestScope.wDTOList}" var="workflowDTO">
				            	<c:if test="${workflowDTO.appName == appDTO.appName}">
						            	<li>
							            	<a href='workflow.do?method=viewWorkflow&workflowId=<c:out value="${workflowDTO.workflowId}"/>' title="Workflow" style="color: #344047"> 
												<c:out value="${workflowDTO.workflowName}"/>
											</a>
										</li>
								</c:if>
				    		</c:forEach>
			    		</ul>
			    	</div>
		        </c:forEach>
		    </div>
  		</div>
	  	<!-- Sidebar Tree Structure and Search End -->
	</body>
	<!-- Javascript: START -->
		<script>
			//To select default application onload 
		    function filterOnLoad(){
		        $( "#accordion" ).accordion({active: selAccordianIdx});	// To select the accordian value from left pannel.

				if(selAccordianIdx > 0 ){
					filterOnSideBar(document.getElementById('appName').value);
				}else{
					var onloadApp = $('h3:first > a').text();
		        	filterOnSideBar(onloadApp.trim());							// To select the filter value on workflow list page.
				}
		    }
		</script>
		<!-- Javscript: END -->
</html>
