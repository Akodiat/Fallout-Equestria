package utils;

import java.util.ArrayList;
import java.util.List;

import math.MathHelper;
import math.Vector2;

public class HeightMap {
	private List<Vector2> points;
	private final float defaultHeight = 0;
	
	public HeightMap(List<Vector2> points, float defaultHeight) {
		this.points = new ArrayList<>(points);
	}
	
	
	public float getHeightAt(Vector2 position) {
		Vector2 left;
		Vector2 right;
		
		int rightIndex = findRightIndex(position);
		if(rightIndex != 0) {
			left = points.get(rightIndex - 1);
			right = points.get(rightIndex);
			float amount = (position.X - left.X) / (right.X - left.X);
			
			return MathHelper.lerp(left.Y, right.Y, amount);
			
		} else {
			//If this happens we are outside the map.	
			return defaultHeight;
		}
		
	}


	private int findRightIndex(Vector2 position) {
		for (int i = 1; i < this.points.size(); i++) {
			Vector2 point = this.points.get(i);
			if(point.X > position.X) {
				return i;
			}
		}
		
		return 0;
	}
}
