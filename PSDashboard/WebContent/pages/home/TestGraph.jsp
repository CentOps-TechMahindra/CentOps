<!DOCTYPE html>
<html>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<head>
		<script type="text/javascript" src="js/highcharts.js"></script> 
		<script type="text/javascript" src="js/highcharts-more.js"></script>
		<script src="js/jquery-1.8.2.js" type="text/javascript"></script> 
		<script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
		<script src="js/solid-gauge.js"></script>
		
		<link href="css/small-business.css" rel="stylesheet">
		<style>
		.highcharts-yaxis-grid .highcharts-grid-line {
			display: none;
		}
		</style>
		<script>

jQuery(document).ready(function() { 
    $.ajax({url: "UserController?action=loadLables", 
   	 async: false,
       dataType: "text",
		 success: function(result){
			 var str=result.split(';');
			 
			var lineHeader =str[0];
			var xTitle   =str[1];
			var yTitle   =str[2];
			var xRange   =str[3];
			var xInterval  =str[4];
			var lQuery   =str[5];
			var refRate   =parseInt(str[6]);

		var st=xRange.split('-');
		var arr=new Array();
		var j=0;
	
		var cnt=0;
		var lmt=0;
		cnt=parseInt(st[0]);
		lmt=(parseInt(st[1]));
	//	alert(lmt);
	for(;cnt<=lmt;)
		{
		arr[j]=cnt;
		cnt=cnt+parseFloat(xInterval);
		j++;
	//	alert(cnt);
		}
//	alert(arr);

		var mss_dt= new Array;
	    var options = {
	        chart: {
	            renderTo: 'container',
	            type: 'spline',
	            animation: Highcharts.svg
	        },
	        title: {
	            text: lineHeader,
	            x: 0 //center
	        },
	      
	        xAxis: { title: {
	            text: xTitle
	        },
	        categories:arr
	 //       categories:[ '00','01', '02', '03', '04', '05', '06',
//			                '07', '08', '09', '10', '11', '12','13', '14', '15', '16', '17', '18', '19',
//			                '20', '21', '22', '23']
	        },
	        yAxis: {
	            title: {
	                text: yTitle
	            },
	            
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        credits: {
	            enabled: false
	        },
	        tooltip: {
	            valueSuffix: 'Counts'
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'middle',
	            borderWidth: 0
	        },
	        series: []
	    };
	    
	  
	    $.ajax({
			    type: "GET",
			    url: "UserController?action=LinegrphData&lQuery="+lQuery,
			    dataType: "json",
			    //if received a response from the server
	         		success: function(response) {
		  for (var i = 0; i < response.length; i++) {	
			 // alert (response[i].stream);
			 //   alert (JSON.parse("[" + response[i].values + "]"));
				options.series.push({
				    name: response[i].stream,
				    data: JSON.parse("[" + response[i].values + "]"),
				    
			//	    categories:response[i].length
				})
		  }
		//alert(response);
		/*var name = 'MMS'
		var mySeries = [];
		options.series.push({
		    name: name,
		    data: response
		},
		{
		    name: 'SMS',
		    data: response
		})*/
		
		
	    var chart = new Highcharts.Chart(options);   
	}
    
	
    });
    

    setInterval(function () {
    	var chart=new Highcharts.Chart(options);
    	  $.ajax({
    			    type: "GET",
    			    url: "UserController?action=LinegrphData&lQuery="+lQuery,
    			    dataType: "json",
    			  
    	         		success: function(response) {
    		  for (var i = 0; i < response.length; i++) {	
    			  //alert (response[i].stream);
    			   // alert (JSON.parse("[" + response[i].values + "]"));
    			  
    			   chart.series[i].update({
                 //      x:[],
    				    name: response[i].stream,
    				    data: JSON.parse("[" + response[i].values + "]")
    				    //categories:response[i].length
    				    
    				})
    				//alert (response[i].length);
    		  }
    	    //chart.redraw();
    	}
	    	  });
	    	  //alert("Pushed");
	    }, refRate);
		
		
		
		
		 }
		 
  
   }); 
});


</script>


<script>
$(function () {
	
    $.ajax({url: "GaugeData?lables=yes", 
    	 async: false,
        dataType: "text",
		 success: function(result){
		 
		 
		 callgaugesfun(result);
		 }
		 
   
    }); 
	
	
});
function	callgaugesfun(result)
	{
	 var str=result.split(";");
	// alert(str);
	 var firstGaugeName =str[0];
	 var   secondGaugeName =str[1];
	 var    minRange =str[2];
	 var    maxRange=str[3];
	 var    dataInfo =str[4];
	 var   dataQuery =str[5];
	 var refRate   =parseInt(str[6]);
	// alert(firstGaugeName);alert(dataQuery);
		
	 var gaugeOptions = { 
			 
		        chart: { 
		            type: 'solidgauge' 
		        }, 
		 
		        title: null, 
		 
		        pane: { 
		            center: ['50%', '85%'], 
		            size: '140%', 
		            startAngle: -90, 
		            endAngle: 90, 
		            background: { 
		                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || '#EEE', 
		                innerRadius: '60%', 
		                outerRadius: '100%', 
		                shape: 'arc' 
		            } 
		        }, 
		 
		        tooltip: { 
		            enabled: false 
		        }, 
		 
		        // the value axis 
		 
		        yAxis: { 
		            stops: [ 
		                [0.1, '#55BF3B'], // green 
		                [0.5, '#55BF3B'], // yellow 
		                [0.9, '#55BF3B'] // red 
		            ], 
		 
		 
		            lineWidth: 0, 
		            minorTickInterval: null, 
		            tickAmount: 2, 
		            title: { 
		                y: -70 
		            }, 
		            labels: { 
		                y: 16 
		            } 
		        }, 
		 
		        plotOptions: { 
		            solidgauge: { 
		                dataLabels: { 
		                    y: 5, 
		                    borderWidth: 0, 
		                    useHTML: true 
		                } 
		            } 
		        } 
		    }; 
		 
		    // The speed gauge 
		    var chartSpeed = Highcharts.chart('container-speed', Highcharts.merge(gaugeOptions, { 
		        yAxis: { 
		            min: minRange, 
		            max: maxRange, 
		            title: { 
		                text: firstGaugeName
		            } 
		        }, 
		 
		        credits: { 
		            enabled: false 
		        }, 
		 
		        series: [{ 
		            name: firstGaugeName, 
		            data: [235], 
		            dataLabels: { 
		                format: '<div style="text-align:center"><span style="font-size:15px;color:' + 
		                    ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' + 
		                       '<span style="font-size:12px;color:silver">'+dataInfo+'</span></div>' 
		            }, 
		            tooltip: { 
		                valueSuffix: ' Near Realtime' 
		            } 
		        }] 
		 
		    })); 
		 
		    // The RPM gauge 
		    var chartRpm = Highcharts.chart('container-rpm', Highcharts.merge(gaugeOptions, { 
		        yAxis: { 
		            min: minRange, 
		            max: maxRange, 
		            title: { 
		                text: secondGaugeName 
		            } 
		        }, 
		 
		        credits: { 
		            enabled: false 
		        }, 
		 
		        series: [{ 
		            name: secondGaugeName, 
		            data: [290], 
		            dataLabels: { 
		                format: '<div style="text-align:center"><span style="font-size:15px;color:' + 
		                ((Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black') + '">{y}</span><br/>' + 
		                '<span style="font-size:12px;color:silver">'+dataInfo+'</span></div>' 
		            }, 
		            tooltip: { 
		                valueSuffix: ' Approx' 
		            } 
		        }] 
		 
		    })); 
		 	
		 	
		 				callValues();
		 				setInterval(callValues, refRate);
		 		function callValues() {
		 		    // Speed
		 		    var point,point2,
		 		        newVal;
		 		   
		 		        
		 		    var i=0;
		 		   var j=0;
		 		    $.ajax({url: "GaugeData?lables=no", 
		 		    	async: false,
		 		    	type: "GET",
		 		       data : {"lables":"no","query":dataQuery},
		 		        dataType: "text",
		 				 success: function(result){
		 					
		 			    var ss=result.split(':');
		 			    
		 			       i=ss[0];
		 			      j=ss[1];
		 			 
		 			     if (chartRpm) {
			 		 			point2 = chartRpm.series[0].points[0];
				 	 		  //      inc = Math.random() - 0.5;
				 	 		
				 		  			point2.y=(j);
				 		 			newVal= point2.y;
				 		 		
				 		 			point2.update(newVal);
				 	 		    }
		 			  
		 		 		   if (chartSpeed) {
		 		 		        point = chartSpeed.series[0].points[0];
		 		 	
		 		 		    point.y= i;
		 		 		 
		 		 		        newVal= point.y;
		 		 		    
		 		 		        point.update(newVal);
		 		 				
		 		 		    }
		 		 {}
		 		 		  
		 			   
		 			    }
		 		    });
		 			 
		 	
		 		  

		 		    // RPM
		 		
		 		}

	}

</script>

	</head>
	<body>
		<div class="row">
			<div class="col-md-8" Style = "margin: 1% 0 0 -5%">
				<div class = "PersonTableContainer" style="width: 600px; height: 200px; margin: 0 auto">
    				<div id="container-speed" style="width: 300px; height: 200px; float: left"></div>
    				<div id="container-rpm" style="width: 300px; height: 200px; float: left"></div>
				</div>
			</div>
			<div class="col-md-4" Style = "margin: 1% 0 0 -6%">
				<a href="#">
					<div class = "PersonTableContainer" id="container" style="min-width: 475px; height: 200px; margin: 0 auto"></div>
				</a>
			</div>
		</div>
	</body>
</html>
