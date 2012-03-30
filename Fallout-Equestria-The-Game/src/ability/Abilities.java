package ability;

public enum Abilities {

	
	None(0),
	Teleport(60), 
	Telekinesis(20);
	
	public final int Cost;
	
	Abilities(int cost){
		this.Cost = cost;
	}
}
