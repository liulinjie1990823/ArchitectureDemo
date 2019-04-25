precision mediump float;
varying vec2 ft_Position;
uniform float progress;//0-1变化
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;


vec4 getFromColor(vec2 ft_Position){
    return texture2D(s_Texture0, ft_Position);
}

vec4 getToColor(vec2 ft_Position){
    return texture2D(s_Texture1, ft_Position);
}

void main() {

    vec4 colorA=getFromColor(ft_Position);
    vec4 colorB=getToColor(ft_Position);

    vec4 color = mix(colorA, colorB, progress);

    gl_FragColor=vec4(color);
}

