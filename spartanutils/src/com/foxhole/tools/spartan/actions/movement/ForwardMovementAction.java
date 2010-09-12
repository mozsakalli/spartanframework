package com.foxhole.tools.spartan.actions.movement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.states.PositionalGameState;

public class ForwardMovementAction extends BasicGameAction {

	float speed;
	int fowardKey = Input.KEY_W;
	PositionalGameState posState = null;
	boolean auto = false;
	
	public ForwardMovementAction(String name, IGameEntityObject userEntity, float speed, boolean auto)
			throws SpartanException {
		super(name, userEntity);
		this.speed = speed;
		
		posState = (PositionalGameState) this.userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		
		this.auto = auto;
	}
	
	public ForwardMovementAction(String name, IGameEntityObject userEntity, float speed)
	throws SpartanException {
		this(name, userEntity, speed, false);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		if(auto || gc.getInput().isKeyDown(fowardKey)){
			Vector2f direction = posState.getDirection();
			
			direction = direction.normalise();
			
			posState.setPosition(posState.getX()+direction.getX() * delta*speed, posState.getY()+direction.getY() * delta*speed );
		}
	}

}
