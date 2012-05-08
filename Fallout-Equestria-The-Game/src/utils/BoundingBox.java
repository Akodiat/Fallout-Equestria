package utils;

import math.Vector3;

public class BoundingBox {
	
	public final Vector3 Min;
	public final Vector3 Max;
	
	public Vector3[] getCorners() {
		return new Vector3[]
			{
			new Vector3(this.Min.X, this.Max.Y, this.Max.Z),
			new Vector3(this.Max.X, this.Max.Y, this.Max.Z),
			new Vector3(this.Max.X, this.Min.Y, this.Max.Z),
			new Vector3(this.Min.X, this.Min.Y, this.Max.Z),
			new Vector3(this.Min.X, this.Max.Y, this.Min.Z),
			new Vector3(this.Max.X, this.Max.Y, this.Min.Z),
			new Vector3(this.Max.X, this.Min.Y, this.Min.Z),
			new Vector3(this.Min.X, this.Min.Y, this.Min.Z)
		};
	}
	
	public BoundingBox(Vector3 min, Vector3 max) {
		this.Min = min;
		this.Max = max;
	}
	
	public Vector3 getCenter() {
		return Vector3.lerp(Min, Max, 0.5f);
	}
	
	public boolean intersects(BoundingBox box) {
		return this.Max.X >= box.Min.X && this.Min.X <= box.Max.X && 
			   this.Max.Y >= box.Min.Y && this.Min.Y <= box.Max.Y && 
			   this.Max.Z >= box.Min.Z && this.Min.Z <= box.Max.Z;
	}
	
	
	public static boolean intersects(BoundingBox first, Vector3 firstOffset, BoundingBox second, Vector3 secondOffset) {
		BoundingBox box0 = new BoundingBox(Vector3.add(first.Min, firstOffset),Vector3.add(first.Max, firstOffset));
		BoundingBox box1 = new BoundingBox(Vector3.add(second.Min, secondOffset),Vector3.add(second.Max, secondOffset));
		
		return box0.intersects(box1);
	}
	
	
	@Override
	public int hashCode() {
		return this.Max.hashCode() + this.Min.hashCode();
	}
	
	public boolean equals(Object other) {
		if(other instanceof BoundingBox) {
			BoundingBox box = (BoundingBox)other;
			return box.Min.equals(this.Min) && 
				   box.Max.equals(this.Max);
		}
		return false;
	}
	
}
