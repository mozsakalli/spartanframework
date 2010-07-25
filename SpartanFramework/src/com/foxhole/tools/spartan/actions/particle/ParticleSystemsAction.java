package com.foxhole.tools.spartan.actions.particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.states.PropertiesGameState;

public class ParticleSystemsAction extends BasicGameAction {

	ParticleSystem system;
	
	public ParticleSystemsAction(String name, IGameEntityObject userEntity, ParticleSystem system)
			throws SpartanException {
		super(name, userEntity);
		
		this.system = system;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		system.update(delta);
	}

}
