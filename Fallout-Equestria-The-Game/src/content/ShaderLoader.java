package content;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import graphics.ShaderEffect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

 public class ShaderLoader implements IContentLoader<ShaderEffect>{
	
	  private int createShader(int glShaderType, String shaderCode) {
		  int shader = glCreateShader(glShaderType);
		  
		  glShaderSource(shader, shaderCode);
		  glCompileShader(shader);
		  
		  validateShader(glShaderType, shader);  
		  
		  return shader;
	  }
	  
	  private void validateShader(int glShaderType, int shader) {
    	int status = glGetShader(shader, GL_COMPILE_STATUS);
  		if(status == GL_FALSE) {
			int infoLogLength = glGetShader(shader, GL_INFO_LOG_LENGTH);
			
			String infoLog = glGetShaderInfoLog(shader, infoLogLength);
			
			String strShaderType = null;
			switch(glShaderType) {
				case GL_VERTEX_SHADER:
					strShaderType = "Vertex Shader";
				break;
				case GL_FRAGMENT_SHADER:
					strShaderType = "Fragment Shader";
				break;
			}			
			throw new RuntimeException("There is a problem with" + strShaderType + 
										"\n \n \n " + infoLog);
	  	}	
	}

	  private int createProgram(int vertexShader, int fragmentShader) {
		  int program = glCreateProgram();
		  
		  glAttachShader(program, vertexShader);
		  glAttachShader(program, fragmentShader);
		  
		  glLinkProgram(program);
		  glValidateProgram(program);
		  
		  glDetachShader(program, vertexShader);
		  glDetachShader(program, fragmentShader);
		  
		  return program;
	  }

	  
	  private int loadProgram(InputStream in) {
		  
		  String effectText = loadEffect(in);  	  
		  int vertexShader   = createShader(GL_VERTEX_SHADER, effectText.split("split")[0]);
		  int fragmentShader   = createShader(GL_FRAGMENT_SHADER, effectText.split("split")[1]);
		  int program = createProgram(vertexShader, fragmentShader);
		  
		  return program;
	  }
	  
	  
	private String loadEffect(InputStream in) {
		  try {
			  BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			  StringBuilder textBuilder = new StringBuilder();
			  
			  String line; 
			  while ((line = reader.readLine()) != null) {
				  textBuilder.append(line).append("\n");
		      }
			 		  
			  return textBuilder.toString();  
		  } catch(IOException exe) {
			  throw new RuntimeException("Something went horrobly wrong in parsing the shader!");
		  }
	  }
	  
	@Override
	public Class<ShaderEffect> getClassAbleToLoad() {
		return ShaderEffect.class;
	}

	@Override
	public ShaderEffect loadContent(InputStream in) throws Exception {
		return new ShaderEffect(this.loadProgram(in));
	}

	@Override
	public String getFoulder() {
		return "shaders";
	}
}
