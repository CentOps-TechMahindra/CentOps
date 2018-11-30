package com.techm.psd.workflow.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class ExportToExcelHistory extends DispatchAction{

	private Logger LOGGER = Logger.getLogger(ExportToExcelHistory.class);

	private WritableFont noboldDataFont = null;	
    private WritableCellFormat stringFormat = null;
    private WritableCellFormat numberformat = null;
    private WritableCellFormat wrapDataFormat = null;    
	private WritableFont boldHeaderFont = null; 	
	private WritableCellFormat boldHeaderFormat = null;
	private WritableCellFormat boldTitleHeaderFormat = null;
	
	/**Method to get the list from Session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportToExcelHistory(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)  throws Exception{		
		LOGGER.info("Enters from ExportToExcelHistory.exportToExcelHistory...");
		int workflowId 		= 0;
		String action		= "";
		
		if(request.getParameter("action") != null) 			{action			= request.getParameter("action").toString();}
		
		if(request.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(request.getParameter("workflowId").toString());}
		
		try{			
			WorkflowDAOImpl 			wDAO			= new WorkflowDAOImpl();
			List<StartFlowHistoryDTO> 	buildDTOList 	= wDAO.getStartFlowHistory(workflowId, action);
			this.createSheet(response, buildDTOList);		
		}
		catch(Exception e){
			LOGGER.error("Error while exporting ExportToExcelHistory.exportToExcelHistory report details!!", e);
		}	
		LOGGER.info("Exits from ExportToExcelHistory.exportToExcelHistory...");
	}
	
	/**Creates Excel sheet
	 * @param response
	 * @param reqList
	 * @throws Exception
	 */
	public void createSheet(HttpServletResponse response, List<StartFlowHistoryDTO> buildDTOList) throws Exception{
		String excelName = "Report.xls";
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + excelName);
		OutputStream out= response.getOutputStream();
				
		WritableWorkbook workbook = Workbook.createWorkbook(out); 
   	    WritableSheet sheet = workbook.createSheet("Report", 0);
   	    int startRow = 0;
   	    
   	    //Headers start here
		CellView cv = new CellView();
	    cv.setSize(20 * 256);
	    sheet.setColumnView(0, cv);	
	    cv.setSize(20 * 256);
	    sheet.setColumnView(1, cv);
	    cv.setSize(25 * 256);
	    sheet.setColumnView(2, cv);
	    cv.setSize(25 * 256);
	    sheet.setColumnView(3, cv);
	    cv.setSize(15 * 256);
	    sheet.setColumnView(4, cv);
	    cv.setSize(10 * 256);
	    sheet.setColumnView(5, cv);
	    cv.setSize(10 * 256);
	    setFormats();
	    
	    sheet.addCell(new Label(0, startRow,"Executed From",boldHeaderFormat));
    	sheet.addCell(new Label(1, startRow,"CentOps Console ID",	boldHeaderFormat));
    	sheet.addCell(new Label(2, startRow,"Executed By",	boldHeaderFormat));
    	sheet.addCell(new Label(3, startRow,"Date & Time",	boldHeaderFormat));
    	sheet.addCell(new Label(4, startRow,"Jenkins Status",		boldHeaderFormat));
    	sheet.addCell(new Label(5, startRow,"Report Status",		boldHeaderFormat));
    	
    	//Ends Headers
   	    
    	//Data starts here
    	startRow = startRow +1;
    	for(StartFlowHistoryDTO obj :buildDTOList){
    		sheet.addCell(new Label(0,	startRow,	"Dashboard",			stringFormat));
    		sheet.addCell(new Label(1, 	startRow, 	obj.getStartFlowId()+"",numberformat));
    		sheet.addCell(new Label(2,	startRow,	obj.getStartBy(),		stringFormat));
    		sheet.addCell(new Label(3,	startRow,	obj.getStartTime()+"",	stringFormat));
    		sheet.addCell(new Label(4, 	startRow,	obj.getJenkinsStatus(),	stringFormat));
    		sheet.addCell(new Label(5, 	startRow,	obj.getReportStatus(),	stringFormat));
    		startRow++;
    	}
    	workbook.write();
		workbook.close();
		out.flush();		
		out.close();
    }
	
	
	/**This is a common method to set all font/cell formats.
	 * @throws Exception
	 */
	private void setFormats() throws Exception{
		noboldDataFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);		
	    stringFormat = new WritableCellFormat(noboldDataFont);
	    numberformat = new WritableCellFormat(NumberFormats.INTEGER);
	    wrapDataFormat = new WritableCellFormat(noboldDataFont);    
		boldHeaderFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD); 	
		boldHeaderFormat = new WritableCellFormat(boldHeaderFont);
		boldTitleHeaderFormat = new WritableCellFormat(boldHeaderFont);
		//Set formats
		stringFormat.setAlignment(Alignment.LEFT);
		stringFormat.setVerticalAlignment(VerticalAlignment.TOP);
		stringFormat.setWrap(true);
		numberformat.setAlignment(Alignment.LEFT);
		numberformat.setVerticalAlignment(VerticalAlignment.TOP);
		wrapDataFormat.setWrap(true);
		wrapDataFormat.setAlignment(Alignment.LEFT);
		boldHeaderFormat.setBackground(Colour.LIGHT_GREEN);
		boldHeaderFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		boldHeaderFormat.setAlignment(Alignment.LEFT);		
		boldTitleHeaderFormat.setBackground(Colour.YELLOW);
		boldTitleHeaderFormat.setBorder(Border.ALL, BorderLineStyle.THIN, Colour.BLACK);
		boldTitleHeaderFormat.setAlignment(Alignment.CENTRE);
	}
	

}
