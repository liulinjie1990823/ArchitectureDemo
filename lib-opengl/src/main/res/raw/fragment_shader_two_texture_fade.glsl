precision mediump float;
varying vec2 ft_Position;
uniform float time;// 变化时间
uniform sampler2D s_Texture;
uniform sampler2D s_Texture1;
void main() {
    vec4 color = mix(texture2D(s_Texture, ft_Position), texture2D(s_Texture1, ft_Position), abs(sin(time)));
    gl_FragColor=vec4(color);
}