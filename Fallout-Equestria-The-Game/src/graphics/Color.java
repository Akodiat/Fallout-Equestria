package graphics;

import math.MathHelper;

public class Color {

	public final float R;
	public final float G;
	public final float B;
	public final float A;
	
	public Color(){
		this(1,1,1,1);
	}
	
	public Color(float red, float green, float blue, float alpha) {
		R = MathHelper.clamp(0.0f, 1.0f, red);
		G = MathHelper.clamp(0.0f, 1.0f, green);
		B = MathHelper.clamp(0.0f, 1.0f, blue);
		A = MathHelper.clamp(0.0f, 1.0f, alpha);
	}
	
	
	public Color(int red, int green, int blue, int alpha) {
		R = (float)MathHelper.clamp(0, 255, red) / 255;
		G = (float)MathHelper.clamp(0, 255, green) / 255;
		B = (float)MathHelper.clamp(0, 255, blue) / 255;
		A = (float)MathHelper.clamp(0, 255, alpha) / 255;	
	}
	
	public Color(Color c, float alpha) {
		R = c.R;
		G = c.G;
		B = c.B;
		A = MathHelper.clamp(0.0f, 1.0f, alpha);
	}
	
	public Color(int hex) {
		R = ((hex >> 24) & 0xFF)  / (float)0xFF; 
		G = ((hex >> 16) & 0xFF)  / (float)0xFF;
		B = ((hex >> 8)  & 0xFF)   / (float)0xFF;
		A = ((hex >> 0)  & 0xFF)   / (float)0xFF;
	}
	
	public static Color lerp(Color c1, Color c2, float t) {
		float r = MathHelper.lerp(c1.R, c2.R, t);
		float g = MathHelper.lerp(c1.G, c2.G, t);
		float b = MathHelper.lerp(c1.B, c2.B, t);
		float a = MathHelper.lerp(c1.A, c2.A, t);
		
		return new Color(r, g, b, a);
	}
	
	@Override
	public String toString() {
		return "{ " + this.R + "," + this.G + "," 
				+ this.B + "," + this.A + " }";
	}
	
	public int toHex() {
		int r = (int)(R*255);
		int g = (int)(G*255);
		int b = (int)(B*255);
		int a = (int)(A*255);
		
		return (r << 24 | g << 16 | b << 8 | a);	
	}
	
	public float[] toArray() {
		return new float[] {
			R,
			G,
			B,
			A
		};
	}
	
	public final static Color White		     = 	 new Color(0xFFFFFFFF);
	public final static Color Black			 = 	 new Color(0x000000FF);
	public final static Color Crimson   	 =   new Color(0xDC143CFF);
	public final static Color LightPink 	 = 	 new Color(0xFFB6C1FF);
	public final static Color Pink      	 = 	 new Color(0xFFC0CBFF);
	public final static Color Raspberry 	 = 	 new Color(0x872657FF);
	public final static Color Orchid    	 = 	 new Color(0xDA70D6FF);
	public final static Color Violet   		 = 	 new Color(0xEE82EEFF);
	public final static Color Magenta   	 = 	 new Color(0xFF00FFFF);
	public final static Color Purple   		 = 	 new Color(0x800080FF);
	public final static Color Blue      	 = 	 new Color(0x0000FFFF);
	public final static Color NavyBlue     	 = 	 new Color(0x000080FF);
	public final static Color CornflowerBlue = 	 new Color(0x6495EDFF);
	public final static Color LightBlue 	 = 	 new Color(0x87CEFFFF);
	public final static Color Turquoise	 	 = 	 new Color(0x00F5FFFF);
	public final static Color Cyan 			 = 	 new Color(0x00FFFFFF);
	public final static Color Green 		 = 	 new Color(0x00FF00FF);
	public final static Color EmeraldGreen   = 	 new Color(0x00C957FF);
	public final static Color ForestGreen 	 = 	 new Color(0x228B22FF);
	public final static Color Yellow 		 = 	 new Color(0xFFFF00FF);
	public final static Color Gold 			 = 	 new Color(0xFFD700FF);
	public final static Color Goldenrod 	 = 	 new Color(0xDAA520FF);
	public final static Color Orange 		 = 	 new Color(0xFFA500FF);
	public final static Color DarkOrange	 = 	 new Color(0xFF8C00FF);
	public final static Color Wheat 		 = 	 new Color(0xF5DEB3FF);
	public final static Color Chocolate 	 = 	 new Color(0xD2691EFF);
	public final static Color Brown 		 = 	 new Color(0xA52A2AFF);
	public final static Color Red			 = 	 new Color(0xFF0000FF);
	public final static Color Teal			 = 	 new Color(0x388E8EFF);
	public final static Color Transparent    =   new Color(0x00000000);
	public final static Color Gray			 =	 new Color(0xCCCCCCFF);

}
