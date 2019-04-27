precision mediump float;
varying vec2 uv;
uniform sampler2D s_Texture0;
void main() {
    gl_FragColor=texture2D(s_Texture0, uv);
}