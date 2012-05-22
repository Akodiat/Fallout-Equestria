package player;

public class ManeStyle {
	public String upperManeStyle;
	public String lowerManeStyle;
	public String upperTailStyle;
	public String lowerTailStyle;
	
	public ManeStyle() {}
	
	public ManeStyle(String upperManeStyle, String lowerManeStyle,
			String upperTailStyle, String lowerTailStyle) {
		this.upperManeStyle = upperManeStyle;
		this.lowerManeStyle = lowerManeStyle;
		this.upperTailStyle = upperTailStyle;
		this.lowerTailStyle = lowerTailStyle;
	}
	public ManeStyle(ManeStyle other) {
		this.upperManeStyle = other.upperManeStyle;
		this.lowerManeStyle = other.lowerManeStyle;
		this.upperTailStyle = other.upperTailStyle;
		this.lowerTailStyle = other.lowerTailStyle;
	}
	public ManeStyle clone(){
		return new ManeStyle(this);
	}
	
}