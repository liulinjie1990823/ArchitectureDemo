precision mediump float;
varying vec2 ft_Position;
uniform sampler2D s_Texture;
void main() {
    gl_FragColor = vec4(vec3(1.0 - texture2D(s_Texture, ft_Position)), 1.0);
}
