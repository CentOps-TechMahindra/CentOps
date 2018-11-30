package com.techm.psd.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.techm.psd.auth.AuthFilterConstants;
import com.techm.psd.common.dao.CommonDAO;
import com.techm.psd.common.dao.CommonDAOImpl;
import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.common.utils.AppConfig;
import com.techm.psd.common.utils.LDAPUtility;
import com.techm.psd.common.utils.UserSessionUtil;
import com.techm.psd.constants.CommonConstants;
import com.techm.psd.constants.LDAP_CONSTANTS;
import com.techm.psd.constants.SQLConstants;
import com.techm.psd.user.dao.UserDAOImpl;

import esGateKeeper.esGateKeeper;

public class AuthFilter implements Filter {
	
	private static final Logger LOGGER 							= Logger.getLogger(AuthFilter.class);
	
	
	public static List<String>bypassList;
	private static FilterConfig filterConfig;
	
	public void init(FilterConfig aFilterConfig) throws ServletException {	
		AuthFilterConstants.WEB_APP_ROOT = AuthFilterConstants.FORWARD_SLASH + aFilterConfig.getServletContext().getServletContextName() + AuthFilterConstants.FORWARD_SLASH;
		LOGGER.info("Init is called, Web Root:  " + AuthFilterConstants.WEB_APP_ROOT);
		
		this.filterConfig = aFilterConfig;
		bypassList = new ArrayList<String>();
		//bypassList.add("home.do");
	}
	
	public void destroy() {}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		LOGGER.info("Enters in doFilter()...");
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String uri = httpRequest.getRequestURI();
		HttpSession session = httpRequest.getSession();
		ServletContext context=filterConfig.getServletContext();
		LOGGER.info("Checking for AUTH cookie...URI: " + uri);
				
		if(uri.contains("logout.do")){
			LOGGER.info("Enters in AUTHFilter.logout...");
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(302);
			String url = "/PSDashboard/pages/authentication/logout.jsp";
			LOGGER.info("Exit from AUTHFilter.logout and redirecting to Gloabl Logon... URL::::::::::: "+url);
			httpResponse.sendRedirect(url);
		}else if(AuthFilterConstants._ON.equalsIgnoreCase(AppConfig._AUTH_LOGON_SWITCH)){
			
			//Resetting sessionTimeout for every click.		
			if (!AUTHSecureCookieExists(request)){
				LOGGER.info("AUTH cookie was not found.  Redirecting to AUTH web-site...");
				session.setAttribute("sessionTimeout", AuthFilterConstants.sessionTimeout);			
				redirectToAUTH(response, httpRequest);
				return;
			}else{
				LOGGER.info("AUTH cookie was found...");			
				session.setAttribute("sessionTimeout", AuthFilterConstants.sessionTimeout);							
				try {
					cacheUserInSession(httpRequest, response);
					//TODO: Check: If its from direct URL then check user hasAccessToLink and forward accordingly
					UserDTO userSession_ldap_validity=(UserDTO)session.getAttribute(AuthFilterConstants.USER_SESSION_KEY);
					if(userSession_ldap_validity.getLdapStatus().equalsIgnoreCase(CommonConstants.LDAP_PROBLEM)){
						context.getRequestDispatcher(LDAP_CONSTANTS._LDAP_DOWN_URL).forward(request, response);
					}else{
						chain.doFilter(request, response);
					}
				} catch (Exception ise) {					
					LOGGER.fatal("Error while creating user session.", ise);				
				}
			}
		}else{
			String url2= null;
	        StringBuilder sb = new StringBuilder();
	        sb.append(httpRequest.getRequestURL().toString());
	        
	        if(httpRequest.getQueryString() != null){
	        	sb.append("?"+httpRequest.getQueryString());
	        }
	        url2 = sb.toString();
        	
            if ( uri.indexOf("/css") > 0){
                chain.doFilter(request, response);
            }
            else if( uri.indexOf("/styles") > 0){
                chain.doFilter(request, response);
            }
            else if( uri.indexOf("/images") > 0){
                chain.doFilter(request, response);
            }
            else if( uri.indexOf("/js") > 0){
                chain.doFilter(request, response);
            }
            
			if (null != session.getAttribute("user") || null != session.getAttribute("uri")) {
				chain.doFilter(request, response);
	        } else {
	        	session.setAttribute("uri", url2);
	        	filterConfig.getServletContext().getRequestDispatcher("/pages/authentication/login.jsp").forward(request, response);
	        }//Check userAuthCookie.
			
			//context.getRequestDispatcher("/pages/authentication/login.jsp").forward(request, response);
		}
	}//ends doFilter()
		
	private void cacheUserInSession(HttpServletRequest request, ServletResponse response) throws Exception {
		LOGGER.info("AUTH cookie was found.");
		
		String value = getCookie(request, AuthFilterConstants.AUTH_SECURE_COOKIE_NAME).getValue();
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Encrypted AUTH cookie value:  " + value);
		}
				
		String suitsID = parseSuitsID(value, response, request);
		if (suitsID == null) {
			throw new IllegalStateException("Invalid AUTH cookie [suitsID] was missing.");
		}
		
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("AUTH suits ID:  " + suitsID);
		}
		
		HttpSession session = request.getSession();
		if (session.getAttribute(AuthFilterConstants.USER_SESSION_KEY) == null) { // First time login today
			session.setAttribute(AuthFilterConstants.USER_SESSION_KEY, createUserSession(request, suitsID, response));
			//SAVING FIRST TIME LOGGIN USER DETAILS IN 'PS_USER_LOGIN_LOG'
			UserDTO user	 	= UserSessionUtil.getUserSession(session);
			CommonDAO cDAO = new CommonDAOImpl();
			cDAO.saveUserLoginLog(user.getUserId());
			
			LOGGER.debug("User was placed into the HTTP session.");
		} else {
			LOGGER.debug("User has already logged in today.");
		}
	}
	
	private UserDTO createUserSession(HttpServletRequest request, String userId, ServletResponse response) throws Exception {
		LOGGER.info("AUTHFILTER:UserSession "+userId);			
		UserDTO user = new UserDTO();
		user = LDAPUtility.getUserDetails(userId);
		
		//String requestURI = request.getRequestURI();					//TODO: If user hit Direct URL
		
		if(user != null){
			UserDAOImpl userDAO = new UserDAOImpl();
			UserDTO existingUser = userDAO.getUser(user.getUserId());
			
			if(existingUser == null){									// If its a new New User add his details into DB.
				userDAO.saveNewUser(user);
				user.setLevelId(SQLConstants.DEFAULT_LVL);				//
				user.setLevelName(SQLConstants.DEFAULT_LVL_NAME);		//
			}
			else{
				user = existingUser;
				user.setLdapStatus(CommonConstants.LDAP_UP);
			}
		}	
		return user;
	}
	
	private String parseSuitsID(String cookieValue, ServletResponse response, HttpServletRequest httpRequest) throws IOException {
		String unencrypted = esGateKeeper.esGateKeeper(cookieValue, AuthFilterConstants.AUTH_SYSTEM_VALUE, AuthFilterConstants.AUTH_LEVEL);
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Un-encrypted AUTH cookie value:  " + unencrypted);
		}
		
		String[] tokens = unencrypted.split(AuthFilterConstants.DELIMITER);
		if (LOGGER.isDebugEnabled()) {
			for (int i = 0; i < tokens.length; i++) {
				LOGGER.debug("Token[" + i + "]:  " + tokens[i]);
			}
		}

		if (AuthFilterConstants.AUTH_COOKIE_SUITS_ID_INDEX >= tokens.length) {
			LOGGER.info("Invalid AUTH cookie detected.");
			redirectToAUTH(response, httpRequest);			
			return null;
		}
		
		return tokens[AuthFilterConstants.AUTH_COOKIE_SUITS_ID_INDEX];
	}
	
	private void redirectToAUTH(ServletResponse response, HttpServletRequest httpRequest) throws IOException {
		if (response == null) {
			return;
		}
		
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		StringBuilder url = new StringBuilder(AuthFilterConstants.AUTH_LOGIN_URL);
		url.append(AuthFilterConstants.QUESTION_MARK);
		url.append(AuthFilterConstants.AUTH_RETURN_URL_PARAM);
		url.append(AuthFilterConstants.EQUALS);
		url.append(httpRequest.getRequestURL().toString());
		
		if (!httpRequest.getParameterMap().isEmpty()) {
			url.append(AuthFilterConstants.ENCODED_QUESTION_MARK);
		}
		
		HttpSession session = httpRequest.getSession();
		boolean isFirst = true;
		for (Object key : httpRequest.getParameterMap().keySet()) {
			session.setAttribute((String) key, httpRequest.getParameter((String) key));
			if (!isFirst) {
				url.append(AuthFilterConstants.ENCODED_AMPERSAND);
			}
			url.append((String)key).append(AuthFilterConstants.EQUALS).append(httpRequest.getParameter((String) key));
			isFirst = false;
		}
		
		url.append(AuthFilterConstants.AMPERSAND);
		
		url.append(AuthFilterConstants.AUTH_SYSTEM_PARAM);
		url.append(AuthFilterConstants.EQUALS);
		url.append(AuthFilterConstants.AUTH_SYSTEM_VALUE);
		
		LOGGER.debug("Redirecting to " + url);
		
		httpResponse.setStatus(302);
		httpResponse.sendRedirect(url.toString());
	}
	
	private boolean AUTHSecureCookieExists(ServletRequest request) {
		return getCookie(request, AuthFilterConstants.AUTH_SECURE_COOKIE_NAME) != null;
	}

	private Cookie getCookie(ServletRequest request, String cookieName) {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Cookie[] cookies = httpRequest.getCookies();
		if (cookies == null)
			return null;
		
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals(cookieName))
			{
				LOGGER.info(cookies[i].getValue()+"|"+cookies[i].getMaxAge()+
						"|"+cookies[i].getVersion()+"|"+cookies[i].getPath()+"|"+cookies[i].getDomain());
				return cookies[i];
			}
		}
		return null;	
	}	
	
}