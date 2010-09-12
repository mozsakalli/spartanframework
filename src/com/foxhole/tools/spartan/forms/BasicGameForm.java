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

package com.foxhole.tools.spartan.forms;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.geom.Shape;

import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.IGameStateObject;
import com.foxhole.tools.spartan.states.PositionalGameState;


/**
 * This is the implementation of the basic for all forms.
 * All the forms should derive from this class.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
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
	
	private boolean identity;
	
	private boolean pushTheMatrix;
	
	protected Shape boundingShape;
	
	/**
	 * Constructs a basic form
	 */
	public BasicGameForm(){
		virtualSpace = new VirtualGameSpace("form");
		activeState = null;
		
		states = new HashMap<String, IGameStateObject>();
		
		// regular states
		// Positional
		posState = new PositionalGameState(PositionalGameState.class.getCanonicalName());
		posState.setScale(1);
		posState.setRotation(0);
		posState.setPosition(0,0);
		
		addState(posState);
		
		pushTheMatrix = true;
		
		identity = false;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#setEntity(com.foxhole.tools.spartan.entity.IGameEntityObject)
	 */
	public void setEntity(IGameEntityObject gameEntity){
		this.entity = gameEntity;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#getEntity()
	 */
	public final IGameEntityObject getEntity(){
		return entity;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#addEntityToSpace(com.foxhole.tools.spartan.forms.IGameFormObject)
	 */
	public void addEntityToSpace(IGameFormObject form) {
		virtualSpace.addObject(form);
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#removeEntityFromSpace(com.foxhole.tools.spartan.forms.IGameFormObject)
	 */
	public void removeEntityFromSpace(IGameFormObject form) {
		virtualSpace.removeObject(form);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#getStates()
	 */
	public Map<String, IGameStateObject> getStates() {
		return states;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#getState(java.lang.String)
	 */
	public IGameStateObject getState(String stateName){
		return states.get(stateName);
	}

	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#getVirtualSpace()
	 */
	public VirtualGameSpace getVirtualSpace() {
		return virtualSpace;
	}

	//@Override
	/**
	 * sets thge active state
	 * @param state the active state
	 * @deprecated no use for this metod
	 */
	public void setActiveState(IGameStateObject state) {
		if(states.containsKey(state.getId()))
			activeState = state;
	}
	
	//@Override
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#addState(com.foxhole.tools.spartan.states.IGameStateObject)
	 */
	public void addState(IGameStateObject state) {
		if( !states.containsKey(state.getId()) )
			states.put(state.getId(), state);
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#setStateList(java.util.Map)
	 */
	public void setStateList(Map<String, IGameStateObject> stateMap) {
		states = stateMap;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#getPosition()
	 */
	public final IFormPosition getPosition(){
		return posState;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#setMatrixPush(boolean)
	 */
	public final void setMatrixPush(boolean pushTheMatrix){
		this.pushTheMatrix = pushTheMatrix;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#useMatrixPush()
	 */
	public final boolean useMatrixPush(){
		return pushTheMatrix;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#setIdentity(boolean)
	 */
	public void setIdentity(boolean identity){
		this.identity = identity;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#useIdentity()
	 */
	public final boolean useIdentity(){
		return identity;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IGameFormObject#collidesWith(com.foxhole.tools.spartan.forms.IGameFormObject)
	 */
	public boolean collidesWith(IGameFormObject other) {
		Shape collisionShape = getPosition().getCollisionShape();
		
		Shape otherCollisionShape = other.getPosition().getCollisionShape();
		
		return collisionShape != null && otherCollisionShape != null && 
			   collisionShape.intersects(otherCollisionShape);
	}
}
