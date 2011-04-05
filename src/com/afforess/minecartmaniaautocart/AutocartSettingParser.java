package com.afforess.minecartmaniaautocart;

import java.io.File;
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
import org.w3c.dom.NodeList;

import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.config.SettingParser;

public class AutocartSettingParser implements SettingParser{
	private static final double version = 1.0;
	
	public boolean isUpToDate(Document document) {
		try {
			NodeList list = document.getElementsByTagName("version");
			Double version = MinecartManiaConfigurationParser.toDouble(list.item(0).getChildNodes().item(0).getNodeValue(), 0);
			return version == AutocartSettingParser.version;
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean read(Document document) {
		Object value;
		NodeList list;
		String setting;
		
		try {
			setting = "AutocartForPlayersOnly";
			list = document.getElementsByTagName(setting);
			value = MinecartManiaConfigurationParser.toBool(list.item(0).getChildNodes().item(0).getNodeValue());
			MinecartManiaWorld.getConfiguration().put(setting, value);
			
			setting = "DefaultThrottle";
			list = document.getElementsByTagName(setting);
			value = MinecartManiaConfigurationParser.toInt(list.item(0).getChildNodes().item(0).getNodeValue(), 25);
			MinecartManiaWorld.getConfiguration().put(setting, value);
		}
		catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public boolean write(File configuration) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			//root elements
			Document doc = docBuilder.newDocument();
			doc.setXmlStandalone(true);
			Element rootElement = doc.createElement("MinecartManiaConfiguration");
			doc.appendChild(rootElement);
			
			Element setting = doc.createElement("version");
			setting.appendChild(doc.createTextNode(String.valueOf(version)));
			rootElement.appendChild(setting);
			
			setting = doc.createElement("AutocartForPlayersOnly");
			Comment comment = doc.createComment("Only minecarts with players in them will be effected by Autocart");
			setting.appendChild(doc.createTextNode("false"));
			rootElement.appendChild(setting);
			rootElement.insertBefore(comment,setting);
			
			setting = doc.createElement("DefaultThrottle");
			comment = doc.createComment("The default throttle percent for minecarts. Must be between 1 and 500.");
			setting.appendChild(doc.createTextNode("100"));
			rootElement.appendChild(setting);
			rootElement.insertBefore(comment,setting);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(configuration);
			transformer.transform(source, result);
		}
		catch (Exception e) { return false; }
		return true;
	}
}
