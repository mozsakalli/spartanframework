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

package com.foxhole.spartan.actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;


/**
 * 
 * @author Tiago "Spiegel" Costa
 *
 * Usage: All the actions based on this basic game action also make use of the
 * action manager. As such the the only thing that a class must be able to do is 
 * to register and unresgister itself from the manager.  
 * 
 * creation: 2010/01/12
 */
public abstract class BasicGameAction implements IGameActionObject {

	private String name;
	protected List<IGameEntityObject> targetEntities;
	protected IGameEntityObject userEntity;
	
	protected boolean isOver;
	private boolean isPaused = false;
	
	public BasicGameAction( String name, IGameEntityObject userEntity, List<IGameEntityObject> targetEntities) throws SpartanException{
		initializeAction(name, userEntity, targetEntities);
	}
	
	public BasicGameAction( String name, IGameEntityObject userEntity) throws SpartanException{
		initializeAction(name, userEntity, null);
	}
	
	public BasicGameAction( String name, IGameEntityObject userEntity, IGameEntityObject targetEntity) throws SpartanException{
		List<IGameEntityObject> tempEntities = new ArrayList<IGameEntityObject>();
		tempEntities.add(targetEntity);
		
		initializeAction(name, userEntity, tempEntities);
	}
	
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
	
	
	public boolean isOver(){
		return isOver;
	}
	
	public void setIsOver(boolean isOver){
		this.isOver = isOver;
	}
	
	public String getActionName() {
		return name;
	}

	public IGameEntityObject getLauncherEntity() {
		return userEntity;
	}
	
	public void addAffectableEntity(IGameEntityObject entity) {
		if(!targetEntities.contains(entity))
			targetEntities.add(entity);
	}

	public void removeAffectableEntity(IGameEntityObject entity) {
		if(targetEntities.contains(entity))
			targetEntities.remove(entity);
	}
	
	public final boolean isPaused(){
		return isPaused;
	}
	
	public void pause(){
		isPaused = true;
	}
	
	public void resume(){
		isPaused = false;
	}
}
