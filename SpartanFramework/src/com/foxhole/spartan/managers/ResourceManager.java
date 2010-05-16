package com.foxhole.spartan.managers;

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
import org.newdawn.slick.loading.LoadingList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.foxhole.spartan.actions.IGameActionObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PropertiesGameState;

public class ResourceManager implements ISpartanManager {

	private static ResourceManager _instance = new ResourceManager();
	
	private Map<String, Sound> soundMap;
	private Map<String, Image> imageMap;
	private Map<String, Animation> animationMap;
	private Map<String, String> textMap;
	
	private PropertiesGameState deferredState;
	
	private ResourceManager(){
		soundMap 	 = new HashMap<String, Sound>();
		imageMap 	 = new HashMap<String, Image>();
		animationMap = new HashMap<String, Animation>();
		textMap 	 = new HashMap<String, String>();
	}
	
	public final static ResourceManager getInstance(){
		return _instance;
	}
	
	public void loadResources(InputStream is) throws SpartanException {
		loadResources(is, false);
	}
	
	public void loadResources(InputStream is, boolean deferred) throws SpartanException {
		
		IGameActionObject result = null;
		
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
        	
        	//result = new ResourceLoadingGameAction("RESOURCE_LOADER", null);
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
        			
        		}
        	}
        }

	}
	
	private void addElementAsText(Element resourceElement) throws SpartanException{
		loadText(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}

	public String loadText(String id, String value) throws SpartanException{
		if(value == null)
			throw new SpartanException("Text resource [" + id + "] has invalid value");
		
		textMap.put(id, value);
		
		return value;
	}
	
	private void addElementAsSound(Element resourceElement) throws SpartanException {
		loadSound(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}

	public Sound loadSound(String id, String path) throws SpartanException{
		if(path == null || path.length() == 0)
			throw new SpartanException("Sound resource [" + id + "] has invalid path");
		
		Sound sound = null;
		
		try {
			sound = new Sound(path);
		} catch (SlickException e) {
			throw new SpartanException("Could not load sound", e);
		}
		
		this.soundMap.put(id, sound);
		
		return sound;
	}

	private final void addElementAsImage(Element resourceElement) throws SpartanException {
		loadImage(resourceElement.getAttribute("id"), resourceElement.getTextContent());
	}
	
	public Image loadImage(String id, String path) throws SpartanException{
		if(path == null || path.length() == 0)
			throw new SpartanException("Image resource [" + id + "] has invalid path");
		
		Image image = null;
		try{
			image = new Image(path);
		} catch (SlickException e) {
			throw new SpartanException("Could not load image", e);
		}
		
		this.imageMap.put(id, image);
		
		return image;
	}

	public final Sound getSound(String ID){
		return soundMap.get(ID);
	}
	
	public final Image getImage(String ID){
		return imageMap.get(ID);
	}
	
	public final Animation getAnimation(String ID){
		return animationMap.get(ID);
	}

	public String getText(String ID) {
		return textMap.get(ID);
	}
	
}
