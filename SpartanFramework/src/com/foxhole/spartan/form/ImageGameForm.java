/* ************************************************************************************
 * Copyright (c) 2010, FoxholeStudios
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, 
 * are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list 
 * of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this 
 * list of conditions and the following disclaimer in the documentation and/or other 
 * materials provided with the distribution.
 * Neither the name of FoxholeStudios nor the names of its contributors may be used 
 * to endorse or promote products derived from this software without specific prior 
 * written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES 
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED 
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR 
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN 
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * ************************************************************************************/

package com.foxhole.spartan.form;


import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class ImageGameForm extends BasicGameForm {
	Image mesh;
	
	public ImageGameForm(Image image){
		super();
		
		mesh = image;
		
		getPosition().getArea();
	}
	
	public void render(Graphics graphics) {
		mesh.draw(-posState.getCenterposX(),-posState.getCenterposY());
		
		//graphics.setColor(Color.green);
		//graphics.drawString("A="+posState.getRotation() + " D=(" + posState.getDirection().getX() + ", " + posState.getDirection().getY() + ")", -posState.getCenterposX()+8.5f, -posState.getCenterposY()+13.5f);
//		graphics.drawLine(posState.getX()-posState.getCenterposX(),posState.getY()-posState.getCenterposY(),
//				( posState.getX()-posState.getCenterposX() + posState.getDirection().getX()*100 ), ( posState.getY()-posState.getCenterposY()+posState.getDirection().getY()*100));
		
		/*AABBCollisionGameState state = (AABBCollisionGameState)getState(AABBCollisionGameState.class.getCanonicalName());
		
		if(state != null){
			graphics.drawRect(state.getUpMostLeftPoint().x, state.getUpMostLeftPoint().y, state.getDownMostRightPoint().x - state.getUpMostLeftPoint().x , state.getDownMostRightPoint().y - state.getUpMostLeftPoint().y );
		}*/
	}



}
