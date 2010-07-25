package com.foxhole.tools.spartan.forms.ui;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.TextField;

import com.foxhole.tools.spartan.forms.BasicGameForm;
import com.foxhole.tools.spartan.states.PropertiesGameState;

public class TextFieldForm extends BasicGameForm {

	TextField textField;
	GameContainer container;
	private Font font;
	String message;
	
	public TextFieldForm(GameContainer container, int x, int y, int width, int height, Font font){
		this.font = font;
		textField = new TextField(container, font, x,y,width,height,new ComponentListener() {
			
			public void componentActivated(AbstractComponent source) {
				message = textField.getText();
				
				PropertiesGameState propState = (PropertiesGameState)getState(PropertiesGameState.class.getCanonicalName());
				propState.addString("textField", message);
			}
		});
	}
	
	@Override
	public void render(Graphics graphics) {
		textField.render(container, graphics);
	}
}
