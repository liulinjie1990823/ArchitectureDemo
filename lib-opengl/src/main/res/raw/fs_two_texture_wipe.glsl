//ok

precision mediump float;
varying vec2 uv;
uniform float progress;//0-1变化
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;

uniform int direction;


vec4 getFromColor(vec2 uv){
    return texture2D(s_Texture0, uv);
}

vec4 getToColor(vec2 uv){
    return texture2D(s_Texture1, uv);
}

vec4 transition (vec2 uv) {
    return mix(
    getFromColor(uv),
    getToColor(uv),
    progress
    );
}

void main() {
    gl_FragColor=transition(uv);
}


