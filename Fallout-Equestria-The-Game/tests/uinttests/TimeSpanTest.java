package uinttests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

import utils.time.TimeSpan;

public class TimeSpanTest {

	@Test
	public void testIfConstructorIsCorrect() {
		Random r = new Random();
		int days = r.nextInt(10000), 
		    hours = r.nextInt(24),
			minutes = r.nextInt(60),
		    seconds = r.nextInt(60),
		    miliseconds = r.nextInt(1000);
		
		TimeSpan span = new TimeSpan(days, hours, minutes,
									 seconds, miliseconds);
		
		assertEquals(days, span.getDays());
		assertEquals(hours, span.getHours());
		assertEquals(minutes, span.getMinutes());
		assertEquals(seconds, span.getSeconds());
		assertEquals(miliseconds, span.getMiliSeconds());
	}
	
	@Test 
	public void testIfFromTimeMethodsWork() {
		Random r = new Random();
		int days = r.nextInt(10000), 
			    hours = r.nextInt(24),
				minutes = r.nextInt(60),
			    seconds = r.nextInt(60),
			    miliseconds = r.nextInt(1000);
		
		TimeSpan span = TimeSpan.fromDays(days);
		assertEquals(days, span.getDays());

		span = TimeSpan.fromHours(hours);
		assertEquals(hours, span.getHours());
		
		span = TimeSpan.fromMinutes(minutes);
		assertEquals(minutes, span.getMinutes());
		
		span = TimeSpan.fromSeconds(seconds);
		assertEquals(seconds, span.getSeconds());
		
		span = TimeSpan.fromMiliseconds(miliseconds);
		assertEquals(miliseconds, span.getMiliSeconds());
	}
	
	@Test 
	public void testAddingAndSubtractingTimeSpans() {
		Random r = new Random();
		int num0 = r.nextInt(1000), 
		    num1 = r.nextInt(1000);
		
		TimeSpan span0 = TimeSpan.fromSeconds(num0);
		TimeSpan span1 = TimeSpan.fromSeconds(num1);
		TimeSpan expected = TimeSpan.fromSeconds(num0 + num1);
		
		assertEquals(expected, TimeSpan.add(span0, span1));
		
		expected = TimeSpan.fromSeconds(num0 - num1);
		assertEquals(expected, TimeSpan.subtract(span0, span1));
	}
	
	@Test
	public void testEquals() {
		Random r = new Random();
		
		int num0 = r.nextInt(1000),
			num1 = num0 + 1;
		
		TimeSpan span0 = TimeSpan.fromSeconds(num0);
		TimeSpan span1 = TimeSpan.fromSeconds(num1);
		
		assertEquals(span0, span0);
		assertEquals(span0, TimeSpan.fromSeconds(num0));
		
		assertNotSame(span0, span1);
	}
	
	@Test
	public void testComparing() {
		Random r = new Random();
		
		int num0 = r.nextInt(1000),
		    num1 = num0 + 1;
		
		TimeSpan span0 = TimeSpan.fromSeconds(num0);
		TimeSpan span1 = TimeSpan.fromSeconds(num1);
		
		int actual = span0.compareTo(span1);
		assertTrue(actual < 0);
		
		actual = span1.compareTo(span0);
		assertTrue(actual > 0);
		
		actual = span0.compareTo(span0);
		assertTrue(actual == 0);
	}

}
