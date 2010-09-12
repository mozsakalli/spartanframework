package com.foxhole.tools.spartan.forms;

import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

/**
 * The interface that describes the position data for a form.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public interface IFormPosition {
	/**
	 * Obtains the direction for the given element- 
	 * 
	 * @return A vector with a normalized direction
	 */
	public Vector2f getDirection();
	
	/**
	 * Sets the direction of this object.
	 *  
	 * @param direction a normalized vector
	 */
	public void setDirection(Vector2f direction);
	
	/**
	 * Sets the direction of this object.
	 * 
	 * @param xDirection the X Position for the direction
	 * @param yDirection the Y Position for the direction
	 */
	public void setDirection(float xDirection, float yDirection);
	
	/**
	 * Sets the position for this object.
	 * 
	 * @param position a vector2f with the x,y position. 
	 */
	public void setPosition(Vector2f position);
	
	/**
	 * Sets the position for this object.
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void setPosition(float x, float y);
	
	/**
	 * Obtains the X coordinate from the position
	 * 
	 * @return a float representing the X coordinate
	 */
	public float getX();
	
	/**
	 * Obtains the Y coordinate from the position
	 * 
	 * @return a float representing the Y coordinate
	 */
	public float getY();
	
	/**
	 * Sets the position X coordinate 
	 * 
	 * @param x a float with the new X coordinate value
	 */
	public void setX(float x);
	
	/**
	 * Sets the position Y coordinate 
	 * 
	 * @param y a float with the new Y coordinate value
	 */
	public void setY(float y);
	
	/**
	 * Obtains the value of the objects rotation, in degrees
	 * 
	 * @return a float between 0 and 360 representing the rotation in degrees
	 */
	public float getRotation();
	
	/**
	 * Sets the actual rotation of the object, the value passed is expected to be in degrees
	 * 
	 * @param rotation a float representing the rotation value in degrees 
	 */
	public void setRotation(float rotation);
	
	/**
	 * Adds an amount of rotation to the current rotation
	 * 
	 * @param rotation a float representing the amount of rotation value in degrees to add
	 */
	public void addRotation(float rotation);
	
	/**
	 * Obtains the object scale. 1x equals 1.0f 2x equals 2.0f
	 * 
	 * @return a float representing the amount of scale of the object
	 */
	public float getScale();
	
	/**
	 * Sets the scale to this new value
	 * 
	 * @param scale a float representing the new scale value
	 */
	public void setScale(float scale);
	
	/**
	 * Adds an amount of scale to the current scale
	 * 
	 * @param scale a float representing the amount of scale to add on top of the current scale
	 */
	public void addScale(float scale);
	
	/**
	 * Sets the center position of the object. Add rotations and scales use this point as reference.
	 * The coordinates for this point refer to the coordinates (0,0) of the form, so in order to put
	 * the center of rotation on a image with 200 width and 100 height, the center of rotation should be
	 * (100, 50). Even thought the position of the image form might change the center of rotation wont.
	 *  
	 * @param centerPosition a vector containing the x,y coordinate of the center position point.
	 */
	public void setCenterPosition(Vector2f centerPosition);
	
	/**
	 * Sets the center position of the object. Add rotations and scales use this point as reference.
	 * The coordinates for this point refer to the coordinates (0,0) of the form, so in order to put
	 * the center of rotation on a image with 200 width and 100 height, the center of rotation should be
	 * (100, 50). Even thought the position of the image form might change the center of rotation wont.
	 *  
	 * @param x a float containing the X coordinate of the center position point.
	 * @param y a float containing the Y coordinate of the center position point.
	 */
	public void setCenterPosition(float x, float y);
	
	/**
	 * Obtains the value of the center position's X coordinate
	 * 
	 * @return a float containing the value of the X coordinate
	 */
	public float getCenterposX();
	
	/**
	 * Obtains the value of the center position's Y coordinate
	 * 
	 * @return a float containing the value of the Y coordinate
	 */
	public float getCenterposY();
	
	/**
	 * Obtains the difference angle between the rotation and the direction.
	 * 
	 * @return a float with the difference angle in degrees
	 * @deprecated no need for this method
	 */
	public float getDifferenceAngle();
	
	// collision
	
	/**
	 * Obtains the collision shape for this object.
	 * The collision shape is dynamic and rotates, scales and translates along with the form. 
	 * 
	 * @return a Slick2d Shape representing the shape
	 */
	public Shape getCollisionShape();
	
	/**
	 * Sets a new collision shape for this form
	 * 
	 * @param shape a Slick2d Shape representing the new shape
	 */
	public void setCollisionShape(Shape shape);
}
