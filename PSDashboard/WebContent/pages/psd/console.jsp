<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Home</title>
		<script src="jqGrid_JS/js/jquery-1.11.0.min.js"></script>
	    <script src="js/Sortable.min.js"></script>
		<style type="text/css">
			#tab-list .close{
				margin-left: 7px;
			}
			
			.bodySize{
				height: 100vh; 
				/*overflow:hidden;*/
			}
			/*Below code is for display dropdown list on mouse hover: console feature list and Header configuration icon features.*/ 
			.dropdown:hover .dropdown-menu {
				display: block;
			}
		</style>
		
	    <script type="text/javascript">
		    $(document).ready(function () {
	            $('#tab-list').on('click','.close',function(){
	                var tabID = $(this).parents('a').attr('href');
	                $(this).parents('li').remove();
	                $(tabID).remove();
	
	                //display first tab
	                var tabFirst = $('#tab-list a:first');
	                tabFirst.tab('show');
	
	            });
	
				var list = document.getElementById("tab-list");
				new Sortable(list);
	        });
	
	        function openApp(srn, lic, appName, url){
		    		if(lic == 'Yes'){
		    			openTab(srn, appName, url);
					}else{
						var win = window.open(url, '_blank');
						if (win) {
						    win.focus();
						} else {
						    alert('Please allow popups for this website');
						}
					}
				}
			    
	    	function openTab(srn, appName, url) {
		    	var listItem = $( "#liID_"+srn);
				var intTabPos = $( "li" ).index( listItem ) ;
				if (intTabPos < 0) {
	                $('#tab-list').append($('<li id="liID_'+srn+'"><a href="#'+srn+'" role="tab" data-toggle="tab"> '+appName+' <button class="close" type="button" title="Remove this page">×</button></a></li>'));
	                $('#tab-content').append($('<div class="tab-pane fade" id="'+srn+'"><iframe src="'+url+'" width="100%" height="600"></iframe></div>'));
	                $('.nav-tabs a[href="#'+srn+'"]').tab('show');
				} else {
				    $('.nav-tabs a[href="#'+srn+'"]').tab('show');
				}
	
				var list = document.getElementById("tab-list");
				new Sortable(list);
	    	}
		</script>
		<link rel="stylesheet" type="text/css" href="css/w3.css" />
	</head>
	
	<body class="bodySize" onload="parseText();">
		<html:form name="consoleForm"  method="post"  action="/console.do" type="com.techm.psd.console.form.ConsoleForm" enctype="multipart/form-data">
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			<!-- Header START -->
				<jsp:include page="/pages/common/header.jsp" />
			<!-- Header End -->
			
			<!-- iFrame: Start -->
			<div>
	            <div style="width: 18%; float: right; margin-right:20px; margin-top: 4px;">
		            	<div class="dropdown pull-right">	
							<button class="btn btn-primary dropdown-toggle pull-right" type="button" data-toggle="dropdown">
								&#9776;<span class="caret"></span>
							</button>
							<ul class="dropdown-menu pull-right">
								<logic:iterate id="urlList" name="consoleForm" property="urlList" >
		                			<li id="btn_<bean:write name="urlList" property="srn" />">
		                				<a href='#<bean:write name="urlList" property="srn" />' title="category" style="color: #344047" onclick="openApp('<bean:write name="urlList" property="srn" />','<bean:write name="urlList" property="lic" />','<bean:write name="urlList" property="desc" />','<bean:write name="urlList" property="url" />');">
			                				<bean:write name="urlList" property="desc" />
			                			</a>
			                		</li> 
								</logic:iterate>
							</ul>
						</div>
					
					<!-- Speech: Start -->
				    <ul class="nav navbar-nav pull-right" style="margin-top:-5px; margin-left:65px;">
			        	<img src="images/mic-enable.png" style="height:28px; margin-top:8px; margin-right: 5px;" alt="Open to application" onclick="openDictation();" id="mic"/>
			        </ul>
				    <!-- Speech: END -->
				</div>
                
                <div style="width: 82%">
	                <!-- Nav tabs -->
		            <ul id="tab-list" class="nav nav-tabs" role="tablist">
		                <li class="active"><a href="#tab1" role="tab" data-toggle="tab">Console</a></li>
		            </ul>
		        </div>
		        
	            <!-- Tab panes -->
	            <div id="tab-content" class="tab-content">
	                <div class="tab-pane fade in active" id="tab1">
	                	<iframe src="/PSDashboard/dashboard.do" width="100%" height="520"></iframe>
	                </div>
	            </div>
			</div>
			<!-- iFrame: END -->
			
			<!--footer start-->
				<jsp:include page="/pages/common/footer.jsp" />
			<!--footer end-->
		</html:form>
	</body>
	
	<script>
		function parseText(){
			var host = window.location.origin;
			var text = getParamValue('text');
			if(text != null){
				var appName = "My Reports";
				var url		= host+"/Configurator/pages/configuratorAction.do?action=getCentopsReport&reportName="+text;
				openTab(4, appName, url);
			}
		}
		function getParamValue(paramName)
		{
		    var url = window.location.search.substring(1); //get rid of "?" in querystring
		    var qArray = url.split('&'); //get key-value pairs
		    for (var i = 0; i < qArray.length; i++) 
		    {
		        var pArr = qArray[i].split('='); //split key and value
		        if (pArr[0] == paramName) 
		            return pArr[1]; //return value
		    }
		}
	</script>
</html>