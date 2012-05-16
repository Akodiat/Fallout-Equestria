package common;

public class AudioPlayedMessage extends NetworkMessage{
	public String audioPath;
	public Boolean isMusic;
	
	public float 	pitch;
	public float 	gain;
	public boolean 	loop;
}
