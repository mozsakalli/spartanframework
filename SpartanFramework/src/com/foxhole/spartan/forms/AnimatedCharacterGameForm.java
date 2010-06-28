package com.foxhole.spartan.forms;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.PositionalGameState;


public class AnimatedCharacterGameForm extends BasicGameForm {

	String currentIdName;
	
	Map<String, Animation> animations;
	
	Map<String, ImageData> images;
	
	BasicGameForm currentGameForm;
	
	Image currentImage;
	Animation currentAnimation;
	
	public AnimatedCharacterGameForm(){
		super();
		
		animations = new HashMap<String, Animation>();
		images = new HashMap<String, ImageData>();
	}
	
	public void render(Graphics graphics) {
		
		if(currentImage != null) {
			currentImage.draw();
		}else if(currentAnimation != null){
			currentAnimation.draw();
		}
		
		//renderCollisionShape(graphics);
	}

	public void addAnimation(String animationName, Animation animation){
		animations.put(animationName, animation);
	}
	
	public final void addImage(String imageName, Image image){
		addImage(imageName, image, 0, 0);
	}
	
	public final void addImage(String imageName, Image image, int centerPosX, int centerPosY){
		images.put(imageName, new ImageData(image, centerPosX, centerPosY));
	}
	
	public void setAnimation(String animation){
		currentAnimation = animations.get(animation);
		
		Image initialFrame = currentAnimation.getImage(0);
		
		setBoundingShape(initialFrame.getWidth(), initialFrame.getHeight());
		
		currentImage = null;
	}
	
	private void setBoundingShape(int width, int height) {
		getPosition().setCollisionShape(new Rectangle(0, 0, width, height));
	}

	public void setImage(String image){
		
		ImageData data = images.get(image);
		
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

	public String getCurrentFormName() {
		return currentIdName;
	}
	
	private class ImageData{
		Image image;
		int centerPosX;
		int centerPosY;
		
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
