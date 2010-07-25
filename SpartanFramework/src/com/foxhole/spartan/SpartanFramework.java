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

package com.foxhole.spartan;

import com.foxhole.spartan.managers.ActionManager;
import com.foxhole.spartan.managers.RenderManager;
import com.foxhole.spartan.managers.collision.CollisionManager;

/**
 * @author Tiago "Spiegel" Costa
 * 
 * Spartan framework is a simple way of addressing the active Managers (when applicable).
 * SpartanGameState fills this class, otherwise it must be filled by hand.
 * Several actions use these managers as the current active managers.
 */
public class SpartanFramework {

	private static RenderManager activeRenderManager;
	private static ActionManager activeActionManager;
	private static CollisionManager activeCollisionManager;

	
	/**
	 * Sets the active render manager.
	 * @param renderManager the manager to be set as the active one
	 */
	public static final void setActiveRenderManager(RenderManager renderManager){
		activeRenderManager = renderManager;
	}
	
	/**
	 * Sets the active action manager.
	 * @param actionManager the manager to be set as the active one
	 */
	public static final void setActiveActionManager(ActionManager actionManager){
		activeActionManager = actionManager;
	}
	
	/**
	 * Sets the active collision manager.
	 * @param collisionManager the manager to be set as the active one
	 */
	public static final void setActiveCollisionManager(CollisionManager collisionManager){
		activeCollisionManager = collisionManager;
	}
	
	/**
	 * Obtains the current active render manager.
	 * @return A handle to a render manager
	 */
	public static final RenderManager getActiveRenderManager(){
		return activeRenderManager;
	}
	
	/**
	 * Obtains the current active action manager.
	 * @return A handle to a action manager
	 */
	public static final ActionManager getActiveActionManager(){
		return activeActionManager;
	}
	
	/**
	 * Obtains the current active collision manager.
	 * @return A handle to a collision manager
	 */
	public static final CollisionManager getActiveCollisionManager(){
		return activeCollisionManager;
	}
}
