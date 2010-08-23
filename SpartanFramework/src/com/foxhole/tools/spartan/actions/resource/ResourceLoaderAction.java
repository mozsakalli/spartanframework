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

package com.foxhole.tools.spartan.actions.resource;

import java.io.IOException;
import java.util.logging.Logger;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.states.PropertiesGameState;

/**
 * A resource loading action that loads resources from the deference list while updating a percentage
 * value in the target entity state.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class ResourceLoaderAction extends BasicGameAction {
	
	private static final Logger _tracer = Logger.getLogger(ResourceLoaderAction.class.getName());

	private PropertiesGameState properties;
	
	/**
	 * Constructs a Resource Loader Action.
	 * 
	 * @param name Identifier of the action
	 * @param userEntity The target entity that shall receive the updates for the loading percentage
	 * @throws SpartanException When an error occurs.
	 */
	public ResourceLoaderAction(String name, IGameEntityObject userEntity)
			throws SpartanException {
		super(name, userEntity);
		
		properties = (PropertiesGameState) userEntity.getForm().getState(PropertiesGameState.class.getCanonicalName());
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.actions.IGameActionObject#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// load the resources
		if (LoadingList.get().getRemainingResources() > 0) { 
	        DeferredResource nextResource = LoadingList.get().getNext(); 
	        try {
				nextResource.load();
				
				// update percentages
				int percentage = 100 * (LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources()) / LoadingList.get().getTotalResources();
				
				properties.addInt("LOAD_PERCENTAGE", percentage);
			} catch (IOException ioEx) {
				_tracer.throwing(getClass().getCanonicalName(),"update(GameContainer gc, StateBasedGame sbg, int delta)", ioEx);
			}
		}else{
			isOver = true;
		}
		
	}

}
