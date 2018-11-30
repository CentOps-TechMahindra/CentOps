package com.techm.psd.common.form;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class BaseForm extends ActionForm {

	private String action;
	private String paginate = "N";
	private String paginateAction = "";
	private String tabNavigation;
	private ArrayList errorMessages;
	private ArrayList successMessages;

	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action
	 *            the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the paginate
	 */
	public String getPaginate() {
		return paginate;
	}

	/**
	 * @param paginate
	 *            the paginate to set
	 */
	public void setPaginate(String paginate) {
		this.paginate = paginate;
	}

	/**
	 * @return the paginateAction
	 */
	public String getPaginateAction() {
		return paginateAction;
	}

	/**
	 * @param paginateAction
	 *            the paginateAction to set
	 */
	public void setPaginateAction(String paginateAction) {
		this.paginateAction = paginateAction;
	}

	/**
	 * @return the tabNavigation
	 */
	public String getTabNavigation() {
		return tabNavigation;
	}

	/**
	 * @param tabNavigation
	 *            the tabNavigation to set
	 */
	public void setTabNavigation(String tabNavigation) {
		this.tabNavigation = tabNavigation;
	}

	/**
	 * @return the errorMessages
	 */
	public ArrayList getErrorMessages() {
		return errorMessages;
	}

	/**
	 * @param errorMessages
	 *            the errorMessages to set
	 */
	public void setErrorMessages(ArrayList errorMessages) {
		this.errorMessages = errorMessages;
	}

	/**
	 * @return the successMessages
	 */
	public ArrayList getSuccessMessages() {
		return successMessages;
	}

	/**
	 * @param successMessages
	 *            the successMessages to set
	 */
	public void setSuccessMessages(ArrayList successMessages) {
		this.successMessages = successMessages;
	}

	public void addSuccessMessage(String successMessage) {
		if (successMessages == null) {
			successMessages = new ArrayList();
		}
		successMessages.add(successMessage);
	}

	public void addErrorMessage(String errorMessage) {
		if (errorMessages == null) {
			errorMessages = new ArrayList();
		}
		errorMessages.add(errorMessage);
	}

	public void clearErrorMessages() {
		if (errorMessages != null) {
			errorMessages.clear();
		}
	}

	public void clearSuccessMessages() {
		if (successMessages != null) {
			successMessages.clear();
		}
	}

	public boolean hasErrorMessage() {
		boolean hasErrorMessage = false;
		if (errorMessages != null && errorMessages.size() > 0) {
			hasErrorMessage = true;
		}
		return hasErrorMessage;
	}

	public boolean hasSuccessMessage() {
		boolean hasErrorMessage = false;
		if (successMessages != null && successMessages.size() > 0) {
			hasErrorMessage = true;
		}
		return hasErrorMessage;
	}

}
