package uinttests;
import static org.junit.Assert.*;
import misc.IEventListener;
import misc.TimerEventArgs;

import org.junit.Test;
import utils.TimeSpan;
import utils.Timer;

public class TimerTests {

	@Test
	public void testStart() {
		final Timer timer = new Timer(1, 1f);
		timer.addStartListener(new IEventListener<TimerEventArgs>() {
			public void onEvent(Object sender, TimerEventArgs e) {
				assertEquals(timer, sender);
				assertEquals(e.getCurrentTick(), 0);
				assertEquals(e.getNumTicks(), 1);
				assertEquals(e.getTickInterval(), 1.0f, 0.1f);
			}
		});
		timer.start();
	}
	
	@Test
	public void testTick() {
		final Timer timer = new Timer(2, 1f);
		timer.addTickListener(new IEventListener<TimerEventArgs>() {
			public void onEvent(Object sender, TimerEventArgs e) {
				assertEquals(timer, sender);
				assertEquals(e.getCurrentTick(), 1);
				assertEquals(e.getNumTicks(), 2);
				assertEquals(e.getTickInterval(), 1.0f, 0.1f);
			}
		});
		timer.start();
		timer.Update(TimeSpan.fromSeconds(1f));
	}
	
	@Test
	public void testComplete() {
		final Timer timer = new Timer(5, 1f);
		timer.addCompleteListener(new IEventListener<TimerEventArgs>() {
			public void onEvent(Object sender, TimerEventArgs e) {
				assertEquals(timer, sender);
				assertEquals(e.getCurrentTick(), 5);
				assertEquals(e.getNumTicks(), 5);
				assertEquals(e.getTickInterval(), 1.0f, 0.1f);
			}
		});
		timer.start();
		timer.Update(TimeSpan.fromSeconds(5f));
		
	}
}
