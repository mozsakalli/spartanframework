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

import java.util.Map;

import com.foxhole.spartan.IRenderableSpartanObject;
import com.foxhole.spartan.entity.GameEntity;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.IGameStateObject;
import com.foxhole.spartan.states.PositionalGameState;


public interface IGameFormObject extends IRenderableSpartanObject {

	/* *********************************************************************************
	 * Through this relationship From objects can contain Sate Objects, and these State 
	 * objects can modify the behaviors that a Form object can exhibit in virtual space.
	 * 
	 */
	// Form
	public void setActiveState( IGameStateObject state );
	
	public Map<String, IGameStateObject> getStates();
	
	public IGameStateObject getState(String stateName);
	
	public void setStateList( Map<String, IGameStateObject> stateList );
	
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
	
	public VirtualGameSpace getVirtualSpace();
	
	public void addEntityToSpace(IGameFormObject entity);
	
	public void removeEntityFromSpace(IGameFormObject entity);

	public void setEntity(IGameEntityObject gameEntity);
	
	public IGameEntityObject getEntity();
	
	public void setMatrixPush(boolean pushTheMatrix);
	
	public boolean isMatrixPush();
	
	public boolean collidesWith(IGameFormObject form);
	
	public IFormPosition getPosition();
}
