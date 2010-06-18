package com.foxhole.spartan.actions.movement;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PositionalGameState;

import java.lang.Math;

public class AutoAimGiroMovementAction extends BasicGameAction {

	PositionalGameState targetPosState;
	PositionalGameState userPosState;
	
	public AutoAimGiroMovementAction(String name, IGameEntityObject userEntity,
			IGameEntityObject targetEntity) throws SpartanException {
		super(name, userEntity, targetEntity);
		
		this.targetPosState = (PositionalGameState) targetEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		this.userPosState = (PositionalGameState) userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// Obter a posição do target
		// obter o ponto inicial
		float ca = userPosState.getY() - targetPosState.getY();
		float co = userPosState.getX() - targetPosState.getX();
		
		if(ca != 0){
			float k = (float) (ca /( Math.sqrt( ca*ca + co*co ) ));
			
			if( (int)k == -1 ){
				k = -0.99f;
			}
			float angle = (float) Math.toDegrees( Math.acos( k ) ) % 360;
			//System.out.println("angle : " + angle + " co(X) :" + co + " ca:(Y) " + ca);
		
			if(co > 0)
				angle = 270 - angle;
			else
				angle = angle - 90;
			
			angle = ((userPosState.getDifferenceAngle() -  userPosState.getRotation()) + angle) % 360;
			
			userPosState.addRotation(angle);
		}
	}

}
