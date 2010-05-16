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

package com.foxhole.spartan.form;

import java.util.HashMap;
import java.util.Map;

import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.IGameStateObject;
import com.foxhole.spartan.states.PositionalGameState;


public abstract class BasicGameForm implements IGameFormObject {

	// Game Form virtual space
	protected VirtualGameSpace virtualSpace;
	// list of known states
	protected Map<String, IGameStateObject> states;
	// active state
	protected IGameStateObject activeState;
	// POsitional state
	protected PositionalGameState posState;
	// Parent Entity
	IGameEntityObject entity;
	
	private boolean pushTheMatrix;
	
	public BasicGameForm(){
		virtualSpace = new VirtualGameSpace("form");
		activeState = null;
		
		states = new HashMap<String, IGameStateObject>();
		
		// regular states
		// Positional
		posState = new PositionalGameState(PositionalGameState.class.getCanonicalName());
		posState.setScale(1);
		posState.setRotation(0);
		posState.setXY(0,0);
		
		addState(posState);
		
		pushTheMatrix = true;
	}
	
	public void setEntity(IGameEntityObject gameEntity){
		this.entity = gameEntity;
	}
	
	public final IGameEntityObject getEntity(){
		return entity;
	}
	
	//@Override
	public void addEntityToSpace(IGameFormObject form) {
		virtualSpace.addObject(form);
	}
	
	//@Override
	public void removeEntityFromSpace(IGameFormObject form) {
		virtualSpace.removeObject(form);
	}

	//@Override
	public Map<String, IGameStateObject> getStates() {
		return states;
	}
	
	//@Override
	public IGameStateObject getState(String stateName){
		return states.get(stateName);
	}

	//@Override
	public VirtualGameSpace getVirtualSpace() {
		return virtualSpace;
	}

	//@Override
	public void setActiveState(IGameStateObject state) {
		if(states.containsKey(state.getId()))
			activeState = state;
	}
	
	//@Override
	public void addState(IGameStateObject state) {
		if( !states.containsKey(state.getId()) )
			states.put(state.getId(), state);
	}

	//@Override
	public void setStateList(Map<String, IGameStateObject> stateMap) {
		states = stateMap;
	}
	
	public final IFormPosition getPosition(){
		return posState;
	}
	
	public final void setMatrixPush(boolean pushTheMatrix){
		this.pushTheMatrix = pushTheMatrix;
	}
	
	public final boolean isMatrixPush(){
		return pushTheMatrix;
	}
	
	public boolean collidesWith(IGameFormObject form) {
		return false;
	}
}
