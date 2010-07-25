package com.foxhole.spartan.actions.util;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.Transition;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.actions.IGameActionObject;
import com.foxhole.spartan.exception.SpartanException;

public class EnterGameStateOnActionOver extends BasicGameAction {

	int targetStateId;
	IGameActionObject targetAction;
	Transition leave;
	Transition enter;
	
	public EnterGameStateOnActionOver(String name, IGameActionObject targetAction, int targetStateId, Transition leave, Transition enter) throws SpartanException {
		super(name);
		
		this.targetAction = targetAction;
		this.targetStateId = targetStateId;
		
		this.leave = leave;
		this.enter = enter;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		if(targetAction.isOver()){
			if(leave == null || enter == null)
				sbg.enterState(targetStateId);
			else
				sbg.enterState(targetStateId, leave, enter);
		}
	}

}
