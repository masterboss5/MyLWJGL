#version 400 core

in vec3 position;
in vec2 textureUV;

out vec2 passTextureUV;

void main(void) {
    gl_Position = vec4(position, 1.0);
    passTextureUV = textureUV;
}
