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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.foxhole.tools.spartan.ISpartanObject;
import com.foxhole.tools.spartan.entity.IGameEntityObject;


public class LogicalGameSpace implements IGameSpaceObject {
	
	public LogicalGameSpace(){
		reset();
	}
	
	List<IGameEntityObject> entityList;
	Map<Integer, IGameEntityObject> entityMap;
	
	public void addObject(ISpartanObject object){
		if(object != null && object instanceof IGameEntityObject){
			IGameEntityObject entity = (IGameEntityObject)object;
			entityList.add(entity);
			entityMap.put(entity.getId(), entity);
		}
	}
	
	public void removeObject(ISpartanObject object){
		if(object != null && object instanceof IGameEntityObject){
			IGameEntityObject entity = (IGameEntityObject)object;
			entityList.remove(entity);
			entityMap.remove(entity.getId());
		}
	}
	
	public boolean checkForEntity(IGameEntityObject entity){
		if(entity != null){
			return entityMap.containsKey(entity.getId());
		}
		
		return false;
	}
	
	public List<IGameEntityObject> getEntities(){
		return entityList;
	}
	
	public void reset(){
		entityList = new LinkedList<IGameEntityObject>();
		entityMap = new HashMap<Integer, IGameEntityObject>();
	}
}
