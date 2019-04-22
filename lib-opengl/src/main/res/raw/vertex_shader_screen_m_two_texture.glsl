attribute vec4 v_Position;
varying vec2 ft_Position;
void main() {
    gl_Position = vec4(vec2(v_Position), 0.0, 1.0);
    ft_Position = vec2(0.5, 0.5) * (vec2(v_Position) + vec2(1.0, 1.0));
}
