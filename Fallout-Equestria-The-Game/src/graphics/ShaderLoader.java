package graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

 public class ShaderLoader {
	
	  private static int loadShader(int shaderType, InputStream stream) throws IOException{
	        int shader = glCreateShader(shaderType);
	       
	        if (shader != 0) {
	        	String shaderCode;
	        	
	        	{
	        		// Load file as a string
	                StringBuilder text = new StringBuilder();
	                
	                	
                	BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                	
                	String line;
                	line = reader.readLine();
                	
                	while ((line = reader.readLine()) != null) {
                		text.append(line).append("\n");
                	}
                	
                	reader.close();
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

	  private static int loadVerexShader(InputStream stream) throws IOException {
		  return loadShader(GL_VERTEX_SHADER, stream);
	  }
	  
	  private static int loadFragmentShader(InputStream stream) throws IOException {
		  return loadShader(GL_FRAGMENT_SHADER, stream);
	  }
	  
	  public static ShaderEffect loadShader(InputStream vertexStream, InputStream fragmentStream) throws IOException {		  
		  int vertexShader = loadVerexShader(vertexStream);
		  int fragmentShader = loadFragmentShader(fragmentStream);
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
