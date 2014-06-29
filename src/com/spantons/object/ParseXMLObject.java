package com.spantons.object;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.ILoadSprite;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.stagesLevel.StagesLevel;

public class ParseXMLObject {

	public static Object getObjectFromXML(String _path, StagesLevel _stage, int _xMap, int _yMap){
		try {
			URL is = ParseXMLObject.class.getResource(_path);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(is.toString());
			document.getDocumentElement().normalize();
		 
			if (document.hasChildNodes()) 
				return createObject(document.getChildNodes(),_stage,_xMap,_yMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * @throws ClassNotFoundException 
	 * @throws DOMException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException **************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Object createObject(NodeList childNodes, StagesLevel _stage, int _xMap, int _yMap) throws DOMException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		 
		if(childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				 
				Node nNode = childNodes.item(i);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					Object aux = new Object(_stage.getTileMap(), _xMap, _yMap);
					
					if(eElement.getElementsByTagName("Description").item(0) != null) 
						aux.description = eElement.getElementsByTagName("Description").item(0).getTextContent();
					
					if(eElement.getElementsByTagName("Scale").item(0) != null) 
						aux.scale = Double.parseDouble(eElement.getElementsByTagName("Scale").item(0).getTextContent());
					
					if(eElement.getElementsByTagName("OffSetXLoading").item(0) != null) 
						aux.offSetXLoading = Integer.parseInt(eElement.getElementsByTagName("OffSetXLoading").item(0).getTextContent());
					
					if(eElement.getElementsByTagName("OffSetYLoading").item(0) != null) 
						aux.offSetYLoading = Integer.parseInt(eElement.getElementsByTagName("OffSetYLoading").item(0).getTextContent());
					
					if(eElement.getElementsByTagName("Type").item(0) != null) {
						String type = eElement.getElementsByTagName("Type").item(0).getTextContent();
						if (type.equals("CONSUMABLE")) {
							aux.type = Object.CONSUMABLE;
							if(eElement.getElementsByTagName("TimeToConsumable").item(0) != null)
								aux.timeToConsumable = Integer.parseInt(eElement.getElementsByTagName("TimeToConsumable").item(0).getTextContent());
						}
						else if (type.equals("NON_CONSUMABLE")) 
							aux.type = Object.NON_CONSUMABLE;
					}
						
					if(eElement.getElementsByTagName("Update").item(0) != null) {
						Class updateClass = Class.forName(
								"com.spantons.object." + eElement.getElementsByTagName("Update").item(0).getTextContent());
						Class[] types = {StagesLevel.class, Object.class};
						Constructor constructor = updateClass.getConstructor(types);
						java.lang.Object[] parameters = {_stage, aux};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.update = (IUpdateable) instance;
					}

					if(eElement.getElementsByTagName("Draw").item(0) != null) {
						Class updateClass = Class.forName(
								"com.spantons.object." + eElement.getElementsByTagName("Draw").item(0).getTextContent());
						Class[] types = {Object.class};
						Constructor constructor = updateClass.getConstructor(types);
						java.lang.Object[] parameters = {aux};
						java.lang.Object instance = constructor.newInstance(parameters);
						aux.draw = (IDrawable) instance;
					}
					
					if(eElement.getElementsByTagName("AttributeValue").item(0) != null) {
						double attributeValue = Double.parseDouble(eElement.getElementsByTagName("AttributeValue").item(0).getTextContent());
						if(eElement.getElementsByTagName("Attribute").item(0) != null) {
							Class updateClass = Class.forName(
									"com.spantons.object." + eElement.getElementsByTagName("Attribute").item(0).getTextContent());
							Class[] types = {double.class,Object.class};
							Constructor constructor = updateClass.getConstructor(types);
							java.lang.Object[] parameters = {attributeValue,aux};
							java.lang.Object instance = constructor.newInstance(parameters);
							aux.attribute = (IObjectAttribute) instance;
						}
					}
					
					if(eElement.getElementsByTagName("Sprite").item(0) != null) {
						String sprite = eElement.getElementsByTagName("Sprite").item(0).getTextContent();
						if(eElement.getElementsByTagName("LoadSprite").item(0) != null) {
							Class updateClass = Class.forName(
									"com.spantons.object." + eElement.getElementsByTagName("LoadSprite").item(0).getTextContent());
							Class[] types = {Object.class};
							Constructor constructor = updateClass.getConstructor(types);
							java.lang.Object[] parameters = {aux};
							java.lang.Object instance = constructor.newInstance(parameters);
							aux.loadSprite = (ILoadSprite) instance;
							aux.loadSprite.loadSprite(sprite);
						}
					}
					
					return aux;
				}
			}
		}
		return null;
	}
	
}
