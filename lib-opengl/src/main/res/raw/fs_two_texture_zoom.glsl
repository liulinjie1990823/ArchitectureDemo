precision mediump float;
varying vec2 ft_Position;
uniform float time;// 变化时间
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;


vec4 getFromColor(vec2 ft_Position){
    return texture2D(s_Texture0, ft_Position);
}

vec4 getToColor(vec2 ft_Position){
    return texture2D(s_Texture1, ft_Position);
}

void main() {
    //正弦，时间累加，结果一直是0-1
    float pct = abs(sin(time));

    vec4 colorA=getFromColor(ft_Position);
    vec4 colorB=getToColor(ft_Position);

    vec4 color = mix(colorA, colorB, pct);

    gl_FragColor=vec4(color);
}

