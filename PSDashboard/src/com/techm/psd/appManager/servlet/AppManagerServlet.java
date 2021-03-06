package com.techm.psd.appManager.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.techm.psd.appManager.dao.AppManagerDAOImpl;
import com.techm.psd.common.dto.UrlDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

public class AppManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AppManagerDAOImpl dao;
    
    public AppManagerServlet() {
        dao=new AppManagerDAOImpl();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")!=null){
			List<UrlDTO> lstUser=new ArrayList<UrlDTO>();
			String action=(String)request.getParameter("action");
			Gson gson = new Gson();
			response.setContentType("application/json");
			
			if(action.equals("list")){
				try{						
				
					//Fetch Data from User Table
					int startPageIndex=Integer.parseInt(request.getParameter("jtStartIndex"));
					int numRecordsPerPage=Integer.parseInt(request.getParameter("jtPageSize"));
					String sortString = (request.getParameter("jtSorting"));
					lstUser=dao.getUrlByRange(startPageIndex,numRecordsPerPage,sortString);
					
					//Get Total Records for Pagination
					int userCount=dao.getUserCount();
					//Convert Java Object to Json				
					
					JsonElement element = gson.toJsonTree(lstUser, new TypeToken<List<UrlDTO>>() {}.getType());
					JsonArray jsonArray = element.getAsJsonArray();
					String listData=jsonArray.toString();				
					
					//Return Json in the format required by jTable plugin
					listData="{\"Result\":\"OK\",\"Records\":"+listData+",\"TotalRecordCount\":"+userCount+"}";			
					response.getWriter().print(listData);
				}catch(Exception ex){
					String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
					response.getWriter().print(error);
					ex.printStackTrace();
				}				
			}else if(action.equals("create") || action.equals("update")){
				UrlDTO urlObj=new UrlDTO();
				if(request.getParameter("srn")!=null){
					int srn=(Integer.parseInt(request.getParameter("srn")));
					urlObj.setSrn(srn);
				}
				if(request.getParameter("url")!=null){
					String url=(String)request.getParameter("url");
					urlObj.setUrl(url);
				}
				if(request.getParameter("desc")!=null){
				   String desc=(String)request.getParameter("desc");
				   urlObj.setDesc(desc);
				}
				if(request.getParameter("lic")!=null){
				   String lic=(String)request.getParameter("lic");
				   urlObj.setLic(lic);
				}
				
				try{											
					if(action.equals("create")){//Create new record
						dao.addUrl(urlObj);					
						lstUser.add(urlObj);
						//Convert Java Object to Json				
						String json=gson.toJson(urlObj);					
						//Return Json in the format required by jTable plugin
						String listData="{\"Result\":\"OK\",\"Record\":"+json+"}";											
						response.getWriter().print(listData);
					}else if(action.equals("update")){//Update existing record
						dao.updateUrl(urlObj);
						String listData="{\"Result\":\"OK\"}";									
						response.getWriter().print(listData);
					}
				}catch(Exception ex){
						String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
						response.getWriter().print(error);
				}
			}else if(action.equals("delete")){//Delete record
				try{
					if(request.getParameter("srn")!=null){
						int srn=(Integer.parseInt(request.getParameter("srn")));
						dao.deleteUrl(srn);
						String listData="{\"Result\":\"OK\"}";								
						response.getWriter().print(listData);
					}
				}catch(Exception ex){
					String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getStackTrace().toString()+"}";
					response.getWriter().print(error);
				}
			}
		}
	}
}
