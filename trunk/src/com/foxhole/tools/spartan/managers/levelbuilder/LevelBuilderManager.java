/* ************************************************************************************
 * Copyright (c) 2010, FoxholeStudios
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list 
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this 
 * list of conditions and the following disclaimer in the documentation and/or other 
 * materials provided with the distribution.
 * Neither the name of FoxholeStudios nor the names of its contributors may be used 
 * to endorse or promote products derived from this software without specific prior 
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * ************************************************************************************/

package com.foxhole.tools.spartan.managers.levelbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foxhole.tools.spartan.entity.GameEntity;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.IGameFormObject;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.IGameStateObject;

/**
 * The level builder manager is responsible for building levels from XML files.
 * Every object in the spartan framework can be constructed from XML data, therefore 
 * a game level (or an entire game) can be inserted in a XML file and constructed.
 *
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class LevelBuilderManager implements ISpartanManager {

	private static LevelBuilderManager _instance = new LevelBuilderManager();
	
	private static String PREFIX 			 	= "fhs:";
	private static String LEVEL_TAG 		 	= PREFIX+"level";
	private static String NAME_TAG 				= PREFIX+"name";
	private static String RENDER_MANAGER_TAG 	= PREFIX+"renderManager";
	private static String RENDER_MANAGER_DEBUG_TAG 	= PREFIX+"debug";
	private static String RENDER_MANAGER_LAYER_TAG 	= PREFIX+"render_layer";
	private static String COLLISION_MANAGER_TAG = PREFIX+"collisionManager";
	private static String ENTITIES_TAG 			= PREFIX+"entities";
	private static String ENTITY_TAG 			= PREFIX+"entity";
	private static String FORM_TAG 				= PREFIX+"form";
	private static String ACTIONS_TAG 			= PREFIX+"actions";
	private static String ATTRIBUTE_TAG			= PREFIX+"attribute";
	private static String STATE_LIST_TAG			= PREFIX+"states";
	private static String STATE_TAG			= PREFIX+"state";
	
	private static String NAME_ATTR = "name";
	private static String CLASS_ATTR = "class";
	private static String X_ATTR = "x";
	private static String Y_ATTR = "y";
	private static String PRIORITY_ATTR = "priority";
	
	/**
	 * Obtains the current active instance of the level builder manager
	 * 
	 * @return a handle the current active instance of the level builder manager, null if no active instance is selected
	 */
	public static LevelBuilderManager getInstance(){
		return _instance;
	}
	
	private LevelBuilderManager(){
		
	}
	
	/**
	 * Start loading the level from a inputstream
	 * 
	 * @param is the inputstream holding the xml level information
	 * @return the name of the level
	 * @throws SpartanException if the level xml is malformed 
	 */
	public String loadLevel(InputStream is) throws SpartanException {
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SpartanException("Could not load level", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SpartanException("Could not parse level file", e);
		} catch (IOException e) {
			throw new SpartanException("Could not read input stream", e);
		}
		// normalize text representation
        doc.getDocumentElement ().normalize();
        
        NodeList levelListData = doc.getElementsByTagName(LEVEL_TAG);
        
        if(levelListData == null || levelListData.getLength() == 0){
        	throw new SpartanException("Mandatory TAG [" + LEVEL_TAG + "] does not exist.");
        }
        
        Element levelElement = (Element)levelListData.item(0);
        
        // obtain the name of the level
		String levelName = getLevelName(levelElement);
		// Obtain the rendermanager data
		
		NodeList renderManagerData = levelElement.getElementsByTagName(RENDER_MANAGER_TAG);
		
		if(renderManagerData == null || renderManagerData.getLength() == 0){
			throw new SpartanException("Mandatory TAG [" + RENDER_MANAGER_TAG + "] does not exist. Could not load renderManager information"); 
		}
		
		Element renderManagerElement = (Element) renderManagerData.item(0);
		processRenderManagerData(renderManagerElement);
		// TODO: Obtain the collisionManager data
		NodeList collisionManagerData = levelElement.getElementsByTagName(COLLISION_MANAGER_TAG);
		processCollisionManagerData(collisionManagerData);
		// TODO: Create all entities
		NodeList entitiesData = levelElement.getElementsByTagName(ENTITIES_TAG);
		processEntitiesData(entitiesData);
		// TODO: Create all action data
		NodeList actionsData = levelElement.getElementsByTagName(ACTIONS_TAG);
		processActionData(actionsData);
		
		return levelName;
	}

	private void processActionData(NodeList actionsData) {
		// TODO Auto-generated method stub
		
	}

	private void processEntitiesData( NodeList entitiesData ) throws SpartanException {
		if(entitiesData == null || entitiesData.getLength() != 1){
			throw new SpartanException("Invalid data entry");
		}
		
		Element entitiesElement = (Element)entitiesData.item(0);
		
		NodeList entitiesNode = entitiesElement.getElementsByTagName(ENTITY_TAG);
		
		for(int entityIdx = 0; entityIdx < entitiesNode.getLength(); entityIdx++){
		        	
        	Node entityNode = entitiesNode.item(entityIdx);
        	
        	if(entityNode.getNodeType() == Node.ELEMENT_NODE){
        		Element entityElement = (Element)entityNode;
        		
        		String name = entityElement.getAttribute("name");
        		
        		GameEntity entity = new GameEntity(name);
        		
        		NodeList formNodeList = entityElement.getElementsByTagName(FORM_TAG);
        		
        		if(formNodeList != null && formNodeList.getLength() > 0){
        			Element formElement = (Element)formNodeList.item(0);
        			
	        		IGameFormObject form = getForm( formElement );
	        		
	        		entity.setForm(form);
        		}
        		
        		// states
        		NodeList statesList = entityElement.getElementsByTagName(STATE_LIST_TAG);
        		
        		if(statesList != null && statesList.getLength() == 1){
        			
        			NodeList stateList = entityElement.getElementsByTagName(STATE_TAG);
        			
        			if(stateList != null){
        				for(int stateIdx = 0; stateIdx < stateList.getLength(); stateIdx++){
        		        	
        		        	Node stateNode = stateList.item(stateIdx);
        		        	
        		        	if(stateNode.getNodeType() == Node.ELEMENT_NODE){
        		        		Element stateElement = (Element)stateNode;
        		        		
        		        		IGameStateObject state = createState( stateElement );
        		        		
        		        		if(state != null){
        		        			entity.addState(state);
        		        		}
        		        	}
        				}
        			}
        		}
        		
        		// actions
        	}
		}
	}

	private IGameFormObject getForm(Element formElement) throws SpartanException {
		
		IGameFormObject form = null;
		
		String className = formElement.getAttribute(CLASS_ATTR);
		
		Class<?> formClass = null;
		
		NodeList attributeList = formElement.getElementsByTagName(ATTRIBUTE_TAG);
		
		HashMap<String, String> attributes = getAttributes( attributeList );
		
		try {
			formClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new SpartanException("Could not find required class: " + className, e);
		}
		
		try {
			Constructor<?> constructor = formClass.getConstructor(attributes.getClass());
			
			form = (IGameFormObject) constructor.newInstance(attributes);
		} catch (SecurityException e) {
			throw new SpartanException("Could not load required constructor", e);
		} catch (NoSuchMethodException e) {
			throw new SpartanException("Could not load required constructor", e);
		} catch (IllegalArgumentException e) {
			throw new SpartanException("Invalid constructor", e);
		} catch (InstantiationException e) {
			throw new SpartanException("Invalid constructor", e);
		} catch (IllegalAccessException e) {
			throw new SpartanException("Invalid constructor", e);
		} catch (InvocationTargetException e) {
			throw new SpartanException("Could not instanciate class" + className, e);
		}
		
		// atts
		
		VirtualGameSpace layer = RenderManager.getInstance().getVirtualSpace( formElement.getAttribute("layer") );
		
		if(layer != null){
			layer.addForm(form);
		}
		
		// X, Y, Center Position,
		int x = Integer.parseInt(formElement.getAttribute("x"));
		int y = Integer.parseInt(formElement.getAttribute("y"));
		int centerX = Integer.parseInt(formElement.getAttribute("centerX") == null ? "0" : formElement.getAttribute("centerX"));
		int centerY = Integer.parseInt(formElement.getAttribute("centerX") == null ? "0" : formElement.getAttribute("centerX"));
		
		form.getPosition().setPosition(x, y);
		form.getPosition().setCenterPosition(centerX, centerY);
		
		// states
		NodeList statesList = formElement.getElementsByTagName(STATE_LIST_TAG);
		
		if(statesList != null && statesList.getLength() == 1){
			
			NodeList stateList = formElement.getElementsByTagName(STATE_TAG);
			
			if(stateList != null){
				for(int stateIdx = 0; stateIdx < stateList.getLength(); stateIdx++){
		        	
		        	Node stateNode = stateList.item(stateIdx);
		        	
		        	if(stateNode.getNodeType() == Node.ELEMENT_NODE){
		        		Element stateElement = (Element)stateNode;
		        		
		        		IGameStateObject state = createState( stateElement );
		        		
		        		if(state != null){
		        			form.addState(state);
		        		}
		        	}
				}
			}
		}
		
		return form;
	}

	private IGameStateObject createState(Element stateElement) {
		// TODO Auto-generated method stub
		return null;
	}

	private HashMap<String, String> getAttributes(NodeList attributeList) {
		
		HashMap<String, String> attributes = null;
		
		if(attributeList != null || attributeList.getLength() > 0){
			attributes = new HashMap<String, String>();
			 for(int attributeIdx = 0; attributeIdx < attributeList.getLength(); attributeIdx++){
		        	
		        	Node attributeNode = attributeList.item(attributeIdx);
		        	
		        	if(attributeNode.getNodeType() == Node.ELEMENT_NODE){
		        		Element attributeElement = (Element)attributeNode;
		        		
		        		String name = attributeElement.getAttribute(NAME_ATTR);
		        		String value = attributeElement.getTextContent();
		        		
		        		attributes.put(name, value);
		        	}
			 }
		}
		
		return attributes;
	}

	private void processCollisionManagerData(NodeList collisionManagerData) {
		// TODO Auto-generated method stub
		
	}

	private void processRenderManagerData(Element renderManagerElement) throws SpartanException {
		
		RenderManager rm = RenderManager.getInstance();
		
		if(rm == null){
			throw new SpartanException("Current active rendermanager is invalid");
		}
		
		String debugStr = renderManagerElement.getAttribute("debug");
		
		if(debugStr != null){
			rm.setDebugInfo(Boolean.parseBoolean(debugStr));
		}
		
		// read all layers
		NodeList rmLayers = renderManagerElement.getElementsByTagName(RENDER_MANAGER_LAYER_TAG);
		
		if(rmLayers != null){
			for(int layerIdx = 0; layerIdx < rmLayers.getLength(); layerIdx++){
				Node layerNode = rmLayers.item(layerIdx);
				
				if(layerNode.getNodeType() == Node.ELEMENT_NODE){
	        		Element layerNodeElement = (Element)layerNode;
	        		
	        		int id = 0;
	        		try{
	        			id = Integer.parseInt( layerNodeElement.getAttribute("priority"));
	        		}catch(Exception e){
	        			throw new SpartanException("Invalid attribute value [id] for TAG [" + RENDER_MANAGER_LAYER_TAG + "]"); 
	        		}
	        		
	        		String layerName = null;
	        		
	        		try{
	        			layerName = layerNodeElement.getAttribute(NAME_ATTR);
	        		}catch(Exception e){
	        			throw new SpartanException("Invalid attribute value [name] for TAG [" + RENDER_MANAGER_LAYER_TAG + "]"); 
	        		}
	        		
	        		rm.addNewVirtualGameSpace(layerName, id);
				}
			}
		}
	}

	private String getLevelName(Element levelElement) throws SpartanException {
		NodeList nameList = levelElement.getElementsByTagName(NAME_TAG);
		
		if(nameList == null || nameList.getLength() == 0){
			throw new SpartanException("No level name found");
		}
		
		return nameList.item(0).getTextContent();
	}
}
