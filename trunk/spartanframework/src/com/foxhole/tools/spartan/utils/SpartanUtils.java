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

package com.foxhole.tools.spartan.utils;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

import com.foxhole.tools.spartan.exception.SpartanException;

/**
 * A class for several useful utilitaries used in the library.
 * 
 * @author Tiago "Spiegel" Costa
 *
 */
public class SpartanUtils {
	
	/**
	 * Extracts a Vector2f from a string with the following format : [float],[float]
	 * If no string is passed an empty vector (0,0) is returned.
	 *  
	 * @param vectorInStr The string that contains the vector representation
	 * @return A vector initialized 
	 * @throws SpartanException On Invalid format or unparsable float values.
	 */
	public static Vector2f getVectorFromString(String vectorInStr) throws SpartanException{
		Vector2f vector = new Vector2f(0,0);
		
		if(vectorInStr != null){
			String[] vectorPos = vectorInStr.split(",");
			
			if(vectorPos.length != 2){
				throw new SpartanException("Invalid vector data :" + vectorInStr);
			}
			
			try{
				vector.x = Float.parseFloat( vectorPos[0] );
				vector.y = Float.parseFloat( vectorPos[1] );
			}catch(Throwable t){
				throw new SpartanException("Unparsable values : " + vectorInStr, t);
			}
		}
		
		return vector;
	}
	
	/**
	 * Extracts a float from a string with the following format : [float]
	 * If no string is passed an zero float value is returned.
	 * 
	 * @param floatInStr the string with the float representation
	 * @return A float with the value
	 * @throws SpartanException On unparsable float values.
	 */
	public static float getFloatFromString(String floatInStr) throws SpartanException{
		float value = 0;
		
		if(floatInStr != null){
			try{
				value = Float.parseFloat(floatInStr);
			}catch(Throwable t){
				throw new SpartanException("Unparsable values : " + floatInStr, t);
			}
		}
		
		return value;
	}

	/**
	 * Extracts a Shape from a string with the following format : [float],[float];[float],[float];...;[float],[float]
	 * Each pair [float],[float] represents a point for the shape, all the points must be 
	 * If no string is passed an null shape is returned.
	 * 
	 * @param shapeInStr The string with the representation of the points
	 * @return A Shape representing a closed polygon from the given points
	 * @throws SpartanException If the number of points are below 3 (invalid shape), unparsable floats or malformed string.
	 */
	public static Shape getShapeFromString(String shapeInStr) throws SpartanException {
		Polygon polygon = null;
		
		if(shapeInStr != null){
			String[] points = shapeInStr.split(";");
			
			if(points.length < 3){
				throw new SpartanException("Insuficient number of points to create shape");
			}
			
			polygon = new Polygon();
			
			for(int pointIdx = 0; pointIdx < points.length; pointIdx++){
				Vector2f point = getVectorFromString(points[pointIdx]);
				
				polygon.addPoint(point.x, point.y);
			}
			
			// close the poligon
			polygon.setClosed(true); 
		}
		
		return polygon;
	}
}
