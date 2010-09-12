package com.foxhole.spartan.examples.topdownplane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.tools.spartan.exception.SpartanException;

public class TopDownPlaneGame extends StateBasedGame {

	public TopDownPlaneGame() {
		super("Example 1 : Plane Tutorial");
	}
	
	public void initStatesList(GameContainer gc) throws SlickException {
		try {
			addState(new BattleZoneState());
		} catch (SpartanException e) {
			throw new SlickException("Could not create BattleZoneState", e);
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new TopDownPlaneGame());
		
		app.setDisplayMode(800, 600, false);
		
		app.start();
	}
}
