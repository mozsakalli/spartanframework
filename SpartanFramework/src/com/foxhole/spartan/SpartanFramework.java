package com.foxhole.spartan;

import com.foxhole.spartan.managers.ActionManager;
import com.foxhole.spartan.managers.RenderManager;
import com.foxhole.spartan.managers.collision.CollisionManager;

public class SpartanFramework {

	private static RenderManager activeRenderManager;
	private static ActionManager activeActionManager;
	private static CollisionManager activeCollisionManager;
	
	public static final void setActiveRenderManager(RenderManager renderManager){
		activeRenderManager = renderManager;
	}
	
	public static final void setActiveActionManager(ActionManager actionManager){
		activeActionManager = actionManager;
	}
	
	public static final void setActiveCollisionManager(CollisionManager collisionManager){
		activeCollisionManager = collisionManager;
	}
	
	public static final RenderManager getActiveRenderManager(){
		return activeRenderManager;
	}
	
	public static final ActionManager getActiveActionManager(){
		return activeActionManager;
	}
	
	public static final CollisionManager getActiveCollisionManager(){
		return activeCollisionManager;
	}
}
