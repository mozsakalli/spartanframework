package com.foxhole.tools.spartan.managers.collision;

import com.foxhole.tools.spartan.forms.IGameFormObject;

public interface ICollisionHandler {
	public void performCollision(IGameFormObject firstForm, IGameFormObject secondForm);
	
	public int getFirstCollisionType();
	
	public int getSecondCollisionType();
}
