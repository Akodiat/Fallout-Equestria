package scripting;

import utils.GameTime;

public interface IScript extends Cloneable{
	
	public void start();
	public void update(GameTime time);

	public Object clone();	
}
