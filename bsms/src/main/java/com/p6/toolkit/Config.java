/**
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.toolkit;

import java.io.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Config {
	/**
	 * 构造函数
	 * @param filename 需要创建的配置文件的文件名
	 */
	public Config(String filename) {
		this.filename = filename;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File f = new File(filename);
			if(f.exists()) {
				doc = builder.parse(f);
			}
			else {
				doc = builder.newDocument();
			}
		} catch (ParserConfigurationException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (SAXException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (IOException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		}
	}
	
	/**
	 * 初始化配置文件
	 */
	public void initialConfig(String databaseURL, String databasePort, String databaseName, String username, String password) {
		try {
			File f = new File(filename);
			if(f.exists()) {
				logger.info("配置文件" + filename + "已经存在.\n消息来自" + Config.class.getName());
				f.delete();
				doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			}
			Element database = doc.createElement("Database");
			Element url = doc.createElement("URL");
			Element dbname = doc.createElement("DatabaseName");
			Element port = doc.createElement("Port");
			Element user = doc.createElement("Username");
			Element pwd = doc.createElement("Password");
			
			Text textURL = doc.createTextNode(databaseURL);
			Text textPort = doc.createTextNode(databasePort);
			Text textDatabaseName = doc.createTextNode(databaseName);
			Text textUsername = doc.createTextNode(username);
			Text textPassword = doc.createTextNode(password);
			
			database.appendChild(url);
			database.appendChild(port);
			database.appendChild(dbname);
			database.appendChild(user);
			database.appendChild(pwd);
			
			url.appendChild(textURL);
			port.appendChild(textPort);
			dbname.appendChild(textDatabaseName);
			user.appendChild(textUsername);
			pwd.appendChild(textPassword);
			
			doc.appendChild(database);
			
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			DOMSource ds = new DOMSource(doc);
			
			PrintWriter pw = new PrintWriter(new FileOutputStream(f));
			
			StreamResult sr = new StreamResult(pw);
			transformer.transform(ds, sr);
		} catch (DOMException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (TransformerConfigurationException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (IllegalArgumentException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (FileNotFoundException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (ParserConfigurationException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (TransformerFactoryConfigurationError e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		} catch (TransformerException e) {
			logger.warn(e.getMessage() + " - " + Config.class.getName());
		}
	}
	
	/**
	 *从配置文件中得到数据库地址 
	 */
	public String getDatabaseURL() {
		NodeList nodeList = doc.getElementsByTagName("URL");
		Node node = nodeList.item(0).getFirstChild();
		return node.getNodeValue();
	}
	
	/**
	 * 从配置文件中得到连接数据库的端口
	 */
	public String getPort() {
		NodeList nodeList = doc.getElementsByTagName("Port");
		Node node = nodeList.item(0).getFirstChild();
		return node.getNodeValue();
	}
	
	/**
	 * 从配置文件中得到数据库名称
	 */
	public String getDatabaseName() {
		NodeList nodeList = doc.getElementsByTagName("DatabaseName");
		Node node = nodeList.item(0).getFirstChild();
		return node.getNodeValue();
	}
	
	/**
	 * 从配置文件中得到用户名
	 */
	public String getUsername() {
		NodeList nodeList = doc.getElementsByTagName("Username");
		Node node = nodeList.item(0).getFirstChild();
		return node.getNodeValue();
	}
	
	/**
	 * 从配置文件中得到用户密码
	 */
	public String getPassword() {
		NodeList nodeList = doc.getElementsByTagName("Password");
		Node node = nodeList.item(0).getFirstChild();
		return node.getNodeValue();
	}

	private String filename = null;
	private Document doc = null;
	private static Logger logger = Logger.getLogger(Config.class.getName());
	
	public static void main(String[] args) {
		Config cf = new Config("config.xml");
		cf.initialConfig("localhost", "1433", "BSMS", "bsms", "bsms");
		System.out.println(cf.getDatabaseURL());
		System.out.println(cf.getPort());
		System.out.println(cf.getDatabaseName());
		System.out.println(cf.getUsername());
		System.out.println(cf.getPassword());
	}
}
