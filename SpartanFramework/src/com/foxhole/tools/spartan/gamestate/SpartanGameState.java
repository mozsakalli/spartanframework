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

package com.foxhole.tools.spartan.gamestate;
 
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.action.ActionManager;
import com.foxhole.tools.spartan.managers.collision.CollisionManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;
import com.foxhole.tools.spartan.managers.input.InputManager;
import com.foxhole.tools.spartan.managers.render.RenderManager;

/**
 * The Spartan game state is the simple way to create a fully functional slick2d state
 * integrating all managers.
 * The only thing that the user must do is initialize the entitys and its actions, and the game
 * shall run by itself.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public abstract class SpartanGameState extends org.newdawn.slick.state.BasicGameState {

	/**
	 * Constructs a spartangamestate, this will create an entry in all managers for this state
	 * 
	 * @throws SpartanException
	 */
	public SpartanGameState() throws SpartanException{
		GameStateManager.getInstance().addState(getID());
	}
	
	/**
	 * Uses the action manager to update all actions and the collision manager to check if any collision
	 * is available.
	 * @see org.newdawn.slick.state.GameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta)	throws SlickException {
		// update all actions
		ActionManager.getInstance().updateActions(gc, sbg, delta);
		// execute all collisions
		CollisionManager.getInstance().execute();
		
		
	}
	
	/**
	 * Uses the render manager to render the forms.
	 * 
	 * @see org.newdawn.slick.state.GameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		RenderManager.getInstance().render(gc, sbg, graphics);
	}
	
	/**
	 * Changes the current state in the statemanager for this state, so all references for the managers will
	 * refer to this state.
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#enter(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void enter(GameContainer container, StateBasedGame game) throws SlickException {
		GameStateManager.getInstance().setState(getID());
		
		container.getInput().addListener( InputManager.getInstance() );
	}
	
	/**
	 * Removes the listenre from the input manager 
	 * 
	 * @see org.newdawn.slick.state.BasicGameState#enter(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void leave(GameContainer container, StateBasedGame game) throws SlickException {
		container.getInput().removeListener( InputManager.getInstance() );
	}
}
