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

package com.foxhole.tools.spartan.actions.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.actions.IGameActionObject;
import com.foxhole.tools.spartan.exception.SpartanException;

/**
 * EnterGameStateOnActionOver is an action that monitors another action existence, 
 * and when that action ceases to exist, this action takes over and changes the Slick game state
 * to the one defined in this action
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class EnterGameStateOnActionOver extends BasicGameAction {

	private int targetStateId;
	private IGameActionObject targetAction;
	private Transition leave;
	private Transition enter;
	
	/**
	 * Creates a EnterGameStateOnActionOver.
	 * 
	 * @param name The name of the action
	 * @param targetAction The target action to monitor.
	 * @param targetStateId The slick state Id to jump after the action is over.
	 * @param leave The leave transactions (see slick2d transactions)
	 * @param enter The enter transactions (see slick2d transactions)
	 * @throws SpartanException When an error occurs
	 */
	public EnterGameStateOnActionOver(String name, IGameActionObject targetAction, int targetStateId, Transition leave, Transition enter) throws SpartanException {
		super(name);
		
		this.targetAction = targetAction;
		this.targetStateId = targetStateId;
		
		this.leave = leave;
		this.enter = enter;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.actions.IGameActionObject#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	//@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(targetAction == null || targetAction.isOver()){
			try{
				if(leave == null || enter == null){
					sbg.enterState(targetStateId);
				} else {
					sbg.enterState(targetStateId, leave, enter);
				}
			}finally{
				targetAction = null;
			}
		}
	}

}
