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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.IGameFormObject;
import com.foxhole.tools.spartan.managers.IdentifierManager;
import com.foxhole.tools.spartan.managers.entity.EntityManager;
import com.foxhole.tools.spartan.spaces.LogicalGameSpace;
import com.foxhole.tools.spartan.states.IGameStateObject;
import com.foxhole.tools.spartan.states.PropertiesGameState;


/**
 * The game entity object, this represents the entity in the spartan framework and it is the actual 
 * implementation of the concept. Every entity must be of this type.
 * 
 * The game entity is only a concept, since it is used to unite several other concepts, 
 * such a list of states that give meaning to the entity, a list of actions performed over the
 * information in those states and a form to visually represent the concept of the entity.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class GameEntity implements IGameEntityObject {

	private int id;
	// Parent Entity
	private IGameEntityObject parent;
	// children entities
	private List<IGameEntityObject> children;
	// states
	private Map<String, IGameStateObject> stateMap;
	// logical entity space
	private LogicalGameSpace space;
	// currently active Action list
	private Map<String, IGameActionObject> actionMap;
	// form
	private IGameFormObject form;
	// Action prequeued
	//private Map<String, String> registeredActions;


	/**
	 * Constructs a GameEntity.
	 * 
	 * @param name The name of the entity, it must be unique per gamestate
	 * @throws SpartanException When an error occurs
	 */
	public GameEntity(String name ) throws SpartanException{
		id = IdentifierManager.getInstance().generateId(GameEntity.class.getCanonicalName());
		
		stateMap = new HashMap<String, IGameStateObject>();
		
		actionMap = new HashMap<String, IGameActionObject>();
		
		children = new LinkedList<IGameEntityObject>();
		
		space = new LogicalGameSpace();
		
//		registeredActions = new HashMap<String, String>();
		
		PropertiesGameState state = new PropertiesGameState(PropertiesGameState.class.getCanonicalName());
		state.addString("name", name);
		
		this.addState(state);
		
		EntityManager em = EntityManager.getInstance();
		
		if(em == null){
			throw new SpartanException("No active entity manager was found.");
		}
		
		em.addEntity(name, this);
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#createEntity(java.lang.String)
	 */
	public IGameEntityObject createEntity( String name ) throws SpartanException {
		IGameEntityObject child = new GameEntity(name);
		
		children.add(child);
		
		child.setParent(this);
		
		return child;
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getActiveActions()
	 */
	public Collection<IGameActionObject> getActiveActions() {
		return actionMap.values();
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getChildren()
	 */
	public List<IGameEntityObject> getChildren() {
		return children;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getForm()
	 */
	public IGameFormObject getForm() {
		return form;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#setForm(com.foxhole.tools.spartan.forms.IGameFormObject)
	 */
	public void setForm(IGameFormObject newForm) {
		this.form = newForm;
		if(newForm != null)
			newForm.setEntity(this);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getId()
	 */
	public int getId() {
		return id;
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getParent()
	 */
	public IGameEntityObject getParent() {
		return parent;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#setParent(com.foxhole.tools.spartan.entity.IGameEntityObject)
	 */
	public void setParent(IGameEntityObject parent) {
		this.parent = parent;
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getSpace()
	 */
	public LogicalGameSpace getSpace() {
		return space;
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getStates()
	 */
	public Map<String, IGameStateObject> getStates() {
		return stateMap;
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#removeEntity(com.foxhole.tools.spartan.entity.IGameEntityObject)
	 */
	public void removeEntity(IGameEntityObject entity) {
		if ( children.contains(entity) )
			children.remove(entity);
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#addState(com.foxhole.tools.spartan.states.IGameStateObject)
	 */
	public void addState(IGameStateObject newState) {
		if(!stateMap.containsKey(newState.getId()))
			stateMap.put(newState.getId(), newState);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#removeState(com.foxhole.tools.spartan.states.IGameStateObject)
	 */
	public void removeState(IGameStateObject state) {
		if(stateMap.containsKey(state.getId()))
			stateMap.remove(state.getId());
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#addAction(com.foxhole.tools.spartan.actions.IGameActionObject)
	 */
	public final void addAction(IGameActionObject action) {
		addAction(action.getActionName(), action);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#removeAction(java.lang.String)
	 */
	public final void removeAction(String actionName) {
		if(actionName != null && actionMap.containsKey(actionName))
			actionMap.remove(actionName);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getAction(java.lang.String)
	 */
	public final IGameActionObject getAction(String actionName) {
		if(actionName == null)
			return null;
		
		return actionMap.get(actionName);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#addAction(java.lang.String, com.foxhole.tools.spartan.actions.IGameActionObject)
	 */
	public final void addAction(String actionName, IGameActionObject action) {
		if(action != null && actionName != null)
			actionMap.put(actionName, action);
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.entity.IGameEntityObject#getState(java.lang.String)
	 */
	@Override
	public IGameStateObject getState(String gameState) {
		
		return getStates().get(gameState);
	}
}
