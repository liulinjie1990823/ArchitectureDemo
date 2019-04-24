attribute vec4 v_Position;//顶点坐标系
attribute vec2 f_Position;//纹理坐标系
varying vec2 ft_Position;//定义将要传递到片元着色器中的纹理坐标系
void main() {
    ft_Position = vec2(0.5f, -0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f));
    gl_Position = v_Position;
}