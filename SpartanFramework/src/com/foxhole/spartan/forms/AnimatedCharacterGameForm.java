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
	
	Map<String, Image> images;
	
	BasicGameForm currentGameForm;
	
	Image currentImage;
	Animation currentAnimation;
	
	public AnimatedCharacterGameForm(){
		super();
		
		animations = new HashMap<String, Animation>();
		images = new HashMap<String, Image>();
	}
	
	public void render(Graphics graphics) {
		
		if(currentImage != null) {
			currentImage.draw();
		}else if(currentAnimation != null){
			currentAnimation.draw();
		}
		
		renderCollisionShape(graphics);
	}

	public void addAnimation(String animationName, Animation animation){
		animations.put(animationName, animation);
	}
	
	public void addImage(String imageName, Image image){
		images.put(imageName, image);
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
		currentImage = images.get(image);
		
		setBoundingShape(currentImage.getWidth(), currentImage.getHeight());
		
		currentAnimation = null; 
	}
	
	protected void updateBoundingShape(){
		getPosition().setCollisionShape(currentGameForm.getPosition().getCollisionShape());
	}

	public String getCurrentFormName() {
		return currentIdName;
	}
	
	
}
