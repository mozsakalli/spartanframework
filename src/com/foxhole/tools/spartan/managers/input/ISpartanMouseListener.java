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

package com.foxhole.tools.spartan.managers.input;

/**
 * Mouse Listener interface
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public interface ISpartanMouseListener extends ISpartanInputListener {
	/**
	 * Notification that the mouse wheel position was updated
	 * 
	 * @param change The amount of the wheel has moved
	 */
	public abstract void mouseWheelMoved(int change);

	/**
	 * Notification that a mouse button was clicked. Due to double click
	 * handling the single click may be delayed slightly. For absolute notification
	 * of single clicks use mousePressed().
	 * 
	 * To be absolute this method should only be used when considering double clicks
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x The x position of the mouse when the button was pressed
	 * @param y The y position of the mouse when the button was pressed
	 * @param clickCount The number of times the button was clicked
	 */
	public abstract void mouseClicked(int button, int x, int y, int clickCount);

	/**
	 * Notification that a mouse button was pressed
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x The x position of the mouse when the button was pressed
	 * @param y The y position of the mouse when the button was pressed
	 */
	public abstract void mousePressed(int button, int x, int y);

	/**
	 * Notification that a mouse button was released
	 * 
	 * @param button The index of the button (starting at 0)
	 * @param x The x position of the mouse when the button was released
	 * @param y The y position of the mouse when the button was released
	 */
	public abstract void mouseReleased(int button, int x, int y);

	/**
	 * Notification that mouse cursor was moved
	 * 
	 * @param oldx The old x position of the mouse
	 * @param oldy The old y position of the mouse
	 * @param newx The new x position of the mouse
	 * @param newy The new y position of the mouse
	 */
	public abstract void mouseMoved(int oldx, int oldy, int newx, int newy);
	
	/**
	 * Notification that mouse cursor was dragged
	 * 
	 * @param oldx The old x position of the mouse
	 * @param oldy The old y position of the mouse
	 * @param newx The new x position of the mouse
	 * @param newy The new y position of the mouse
	 */
	public abstract void mouseDragged(int oldx, int oldy, int newx, int newy);
}
