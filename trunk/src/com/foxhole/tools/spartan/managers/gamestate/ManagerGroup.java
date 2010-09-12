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

package com.foxhole.tools.spartan.managers.gamestate;

import com.foxhole.tools.spartan.managers.action.ActionManager;
import com.foxhole.tools.spartan.managers.collision.CollisionManager;
import com.foxhole.tools.spartan.managers.entity.EntityManager;
import com.foxhole.tools.spartan.managers.input.InputManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;

/**
 * A helper class for the State Manager.
 * This class shall be used for grouping all the managers into a simple structure for better
 * organization when requesting/organizing data from the state manager.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class ManagerGroup{
	private ActionManager actionManager;
	private EntityManager entityManager;
	private RenderManager renderManager;
	private CollisionManager collisionManager;
	private InputManager inputManager;
	
	/**
	 * Consructs the manager group
	 */
	public ManagerGroup(){
		actionManager = new ActionManager();
		
		entityManager = new EntityManager();
		
		renderManager = new RenderManager();
		
		collisionManager = new CollisionManager();
		
		inputManager = new InputManager();
	}

	/**
	 * Obtains the action manager
	 * 
	 * @return a handle for the action manager defined inside, null if not defined
	 */
	public final ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * Obtains the entity manager
	 * 
	 * @return a handle for the entity manager defined inside, null if not defined
	 */
	public final EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Obtains the render manager
	 * 
	 * @return a handle for the render manager defined inside, null if not defined
	 */
	public final RenderManager getRenderManager() {
		return renderManager;
	}

	/**
	 * Obtains the collision manager
	 * 
	 * @return a handle for the collision manager defined inside, null if not defined
	 */
	public final CollisionManager getCollisionManager() {
		return collisionManager;
	}

	/**
	 * Obtains the input manager
	 * 
	 * @return a handle to the input manager defined inside, null if not defined
	 */
	public final InputManager getInputManager() {
		return inputManager;
	}
}

