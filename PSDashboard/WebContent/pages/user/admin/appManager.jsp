<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Centops App Manager</title>
	</head>
	<body style="background: linear-gradient(#B0B0B0 , #F0F0F0 ); overflow-x: hidden; min-height: 100vh;" class="">
		<!-- Common Include CSS & JS: START-->
			<jsp:include page="/pages/common/common_include.jsp" />
		<!-- Common Include CSS & JS: END-->
		<!-- Header START -->
			<jsp:include page="/pages/common/header.jsp" />
		<!-- Header End -->
		
		<br />
		<br />
		
		<table align="center">
			<tr>
				<td>
					<div class="">
						<div>
							<table width="100%">
								<tr>
									<td style="padding: 5px 5px 5px 5px;" valign="top">
										<div id="mainData"></div>
									</td>
									<td style="padding: 17px 5px 5px 5px;" valign="top">
										<div id="PersonTableContainer"></div> <br />
										<div id="AddUrl" style="width: 100%; border-radius: 10px; background: linear-gradient(#F0F0F0, #B0B0B0); border: 1px solid #909090; padding: 10px;">
											<form id="frmAddUrl" name="frmAddUrl" method="post">
												<table>
													<tr>
														<td>URL<font color="red" size="4"><b>*</b></font>: 
															<input type="text" id="urlId" name="urlId"></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
														<td>Description<font color="red" size="4"><b>*</b></font>: 
															<input type="text" id="descripId" name="descripId"></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
														<td>LIC: 
																Yes <input type="radio" id="inputemail1" name="inputemail1" value="Yes" checked="checked"></input> &nbsp;&nbsp;
    															No <input type="radio" id="inputemail1" name="inputemail1" value="No"></input>&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
														<td>
															<input type="hidden" name="inputemail" id="inputemail" />
															<button onclick="setRadioValue();" style="border-radius: 2px;">Add</button>
														</td>
													</tr>
												</table>
											</form>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</td>
			</tr>
		</table>
		
		<div style="padding-left: 50%; margin-top:10px;">
	    	<a href="console.do" title="back to Console" class="btnSubmit" style="">Console</a>
	    	<a href="#" title="back" class="btnSubmit" style="" onclick="jsGoBack();">Back</a>
	    </div>
	    
       	<!--footer start-->
		<div style="position:fixed;bottom:0;height:auto;margin-top:40px;width:100%;text-align:center"><jsp:include page="/pages/common/footer.jsp" /></div>
		<!--footer end-->
	</body>
	
	<!-- Include one of jTable styles. -->
	<link rel="stylesheet" type="text/css" href="css/metro/crimson/jtable.css"/>
	<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.10.3.custom.css?n=1"/>
	<link rel="stylesheet" type="text/css" href="css/appManager.css">
	
	<!-- Include jTable script file. -->
	<script type="text/javascript" src="js/jquery-1.8.2.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.10.3.custom.js"></script>
	<script type="text/javascript" src="js/jquery.jtable.js"></script>
	<script type="text/javascript" src="js/appManager.js"></script>
</html>