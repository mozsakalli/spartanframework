package com.foxhole.spartan.form.camera;

import org.newdawn.slick.Graphics;

import com.foxhole.spartan.form.BasicGameForm;

public class TopDownCameraForm extends BasicGameForm {

	public TopDownCameraForm(){
	}
	
	public void render(Graphics graphics) {
		graphics.translate(this.getPosition().getX(),this.getPosition().getY());
	}

}
