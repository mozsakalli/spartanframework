package com.foxhole.spartan.actions.resource;

import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.loading.DeferredResource;
import org.newdawn.slick.loading.LoadingList;
import org.newdawn.slick.state.StateBasedGame;

import com.foxhole.spartan.actions.BasicGameAction;
import com.foxhole.spartan.entity.IGameEntityObject;
import com.foxhole.spartan.exception.SpartanException;
import com.foxhole.spartan.states.PropertiesGameState;

public class ResourceLoaderAction extends BasicGameAction {

	PropertiesGameState properties;
	
	public ResourceLoaderAction(String name, IGameEntityObject userEntity)
			throws SpartanException {
		super(name, userEntity);
		
		properties = (PropertiesGameState) userEntity.getForm().getState(PropertiesGameState.class.getCanonicalName());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) {
		// load the resources
		if (LoadingList.get().getRemainingResources() > 0) { 
	        DeferredResource nextResource = LoadingList.get().getNext(); 
	        try {
				nextResource.load();
				
				// update percentages
				int percentage = 100 * (LoadingList.get().getTotalResources() - LoadingList.get().getRemainingResources()) / LoadingList.get().getTotalResources();
				
				properties.setInt("LOAD_PERCENTAGE", percentage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
		}else{
			isOver = true;
		}
		
	}

}
