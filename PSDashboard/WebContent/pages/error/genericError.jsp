<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Generic Error Page</title>
	</head>
	<body>
		<div id="pageBackground">
			<div id="wrapper">
	    		<!-- main content start -->
		  		<div class="pageTitle">
		    		<h1 class="pullLeft">Error</h1>
		  		</div>
		  		<!-- main content start -->
		  		<div id="bodyContainer">
					<div class="unauthorized genricError">
						<h1>The server encounter an error while executing your request. </h1>
							Please <a href="#" title="Contact Us">contact CentOps support</a> to resolve this issue. 
		  			</div>
		  		</div>
		  		<!-- main content end -->
			</div>	
	   	</div>
		
		<script>
			$(document).ready(function (){
				var docHeight = $(window).height();
				var fixedHeight = $('#header').outerHeight() + $('.dropdown').outerHeight() + $('.pageTitle').outerHeight() + $('#footer').outerHeight() + 30;
				var scrollConentHeight = docHeight - fixedHeight ;
				$('#bodyContainer').css('height', scrollConentHeight + 'px');
				
				$(window).resize(function() { 
					var docHeight = $(window).outerHeight();
					var fixedHeight = $('#header').outerHeight() + $('.dropdown').outerHeight() + $('.pageTitle').outerHeight() + $('#footer').outerHeight()+30;
					var scrollConentHeight = docHeight - fixedHeight ;
					$('#bodyContainer').css('height', scrollConentHeight + 'px');
				});
			});
		</script>
	</body>
</html>
