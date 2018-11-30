package com.techm.psd.email;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;

import com.techm.psd.feedback.dao.FeedbackDAO;
import com.techm.psd.feedback.dao.FeedbackDAOImpl;
import com.techm.psd.feedback.dto.FeedbackDTO;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.AppConfig;
import com.techm.psd.common.utils.CommonUtil;
import com.techm.psd.constants.CommonConstants;
import com.techm.psd.email.bo.PSDMailer;
import com.techm.psd.exception.PSDException;

public class FeedbackNotificationEmail {

	private static Logger LOGGER = Logger.getLogger(FeedbackNotificationEmail.class);
	
	public void sendFeedbaclEmail(UserDTO user, FeedbackDTO fDTO, String action) throws MessagingException {
		LOGGER.info("Enters into sendEmailCreate()... feedbackId : ");
		String responseMsg 		= "";
		//Step1: Get basic info
		
		//Step2: Build email body
		PSDMailer emailer = new PSDMailer();

		String subject = "";
		if(action.equalsIgnoreCase(CommonConstants._ACTION_NEW)){
			   subject = "New Feedback Created - Feedback ID - "+fDTO.getFeedbackId();
		}else if(action.equalsIgnoreCase(CommonConstants._ACTION_UPDATE)){	   
			subject = "Feedback Updated - Feedback ID - "+fDTO.getFeedbackId();
		}
		
		StringBuffer content = new StringBuffer();

		content.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		content.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		content.append("<head>");
		content.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />");
		content.append("<title>E-Mail</title>");
		content.append("<style type='text/css'>");
		content.append("body {");
		content.append("	font-family:Arial;");
		content.append("	font-size:100%");
		content.append("}");
		content.append("h1 {");
		content.append("	font-size:95%;");
		content.append("	color:#039");
		content.append("}");
		content.append("h2, p, span, table {");
		content.append("	font-size:75%");
		content.append("}");
		content.append("p a, p a:active {");
		content.append("	color:#EE8444;");
		content.append("	text-decoration:underline");
		content.append("}");
		content.append("table {");
		content.append("	border:1px solid #E5E5E5;");
		content.append("	border-collapse:collapse");
		content.append("}");
		content.append("table th {");
		content.append("	font-weight:bold;");
		content.append("	text-align: left;");
		content.append("	background-color: #E5E5E5;");
		content.append("	padding:3px 5px;");
		content.append("	vertical-align:top;");
		content.append("	border-bottom: 1px solid #fff");
		content.append("}");
		content.append("table td {");
		content.append("	line-height:20px;");
		content.append("	padding:3px 5px;");
		content.append("	vertical-align:top;");
		content.append("	border-bottom: 1px solid #E5E5E5");
		content.append("}");
		content.append(".clear {");
		content.append("	clear:both;");
		content.append("	height:10px");
		content.append("}");
		content.append("</style>");
		content.append("</head>");
		content.append("<body>");
		content.append("<h1>Feedback ID: "+fDTO.getFeedbackId()+"</h1>");
		content.append("");
		
		content.append("<p>Please <a href='"+AppConfig._SERVERNAME+"/"+AppConfig._APPNAME+"/feedback.do?method=view&feedbackId=" + fDTO.getFeedbackId() + "'>Click here to view</a> the Feedback</p>");
		content.append("<table width='100%' border='0' cellspacing='0' cellpadding='0'>");
		content.append(" <tr>");
		content.append("    <th width='20%'>Feedback ID</th>");
		content.append("    <td width='80%'>"+fDTO.getFeedbackId()+"</td>");
		content.append(" </tr>");
		content.append(" <tr>");
		content.append("    <th width='20%'>Workflow Name</th>");
		content.append("    <td width='80%'>"+fDTO.getWorkflowName()+" </td>");
		content.append("  </tr>");
		content.append("  <tr>");
		content.append("    <th>Added By</th>");
		content.append("    <td>"+fDTO.getAddedBy()+"</td>");
		content.append("  </tr>");
		content.append("  <tr>");
		content.append("    <th>Added Date</th>");
		content.append("    <td>"+fDTO.getAddedDate()+"</td>");
		content.append("  </tr>");
		if(action.equalsIgnoreCase(CommonConstants._ACTION_UPDATE)){
			content.append("  <tr>");
			content.append("    <th>Updated By</th>");
			content.append("    <td>"+fDTO.getUpdatedBy()+"</td>");
			content.append("  </tr>");
			content.append("  <tr>");
			content.append("    <th>Updated Date</th>");
			content.append("    <td>"+fDTO.getUpdatedDate()+"</td>");
			content.append("  </tr>");
		}
		content.append("  <tr>");
		content.append("    <th>Status</th>");
		content.append("    <td>"+fDTO.getStatus()+"</td>");
		content.append("  </tr>");
		if(CommonUtil.isNotEmpty(fDTO.getComment())){
			content.append("  <tr>");
			content.append("    <th>Comment</th>");
			content.append("    <td>"+fDTO.getComment()+"</td>");
			content.append("  </tr>");
		}
		content.append("</table>");
		content.append("<div class='clear'>&nbsp;</div>");		
		content.append("<p>Thank You,<br />");
		content.append(" Admin </p>");		
		//content.append("<p><em>This is an AUTO-GENERATED e-mail, for further information please contact CentOps Support ( <a href='mailto:"+ user.getUserEmailId() + "'>" + user.getUserId() + "</a>).</em></p>");
		content.append("<p><em>This is an AUTO-GENERATED e-mail, for further information please contact CentOps Support!</p>");
		content.append("</body>");
		content.append("</html>");        

		//Step3: Set To and Cc list
		Collection<String> mailTo = new ArrayList<String>();

		mailTo.add(user.getUserEmailId());
		
		//As there could be multiple receiver, keep a distinct set 
		List<String> cc = new ArrayList<String>();

		FeedbackDAO fDao = new FeedbackDAOImpl();
		if(user != null) {
			try {
				cc = fDao.getAdminsEmailIdsByApplication(user.getAppId());
			} catch (PSDException e) {
				LOGGER.error("Error while setting cc Admin emailds()...");
				e.printStackTrace();
			}
		}

		if(emailer.sendMail(subject, content.toString(), mailTo, cc, null, null)){
			responseMsg = "Email sent successfully.\n"+responseMsg;
			//nDTO.setResponseMsg(responseMsg);
		}else {
			responseMsg = "Email sent unsuccessfully.\n"+responseMsg;
			//nDTO.setResponseMsg(responseMsg);
		}
		
		// Log sent email in the database
		//EmailDAO createLog = new EmailDAO();
		//createLog.createEmailObject(subject, content.toString(), mailTo, ccSet);
		LOGGER.info("Exits from sendEmailCreate()...");
	}//ends sendEmailCreate()		

}