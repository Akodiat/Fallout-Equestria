package uinttests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import tests.Matrix4InvTest;

import content.AnimationLoadTest;
import content.EntityArchetypeLoaderTests;
import content.SceneLoaderTests;
import content.ShaderLoaderTests;
import content.SoundLoaderTests;
import content.TextureFontLoaderTests;


@RunWith(Suite.class)
@SuiteClasses({ CircleTests.class, AnimationLoadTest.class, EntityArchetypeLoaderTests.class, 
				TimerTests.class, Vector2Tests.class, RectangleTests.class, Matrix4InvTest.class, ColorTests.class ,
				SceneLoaderTests.class, ShaderLoaderTests.class, SoundLoaderTests.class, TextureFontLoaderTests.class})

public class AllTests {

}
