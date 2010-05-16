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

package com.foxhole.spartan.managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.IGameActionObject;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;


public class ActionManager implements ISpartanManager {

	//private static ActionManager _instance = new ActionManager();
	
	private Map<String, IGameActionObject> registedActions;
	
	
	public ActionManager(){
		registedActions = new HashMap<String, IGameActionObject>();
	}
	/*
	public final static ActionManager getInstance(){
		return _instance;
	}
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
	
	public final IGameActionObject getAction(String id, IGameEntityObject entity){
		return registedActions.get(generateKey( id, entity ));		
	}
	
	public final void unregisterAction(String id, IGameEntityObject entity ){
		registedActions.remove(generateKey( id, entity ));
		
		if(entity != null)
			entity.removeAction(id);
	}
	
	public final void unregisterAction(IGameActionObject action ){
		unregisterAction(action.getActionName(), action.getLauncherEntity());
	}
	
	public final Collection<IGameActionObject> getActions(){
		Collection<IGameActionObject> result = new ArrayList<IGameActionObject>();
		
		result.addAll(registedActions.values());
		
		return result;
	}
	
	private final static String generateKey(String id, IGameEntityObject entity){
		return (entity == null ? "SpartanWorld" : entity.getId()) + "-" + id ;
	}

	public void reset() {
		this.registedActions.clear();
	}

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

	public void getAllActionsFor(IGameEntityObject entity) {
		// TODO Auto-generated method stub
		
	}
}
