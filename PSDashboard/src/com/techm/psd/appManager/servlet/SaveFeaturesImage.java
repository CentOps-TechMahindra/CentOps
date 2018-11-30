package com.techm.psd.appManager.servlet;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.techm.psd.common.dao.BaseDAO;

public class SaveFeaturesImage {
	private static String _IMAGE_TABLE	=	"APP_URL_LIST";
	
	public static void main(String args[]){
		try{  
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			              
			PreparedStatement ps=con.prepareStatement
						("INSERT INTO "+_IMAGE_TABLE+" (SRNO, APPNAME, DESCRIP, URL, LIC, ISFEATURE, IMGNAME, PHOTO, ISDISABLED, ISINSTALLED) "
								+ "VALUES (APP_URL_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
													
			ps.setString(1,"Error Trends");  										// APPNAME
			ps.setString(2, "Error Trends");										// DESCRIP
			ps.setString(3, "http://serverName:8080/Context/report.htm");	// ADD URL
			ps.setString(4, "Yes");													// LIC
			ps.setInt(5, 1);														// ISFEATURE	: 1 for feature 0 for application			
			ps.setString(6, "Error_Trends");										// IMAGE NAME: Should be without Space
			FileInputStream fin=new FileInputStream("C:\\Users\\aj797k\\Desktop\\Console\\Centops_v_03 scripts\\images\\errorTrends.png");  
			ps.setBinaryStream(7,fin,fin.available());								// PHOTO
			ps.setInt(8, 0);														// ISDISABLED
			ps.setInt(9, 1);														// ISINSTALLED
			
			
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			          
			con.close();  
			}catch (Exception e) {
				e.printStackTrace();
			}  
		
		try{  
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			              
			PreparedStatement ps=con.prepareStatement
						("INSERT INTO "+_IMAGE_TABLE+" (SRNO, APPNAME, DESCRIP, URL, LIC, ISFEATURE, IMGNAME, PHOTO, ISDISABLED, ISINSTALLED) "
								+ "VALUES (APP_URL_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
													//INSERT INTO "+ _TB_USER_LOGIN_LOG+" (USER_ID, LOG_IN_DATE) values (?, SYSDATE)
			ps.setString(1,"Find Solutions");  
			ps.setString(2, "Find Solutions");
			ps.setString(3, "http://serverName:8080/Context/search.htm");
			ps.setString(4, "Yes");
			ps.setInt(5, 1);			//1 for feature 0 for application
			ps.setString(6, "Find_Solutions");	// IMAGE NAME: Should be without Space
			FileInputStream fin=new FileInputStream("C:\\Users\\aj797k\\Desktop\\Console\\Centops_v_03 scripts\\images\\intelli-search.png");  
			ps.setBinaryStream(7,fin,fin.available());
			ps.setInt(8, 0);
			ps.setInt(9, 1);
			
			
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			          
			con.close();  
			}catch (Exception e) {
				e.printStackTrace();
			}  
		
		try{  
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			              
			PreparedStatement ps=con.prepareStatement
						("INSERT INTO "+_IMAGE_TABLE+" (SRNO, APPNAME, DESCRIP, URL, LIC, ISFEATURE, IMGNAME, PHOTO, ISDISABLED, ISINSTALLED) "
								+ "VALUES (APP_URL_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
													//INSERT INTO "+ _TB_USER_LOGIN_LOG+" (USER_ID, LOG_IN_DATE) values (?, SYSDATE)
			ps.setString(1,"My Knowledge Repository");  
			ps.setString(2, "My Knowledge Repository");
			ps.setString(3, "http://ServerName/PocWork/home.action"); //Server names needed here 
			ps.setString(4, "Yes");
			ps.setInt(5, 1);			//1 for feature 0 for application
			ps.setString(6, "My_Knowledge_Repository");	// IMAGE NAME: Should be without Space
			FileInputStream fin=new FileInputStream("C:\\Users\\aj797k\\Desktop\\Console\\Centops_v_03 scripts\\images\\intelli-docs.png");  
			ps.setBinaryStream(7,fin,fin.available());
			ps.setInt(8, 0);
			ps.setInt(9, 1);
			
			
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			          
			con.close();  
			}catch (Exception e) {
				e.printStackTrace();
			}  
		
		try{  
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			              
			PreparedStatement ps=con.prepareStatement
						("INSERT INTO "+_IMAGE_TABLE+" (SRNO, APPNAME, DESCRIP, URL, LIC, ISFEATURE, IMGNAME, PHOTO, ISDISABLED, ISINSTALLED) "
								+ "VALUES (APP_URL_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
													//INSERT INTO "+ _TB_USER_LOGIN_LOG+" (USER_ID, LOG_IN_DATE) values (?, SYSDATE)
			ps.setString(1,"My Reports");  
			ps.setString(2, "My Reports");
			ps.setString(3, "http://ServerName/Configurator/pages/configuratorAction.do?action=getPortal"); //need to add server name here 
			ps.setString(4, "Yes");
			ps.setInt(5, 1);			//1 for feature 0 for application
			ps.setString(6, "My_Reports");	// IMAGE NAME: Should be without Space
			FileInputStream fin=new FileInputStream("C:\\Users\\aj797k\\Desktop\\Console\\Centops_v_03 scripts\\images\\MyReports.png");  
			ps.setBinaryStream(7,fin,fin.available());
			ps.setInt(8, 0);
			ps.setInt(9, 1);
			
			
			int i=ps.executeUpdate();  
			System.out.println(i+" records affected");  
			          
			con.close();  
			}catch (Exception e) {
				e.printStackTrace();
			}  
		}   
}