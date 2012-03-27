package components;

import entityFramework.IComponent;
/**
 * 
 * @author Joakim Johansson
 *
 */
public final class SpecialComp implements IComponent{
	private int strength;
	private int perception;
	private int endurance;
	private int charisma;
	private int intelligence;
	private int agility;
	private int luck;
	
	public SpecialComp(){
		this.strength = 5;
		this.perception = 5;
		this.endurance = 5;
		this.charisma = 5;
		this.intelligence = 5;
		this.agility = 5;
		this.luck = 5;
	}
	public SpecialComp(int strength, int perception, int endurance, int charisma, int intelligence, int agility, int luck){
		this.strength = strength;
		this.perception = perception;
		this.endurance = endurance;
		this.charisma = charisma;
		this.intelligence = intelligence;
		this.agility = agility;
		this.luck = luck;
	}
	private SpecialComp(SpecialComp other){
		this.strength = other.strength;
		this.perception = other.perception;
		this.endurance = other.endurance;
		this.charisma = other.charisma;
		this.intelligence = other.intelligence;
		this.agility = other.agility;
		this.luck = other.luck;
	}
	
	public Object clone() {
		return new SpecialComp(this);
	}
	/**
	 * @return the strength
	 */
	public int getStrength() {
		return strength;
	}
	/**
	 * @param strength the strength to set
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}
	/**
	 * @return the perception
	 */
	public int getPerception() {
		return perception;
	}
	/**
	 * @param perception the perception to set
	 */
	public void setPerception(int perception) {
		this.perception = perception;
	}
	/**
	 * @return the endurance
	 */
	public int getEndurance() {
		return endurance;
	}
	/**
	 * @param endurance the endurance to set
	 */
	public void setEndurance(int endurance) {
		this.endurance = endurance;
	}
	/**
	 * @return the charisma
	 */
	public int getCharisma() {
		return charisma;
	}
	/**
	 * @param charisma the charisma to set
	 */
	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}
	/**
	 * @return the intelligence
	 */
	public int getIntelligence() {
		return intelligence;
	}
	/**
	 * @param intelligence the intelligence to set
	 */
	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}
	/**
	 * @return the agility
	 */
	public int getAgility() {
		return agility;
	}
	/**
	 * @param agility the agility to set
	 */
	public void setAgility(int agility) {
		this.agility = agility;
	}
	/**
	 * @return the luck
	 */
	public int getLuck() {
		return luck;
	}
	/**
	 * @param luck the luck to set
	 */
	public void setLuck(int luck) {
		this.luck = luck;
	}

}
