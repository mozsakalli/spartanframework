package com.foxhole.spartan.actions.movement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.SpartanFramework;
import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PositionalGameState;

public class LookAtMouseMovementAction extends BasicGameAction {

	PositionalGameState userPosState;
	
	public LookAtMouseMovementAction(String name, IGameEntityObject userEntity) throws SpartanException {
		super(name, userEntity);
		
		this.userPosState = (PositionalGameState) userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// Obter a posição do target
		// obter o ponto inicial
		Input input = gc.getInput();
		Vector2f direction = new Vector2f( 
				(-SpartanFramework.getActiveRenderManager().translationX + input.getMouseX() ) -userPosState.getX(), 
				(-SpartanFramework.getActiveRenderManager().translationY + input.getMouseY())-userPosState.getY() );
		
		double angle = direction.getTheta();
		
		userPosState.setRotation((float) angle);	
	}

}
