package com.techm.psd.email.bo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.log4j.Logger;
import com.techm.psd.email.bo.MailObject;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.AppPropertiesReader;
import com.techm.psd.common.utils.CommonUtil;

public class PSDMailer  implements Serializable {

	private static Logger logger = Logger.getLogger(PSDMailer.class);

	static String mailFrom = AppPropertiesReader.getGroupMechID();
	static String smtpHost = "smtp server";
	
	/**
	 * Method to return String for Char
	 * 
	 * @param c
	 * @return
	 */
	private String getStringForChar(char c) {
		String str = "";
		switch (c) {
		case 'T':
			str = "Not Available";
			break;
		case 'Y':
			str = "Yes";
			break;
		case 'N':
			str = "No";
			break;
		}
		return str;
	}
		
	/**
	 * Method to send mail to Env. Mgr and other recipient after generation of
	 * front door request/ task / issue.
	 * 
	 * @param subject
	 * @param content
	 * @param mailTo
	 * @param mailCc
	 * @param mailBcc
	 * @param attach
	 */
	public boolean sendMail(String subject, String content, Collection mailTo,
			Collection mailCc, Collection mailBcc, File attach) throws MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.sendpartial", true);
		boolean flag = false;
		
		Session session = Session.getInstance(props, null);
	
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mailFrom));
		msg.setSentDate(new Date());
		msg.setSubject(subject);

		if ((mailTo != null) && (mailTo.size() > 0)) {
			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					getInternetAddressArray(mailTo));

			if (mailCc != null && (mailCc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.CC,
						getInternetAddressArray(mailCc));
			}

			if (mailBcc != null && (mailBcc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.BCC,
						getInternetAddressArray(mailBcc));
			}

			MimeMultipart multiPart = new MimeMultipart();

			MimeBodyPart bodyPartOne = new MimeBodyPart();			
			bodyPartOne.setContent(content, "text/html");
			multiPart.addBodyPart(bodyPartOne);
			
			if (attach != null) {
				MimeBodyPart bodyPartTwo = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attach
						.getAbsolutePath());
				bodyPartTwo.setDataHandler(new DataHandler(fds));
				bodyPartTwo.setFileName(attach.getName());
				multiPart.addBodyPart(bodyPartTwo);
			}
		 
			msg.setContent(multiPart);

			logger.debug("SUBJECT --> " + subject);
			if(msg != null) {
				Transport.send(msg);
				logger.debug("SEND MAIL SUCCESS");
				flag = true;
			}
		}			
		
		return flag;
	}
	
	public boolean sendMaileHighPriority(String subject, String content, Collection mailTo,
			Collection mailCc, Collection mailBcc, File attach) throws MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.sendpartial", true);
		boolean flag = false;
		
		Session session = Session.getInstance(props, null);
	
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mailFrom));
		msg.setSentDate(new Date());
		msg.setSubject(subject);

		if ((mailTo != null) && (mailTo.size() > 0)) {
			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					getInternetAddressArray(mailTo));

			if (mailCc != null && (mailCc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.CC,
						getInternetAddressArray(mailCc));
			}

			if (mailBcc != null && (mailBcc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.BCC,
						getInternetAddressArray(mailBcc));
			}

			MimeMultipart multiPart = new MimeMultipart();

			MimeBodyPart bodyPartOne = new MimeBodyPart();			
			bodyPartOne.setContent(content, "text/html");
			multiPart.addBodyPart(bodyPartOne);
			
			if (attach != null) {
				MimeBodyPart bodyPartTwo = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(attach
						.getAbsolutePath());
				bodyPartTwo.setDataHandler(new DataHandler(fds));
				bodyPartTwo.setFileName(attach.getName());
				multiPart.addBodyPart(bodyPartTwo);
			}
		 
			msg.setContent(multiPart);
			msg.setHeader("X-Priority", "1") ;  
			logger.debug("SUBJECT --> " + subject);
			if(msg != null) {
				Transport.send(msg);
				logger.debug("SEND MAIL SUCCESS");
				flag = true;
			}
		}			
		
		return flag;
	}

	/**
	 * Method to get an array of intermet address
	 * 
	 * @param mailAdd
	 * @return
	 */
	private static InternetAddress[] getInternetAddressArray(Collection mailAdd) {
		InternetAddress[] addList = new InternetAddress[mailAdd.size()];

		Iterator iter = mailAdd.iterator();
		String email= null;
		int i = 0;

		while (iter.hasNext()) {
			try {
				email = (String) iter.next();
				addList[i] = new InternetAddress(email);
			} catch (Exception e) {
				logger.info("Wrong Email Address : " + email);
				e.printStackTrace();
			}

			i++;
		}

		return addList;
	}

	/**
	 * Method to add an email list
	 * 
	 * @param emailList
	 * @param user
	 */
	private void addEmailList(Collection emailList, UserDTO user) {
		if ((emailList != null) && (user != null)) {
			if (user.getUserEmailId() != null) {
				emailList.add(user.getUserEmailId());
			}
		}
	}
	
	/**
	 * Method to add an email list
	 * 
	 * @param emailList
	 * @param user
	 */
	private void addEmailList(Collection emailList, String user) {
		if ((emailList != null) && (user != null)) {			
			emailList.add(user);			
		}
	}

	/**
	 * Method to add an email list
	 * 
	 * @param emailList
	 * @param userList
	 */
	private void addEmailList(Collection emailList, Collection userList) {
		if ((emailList != null) && (userList != null)) {
			Iterator iter = userList.iterator();

			while (iter.hasNext()) {
				try {
					UserDTO user = (UserDTO) iter.next();
					addEmailList(emailList, user);
				} catch (ClassCastException cce) {
				}
			}
		}
	}
	
	/**
	 * Method to add an email list
	 * 
	 * @param emailList
	 * @param userList
	 */
	private void addEmailStrList(Collection emailList, Collection userList) {
		if ((emailList != null) && (userList != null)) {
			Iterator iter = userList.iterator();

			while (iter.hasNext()) {
				try {
					String user = (String) iter.next();
					addEmailList(emailList, user);
				} catch (ClassCastException cce) {
				}
			}
		}
	}

	/**
	 * 
	 * 
	 * @param e
	 * @param userId
	 */
	public void mailErrors(Throwable e, String userId) throws MessagingException {
		if (e != null) {
			String timestamp = CommonUtil.getDateText(new Timestamp(System
					.currentTimeMillis()))
					+ " "
					+ CommonUtil.getTimeText(new Timestamp(System
							.currentTimeMillis()));
			String subject = "Exception \"" + e.getMessage()
					+ "\" occured on EMP Portal (Timestamp : " + timestamp
					+ " )";
			String content = subject
					+ " when User "
					+ userId
					+ " performing some actions, please get into the Server logs for more details";

			StackTraceElement[] expeptionChain = e.getStackTrace();

			if (expeptionChain != null) {
				for (int i = 0; i < expeptionChain.length; i++) {
					StackTraceElement trace = expeptionChain[i];
					content += ("\n <br> " + trace.toString());
				}
			}

			HashSet toAddress = new HashSet();
			toAddress.add("");
			this.sendMail(subject, content, toAddress, null, null, null);
		}
	}

	/**
	 * 
	 * 
	 * @param mailObject
	 */
	public void mailReport(MailObject mailObject) {
		try {
			this.sendMail(mailObject.getMailSubject(), mailObject.getContent(),
					mailObject.getToList(), mailObject.getCcList(), mailObject
							.getBccList(), mailObject.getAttachment());
		} catch (Exception e) {
		}
	}
	
	/**
	 * Method to send mail with attachment.
	 * 
	 * @param subject
	 * @param content
	 * @param mailTo
	 * @param mailCc
	 * @param mailBcc
	 * @param attach
	 */
	public boolean sendMailWithAttachment(String subject, 
										String content, 
										Collection<String> mailTo,
										Collection<String> mailCc, 
										Collection<String> mailBcc, 
										ByteArrayOutputStream  attach,
										String attchName) throws MessagingException {
		Properties props = System.getProperties();
		props.put("mail.smtp.host", smtpHost);
		boolean flag = false;
		
		Session session = Session.getInstance(props, null);
	
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(mailFrom));
		msg.setSentDate(new Date());
		msg.setSubject(subject);

		if ((mailTo != null) && (mailTo.size() > 0)) {
			msg.setRecipients(javax.mail.Message.RecipientType.TO,
					getInternetAddressArray(mailTo));

			if (mailCc != null && (mailCc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.CC,
						getInternetAddressArray(mailCc));
			}

			if (mailBcc != null && (mailBcc.size() > 0)) {
				msg.setRecipients(javax.mail.Message.RecipientType.BCC,
						getInternetAddressArray(mailBcc));
			}

			MimeMultipart multiPart = new MimeMultipart();

			MimeBodyPart bodyPartOne = new MimeBodyPart();			
			bodyPartOne.setContent(content, "text/html");
			multiPart.addBodyPart(bodyPartOne);
			
			if (attach != null) {		
				
				MimeBodyPart attachment = new MimeBodyPart();         
				attachment.setFileName(attchName);         
				attachment.setContent(attach.toByteArray(), "application/vnd.ms-excel");
				multiPart.addBodyPart(attachment); 
			}
		 
			msg.setContent(multiPart);

			logger.debug("SUBJECT --> " + subject);
			if(msg != null) {
				Transport.send(msg);
				logger.debug("SEND MAIL SUCCESS");
				flag = true;
			}
		}			
		
		return flag;
	}
	
}
