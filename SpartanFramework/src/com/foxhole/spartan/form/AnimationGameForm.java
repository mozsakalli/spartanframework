package com.foxhole.spartan.form;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

public class AnimationGameForm extends BasicGameForm {

	private Animation animation;
	
	public AnimationGameForm(Animation animation){
		this.animation = animation;
	}
	
	public Animation getAnimation(){
		return animation;
	}
	
	public void render(Graphics graphics) {
		animation.draw();
	}

}
