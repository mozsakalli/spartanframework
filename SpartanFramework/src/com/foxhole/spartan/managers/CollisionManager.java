package com.foxhole.spartan.managers;

public class CollisionManager implements ISpartanManager {
	int initialSizeX;
	int initialSizeY;
	int finalSizeX;
	int finalSizeY;
	
	CollisionManager(int initialSizeX, int initialSizeY, int finalSizeX, int finalSizeY){
		this.initialSizeX = initialSizeX;
		this.initialSizeY = initialSizeY;
		this.finalSizeX = finalSizeX;
		this.finalSizeY = finalSizeY;
	}
	

}
