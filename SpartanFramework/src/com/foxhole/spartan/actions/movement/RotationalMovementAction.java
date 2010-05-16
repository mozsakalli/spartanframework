package com.foxhole.spartan.actions.movement;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PositionalGameState;

public class RotationalMovementAction extends BasicGameAction {

	float rotation;
	int rotateLeftKey = Input.KEY_A;
	int rotateRightKey = Input.KEY_D;

	PositionalGameState posState = null;
	
	public RotationalMovementAction(String name, IGameEntityObject userEntity, float rotation) throws SpartanException {
		super(name);

		if ( userEntity == null || userEntity.getForm() == null){
			isOver = true;
		}

		this.rotation = rotation;
		
		posState = (PositionalGameState)userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();
		
		if(input.isKeyDown(rotateLeftKey)){
			posState.addRotation(-rotation*delta);
		}else if(input.isKeyDown(rotateRightKey)){
			posState.addRotation(rotation*delta);
		}
	}
}
