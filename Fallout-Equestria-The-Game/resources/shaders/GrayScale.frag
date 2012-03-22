
#version 330 core

uniform sampler2D colorTexture;

in vec2 _textureCoord;

out vec4 outputColor;
void main() 
{	
	outputColor = texture(colorTexture, _textureCoord);
	float gray = dot(outputColor.rgb, vec3(0.3,0.59,0.11));
	outputColor.rgb = vec3(gray, gray ,gray);
}
