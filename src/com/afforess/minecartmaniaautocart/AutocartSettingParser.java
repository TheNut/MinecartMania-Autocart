package com.afforess.minecartmaniaautocart;

import java.io.File;
import java.util.Enumeration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.config.SettingParser;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;

public class AutocartSettingParser implements SettingParser{
	private static final double version = 1.0;
	private static MinecartManiaLogger log = MinecartManiaLogger.getInstance();

	public boolean isUpToDate(Document document) {
		try {
			NodeList list = document.getElementsByTagName("version");
			Double version = MinecartManiaConfigurationParser.toDouble(list.item(0).getChildNodes().item(0).getNodeValue(), 0);
			log.debug("Autocart Config read: version: " + list.item(0).getTextContent());
			if (version == 1.0) {
				//Place the code to update to the next version here
				//version = 1.1;	//This needs to be updated to the next version of the document.
				//list.item(0).setTextContent(version.toString());
			}
			return version == AutocartSettingParser.version;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean read(Document document) {
		//Set the default configuration before we try to read anything.
		setDefaultConfiguration();

		NodeList list;
		try {
			list = document.getElementsByTagName("MinecartManiaConfiguration").item(0).getChildNodes();	//get the root nodes of the ConfigurationTree
			String elementChildName = "";		//holds the name of the node
			String elementChildValue = "";		//holds the value of the node
			//loop through each of the child nodes of the document
			for (int idx = 0; idx < list.getLength(); idx++) {
				Node elementChild = list.item(idx);	//extract the node
				elementChildName = "";				//reset the child name
				elementChildValue = null;			//reset the child value
				//do we have a valid element node
				if (elementChild.getNodeType() == Node.ELEMENT_NODE) {
					elementChildName = elementChild.getNodeName();	//get the node name
					elementChildValue = elementChild.getTextContent(); //get the node value
					if (elementChildValue != null && elementChildValue != "") {
						//Handle the possible nodes we have at this level.
						if (elementChildName == "version") {
							if (elementChildValue != String.valueOf(version)) { /* documentUpgrade(document); */ }
						} else if (elementChildName == "DefaultThrottle") {
							MinecartManiaWorld.getConfiguration().put(elementChildName, MinecartManiaConfigurationParser.toInt(elementChildValue, getDefaultConfigurationIntegerValue(elementChildName)));
							log.debug("Autocart Config read: " + elementChildName + " = " + elementChildValue);
						} else if (elementChildName == "AutocartForPlayersOnly") {
							MinecartManiaWorld.getConfiguration().put(elementChildName, MinecartManiaConfigurationParser.toBool(elementChildValue));
							log.debug("Autocart Config read: " + elementChildName + " = " + elementChildValue);
						} else {
							log.info("Autocart Config read unknown node: " + elementChildName);
						}
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		debugShowConfigs();
		return true;
	}
	private void setDefaultConfiguration() {
		//Create the default Configuration values
		MinecartManiaWorld.getConfiguration().put("DefaultThrottle",		getDefaultConfigurationIntegerValue("DefaultThrottle"));
		MinecartManiaWorld.getConfiguration().put("AutocartForPlayersOnly",	false);
	}
	private int getDefaultConfigurationIntegerValue(String ConfigName) {
		if (ConfigName == "DefaultThrottle") return (100);
		return 0;
	}

	private void debugShowConfigs() {
		//Display global configuration values
		for (Enumeration<String> ConfigKeys = MinecartManiaWorld.getConfiguration().keys(); ConfigKeys.hasMoreElements();) {
			String temp = ConfigKeys.nextElement();
			String value = MinecartManiaWorld.getConfigurationValue(temp).toString();
			if     (temp == "DefaultThrottle"
				 || temp == "AutocartForPlayersOnly"
				){
				log.debug("Autocart Config: " + temp + " = " + value);
			}
		}
	}

	@Override
	public boolean write(File configuration, Document document) {
		try {
			if (document == null) {
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
				//root elements
				document = docBuilder.newDocument();
				document.setXmlStandalone(true);
				Element rootElement = document.createElement("MinecartManiaConfiguration");
				document.appendChild(rootElement);

				Element setting = document.createElement("version");
				setting.appendChild(document.createTextNode(String.valueOf(version)));
				rootElement.appendChild(setting);

				setting = document.createElement("AutocartForPlayersOnly");
				Comment comment = document.createComment("Only minecarts with players in them will be effected by Autocart");
				setting.appendChild(document.createTextNode("false"));
				rootElement.appendChild(setting);
				rootElement.insertBefore(comment,setting);

				setting = document.createElement("DefaultThrottle");
				comment = document.createComment("The default throttle percent for minecarts. Must be between 1 and 500.");
				setting.appendChild(document.createTextNode("100"));
				rootElement.appendChild(setting);
				rootElement.insertBefore(comment,setting);
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(configuration);
			transformer.transform(source, result);
		}
		catch (Exception e) { return false; }
		return true;
	}
}
