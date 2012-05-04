package utils;

/**
 * 
 * @author Lukas Kurtyan
 *
 */
public final class TimeSpan implements Comparable<TimeSpan> {

	private static final long TicksInMiliSeconds   = 1L;
	private static final long TicksInSeconds 	   = 1000L;
	private static final long TicksInMinutes 	   = 6000_0L;
	
	//These two are not essential from the perspective of a game 
	//But they add orthogonality to the class.
	private static final long TicksInHours   	   = 3600_000L;
	private static final long TicksInDays		   = 8640_000_0L;
	
	private final long ticks;
	
	public TimeSpan(int days, int hours, int minutes) {
		this(days, hours, minutes, 0);
	}

	public TimeSpan(int days, int hours, int minutes, int seconds) {
		this(days, hours, minutes, seconds, 0);
	}

	public TimeSpan(int days, int hours, int minutes, int seconds, int miliseconds) {
		long ticks = days    * TicksInDays;
		ticks += hours 	     * TicksInHours;
		ticks += minutes     * TicksInMinutes;
		ticks += seconds     * TicksInSeconds;
		ticks += miliseconds * TicksInMiliSeconds;
		
		this.ticks = ticks;
	}
	
	private TimeSpan(long ticks) {
		this.ticks = ticks;
	}

	public static TimeSpan fromMiliseconds(double miliseconds) {
		long ticks = (long) (miliseconds + 0.5d); //Rounding
		return new TimeSpan(ticks);
	}
	
	public static TimeSpan fromSeconds(double seconds) {
		long ticks = (long)(seconds * TicksInSeconds);		
		return new TimeSpan(ticks);
	}
	
	public static TimeSpan fromMinutes(double minutes) {
		long ticks = (long)(minutes * TicksInMinutes);
		return new TimeSpan(ticks);
	}
	
	public static TimeSpan fromHours(double hours) {
		long ticks = (long)(hours * TicksInHours);
		return new TimeSpan(ticks);
	}
	
	public static TimeSpan fromDays(double days) {
		long ticks = (long)(days * TicksInDays);
		return new TimeSpan(ticks);
	}
	
	public int getMiliSeconds() {
		return (int)(this.ticks  % 1000L);
	}
	
	public int getSeconds() {
		return (int)(this.ticks / TicksInSeconds) % 60;
	}
	
	public int getMinutes() {
		return (int)(this.ticks / TicksInMinutes) % 60;
	}
	
	public int getHours() {
		return (int)(this.ticks / TicksInHours) % 24;
	}
	
	public int getDays() {
		return (int)(this.ticks / TicksInDays);
	}
	
	public double getTotalMiliSeconds() {
		return (this.ticks / (double)TicksInMiliSeconds);
	}
	
	public double getTotalSeconds() {
		return (this.ticks / (double)TicksInSeconds);
	}
	
	public double getTotalMinutes() {
		return (this.ticks / (double)TicksInMinutes);
	}

	public double getTotalHours() {
		return (this.ticks / (double)TicksInHours);
	}

	public double getTotalDays() {
		return (this.ticks / (double)TicksInDays);
	}
	
	
	public TimeSpan add(TimeSpan other) {
		long totalTicks = this.ticks + other.ticks;
		return new TimeSpan(totalTicks);
	}
	
	public static TimeSpan add(TimeSpan first, TimeSpan second) {
		return first.add(second);
	}
	
	public TimeSpan subtract(TimeSpan other) {
		long totalTicks = this.ticks - other.ticks;
		return new TimeSpan(totalTicks);
	}
	

	public static TimeSpan subtract(TimeSpan first, TimeSpan second) {
		return first.subtract(second);
	}
	
	public boolean equals(Object other) {
		if(other instanceof TimeSpan) {
			TimeSpan otherTime = (TimeSpan)other;
			return otherTime.ticks == this.ticks;
		}
		return false;
	}
	
	@Override
	public int compareTo(TimeSpan other) {
		return (int) (this.ticks - other.ticks);
	}
	
	public String toString() {
		return this.getTotalSeconds() + " ";
	}

	
}
