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

package com.foxhole.tools.spartan.actions;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.INonRenderableSpartanObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;

/**
 * @author Tiago "Spiegel" Costa
 * 
 * The definition of a action in the spartan framework, all entities MUST
 * implement this interface in order to interact with all the other components
 * in the system.
 */
public interface IGameActionObject extends INonRenderableSpartanObject {
	
	/**
	 * Obtains the name of the action
	 * @return a String with the name of the action
	 */
	public String getActionName();
	
	// Affected objects
	/**
	 * Obtains the entity associated with the launch of the action, this is not the 
	 * entity that has launched the action, but the entity to which the action concerns.
	 * Example: a movement action shall have the launcher entity as the entity that is affected
	 * by the movement.
	 * @return An entity, null if the action does not concern an entity.
	 */
	public IGameEntityObject getLauncherEntity();
	
	/**
	 * Adds a new entity to the list of affected entities to this action.
	 * Example: if there is a forcefield action that slows every entity within it,
	 * when an entity enters the force field it should be added using this method.
	 * @param entity the entity to add
	 */
	public void addAffectableEntity( IGameEntityObject entity );
	
	/**
	 * Removes the entity from the list of affected entities to this action.
	 * Example: if there is a forcefield action that slows every entity within it,
	 * when an entity exists the force field it should be removed from the action 
	 * using this method.
	 * @param entity the entity to remove
	 */
	public void removeAffectableEntity( IGameEntityObject entity );
	
	// action 	
	/**
	 * The update method of the action, this is where the action shall perform its fucntion.
	 * This is exactly the same method signature that can be found in Slick2d update method.
	 * Usually this method is called by the action manager, and not directly by the user.
	 * @param gc the game container
	 * @param sbg the state base game (null if no slick states are used)
	 * @param delta the time in milliseconds since the las update
	 */
	public void update( GameContainer gc, StateBasedGame sbg, int delta );
	
	/**
	 * Checks if the action has ended.
	 * @return true if the action is over, false otherwise
	 */
	public boolean isOver();
	
	/**
	 * Checks if the action is paused.
	 * @return true if the action is paused, false otherwise
	 */
	public boolean isPaused();
	
	/**
	 * Pauses the action.
	 */
	public void pause();
	
	/**
	 * Resume the actionif it has been paused before, otherwise it does nothing.
	 */
	public void resume();

	/**
	 * Stops the action.
	 */
	public void stop();
}
