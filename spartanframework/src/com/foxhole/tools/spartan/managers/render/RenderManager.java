package com.foxhole.tools.spartan.managers.render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.forms.IGameFormObject;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.PositionalGameState;

/**
 * The render manager is responsible for rendering all the registered forms into the screen
 * The user registers the forms into "layers" available in the render manager (also created by the player)
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class RenderManager implements ISpartanManager {
	
	/**
	 * Obtains the current active instance of the render manager
	 * 
	 * @return a handle the current active instance of the render manager, null if no active instance is selected
	 */
	public static RenderManager getInstance(){
		
		RenderManager _theInstance = null;
		
		int currentId = GameStateManager.getInstance().getCurrentState();
		
		if( GameStateManager.getInstance().getStateManagers(currentId) != null){
			_theInstance = GameStateManager.getInstance().getStateManagers(currentId).getRenderManager();
		}
		
		return _theInstance;
	}
	
	/**
	 * The X coordinate translation for the render manager
	 */
	public float translationX;
	/**
	 * The Y coordinate translation for the render manager
	 */
	public float translationY;
	
	private boolean inDebugMode;
	
	Map<String, VirtualGameSpace> virtualSpaces;
	List<VirtualGameSpace> renderOrder;
	
	/**
	 * Constructs the render manager
	 */
	public RenderManager(){
		virtualSpaces = new HashMap<String, VirtualGameSpace>();
		renderOrder = new ArrayList<VirtualGameSpace>();
		
		inDebugMode = false;
	}
	
	/**
	 * Erases all forms from this manager
	 */
	public void reset(){
		virtualSpaces.clear();
		renderOrder.clear();
	}
	
	/**
	 * Creates a new "layer" to add forms, this layer is added on top of all layers 
	 * and is rendered in the last.
	 * 
	 * @param name the name and identifier of the layer
	 * @return A virtualGameSpace handle that represents the new layer
	 */
	public VirtualGameSpace addNewVirtualGameSpace(String name){
		return addNewVirtualGameSpace(name, renderOrder.size());
	}
	
	/**
	 * Creates a new "layer" to add forms, this layer is added by priority within all the remaining
	 * layers and is rendered by that order.
	 * @param name the name and identifier of the layer
	 * @param priority the priority of the layer, the bigger the number the more in front the layer is
	 * @return A virtualGameSpace handle that represents the new layer
	 */
	public VirtualGameSpace addNewVirtualGameSpace(String name, int priority){
		if(priority < 0)
			priority = 0;
		else if(priority > renderOrder.size())
			priority = renderOrder.size();
		
		VirtualGameSpace space = new VirtualGameSpace(name);
		
		virtualSpaces.put(name, space);
		
		renderOrder.add(priority, space);
		
		return space;
	}
	
	/**
	 * Removes a layer from the render manager, eliminating all forms contained inside
	 * 
	 * @param name the identifier of the layer
	 */
	public void removeVirtualGameSpace(String name){
		VirtualGameSpace space = virtualSpaces.get(name);
		
		if(space != null){
			renderOrder.remove(space);
			virtualSpaces.remove(space);
		}
	}
	
	/**
	 * Sets the debug info for the render manager
	 * 
	 * When turned on, it will show the FPS, number of entities and number of forms rendered, as well
	 * as the collision boxes for all the forms that have them.
	 * 
	 * @param debugInfo a boolean representing the value chosen
	 */
	public void setDebugInfo(boolean debugInfo){
		inDebugMode = debugInfo;
	}
	
	/**
	 * Performs the rendering of all layers and forms
	 * 
	 * @param gc a handle for the active slick game container
	 * @param sbg a handle for the active state base game (null if no slick2d states are used)
	 * @param graphics a handle to the slick2d graphics
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics){
		GL11.glLoadIdentity();
		
		GL11.glTranslatef(translationX, translationY, 0);
		
		for(VirtualGameSpace space : renderOrder){
			List<IGameFormObject> forms = space.getForms();
			
			for(IGameFormObject form : forms){
				
				if(form.useMatrixPush()){
					GL11.glPushMatrix();
				
					if( form.useIdentity())
						GL11.glLoadIdentity();
					
					PositionalGameState posState = (PositionalGameState)form.getState(PositionalGameState.class.getCanonicalName());
					
					if(posState != null){
						GL11.glTranslatef(posState.getX(), posState.getY(),0);
						GL11.glRotatef(posState.getRotation(), 0, 0, 1);
						GL11.glTranslatef(-posState.getCenterposX(), -posState.getCenterposY(),0);
						GL11.glScalef(posState.getScale(), posState.getScale(), 1);
					}
				}
				
				form.render(graphics);
				
				if(form.useMatrixPush())
					GL11.glPopMatrix();
			}
		}
		
		if(inDebugMode){
			
			drawDebugInfo(gc, sbg, graphics);
			
			for(VirtualGameSpace space : renderOrder){
				List<IGameFormObject> forms = space.getForms();
				
				for(IGameFormObject form : forms){
					Shape s = form.getPosition().getCollisionShape();
					if(s != null){
						if(form.useMatrixPush()){
							GL11.glPushMatrix();
						
							if( form.useIdentity())
								GL11.glLoadIdentity();
						}
						
						graphics.setColor(Color.green);
						graphics.draw(s);
						
						if(form.useMatrixPush())
							GL11.glPopMatrix();
					}
				}
			}
		}
	}

	private void drawDebugInfo(GameContainer gc, StateBasedGame sbg,
			Graphics graphics) {
		
		graphics.drawString("Spartan FPS:" + gc.getFPS(), -translationX + 10, -translationY + 10);
		
		int count = 0;
		
		for(VirtualGameSpace space : renderOrder){
			count += space.getForms().size();
		}
		
		graphics.drawString("Forms: " + count , - translationX + 10, -translationY + 30);
		
		graphics.drawString("Layer Count : " + renderOrder.size(), - translationX + 10, -translationY + 50);
		
	}

	/**
	 * Removes a form from the render manager
	 * 
	 * @param form a handle to he form to remove
	 */
	public void removeForm(IGameFormObject form) {
		for(VirtualGameSpace space : renderOrder){
			space.removeForm(form);
		}
	}
	
	/**
	 * Translate the viewport by x,y addinf to the current viewpoint position 
	 * 
	 * @param translationX the amount of coordinate X to add
	 * @param translationY the amount of coordinate Y to add
	 */
	public void translate(float translationX, float translationY){
		this.translationX = translationX;
		this.translationY = translationY;
	}

	/**
	 * Obtains a layer from the render manager 
	 * 
	 * @param layerName the identifier of the layer
	 * @return a handle to the virtual game space representing the layer, null if none is found
	 */
	public VirtualGameSpace getVirtualSpace(String layerName) {
		return virtualSpaces.get(layerName);
	}
}
