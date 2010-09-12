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

package com.foxhole.tools.spartan.states;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.FastTrig;

import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.forms.IFormPosition;
import com.foxhole.tools.spartan.utils.SpartanUtils;

/**
 * The game state that represents the entire spectrum of transformations that an form might
 * possess. It also contains the Shape used for for collision checking.
 * 
 * <p>Initialization by XML:
 * <table border="1">
 * <tr><td>Name</td><td>String Form</td><td>Description</td><td>Example</td></tr>
 * <tr><td>IDENTIFIER</td><td>[String]</td><td>A string representing the identifier of the state</td><td>PositionalGameState</td></tr>
 * <tr><td>POSITION</td><td>[float],[float]</td><td>An float Tuple separated by a ','</td><td>3.0,2.1</td></tr>
 * <tr><td>ROTATION</td><td>[float]</td><td>A single float representing an angle in degrees</td><td>35.1</td></tr>
 * <tr><td>SCALE</td><td>[float]</td><td>A single float representing the scale of the object</td><td>2.0</td></tr>
 * <tr><td>CENTER_POSITION</td><td>X,Y</td><td>An float Tuple separated by a ','</td><td>3.0,2.1</td></tr>
 * <tr><td>DIRECTION</td><td>X,Y</td><td>An float Tuple separated by a ','</td><td>3.0,2.1</td></tr>
 * <tr><td>COLLISION_AREA</td><td>[float],[float];[float],[float];...;[float],[float]</td><td>A list of at least three float value tuples</td><td>0f,0f;1f,0f;1f,1f;0f,1f;</td></tr>
 * </table>
 * </p>
 * 
 * @author Tiago "Spiegel" Costa
 */
public class PositionalGameState extends AbstractGameState implements IFormPosition {

	protected static String POSITION 		= "POSITION";
	protected static String ROTATION 		= "ROTATION";
	protected static String SCALE 			= "SCALE";
	protected static String CENTER_POSITION = "CENTER POSITION";
	protected static String DIRECTION		= "DIRECTION";
	protected static String COLLISION_AREA	= "COLLISION AREA";

	/**
	 * Initializes an object by a set of parameters
	 * 
	 * @param stateDetails The parameters to use for initialization
	 * @return An initialized PositionalGameState in the form of an IGameStateObject 
	 * @throws SpartanException When an error occurs
	 */
	public static IGameStateObject initialize(Map<String, String> stateDetails) throws SpartanException{
		PositionalGameState state = null;

		if(stateDetails == null){
			throw new SpartanException("Invalid data for " + PositionalGameState.class.getCanonicalName() + " initialization." );
		}
		
		// IDENTIFIER
		String id = stateDetails.get(IDENTIFIER);
		if (id == null){
			id = PositionalGameState.class.getCanonicalName();
		}
		
		state = new PositionalGameState(id);
		
		// POSITION
		Vector2f position = SpartanUtils.getVectorFromString( stateDetails.get(POSITION) );
		state.setPosition(position);
		
		// ROTATION
		float rotation = SpartanUtils.getFloatFromString( stateDetails.get(ROTATION) );
		state.setScale(rotation);
		
		// SCALE
		float scale = SpartanUtils.getFloatFromString( stateDetails.get(SCALE) );
		state.setScale(scale);
		
		// CENTER_POSITION
		Vector2f centerPosition = SpartanUtils.getVectorFromString( stateDetails.get(CENTER_POSITION) );
		state.setCenterPosition(centerPosition);
		
		// DIRECTION
		Vector2f direction = SpartanUtils.getVectorFromString( stateDetails.get(DIRECTION) );
		state.setDirection(direction);
		
		// COLLISION_AREA
		Shape shape = SpartanUtils.getShapeFromString( stateDetails.get(COLLISION_AREA) );
		state.setCollisionShape(shape);
		
		return state;
	}

	public static List<String> getStateDetails(){
		ArrayList<String> list = new ArrayList<String>();

		list.add(IDENTIFIER);
		list.add(POSITION);
		list.add(ROTATION);
		list.add(SCALE);
		list.add(CENTER_POSITION);
		list.add(DIRECTION);
		list.add(COLLISION_AREA);

		return list;
	}


	private float xPos;
	private float yPos;

	private float rotation;
	private float scale;

	private float xCenterPos;
	private float yCenterPos;

	private Vector2f direction;
	private float differenceAngle;
	private Shape collisionArea;
	private Shape updatedCollisionArea;
	private boolean dirty;

	/**
	 * Creates a positional game state using the class canonical name as the gamestate Identifier
	 */
	public PositionalGameState(){
		this(PositionalGameState.class.getCanonicalName());
		dirty = true;
	}
	
	
	/**
	 * Creates a positional game state
	 * 
	 * @param id the game state Identifier
	 */
	public PositionalGameState(String id){
		super(id);

		direction = new Vector2f();

		setDirection(0, 0);
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getDirection()
	 */
	public final Vector2f getDirection(){
		return direction;
	}

	/**
	 * Sets the direction of the game state
	 * 
	 * @param angle the angle in degrees of the direction
	 */
	public final void setDirection(float angle){
		differenceAngle = angle;
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setDirection(org.newdawn.slick.geom.Vector2f)
	 */
	public final void setDirection(Vector2f direction){
		setDirection(direction.x, direction.y);
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setDirection(float, float)
	 */
	public final void setDirection(float xDirection, float yDirection){
		direction.set(xDirection,yDirection);

		// calculate the angle
		if(xDirection == 0 && yDirection == 0)
			differenceAngle = 0;
		else{
			differenceAngle = (float) java.lang.Math.toDegrees( java.lang.Math.acos( 
					xDirection / java.lang.Math.sqrt( (double)(xDirection * xDirection + yDirection * yDirection) )) - rotation );

		}
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setPosition(float, float)
	 */
	public final void setPosition(float x, float y){
		setX(x);
		setY(y);
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setPosition(org.newdawn.slick.geom.Vector2f)
	 */
	public final void setPosition(Vector2f position){
		setX(position.x);
		setY(position.y);
	}

	/**
	 * Obtains the position
	 * 
	 * @return a vector representation of the position
	 */
	public final Vector2f getPosition(){
		return new Vector2f(xPos, yPos);
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getX()
	 */
	public final float getX(){
		return xPos;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getY()
	 */
	public final float getY(){
		return yPos;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setX(float)
	 */
	public final void setX(float x){
		xPos = x;
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setY(float)
	 */
	public final void setY(float y){
		yPos = y;
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getRotation()
	 */
	public final float getRotation(){
		return rotation;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setRotation(float)
	 */
	public final void setRotation(float rotation){
		this.rotation = rotation;
		this.rotation %= 360;

		updateDirection();
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#addRotation(float)
	 */
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

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getScale()
	 */
	public final float getScale(){
		return scale;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setScale(float)
	 */
	public final void setScale(float scale){
		this.scale = scale;
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#addScale(float)
	 */
	public final void addScale(float scale){
		this.scale += scale;
		dirty = true;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setCenterPosition(float, float)
	 */
	public final void setCenterPosition(float x, float y){
		xCenterPos = x;
		yCenterPos = y;
		dirty = true;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setCenterPosition(org.newdawn.slick.geom.Vector2f)
	 */
	public final void setCenterPosition(Vector2f centerPosition){
		setCenterPosition(centerPosition.x, centerPosition.y);
	}

	/**
	 * Obtains the center position
	 *  
	 * @return a vector representation of the center position
	 */
	public Vector2f getCenterPosition(){
		return new Vector2f(xCenterPos, yCenterPos);
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getCenterposX()
	 */
	public final float getCenterposX(){
		return xCenterPos;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getCenterposY()
	 */
	public final float getCenterposY(){
		return yCenterPos;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getDifferenceAngle()
	 */
	public final float getDifferenceAngle() {
		return differenceAngle;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#getCollisionShape()
	 */
	//@Override
	public Shape getCollisionShape() {
		if(collisionArea != null && dirty){
			updateCollisionArea();
		}
		return updatedCollisionArea;
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.forms.IFormPosition#setCollisionShape(org.newdawn.slick.geom.Shape)
	 */
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
