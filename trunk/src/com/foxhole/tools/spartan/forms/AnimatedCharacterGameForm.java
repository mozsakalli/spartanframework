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

package com.foxhole.tools.spartan.forms;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


/**
 * This form represents a fully animated character form.
 * With this 
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class AnimatedCharacterGameForm extends BasicGameForm {

	String currentIdName;
	
	Map<String, Animation> animations;
	
	Map<String, ImageData> images;
	
	BasicGameForm currentGameForm;
	
	Image currentImage;
	Animation currentAnimation;
	
	/**
	 * Constructs an Animated character game form
	 */
	public AnimatedCharacterGameForm(){
		super();
		
		animations = new HashMap<String, Animation>();
		images = new HashMap<String, ImageData>();
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.IRenderableSpartanObject#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics graphics) {
		
		if(currentImage != null) {
			currentImage.draw();
		}else if(currentAnimation != null){
			currentAnimation.draw();
		}
		
		//renderCollisionShape(graphics);
	}

	/**
	 * Adds a new animation for this form
	 * 
	 * @param animationName the name that will represent the animation
	 * @param animation a handle to the animation
	 */
	public void addAnimation(String animationName, Animation animation){
		animations.put(animationName, animation);
	}
	
	/**
	 * Adds an image to this form.
	 * 
	 * @param imageName the name that will represent the image
	 * @param image a handle to the image
	 */
	public final void addImage(String imageName, Image image){
		addImage(imageName, image, 0, 0);
	}
	
	/**
	 * Adds an image to this form.
	 * 
	 * @param imageName the name that will represent the image
	 * @param image a handle to the image
	 * @param centerPosX the x coordinate center of the image
	 * @param centerPosY the y coordinate center of the image
	 */
	public final void addImage(String imageName, Image image, int centerPosX, int centerPosY){
		images.put(imageName, new ImageData(image, centerPosX, centerPosY));
	}
	
	/**
	 * Sets the current animation to 
	 * 
	 * @param animationName the name that represents the animation
	 */
	public void setAnimation(String animationName){
		currentAnimation = animations.get(animationName);
		
		Image initialFrame = currentAnimation.getImage(0);
		
		setBoundingShape(initialFrame.getWidth(), initialFrame.getHeight());
		
		currentImage = null;
	}
	
	/**
	 * Sets the bounding shape of the form to a new one staring at position (0,0) 
	 * 
	 * @param width the new width of bounding box
	 * @param height the new height of bounding box
	 */
	private void setBoundingShape(int width, int height) {
		getPosition().setCollisionShape(new Rectangle(0, 0, width, height));
	}

	/**
	 * Sets the current image to the one referenced by the imageName parameter.
	 * If no image represents that name, nothing changes.
	 * 
	 * @param imageName the identifier of the image
	 */
	public void setImage(String imageName){
		
		ImageData data = images.get(imageName);
		
		if(data != null){
			currentImage = data.image;
			
			setBoundingShape(currentImage.getWidth(), currentImage.getHeight());
			
			getPosition().setCenterPosition(data.getCenterPosX(), data.getCenterPosY());
			
			currentAnimation = null;
		}
	}
	
	protected void updateBoundingShape(){
		getPosition().setCollisionShape(currentGameForm.getPosition().getCollisionShape());
	}

	/**
	 * Obtains current form identifier, this means any image or animation identifier.
	 * @return the identifier that represents what this form renders
	 */
	public String getCurrentFormName() {
		return currentIdName;
	}
	
	private class ImageData{
		private Image image;
		private int centerPosX;
		private int centerPosY;
		
		public ImageData(Image image, int centerPosX, int centerPosY){
			this.image = image;
			this.centerPosX = centerPosX;
			this.centerPosY = centerPosY;
		}
		
		public final Image getImage() {
			return image;
		}
		
		public final int getCenterPosX() {
			return centerPosX;
		}
		
		public final int getCenterPosY() {
			return centerPosY;
		}
	}
}
