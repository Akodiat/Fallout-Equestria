package ability;

public enum Abilities {

	
	None(0),
	Teleport(60), 
	Telekinesis(20), 
	Bullet(5),
	SpawnCreatures(60),
	TimeBomb(60),
	SuperTimeBomb(60);
	
	public final int Cost;
	
	Abilities(int cost){
		this.Cost = cost;
	}
}
