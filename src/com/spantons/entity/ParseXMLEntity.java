package com.spantons.entity;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spantons.gameStages.StagesLevels;

public class ParseXMLEntity {

	public static Entity getEntityFromXML(String _path, StagesLevels _stage, int _xMap, int _yMap){
		try {
			File file = new File(_path);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
		 
			if (document.hasChildNodes()) 
				return createEntity(document.getChildNodes(),_stage,_xMap,_yMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/****************************************************************************************/
	private static Entity createEntity(NodeList childNodes, StagesLevels _stage, int _xMap, int _yMap) {
		 
		if(childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				 
				Node nNode = childNodes.item(i);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					Entity aux = null;
					
					if (eElement.getElementsByTagName("Type").item(0).getTextContent().equals("character")) 
						aux = new Character(_stage, _xMap, _yMap);
					else if(eElement.getElementsByTagName("Type").item(0).getTextContent().equals("enemy"))
						aux = new Enemy(_stage, _xMap, _yMap);
						
					aux.description = eElement.getElementsByTagName("Description").item(0).getTextContent();
					aux.health = Double.parseDouble(eElement.getElementsByTagName("Health").item(0).getTextContent());
					aux.maxHealth = Double.parseDouble(eElement.getElementsByTagName("MaxHealth").item(0).getTextContent());
					aux.perversity = Integer.parseInt(eElement.getElementsByTagName("Perversity").item(0).getTextContent());
					aux.maxPerversity = Integer.parseInt(eElement.getElementsByTagName("MaxPerversity").item(0).getTextContent());
					aux.damage = Double.parseDouble(eElement.getElementsByTagName("Damage").item(0).getTextContent());
					aux.damageBackup = Double.parseDouble(eElement.getElementsByTagName("DamageBackup").item(0).getTextContent());
					aux.flinchingIncreaseDeltaTimePerversity = Integer.parseInt(eElement.getElementsByTagName("FlinchingIncreaseDeltaTimePerversity").item(0).getTextContent());
					aux.flinchingDecreaseDeltaTimePerversity = Integer.parseInt(eElement.getElementsByTagName("FlinchingDecreaseDeltaTimePerversity").item(0).getTextContent());
					aux.deltaForReduceFlinchingIncreaseDeltaTimePerversity = Integer.parseInt(eElement.getElementsByTagName("DeltaForReduceFlinchingIncreaseDeltaTimePerversity").item(0).getTextContent());
					aux.moveSpeed = Integer.parseInt(eElement.getElementsByTagName("MoveSpeed").item(0).getTextContent());
					aux.scale = Double.parseDouble(eElement.getElementsByTagName("Scale").item(0).getTextContent());
					aux.dead = Boolean.parseBoolean(eElement.getElementsByTagName("Dead").item(0).getTextContent());
					aux.facingRight = Boolean.parseBoolean(eElement.getElementsByTagName("FacingRight").item(0).getTextContent());
					aux.visible = Boolean.parseBoolean(eElement.getElementsByTagName("Visible").item(0).getTextContent());
					aux.loadSprite(eElement.getElementsByTagName("HUD").item(0).getTextContent(), eElement.getElementsByTagName("Sprite").item(0).getTextContent());
					return aux;
				}
			}
		}
		return null;
	}
	
}
