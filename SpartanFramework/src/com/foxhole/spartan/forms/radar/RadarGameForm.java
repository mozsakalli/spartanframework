package com.foxhole.spartan.forms.radar;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.foxhole.spartan.forms.BasicGameForm;
import com.foxhole.spartan.forms.IGameFormObject;
import com.foxhole.spartan.spaces.VirtualGameSpace;
import com.foxhole.spartan.states.PositionalGameState;

public class RadarGameForm extends BasicGameForm {

	Image radarMesh;
	VirtualGameSpace friendlies;
	VirtualGameSpace enemies;
	int maxSizeX;
	int maxSizeY;
	
	int minSizeX;
	int minSizeY;
	int radarSizeX;
	int radarSizeY;
	
	List<VirtualSpace4Color> vsList;
	
	
	public RadarGameForm (Image radarImage, int minSizeX, int minSizeY, int maxSizeX, int maxSizeY, int radarSizeX, int radarSizeY){
		super();
		this.radarMesh = radarImage;
		this.maxSizeX = maxSizeX;
		this.maxSizeY = maxSizeY;
		
		this.minSizeX = minSizeX;
		this.minSizeY = minSizeY;
		this.radarSizeX = radarSizeX;
		this.radarSizeY = radarSizeY;
		
		vsList = new ArrayList<VirtualSpace4Color>();
	}
	
	public void addVirtualSpace(VirtualGameSpace vs, Color color){
		vsList.add(new VirtualSpace4Color(vs, color));
	}
	
	public void render(Graphics graphics) {
		
		
		for(VirtualSpace4Color vs4Color : vsList){
			for(IGameFormObject form : vs4Color.vs.getForms()){
				PositionalGameState formPosState = (PositionalGameState) form.getState(PositionalGameState.class.getCanonicalName());
				
				float pointX = formPosState.getX();
				float pointY = formPosState.getY();
				
				if( (pointX > minSizeX && pointX < maxSizeX) && (pointY > minSizeY || pointY < maxSizeY) ){
					
				float x = (formPosState.getX()/(maxSizeX-minSizeX)) * radarSizeX;
				float y = (formPosState.getY()/(maxSizeY-minSizeY)) * radarSizeY;
				
					graphics.setColor(vs4Color.color);
					graphics.drawRect(x, y, 1, 1);
				}
			}
		}
		radarMesh.draw(-posState.getCenterposX(),-posState.getCenterposY());
	}

	class VirtualSpace4Color{
		public VirtualSpace4Color(VirtualGameSpace vs, Color color){
			this.vs = vs;
			this.color = color;
		}
		public VirtualGameSpace vs;
		public Color color;
	}
}
