#version 330 core

uniform sampler2D colorTexture;

in vec2 _textureCoord;
in vec4 _color;

out vec4 outputColor;
void main() 
{	
	outputColor = texture(colorTexture, _textureCoord) * _color;
}
