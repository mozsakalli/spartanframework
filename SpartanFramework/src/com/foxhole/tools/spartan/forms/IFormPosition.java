package com.foxhole.tools.spartan.forms;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public interface IFormPosition {
	public Vector2f getDirection();
	
	public void setDirection(float xDirection, float yDirection);
	
	public void setXY(float x, float y);
	
	public float getX();
	
	public float getY();
	
	public void setX(float x);
	
	public void setY(float y);
	
	public float getRotation();
	
	public void setRotation(float rotation);
	
	public void addRotation(float rotation);
	
	public float getScale();
	
	public void setScale(float scale);
	
	public void addScale(float scale);
	
	public void setCenterPosition(float x, float y);
	
	public float getCenterposX();
	
	public float getCenterposY();
	
	public float getDifferenceAngle();
	
	// collision
	
	public Shape getCollisionShape();
	
	public void setCollisionShape(Shape shape);
}
