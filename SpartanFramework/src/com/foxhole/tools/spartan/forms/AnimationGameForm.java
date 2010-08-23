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

package com.foxhole.tools.spartan.forms;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * The animation game form performs a simple animation using slick 2d animation tools.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class AnimationGameForm extends BasicGameForm {

	private Animation animation;
	
	/**
	 * Constructs an animationGameForm, the animations should auto update and loop by default
	 * 
	 * @param animation a slick2d animation 
	 */
	public AnimationGameForm(Animation animation){
		this(animation, true, true);
		
		Rectangle collisionShape = new Rectangle(0, 0, animation.getWidth(), animation.getHeight());
		
		getPosition().setCollisionShape(collisionShape);
	}
	
	/**
	 * Constructs an animationGameForm
	 * 
	 * @param animation a slick2d animation 
	 * @param autoUpdate is the animations should auto update
	 * @param looping true if it should loop, false if not
	 */
	public AnimationGameForm(Animation animation, boolean autoUpdate, boolean looping){
		this.animation = animation;
		animation.restart();
		animation.setAutoUpdate(autoUpdate);
		animation.setLooping(looping);
	}
	
	/**
	 * Obtains the animation
	 * 
	 * @return a slick2d animation 
	 */
	public Animation getAnimation(){
		return animation;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.IRenderableSpartanObject#render(org.newdawn.slick.Graphics)
	 */
	public void render(Graphics graphics) {
		animation.draw();
	}

}
