package com.foxhole.spartan.managers.collision;

import com.foxhole.spartan.forms.IGameFormObject;

public interface ICollisionHandler {
	public void performCollision(IGameFormObject firstForm, IGameFormObject secondForm);
	
	public int getFirstCollisionType();
	
	public int getSecondCollisionType();
}
