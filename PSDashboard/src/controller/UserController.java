package controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import dao.UserDao;
import model.AppColumn;
import model.AppTable;
import model.Availibility;
import model.User;
import model.compData;
import model.grphValues;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
//import org.codehaus.jackson.map.ObjectMapper;

public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String LIST_USER = "/listUser.jsp";
    private UserDao dao;

    public UserController() {
        super();
        dao = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward="";
        String action = request.getParameter("action");
        
        if(action.equalsIgnoreCase("listUser")){
    			try{						
    			//Fetch Data from User Table
    			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + request.getParameter("tableQuery"));
    			List<User> lstUser=new ArrayList<User>();
    			Gson gson = new Gson();
    			lstUser=dao.getAllUsers(request.getParameter("tableQuery"));			
    			//Convert Java Object to Json				
    			JsonElement element = (JsonElement) gson.toJsonTree(lstUser, new TypeToken<List<User>>() {}.getType());	
    			JsonArray jsonArray = element.getAsJsonArray();
    			//String listData=jsonArray.toString();				
    			//Return Json in the format required by jTable plugin
    			//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
    			//response.getWriter().print(listData);
    			
    			
    			response.setContentType("application/html");
    			response.setHeader("cache-control", "no-cache");
    			System.out.println(jsonArray.toString());
    			response.getWriter().println(jsonArray.toString());
    			

    			//out.println(jsonResponse.toString());
    			//out.flush();

    			}catch(Exception ex){
    				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
    				response.getWriter().print(error);
    				ex.printStackTrace();
    			}				
    		}
            
            if(action.equalsIgnoreCase("getComp")){
    			try{						
    			//Fetch Data from User Table				
    			List<compData> comp_DL=new ArrayList<compData>();
    			Gson gson = new Gson();
    			comp_DL=dao.getComplData(request.getParameter("tableQuery"));			
    			//Convert Java Object to Json				
    			JsonElement element = (JsonElement) gson.toJsonTree(comp_DL, new TypeToken<List<compData>>() {}.getType());	
    			JsonArray jsonArray = element.getAsJsonArray();
    			//String listData=jsonArray.toString();				
    			//Return Json in the format required by jTable plugin
    			//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
    			//response.getWriter().print(listData);
    			
    			
    			response.setContentType("application/html");
    			response.setHeader("cache-control", "no-cache");
    			System.out.println(jsonArray.toString());
    			response.getWriter().println(jsonArray.toString());
    			

    			//out.println(jsonResponse.toString());
    			//out.flush();

    			}catch(Exception ex){
    				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
    				response.getWriter().print(error);
    				ex.printStackTrace();
    			}				
    		}
            
        
     /*   if(action.equalsIgnoreCase("listUser")){
			try{						
			//Fetch Data from User Table
			List<User> lstUser=new ArrayList<User>();
			Gson gson = new Gson();
			lstUser=dao.getAllUsers();			
			//Convert Java Object to Json				
			JsonElement element = (JsonElement) gson.toJsonTree(lstUser, new TypeToken<List<User>>() {}.getType());	
			JsonArray jsonArray = element.getAsJsonArray();
			//String listData=jsonArray.toString();				
			//Return Json in the format required by jTable plugin
			//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
			//response.getWriter().print(listData);
			
			
			response.setContentType("application/html");
			response.setHeader("cache-control", "no-cache");
			System.out.println(jsonArray.toString());
			response.getWriter().println(jsonArray.toString());
			

			//out.println(jsonResponse.toString());
			//out.flush();

			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}
        
        if(action.equalsIgnoreCase("getComp")){
			try{						
			//Fetch Data from User Table				
			List<compData> comp_DL=new ArrayList<compData>();
			Gson gson = new Gson();
			comp_DL=dao.getComplData();			
			//Convert Java Object to Json				
			JsonElement element = (JsonElement) gson.toJsonTree(comp_DL, new TypeToken<List<compData>>() {}.getType());	
			JsonArray jsonArray = element.getAsJsonArray();
			//String listData=jsonArray.toString();				
			//Return Json in the format required by jTable plugin
			//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
			//response.getWriter().print(listData);
			
			
			response.setContentType("application/html");
			response.setHeader("cache-control", "no-cache");
			System.out.println(jsonArray.toString());
			response.getWriter().println(jsonArray.toString());
			

			//out.println(jsonResponse.toString());
			//out.flush();

			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}*/
        
        if(action.equalsIgnoreCase("LinegrphData")){
        	System.out.println("----------------1-------------");
			try{						
				List<grphValues> grph_DL=new ArrayList<grphValues>();
				System.out.println("----------------2-------------");
				Gson gson = new Gson();
				String srt=request.getParameter("lQuery");
				System.out.println("----------------3-------------");
				grph_DL=dao.StreamData(srt);		
				System.out.println("----------------4-------------"+grph_DL);
				//Convert Java Object to Json				
				JsonElement element = (JsonElement) gson.toJsonTree(grph_DL, new TypeToken<List<grphValues>>() {}.getType());	
				System.out.println("----------------5-------------");
				JsonArray jsonArray = element.getAsJsonArray();
				System.out.println("----------------6-------------");
				//String listData=jsonArray.toString();				
				//Return Json in the format required by jTable plugin
				//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
				//response.getWriter().print(listData);
				
				
				response.setContentType("application/html");
				response.setHeader("cache-control", "no-cache");
				System.out.println(jsonArray.toString());
				System.out.println("----------------7-------------");
				response.getWriter().println(jsonArray.toString());
				System.out.println("----------------8-------------");

				//out.println(jsonResponse.toString());
				//out.flush();


			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}
        if(action.equalsIgnoreCase("loadLables")){
			try{						
				
				String str = dao.loadLineLables();

				
				
			System.out.println("str"+str);
			response.getWriter().write(str);
//			response.getWriter().write(pcounts+":"+vcounts);
			}

		catch(Exception ex){
			String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
			response.getWriter().print(error);
			ex.printStackTrace();
		}	
        }
        
        if(action.equalsIgnoreCase("getTableNames")){
			try{						
				List<AppTable> appTableNames = new ArrayList<AppTable>();
				Gson gson = new Gson();
				appTableNames=dao.getAppTableNames();
				System.out.println("Inside servlet ... Called DAO");
				//Convert Java Object to Json				
				JsonElement element = (JsonElement) gson.toJsonTree(appTableNames, new TypeToken<List<grphValues>>() {}.getType());	
				JsonArray jsonArray = element.getAsJsonArray();
				//String listData=jsonArray.toString();				
				//Return Json in the format required by jTable plugin
				//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
				//response.getWriter().print(listData);
				
				
				response.setContentType("application/html");
				response.setHeader("cache-control", "no-cache");
				System.out.println(jsonArray.toString());
				response.getWriter().println(jsonArray.toString());
				System.out.println("Inside servlet ... Returned JASON Response");
				//out.println(jsonResponse.toString());
				//out.flush();


			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}
        
        if(action.equalsIgnoreCase("getTableColumn")){
			try{
				System.out.println(" --------------------------- "+request.getParameter("tableId"));
				System.out.println(" --------------------------- "+request.getParameter("tableName"));
				int id = Integer.parseInt(request.getParameter("tableId"));
				String name = request.getParameter("tableName");
				List<AppColumn> appTableColumn = new ArrayList<AppColumn>();
				Gson gson = new Gson();
				appTableColumn=dao.getAppTableColumn(id,name);
				System.out.println("Inside servlet ... Called DAO");
				//Convert Java Object to Json				
				JsonElement element = (JsonElement) gson.toJsonTree(appTableColumn, new TypeToken<List<grphValues>>() {}.getType());	
				JsonArray jsonArray = element.getAsJsonArray();
				//String listData=jsonArray.toString();				
				//Return Json in the format required by jTable plugin
				//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
				//response.getWriter().print(listData);
				
				
				response.setContentType("application/html");
				response.setHeader("cache-control", "no-cache");
				System.out.println(jsonArray.toString());
				response.getWriter().println(jsonArray.toString());
				System.out.println("Inside servlet ... Returned JASON Response");
				//out.println(jsonResponse.toString());
				//out.flush();


			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}
        
        
        if(action.equalsIgnoreCase("availibility")){
			try{
				//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + request.getParameter("tableQuery"));
				List<Availibility> ava=new ArrayList<Availibility>();
				Gson gson = new Gson();
				ava=dao.getAvailibility(request.getParameter("tableQuery"));			
				//Convert Java Object to Json				
				JsonElement element = (JsonElement) gson.toJsonTree(ava, new TypeToken<List<User>>() {}.getType());	
				JsonArray jsonArray = element.getAsJsonArray();
				//String listData=jsonArray.toString();				
				//Return Json in the format required by jTable plugin
				//listData="{\"Result\":\"OK\",\"Records\":"+listData+"}";			
				//response.getWriter().print(listData);
				
				
				response.setContentType("application/html");
				response.setHeader("cache-control", "no-cache");
				System.out.println(jsonArray.toString());
				response.getWriter().println(jsonArray.toString());
				

				//out.println(jsonResponse.toString());
				//out.flush();


			}catch(Exception ex){
				String error="{\"Result\":\"ERROR\",\"Message\":"+ex.getMessage()+"}";
				response.getWriter().print(error);
				ex.printStackTrace();
			}				
		}
        /*

        if (action.equalsIgnoreCase("listUser")){
            forward = LIST_USER;
            request.setAttribute("users", dao.getAllUsers());
        } 
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
        */
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        //request.setAttribute("users", dao.getAllUsers());
        view.forward(request, response);
    }
}