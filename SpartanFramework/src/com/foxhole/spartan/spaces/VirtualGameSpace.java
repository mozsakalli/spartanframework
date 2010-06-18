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

package com.foxhole.spartan.spaces;

import java.util.LinkedList;
import java.util.List;

import com.foxhole.spartan.ISpartanObject;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.forms.IGameFormObject;


public class VirtualGameSpace implements IGameSpaceObject {

	List<IGameFormObject> formList;
	String name;
	
	public VirtualGameSpace(String name){
		this.name = name;
		reset();
	}
	
	public void addObject(ISpartanObject object){
		if ( object != null && object instanceof IGameFormObject){
			IGameFormObject form = (IGameFormObject)object;
			formList.add(form);
		}
	}

	public void removeObject(ISpartanObject object){
		if ( object != null && object instanceof IGameFormObject){
			formList.remove((IGameFormObject)object);
		}
	}

	public void addForm(IGameFormObject form){
		if ( form != null){
			formList.add(form);
		}
	}

	public void removeForm(IGameFormObject form){
		if ( form != null){
			formList.remove(form);
		}
	}
	
	public List<IGameFormObject> getForms(){
		return formList;
	}
	
	public void reset(){
		formList = new LinkedList<IGameFormObject>();
	}
}
