#version 400 core

in vec2 passTextureUV;

out vec4 outputColor;

uniform sampler2D texureSampler;

void main(void) {
    outputColor = texture(texureSampler, passTextureUV);
}
