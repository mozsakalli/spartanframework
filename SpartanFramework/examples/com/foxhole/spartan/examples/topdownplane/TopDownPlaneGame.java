package com.foxhole.spartan.examples.topdownplane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.movement.ForwardMovementAction;
import com.foxhole.spartan.actions.movement.RotationalMovementAction;
import com.foxhole.spartan.entity.GameEntity;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.forms.ImageGameForm;
import com.foxhole.spartan.managers.ActionManager;
import com.foxhole.spartan.managers.RenderManager;
import com.foxhole.spartan.spaces.VirtualGameSpace;

public class TopDownPlaneGame extends BasicGame {

	ActionManager actionManager;
	RenderManager renderManager;
	
	public TopDownPlaneGame() {
		super("Example 1 : Top Down Plane");
		
		actionManager = new ActionManager();
		renderManager = new RenderManager();
	}
	
	
	@Override
	public void init(GameContainer container) throws SlickException {
		// create the virtualspace in which to render
		
		VirtualGameSpace background = renderManager.addNewVirtualGameSpace("background");
		VirtualGameSpace action = renderManager.addNewVirtualGameSpace("action");
		
		// create the objects to render
		IGameEntityObject backgroundEntity = new GameEntity("background");
		ImageGameForm backgroundForm = new ImageGameForm(new Image("testdata/background.jpg"));
		backgroundEntity.setForm(backgroundForm);
		
		background.addForm(backgroundForm);
		
		// create the plane
		IGameEntityObject planeEntity = new GameEntity("plane");
		ImageGameForm planeForm = new ImageGameForm(new Image("testdata/plane.png"));
		
		planeForm.getPosition().setCenterPosition(61, 60);
		planeForm.getPosition().setXY(container.getWidth()/2+planeForm.getPosition().getCenterposX(), 
				container.getHeight()/2+planeForm.getPosition().getCenterposY());
		planeForm.getPosition().setDirection(0, -1);
		
		planeEntity.setForm(planeForm);
		
		
		action.addForm(planeForm);
		
		//actions
		try {
			RotationalMovementAction raction = new RotationalMovementAction(RotationalMovementAction.class.getCanonicalName(), planeEntity);
			actionManager.registerAction(raction);
			
			ForwardMovementAction faction = new ForwardMovementAction(ForwardMovementAction.class.getCanonicalName(), planeEntity, 0.3f);
			actionManager.registerAction(faction);
			
		} catch (SpartanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		actionManager.updateActions(gc, null, delta);
	}

	public void render(GameContainer gc, Graphics graphics) throws SlickException {
		renderManager.render(gc, null, graphics);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TopDownPlaneGame());
		
		app.setDisplayMode(800, 600, false);
		
		app.start();
	}
}
