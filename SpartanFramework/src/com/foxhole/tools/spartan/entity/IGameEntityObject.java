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
 * @author Spiegel
 * creation: 2009/12/06
 */
public interface IGameEntityObject extends INonRenderableSpartanObject {

	// Entity methods
	public int getId();
	
	public IGameEntityObject createEntity( String name );
	
	public void removeEntity( IGameEntityObject entity );
	
	public List<IGameEntityObject> getChildren();
	
	public IGameEntityObject getParent();
	
	public void setParent(IGameEntityObject parent);
	
	// Action	
	public Collection<IGameActionObject> getActiveActions();
	
	public void addAction(String actionName, IGameActionObject action);
	
	public void addAction(IGameActionObject action);
	
	public void removeAction(String action);
	
	public IGameActionObject getAction(String action);
	
	// Form
	public IGameFormObject getForm();
	
	public void setForm(IGameFormObject newForm);
	
	// State
	public Map<String, IGameStateObject> getStates() ;
	
	public IGameStateObject getState(String gameState) ;
	
	public void addState(IGameStateObject newState);
	
	public void removeState(IGameStateObject state);
	
	// Space
	public LogicalGameSpace getSpace();
}