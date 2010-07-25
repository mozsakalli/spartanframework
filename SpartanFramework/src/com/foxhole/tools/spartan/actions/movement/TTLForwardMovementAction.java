package com.foxhole.tools.spartan.actions.movement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.spaces.VirtualGameSpace;
import com.foxhole.tools.spartan.states.PositionalGameState;

public class TTLForwardMovementAction extends BasicGameAction {

	float speed;
	int fowardKey = Input.MOUSE_RIGHT_BUTTON;
	PositionalGameState posState = null;
	int ttl = 0;
	
	int counter = 0;
	
	VirtualGameSpace gameSpace;
	
	public TTLForwardMovementAction(String name, IGameEntityObject userEntity, float speed, int ttl, VirtualGameSpace gameSpace)
			throws SpartanException {
		super(name, userEntity);
		this.speed = speed;
		
		posState = (PositionalGameState) this.userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		
		this.ttl = ttl;
		this.gameSpace = gameSpace;
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		Vector2f direction = posState.getDirection();
		posState.setXY(posState.getX()+direction.getX()*delta*speed, posState.getY()+direction.getY()*delta*speed );
		
		counter += delta;
		
		if(counter > ttl){
			isOver = true;
			gameSpace.removeForm(userEntity.getForm());
		}
	}

}
