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

package com.foxhole.tools.spartan.spaces;

import java.util.LinkedList;
import java.util.List;

import com.foxhole.tools.spartan.ISpartanObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;
import com.foxhole.tools.spartan.forms.IGameFormObject;


/**
* The vitual gamespace is an form oriented implementation of a gamespace.
 * This gamespace will only serve to hold forms showing a set of virtual representations (forms), 
 * hence the virtual part of the name. 
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class VirtualGameSpace implements IGameSpaceObject {

	private List<IGameFormObject> formList;
	private String name;
	
	/**
	 * Constructs a virtual game space
	 * 
	 * @param name name of the game space
	 */
	public VirtualGameSpace(String name){
		this.setName(name);
		reset();
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#addObject(com.foxhole.tools.spartan.ISpartanObject)
	 */
	public void addObject(ISpartanObject object){
		if ( object != null && object instanceof IGameFormObject){
			IGameFormObject form = (IGameFormObject)object;
			formList.add(form);
		}
	}

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#removeObject(com.foxhole.tools.spartan.ISpartanObject)
	 */
	public void removeObject(ISpartanObject object){
		if ( object != null && object instanceof IGameFormObject){
			formList.remove((IGameFormObject)object);
		}
	}

	/**
	 * Adds a form to the game space
	 * 
	 * @param form the form to add
	 */
	public void addForm(IGameFormObject form){
		if ( form != null){
			formList.add(form);
		}
	}

	/**
	 * Removes a form from the game space
	 * 
	 * @param form the form to remove
	 */
	public void removeForm(IGameFormObject form){
		if ( form != null){
			formList.remove(form);
		}
	}
	
	/**
	 * Obtains all the forms from the virtual gamespace
	 * 
	 * @return A list of forms
	 */
	public List<IGameFormObject> getForms(){
		return formList;
	}
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#reset()
	 */
	public void reset(){
		formList = new LinkedList<IGameFormObject>();
	}

	/**
	 * Sets the virtual game space name
	 * 
	 * @param name the name of the virtual space
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *  Obtains the virtual game space name
	 *  
	 * @return the name of the virtual space
	 */
	public String getName() {
		return name;
	}
}
