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

import java.util.Map;

import com.foxhole.tools.spartan.IRenderableSpartanObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.IGameStateObject;


/**
 * Interface that specifies the well known behavior of a form.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public interface IGameFormObject extends IRenderableSpartanObject {

	/* *********************************************************************************
	 * Through this relationship From objects can contain Sate Objects, and these State 
	 * objects can modify the behaviors that a Form object can exhibit in virtual space.
	 * 
	 */
	// Form
	//public void setActiveState( IGameStateObject state );
	
	/**
	 * Get the list of states in the form
	 * 
	 * @return a map of states
	 */
	public Map<String, IGameStateObject> getStates();
	
	/**
	 * Obtains a specific state
	 * 
	 * @param stateName the state identifier
	 * @return an handle to a state object, null if none is found
	 */
	public IGameStateObject getState(String stateName);
	
	/**
	 * Sets the states to the ones in the parameter list.
	 * 
	 * @param stateList the new list of states
	 */
	public void setStateList( Map<String, IGameStateObject> stateList );
	
	/**
	 * Ads a new state to the form
	 * 
	 * @param state a handle to a state object
	 */
	public void addState( IGameStateObject state );
	
	// space
	
	/* *********************************************************************************
	 * A form object can contain Space Objects, so other entities can be implemented and
	 * contained within Space Objects, and can be implemented to affect entire sets of 
	 * entities. Space lists contained in a form may be a quick way to impose limits on 
	 * the entities that compose them based on this form.
	 * 
	 * The form space can be viewed as a collection of entities that may be affected by
	 * the form of the object. For example: A local gravity field, any object inside it 
	 * may be slowed down. So with a list like this we could easily perform a slow down 
	 * action on all intervinent entities.
	 */
	
	/**
	 * Obtains the virtual space of this form.
	 * 
	 * @return an handle to the virtual space
	 */
	public VirtualGameSpace getVirtualSpace();
	
	/**
	 * Adds a new entity to the virtual space of this form
	 * 
	 * @param entity an handle to the entity to add
	 */
	public void addEntityToSpace(IGameFormObject entity);
	
	/**
	 * Removes a entity from this virtual space
	 * 
	 * @param entity an handle to the entity to remove
	 */
	public void removeEntityFromSpace(IGameFormObject entity);

	/**
	 * Sets the owner entity of this form.
	 * 
	 * @param gameEntity the game entity that the form will recognize as its owner
	 */
	public void setEntity(IGameEntityObject gameEntity);
	
	/**
	 * Obtains the owner entity of this form.
	 * 
	 * @return an handle to the entity that is recognized as the owner of this form
	 */
	public IGameEntityObject getEntity();
	
	/**
	 * Obtains the Positional element data of the form.
	 * 
	 * @return an handle to the positional data
	 */
	public IFormPosition getPosition();
	
	/**
	 * Sets the form to render on top of an identity matrix.
	 * This will make the HUD effect where the form is rendered as a HUD element and stay put on its
	 * position, even if the world moves.
	 * 
	 * @param identity a boolean value
	 */
	public void setIdentity(boolean identity);
	
	/**
	 * Check to see if the form uses the identity. 
	 * 
	 * @return a boolean 
	 */
	public boolean useIdentity();
	
	/**
	 * No need to use this
	 * 
	 * @param pushTheMatrix
	 * @deprecated
	 */
	public void setMatrixPush(boolean pushTheMatrix);
	
	/**
	 * No need to use this
	 * 
	 * @return boolean
	 * @deprecated
	 */
	public boolean useMatrixPush();
	
	// Collision
	
	/**
	 * Check to see if a collision between forms occurs
	 * 
	 * @param other the other form
	 * @return true of the two forms are colliding, false if not.
	 */
	public boolean collidesWith(IGameFormObject other);
}

