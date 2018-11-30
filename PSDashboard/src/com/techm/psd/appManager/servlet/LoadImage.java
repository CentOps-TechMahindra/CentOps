package com.techm.psd.appManager.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techm.psd.common.dao.BaseDAO;

/**
 * Servlet implementation class LoadImage
 */
public class LoadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static String _IMAGE_TABLE	= "APP_URL_LIST";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadImage() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			
			if(null!=request.getParameter("action") && request.getParameter("action").equals("iload")){
				String imgName=request.getParameter("imgName");
				PreparedStatement ps=con.prepareStatement("select * from "+_IMAGE_TABLE+" where imgname='"+imgName+"'");  
				ResultSet rs=ps.executeQuery();  
				if(rs.next()){//now on 1st row  
					response.setContentType("image/jpg");  
					response.getOutputStream().write(rs.getBytes("photo"));
				}//end of if  
			
			} else if(null!=request.getParameter("action")&&request.getParameter("action").equals("load")){
				System.out.println("in load");	
				PreparedStatement ps=con.prepareStatement("select imgname,isdisabled from "+_IMAGE_TABLE+" where isInstalled=1 AND ISFEATURE = 1 ORDER BY APPNAME");  
				ResultSet rs=ps.executeQuery();
				String str="";
				while(rs.next()){//now on 1st row
					str=str+(rs.getString("imgname")+rs.getString("isdisabled"))+",";
				}
				response.setContentType("text"); 
				response.getOutputStream().print(str);
			}
			con.close();  
			
		}catch (Exception e) {
			e.printStackTrace();  
		}  
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{  
			BaseDAO dao = new BaseDAO();
			Connection con=dao.getConnection();   
			
			if(null!=request.getParameter("action")&&request.getParameter("action").equals("disable")){ 
				String imgName=request.getParameter("imgName");
				
				PreparedStatement ps=con.prepareStatement("update "+_IMAGE_TABLE+"  set isdisabled=1 where imgname='"+imgName+"'");  
				ps.executeUpdate();  
				
				response.setContentType("text");  
				response.getOutputStream().print("Click to Enable");
			}else if(null!=request.getParameter("action")&&request.getParameter("action").equals("enable")){
				System.out.println("Inside Enable");
				
				String imgName=request.getParameter("imgName");
				
				PreparedStatement ps=con.prepareStatement("update "+_IMAGE_TABLE+"  set isdisabled=0 where imgname='"+imgName+"'");  
				ps.executeUpdate();  
				
				response.setContentType("text");  
				response.getOutputStream().print("Click to Disable");
				
			}
			con.close();  
		
		}catch (Exception e) {
			e.printStackTrace();  
		}  
	}
}