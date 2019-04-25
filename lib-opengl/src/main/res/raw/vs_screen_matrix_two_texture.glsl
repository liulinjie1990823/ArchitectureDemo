attribute vec4 v_Position;//顶点坐标系
varying vec2 ft_Position;//传递纹理坐标系
uniform mat4 u_Matrix;//矩阵
void main() {
    gl_Position = u_Matrix * v_Position;
    ft_Position = vec2(0.5f, 0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f));
}

