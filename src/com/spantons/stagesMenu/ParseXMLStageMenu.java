package com.spantons.stagesMenu;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.spantons.stages.GameStagesManager;
import com.spantons.stages.IFontStage;
import com.spantons.stages.IStage;

public class ParseXMLStageMenu {

	public static IStage getStageFromXML(String _path, GameStagesManager _gsm){
		try {
			File file = new File(_path);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document document = builder.parse(file);
			document.getDocumentElement().normalize();
		 
			if (document.hasChildNodes()) 
				return createStage(document.getChildNodes(),_gsm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * @throws NoSuchFieldException **************************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static IStage createStage(NodeList childNodes, GameStagesManager _gsm) throws DOMException, ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
		 
		if(childNodes != null && childNodes.getLength() > 0) {
			for (int i = 0; i < childNodes.getLength(); i++) {
				 
				Node nNode = childNodes.item(i);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					StagesMenu aux = new StagesMenu(_gsm);

					Class fontClass = Class.forName(
							"com.spantons.stagesMenu." + eElement.getElementsByTagName("Select").item(0).getTextContent());
					Class[] types = {GameStagesManager.class};
					Constructor constructor = fontClass.getConstructor(types);
					Object[] parameters = {_gsm};
					Object instance = constructor.newInstance(parameters);
					aux.select = (ISelectAction) instance;
					
					Class fontClass2 = Class.forName(
							"com.spantons.stagesMenu." + eElement.getElementsByTagName("Font").item(0).getTextContent());
					Class[] types2 = {StagesMenu.class};
					Constructor constructor2 = fontClass2.getConstructor(types2);
					Object[] parameters2 = {aux};
					Object instance2 = constructor2.newInstance(parameters2);
					aux.font = (IFontStage) instance2;
					
					if(eElement.getElementsByTagName("Background").item(0) != null){
						NodeList backgrounds = eElement.getElementsByTagName("Background");
						Field field = Color.class.getField(eElement.getElementsByTagName("BackgroundColor").item(0).getTextContent());
						Color c = (Color)field.get(null);
						if (backgrounds.getLength() < 2) {
							aux.bg = new Background(
									eElement.getElementsByTagName("Background").item(0).getTextContent(),
									Double.parseDouble(eElement.getElementsByTagName("BackgroundMoveScale").item(0).getTextContent()), 
									Boolean.parseBoolean(eElement.getElementsByTagName("BackgroundRepeat").item(0).getTextContent()),
									c);
						
						} else {
							ArrayList<String> listBackgroundsPath = new ArrayList<String>();
							for (int l = 0; l < backgrounds.getLength(); l++) {
								Node imageNode = backgrounds.item(l);
								if (imageNode.getNodeType() == Node.ELEMENT_NODE)
									listBackgroundsPath.add(imageNode.getTextContent());
							}
							Random randomGenerator = new Random();
							aux.bg = new Background(listBackgroundsPath.get(randomGenerator.nextInt(listBackgroundsPath.size())),
									Double.parseDouble(eElement.getElementsByTagName("BackgroundMoveScale").item(0).getTextContent()), 
									Boolean.parseBoolean(eElement.getElementsByTagName("BackgroundRepeat").item(0).getTextContent()),
									c);
						}
					}
					
					NodeList imagesList = eElement.getElementsByTagName("ImageComposite");
					if (imagesList.getLength() > 0) {
						ArrayList<ImageComposite> imagesIntoScene = new ArrayList<ImageComposite>();
						for (int j = 0; j < imagesList.getLength(); j++) {
							Node imageNode = imagesList.item(j);
							if (imageNode.getNodeType() == Node.ELEMENT_NODE) {
								Element imageComposite = (Element) imageNode;
								imagesIntoScene.add(
										new ImageComposite(
												imageComposite.getElementsByTagName("Path").item(0).getTextContent(), 
												Integer.parseInt(imageComposite.getElementsByTagName("ResizeLength").item(0).getTextContent()), 
												Integer.parseInt(imageComposite.getElementsByTagName("X").item(0).getTextContent()), 
												Integer.parseInt(imageComposite.getElementsByTagName("Y").item(0).getTextContent())));
							}
						
						aux.images = new ImagesIntoScene(imagesIntoScene);}
					}
					
					if(eElement.getElementsByTagName("Title").item(0) != null)
						aux.title = eElement.getElementsByTagName("Title").item(0).getTextContent();
					
					if(eElement.getElementsByTagName("SomeOtherText").item(0) != null)
						aux.someOtherText = eElement.getElementsByTagName("SomeOtherText").item(0).getTextContent();
					
					aux.choices = new ArrayList<String>();
					NodeList choices = eElement.getElementsByTagName("Choice");
					if (choices.getLength() > 0) {
						for (int k = 0; k < choices.getLength(); k++) {
							Node choice = choices.item(k);
							if (choice.getNodeType() == Node.ELEMENT_NODE) 
								aux.choices.add(choice.getTextContent());
						}
					}
					
					aux.font.setFont();
					return aux;
				}
			}
		}
		return null;
	}
	
}
