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

package com.foxhole.tools.spartan.actions.particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;

/**
 * Particle system action, an action to update any particle system 
 * made with pedrigree.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class ParticleSystemsAction extends BasicGameAction {

	private ParticleSystem system;
	
	/**
	 * Constructs a new Particle System Action.
	 * 
	 * @param name
	 * @param userEntity
	 * @param system
	 * @throws SpartanException
	 */
	public ParticleSystemsAction(String name, IGameEntityObject userEntity, ParticleSystem system)
			throws SpartanException {
		super(name, userEntity);
		
		this.system = system;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.actions.IGameActionObject#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		system.update(delta);
	}

}
