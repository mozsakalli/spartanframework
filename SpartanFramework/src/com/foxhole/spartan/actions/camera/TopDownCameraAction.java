package com.foxhole.spartan.actions.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.form.IGameFormObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.PositionalGameState;

public class TopDownCameraAction extends BasicGameAction {

	PositionalGameState cameraPosState = null;
	
	float initialX;
	float initialY;
	float minX;
	float minY;
	float maxX;
	float maxY;
	float width;
	float height;
	
	float halfWidth;
	float halfHeight;
	
	public TopDownCameraAction(String name, IGameEntityObject cameraEntity, IGameEntityObject targetEntity, float initialX, float initialY, float minX, float minY, float maxX, float maxY, float width, float height)
			throws SpartanException {
		super(name, cameraEntity, targetEntity);
		
		cameraPosState = (PositionalGameState) userEntity.getForm().getState(PositionalGameState.class.getCanonicalName());
		
		this.initialX = initialX;
		this.initialY = initialY;
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.width = width;
		this.height = height;
		
		halfWidth = width/2;
		halfHeight = height/2;
		
		initialX = initialX > halfWidth ? initialX : halfWidth;
		initialY = initialY > halfHeight ? initialY : halfHeight;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		float xTranslaction = 0;
		float yTranslaction = 0;
		
		PositionalGameState targetPosState = null;
		IGameEntityObject currentTarget = this.targetEntities.get(0);
		while(currentTarget != null){
			targetPosState = (PositionalGameState) currentTarget.getForm().getState(PositionalGameState.class.getCanonicalName());
			
			xTranslaction += targetPosState.getX();
			yTranslaction += targetPosState.getY();
			
			currentTarget = currentTarget.getParent();
		}
		
		//System.out.println("Setting new cameraPos to (" + xTranslaction + ", " + yTranslaction+")");
		currentTarget = this.targetEntities.get(0);
		targetPosState = (PositionalGameState) currentTarget.getForm().getState(PositionalGameState.class.getCanonicalName());
		//System.out.println("targetPos to (" + targetPosState.getX() + ", " + targetPosState.getY()+")");
		
		float finalX = 0;
		if(xTranslaction < (minX+halfWidth))
			finalX = minX+halfWidth;
		else if(xTranslaction > (maxX-halfWidth) )
			finalX = maxX-halfWidth;
		else
			finalX = xTranslaction;
		
		float finalY = 0;
		if(yTranslaction < (minY+halfHeight))
			finalY = minY+gc.getHeight()/2;
		else if(yTranslaction > (maxY-halfHeight) )
			finalY = maxY-halfHeight;
		else
			finalY = yTranslaction;
		
		cameraPosState.setXY( halfWidth-finalX, halfHeight-finalY);
		
		VirtualGameSpace virtualSpace = userEntity.getForm().getVirtualSpace();
		for(IGameFormObject form : virtualSpace.getForms()){
			PositionalGameState posState = (PositionalGameState)form.getState(PositionalGameState.class.getCanonicalName());
			
			//System.out.println("Radar at: " + cameraPosState.getX() + ", " + cameraPosState.getY());
			posState.setXY(850 - cameraPosState.getX(), 600 - cameraPosState.getY());
		}
		
	}

}
