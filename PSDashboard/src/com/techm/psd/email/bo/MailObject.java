package com.techm.psd.email.bo;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author aj797k
 *
 */
public class MailObject implements Serializable {
	
	private String mailSubject;
    private String content;
    private String mailFrom;
    private ArrayList toList;
    private ArrayList ccList;
    private ArrayList bccList;
    private File attachment;
	
    /**
	 * @return the mailSubject
	 */
	public String getMailSubject() {
		return mailSubject;
	}
	/**
	 * @param mailSubject the mailSubject to set
	 */
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the mailFrom
	 */
	public String getMailFrom() {
		return mailFrom;
	}
	/**
	 * @param mailFrom the mailFrom to set
	 */
	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}
	/**
	 * @return the toList
	 */
	public ArrayList getToList() {
		return toList;
	}
	/**
	 * @param toList the toList to set
	 */
	public void setToList(ArrayList toList) {
		this.toList = toList;
	}
	/**
	 * @return the ccList
	 */
	public ArrayList getCcList() {
		return ccList;
	}
	/**
	 * @param ccList the ccList to set
	 */
	public void setCcList(ArrayList ccList) {
		this.ccList = ccList;
	}
	/**
	 * @return the bccList
	 */
	public ArrayList getBccList() {
		return bccList;
	}
	/**
	 * @param bccList the bccList to set
	 */
	public void setBccList(ArrayList bccList) {
		this.bccList = bccList;
	}
	/**
	 * @return the attachment
	 */
	public File getAttachment() {
		return attachment;
	}
	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(File attachment) {
		this.attachment = attachment;
	}
	
	/**
	 * 
	 */
	public String toString() {
        return "mailSubject - " + mailSubject + "content - " + content + "mailFrom - " + mailFrom + "toList - " + toList + "ccList - " + ccList + "bccList - " + bccList;
    }

}
