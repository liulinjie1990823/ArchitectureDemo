//ok

precision mediump float;
varying vec2 uv;
uniform float progress;//0-1变化
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;

uniform int direction;

float switchA(vec2 p){
    if (direction==0){
        return 1.0-p.x;
    } else if (direction==1){
        return 0.0+p.y;
    } else if (direction==2){
        return 0.0+p.x;
    } else if (direction==3){
        return 1.0-p.y;
    }
    return 1.0-p.x;
}


vec4 getFromColor(vec2 uv){
    return texture2D(s_Texture0, uv);
}

vec4 getToColor(vec2 uv){
    return texture2D(s_Texture1, uv);
}

vec4 transition(vec2 uv) {
    vec2 p=uv.xy/vec2(1.0).xy;
    vec4 a=getFromColor(p);
    vec4 b=getToColor(p);
    return mix(a, b, step(switchA(p), progress));
}

void main() {
    gl_FragColor=transition(uv);
}


