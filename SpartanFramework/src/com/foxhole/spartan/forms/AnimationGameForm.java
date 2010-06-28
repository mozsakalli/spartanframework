package com.foxhole.spartan.forms;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

public class AnimationGameForm extends BasicGameForm {

	private Animation animation;
	
	public AnimationGameForm(Animation animation){
		this(animation, true, true);
		
		Rectangle collisionShape = new Rectangle(0, 0, animation.getWidth(), animation.getHeight());
		
		getPosition().setCollisionShape(collisionShape);
	}
	
	public AnimationGameForm(Animation animation, boolean autoUpdate, boolean looping){
		this.animation = animation;
		animation.restart();
		animation.setAutoUpdate(autoUpdate);
		animation.setLooping(looping);
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public void render(Graphics graphics) {
		animation.draw();
	}

}
