package com.foxhole.tools.spartan.actions.movement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.render.RenderManager;
import com.foxhole.tools.spartan.states.PositionalGameState;

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
				(-RenderManager.getInstance().translationX + input.getMouseX() ) -userPosState.getX(), 
				(-RenderManager.getInstance().translationY + input.getMouseY())-userPosState.getY() );
		
		double angle = direction.getTheta();
		
		userPosState.setRotation((float) angle);	
	}

}
