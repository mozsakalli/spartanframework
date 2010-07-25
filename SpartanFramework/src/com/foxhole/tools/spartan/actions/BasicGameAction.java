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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;


/**
 * 
 * @author Tiago "Spiegel" Costa
 * 
 * The Basic Game action creates a standard abstract action to serve as a beginning point
 * for all other actions in the framework, defining all the common functionality.
 * While the actions are not forced to derive from this class, all should o so
 * as this class already defines a lot of concepts for game development using actions 
 * and entities.
 * 
 */
public abstract class BasicGameAction implements IGameActionObject {

	private String name;
	protected List<IGameEntityObject> targetEntities;
	protected IGameEntityObject userEntity;
	
	protected boolean isOver;
	private boolean isPaused = false;
	
	/**
	 * Expanded constructor, creates an action with a applicable entity (user entity) and a set of target
	 * entities.
	 * The applicable entity may be a target of the action or a source to obtain information, it will always depend
	 * on the context of the action.
	 * The target list is a simple way to apply a single action to a set of entities.
	 * @param name the name of the action
	 * @param userEntity the applicable entity that has launched the action
	 * @param targetEntities a list of entities that may be targeted by the action.
	 * @throws SpartanException returned upon error
	 */
	public BasicGameAction( String name, IGameEntityObject userEntity, List<IGameEntityObject> targetEntities) throws SpartanException{
		initializeAction(name, userEntity, targetEntities);
	}
	
	/**
	 * Normal constructor, creates an action with a applicable entity (user entity).
	 * The applicable entity may be a target of the action or a source to obtain information, it will always depend
	 * on the context of the action.
	 * @param name the name of the action
	 * @param userEntity the applicable entity that has launched the action
	 * @throws SpartanException returned upon error
	 */
	public BasicGameAction( String name, IGameEntityObject userEntity) throws SpartanException{
		initializeAction(name, userEntity, null);
	}
	
	/**
	 * One target constructor, creates an action with a applicable entity (user entity) and a single target.
	 * The applicable entity may be a target of the action or a source to obtain information, it will always depend
	 * on the context of the action.
	 * @param name the name of the action
	 * @param userEntity the applicable entity that has launched the action
	 * @param targetEntity A single action target
	 * @throws SpartanException returned upon error
	 */
	public BasicGameAction( String name, IGameEntityObject userEntity, IGameEntityObject targetEntity) throws SpartanException{
		List<IGameEntityObject> tempEntities = new ArrayList<IGameEntityObject>();
		tempEntities.add(targetEntity);
		
		initializeAction(name, userEntity, tempEntities);
	}
	
	/**
	 * Basic Constructor, creates a simple object with no entities associated with the action.
	 * @param name the name of the action
	 * @throws SpartanException returned upon error
	 */
	public BasicGameAction(String name) throws SpartanException{
		initializeAction(name, null, null);
	}
	
	private void initializeAction(String name, IGameEntityObject userEntity, List<IGameEntityObject> targetEntities){
		this.targetEntities = new LinkedList<IGameEntityObject>();
		
		if(targetEntities != null){
			this.targetEntities.addAll(targetEntities);
		}
		
		this.userEntity = userEntity;
		
		this.name = name;
		
		isOver = false;
	}
	
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#isOver()
	 */
	public boolean isOver(){
		return isOver;
	}
	
	/**
	 * Sets the action in an ended or open state. 
	 * @param isOver the value to be set
	 */
	public void setIsOver(boolean isOver){
		this.isOver = isOver;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#getActionName()
	 */
	public String getActionName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#getLauncherEntity()
	 */
	public IGameEntityObject getLauncherEntity() {
		return userEntity;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#addAffectableEntity(com.foxhole.spartan.entity.IGameEntityObject)
	 */
	public void addAffectableEntity(IGameEntityObject entity) {
		if(!targetEntities.contains(entity))
			targetEntities.add(entity);
	}

	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#removeAffectableEntity(com.foxhole.spartan.entity.IGameEntityObject)
	 */
	public void removeAffectableEntity(IGameEntityObject entity) {
		if(targetEntities.contains(entity))
			targetEntities.remove(entity);
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#isPaused()
	 */
	public final boolean isPaused(){
		return isPaused;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#pause()
	 */
	public void pause(){
		isPaused = true;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#resume()
	 */
	public void resume(){
		isPaused = false;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#stop()
	 */
	public void stop(){
		isOver = true;
	}
}
