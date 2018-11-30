/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.techm.psd.common.dao.BaseDAO;

public class Connect extends BaseDAO{
    public List runCommand(String pojo,String query)
    throws SQLException, ClassNotFoundException
  {
    	Connection conn = getLnGraphConnection();
    
    List toReturn = new ArrayList();
   
    try {
      if ((conn == null) || (!conn.isClosed())) {
      //  conn = DriverManager.getConnection(URL, usr, pwd);
        Statement stm = conn.createStatement();
        //System.out.println(query);
        ResultSet rs = stm.executeQuery(query);
        //stm.executeUpdate("update survey set name='newName' where id = 111");
        toReturn = getObjList(rs, pojo);
      }

    }
    catch (SQLException ex)
    {
      Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
      conn.close();
    } catch (SQLException ex) {
      Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
    }
    return toReturn;
  }

  private List getObjList(ResultSet rs, String pojo)
  {
    List objList = new ArrayList();
    try {
      Object obj = Class.forName(pojo).newInstance();
      Method[] m = Class.forName(pojo).getMethods();
      ResultSetMetaData rsmd = rs.getMetaData();
      int cols = rsmd.getColumnCount();

      while (rs.next()) {
        obj = Class.forName(pojo).newInstance();
        for (int i = 0; i < cols; i++) {
          int k = determineMethod(m, rsmd.getColumnName(i + 1));
          if (k >= 0) {
            Class objCl = m[k].getParameterTypes()[0];

            if ((rs.getObject(i + 1) != null) && (objCl.isPrimitive()))
            {
              if (objCl.getName().equals("int")) {
                m[k].invoke(obj, new Object[] { Integer.valueOf(rs.getInt(i + 1)) });
              }
              if (objCl.getName().equals("timestamp")) {
                m[k].invoke(obj, new Object[] { rs.getTimestamp(i + 1) });
              }
              if (objCl.getName().equals("long")) {
                m[k].invoke(obj, new Object[] { Long.valueOf(rs.getLong(i + 1)) });
              }
              if (objCl.getName().equals("float")) {
                m[k].invoke(obj, new Object[] { Float.valueOf(rs.getFloat(i + 1)) });
              }
              if (objCl.getName().equals("double")) {
                m[k].invoke(obj, new Object[] { Double.valueOf(rs.getDouble(i + 1)) });
              }
              if (objCl.getName().equals("short")) {
                m[k].invoke(obj, new Object[] { Short.valueOf(rs.getShort(i + 1)) });
              }
              if (objCl.getName().equals("char"))
                m[k].invoke(obj, new Object[] { rs.getString(i + 1) });
            } else {
              if ((rs.getObject(i + 1) == null) || 
                (!objCl.getName().equals("java.lang.String"))) continue;
              m[k].invoke(obj, new Object[] { rs.getString(i + 1) });
            }
          }
        }

        objList.add(obj);
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return objList;
  }

  private int determineMethod(Method[] m, String key)
  {
    for (int i = 0; i < m.length; i++) {
      if (m[i].getName().equalsIgnoreCase("set" + key)) {
        return i;
      }
    }
    return -1;
  }
    
}
