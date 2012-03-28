package components;

import entityFramework.IComponent;

/**
 * 
 * @author Joakim Johansson
 *
 */
public class AntiGravityComp implements IComponent{
	private int controllingEntityID;
	
	public AntiGravityComp(int controllingEntityID){
		this.controllingEntityID = controllingEntityID;
	}
	
	private AntiGravityComp(AntiGravityComp other){
		this.controllingEntityID = other.controllingEntityID;
	}
	
	public Object clone(){
		return new AntiGravityComp(this);
	}

	/**
	 * @return the controllingEntityID
	 */
	public int getControllingEntityID() {
		return controllingEntityID;
	}

	/**
	 * @param controllingEntityID the controllingEntityID to set
	 */
	public void setControllingEntityID(int controllingEntityID) {
		this.controllingEntityID = controllingEntityID;
	}
	
	public String toString() {
		return "AntiGravityComp: \n" 
			+  "ControlingEntityID: " + this.controllingEntityID + "\n";
	}
}
