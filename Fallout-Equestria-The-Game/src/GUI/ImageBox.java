package GUI;

import graphics.Texture2D;
import utils.Rectangle;

public class ImageBox extends GUIImageControl {
	private ImageBoxRenderer DEFAULT_RENDERER = new ImageBoxRenderer();
	
	private Rectangle imageSrcRect;
		
	public ImageBox() {
		this.imageSrcRect = Rectangle.Empty;
		this.setRenderer(DEFAULT_RENDERER);
	}
	
	public void setImage(Texture2D texture) {
		super.setImage(texture);
		this.imageSrcRect = texture.getBounds();
	}
	
	public Rectangle getImageSrcRect() {
		return imageSrcRect;
	}

	public void setImageSrcRect(Rectangle imageSrcRect) {
		this.imageSrcRect = imageSrcRect;
	}
	
}
