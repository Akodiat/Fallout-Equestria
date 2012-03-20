package tests;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import utils.ILineScript;
import utils.ILineScriptProcessor;
import utils.IScriptFunction;
import utils.LineScript;
import utils.LineScriptProcessor;

public class ScriptSystemTest {

	private ILineScriptProcessor scriptProcessor;

	@Before
	public void setUp() throws Exception {
		scriptProcessor = new LineScriptProcessor();
	}

	@Test
	public void testIfScriptFunctionGetsCalled() {
		
		IScriptFunction function = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function);	
		
		String[] testScript = new String[1];
		testScript[0] = "clear";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processLine(script);
		
		verify(function).excecute(new String[0]);
	}
	
	@Test
	public void testIfScriptSingleFunctionParamAreCorrect() {		
		IScriptFunction function = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function);	
		
		String[] testScript = new String[1];
		testScript[0] = "clear, 0xFFFF00FF";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processLine(script);
		
		verify(function).excecute(new String[] {"0xFFFF00FF"} );
	}

	@Test
	public void testIfScriptManyFunctionParamAreCorrect() {		
		IScriptFunction function = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function);	
		
		String[] testScript = new String[1];
		testScript[0] = "clear,0xFFFF00FF,hello,work harder!,birds in the sky!";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processLine(script);
		
		verify(function).excecute(new String[] {"0xFFFF00FF", "hello", "work harder!", "birds in the sky!"} );
	}
	
	@Test
	public void testIfScriptWhiteSpaceParamsWork() {		
		IScriptFunction function = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function);	
		
		String[] testScript = new String[1];
		testScript[0] = "clear,				0xFFFF00FF,			 	hallå";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processLine(script);
		
		verify(function).excecute(new String[] {"0xFFFF00FF", "hallå"} );
	}

	@Test
	public void testIfMultScriptsWork() {
		
		IScriptFunction function1 = mock(IScriptFunction.class);
		IScriptFunction function2 = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function1);	
		this.scriptProcessor.registerScirptFunction("deleate", function2);	
		
		
		String[] testScript = new String[2];
		testScript[0] = "clear";
		testScript[1] = "deleate, With a smile!";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processLine(script);
		this.scriptProcessor.processLine(script);
		
		verify(function1).excecute(new String[0]);
		verify(function2).excecute(new String[] { "With a smile!" });	
	}
	
	@Test 
	public void testIfAbleToProcessEntireScript() {
		
		IScriptFunction function1 = mock(IScriptFunction.class);
		IScriptFunction function2 = mock(IScriptFunction.class);
		this.scriptProcessor.registerScirptFunction("clear", function1);	
		this.scriptProcessor.registerScirptFunction("deleate", function2);	
				
		String[] testScript = new String[2];
		testScript[0] = "clear";
		testScript[1] = "deleate, With a smile!";
		
		ILineScript script = new LineScript(testScript);
		
		this.scriptProcessor.processEntireScript(script);
		
		verify(function1).excecute(new String[0]);
		verify(function2).excecute(new String[] { "With a smile!" });	
	}


}
