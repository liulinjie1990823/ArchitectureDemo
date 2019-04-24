# lib-opengl

https://www.cnblogs.com/mazhenyu/p/6439304.html
https://blog.csdn.net/hk_shao/article/details/82084274

## vertex shader
###### Attributes
该坐标值一般是由glVertex* 或者是glDraw*传入的。他有（vec2,vec3,vec4），可能代表了一个空间坐标(x,y,z,w),
或者代表了一个颜色(r,g,b,a),再或者代表一个纹理坐标(s,t,p,q)
```
attribute vec4 v_Position
```
```
//输入顶点坐标
GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mFragmentBuffer);
```
###### gl_Position
顶点坐标，这个是经过几何变换后的坐标。

>功能：简单的说就是把输入的顶点坐标乘以（一系列）几何变换矩阵。每输入一个顶点（也就是glVertex*每调用一次），Vertex shader都会被调用一次。
Vertex Shader只知道处理顶点，它不知道这些顶点是做什么用的，也就是不知道这些顶点将来会被装配成什么图元。（因为Vertex shader后面才会有图
元装配的过程）当然，VS还可以接收颜色，纹理坐标，雾坐标等属性，并在内部对他们做一点点变化，然后再输出。

## fragment shader
###### Varying
varying 变量用于存储顶点着色器的输出数据，当然也存储片元着色器的输入数据，varying 变量最终会在光栅化处理阶段被线性插值。
顶点着色器如果声明了varying变量，它必须被传递到片元着色器中才能进一步传递到下一阶段，因此顶点着色器中声明的 varying 变量都应在片元着
色器中重新声明同名同类型的 varying 变量
###### Uniforms
uniforms保存由应用程序传递给着色器的只读常量数据。在顶点着色器中，这些数据通常是变换矩阵，光照参数，颜色等。由uniform修
饰符修饰的变量属于全局变量，该全局性对顶点着色器与片元着色器均可见，也就是说，这两个着色器如果被连接到同一个应用程序中，它们共享同一份
uniform 全局变量集。因此如果在这两个着色器中都声明了同名的 uniform 变量，要保证这对同名变量完全相同：同名+同类型，因为它们实际是同一个
变量。此外，uniform 变量存储在常量存储区
###### mix函数
```
void main() {
    vec4 color = mix(texture2D(s_Texture0, ft_Position), texture2D(s_Texture1, ft_Position), time);
    gl_FragColor=vec4(color);
}
```
如果第三个值是0.0，它会返回第一个输入；如果是1.0，会返回第二个输入值。0.2会返回80%的第一个输入颜色和20%的第二个输入颜色，即返回两个纹理的混合色。


## 方法使用
![image](http://img.freelooper.com/QQ20190424-151959.png-resize.w300)  
![image](http://img.freelooper.com/QQ20190424-152132.png-resize.w300)
###### glActiveTexture 
设置纹理可用，需要先调用
###### glBindTexture 
把已经处理好的Texture传到GL上面
###### glUniform1i 
将已经绑定到GL上面的纹理传递到fragment_shader中的纹理中，对纹理做进一步的处理，
如果纹理绑定后又解绑了则不能使用，必须是已经绑定到GL上面的纹理
###### glDrawArrays 
绘制形状
- 绘制三角形：绘制出来是实心的
https://blog.csdn.net/kunluo/article/details/70238931   
 1.GL_TRIANGLES：
（v0，v1，v2）、（v3，v4，v5）、（v0，v3，v4）  
 ![image](http://img.freelooper.com/QQ20190424-152335@2x.png-resize.w300)  
 2. GL_LINE_STRIP  
 如果当前顶点是奇数：组成三角形的顶点排列顺序：T = [n-1 n-2 n].  
 如果当前顶点是偶数：组成三角形的顶点排列顺序：T = [n-2 n-1 n].  
 如下图，第一个顶点是2：（v0，v1，v2）；第二个顶点是3：（v2，v1，v3）；第三个顶点是4：（v2，v3，v4）；
（v0，v1，v2）、（v2，v1，v3）、（v0，v3，v4）  
 ![image](http://img.freelooper.com/QQ20190424-152357@2x.png-resize.w300)  
 3. GL_TRIANGLE_FAN：该中规则比较容易理解和记忆  
（v0，v1，v2）、（v3，v4，v5）、（v0，v3，v4）  
 ![image](http://img.freelooper.com/QQ20190424-152406@2x.png-resize.w300)
 ```
     private float[] mVertexData = {
            -1f, -1f,//bottom left
            1f, -1f,//bottom right
            -1f, 1f,//top left
            1f, 1f//top right
    };
    //如果按上面定义的顶点画矩形，要用GL_LINE_STRIP规则画
    
    private float[] mVertexData = {
            1.0f, -1.0f,//bottom right
            1.0f, 1.0f,//top right
            -1.0f, 1.0f,//top left
            -1.0f, -1.0f//bottom left
    };
    //如果按上面定义的顶点画矩形，要用GL_TRIANGLE_FAN规则画
 ```
- 绘制线段：绘制出来是空心的，只有线有颜色
###### 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
