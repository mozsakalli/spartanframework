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

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;

import com.foxhole.tools.spartan.exception.SpartanException;
import com.foxhole.tools.spartan.managers.ISpartanManager;
import com.foxhole.tools.spartan.managers.gamestate.GameStateManager;

/**
 * Input manager allows actions to register themselves and receive updates for the target input methods
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class InputManager implements ISpartanManager, InputListener {

	/**
	 * Obtains the current active instance of the render manager
	 * 
	 * @return a handle the current active instance of the render manager, null if no active instance is selected
	 */
	public static InputManager getInstance(){
		
		InputManager _theInstance = null;
		
		int currentId = GameStateManager.getInstance().getCurrentState();
		
		if( GameStateManager.getInstance().getStateManagers(currentId) != null){
			_theInstance = GameStateManager.getInstance().getStateManagers(currentId).getInputManager();
		}
		
		return _theInstance;
	}
	
	
	private List<ListenerLayer> listenerLayers = null;
	
	private ListenerLayer currentFocusLayer = null;
	
	/**
	 * Constructs an instance for the input manager
	 */
	public InputManager(){
		listenerLayers = new ArrayList<ListenerLayer>();
	}
	
	/**
	 * Pushes a new line of focus, saving the last one into a heap, think of this as the same 
	 * as the pushMatrix from OpenGL
	 */
	public void pushFocusLayer(){
		currentFocusLayer = new ListenerLayer();
		listenerLayers.add( currentFocusLayer );
	}
	
	/**
	 * Pops a line of focus from the input manager, removing the topmost line of the heap, 
	 *  think of this as the same as the popMatrix from OpenGL
	 */
	public void popFocusLayer(){
		if( listenerLayers.size() > 0 ){
			listenerLayers.remove(0);
			
			currentFocusLayer = (listenerLayers.size() == 0) ? null : listenerLayers.get(0);
		}
	}
	
	/**
	 * Adds a listener to the current focus
	 * 
	 * @param listener the listener to add
	 * @throws SpartanException if no focus line is created
	 */
	public void addFocus(ISpartanInputListener listener) throws SpartanException{
		
		if(currentFocusLayer == null){
			throw new SpartanException("No focus layer available");
		}
		
		if(listener instanceof ISpartanMouseListener){
			currentFocusLayer.addMouseListener( (ISpartanMouseListener)listener);
		}else if(listener instanceof ISpartanMouseListener){
			currentFocusLayer.addKeyListener( (ISpartanKeyListener)listener);
		}
	}
	
	/**
	 * Removes a listener to the current focus
	 * 
	 * @param listener the listener to remove
	 * @throws SpartanException if no focus line is created
	 */
	public void removeFocus(ISpartanInputListener listener) throws SpartanException{
		if(currentFocusLayer == null){
			throw new SpartanException("No focus line is available");
		}
		
		if(listener instanceof ISpartanMouseListener){
			currentFocusLayer.removeMouseListener( (ISpartanMouseListener)listener);
		}else if(listener instanceof ISpartanMouseListener){
			currentFocusLayer.removeKeyListener( (ISpartanKeyListener)listener);
		}
	}

	
	
	/* *************************************************************
	 * INPUT LISTENER CODE
	 */
	
	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseClicked(int, int, int, int)
	 */
	//@Override
	public void mouseClicked(int button, int x, int y, int clickCount) {
		if(currentFocusLayer != null){
			for( ISpartanMouseListener listener : currentFocusLayer.mouseListeners ){
				listener.mouseClicked(button, x, y, clickCount);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseMoved(int, int, int, int)
	 */
	//@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(currentFocusLayer != null){
			for( ISpartanMouseListener listener : currentFocusLayer.mouseListeners ){
				listener.mouseMoved(oldx, oldy, newx, newy);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mousePressed(int, int, int)
	 */
	//@Override
	public void mousePressed(int button, int x, int y) {
		if(currentFocusLayer != null){
			for( ISpartanMouseListener listener : currentFocusLayer.mouseListeners ){
				listener.mousePressed(button, x, y);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseReleased(int, int, int)
	 */
	//@Override
	public void mouseReleased(int button, int x, int y) {
		if(currentFocusLayer != null){
			for( ISpartanMouseListener listener : currentFocusLayer.mouseListeners ){
				listener.mouseReleased(button, x, y);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.MouseListener#mouseWheelMoved(int)
	 */
	//@Override
	public void mouseWheelMoved(int change) {
		if(currentFocusLayer != null){
			for( ISpartanMouseListener listener : currentFocusLayer.mouseListeners ){
				listener.mouseWheelMoved(change);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#inputEnded()
	 */
	//@Override
	public void inputEnded() {
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#isAcceptingInput()
	 */
	//@Override
	public boolean isAcceptingInput(){ 
		return true;
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.ControlledInputReciever#setInput(org.newdawn.slick.Input)
	 */
	//@Override
	public void setInput(Input input) {
		
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyPressed(int, char)
	 */
	//@Override
	public void keyPressed(int key, char c) {
		if(currentFocusLayer != null){
			for( ISpartanKeyListener listener : currentFocusLayer.keyListeners ){
				listener.keyPressed(key, c);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.newdawn.slick.KeyListener#keyReleased(int, char)
	 */
	//@Override
	public void keyReleased(int key, char c) {
		if(currentFocusLayer != null){
			for( ISpartanKeyListener listener : currentFocusLayer.keyListeners ){
				listener.keyReleased(key, c);
				
				if(listener.wasInputProcessed()){
					break;
				}
			}
		}
	}

	//@Override
	public void controllerButtonPressed(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerButtonReleased(int controller, int button) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerDownPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerDownReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerLeftPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerLeftReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerRightPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerRightReleased(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerUpPressed(int controller) {
		// TODO Auto-generated method stub
		
	}

	//@Override
	public void controllerUpReleased(int controller) {
		// TODO Auto-generated method stub
		
	}
	
	class ListenerLayer{
		List<ISpartanMouseListener> mouseListeners;
		List<ISpartanKeyListener> keyListeners;
		
		public ListenerLayer(){
			mouseListeners = new ArrayList<ISpartanMouseListener>();
			keyListeners = new ArrayList<ISpartanKeyListener>();
		}
		
		public void addMouseListener(ISpartanMouseListener listener){
			int priority = listener.getPriority();
			
			int insertionIdx = 0;
			
			for(ISpartanMouseListener currentListener : mouseListeners){
				if(currentListener.getPriority() < priority){
					break;
				}
				
				insertionIdx++;
			}
			
			mouseListeners.add(insertionIdx, listener);
		}
		
		public void removeMouseListener(ISpartanMouseListener listener){
			mouseListeners.remove(listener);
		}
		
		public void addKeyListener(ISpartanKeyListener listener){
			int priority = listener.getPriority();
			
			int insertionIdx = 0;
			
			for(ISpartanKeyListener currentListener : keyListeners){
				if(currentListener.getPriority() < priority){
					break;
				}
				
				insertionIdx++;
			}
			
			keyListeners.add(insertionIdx, listener);
		}
		
		public void removeKeyListener(ISpartanKeyListener listener){
			keyListeners.remove(listener);
		}
	}
}
