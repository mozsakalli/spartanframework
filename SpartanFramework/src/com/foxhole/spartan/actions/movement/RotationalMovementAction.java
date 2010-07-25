package com.foxhole.spartan.actions.movement;

import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PositionalGameState;
import com.foxhole.spartan.states.PropertiesGameState;

public class RotationalMovementAction extends BasicGameAction {

	int rotateLeftKey = Input.KEY_A;
	int rotateRightKey = Input.KEY_D;

	PositionalGameState posState = null;
	PropertiesGameState propsGameState = null;
	
	public RotationalMovementAction(String name, IGameEntityObject userEntity) throws SpartanException {
		super(name);

		if ( userEntity == null || userEntity.getForm() == null){
			isOver = true;
		}

		posState = (PositionalGameState)userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		
		propsGameState = (PropertiesGameState)userEntity.getState(PropertiesGameState.class.getCanonicalName());
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		Input input = gc.getInput();
		
		float rotation = 0;
		
		if(propsGameState != null)
			rotation = propsGameState.getFloatValue("ROTATION");
		
		if(input.isKeyDown(rotateLeftKey)){
			posState.addRotation(-rotation*delta);
		}else if(input.isKeyDown(rotateRightKey)){
			posState.addRotation(rotation*delta);
		}
	}
}
