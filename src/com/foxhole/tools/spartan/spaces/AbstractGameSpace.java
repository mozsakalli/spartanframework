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

import java.util.List;

import com.foxhole.tools.spartan.ISpartanObject;


/**
 * The game space object is a collection of other objects in the system.
 * This is an generic interface for all the user implemented gameSpaces. 
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public interface AbstractGameSpace extends IGameSpaceObject {

	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#addObject(com.foxhole.tools.spartan.ISpartanObject)
	 */
	public void addObject(ISpartanObject object);
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#removeObject(com.foxhole.tools.spartan.ISpartanObject)
	 */
	public void removeObject(ISpartanObject object);
	
	/**
	 * Verifies if the object exists in the GameSpace
	 * 
	 * @param object the object to check
	 * @return true if it exists, false otherwise
	 */
	public boolean checkForObject(ISpartanObject object);
	
	/**
	 * Obtains a list of objects in the gamespace
	 * 
	 * @return a list of objects
	 */
	public List<ISpartanObject> getObjects();
	
	/* (non-Javadoc)
	 * @see com.foxhole.tools.spartan.spaces.IGameSpaceObject#reset()
	 */
	public void reset();
}
