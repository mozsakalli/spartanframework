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

package com.foxhole.tools.spartan.managers.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.action.ActionManager;
import com.foxhole.tools.spartan.managers.collision.CollisionManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;
import com.foxhole.tools.spartan.managers.input.InputManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;

/**
 * Entity manager is the manager responsible for gathering all the entities in the system.
 * It shall act as a unique repository of entities that can be queried at any time to find any
 * entity.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class EntityManager implements ISpartanManager {
	
	/**
	 * Obtains the current active instance of the entity manager
	 * 
	 * @return a handle the current active instance of the entity manager, null if no active instance is selected
	 */
	public static EntityManager getInstance(){
		
		EntityManager _theInstance = null;
		
		int currentId = GameStateManager.getInstance().getCurrentState();
		
		if( GameStateManager.getInstance().getStateManagers(currentId) != null){
			_theInstance = GameStateManager.getInstance().getStateManagers(currentId).getEntityManager();
		}
		
		return _theInstance;
	}
		
	Map<String, IGameEntityObject> entities;
	
	/**
	 * Constructs the entity manager 
	 */
	public EntityManager(){
		entities = new HashMap<String, IGameEntityObject>();
	}
	
	/**
	 * Adds an entity to the entity manager
	 * 
	 * @param id the Identifier of the entity
	 * @param entity a handle for the entity 
	 * @throws SpartanException if the id is already taken
	 */
	public void addEntity( String id, IGameEntityObject entity ) throws SpartanException{
		
		if(entities.containsKey(id)){
			throw new SpartanException("Entity already created " + id +", no duplicate entities can be created.");
		}
			
		entities.put(id, entity);
	}
	
	/**
	 * Obtains the entity
	 * 
	 * @param id a string identifier representing the entity
	 * @return a handle to the entity represented by id or null if none is found
	 */
	public IGameEntityObject getEntity(String id){
		return entities.get(id);
	}
	
	/**
	 * Removes an entity from the entity manager 
	 * 
	 * @param id a string identifier representing the entity
	 */
	public void removeEntity(String id){
		IGameEntityObject entity = entities.remove(id);
		
		if(entity != null){
			

			if(entity.getForm() != null){
				// Form
				RenderManager.getInstance().removeForm(entity.getForm());

				// Collision handling
				CollisionManager.getInstance().unregisterForm(entity.getForm());
			}
			
			// Actions
			Collection<IGameActionObject> actions = entity.getActiveActions();
			
			for(IGameActionObject action : actions){
				ActionManager.getInstance().unregisterAction(action);
				action.stop();
				
				// TODO: inputhandling?
			}
		}
		
		
	}

	/**
	 * Removes all entities from the manager
	 */
	public void reset() {
		entities.clear();
	}
}
