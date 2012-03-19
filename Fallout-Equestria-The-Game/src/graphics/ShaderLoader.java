package graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

 class ShaderLoader {
	
	  private static int loadShader(int shaderType, String path){
	        int shader = glCreateShader(shaderType);
	       
	        if (shader != 0) {
	        	String shaderCode;
	        	
	        	{
	        		// Load file as a string
	                StringBuilder text = new StringBuilder();
	                
	                try {
	                	
	                	BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderLoader.class.getResourceAsStream(path)));
	                	
	                	String line;
	                	line = reader.readLine();
	                	if(line == null) {
	                		throw new IOException("File not found!" + path);
	                	} else {
	                		text.append(line).append("\n");
	                	}
	                	
	                	while ((line = reader.readLine()) != null) {
	                		text.append(line).append("\n");
	                	}
	                	
	                	reader.close();
	                } catch (Exception e){
	                	System.err.println("Fail reading " + path + ": " + e.getMessage());
	                }
	                
	                shaderCode = text.toString();
	        	}
	            
	            glShaderSource(shader, shaderCode);
	            glCompileShader(shader);
	            
	            int status = glGetShader(shader, GL_COMPILE_STATUS);
	    		if(status == GL_FALSE) {
	    			int infoLogLength = glGetShader(shader, GL_INFO_LOG_LENGTH);
	    			
	    			String infoLog = glGetShaderInfoLog(shader, infoLogLength);
	    			
	    			String strShaderType = null;
	    			switch(shaderType) {
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
	        
	        return shader;
	    }

	  private static int loadVerexShader(String path) {
		  return loadShader(GL_VERTEX_SHADER, path);
	  }
	  
	  private static int loadFragmentShader(String path) {
		  return loadShader(GL_FRAGMENT_SHADER, path);
	  }
	  
	  public static ShaderEffect loadShader(String vertexShaderPath, String pixelShaderPath) {
		  int vertexShader = loadVerexShader(vertexShaderPath);
		  int fragmentShader = loadFragmentShader(pixelShaderPath);
		  int program = glCreateProgram();
		  
		  glAttachShader(program, vertexShader);
		  glAttachShader(program, fragmentShader);
		  
		  glLinkProgram(program);
		  glValidateProgram(program);
		  
		  glDetachShader(program, vertexShader);
		  glDetachShader(program, fragmentShader);

		  return new ShaderEffect(program);
	  }
}
