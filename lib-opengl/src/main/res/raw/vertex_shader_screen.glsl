attribute vec4 v_Position;
attribute vec2 f_Position;
varying vec2 ft_Position;
void main() {
    ft_Position = f_Position;
//    ft_Position = vec2(0.5f, 0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f));
    gl_Position = v_Position;
}
