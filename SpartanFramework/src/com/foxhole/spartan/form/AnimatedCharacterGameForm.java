package com.foxhole.spartan.form;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import com.foxhole.spartan.states.PositionalGameState;


public class AnimatedCharacterGameForm extends BasicGameForm {

	Map<String, AnimationGameForm> animations;
	
	Map<String, ImageGameForm> images;
	
	BasicGameForm currentGameForm;
	
	public AnimatedCharacterGameForm(){
		super();
		
		animations = new HashMap<String, AnimationGameForm>();
		images = new HashMap<String, ImageGameForm>();
	}
	
	public void render(Graphics graphics) {
		
		if(currentGameForm != null) {
		GL11.glPushMatrix();
		
		IFormPosition posState = currentGameForm.getPosition();
			
		if(posState != null){
			GL11.glRotatef(posState.getRotation(), 0, 0, 1);
			GL11.glScalef(posState.getScale(), posState.getScale(), 1);
			GL11.glTranslatef(posState.getX()-posState.getCenterposX(), posState.getY()-posState.getCenterposY(),0);
		}
			
		currentGameForm.render(graphics);
		
		GL11.glPopMatrix();
		
		}
		
		graphics.setColor(Color.green);
		//graphics.drawLine(0, 0, ( posState.getDirection().x*10 ), ( posState.getDirection().y*10));
		graphics.drawLine(posState.getCenterposX(), posState.getCenterposY(), ( posState.getCenterposX() + posState.getDirection().x*10 ), ( posState.getCenterposY() + posState.getDirection().y*10));
	}

	public void addAnimation(String animationName, AnimationGameForm form){
		animations.put(animationName, form);
	}
	
	public void addImage(String imageName, ImageGameForm  form){
		images.put(imageName, form);
	}
	
	public void setAnimation(String animation){
		AnimationGameForm form = animations.get(animation);
		
		if( form != null ){
			form.getAnimation().restart();
			currentGameForm = form;
		}
	}
	
	public void setImage(String image){
		ImageGameForm form = images.get(image);
		
		if(form != null){
			currentGameForm = form;
		}
	}
}
