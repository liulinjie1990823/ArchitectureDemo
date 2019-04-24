precision mediump float;
varying vec2 ft_Position;
uniform float time;// 变化时间
uniform int index;// 循环第几次
uniform int size;// 循环几次
uniform sampler2D s_Texture0;
uniform sampler2D s_Texture1;
void main() {
    float m = smoothstep(-0.5F, 0., ft_Position.x - abs(sin(time))*1.5F);
    gl_FragColor = mix(texture2D(s_Texture0, (ft_Position - 0.5F) * (1.0F - m) + 0.5F), texture2D(s_Texture1, (ft_Position - 0.5F) * m + 0.5F), m);

    if(index%size==0){
        //渐变
        vec4 color = mix(texture2D(s_Texture0, ft_Position), texture2D(s_Texture1, ft_Position), abs(sin(time)));
        gl_FragColor=vec4(color);
    }else{

    }
}