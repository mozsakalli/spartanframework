package com.foxhole.spartan.managers.collision;


public class CollisionTypePair{
	int type1;
	int type2;
	
	String id;
	
	public CollisionTypePair(int type1, int type2){
		this.type1 = type1;
		this.type2 = type2;
		id = CollisionTypePair.getID(type1, type2); 
	}
	
	public static String getID(int type1, int type2){
		return (type1 < type2) ? (type1+"-"+type2) : (type2+"-"+type1);
	}

	public final int getType1() {
		return type1;
	}

	public final int getType2() {
		return type2;
	}

	public final String getId() {
		return id;
	}
}
