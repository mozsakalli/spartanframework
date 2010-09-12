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

package com.foxhole.tools.spartan.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.foxhole.tools.spartan.INonRenderableSpartanObject;
import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.IGameFormObject;
import com.foxhole.tools.spartan.spaces.LogicalGameSpace;
import com.foxhole.tools.spartan.states.IGameStateObject;


/**
 * IGameEntityObject, represents a game token.
 * 
 * @author Tiago "Spiegel" Costa
 * 
 */
public interface IGameEntityObject extends INonRenderableSpartanObject {

	// Entity methods
	/**
	 * Obtains the id of the entity
	 * @return the Id for this entity
	 */
	public int getId();
	
	/**
	 * Obtains a child entity for this entity.
	 * @param name The name of the child to create
	 * @return a child entity
	 * @throws SpartanException
	 */
	public IGameEntityObject createEntity( String name ) throws SpartanException;
	
	/**
	 * Removes a child entity.
	 * 
	 * @param entity the entity to remove
	 */
	public void removeEntity( IGameEntityObject entity );
	
	/**
	 * Obtains all the direct children entities of this entity. 
	 * 
	 * @return A list of entities representing the direct children
	 */
	public List<IGameEntityObject> getChildren();
	
	/**
	 * Obtains the parent of this entity.
	 * 
	 * @return An handle for the parent of this entity
	 */
	public IGameEntityObject getParent();
	
	/**
	 * Sets the new parent of this entity, all references of the old parent are destroyed.
	 * 
	 * @param parent A handle for the new parent
	 */
	public void setParent(IGameEntityObject parent);
	
	// Action	
	/**
	 * Obtains all the active actions that were launched by this entity
	 * 
	 * @return A collection of actions.
	 */
	public Collection<IGameActionObject> getActiveActions();
	
	/**
	 * Adds an action to the list of this entity action
	 * 
	 * @param actionName The name that will represent the action
	 * @param action The handle for the action
	 */
	public void addAction(String actionName, IGameActionObject action);
	
	/**
	 * Adds an action to the list of this entity action.
	 * Since no name is give, the name of the action itself is taken into account.
	 * 
	 * @param action The handle for the action
	 */
	public void addAction(IGameActionObject action);
	
	/**
	 * Removes an action from the list of entity actions.
	 * 
	 * @param actionName The action name
	 */
	public void removeAction(String actionName);
	
	/**
	 * Obtains a action from the list of actions for this entity.
	 * 
	 * @param actionName The name of the action
	 * @return The handle for the action, null if none is found.
	 */
	public IGameActionObject getAction(String actionName);
	
	// Form
	/**
	 * Obtains the form of the entity.
	 * 
	 * @return A handle for the form, null if no form is found.
	 */
	public IGameFormObject getForm();
	
	/**
	 * Sets the form of the action.
	 * 
	 * @param newForm The new form to serve as visual representation of the entity
	 */
	public void setForm(IGameFormObject newForm);
	
	// State
	/**
	 * Obtains all states associated with the entity
	 * 
	 * @return a map of states
	 */
	public Map<String, IGameStateObject> getStates() ;
	
	/**
	 * Obtains a specific state of this entity.
	 * 
	 * @param gameStateName The name of the state to obtain.
	 * @return An handle for the state if found, null if nothing is found.
	 */
	public IGameStateObject getState(String gameStateName) ;
	
	/**
	 * Adds a new state to the entity.
	 * 
	 * @param newState The handle for the new state.
	 */
	public void addState(IGameStateObject newState);
	
	/**
	 * Removes a state from the entity. If no state matches, nothing is done.
	 * 
	 * @param state the state handle.
	 */
	public void removeState(IGameStateObject state);
	
	// Space
	/**
	 * Obtains the logical space of the entity.
	 * 
	 * @return A handle for the logical space of this entity
	 */
	public LogicalGameSpace getSpace();
}
