package com.techm.psd.workflow.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.techm.psd.common.dto.StartFlowHistoryDTO;
import com.techm.psd.workflow.dao.WorkflowDAOImpl;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ExportToPdfHistory extends DispatchAction{

	private Logger LOGGER 				= Logger.getLogger(ExportToPdfHistory.class);
	private static Font FONT_NORMAL 	= FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL);
	private static Font FONT_BOLD 		= FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);
	
	/**Method to get the list from Session
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void exportToPdfHistory(ActionMapping mapping, ActionForm form,
			   	HttpServletRequest request, HttpServletResponse response)  throws Exception{		
		LOGGER.info("Enters from ExportToExcelHistory.exportToPdfHistory...");
		int workflowId 		= 0;
		String action		= "";
		
		if(request.getParameter("action") != null) 			{action			= request.getParameter("action").toString();}
		
		if(request.getParameter("workflowId") != null) 		{workflowId		= Integer.parseInt(request.getParameter("workflowId").toString());}
		
		try{			
			WorkflowDAOImpl 			wDAO			= new WorkflowDAOImpl();
			List<StartFlowHistoryDTO> 	buildDTOList 	= wDAO.getStartFlowHistory(workflowId, action);
			this.createPdf(response, buildDTOList);
		}
		catch(Exception e){
			LOGGER.error("Error while exporting ExportToExcelHistory.exportToPdfHistory report details!!", e);
		}	
		LOGGER.info("Exits from ExportToExcelHistory.exportToPdfHistory...");
	}
	
	/**
     * Creates a PDF document.
     * @param filename the path to the new PDF document
     * @throws    DocumentException 
     * @throws    IOException 
     */
    public void createPdf(HttpServletResponse response, List<StartFlowHistoryDTO> 	buildDTOList)
				throws DocumentException, IOException {
        /*// step 1
        Document document = new Document();
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(filename));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World!"));
        // step 5
        document.close();*/
    	
    	String pdfName = "Report.pdf";
		response.reset();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + pdfName);
		OutputStream out= response.getOutputStream();
		
        
        Document document = new Document();
	    try
	    {
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        document.open();
	        
	        //Create PDF Table and set columns size, width and padding... 
	        PdfPTable table = new PdfPTable(6); 	// 6 columns.
	        table.setWidthPercentage(100); 			// Width 100%
	        table.setSpacingBefore(10f); 			// Space before table
	        table.setSpacingAfter(10f); 			// Space after table
	 
	        //Set Column widths
	        float[] columnWidths = {1f, 1f, 1f, 1f, 1f, 1f};
	        table.setWidths(columnWidths);
	        
	        //Add PDF HEADER Values
	        addHeader(table);
	        
	        //Add PDF Data
	        addPdfData(table, buildDTOList);
	        
	        //Add PDF Table in document.
		    document.add(table);
		    
		    document.close();
	        writer.close();
	    } catch (Exception e){
	    	LOGGER.error("Error while exporting ExportToExcelHistory.createPdf report details!!", e);
	    	e.printStackTrace();
	    }
    }
    
    private void addHeader(PdfPTable table){
    	
    	PdfPCell cellHeader1 = new PdfPCell(new Paragraph("Executed From", 		FONT_BOLD));
        pdfCommonSetting(cellHeader1);
        PdfPCell cellHeader2 = new PdfPCell(new Paragraph("CentOps Console ID", FONT_BOLD));
        pdfCommonSetting(cellHeader2);
        PdfPCell cellHeader3 = new PdfPCell(new Paragraph("Executed By", 		FONT_BOLD));
        pdfCommonSetting(cellHeader3);
        PdfPCell cellHeader4 = new PdfPCell(new Paragraph("Date & Time", 		FONT_BOLD));
        pdfCommonSetting(cellHeader4);
        PdfPCell cellHeader5 = new PdfPCell(new Paragraph("Jenkins Status", 	FONT_BOLD));
        pdfCommonSetting(cellHeader5);
        PdfPCell cellHeader6 = new PdfPCell(new Paragraph("Report Status", 		FONT_BOLD));
        pdfCommonSetting(cellHeader6);
        
        table.addCell(cellHeader1);
        table.addCell(cellHeader2);
        table.addCell(cellHeader3);
        table.addCell(cellHeader4);
        table.addCell(cellHeader5);
        table.addCell(cellHeader6);
    	
    }
    
    private void addPdfData(PdfPTable table, List<StartFlowHistoryDTO> 	buildDTOList){
    	
    	for(StartFlowHistoryDTO obj :buildDTOList){
	        PdfPCell cell1 = new PdfPCell(new Paragraph("Dashboard", 				FONT_NORMAL));			// Executed From
	        pdfCommonSetting(cell1);
	 
	        PdfPCell cell2 = new PdfPCell(new Paragraph(obj.getStartFlowId()+"", 	FONT_NORMAL));			// Dashboard ID
	        pdfCommonSetting(cell2);
	 
	        PdfPCell cell3 = new PdfPCell(new Paragraph(obj.getStartBy(), 			FONT_NORMAL));			// Executed By
	        pdfCommonSetting(cell3);
	 
	        PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getStartTime()+"", 		FONT_NORMAL));			// Date & Time
	        pdfCommonSetting(cell4);
	        
	        PdfPCell cell5 = new PdfPCell(new Paragraph(obj.getJenkinsStatus(), 	FONT_NORMAL));			// Status
	        pdfCommonSetting(cell5);
	        
	        PdfPCell cell6 = new PdfPCell(new Paragraph(obj.getReportStatus(), 		FONT_NORMAL));			// Status
	        pdfCommonSetting(cell6);
	        
	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
		    }
    }
    
    private void pdfCommonSetting(PdfPCell cell){
    	//cell.setBorderColor(BaseColor.RED);
        cell.setPaddingLeft(10);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    }
    
    
}
