package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.techm.psd.common.dao.BaseDAO;

import java.util.Map.Entry;

import model.AppColumn;
import model.AppTable;
import model.Availibility;
import model.User;
import model.compData;
import model.getter;
import model.grphValues;
import util.*;

public class UserDao extends BaseDAO{

	 private Connection connection;

	    public UserDao() {
	    }	    
	    
	    public List<Availibility> getAvailibility(String query) throws SQLException {
	    	connection = getTbl3Connection();
	    	System.out.println("Inside getAvailibility ... Got Dyson connection");
        List<Availibility> availibilitys = new ArrayList<Availibility>();
        try {
            Statement statement = connection.createStatement();
            String Query = query;
            System.out.println(Query);
            ResultSet rs = statement.executeQuery(Query);
            while (rs.next()) {
            	Availibility ava = new Availibility();
            	ava.setAppName(rs.getString("SUB_APP_NAME"));
            	ava.setApps(rs.getInt("APPS"));
            	ava.setAva(rs.getInt("AVAILIBILITY"));  
            	availibilitys.add(ava);
            }
            System.out.println("Inside getAvailibility ... Got list of Availibility");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Inside getAvailibility ... Returned list of Availibility");
        return availibilitys;
    }
	    
	    
	    public List<AppTable> getAppTableNames() throws SQLException {
	    	connection = getConnection();
	    	System.out.println("Inside getAppTableNames ... Got Dyson connection");
        List<AppTable> AppTables = new ArrayList<AppTable>();
        try {
            Statement statement = connection.createStatement();
            String Query = "select * from APP_TBL_MASTER order by TBL_ID asc";
            System.out.println(Query);
            ResultSet rs = statement.executeQuery(Query);
            while (rs.next()) {
            	AppTable appTable = new AppTable();
            	appTable.setAppName(rs.getString("APP_NAME"));
            	appTable.setTblId(rs.getInt("TBL_ID"));
            	appTable.setTblName(rs.getString("TBL_NAME"));
            	appTable.setTblQuery(rs.getString("TBL_QUERY")); 
                AppTables.add(appTable);
            }
            System.out.println("Inside getAppTableNames ... Got list of AppTables");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Inside getAppTableNames ... Returned list of AppTables");
        return AppTables;
    }
	    
	    public List<AppColumn> getAppTableColumn(int id, String name) throws SQLException {
	    	connection = getConnection();
	    	System.out.println("Inside getAppTableColumn ... Got Dyson connection");
        List<AppColumn> appColumns = new ArrayList<AppColumn>();
        try {
            Statement statement = connection.createStatement();
            String Query = "select * from APP_TBL_COLUMN where TBL_ID = " + id;
            
            System.out.println(Query);
            ResultSet rs = statement.executeQuery(Query);
            while (rs.next()) {
            	AppColumn appColumn = new AppColumn();
            	appColumn.setTblId(rs.getInt("TBL_ID"));
            	appColumn.setTblName(name);
            	appColumn.setTblColumn(rs.getString("TBL_COLUMN"));  
            	appColumns.add(appColumn);
            }
            System.out.println("Inside getAppTableColumn ... Got list of AppTables");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Inside getAppTableColumn ... Returned list of AppTables");
        return appColumns;
    }

	    public List<User> getAllUsers(String query) throws SQLException {
	    	connection = getTbl1Connection();
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            String Query = query;
            System.out.println(Query);
            ResultSet rs = statement.executeQuery(Query);
            while (rs.next()) {
                User user = new User();
                user.setCnt(rs.getInt("cnt"));
                user.setPriority(rs.getString("priority"));
                user.setCritical(rs.getString("Critical"));  
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
	    public List<compData> getComplData(String query) throws SQLException {
	    	connection = getTbl2Connection();
	        List<compData> compD = new ArrayList<compData>();
	        try {
	        	String Query = query;
	        	/*String Query= "select 'OS_PATCH' as Name ,(select count(*) from MED_SERVER_OS) as Total_cnt," 
	        			+ "(select count(*) from med_server_os where COMPLIANT = 'YES') as Total_complaint"
	        			+" from dual";*/
	        	 System.out.println(Query);
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement.executeQuery(Query);
	            
	           
	            while (rs.next()) {
	                compData compl = new compData();
	                compl.setTotal(rs.getInt("Total_cnt"));
	                compl.setTotal_Complaint(rs.getInt("Total_complaint"));
	                compl.setName(rs.getString("Name"));
	                compD.add(compl);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return compD;
	    }

	   /* public List<User> getAllUsers() {
	    	connection = db.getPerfConnection();
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            String Query = "select priority,cnt,0 as Critical from (select a.*, rank() over (partition by priority order by cnt desc) rnk from" 
+ "(select priority,count(*) cnt from AMOCCON.SBPM_PERFALERTS where status not like '%Final%'  group by priority" 
+ " union select 'P2', 0 cnt from dual union select 'P1', 0 cnt from dual union select 'Proactive', 0 cnt from dual"
+ " union select 'Outage', 0 cnt from dual) a) where rnk=1 order by priority desc";
            System.out.println(Query);
            ResultSet rs = statement.executeQuery(Query);
            while (rs.next()) {
                User user = new User();
                user.setCnt(rs.getInt("cnt"));
                user.setPriority(rs.getString("priority"));
                user.setCritical(rs.getString("Critical"));  
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
	    public List<compData> getComplData() {
	    	connection = db.getComplConnection();
	        List<compData> compD = new ArrayList<compData>();
	        try {
	        	String Query= "select 'OS_PATCH' as Name ,(select count(*) from MED_SERVER_OS) as Total_cnt," 
	        			+ "(select count(*) from med_server_os where COMPLIANT = 'YES') as Total_complaint"
	        			+" from dual";
	        	 System.out.println(Query);
	            Statement statement = connection.createStatement();
	            ResultSet rs = statement.executeQuery(Query);
	            
	           
	            while (rs.next()) {
	                compData compl = new compData();
	                compl.setTotal(rs.getInt("Total_cnt"));
	                compl.setTotal_Complaint(rs.getInt("Total_complaint"));
	                compl.setName(rs.getString("Name"));
	                compD.add(compl);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return compD;
	    }*/
	    public List<grphValues> StreamData(String query) {
	    	List<grphValues> grphD = new ArrayList<grphValues>();
	    	String pojo = "model.getter";
	    	Connect c = new Connect();
	    	List data = null;
	    	String temp_stream = null ;

	    	ArrayList<Integer> storehrlydata = null;
	    	        try {
	    				data = c.runCommand(pojo,query);
	    			} catch (ClassNotFoundException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (SQLException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			
	    	        Iterator i = data.iterator();
	    	        Map<String, ArrayList> map1 = new HashMap(); 
	    	        for (int iq=0;iq<data.size();iq++)
	    	        {
	    	        	getter element = (getter)i.next();
	    	            int BATCH_NO= element.getBATCH_NO();
	    	            int FILE_COUNT = element.getFILE_COUNT();
	    	            String STREAM = element.getSTREAM();
	    	            if (iq==0)
	    	            {
	    	            	temp_stream = STREAM; 
	    	            	storehrlydata = new ArrayList<Integer> ();
	    	            }
	    	            if (!(temp_stream.equalsIgnoreCase(STREAM)))
	    	            {
	    	            	// Here
	    	            	map1.put(temp_stream, (ArrayList) storehrlydata);
	    	            	storehrlydata = new ArrayList<Integer> ();
	    	            	if (BATCH_NO == 0)
	    	            	{
	    	            		storehrlydata.add(BATCH_NO,FILE_COUNT);
	    	            	}
	    	            }
	    	            else
	    	            {
	    	            	storehrlydata.add(BATCH_NO,FILE_COUNT);            	
	    	            }
	    	            temp_stream = STREAM;
	    	            if (iq==(data.size()-1))
	    	            {
	    	            	// Here
	    	            	map1.put(STREAM, (ArrayList) storehrlydata);            	
	    	            	storehrlydata = new ArrayList<Integer> ();
	    	            }
	    	           }
	    	        for(Entry<String, ArrayList> entry: map1.entrySet()) {
	    	            grphValues g = new grphValues();
		                g.setStream(entry.getKey());
		                g.setValues(entry.getValue());
		                grphD.add(g);
	    	        }
	    	    return grphD;
	}
	    
	    
	    public String fetchGuageData(String labl,String qery)
	    {
	    	String res="";
	    	if(labl!=null && labl.equals("no"))
			{
	String pcounts="0";
	String vcounts="0";
	try {
		
	
		
		

		Connection connDyson = getDialConnection();
		Statement stm=connDyson.createStatement();
		
		/*ResultSet rs=stm.executeQuery("select pcounts,vcounts,htime from ctps_processing_speed where to_date(htime,'HH24:mi')  <=to_date('"+curTime+"','HH24:mi') "+
				"  and to_date(htime,'HH24:mi') >= (to_date('"+curTime+"','HH24:mi') - interval '14' minute) ");
	*/
		ResultSet rs=stm.executeQuery(qery);
	/*	ResultSet rs=stm.executeQuery("select pcounts,vcounts,htime from ctps_processing_speed where to_date(htime,'HH24:mi')  <=to_date(to_char(sysdate,'HH24:mi'),'HH24:mi') "+ 
				  " and to_date(htime,'HH24:mi') >= (to_date(to_char(sysdate,'HH24:mi'),'HH24:mi') - interval '14' minute) ");
	*/
		if(rs.next())
		{
			
			pcounts=rs.getString("pcounts");
			vcounts=rs.getString("vcounts");
		}
		rs.close();
		stm.close();
		connDyson.close();

		
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("pcounts"+pcounts+"vcounts"+vcounts);

//	response.getWriter().write(pcounts+":"+vcounts);
	res=pcounts+":"+vcounts;
	}	else 	if(labl!=null && labl.equals("yes"))
	{
		//String res="";
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		
		Connection connDyson = getConnection();
		Statement stm=connDyson.createStatement();
		

		ResultSet rs=stm.executeQuery("select * from ps_gauge_labels ");

		while(rs.next())
		{
			res=rs.getString("firstGaugeName")+";"+rs.getString("secondGaugeName")+";" +
					""+rs.getString("minRange")+";"+rs.getString("maxRange")+";"+rs.getString("dataInfo")+";"+rs.getString("dataQuery")+";"+rs.getString("refreshRate");
		
		}
		rs.close();
		stm.close();
		connDyson.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//response.getWriter().write(res);
	}
	    	
			return res;
	    	
	    }

		public String loadLineLables() {
			// TODO Auto-generated method stub
			String str="";
			try{
				Connection	connDyson = getConnection();
				Statement stm=connDyson.createStatement();
			
			/*ResultSet rs=stm.executeQuery("select pcounts,vcounts,htime from ctps_processing_speed where to_date(htime,'HH24:mi')  <=to_date('"+curTime+"','HH24:mi') "+
					"  and to_date(htime,'HH24:mi') >= (to_date('"+curTime+"','HH24:mi') - interval '14' minute) ");
		*/
			ResultSet rs=stm.executeQuery("select * from ps_line_labels");
		/*	ResultSet rs=stm.executeQuery("select pcounts,vcounts,htime from ctps_processing_speed where to_date(htime,'HH24:mi')  <=to_date(to_char(sysdate,'HH24:mi'),'HH24:mi') "+ 
					  " and to_date(htime,'HH24:mi') >= (to_date(to_char(sysdate,'HH24:mi'),'HH24:mi') - interval '14' minute) ");
		*/
	
			if(rs.next())
			{
				str=str+rs.getString("lineHeader")+";"+rs.getString("xTitle")+";"+rs.getString("yTitle")+";"+rs.getString("xRange")+";"+rs.getString("xInterval")+";"+
						rs.getString("lQuery")+";"+rs.getString("refreshRate");
				
				
			}
			rs.close();
			stm.close();
			connDyson.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return str;
		}
}