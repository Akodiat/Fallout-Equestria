package graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.google.common.io.Resources;

public class ShaderLoader {
	
	  public static int loadShader(int shaderType, String path){
	        int shader = glCreateShader(shaderType);
	       
	        if (shader != 0) {
	        	String shaderCode;
	        	
	        	{
	        		// Load file as a string
	                StringBuilder text = new StringBuilder();
	                
	                try {
	                	BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderLoader.class.getResourceAsStream(path)));
	                	String line;
	                	
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
	    
	    
		public static int createProgram(List<Integer> shaderList) {		
		    int program = glCreateProgram();

		    if (program != 0) {
			    for (Integer shader : shaderList) {
			    	glAttachShader(program, shader);
				}

			    glLinkProgram(program);
		        glValidateProgram(program);

			    for (Integer shader : shaderList) {
			    	glDetachShader(program, shader);
				}
		    }

		    return program;
		}

}
