#version 330

uniform sampler2D textureTest;
smooth in vec2 _texCoord;
in vec4 _color;

out vec4 outputColor;


void main() 
{	
	outputColor = texture2D(textureTest, _texCoord) * _color;
}
