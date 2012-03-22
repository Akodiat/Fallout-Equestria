package scripting;

import components.RenderingComponent;

import graphics.ShaderEffect;
import utils.IScriptFunction;

public class CustomEffectScriptFunction implements IScriptFunction{
	
	private RenderingComponent renderComp;
	
	@Override
	public void excecute(String[] params) {
		if(params.length != 1) {
			throw new ScriptException();
		}
		
		String effectPath = params[0];
		ShaderEffect customEffect = ShaderEffect.fromFile("Standard.vert", effectPath);
		
		this.renderComp.setEffect(customEffect);		
	}
	
	@Override
	public String getFunctionName() {
		return "shader";
	}
	public RenderingComponent getRenderComp() {
		return renderComp;
	}
	public void setRenderComp(RenderingComponent renderComp) {
		this.renderComp = renderComp;
	}

}
