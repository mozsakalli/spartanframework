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

package com.foxhole.spartan.tools.actions.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.SpartanFramework;
import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.RenderManager;
import com.foxhole.tools.spartan.states.PositionalGameState;

/**
 * @author Tiago "Spiegel" Costa
 *
 * An action that emulates a TopDown Camera focused on a single entity.
 * This action makes the 2d Camera follow the selected entity.
 * It is also limited to a minimal and maximum values in which the camera operates
 * it also uses the values of the game resolution to adapt the camera.
 * The camera stops at the limit of the minimal and maximum values.
 */
public class TopDownCameraAction extends BasicGameAction {

	private Vector2f minimumPoint; 
	private Vector2f maximumPoint;

	
	/**
	 * Cosntructor
	 * @param name Name of the instance of the action
	 * @param targetEntity
	 * @param minimumPoint
	 * @param maximumPoint
	 * @throws SpartanException
	 */
	public TopDownCameraAction(String name, 
			IGameEntityObject targetEntity, 
			Vector2f minimumPoint, 
			Vector2f maximumPoint)
			throws SpartanException {
		super(name, targetEntity);
		
		this.minimumPoint = minimumPoint;
		this.maximumPoint = maximumPoint;
		
		setTargetEntity(targetEntity);
	}
	
	/**
	 * Sets the target of the camera, this entity will be followed by the camera.
	 * @param targetEntity the entity to follow
	 */
	public void setTargetEntity(IGameEntityObject targetEntity){
		targetEntities.clear();
		
		userEntity = targetEntity;

	}

	/* (non-Javadoc)
	 * @see com.foxhole.spartan.actions.IGameActionObject#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
		float xTranslaction = 0;
		float yTranslaction = 0;
		
		float halfWidth  = container.getWidth()/2;
		float halfHeight  = container.getHeight()/2;
		
		PositionalGameState targetPosState = null;
		IGameEntityObject currentTarget = userEntity;
		float finalX = 0;
		float finalY = 0;
		if(currentTarget != null){
			
		while(currentTarget != null){
			targetPosState = (PositionalGameState) currentTarget.getForm().getState(PositionalGameState.class.getCanonicalName());
			
			xTranslaction += targetPosState.getX();
			yTranslaction += targetPosState.getY();
			
			currentTarget = currentTarget.getParent();
		}
		
		if(xTranslaction < (minimumPoint.x+halfWidth))
			finalX = minimumPoint.x+container.getWidth()/2;
		else if(xTranslaction > (maximumPoint.x-halfWidth) )
			finalX = maximumPoint.x-halfWidth;
		else
			finalX = xTranslaction;
		
		
		if(yTranslaction < (minimumPoint.y+halfHeight))
			finalY = minimumPoint.y+container.getHeight()/2;
		else if(yTranslaction > (maximumPoint.y-halfHeight) )
			finalY = maximumPoint.y-halfHeight;
		else
			finalY = yTranslaction;
		
		
	}else{
		finalX = (xTranslaction > container.getWidth()/2) ? xTranslaction : container.getWidth()/2;;
		finalY = (yTranslaction > container.getHeight()/2) ? yTranslaction : container.getHeight()/2;
	}
	
		SpartanFramework.getActiveRenderManager().translate((halfWidth-finalX), (container.getHeight()/2-finalY));
	}

}
