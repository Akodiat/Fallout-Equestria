#version 330

layout(location = 0) in vec2 vertexPos;
layout(location = 1) in vec2 texCoord;


out vec2 _texCoord;
out vec4 _color;

uniform mat4 matrix;
uniform vec4 color;



void main() {
	vec4 realPos = vec4(vertexPos.x,vertexPos.y, 0.0f, 1.0f);
	gl_Position = matrix * realPos;
	_color = color;
	_texCoord = texCoord;
}
