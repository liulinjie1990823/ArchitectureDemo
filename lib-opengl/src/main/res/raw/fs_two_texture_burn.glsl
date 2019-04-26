

precision mediump float;
varying vec2 uv;
uniform float progress;//0-1变化
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;


vec4 getFromColor(vec2 uv){
    return texture2D(s_Texture0, uv);
}

vec4 getToColor(vec2 uv){
    return texture2D(s_Texture1, uv);
}

uniform vec3 color /* = vec3(0.9, 0.4, 0.2) */;
vec4 transition (vec2 uv) {
    return mix(
    getFromColor(uv) + vec4(progress*color, 1.0),
    getToColor(uv) + vec4((1.0-progress)*color, 1.0),
    progress
    );
}


void main() {
    gl_FragColor=transition(uv);
}


