package com.foxhole.tools.spartan.actions.camera;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.actions.BasicGameAction;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.render.RenderManager;
import com.foxhole.tools.spartan.states.PositionalGameState;

public class TopDownCameraAction extends BasicGameAction {

	private Vector2f minimumPoint;
	private Vector2f maximumPoint;
	
	public TopDownCameraAction(String name, 
			IGameEntityObject targetEntity, 
			Vector2f minimumPoint,
			Vector2f maximumPoint)
			throws SpartanException {
		super(name, targetEntity);
		
		this.maximumPoint = maximumPoint;
		this.minimumPoint = minimumPoint;
	}
	
	public void setTargetEntity(IGameEntityObject targetEntity){
		targetEntities.clear();
		
		userEntity = targetEntity;
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		float xTranslaction = 0;
		float yTranslaction = 0;
		
		float halfWidth = gc.getWidth()/2;
		float halfHeight = gc.getHeight()/2;
		
		PositionalGameState targetPosState = null;
		IGameEntityObject currentTarget = userEntity;
		float finalX = 0;
		float finalY = 0;
		if(currentTarget != null){
			
		while(currentTarget != null){
			targetPosState = (PositionalGameState) currentTarget.getForm().getState(PositionalGameState.class.getCanonicalName());
			
			xTranslaction += targetPosState.getX();
			yTranslaction += targetPosState.getY();
			
			currentTarget = currentTarget.getParent();
		}
		
		
		if(xTranslaction < (minimumPoint.x+halfWidth))
			finalX = minimumPoint.x+halfWidth;
		else if(xTranslaction > (maximumPoint.x-halfWidth) )
			finalX = maximumPoint.x-halfWidth;
		else
			finalX = xTranslaction;
		
		
		if(yTranslaction < (minimumPoint.y+halfHeight))
			finalY = minimumPoint.y+gc.getHeight()/2;
		else if(yTranslaction > (maximumPoint.y-halfHeight) )
			finalY = maximumPoint.y-halfHeight;
		else
			finalY = yTranslaction;
		}else{
			finalX = xTranslaction > halfWidth ? xTranslaction : halfWidth;
			finalY = yTranslaction > halfHeight ? yTranslaction : halfHeight;
		}
		
		RenderManager.getInstance().translate((halfWidth-finalX), (halfHeight-finalY));
		/*
		gc.getGraphics().clearWorldClip();
		gc.getGraphics().setWorldClip(
				-(int)SpartanFramework.getActiveRenderManager().translationX-50,
				-(int)SpartanFramework.getActiveRenderManager().translationY-50,
				gc.getWidth()+100,
				gc.getHeight()+100);
				*/
	}

}
