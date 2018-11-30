<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>CentOps - Review Feedback(s)</title>
		 
		<script type="text/ecmascript" src="jqGrid_JS/js/jquery-1.11.0.min.js"></script> 
	    <script type="text/ecmascript" src="jqGrid_JS/js/i18n/grid.locale-en.js"></script>
	    <script type="text/ecmascript" src="jqGrid_JS/js/jquery.jqGrid.min.js"></script>
	    <link rel="stylesheet" type="text/css" href="css/ps-feedback.css"/>
	</head>
	
	<body>
			<!-- Common Include CSS & JS: START-->
				<jsp:include page="/pages/common/common_include.jsp" />
			<!-- Common Include CSS & JS: END-->
			<!-- Header START -->
				<jsp:include page="/pages/common/header.jsp" />
			<!-- Header End -->
		  	<!-- Menu End -->
		  	<!-- Container Start -->
			<div class="container-fluid" style=" min-height: 78vh;">
			<!-- Main Content Area Start -->
			  	<div class="main-container" style="width:100%; min-height: 100%">
			    	<div class="panel panel-danger" style="">
				      	<div class="panel-heading">
				        	<div class="panel-title pull-left">Review Feedback(s)</div>
				        	<div class="clearfix"></div>
					    </div>
					    <!-- main content start -->
								<div class="clearfix"></div>
								<div style="" id="gridDiv">
								    <table id="list"></table>
								    <div id="pager"></div>
								</div>
								<div class="clearfix"></div>
						<!-- main content end -->
					</div>
				</div>
				<!-- Main Content Area END -->
			</div>
			<!-- Container End -->
			
			
			
		<!--footer start-->
		<div style="position:fixed;bottom:0;height:auto;margin-top:40px;width:100%;text-align:center"><jsp:include page="/pages/common/footer.jsp" /></div>
		<!-- footer end-->
	</body>
	<script>
		function SetAllCheckBoxes(FormName, AreaID, CheckValue) {
		    var objCheckBoxes = document.getElementById(AreaID).getElementsByTagName('input');
		    var countCheckBoxes = objCheckBoxes.length;
			if (!countCheckBoxes)
		        objCheckBoxes.checked = CheckValue;
		    else
		        for (var i = 0; i < countCheckBoxes; i++)
		            objCheckBoxes[i].checked = CheckValue;
		}
	</script>
	
	<script type="text/javascript">
	jQuery(document).ready(function() {
        $("#list").jqGrid({
                url 		: "FeedbackAJAX",
                datatype 	: "json",
                mtype 		: 'POST',
                styleUI 	: 'Bootstrap',
                colNames 	: [ 'ID', 'Feedback ID', 'Feedback', 'Workflow Name', 'Start Flow ID', 'Added By', 'Added Date', 'Status', 'Comment', 'Actions'], //
                colModel 	: [ 
	                {	
	                        name 	: 'feedbackId',		//Reference to ID field marked as hidden.
	                        index 	: 'feedbackId',		// Using to pass ID for update row.
	                        editable: true,
		                    sortable: true,
		                    hidden	: true,
		                    readonly: true
	                },{
	                        name 	: 'feedbackId',
	                        index 	: 'feedbackId',
	                        editable: false,
		                    sortable: true,
		                    hidden	: false,
		                    readonly: true
	                }, {
	                        name 	: 'feedback',
	                        index 	: 'feedback',
	                        sortable: false,
	                        classes: "textInDiv",
			            	formatter: function (v) {
				            	if(v != null)
			                    	return '<div>' + $.jgrid.htmlEncode(v) + '</div>';
			                    else
			                    	return '<div></div>';
			                }
	                }, {
	                        name 	: 'workflowName',
	                        index 	: 'workflowName',
	                        sortable: false
	                }, {
	                        name 	: 'startFlowId',
	                        index 	: 'startFlowId',
	                        sortable: false
	                }, {
	                        name 	: 'addedBy',
	                        index 	: 'addedBy',
	                        sortable: false
	                }, {
		                    name 	: 'addedDate',
		                    index 	: 'addedDate',
		                    sortable: false
		            }, {
		                    name 	: 'status',
		                    index 	: 'status',
		                    editable: true,
		                    edittype:"select", 
		                    editoptions: { value: "New:New;Inprogress:Inprogress;Closed:Closed"},
		                    sortable: false
		            }, {
		                    name 	: 'comment',
		                    index 	: 'comment',
		                    edittype: 'textarea',
		                    editrules: {required: true}, 
		                    //editrules: {custom: true,custom_func: function (value, colName) {return validLen(value, colName);}},
		                    editoptions:{rows:'2', cols:'10', maxlength:'1999'},
                    		editable: true,
		                    sortable: false,
		                    classes: "textInDiv",
			            	formatter: function (v) {
				            	if(v != null)
			                    	return '<div>' + $.jgrid.htmlEncode(v) + '</div>';
			                    else
			                    	return '<div></div>';
			                }
	    			}, {
							label: "Actions",
		                    name: "actions",
		                    width: 100,
		                    formatter: "actions",
		                    formatoptions: {
		                        keys: false,						// we want use [Enter] key to save the row and [Esc] to cancel editing.
		                       	//onEdit: function(id){alert("Hi ES"+id);},
		                        //onSuccess: function(){alert("Hi onSuccess");},
		                        onError: function(rowid, jqXHR, textStatus){
			                        //$grid.setGridParam({ search: false, postData: { "filters": ""} ,datatype: 'json'}).trigger("reloadGrid");
			                        // the function will be used as "errorfunc" parameter of editRow function
			                        // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#editrow)
			                        // and saveRow function
			                        // (see http://www.trirand.com/jqgridwiki/doku.php?id=wiki:inline_editing#saverow)
			                        /* alert("in onError used only for remote editing:" +
			                            "\nresponseText=" + jqXHR.responseText +
			                            "\nstatus=" + jqXHR.status +
			                            "\nstatusText" + jqXHR.statusText +
			                            "\n\nWe don't need return anything"); */
								},
		                        //afterSave: function(){alert("Hi AfterSave");},
		                        //afterRestore: function(){alert("Hi afterRestore");},
		                        //checkOnSubmit : function(id){alert("Hi onsub--"+id);},
		                        editformbutton: false,
	                            delbutton: false,
	                            editbutton: true,
		                        editOptions: {},
		                        addOptions: {},
		                        delOptions: {}
		                    }       
		             } ],
        		pager 		: '#pager',
                rowNum 		: 10,
                rowList 	: [ 10, 20, 30 ],
                sortname 	: 'invid',
                sortorder 	: 'desc',
                viewrecords : true,
                gridview 	: true,
              	//caption: 'Review Feedback(s)',
                emptyrecords: "No Data Found",
                autowidth	: true,
                height		: '100%',
            	jsonReader 	: {
	            		repeatitems : false
	            	},
	            editurl 	: "UpdateFeedbackAJAX"
        });
	});

	function getCellValue(rowId, cellId) {
	    var cell = jQuery('#' + rowId + '_' + cellId);        
	    var val = cell.val();
	    return val;
	}
	</script>
</html>