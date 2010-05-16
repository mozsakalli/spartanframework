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

package com.foxhole.spartan.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.actions.IGameActionObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.form.IGameFormObject;
import com.foxhole.spartan.managers.ActionManager;
import com.foxhole.spartan.managers.IdentifierManager;
import com.foxhole.spartan.spaces.LogicalGameSpace;
import com.foxhole.spartan.states.IGameStateObject;
import com.foxhole.spartan.states.PropertiesGameState;


public class GameEntity implements IGameEntityObject {

	private int id;
	// Parent Entity
	IGameEntityObject parent;
	// children entities
	List<IGameEntityObject> children;
	// states
	Map<String, IGameStateObject> stateMap;
	// logical entity space
	LogicalGameSpace space;
	// currently active Action list
	Map<String, IGameActionObject> actionMap;
	// form
	IGameFormObject form;
	// Action prequeued
	private Map<String, String> registeredActions;


	public GameEntity(String name ){
		id = IdentifierManager.getInstance().generateId(GameEntity.class.getCanonicalName());
		
		stateMap = new HashMap<String, IGameStateObject>();
		
		actionMap = new HashMap<String, IGameActionObject>();
		
		children = new LinkedList<IGameEntityObject>();
		
		space = new LogicalGameSpace();
		
		registeredActions = new HashMap<String, String>();
		
		PropertiesGameState state = new PropertiesGameState(PropertiesGameState.class.getCanonicalName());
		state.addString("name", name);
		
		this.addState(state);
	}
	
	//@Override
	public IGameEntityObject createEntity( String name ) {
		IGameEntityObject child = new GameEntity(name);
		
		children.add(child);
		
		child.setParent(this);
		
		return child;
	}

	//@Override
	public Collection<IGameActionObject> getActiveActions() {
		return actionMap.values();
	}

	//@Override
	public List<IGameEntityObject> getChildren() {
		return children;
	}
	
	//@Override
	public IGameFormObject getForm() {
		return form;
	}
	
	//@Override
	public void setForm(IGameFormObject newForm) {
		this.form = newForm;
		newForm.setEntity(this);
	}

	//@Override
	public int getId() {
		return id;
	}

	//@Override
	public IGameEntityObject getParent() {
		return parent;
	}
	
	//@Override
	public void setParent(IGameEntityObject parent) {
		this.parent = parent;
	}

	//@Override
	public LogicalGameSpace getSpace() {
		return space;
	}

	//@Override
	public Map<String, IGameStateObject> getStates() {
		return stateMap;
	}

	//@Override
	public void removeEntity(IGameEntityObject entity) {
		if ( children.contains(entity) )
			children.remove(entity);
	}
	
	//@Override
	public void addState(IGameStateObject newState) {
		if(!stateMap.containsKey(newState.getId()))
			stateMap.put(newState.getId(), newState);
	}

	//@Override
	public void removeState(IGameStateObject state) {
		if(stateMap.containsKey(state.getId()))
			stateMap.remove(state.getId());
	}

	//@Override
	public final void addAction(IGameActionObject action) {
		addAction(action.getActionName(), action);
	}

	//@Override
	public final void removeAction(String actionName) {
		if(actionName != null && actionMap.containsKey(actionName))
			actionMap.remove(actionName);
	}

	//@Override
	public final IGameActionObject getAction(String actionName) {
		if(actionName == null)
			return null;
		
		return actionMap.get(actionName);
	}

	//@Override
	public final void addAction(String actionName, IGameActionObject action) {
		if(action != null && actionName != null)
			actionMap.put(actionName, action);
	}
}
