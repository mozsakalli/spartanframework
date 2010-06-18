package com.foxhole.spartan.forms.camera;

import org.newdawn.slick.Graphics;

import com.foxhole.spartan.forms.BasicGameForm;
import com.foxhole.spartan.managers.RenderManager;
import com.foxhole.spartan.states.PropertiesGameState;

public class TopDownCameraForm extends BasicGameForm {

	PropertiesGameState ps;
	
	public TopDownCameraForm(){
		ps = (PropertiesGameState) getState(PropertiesGameState.class.getCanonicalName());
		
		if(ps == null){
			ps = new PropertiesGameState(PropertiesGameState.class.getCanonicalName());
			addState(ps);
		}
	}
	
	public void render(Graphics graphics) {
		int x = ps.getIntValue("CAMERA_X");
		int y = ps.getIntValue("CAMERA_Y");
		
		graphics.translate(x,y);
		
		RenderManager.translationX += x;
		RenderManager.translationY += y;
	}

}
