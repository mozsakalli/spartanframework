package com.foxhole.spartan.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.form.IGameFormObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.PositionalGameState;

public class RenderManager implements ISpartanManager {
	//private static RenderManager _instance = new RenderManager();
	/*
	public static RenderManager getInstance(){
		return _instance;
	}
	*/
	Map<String, VirtualGameSpace> virtualSpaces;
	List<VirtualGameSpace> renderOrder;
	
	public RenderManager(){
		virtualSpaces = new HashMap<String, VirtualGameSpace>();
		renderOrder = new ArrayList<VirtualGameSpace>();
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
		
		for(VirtualGameSpace space : renderOrder){
			List<IGameFormObject> forms = space.getForms();
			
			for(IGameFormObject form : forms){
				PositionalGameState posState = (PositionalGameState)form.getState(PositionalGameState.class.getCanonicalName());
				
				boolean pushMatrix = form.isMatrixPush();
				if(pushMatrix){
					GL11.glPushMatrix();
				
					if(posState != null){
						GL11.glTranslatef(posState.getX()-posState.getCenterposX(), posState.getY()-posState.getCenterposY(),0);
						GL11.glRotatef(posState.getRotation(), 0, 0, 1);
						GL11.glScalef(posState.getScale(), posState.getScale(), 1);
						
					}
				}
				
				form.render(graphics);
				
				if(pushMatrix){
					GL11.glPopMatrix();
				}
			}
		}
	}
}
