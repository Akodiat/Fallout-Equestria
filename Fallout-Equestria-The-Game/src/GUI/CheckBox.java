package GUI;



public class CheckBox extends ToggleButton {
	private static final ButtonRenderer DEFAULT_RENDERER = 
					 new ButtonRenderer(LookAndFeelAssets.CheckBox_BG.toString(),
							 			LookAndFeelAssets.CheckBox_Over.toString(),
							 			LookAndFeelAssets.CheckBox_Down.toString());
	
	public CheckBox() {
		super();
		this.setRenderer(DEFAULT_RENDERER);
	}
}
