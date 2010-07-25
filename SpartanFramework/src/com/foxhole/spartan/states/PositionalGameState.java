/* ************************************************************************************
 * Copyright (c) 2010, FoxholeStudios
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list 
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this 
 * list of conditions and the following disclaimer in the documentation and/or other 
 * materials provided with the distribution.
 * Neither the name of FoxholeStudios nor the names of its contributors may be used 
 * to endorse or promote products derived from this software without specific prior 
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * ************************************************************************************/

package com.foxhole.spartan.states;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import com.foxhole.spartan.forms.IFormPosition;
import com.foxhole.spartan.managers.RenderManager;

public class PositionalGameState extends AbstractGameState implements IFormPosition {

	private float xPos;
	private float yPos;
	
	private float rotation;
	private float scale;
	
	private float xCenterPos;
	private float yCenterPos;
	
	private Vector2f direction;
	float differenceAngle;
	Shape collisionArea;
	Shape updatedCollisionArea;
	boolean dirty;
	
	public PositionalGameState(){
		this(PositionalGameState.class.getCanonicalName());
		dirty = true;
	}
	public PositionalGameState(String id){
		super(id);
		
		direction = new Vector2f();
		
		setDirection(0, 0);
		dirty = true;
	}
	
	public PositionalGameState(String id, float xDirection, float yDirection){
		super(id);
		
		direction = new Vector2f(xDirection,yDirection);
		dirty = true;
	}
	
	public final Vector2f getDirection(){
		return direction;
	}
	
	public final void setDirection(float angle){
		differenceAngle = angle;
		dirty = true;
	}
	
	public final void setDirection(float xDirection, float yDirection){
		direction.set(xDirection,yDirection);
		
		// calculate the angle
		if(xDirection == 0 && yDirection == 0)
			differenceAngle = 0;
		else{
			differenceAngle = (float) java.lang.Math.toDegrees( java.lang.Math.acos( 
					xDirection / java.lang.Math.sqrt( (double)(xDirection * xDirection + yDirection * yDirection) )) - rotation );
			
		}
		//System.out.println("difference angle: " + differenceAngle);
		//updateDirection();
		dirty = true;
	}
	
	public final void setXY(float x, float y){
		setX(x);
		setY(y);
	}
	
	public final float getX(){
		return xPos;
	}
	
	public final float getY(){
		return yPos;
	}
	
	public final void setX(float x){
		xPos = x;
		dirty = true;
	}
	
	public final void setY(float y){
		yPos = y;
		dirty = true;
	}
	
	public final float getRotation(){
		return rotation;
	}
	
	public final void setRotation(float rotation){
		this.rotation = rotation;
		this.rotation %= 360;
		
		updateDirection();
	}
	
	public final void addRotation(float rotation){
		this.rotation += rotation;
		this.rotation %= 360;
		if(this.rotation < 0)
			this.rotation = 360 + this.rotation;
		updateDirection();
	}
	
	private final void updateDirection( ){
		
		direction.set( (float)FastTrig.cos( java.lang.Math.toRadians( rotation - differenceAngle) ), 
					   (float)FastTrig.sin( java.lang.Math.toRadians( rotation - differenceAngle) ) );
		dirty = true;
	}
	
	public final float getScale(){
		return scale;
	}
	
	public final void setScale(float scale){
		this.scale = scale;
		dirty = true;
	}

	public final void addScale(float scale){
		this.scale += scale;
		dirty = true;
	}
	
	public final void setCenterPosition(float x, float y){
		xCenterPos = x;
		yCenterPos = y;
		dirty = true;
	}
	
	public final float getCenterposX(){
		return xCenterPos;
	}
	
	public final float getCenterposY(){
		return yCenterPos;
	}

	public final float getDifferenceAngle() {
		return differenceAngle;
	}

	@Override
	public Shape getCollisionShape() {
		if(collisionArea != null && dirty){
			updateCollisionArea();
		}
		return updatedCollisionArea;
	}
	
	public void setCollisionShape(Shape shape) {
		collisionArea = shape;
		updateCollisionArea();
	}
	
	private final void updateCollisionArea() {
		if(collisionArea != null){
			updatedCollisionArea = collisionArea.transform(Transform.createScaleTransform(scale, scale));
			updatedCollisionArea = updatedCollisionArea.transform(Transform.createTranslateTransform(-getCenterposX(), -getCenterposY()));
			updatedCollisionArea = updatedCollisionArea.transform(Transform.createRotateTransform((float) Math.toRadians(getRotation())));
			updatedCollisionArea = updatedCollisionArea.transform(Transform.createTranslateTransform(getX(), getY()));
			//collisionArea.setLocation(getX()-getCenterposX(), getY()-getCenterposY());
			
			
			dirty = false;
		}
		
	}
}
