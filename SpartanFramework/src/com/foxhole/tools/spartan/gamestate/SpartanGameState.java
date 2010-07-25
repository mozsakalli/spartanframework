package com.foxhole.tools.spartan.gamestate;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.SpartanFramework;
import com.foxhole.tools.spartan.managers.ActionManager;
import com.foxhole.tools.spartan.managers.RenderManager;
import com.foxhole.tools.spartan.managers.ResourceManager;
import com.foxhole.tools.spartan.managers.collision.CollisionManager;
import com.foxhole.tools.spartan.states.AbstractGameState;

public abstract class SpartanGameState extends org.newdawn.slick.state.BasicGameState {

	protected ResourceManager resourceManager;
	protected ActionManager actionManager;
	protected RenderManager renderManager;
	protected CollisionManager collisionManager;
	
	public SpartanGameState(){
		actionManager = new ActionManager();
		renderManager = new RenderManager();
		resourceManager = ResourceManager.getInstance();
		collisionManager = new CollisionManager();
		
		SpartanFramework.setActiveActionManager(actionManager);
		SpartanFramework.setActiveRenderManager(renderManager);
		SpartanFramework.setActiveCollisionManager(collisionManager);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)	throws SlickException {
		 // update all actions of the spartan framework
		actionManager.updateActions(gc, sbg, delta);
		// execute all collisions
		collisionManager.execute();
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		renderManager.render(gc, sbg, graphics);
	}
}
