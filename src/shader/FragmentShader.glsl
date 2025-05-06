#version 400 core

in vec3 color;

out vec4 outputColor;

uniform double red;
uniform double green;
uniform double blue;

void main(void)
{
    outputColor = vec4(red, green, blue, 1.0);
}