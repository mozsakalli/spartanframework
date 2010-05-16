package com.foxhole.spartan.actions.particle;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.particles.ParticleSystem;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PropertiesGameState;

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
