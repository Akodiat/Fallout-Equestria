
#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 textureCoord;
layout(location = 2) in vec4 color;

uniform mat4 projection;
uniform mat4 view;
uniform vec2 viewport;


out vec2 _textureCoord;
out vec4 _color;


void main() {
	vec4 realPos = vec4(position.x,position.y, 0.0f, 1.0f);
	realPos =  view  * realPos;
	realPos.xy -= viewport.xy / 2; 
	
	gl_Position = projection * realPos;
	_textureCoord = textureCoord;
	_color = color;
}
