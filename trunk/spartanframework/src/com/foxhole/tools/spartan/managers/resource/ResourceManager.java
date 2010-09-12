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

package com.foxhole.tools.spartan.managers.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.loading.LoadingList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.states.PropertiesGameState;

/**
 * The resource manager enables a simple way to provide resource handles for any part of the game that 
 * requests it.
 * Every resource shall be known by an unique identifier that shall be used to obtain the resource.
 * The resource manager can load resources from XML files or they can be added to the resource loader via code.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class ResourceManager implements ISpartanManager {

	private static String SPRITE_SHEET_REF = "__SPRITE_SHEET_";
	
	private static ResourceManager _instance = new ResourceManager();
	
	private Map<String, Sound> soundMap;
	private Map<String, Image> imageMap;
	private Map<String, ResourceAnimationData> animationMap;
	private Map<String, String> textMap;
	
	private PropertiesGameState deferredState;
	
	private ResourceManager(){
		soundMap 	 = new HashMap<String, Sound>();
		imageMap 	 = new HashMap<String, Image>();
		animationMap = new HashMap<String, ResourceAnimationData>();
		textMap 	 = new HashMap<String, String>();
	}
	
	/**
	 * Obtains the current active instance of the resource manager
	 * 
	 * @return a handle the current active instance of the resource manager, null if no active instance is selected
	 */
	public final static ResourceManager getInstance(){
		return _instance;
	}
	
	/**
	 * Loads a list of resources from an inputstream given that the inputstream contains a well formed 
	 * XML
	 * 
	 * @param is the inputstream containing the well formed XML
	 * @throws SpartanException when it is not possible to load resources or when the XML is mal formed
	 */
	public void loadResources(InputStream is) throws SpartanException {
		loadResources(is, false);
	}
	
	/**
	 * Loads a list of resources from an inputstream given that the inputstream contains a well formed 
	 * XML
	 * 
	 * @param is the inputstream containing the well formed XML
	 * @param deferred true if it is to use the deferred loading, false otherwise
	 * @throws SpartanException when it is not possible to load resources or when the XML is mal formed
	 */
	public void loadResources(InputStream is, boolean deferred) throws SpartanException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new SpartanException("Could not load resources", e);
		}
		Document doc = null;
        try {
			doc = docBuilder.parse (is);
		} catch (SAXException e) {
			throw new SpartanException("Could not load resources", e);
		} catch (IOException e) {
			throw new SpartanException("Could not load resources", e);
		}
		
		// normalize text representation
        doc.getDocumentElement ().normalize ();
        
        NodeList listResources = doc.getElementsByTagName("resource");
        
        int totalResources = listResources.getLength();
        
        if(deferred){
        	LoadingList.setDeferredLoading(true);
        }
        	
        for(int resourceIdx = 0; resourceIdx < totalResources; resourceIdx++){
        	
        	Node resourceNode = listResources.item(resourceIdx);
        	
        	if(resourceNode.getNodeType() == Node.ELEMENT_NODE){
        		Element resourceElement = (Element)resourceNode;
        		
        		String type = resourceElement.getAttribute("type");
        		
        		if(type.equals("image")){
        			addElementAsImage(resourceElement);
        		}else if(type.equals("sound")){
        			addElementAsSound(resourceElement);
        		}else if(type.equals("text")){
        			addElementAsText(resourceElement);
        		}else if(type.equals("font")){
        			
        		}else if(type.equals("animation")){
        			addElementAsAnimation(resourceElement);
        		}
        	}
        }

	}
	
	private void addElementAsAnimation(Element resourceElement) throws SpartanException{
		loadAnimation(resourceElement.getAttribute("id"), resourceElement.getTextContent(), 
				Integer.valueOf(resourceElement.getAttribute("tw")),
				Integer.valueOf(resourceElement.getAttribute("th")),
				Integer.valueOf(resourceElement.getAttribute("duration")));
	}

	private void loadAnimation(String id, String spriteSheetPath,
			int tw, int th, int duration) throws SpartanException{
		if(spriteSheetPath == null || spriteSheetPath.length() == 0)
			throw new SpartanException("Image resource [" + id + "] has invalid path");
		
		loadImage( SPRITE_SHEET_REF + id, spriteSheetPath);
		
		animationMap.put(id, new ResourceAnimationData(SPRITE_SHEET_REF+id, tw, th, duration));
	}
	
	/**
	 * Obtains the resource, in this case an animation, associated with the ID
	 * 
	 * @param ID the identifier of the resource
	 * @return a handle to a initialized animation, null, it none if found
	 */
	public final Animation getAnimation(String ID){
		Animation animation = null;
		
		ResourceAnimationData rad = animationMap.get(ID);
		
		if(rad != null){
			SpriteSheet spr = new SpriteSheet(getImage(rad.getImageId()), rad.tw, rad.th);
			
			animation = new Animation(spr, rad.duration);
		}
		return animation;
	}

	private void addElementAsText(Element resourceElement) throws SpartanException{
		loadText(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}

	/**
	 * Load a string of text into the resource manager identifying it with the id
	 * 
	 * @param id the identifier
	 * @param value the value of the text
	 * @return returns the text loaded
	 * @throws SpartanException if the value is invalid of if the resource id already exists
	 */
	public String loadText(String id, String value) throws SpartanException{
		if(value == null)
			throw new SpartanException("Text resource [" + id + "] has invalid value");
		
		if(textMap.containsKey(id)){
			throw new SpartanException("Id already inserted");
		}
		
		textMap.put(id, value);
		
		return value;
	}
	
	/**
	 * Obtains the resource, in this case a text string, associated with the ID
	 * 
	 * @param ID the identifier of the resource
	 * @return a handle to a initialized text string, null, it none if found
	 */
	public String getText(String ID) {
		return textMap.get(ID);
	}
	
	private void addElementAsSound(Element resourceElement) throws SpartanException {
		loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}

	/**
	 * Load a sound into the resource manager identifying it with the id
	 * 
	 * @param id the identifier
	 * @param path the path to the sound resource
	 * @return returns the sound resource initialized
	 * @throws SpartanException if the value is invalid of if the resource id already exists
	 */
	public Sound loadSound(String id, String path) throws SpartanException{
		if(path == null || path.length() == 0)
			throw new SpartanException("Sound resource [" + id + "] has invalid path");
		
		Sound sound = null;
		
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			throw new SpartanException("Could not load sound", e);
		}
		
		if(soundMap.containsKey(id)){
			throw new SpartanException("Id already exists");
		}
		
		this.soundMap.put(id, sound);
		
		return sound;
	}
	
	/**
	 * Obtains the resource, in this case a sound resource, associated with the ID
	 * 
	 * @param ID the identifier of the resource
	 * @return a handle to a initialized sound resource, null, it none if found
	 */
	public final Sound getSound(String ID){
		return soundMap.get(ID);
	}
	

	private final void addElementAsImage(Element resourceElement) throws SpartanException {
		loadImage(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
	
	/**
	 * Load an image into the resource manager identifying it with the id
	 * 
	 * @param id the identifier
	 * @param path the path to the image resource
	 * @return returns the image resource initialized
	 * @throws SpartanException if the value is invalid of if the resource id already exists
	 */
	public Image loadImage(String id, String path) throws SpartanException{
		if(path == null || path.length() == 0)
			throw new SpartanException("Image resource [" + id + "] has invalid path");
		
		Image image = null;
		try{
			image = new Image(path);
		} catch (SlickException e) {
			throw new SpartanException("Could not load image", e);
		}
		
		if ( imageMap.containsKey(id) ){
			throw new SpartanException("Id already exists");
		}
		
		this.imageMap.put(id, image);
		
		return image;
	}

	/**
	 * Obtains the resource, in this case a image resource, associated with the ID
	 * 
	 * @param ID the identifier of the resource
	 * @return a handle to a initialized image resource, null, it none if found
	 */
	public final Image getImage(String ID){
		return imageMap.get(ID);
	}
	
	

	
	


	
	
	private class ResourceAnimationData{
		int duration;
		int tw;
		int th;
		String imageId;
		
		public ResourceAnimationData(String id, int tw, int th, int duration){
			this.imageId = id;
			this.tw = tw;
			this.th = th;
			this.duration = duration;
		}
		
		public final int getDuration() {
			return duration;
		}
		public final void setDuration(int duration) {
			this.duration = duration;
		}
		public final int getTw() {
			return tw;
		}
		public final void setTw(int tw) {
			this.tw = tw;
		}
		public final int getTh() {
			return th;
		}
		public final void setTh(int th) {
			this.th = th;
		}
		public final String getImageId() {
			return imageId;
		}
		public final void setImageId(String imageId) {
			this.imageId = imageId;
		}
		
	}
}
