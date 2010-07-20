package com.foxhole.spartan.managers.collision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.foxhole.spartan.forms.IGameFormObject;
import com.foxhole.spartan.managers.ISpartanManager;
import com.foxhole.spartan.states.CollisionGameState;

public class CollisionManager implements ISpartanManager {
	
	HashMap<String, ICollisionHandler> handlers;
	Map<Integer, List<IGameFormObject>> forms;
	Map<Integer,List<Integer>> typeCollisions;
	
	public CollisionManager(){
		handlers = new HashMap<String, ICollisionHandler>();
		forms = new HashMap<Integer, List<IGameFormObject>>();
		typeCollisions = new HashMap<Integer, List<Integer>>();
	}
	
	public final void registerCollisionHandler(ICollisionHandler handler){
		int type1 = handler.getFirstCollisionType();
		int type2 = handler.getSecondCollisionType();
		
		handlers.put(CollisionTypePair.getID(type1, type2), handler);
		
		List<Integer> listType1 = typeCollisions.get(type1);
		
		if(listType1 == null){
			listType1 = new ArrayList<Integer>();
			typeCollisions.put(type1, listType1);
		}
		if(!listType1.contains(type2))
			listType1.add( type2 );
	}
	
	public void registerForm(IGameFormObject form){
		CollisionGameState collisionState = (CollisionGameState)form.getState( CollisionGameState.class.getCanonicalName() );
		
		List<IGameFormObject> formTypeList = forms.get(collisionState.getColliderType());
		
		if(formTypeList == null){
			formTypeList = new ArrayList<IGameFormObject>();
			forms.put(collisionState.getColliderType(), formTypeList);
		}
		
		if (!formTypeList.contains(form))
			formTypeList.add(form);
	}
	
	public void unregisterForm(IGameFormObject form){
		CollisionGameState collisionState = (CollisionGameState)form.getState( CollisionGameState.class.getCanonicalName() );
		
		List<IGameFormObject> formTypeList = forms.get(collisionState.getColliderType());
		
		if(formTypeList != null){
			formTypeList.remove(form);
		}
	}
	
	public void execute(){
		Set allList = new HashSet<IGameFormObject>();
		// Obter uma lista de todos os elementos a colidir.
		
		List<CollisionData> collisions = new ArrayList<CollisionData>();
		
		Set<Integer> types = forms.keySet();
		// Obter para cada um obter a lista de tipos com os quais pode colidir
		for( Integer type : types ){
			//System.out.println("Collisions for type : " +  type);
			List<IGameFormObject> typeForm = forms.get(type);
			
			for(IGameFormObject form : typeForm){
				if ( !allList.contains(form) ){
					//allList.add(form);
					// nao: Por na lista de coisas a colidir
					List<Integer> collisionTypes = typeCollisions.get(type);
					// Para cada uma das listas verificar se ja colidiram
					if(collisionTypes != null)
					for(Integer collisionType : collisionTypes){
						//System.out.println("  |  Collides with type : " + collisionType);
						// obter todas as listas que colidem com este tipo
						List<IGameFormObject> collisionForms = forms.get(collisionType);
						if(collisionForms != null){
							// retirar o par da lista de colisoes
							for(IGameFormObject collisionForm : collisionForms){
								
								//if ( !allList.contains(collisionForm) ){
									CollisionData cd = new CollisionData();
									cd.form1 = form;
									cd.form2 = collisionForm;
									cd.handler = handlers.get(CollisionTypePair.getID(type, collisionType));
									
									collisions.add(cd);
									
									//allList.add(collisionForm);
								//}
							}
						}
						
					}
				}
			}
		}
		
		for(CollisionData cd : collisions){
			cd.handler.performCollision(cd.form1, cd.form2);
		}
	}

	class CollisionData{
		public ICollisionHandler handler;
		public IGameFormObject form1;
		public IGameFormObject form2;
	}
	
}
	