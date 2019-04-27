attribute vec4 v_Position;//顶点坐标系
attribute vec2 f_Position;//纹理坐标系
varying vec2 uv;//定义将要传递到片元着色器中的纹理坐标系
void main() {
    uv = f_Position;
    gl_Position = v_Position;
}