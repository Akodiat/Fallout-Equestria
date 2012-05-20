package GUI;



public class Panel extends GUIControl {
	private static final PanelRenderer DEFAULT_RENDERER = new PanelRenderer();
	//A panel rly isn't special in any way :S
	
	public Panel() {
		this.setRenderer(DEFAULT_RENDERER);
	}
}
