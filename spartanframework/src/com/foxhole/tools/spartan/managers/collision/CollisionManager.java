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

package com.foxhole.tools.spartan.managers.collision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.IGameFormObject;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;
import com.foxhole.tools.spartan.states.collision.CollisionGameState;

/**
 *  This is the manager responsible for identifying and performing collision between the entities
 * in the Spartan Framework.
 * This is done in a two step collision algorithm, identifying the collisions and resolution.
 * 
 * In order to identify collisions, entities must have a collision shape and a collision type
 * and register themselves in the collision manager. This will enable those entities to be found as 
 * collision enabled.
 * 
 *  In order to resolve collisions between two entities, a collision handler must be registered.
 *  
 *  All the handlers must implement the @see com.foxhole.tools.spartan.managers.collision.ICollisionHandler
 *  interface. When a handler is registered, it will enable collision resolve between the two types defined
 *  in the handler.  
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class CollisionManager implements ISpartanManager {
	
	/**
	 * Obtains the current active instance of the collision manager
	 * 
	 * @return a handle to the current active instance of the collision manager, null if no active instance is selected
	 */
	public static CollisionManager getInstance(){
		
		CollisionManager _theInstance = null;
		
		int currentId = GameStateManager.getInstance().getCurrentState();
		
		if( GameStateManager.getInstance().getStateManagers(currentId) != null){
			_theInstance = GameStateManager.getInstance().getStateManagers(currentId).getCollisionManager();
		}
		
		return _theInstance;
	}
	
	
	HashMap<String, ICollisionHandler> handlers;
	Map<Integer, List<IGameFormObject>> forms;
	Map<Integer,List<Integer>> typeCollisions;
	
	/**
	 * Creates a new collision manager
	 */
	public CollisionManager(){
		handlers = new HashMap<String, ICollisionHandler>();
		forms = new HashMap<Integer, List<IGameFormObject>>();
		typeCollisions = new HashMap<Integer, List<Integer>>();
	}
	
	/**
	 * Registers a new collision handler, from now on collisions between the two types defined
	 * in the handler shall be resolved using this handler. If another handler already exists 
	 * for these two types is removed and replaced by the new handler.
	 * 
	 * @param handler a handle to the collision handler
	 */
	public final void registerCollisionHandler(ICollisionHandler handler){
		int type1 = handler.getFirstCollisionType();
		int type2 = handler.getSecondCollisionType();
		
		handlers.put(CollisionTypePair.getID(type1, type2), handler);
		
		List<Integer> listType1 = typeCollisions.get(type1);
		
		if(listType1 == null){
			listType1 = new ArrayList<Integer>();
			typeCollisions.put(type1, listType1);
		}
		if(!listType1.contains(type2))
			listType1.add( type2 );
	}
	
	/**
	 * Registers a form for collision.
	 * 
	 * @param form the form to be added for collision check
	 * @throws SpartanException if no collision data is available for the form
	 */
	public void registerForm(IGameFormObject form) throws SpartanException{
		CollisionGameState collisionState = (CollisionGameState)form.getState( CollisionGameState.class.getCanonicalName() );
		
		if(collisionState == null){
			throw new SpartanException("No Collision State defined for the form");
		}
		
		List<IGameFormObject> formTypeList = forms.get(collisionState.getColliderType());
		
		if(formTypeList == null){
			formTypeList = new ArrayList<IGameFormObject>();
			forms.put(collisionState.getColliderType(), formTypeList);
		}
		
		if (!formTypeList.contains(form))
			formTypeList.add(form);
	}
	
	/**
	 * Removes a form from collision checking
	 * 
	 * @param form a handle to the form to remove
	 */
	public void unregisterForm(IGameFormObject form){
		CollisionGameState collisionState = (CollisionGameState)form.getState( CollisionGameState.class.getCanonicalName() );
		
		if(collisionState != null){
			List<IGameFormObject> formTypeList = forms.get(collisionState.getColliderType());
			
			if(formTypeList != null){
				formTypeList.remove(form);
			}
		}
	}
	
	/**
	 * Executes all collisions
	 */
	public void execute(){
		Set<IGameFormObject> allList = new HashSet<IGameFormObject>();
		// Obter uma lista de todos os elementos a colidir.
		
		List<CollisionData> collisions = new ArrayList<CollisionData>();
		
		Set<Integer> types = forms.keySet();
		// Obter para cada um obter a lista de tipos com os quais pode colidir
		for( Integer type : types ){
			//System.out.println("Collisions for type : " +  type);
			List<IGameFormObject> typeForm = forms.get(type);
			
			for(IGameFormObject form : typeForm){
				if ( !allList.contains(form) ){
					//allList.add(form);
					// nao: Por na lista de coisas a colidir
					List<Integer> collisionTypes = typeCollisions.get(type);
					// Para cada uma das listas verificar se ja colidiram
					if(collisionTypes != null)
					for(Integer collisionType : collisionTypes){
						//System.out.println("  |  Collides with type : " + collisionType);
						// obter todas as listas que colidem com este tipo
						List<IGameFormObject> collisionForms = forms.get(collisionType);
						if(collisionForms != null){
							// retirar o par da lista de colisoes
							for(IGameFormObject collisionForm : collisionForms){
								
								//if ( !allList.contains(collisionForm) ){
									CollisionData cd = new CollisionData();
									cd.form1 = form;
									cd.form2 = collisionForm;
									cd.handler = handlers.get(CollisionTypePair.getID(type, collisionType));
									
									collisions.add(cd);
									
									allList.add(collisionForm);
								//}
							}
						}
						
					}
				}
			}
		}
		
		for(CollisionData cd : collisions){
			cd.handler.performCollision(cd.form1, cd.form2);
		}
	}

	class CollisionData{
		public ICollisionHandler handler;
		public IGameFormObject form1;
		public IGameFormObject form2;
	}
	
}
	