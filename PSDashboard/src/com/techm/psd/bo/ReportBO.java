package com.techm.psd.bo;

import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.techm.psd.bean.DatabaseBean;
import com.techm.psd.common.dto.FixItDTO;
import com.techm.psd.common.dto.WorkflowDTO;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.constants.AppConstants;
import com.techm.psd.exception.PSDException;
import com.techm.psd.services.DBConnectionServices;
import com.techm.psd.workflow.dao.WorkflowDAO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class ReportBO {

	private Logger log = Logger.getLogger(ReportBO.class);
	
	/*public String getReportDataOverlayStr(int workflowId){
		log.info(AppConstants.LOGGING_ENTER_METHOD+ " getPreviewTableList");
		JSONArray jArray 		= new JSONArray();
		List<String> columnList	= new ArrayList<String>();
		WorkflowDAOImpl wDAO	= new WorkflowDAOImpl();
		String responseStr  	= "";
		String query 			= "";
		DatabaseBean dbBean 	= null;
		Connection con 			= null;
		DBConnectionServices dbConnectionServices = new DBConnectionServices();
		
		try {
			dbBean	= wDAO.getReportDBDetails(workflowId);
			query	= dbBean.getQuery();
			if(isValidConnection(dbBean)){
				con 			= dbConnectionServices.getDatabaseConnection(dbBean);
				wDAO.getReportJSON(con, query, columnList, jArray);
				responseStr  	= generateReportOverlay(workflowId, columnList, jArray);
			}else{
				responseStr		= "Please check your Report DB Credentials!";
			}
			
		}catch (Exception e){
				responseStr		= "Please check your Report DB Details!";
				e.printStackTrace();
		}
		return responseStr; 
	}*/
	
	public String getReportDataOverlayForBuildNos(int workflowId, List<Integer> buildNos, int startFlowId){
		log.info(AppConstants.LOGGING_ENTER_METHOD+ " getPreviewTableList");
		JSONArray jArray 		= new JSONArray();
		List<String> columnList	= new ArrayList<String>();
		WorkflowDAOImpl wDAO	= new WorkflowDAOImpl();
		String responseStr  	= "";
		String query 			= "";
		DatabaseBean dbBean 	= null;
		Connection con 			= null;
		DBConnectionServices dbConnectionServices = new DBConnectionServices();
		
		try {
			dbBean	= wDAO.getReportDBDetails(workflowId);
			StringBuilder buildNosStr = new StringBuilder();
			Iterator<Integer> itr = buildNos.iterator();
			while(itr.hasNext()){
				int buildNo = (Integer) itr.next();
				if (buildNosStr.length() > 0) {
					buildNosStr.append(",");
				}
				buildNosStr.append(buildNo);
			}
			
			query	= "SELECT * FROM "+dbBean.getReportTable()+" WHERE BUILD_NUMBER IN ("+buildNosStr+") ORDER BY RESULT, BUILD_NUMBER DESC"; 
			log.info(AppConstants.LOGGING_ENTER_METHOD+ " query: "+query);
			if(isValidConnection(dbBean)){
				con 			= dbConnectionServices.getDatabaseConnection(dbBean);
				wDAO.getReportJSON(con, query, columnList, jArray);
				if(jArray.length()>0){
					responseStr  	= generateReportOverlay(workflowId, columnList, jArray, startFlowId);
				}else{
					responseStr  	= "No Data Found in Report database for build no(s): "+buildNosStr;
				}
			}else{
				responseStr		= "Unable to establish connection to Report database. Please check your Report DB Credentials!";
			}
			
		}catch (Exception e){
			responseStr		= "Unable to establish connection to Report database. Please check your Report DB Credentials!";
			log.error("SQLException in ReportBO.getReportDataOverlayForBuildNos()>>"+e.getMessage());
			e.printStackTrace();
		}
		return responseStr; 
	}
	
	public List<String> getReportBuildsResult(int workflowId, List<Integer> buildNoList, DatabaseBean dbBean){
		log.info(AppConstants.LOGGING_ENTER_METHOD+ " getPreviewTableList");
		WorkflowDAOImpl wDAO	= new WorkflowDAOImpl();
		String query 			= "";
		Connection con 			= null;
		DBConnectionServices dbConnectionServices = new DBConnectionServices();
		List<String> resultList = new ArrayList<String>();
		try {
			
			StringBuilder buildNosStr = new StringBuilder();
			Iterator<Integer> itr = buildNoList.iterator();
			while(itr.hasNext()){
				int buildNo = itr.next();
				if (buildNosStr.length() > 0) {
					buildNosStr.append(",");
				}
				buildNosStr.append(buildNo);
			}
			query	= "SELECT RESULT FROM "+dbBean.getReportTable()+" WHERE BUILD_NUMBER IN ("+buildNosStr+")"; 
			
			if(isValidConnection(dbBean)){
				con 			= dbConnectionServices.getDatabaseConnection(dbBean);
				resultList = wDAO.getReportResultHistory(con, query);
			}else{
				log.info("ReportBO.getReportBuildsResult() Invalid Data Connection!!!");
			}
		}catch (Exception e){
			log.error("SQLException in ReportBO.getReportBuildsResult()>>"+e.getMessage());
			e.printStackTrace();
		}
		return resultList; 
	}
	
	public boolean isValidConnection(DatabaseBean bdBean){
		log.info(AppConstants.LOGGING_ENTER_METHOD+ " isConnectionAvailable");
		
		DBConnectionServices dbConnectionServices = new DBConnectionServices();
		Connection con 	= null;
		boolean response = false; 
		try {
			con = dbConnectionServices.getDatabaseConnection(bdBean);
			if(con!= null){
				response=true;
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log.error("Exception in ReportBO.isValidConnection() !!!"+e.getMessage());
		}
		log.info(AppConstants.LOGGING_EXIT_METHOD+ " isConnectionAvailable");
		return response;
	}
	
	public String generateReportOverlay(int workflowId, List<String> columnList, JSONArray jArray, int startFlowId) throws JSONException, PSDException{
		SimpleDateFormat 	dateFormat 	= new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		String str 			= "";
		String key 			= "";
		String tableThStr  	= "<th style='padding-left: 10px;background-color: SILVER;padding: 5px;	border: 1px solid #45adff;line-height: 24px;font-weight: bold;text-align: center;color:BLACK'>";
		String openingTr	= "<tr style='background-color:#efe;'>";
		String openingTd	= "<td style=' padding-left: 10px;padding: 5px;border: 1px solid #ccc;width: 11.11111111%;'>";
		
		String resultStatus	= "";
		String errorCode	= "";
		
		WorkflowDAO 		wDAO		= new WorkflowDAOImpl();
		List<FixItDTO> 		fixItList 	= new ArrayList<FixItDTO>(); 
		ArrayList<Integer> 	intList 	= new ArrayList<Integer>();
		
		fixItList 	= wDAO.getFixItDTOList(workflowId);
		if(fixItList.size()>0){
			intList = getErrorCodeAssociatedWithWorkflow(fixItList);
		}
		str = str+"<div id='detailsHistoryDiv' style='border:0px solid;'> Report Status"+
				"<table style='width:100%; border: 1px solid black; border-collapse: collapse; padding-bottom:10px;'>" +
					"<tbody style='border: 1px solid black; border-collapse: collapse;'> "+
						"<tr>";
			
						//Generate Report HEADER: START
						Iterator<String> itr1 = columnList.iterator();
						while(itr1.hasNext()){
							key = itr1.next().toString();
							str = 	str + tableThStr + key + "</th>";
						}
						str = str+tableThStr+ "</tr><tr>";
						//Generate Report HEADER: END
						
						//Iterate Data JSON: START
						for (int i = 0; i < jArray.length(); i++){ 
							JSONObject jObj = (JSONObject) jArray.get(i);
							Iterator<String> itr = columnList.iterator();
							
							str = str +	openingTr;
							while(itr.hasNext()){
								key = itr.next().toString();
								String 	val = jObj.get(key).toString();
								
								/*if(key.contains("DATE")){
									str = str+ openingTd + parseReportDateString(val) +"</td>";
								}else{*/
									str = str+ openingTd + val +"</td>";
								/*}
								if(key.equals("RESULT")){
									resultStatus = val;
								}
								if(key.equals("ERROR_CODE")){
									errorCode = val;
								}*/
							}
							
							//Below code is to add "Fixit, Document & Feedback" option in Report: START
							str = str+openingTd;
								/*if(resultStatus.equals("FAILURE") && intList.size() > 0 && intList.contains(Integer.parseInt(errorCode))){
									int fixitWorkflowId = getWorkflowIdByErrorCode(fixItList, Integer.parseInt(errorCode));
									WorkflowDTO wDTO 	= wDAO.getWorkflowDetailsByID(fixitWorkflowId);
									String workflowName	= wDTO.getWorkflowName();
									String buildType	= wDTO.getBuildType();
									//str = str+	"<a href = '#' class='indicator glyphicon glyphicon-wrench    pull-left' id='fixit' 	onclick=\"jQuery('#theGrid').startFixitFlowAjax('"+fixitWorkflowId+"','"+buildType+"');\"		></a>" +
									str = str+	"<a href = '#' class='indicator glyphicon glyphicon-wrench    pull-left' id='fixit' 	onclick=\"jQuery('#theGrid').startFixitFlow('"+workflowName+"','"+fixitWorkflowId+"','"+buildType+"');\"		></a>" +
												"<a href = '#' class='indicator glyphicon glyphicon-file      pull-left' id='document' 	onclick=\"jQuery('#theGrid').underConsDialog();\"		></a>" +
												"<a href = '#' class='indicator glyphicon glyphicon-thumbs-up pull-left' id='feedback' 	onclick=\"jQuery('#theGrid').saveFeedback('"+workflowId+"','"+startFlowId+"');\"	></a>" ;
								}else{
									str = str+	"<a href = '#' class='indicator glyphicon glyphicon-ban-circle pull-left' id='fixit' 	onclick=\"alert('Fixit workflow not configured!');\"></a>" +
												"<a href = '#' class='indicator glyphicon glyphicon-file      pull-left' id='document' 	onclick=\"jQuery('#theGrid').underConsDialog();\"		></a>" +
												"<a href = '#' class='indicator glyphicon glyphicon-thumbs-up pull-left' id='feedback' 	onclick=\"jQuery('#theGrid').saveFeedback('"+workflowId+"','"+startFlowId+"');\"	></a>" ;
								}*/
										
							str = str+"</td>"+
								"</tr>";
							//Above code is to add "Fixit, Document & Feedback" option in Report: END
						}
						//Iterate Data JSON: START
					str = str+"</tbody></table></div>";
		return str;
	}
	
	private static ArrayList<Integer> getErrorCodeAssociatedWithWorkflow(List<FixItDTO> fixItList){
		ArrayList<Integer> intList 	= new ArrayList<Integer>();
		Iterator itr = fixItList.iterator();
		while(itr.hasNext()){
			FixItDTO f = (FixItDTO) itr.next();
			intList.add(f.getErrorCode());
		}
		return intList;
	}
	
	private static int getWorkflowIdByErrorCode(List<FixItDTO> fixItList, int errorCode){
		ArrayList<Integer> intList 	= new ArrayList<Integer>();
		Iterator itr = fixItList.iterator();
		int workflowId = 0;
		while(itr.hasNext()){
			FixItDTO f = (FixItDTO) itr.next();
			if(f.getErrorCode() == errorCode){
				workflowId = f.getFixitWorkflowId();
			}
		}
		return workflowId;
	}
	
	public String parseReportDateString(String dateStr) {
		Date dt = (Date) CommonUtil.convertStringToDate(dateStr, "yyyy-MM-dd hh:mm:ss");
		return CommonUtil.convertDateToString(dt, "MM/dd/yyyy hh:mm:ss");
	}
}
