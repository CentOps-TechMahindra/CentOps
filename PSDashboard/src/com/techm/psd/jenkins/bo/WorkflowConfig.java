package com.techm.psd.jenkins.bo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.techm.psd.common.dto.WorkflowConfigDTO;

public class WorkflowConfig {
	private Logger logger = Logger.getLogger(WorkflowConfig.class);
	
	private final String _CONFIG	= "/config.xml";
	
	 /**
     * Return information about the specified job.
     */
    public WorkflowConfigDTO getJobConfig(JobData jd) {
    	String 					authorization	= "";
		String 					urlStr 			= "";
		URL 					url 			= null;
		WorkflowConfigDTO 		wConfDTO 		= null;
        Document 				jobdoc 			= null;

		urlStr 				= String.format("%s:%s/jenkins/job/%s/%s/", jd.getServer(), jd.getPort(), jd.getJob(), _CONFIG);
		authorization 		= jd.getUsername() + ":" + jd.getPassword();
		try {
			url 	= new URL(urlStr);
		    jobdoc 	= get(url, authorization);
            if (jobdoc != null) {
            	wConfDTO = parseConfigJob(jobdoc.getDocumentElement());
            }
		} catch (MalformedURLException mfex) {
        	logger.error("Error in WorkflowConfig.getJobConfig"+mfex.getMessage());
        } catch (IOException ioex) {
        	logger.error("Error in WorkflowConfig.getJobConfig"+ioex.getMessage());
        }

        return wConfDTO;
    }
    
	/**
     * Submit an XML API request to the Jenkins instance and retrieve
     * the XML response.
     *
     * @param  url   Jenkins XML API url request
     * @return XML result 
     */
    private Document get(URL url, String authorization) throws MalformedURLException,IOException {
        Document doc = null; 

        URLConnection conn = url.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);
        String basicAuth = "Basic " + javax.xml.bind.DatatypeConverter.printBase64Binary(authorization.getBytes("UTF-8"));
	    conn.setRequestProperty ("Authorization", basicAuth);
	    conn.connect();

        // Read the XML reponse from the Jenkins server
        try {
            // Read the response from the server
            if (conn instanceof HttpURLConnection) {
                HttpURLConnection httpConn = (HttpURLConnection) conn;
                if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    doc = db.parse(conn.getInputStream());        
                }
            }
        } catch (ParserConfigurationException pcex) {
        	logger.error("Error in WorkflowConfig.get"+pcex.getMessage());
        } catch (SAXException sex) {
        	logger.error("Error in WorkflowConfig.get"+sex.getMessage());
        }

        return doc;
    }
		
    
    /**
     * Parse the XML document and construct the job data object.
     *
     * @param   root   XML document
     * @return  Job data
     */
    private WorkflowConfigDTO parseConfigJob(Element root) {
    	WorkflowConfigDTO wConfDTO = null;

        // Parse the XML
        if (root != null) {
        	wConfDTO = new WorkflowConfigDTO();

            // Parse the job data
            NodeList jobNodes = root.getChildNodes();
            for (int idx = 0; idx < jobNodes.getLength(); idx++) {
            	Node node = jobNodes.item(idx);
                if ((node != null) && (node.getNodeName() != null) && (node.getTextContent() != null)) {
                    String nodeName  = node.getNodeName();
                    String nodeValue = node.getTextContent();
                    if (nodeName == "description") {
                    	wConfDTO.setDescription(nodeValue);
                    } else if (nodeName == "keepDependencies") {
                    	wConfDTO.setKeepDependencies(Boolean.parseBoolean(nodeValue));
                    } else if (nodeName == "properties") {
                        parseConfigProp(node, wConfDTO);
                    } else if (nodeName == "canRoam") {
                    	wConfDTO.setCanRoam(Boolean.parseBoolean(nodeValue));
                    } else if (nodeName == "disabled") {
                    	wConfDTO.setDisabled(Boolean.parseBoolean(nodeValue));
                    } else if (nodeName == "blockBuildWhenDownstreamBuilding") {
                    	wConfDTO.setBlockBuildWhenDownstreamBuilding(Boolean.parseBoolean(nodeValue));
                    } else if (nodeName == "blockBuildWhenUpstreamBuilding") {
                    	wConfDTO.setBlockBuildWhenUpstreamBuilding(Boolean.parseBoolean(nodeValue));
                    } else if (nodeName == "authToken") {
                    	wConfDTO.setAuthToken(nodeValue);
                    } else if (nodeName == "concurrentBuild") {
                    	wConfDTO.setConcurrentBuild(Boolean.parseBoolean(nodeValue));
                    } else {}
                } else if (node == null) {} 
                else {}
            }
        }
        return wConfDTO; 
    }
    
    /**
     * Parse the build data from XML.
     *
     * @param   node   Build XML node
     * @return  Build data
     */
    private void parseConfigProp(Node node, WorkflowConfigDTO wConfDTO) {
        NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                if (name.equalsIgnoreCase("hudson.model.ParametersDefinitionProperty")) {
                	parseConfig2(child, wConfDTO);
                }
            }
        }
    }
    
    private void parseConfig2(Node node, WorkflowConfigDTO wConfDTO) {
        NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                if (name.equalsIgnoreCase("parameterDefinitions")) {
                	parseParameterDefinitions(child, wConfDTO);
                }
            }
        }
    }
    
    private void parseParameterDefinitions(Node node, WorkflowConfigDTO wDTO){
    	NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                if (name.equalsIgnoreCase("org.jvnet.jenkins.plugins.nodelabelparameter.NodeParameterDefinition")) {
                	parseNodeParam(child, wDTO);
                }
            }
        }
    }
    
    private void parseNodeParam(Node node, WorkflowConfigDTO wConfDTO){
    	NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                String value = child.getTextContent();
                if (name.equalsIgnoreCase("name")) {
                	wConfDTO.setNodeParamName(value);
                } else if (name.equalsIgnoreCase("allowedSlaves")) {
                	wConfDTO.setAllowedSlaves(parseAllowedNode(child));
                } else if (name.equalsIgnoreCase("defaultSlaves")) {
                	wConfDTO.setDefaultSlaves(parseDefaultNode(child));
                } else if (name.equalsIgnoreCase("triggerIfResult")) {
                	wConfDTO.setTriggerIfResult(value);
                } else if (name.equalsIgnoreCase("allowMultiNodeSelection")) {
                	wConfDTO.setAllowMultiNodeSelection(Boolean.parseBoolean(value));
                } else if (name.equalsIgnoreCase("triggerConcurrentBuilds")) {
                	wConfDTO.setTriggerConcurrentBuilds(Boolean.parseBoolean(value));
                } else if (name.equalsIgnoreCase("ignoreOfflineNodes")) {
                	wConfDTO.setIgnoreOfflineNodes(Boolean.parseBoolean(value));
                }
            }
        }
    }
    
    private List<String> parseAllowedNode(Node node){
    	List<String> lSting = new ArrayList<String>();
    	NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                String value = child.getTextContent();
                if (name.equalsIgnoreCase("string")) {
                	lSting.add(value);
                }
            }
        }
		return lSting;
    }
    
    private List<String> parseDefaultNode(Node node){
    	List<String> lSting = new ArrayList<String>();
    	NodeList children = node.getChildNodes();
        for (int idx = 0; idx < children.getLength(); idx++) {
            Node child = children.item(idx);
            if (child != null) {
                String name = child.getNodeName();
                String value = child.getTextContent();
                if (name.equalsIgnoreCase("string")) {
                	lSting.add(value);
                }
            }
        }
		return lSting;
    }
}
