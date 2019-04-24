attribute vec4 v_Position;//顶点坐标系
attribute vec2 f_Position;//纹理坐标系
varying vec2 ft_Position;//传递纹理坐标系
uniform mat4 u_Matrix;//矩阵
void main() {
    ft_Position = f_Position;
    gl_Position = u_Matrix * v_Position;
}
