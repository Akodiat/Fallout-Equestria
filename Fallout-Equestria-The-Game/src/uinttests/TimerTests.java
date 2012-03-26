package uinttests;

import static org.mockito.Mockito.*;

import org.junit.Test;

import utils.ITimerListener;
import utils.Timer;

public class TimerTests {

	@Test
	public void testStart() {
		ITimerListener timerListener = mock(ITimerListener.class);
		Timer timer = new Timer(1f, 1);
		timer.addEventListener(timerListener);
		timer.Start();
		
		verify(timerListener).Start();
	}
	
	@Test
	public void testTick() {
		ITimerListener timerListener = mock(ITimerListener.class);
		Timer timer = new Timer(1f, 3);
		timer.addEventListener(timerListener);
		timer.Start();
		
		Timer.updateTimers(2f);
		verify(timerListener, times(2)).Tick();
	}
	
	@Test
	public void testComplete() {
		ITimerListener timerListener = mock(ITimerListener.class);
		Timer timer = new Timer(1f, 5);
		timer.addEventListener(timerListener);
		timer.Start();
		
		Timer.updateTimers(6f);
		
		verify(timerListener, times(5)).Tick();
		verify(timerListener).Complete();
		
	}
}
