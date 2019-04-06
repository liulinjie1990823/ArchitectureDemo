precision mediump float;
varying vec2 ft_Position;
uniform sampler2D s_Texture;
void main() {
    gl_FragColor=texture2D(s_Texture, ft_Position);
}
