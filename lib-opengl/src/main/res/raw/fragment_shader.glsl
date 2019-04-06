#extension GL_OES_EGL_image_external : require
precision mediump float;
varying vec2 ft_Position;
uniform samplerExternalOES s_Texture;
void main() {
    gl_FragColor=texture2D(s_Texture, ft_Position);
}
