//ok

precision mediump float;
varying vec2 uv;
uniform float progress;//0-1变化
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;

const float smoothness=0.3; // = 0.3
const bool opening=false; // = true

const vec2 center = vec2(0.5, 0.5);
const float SQRT_2 = 1.414213562373;


vec4 getFromColor(vec2 uv){
    return texture2D(s_Texture0, uv);
}

vec4 getToColor(vec2 uv){
    return texture2D(s_Texture1, uv);
}

vec4 transition (vec2 uv) {
    float x = opening ? progress : 1.-progress;
    float m = smoothstep(-smoothness, 0.0, SQRT_2*distance(center, uv) - x*(1.+smoothness));
    return mix(getFromColor(uv), getToColor(uv), opening ? 1.-m : m);
}

void main() {
    gl_FragColor=transition(uv);
}


