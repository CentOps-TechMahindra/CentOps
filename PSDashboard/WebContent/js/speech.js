//function startDictation() {
//    if (window.hasOwnProperty('webkitSpeechRecognition')) {
//        var recognition = new webkitSpeechRecognition();
//        recognition.continuous = false;
//        recognition.interimResults = false;
//        recognition.lang = "en-US";
//        recognition.start();
        
//        recognition.onresult = function(e) {
//            document.getElementById('q').value = e.results[0][0].transcript;
//            recognition.stop();
//            document.getElementById('searchForm').submit();
//        };
//        recognition.onerror = function(e) {
//            recognition.stop();
//        }
//    }else{
//        alert("Your browser does not support 'Speech Recognition'");
//    }
//}

function openDictation() {
	$('#mic').attr('src', 'images/mic-disable.png');
	if (window.hasOwnProperty('webkitSpeechRecognition')) {
        var recognition = new webkitSpeechRecognition();
        recognition.continuous = false;
        recognition.interimResults = false;
        recognition.lang = "en-US";
        recognition.start();
        
        recognition.onresult = function(e) {
            var value = e.results[0][0].transcript;
            $('#mic').attr('src', 'images/mic-enable.png');
            speechParser(value.toLowerCase());
        };
        recognition.onerror = function(e) {
            recognition.stop();
            $('#mic').attr('src', 'images/mic-enable.png');
        }
    }else{
        alert("Your browser does not support 'Speech Recognition'");
        $('#mic').attr('src', 'images/mic-enable.png');
    }
}

function speechParser(text){
	var host 				= window.location.origin;
	
	var appName 			= "";
	var url					= "";
	var repKeyCount			= 0;
	var echoKeyCount		= 0;
	var errTrndKeyCount 	= 0;
	var avaKeyCount			= 0;
	var compKeyCount		= 0;
	var issueKeyCount		= 0;
	var backlogKeyCount		= 0;
	
	var repKey 				= ["repcon", "my", "report", "reports", "inventory"];//["repcon", "my", "report", "reports", "inventory"];
	var echoKey 			= ["echo", "intelligent", "search"];//["echo", "error", "errors", "report", "echo"];
	var errortrendKey 		= ["error", "errors", "trend"];//["error", "errors", "trend"];
	var availabilityKey		= ["availability"];
	var complianceKey		= ["compliance", "os", "db", "sdpr", "sox"]; //["compliance", "os", "db", "sdpr", "sox"];
	var issuesKey			= ["open", "issues", "outages", "incidents", "current", "issues", "incidents"]; //["open", "issues", "outages", "incidents", "current", "issues", "incidents"];
	var backlogKey			= ["backlog"];

	var textArr	= text.split(" ");
	
	for (let letter of textArr) {
		if (repKey.indexOf(letter) !== -1){
			repKeyCount++;
		}else if(echoKey.indexOf(letter) !== -1){
			echoKeyCount++;
		}else if(errortrendKey.indexOf(letter) !== -1){
			errTrndKeyCount++;
		}else if(availabilityKey.indexOf(letter) !== -1){
			avaKeyCount++;
		}else if(complianceKey.indexOf(letter) !== -1){
			compKeyCount++;
		}else if(issuesKey.indexOf(letter) !== -1){
			issueKeyCount++;
		}else if(backlogKey.indexOf(letter) !== -1){
			backlogKeyCount++;
		}
	}
  
	if(errTrndKeyCount>0){	
		appName = "Error Trends";
		url		= host+"/eer/report.htm";
		openTab(1, appName, url);
		
	}else if(echoKeyCount>0){	
		appName = "Find Solutions";
		url		= host+"/eer/search.htm";
		openTab(2, appName, url);
		
	}else if(repKeyCount >0){
		appName = "My Reports";
		url		= host+"/Configurator/pages/configuratorAction.do?action=getPortal";
		openTab(4, appName, url);
		
	}else if(avaKeyCount>0){	
		appName = "Availability";
		url		= "http://Availability URL";
		openTab(5, appName, url);
		
	}else if(backlogKeyCount>0){	
		appName = "Backlog Dashboard";
		url		= "http://Backlog Dashboard URL";
		openTab(12, appName, url);
		
	}else if(issueKeyCount>0){	
		appName = "Current Open Incidents";
		url		= host+"/Configurator/pages/configuratorAction.do?action=viewReportDetails&reportName=Current%20Open%20Issues&userId=ck861u#no-back-button";
		openTab(17, appName, url);
		
	}else if(compKeyCount>0){	
		appName = "Compliance";
		url		= host+"/Configurator/pages/configuratorAction.do?action=viewReportDetails&reportName=OS%20Details&userId=ck861u#no-back-button";
		openTab(18, appName, url);
		
	}else {
    	responsiveVoice.speak("SORRY! I did not get you correctly, can you please try again");
    } 
}

//TODO: to provide selection box if same words match in multiple keysArray.
function confirmationBox(){
	$('<div></div>').appendTo('body')
	  .html('<div><h6>Yes or No?</h6></div>')
	  .dialog({
	      modal: true, title: 'message', zIndex: 10000, autoOpen: true,
	      width: 'auto', resizable: false,
	      buttons: {
	          Yes: function () {
	              doFunctionForYes();
	              $(this).dialog("close");
	          },
	          No: function () {
	              doFunctionForNo();
	              $(this).dialog("close");
	          }
	      },
	      close: function (event, ui) {
	          $(this).remove();
	      }
	});
}