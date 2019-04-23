precision mediump float;
varying vec2 ft_Position;
uniform float time;// 变化时间
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;
void main() {
    float m = smoothstep(-0.5F, 0., ft_Position.x - abs(sin(time))*1.5F);
    gl_FragColor = mix(texture2D(s_Texture0, (ft_Position - 0.5F) * (1.0F - m) + 0.5F), texture2D(s_Texture1, (ft_Position - 0.5F) * m + 0.5F), m);

}