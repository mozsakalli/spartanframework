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

package com.foxhole.tools.spartan.managers.gamestate;

import java.util.HashMap;
import java.util.Map;

import com.foxhole.tools.spartan.exception.SpartanException;

/**
 * Game state manager is the manager responsible for maintaning all the other managers within the same
 * game state.
 * 
 * When a user changes to a spartan game state(sgs), the sgs will enable its state in the state manager
 * and acess the correct managers.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class GameStateManager {
	private static GameStateManager _instance = new GameStateManager();
	
	/**
	 * Obtains the current active instance of the state manager
	 * 
	 * @return a handle the current active instance of the state manager, null if no active instance is selected
	 */
	public static GameStateManager getInstance(){
		return _instance;
	}
	
	private Map<Integer, ManagerGroup> stateGroups;
	private int currentStateName;
	
	private GameStateManager(){
		stateGroups = new HashMap<Integer, ManagerGroup>();
		currentStateName = -1;
	}
	
	/**
	 * Adds a new state, and therefore starting a new set of managers.
	 * 
	 * @param stateId an integer representing the new state
	 * @throws SpartanException if the state was already inserted
	 */
	public void addState(int stateId) throws SpartanException {
		if ( stateGroups.containsKey(stateId) ){
			throw new SpartanException("Double insertion of state " + stateId);
		}
		
		ManagerGroup group = new ManagerGroup();
		
		stateGroups.put(stateId, group);
	}
	
	/**
	 * Remove state from the state manager
	 * 
	 * @param stateId the integer of the state 
	 */
	public void removeState(int stateId){
		stateGroups.remove(stateId);
	}
	
	/**
	 * Sets the current state to the one defined by stateId
	 * 
	 * @param stateId an integer representing the target state
	 */
	public void setState(int stateId){
		if(stateGroups.containsKey( stateId )){
			currentStateName = stateId;
		}
	}
	
	/**
	 * Obtains the group of managers for the give stateId
	 * @param stateId an integer representing the target state
	 * @return a handle to the group of managers for the give stateId, null if no state is defined for stateId
	 */
	public ManagerGroup getStateManagers(int stateId){
		return stateGroups.get(stateId);
	}
	
	
	/**
	 * Obtains current state Id
	 * 
	 * @return an integer representing the current state
	 */
	public int getCurrentState() {
		return currentStateName;
	}
}
