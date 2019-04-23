attribute vec4 v_Position;
varying vec2 ft_Position;
uniform mat4 u_Matrix;
void main() {
    gl_Position = u_Matrix * v_Position;
    ft_Position = vec2(0.5f, 0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f));
}
