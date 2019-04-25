precision mediump float;
varying vec2 uv;
uniform float time;// 变化时间
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;


vec4 getFromColor(vec2 uv){
    return texture2D(s_Texture0, uv);
}

vec4 getToColor(vec2 uv){
    return texture2D(s_Texture1, uv);
}


float nQuick = clamp(0.8,0.2,1.0);

vec2 zoom(vec2 uv, float amount) {
    return 0.5 + ((uv - 0.5) * (1.0-amount));
}

void main() {
    float pct = smoothstep(nQuick-0.2, 1.0, progress);
    vec4 colorA=getFromColor(zoom(uv, smoothstep(0.0, nQuick, progress)));
    vec4 colorB=getToColor(uv);


    vec4 color = mix(colorA, colorB, pct);
    gl_FragColor=vec4(color);
}

