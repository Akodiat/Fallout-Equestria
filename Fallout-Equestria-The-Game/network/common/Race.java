package common;
/**
 * 
 * @author Gustav Alm Rosenblad
 *
 */
public enum Race {

	EARTHPONY ("EARTHPONY"),
	PEGASUS ("PEGASUS"),
	UNICORN ("UNICORN");

	private String value;
	
	private Race(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

}
