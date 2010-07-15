package com.foxhole.spartan.managers;

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

import com.foxhole.spartan.forms.IGameFormObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.PositionalGameState;

public class RenderManager implements ISpartanManager {
	
	public float translationX;
	public float translationY;
	
	boolean inDebugMode;
	
	Map<String, VirtualGameSpace> virtualSpaces;
	List<VirtualGameSpace> renderOrder;
	
	public RenderManager(){
		virtualSpaces = new HashMap<String, VirtualGameSpace>();
		renderOrder = new ArrayList<VirtualGameSpace>();
		
		inDebugMode = false;
	}
	
	public void reset(){
		virtualSpaces.clear();
		renderOrder.clear();
	}
	
	public VirtualGameSpace addNewVirtualGameSpace(String name){
		return addNewVirtualGameSpace(name, renderOrder.size());
	}
	
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
	
	public void removeVirtualGameSpace(String name){
		VirtualGameSpace space = virtualSpaces.get(name);
		
		if(space != null){
			renderOrder.remove(space);
			virtualSpaces.remove(space);
		}
	}
	
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
		
	}

	public void removeForm(IGameFormObject form) {
		for(VirtualGameSpace space : renderOrder){
			space.removeForm(form);
		}
	}
	
	public void translate(float translationX, float translationY){
		this.translationX = translationX;
		this.translationY = translationY;
	}
}
