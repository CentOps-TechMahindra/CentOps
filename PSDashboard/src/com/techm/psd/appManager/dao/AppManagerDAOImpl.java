package com.techm.psd.appManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import com.techm.psd.common.dto.UrlDTO;
import com.techm.psd.common.dao.BaseDAO;

public class AppManagerDAOImpl  extends BaseDAO {
	
	private Connection connection;

	public AppManagerDAOImpl() {
		try {
			connection = getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUrl(UrlDTO url) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into APP_URL_LIST(URL,DESCRIP,LIC,SRNO) values (?,?,?,APP_URL_SEQ.nextval)");
			preparedStatement.setString(1, url.getUrl());
			preparedStatement.setString(2, url.getDesc());			
			preparedStatement.setString(3, url.getLic());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUrl(int srn) {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("delete from APP_URL_LIST where srno=?");
			preparedStatement.setInt(1, srn);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateUrl(UrlDTO url) throws ParseException {
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("update APP_URL_LIST set url=?, descrip=?, lic=? where srno=?");
			preparedStatement.setString(1, url.getUrl());
			preparedStatement.setString(2, url.getDesc());
			preparedStatement.setString(3, url.getLic());
			preparedStatement.setInt(4, url.getSrn());
			preparedStatement.executeUpdate();
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<UrlDTO> getUrlByRange(int jtStartIndex, int jtPageSize, String sortString) {
		List<UrlDTO> urls = new ArrayList<UrlDTO>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("select APPNAME, SRN, DESCRIP, URL, LIC from (SELECT APPNAME, rownum as RN, SRNO as SRN, DESCRIP as DESCRIP, URL as URL, LIC as LIC FROM APP_URL_LIST WHERE ISFEATURE = 0  AND ISDISABLED = 0 AND ISINSTALLED = 1) where RN >= ? and RN < ?  ORDER BY APPNAME, " + sortString);
			preparedStatement.setInt(1, (jtStartIndex+1));
			preparedStatement.setInt(2, ((jtStartIndex+1) + jtPageSize));
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				UrlDTO url = new UrlDTO();
				url.setSrn(rs.getInt("SRN"));
				url.setUrl(rs.getString("URL"));
				url.setDesc(rs.getString("DESCRIP"));
				url.setLic(rs.getString("LIC"));
				urls.add(url);
			}
			
			preparedStatement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return urls;
	}
	
	public List<UrlDTO> getAllUrls() {
		List<UrlDTO> urls = new ArrayList<UrlDTO>();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT SRNO as SRN, DESCRIP as DESCRIP, URL as URL, LIC as LIC FROM APP_URL_LIST WHERE ISDISABLED = 0 AND ISINSTALLED = 1  ORDER BY APPNAME");
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				UrlDTO url = new UrlDTO();
				url.setSrn(rs.getInt("SRN"));
				url.setUrl(rs.getString("URL"));
				url.setDesc(rs.getString("DESCRIP"));
				url.setLic(rs.getString("LIC"));
				urls.add(url);
			}
			
			preparedStatement.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return urls;
	}
	
	public int getUserCount(){
		int count = 0;
		try{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select count(*) as count FROM APP_URL_LIST WHERE ISFEATURE = 0 AND ISDISABLED = 0 AND ISINSTALLED = 1");
			while (rs.next()){
				count=rs.getInt("count");
			}
			
		}catch (SQLException e){
		e.printStackTrace();
		}
		return count;
	}
}