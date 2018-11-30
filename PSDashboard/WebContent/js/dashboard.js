/** GRID CONFIG START
 * 
 */
$(document).ready(function () {
	'use strict';
	var filterAppList 				= document.forms['dashboardForm']['filterAppList'].value; 
	var selFilterVal 				= ":[All]"+filterAppList;
	var filterWorkflowTypeJsonStr 	= document.forms['dashboardForm']['filterWorkflowTypeJsonStr'].value; 
	var workflowTYpeFilterList		= ":[All]"+filterWorkflowTypeJsonStr;
	
	var data = document.forms['dashboardForm']['jsonObject'].value;
    var gidData = eval(data),
    	theGrid = $("#theGrid"),
        numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number'};
 
    theGrid.jqGrid({
        datatype: 'local',
        data: gidData,
        colNames: ['ID','Application', 'Workflow Type', 'Workflow Name', 'Action',  'Report', 'Status'],	//, 'Delete'
        colModel: [
                {name: 'workflowId', 	index: 'workflowId', 	width: '5%', sortable:true,  sorttype: "text", editable:true,searchoptions: { sopt: ['eq']},hidden:false, search : true},
                //{name: 'appName', 	 	index: 'appName',		width: '10%', sortable:true,  sorttype: "text", editable:true, 										search : true},
                {name: 'appName', 	 	index: 'appName',		width: '10%', sortable:true,  sorttype: "text", editable:true, stype:'select', hidden:false,		search : true,
    				searchoptions: { value: selFilterVal} 
                },
                {name: 'workflowType', 	index: 'workflowType', 	width: '10%', sortable:true,  sorttype: "text", editable:true, stype:'select', hidden:false,		search : true,
                	searchoptions: { value: workflowTYpeFilterList}
                },
                {name: 'workflowName', 	index: 'workflowName', 	width: '50%', sortable:true,  sorttype: "text", editable:true,										search : true},
				{name: 'action',		index: 'action',		width: '20%', sortable:false, formatter: actionButtons, classes:'action-tool-icons', stype:'label', search : false},
				{name: 'report',		index: 'report',		width: '5%',  sortable:false, formatter: reportButtons, classes:'status-tool-icons', stype:'label', search : false},
				{name: 'status',		index: 'status',		width: '5%',  sortable:true,  formatter: statusButtons, classes:'status-tool-icons', stype:'label', search : false}
				],
        pager: '#gridPager',
        toppager: true, //Toolbar option
        cloneToTop: true, //Toolbar option
        //pagerpos: 'right',
        //recordpos: 'right',
        rownumbers: true, 
        rowNum: 10,
        rowList: [5, 10, 20, 25, 50, 'All'],
        sortname: 'invid',
        sortorder:'desc',
        viewrecords: true, 
        //shrinkToFit: false,
        ignoreCase: true, //Case insensitive search
        gridview: true, //renders data more faster??
        //viewsortcols: true,//easily show what columns are sortable - With this sorting not working.
        //caption: 'Workflow...',
        //emptyrecords: "No Data Found",
        jsonReader: {
    		repeatitems : false
    	},
    	height: '100%',
    	autowidth: true,
    	//editurl: 'WorkflowAJAX',
    	loadonce: true,
	    	loadComplete: function () {
	    		//SET THE ROWLIST 'ALL' VALUE
	    		var val=jQuery("#gridPager div").text().split('of')[jQuery("#gridPager div").text().split('of').length-1].split(' ').join('');
	    		$(".ui-pg-selbox option[value='All']").val(val);
	    		
	    		//Below code is to Hide & Show empty grid message.
	    		if(jQuery("#theGrid").getDataIDs().length==0){
	    			document.getElementById("theGrid").style.display="none";
	    			document.getElementById("noDataDiv").style.display="block";//ui-pg-table ui-common-table ui-paging-pager
	    			$(".ui-paging-pager").hide();
	            }else{
	            	document.getElementById("theGrid").style.display="block";
	            	document.getElementById("noDataDiv").style.display="none";
	            	$(".ui-paging-pager").show();
	            }
	    		
	    		//Below code is to set the Application name on the Grid header
	    		var appName =  $(".ui-search-toolbar #gs_appName").val();
	    		$("#appDiv").html(appName);
	    	}
    	});
    
    //Adding pager options 
    theGrid.jqGrid('navGrid', '#gridPager', { 
    	add: false, 
    	edit: false, 
    	del: false, 
    	refresh:false,
    	cloneToTop: true
    	//search : true
    	},
    	{}, 
    	{}, 
    	{},
        { 	// CLONE TO TOP
        	multipleSearch: true, 
        	overlay: false,
        	closeOnEscape: true, 
        	closeAfterSearch: true
        }
    );
    
    // Adding Filter in grid
    theGrid.jqGrid('filterToolbar', { stringResult: true, searchOnEnter: true, defaultSearch: 'cn', cloneToTop: true });
    
    //Filter common properties
    var filterProp = {
            caption: "Filter",
            title: "Toggle Searching Toolbar",
            buttonicon: 'ui-icon-pin-s',
            onClickButton: function () {
				theGrid[0].toggleToolbar();
            },
            cloneToTop: true
        }; 
    theGrid.jqGrid('navButtonAdd', '#theGrid_toppager',filterProp);		// ADD Filter option in grid top pager 
    theGrid.jqGrid('navButtonAdd', '#gridPager',filterProp);			// ADD Filter option in grid bottom pager
    theGrid[0].toggleToolbar();											// ADD Filter boxes  to search
    
    $('#pg_theGrid_toppager').addClass('ui-jqgrid-pager ui-state-default ui-corner-bottom');	// To divide Top pager in 2 parts: left, right and center 
   
});
// GRID CONFIG END


/**
 * Function to filter the grid on load 
 * Filter by APPLICATION
 * NOT SURE IS THERE ANY OTHER BETTER WAY TO DO THIS !!!!
 */
function filterOnSideBar(filterOptionSelVal){								// filter value
	var mygrid = $("#theGrid");												// grid id
    //$("#theGrid").jqGrid('showCol',['status']);           
    $(".ui-search-toolbar #gs_appName").val(filterOptionSelVal);			// #gs_appName		: Application
    
    //Below code is to clear the filter values.
    $(".ui-search-toolbar #gs_workflowId").val("");							// #gs_workflowId	: Workflow Id
    $(".ui-search-toolbar #gs_workflowType").val("");						// #gs_workflowType	: workflowType
    $(".ui-search-toolbar #gs_workflowName").val("");						// #gs_workflowName	: Workflow Name
    
    //Below code is to trigger the filter
    mygrid[0].triggerToolbar();
    
}


/**
 * GRID formatter buttons
 * 
 * FOR ACTION COLUMN
 */
function actionButtons(cellvalue, options, rowObject){
	var userLevelId = document.getElementById("levelId").value;
	
	var run 			= "<div id=runDiv"+options.rowId+"><a href='javascript:void(0);' title='Start Workflow' 		id=run"+options.rowId+"			class='run' 		onclick=\"jQuery('#theGrid').startFlow('"+options.rowId+"','"+rowObject.workflowId+"','"+rowObject.workflowName+"','"+rowObject.buildType+"');\"></a></div>",
		runHide			= "<div id=runHideDiv"+options.rowId+" style='display:none;'><a href='javascript:void(0);' title='Start Workflow' 		id=run"+options.rowId+"			class='run'></a></div>",
	    edit 			= "<div id=editDiv"+options.rowId+"><a href='javascript:void(0);' title='Edit Workflow' 			id=edit"+options.rowId+"		class='edit glyphicon glyphicon-edit' 		onclick=\"jQuery('#theGrid').workflowEdit('" + rowObject.workflowId + "');\"></a></div>",
	    history 		= "<div id=historyDiv"+options.rowId+"><a href='javascript:void(0);' title='Workflow History' 		id=historical"+options.rowId+"	class='historical' 	onclick=\"jQuery('#theGrid').workflowHistory('" + options.rowId + "','"+rowObject.workflowId+"');\"></a></div>",
	    remove 			= "<div id=removeDiv"+options.rowId+"><a href='javascript:void(0);' title='Remove Workflow' 		id=remvoe"+options.rowId+"		class='remove glyphicon glyphicon-remove-circle' 		onclick=\"jQuery('#theGrid').restoreRow('" + options.rowId + "'); alert('TODO: Remove')\"></a></div>",
	   
	    runDisable 		= "<div id=runDiv"+options.rowId+"><a href='javascript:void(0);' title='Start Workflow' 		id=run"+options.rowId+"			class='run' 		onclick=\"alert('You are not authorized!');\"></a></div>",
	    editDisable 	= "<div id=editDiv"+options.rowId+"><a href='javascript:void(0);' title='Edit Workflow' 		id=edit"+options.rowId+"		class='edit glyphicon glyphicon-edit' 		onclick=\"alert('You are not authorized!');\"></a></div>",
		historyDisable 	= "<div id=historyDiv"+options.rowId+"><a href='javascript:void(0);' title='Workflow History' 		id=historical"+options.rowId+"	class='historical' 	onclick=\"alert('You are not authorized!');\"></a></div>",
		removeDisable 	= "<div id=removeDiv"+options.rowId+"><a href='javascript:void(0);' title='Remove Workflow' 		id=remvoe"+options.rowId+"		class='remove glyphicon glyphicon-remove-circle' 		onclick=\"alert('You are not authorized!');\"></a></div>";
	
	if(userLevelId == 10){
 		return run+runHide+history+edit+remove;	
	}else if(userLevelId == 5 ){
 		return run+runHide+history+editDisable+removeDisable;	
	}else if(userLevelId == 1 ){
 		return runDisable+historyDisable+editDisable+removeDisable;
 	}
	
}

/**
* GRID formatter buttons
* 
* FOR STATUS COLUMN
*/
function statusButtons(cellvalue, options, rowObject){
	var offline = "<a href='#' title='Status' 	id=statusOffline"+options.rowId+"	class='offline' 	></a>";
	return offline;
}

/**
* GRID formatter buttons
* 
* FOR REPORT COLUMN
*/
function reportButtons(cellvalue, options, rowObject){
	var userLevelId = document.getElementById("levelId").value;
	
	if(userLevelId >= 5){
		var reportB 	= "<a href='javascript:void(0);' title='Report' id=refresh"+options.rowId+"	class='editWorkflow' onclick=\"jQuery('#theGrid').showReport('"+options.rowId+"','"+rowObject.workflowId+"');\"></a>";
	}else{
		var reportB 	= "<a href='javascript:void(0);' title='Report' id=refresh"+options.rowId+"	class='editWorkflow' onclick=\"alert('You are not authorized!');\"></a>";
	}
	return reportB;
}