package com.techm.psd.common.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.log4j.Logger;

import com.techm.psd.common.dto.UserDTO;
import com.techm.psd.constants.CommonConstants;
import com.techm.psd.exception.PSDException;

public class LDAPUtility {

private static Logger LOGGER = Logger.getLogger(LDAPUtility.class);
	
	private static final String INITCTX = "INITCTX";
	static String MY_HOST = "MY_HOST";
	static String MY_SEARCHBASE = "MY_SEARCHBASE";
	private String str_all = "";
	
	public LDAPUtility(String id) {
		str_all = this.get(id);
	}
	
	public String getAll() {
		return (str_all);
	}

	public String getAttr(String attr) {
		if (str_all.indexOf("|" + attr + ":") < 0)
			return ("");
		String s = str_all.substring(str_all.indexOf("|" + attr + ":")
				+ attr.length() + 2);
		return (s.substring(0, s.indexOf("|")));
	}

	public String getAttrs(String attrs) {
		String s = "";
		StringTokenizer myParser = new StringTokenizer(attrs, ",.|");
		while (myParser.hasMoreTokens())
			s += this.getAttr(myParser.nextToken()) + "|";
		return (s);
	}

	public String getId() {
		return (this.getAttr("userid"));
	}

	public String getName() {
		return (this.getAttr("pn"));
	}

	public String getMail() {
		return (this.getAttr("mail"));
	}

	public String getBgrp() {
		return (this.getAttr("bgname"));
	}

	public String getJttl() {
		return (this.getAttr("jtname"));
	}

	public String getTeln() {
		return (this.getAttr("telephoneNumber"));
	}

	public String getStrt() {
		return (this.getAttr("street"));
	}

	public String getCity() {
		return (this.getAttr("l"));
	}

	public String getStat() {
		return (this.getAttr("st"));
	}

	public String getRoom() {
		return (this.getAttr("roomNumber"));
	}

	public String getZip() {
		return (this.getAttr("postalCode"));
	}

	public String getStts() {
		return (this.getAttr("organizationalStatus"));
	}

	public String getHndl() {
		return (this.getAttr("userid"));
	}

	public String getMngr() {
		return (this.getAttr("mgrid"));
	}
	
	public String getConsultingCompanyName(){
		return (this.getAttr("consulting"));
	}
	
	private String get(String id) {
		String MY_FILTER = "(a1=" + id + ")";
		String str_all = "";
		
		
		Hashtable env = new Hashtable();
		

		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY,AppConfig._LDAPPROPERTIES.getProperty(INITCTX) );
			env.put(Context.PROVIDER_URL,AppConfig._LDAPPROPERTIES.getProperty(MY_HOST) );
				
			/* get a handle to an Initial DirContext */
			DirContext ctx = new InitialDirContext(env);
			/* specify search constraints to search subtree */
			SearchControls constraints = new SearchControls();
			constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
			/* search for all entries with id = args[0] */
			NamingEnumeration results = ctx.search(AppConfig._LDAPPROPERTIES.getProperty(MY_SEARCHBASE), MY_FILTER,
					constraints);
			/* for each entry print out name + all attrs and values */
			while (results != null && results.hasMore()) {
				SearchResult si = (SearchResult) results.next();
				str_all += "|name:" + si.getName();
				Attributes attrs = si.getAttributes();
				if (attrs != null) {
					for (NamingEnumeration ae = attrs.getAll(); ae
							.hasMoreElements();) {
						Attribute attr = (Attribute) ae.next();
						String attrId = attr.getID();
						for (Enumeration vals = attr.getAll(); vals
								.hasMoreElements(); str_all += "|" + attrId
								+ ":" + vals.nextElement())
							;
					}
				}
				str_all += "|";
			}
		} catch (NamingException e) {
			str_all = "||";
			System.err.println("Search example failed.");
			e.printStackTrace();
		}catch(NullPointerException nupe){
			LOGGER.error("User Id: "+id+"LDAP_ERROR: "+nupe.getMessage());
			nupe.printStackTrace();
			return CommonConstants.LDAP_PROBLEM;
		}catch(Exception e){
			LOGGER.error("User Id: "+id+"LDAP_ERROR: "+e.getMessage());
			e.printStackTrace();
			return CommonConstants.LDAP_PROBLEM;
		}
		
		return (str_all);
	}

	public static String getUidDetails(String UsrUid, String filter){
		LDAPUtility ldap = new LDAPUtility(UsrUid);
		String resultSet;
		if (CommonUtil.isNotEmpty(filter)){
			resultSet = ldap.getAttrs(filter);
		}
		else{
			resultSet = ldap
							.getAttrs("id|pn|mail|bgname|jtname|telephoneNumber|street|l|st|roomNumber|postalCode|organizationalStatus|userid|mgrid");
		}
		return resultSet; 
	}

	public static UserDTO getUserDetails(String p_userId)throws PSDException
	{
		UserDTO user = null;
		try{
			LDAPUtility ldapuser = new LDAPUtility(p_userId);				
			if(ldapuser.getAll().equalsIgnoreCase(CommonConstants.LDAP_PROBLEM)){
				user=new UserDTO();
				user.setUserId(p_userId);
				user.setLdapStatus(CommonConstants.LDAP_PROBLEM);
			}else{
				StringTokenizer st = new StringTokenizer(ldapuser.getName(), ",");
	
				user = new UserDTO();
				user.setUserId(p_userId);
				user.setLastName(st.nextToken());
				user.setFirstName(st.nextToken());
				user.setUserName(user.getFirstName() + " " + user.getLastName());
				user.setUserContactNo(ldapuser.getTeln());
				//user.setIsActive(ldapuser.getStts());
				//user.setSupervisorId(ldapuser.getMngr());
				user.setUserEmailId(ldapuser.getMail().endsWith("techmahindra.com")?ldapuser.getId()+"@techmahindra.com":ldapuser.getMail());
				//user.setConsulting(ldapuser.getConsultingCompanyName());
				user.setLdapStatus(CommonConstants.LDAP_UP);
			}	
		}catch(Exception e){
			e.printStackTrace();
			throw new PSDException("User '"+p_userId+"' is not an valid User");
		}
		return user;
	}
}
