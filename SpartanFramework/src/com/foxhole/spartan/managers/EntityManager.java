package com.foxhole.spartan.managers;

import java.util.HashMap;
import java.util.Map;

import com.foxhole.spartan.entity.IGameEntityObject;

public class EntityManager implements ISpartanManager {

	public static EntityManager _instance = new EntityManager();
	
	Map<String, IGameEntityObject> entities;
	
	private EntityManager(){
		entities = new HashMap<String, IGameEntityObject>();
	}
	
	public void addEntity( String id, IGameEntityObject entity ){
		entities.put(id, entity);
	}
	
	public IGameEntityObject getEntity(String id){
		return entities.get(id);
	}
	
	public void removeEntity(String id){
		entities.remove(id);
	}
	
	public static EntityManager getInstance(){
		return _instance;
	}
}
