package uinttests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ CircleTests.class, InputTests.class, ScriptSystemTest.class,
		TimerTests.class, Vector2Tests.class })
public class AllTests {

}
