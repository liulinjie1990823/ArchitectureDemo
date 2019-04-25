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

```
attribute vec4 v_Position;//顶点坐标系
attribute vec2 f_Position;//纹理坐标系
varying vec2 ft_Position;//定义将要传递到片元着色器中的纹理坐标系
void main() {
    ft_Position = f_Position;
    gl_Position = v_Position;
}
```
1. 上面这种写法，需要在外部传入顶点坐标系和纹理坐标系，需要注意的是两个坐标系的点的位置需要对应，如：
```
    private float[] mVertexData = {
            -1f, -1f,//bottom left
            1f, -1f,//bottom right
            -1f, 1f,//top left
            1f, 1f//top right
    };
    //点的顺序需要和顶点坐标对应
    private float[] mFragmentData = {
            0f, 1f,//bottom left
            1f, 1f,//bottom right
            0f, 0f,//top left
            1f, 0f//top right
    };
```
需要分别为两个坐标系建立缓存对象，并将顶点数据设置到vbo中：
```
        mVertexBuffer = createBuffer(mVertexData);
        mFragmentBuffer = createBuffer(mFragmentData);
        mVboId = createVbo(mVertexData, mFragmentData, mVertexBuffer, mFragmentBuffer);
```
下面是vs中两坐标系变量的索引：
```
        mVPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        mFPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");
```
在onDrawFrame中将vbo中缓存的顶点数据设置到vs的索引mVPosition和mFPosition中：
```
    public void useVbo() {
        //绑定vbo
        if (mUseVbo) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);
        }
    }


    public void bindPosition() {
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        if (mVPosition >= 0) {
            GLES20.glEnableVertexAttribArray(mVPosition);
        }
        if (mFPosition >= 0) {
            GLES20.glEnableVertexAttribArray(mFPosition);
        }
        if (mUseVbo) {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, 0);
            if (mFPosition >= 0) {
                GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, VERTEX_STRIDE, mVertexDataSize);
            }
        } else {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
            if (mFPosition >= 0) {
                GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mFragmentBuffer);
            }
        }
    }
```
接着绑定纹理索引：
```
    public void onBindTexture(int imgTextureId, int index) {
        if (mTextureDataList.size() > index) {
            Integer textureData = mTextureDataList.get(index);
            if (textureData >= 0) {
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + index);//设置纹理可用
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//将已经处理好的纹理绑定到gl上
                GLES20.glUniform1i(textureData, index);//将第x个纹理设置到fragment_shader中进一步处理
            }
        }
    }
```
最后使用GL_TRIANGLE_STRIP绘制
```
        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);
```
这样在fs中可以通过纹理数据和纹理坐标系进行其他操作：
```
precision mediump float;
varying vec2 ft_Position;
uniform sampler2D s_Texture0;
void main() {
    gl_FragColor=texture2D(s_Texture0, ft_Position);
}
```
 ![image](http://img.freelooper.com/QQ20190424-180823@2x.png-resize.w200) 

显示结果如上，如果不进行矩阵变换，那么红框区域就是顶点坐标系所表示的区域，也就是gl_Position，顶点坐标系主要是规定了显示的区域；而上面设置的纹理坐标系
则是刚好获取的是整张的纹理，然后将纹理中的像素一个个的绘制到显示区域中。

2. 上面那种方式是需要将纹理的坐标定义在外面，其实也可以定义在vs着色器中；如果纹理取得是整张的纹理，在vs着色器中也可以通过将顶点坐标系进行向量换算以
转成纹理坐标系：
```
attribute vec4 v_Position;//顶点坐标系
attribute vec2 f_Position;//纹理坐标系
void main() {
    ft_Position = vec2(0.5f, 0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f));
    gl_Position = v_Position;
}
```
vec2(0.5f, 0.5f) * (vec2(v_Position) + vec2(1.0f, 1.0f))的运算最终将得到




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

###### smoothstep函数
```
float smoothstep(float a, float b, float x)
{
    float t = saturate((x - a)/(b - a));
    return t*t*(3.0 - (2.0*t));
}
```



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
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
