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

package com.foxhole.tools.spartan.states;

import java.util.HashMap;
import java.util.Map;

import com.foxhole.tools.spartan.states.AbstractGameState;


/**
 * The properties game state is a game state for gathering pair <name, value> properties.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class PropertiesGameState extends AbstractGameState {

	Map<String, String> values;
	
	/**
	 * Constructs a property game state
	 * 
	 * @param id the ID of the game state
	 */
	public PropertiesGameState(String id){
		super(id);
		values = new HashMap<String, String>();
	}
	
	/**
	 * Obtains a property in the string form
	 * 
	 * @param key the key of the property
	 * @return the value of the property in the string form, null if none is found
	 */
	public final String getString(String key){
		return values.get(key);
	}
	
	/**
	 * Adds a string to property list 
	 * 
	 * @param key the key of the property
	 * @param value the value of the property in the string form
	 */
	public final void addString(String key, String value){
		if(key != null && value != null)
			values.put(key, value);
	}
	
	/**
	 * Adds a float to property list 
	 * 
	 * @param key the key of the property
	 * @param value the value of the property in the float form
	 */
	public final void addFloat(String key, float value){
		addString(key, String.valueOf(value));
	}
	
	/**
	 * Obtains a property in the float form
	 * 
	 * @param key the key of the property
	 * @return the value of the property in the float form, null if not found
	 */
	public final Float getFloatValue(String key){
		String value = getString(key);
		
		if(value == null){
			return null;
		}
		
		return Float.valueOf( value );
	}
	
	/**
	 * Adds a int to property list 
	 * 
	 * @param key the key of the property
	 * @param value the value of the property in the int form
	 */
	public final void addInt(String key, int value){
		addString(key, String.valueOf(value));
	}
	
	/**
	 * Obtains a property in the int form
	 * 
	 * @param key the key of the property
	 * @return the value of the property in the int form, null if not found
	 */
	public final Integer getAddValue(String key){
		String value = getString(key);
		
		if(value == null){
			return null;
		}
		
		return Integer.valueOf( value );
	}
	
	/**
	 * Obtains a property in the int form, if no value is present the default value is assumed
	 * 
	 * @param key the key of the property
	 * @param defaultValue the default value
	 * @return the value of the property in the int form, null if not found
	 */
	public final Integer getIntValue(String key, int defaultValue){
		String value = getString(key);
		
		return (value == null) ? defaultValue : Integer.valueOf( value );
	}
	
	/**
	 * reset game state
	 */
	public final void reset(){
		values.clear();
	}
}
