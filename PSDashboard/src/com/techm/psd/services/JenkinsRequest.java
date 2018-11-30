package com.techm.psd.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

public class JenkinsRequest {

	private static Logger LOGGER = Logger.getLogger(JenkinsRequest.class);
	private static final long serialVersionUID = 1L;
	
	// HTTP GET RESPONSE request
	public String getResponse(String url, String userpass) throws Exception {
		LOGGER.info("URL : "+url);
		//START: OPEN HTTP REQUEST
		URL obj 				= new URL(url);
	    HttpURLConnection conn 	= (HttpURLConnection) obj.openConnection();
	    conn.setRequestProperty("Content-Type", "application/xml");
	    conn.setDoOutput(true);
	    conn.setRequestMethod("PUT");
	    
	    String basicAuth 		= "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
	    conn.setRequestProperty ("Authorization", basicAuth);
	    //END: OPEN HTTP REQUEST
	    
	    //START: RESPONSE DATA
	    String data 			=  "{\"format\":\"json\",\"pattern\":\"#\"}";
	    OutputStreamWriter out 	= new OutputStreamWriter(conn.getOutputStream());
	    out.write(data);
	    out.close();
	    
	    new InputStreamReader(conn.getInputStream());   
	    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    
	    String line 			= null;
	    StringBuilder responseData = new StringBuilder();
	    while((line = in.readLine()) != null) {
	        responseData.append(line);
	    }
	    //END: RESPONSE DATA
	    return responseData.toString();
	}

}