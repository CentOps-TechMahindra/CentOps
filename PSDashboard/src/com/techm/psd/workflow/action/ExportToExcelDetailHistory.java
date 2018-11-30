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

import com.techm.psd.common.dto.BuildDTO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;

public class ExportToExcelDetailHistory  extends DispatchAction{

	private Logger LOGGER = Logger.getLogger(ExportToExcelDetailHistory.class);

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
	public void exportToExcelDetailHistory(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)  throws Exception{		
		LOGGER.info("Enters from ExportToExcelDetailsHistory.exportToExcelDetailHistory...");
		
		int workflowId 	= 0;
		int startFlowId	= 0;
		if(request.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(request.getParameter("workflowId").toString());}
		if(request.getParameter("startFlowId") != null) 	{startFlowId		= Integer.parseInt(request.getParameter("startFlowId").toString());}
		try{			
			WorkflowDAOImpl 			wDAO			= new WorkflowDAOImpl();
			List<BuildDTO> buildDTOList = wDAO.getBuildsHistory(workflowId, startFlowId);
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
	public void createSheet(HttpServletResponse response, List<BuildDTO> buildDTOList) throws Exception{
		String excelName = "Details_Report.xls";
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
	    cv.setSize(25 * 256);
	    sheet.setColumnView(1, cv);
	    cv.setSize(15 * 256);
	    sheet.setColumnView(2, cv);
	    cv.setSize(20 * 256);
	    sheet.setColumnView(3, cv);
	    cv.setSize(25 * 256);
	    sheet.setColumnView(4, cv);
	    cv.setSize(10 * 256);
	    setFormats();
	    
	    sheet.addCell(new Label(0, startRow,"USER",			boldHeaderFormat));
    	sheet.addCell(new Label(1, startRow,"Built On Node",boldHeaderFormat));
    	sheet.addCell(new Label(2, startRow,"Jenkins ID",	boldHeaderFormat));
    	sheet.addCell(new Label(3, startRow,"Result",		boldHeaderFormat));
    	sheet.addCell(new Label(4, startRow,"Time",			boldHeaderFormat));
    	//Ends Headers
   	    
    	//Data starts here
    	startRow = startRow +1;
    	for(BuildDTO obj :buildDTOList){
    		sheet.addCell(new Label(0,	startRow,	obj.getUserName(),		stringFormat));
    		sheet.addCell(new Label(1, 	startRow, 	obj.getBuiltOn(),		stringFormat));
    		sheet.addCell(new Label(2,	startRow,	obj.getBuildNo()+"",	numberformat));
    		sheet.addCell(new Label(3,	startRow,	obj.getResult(),		stringFormat));
    		sheet.addCell(new Label(4, 	startRow,	obj.getTimestamp()+"",	stringFormat));
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
