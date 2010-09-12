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

package com.foxhole.spartan.examples.topdownplane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.movement.ForwardMovementAction;
import com.foxhole.tools.spartan.actions.movement.RotationalMovementAction;
import com.foxhole.tools.spartan.entity.GameEntity;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.ImageGameForm;
import com.foxhole.tools.spartan.gamestate.SpartanGameState;
import com.foxhole.tools.spartan.managers.action.ActionManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.PropertiesGameState;

public class BattleZoneState extends SpartanGameState {

	public BattleZoneState() throws SpartanException {
		super();
	}

	@Override
	public int getID() {
		return 1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
		
	}
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		super.enter(container, game);
		
		// create the virtualspace in which to render
		VirtualGameSpace background = RenderManager.getInstance().addNewVirtualGameSpace("background");
		VirtualGameSpace action = RenderManager.getInstance().addNewVirtualGameSpace("action");
		
		// create the objects to render
		try {
			IGameEntityObject backgroundEntity = new GameEntity("background");
			ImageGameForm backgroundForm = new ImageGameForm(new Image("testdata/background.jpg"));
			backgroundEntity.setForm(backgroundForm);
			
			background.addForm(backgroundForm);
		} catch (SpartanException e) {
			throw new SlickException("Could not create background entity", e);
		}
		
		
		// create the plane
		IGameEntityObject planeEntity  = null;
		try {
			planeEntity  = new GameEntity("plane");
			
			ImageGameForm planeForm = new ImageGameForm(new Image("testdata/plane.png"));
			
			planeEntity.setForm(planeForm);
			
			action.addForm(planeForm);
			
			planeForm.getPosition().setCenterPosition(61, 60);
			
			planeForm.getPosition().setPosition(container.getWidth()/2+planeForm.getPosition().getCenterposX(), 
					container.getHeight()/2+planeForm.getPosition().getCenterposY());
			planeForm.getPosition().setDirection(0, -1);
			
			((PropertiesGameState)planeEntity.getState(PropertiesGameState.class.getCanonicalName())).addFloat("ROTATION", 0.5f);
		} catch (SpartanException e) {
			throw new SlickException("Could not create plane entity", e);
		}
		
		
		//actions
		try {
			RotationalMovementAction raction = new RotationalMovementAction(RotationalMovementAction.class.getCanonicalName(), planeEntity);
			ActionManager.getInstance().registerAction(raction);
			
			ForwardMovementAction faction = new ForwardMovementAction(ForwardMovementAction.class.getCanonicalName(), planeEntity, 0.3f);
			ActionManager.getInstance().registerAction(faction);
			
		} catch (SpartanException e) {
			throw new SlickException("Could not start actions", e);
		}
	}
}

