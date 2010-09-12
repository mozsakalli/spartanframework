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

package com.foxhole.tools.spartan.managers.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;


/**
 * The action Manager is the component responsible for updating all the 
 * actions in the system.
 * This is the update of the game.
 * The user can register actions into the manager and forget about them,
 * these actions will expire by their own and the action manager cleans all 
 * ended actions.
 * 
 * @author Tiago "Spiegel" Costa
 */
public class ActionManager implements ISpartanManager {

	/**
	 * Obtains the current active instance of the action manager
	 * 
	 * @return a handle the current active instance of the action manager, null if no active instance is selected
	 */
	public static ActionManager getInstance(){
		
		ActionManager _theInstance = null;
		
		int currentState = GameStateManager.getInstance().getCurrentState();
		
		if( GameStateManager.getInstance().getStateManagers(currentState) != null){
			_theInstance = GameStateManager.getInstance().getStateManagers(currentState).getActionManager();
		}
		
		return _theInstance;
	}
		
	protected Map<String, IGameActionObject> registedActions;
	
	
	/**
	 * Creates a new action manager.
	 */
	public ActionManager(){
		registedActions = new HashMap<String, IGameActionObject>();
	}
	
	/**
	 * Registers an action into the action manager. Only after performing this step is the
	 * action updated.
	 * 
	 * @param action the action to be updated
	 * @throws SpartanException if any error occurs
	 */
	public final void registerAction(IGameActionObject action) throws SpartanException{
		if(action == null)
			return;
		
		if(getAction(action.getActionName(), action.getLauncherEntity()) != null)
			throw new SpartanException("action already exists");
		
		registedActions.put(generateKey( action.getActionName(), action.getLauncherEntity()), action);

		if(action.getLauncherEntity() != null)
			action.getLauncherEntity().addAction(action);
	}
	
	/**
	 * Obtains an action by its id and its owner entity. There can be only one action per
	 * pair (id, owner entity)
	 * 
	 * @param id the id to which the action is known
	 * @param entity the entity that is the owner of the action (may be null)
	 * @return an action if any is found, null otherwise
	 */
	public final IGameActionObject getAction(String id, IGameEntityObject entity){
		return registedActions.get(generateKey( id, entity ));		
	}
	
	/**
	 * Unregisters actions, removing it from the update cycle.
	 * 
	 * @param id the id to which the action is known
	 * @param entity the entity that is the owner of the action (may be null)
	 */
	public final void unregisterAction(String id, IGameEntityObject entity ){
		registedActions.remove(generateKey( id, entity ));
		
		if(entity != null)
			entity.removeAction(id);
	}
	
	/**
	 * Unregisters actions, removing it from the update cycle.
	 * 
	 * @param action the intended action to be removed.
	 */
	public final void unregisterAction(IGameActionObject action ){
		unregisterAction(action.getActionName(), action.getLauncherEntity());
	}
	
	/**
	 * Obtains all the actions currently being updated by the manager.
	 * 
	 * @return a Collection of actions
	 */
	public final Collection<IGameActionObject> getActions(){
		Collection<IGameActionObject> result = new ArrayList<IGameActionObject>();
		
		result.addAll(registedActions.values());
		
		return result;
	}
	
	private final static String generateKey(String id, IGameEntityObject entity){
		return (entity == null ? "SpartanWorld" : entity.getId()) + "-" + id ;
	}

	/**
	 * Resets the action manager, removing all actions for the update cycle.
	 */
	public void reset() {
		this.registedActions.clear();
	}

	/**
	 * The update method that is invoked to advance all the actions in the system.
	 * 
	 * @param gc Slick2d game container
	 * @param sbg Slick2d State base game
	 * @param delta the time in milliseconds after the last update
	 */
	public void updateActions(GameContainer gc, StateBasedGame sbg, int delta) {
		Set<String> keys = new HashSet<String>();
		
		keys.addAll( registedActions.keySet() );
		
		List<String> removalKeys = new ArrayList<String>();
		
		for(String key : keys){
			IGameActionObject action = registedActions.get(key);
			
			
			if(action.isOver())
				removalKeys.add(key);
			else if(!action.isPaused()){
				action.update(gc, sbg,delta);
			}
		}
		
		for(String key : removalKeys){
			registedActions.remove(key);
		}
	}
}
