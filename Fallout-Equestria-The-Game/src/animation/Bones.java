package animation;

public enum Bones {
	ROOT ("RIGIDBODY"),
	BODY ("BODY"),
	LEFTARM ("FRONTLEG"),
	RIGHTARM ("BACKFRONTLEG"),
	LEFTLEG ("HINDLEG"),
	RIGHTLEG ("BACKFRONTLEGCOR"),
	NECK ("NECK"),
	HEAD ("HEAD"),
	MOUTH ("MOUTH"),
	EAR ("EAR"),
	HORN ("HORN"),
	EYE ("EYE"),
	SCLERA ("SCLERA"),
	IRIS ("IRIS"),
	HIGHLIGHTS ("HIGHLIGHTS"),
	UPPERMANE ("UPPERMANE"),
	LOWERMANE ("LOWERMANE"),
	UPPERTAIL ("UPPERTAIL"),
	LOWERTAIL ("LOWERTAIL"),
	MARK ("MARK"),
	WINGS ("WINGS"),
	LEFTHAND ("RIGHTFRONTHOOF"),
	RIGHTHAND ("LEFTFRONTHOOF"),
	RIGHTFOOT ("RIGHTBACKHOOF"),
	LEFTFOOT ("LEFTBACKHOOF"),
	PIPBUCK ("UPPERFRONTHOOF");
	
	private String value;
	
	private Bones(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
