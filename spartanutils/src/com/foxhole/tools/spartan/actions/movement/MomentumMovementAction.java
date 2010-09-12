package com.foxhole.tools.spartan.actions.movement;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.states.PositionalGameState;
import com.foxhole.tools.spartan.states.PropertiesGameState;

public class MomentumMovementAction extends BasicGameAction {

	float stepMomentum;
	float maxMomentum;
	
	int fowardKey = Input.KEY_W;
	
	PositionalGameState posState;
	PropertiesGameState properties;
	
	Vector2f finalDirection;
	
	float speed;
	boolean newMomentum;
	List<Momentum> momentums;
	Momentum currentMomentum;
	
	public MomentumMovementAction(String name, IGameEntityObject userEntity, float speed, float stepMomentum, float maxMomentum)
		throws SpartanException {

		super(name, userEntity);
		
		this.stepMomentum = stepMomentum;
		this.maxMomentum = maxMomentum;
		this.speed = speed;
		
		posState = (PositionalGameState) userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		properties = (PropertiesGameState)userEntity.getForm().getState(PropertiesGameState.class.getCanonicalName());
		newMomentum = true;
		
		momentums = new ArrayList<Momentum>();
	}

	List<Momentum> removals = new ArrayList<Momentum>();
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(gc.getInput().isKeyDown(fowardKey)){
			if(currentMomentum == null){
				currentMomentum = new Momentum();
				momentums.add(currentMomentum);
				if(properties != null){
					properties.addInt("FORWARD", 1);
				}
			}
			
			if(currentMomentum.currentMomentum < maxMomentum)
				currentMomentum.currentMomentum += delta * stepMomentum;
			
			if(currentMomentum.currentMomentum > maxMomentum)
				currentMomentum.currentMomentum = maxMomentum;
			
			currentMomentum.finalDirection = posState.getDirection().copy();

		}else{
			if(currentMomentum != null){
				currentMomentum = null;
			
				if(properties != null){
					properties.addInt("FORWARD", 0);
				}
			}
		}
		Vector2f finalDirection = new Vector2f(0,0);
		
		for(Momentum momentum : momentums){
			if(momentum != currentMomentum){
				if(momentum.currentMomentum > 0){
					momentum.currentMomentum -= delta * stepMomentum;
					
					if(momentum.currentMomentum < 0){
						removals.add(momentum);
					}
				}
			}
			
			float currentSpeed = momentum.currentMomentum * speed;
			finalDirection.x += momentum.finalDirection.getX() * currentSpeed;
			finalDirection.y += momentum.finalDirection.getY() * currentSpeed;
			//
		}
		
		for(Momentum momentum : removals)
			momentums.remove(momentum);
		
		posState.setPosition(posState.getX()+ finalDirection.getX() , posState.getY()+ finalDirection.getY() );
	}

	class Momentum{
		
		public float currentMomentum = 0;
		public boolean onMomentum = false;
		public Vector2f finalDirection;
	}
}
