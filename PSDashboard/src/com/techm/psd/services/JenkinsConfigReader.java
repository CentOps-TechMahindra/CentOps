package com.techm.psd.services;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class JenkinsConfigReader {

	private static Logger logger = Logger.getLogger(JenkinsConfigReader.class);
	private void getConfigResponse(String urlStr, String userpass) throws Exception{
		logger.info("Enter into JenkinsConfigReader.getConfigResponse!!");
		URL url = new URL(urlStr);
        URLConnection connection = url.openConnection();
        String basicAuth 		= "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(userpass.getBytes("UTF-8"));
        connection.setRequestProperty ("Authorization", basicAuth);
        Document doc = parseConfigResponseXML(connection.getInputStream());
        NodeList descNodes = doc.getElementsByTagName("defaultSlaves");
        
        for(int i=0; i<descNodes.getLength();i++){
            System.out.println(descNodes.item(i).getTextContent().trim());
        }
        logger.info("Exit from JenkinsConfigReader.getConfigResponse!!");
    }

    private Document parseConfigResponseXML(InputStream stream) throws Exception{
    	logger.info("Enter into JenkinsConfigReader.parseConfigResponseXML!!");
        DocumentBuilderFactory objDocumentBuilderFactory = null;
        DocumentBuilder objDocumentBuilder = null;
        Document doc = null;
        try{
            objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
            objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
            doc = objDocumentBuilder.parse(stream);
        }catch(Exception ex){
        	logger.error("Error in JenkinsConfigReader.parseConfigResponseXML!!", ex);
            throw ex;
        }       
        logger.info("Exit from JenkinsConfigReader.parseConfigResponseXML!!");
        return doc;
    }
  
}
