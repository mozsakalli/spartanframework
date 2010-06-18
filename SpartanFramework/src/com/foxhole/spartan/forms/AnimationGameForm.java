package com.foxhole.spartan.forms;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public class AnimationGameForm extends BasicGameForm {

	private Animation animation;
	
	public AnimationGameForm(Animation animation){
		this(animation, true, true);
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
