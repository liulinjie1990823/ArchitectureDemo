package com.llj.lib.opengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.annotation.DrawableRes;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public class TextureRender implements LGLRenderer {

    private Context context;
    private @DrawableRes int mResId;

    private FloatBuffer vertexBuffer;
    private FloatBuffer fragmentBuffer;

    private int program;

    private int vPosition;
    private int fPosition;
    private int textureId;


    public TextureRender(Context context,@DrawableRes int resId) {
        this.context = context;
        this.mResId = resId;

        float[] vertexData = {
                -1f, -1f,
                1f, -1f,
                -1f, 1f,
                1f, 1f
        };
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        float[] fragmentData = {
//            0f, 1f,
//            1f, 1f,
//            0f, 0f,
//            1f, 0f

                0f, 0.5f,
                0.5f, 0.5f,
                0f, 0f,
                0.5f, 0f
        };
        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        String vertexSource = ShaderUtil.getRawResource(context, R.raw.vertex_shader_screen);
        String fragmentSource = ShaderUtil.getRawResource(context, R.raw.fragment_shader_screen);

        program = ShaderUtil.createProgram(vertexSource, fragmentSource);

        vPosition = GLES20.glGetAttribLocation(program, "v_Position");
        fPosition = GLES20.glGetAttribLocation(program, "f_Position");
        int sampler = GLES20.glGetUniformLocation(program, "sTexture");


        //创建一个纹理
        textureId = createTexture(mResId);

        GLES20.glUniform1i(sampler, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }


    private int createTexture(@DrawableRes int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        if (bitmap != null && !bitmap.isRecycled()) {
            int[] textureIds = new int[1];

            //创建一个纹理
            GLES20.glGenTextures(1, textureIds, 0);

            //绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

            //纹理环绕方式 GLES20.GL_REPEAT
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);


            //边缘拉伸 GLES20.GL_CLAMP_TO_EDGE //https://blog.csdn.net/cassiepython/article/details/51645791
            //GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);


            //过滤方式 https://blog.csdn.net/sharp2wing/article/details/5683379
            //缩小情况：GLES20.GL_TEXTURE_MIN_FILTER
            //GL_NEAREST 最近邻过滤
            //GL_NEAREST_MIPMAP_NEAREST 使用MIP贴图的最近邻过滤
            //GL_NEAREST_MIPMAP_LINEAR 使用MIP贴图级别之间插值的最近邻过滤
            //GL_LINEAR 双线性过滤
            //GL_LINEAR_MIPMAP_NEAREST 使用MIP贴图的双线性过滤
            //GL_LINEAR_MIPMAP_LINEAR 三线性过滤 （使用MIP贴图级别之间插值的双线性过滤 ）

            //最近邻过滤：这个比较简单，每个像素的纹理坐标，并不是刚好对应一个采样点时，按照最接近的采样点进行采样。当放大纹理时，锯齿会比较明显，每个纹理单元都显示为一个小方块。当缩小时，会丢失许多细节。
            //双线性过滤：使用双线性插值平滑像素之间的过渡。放大时，锯齿看起来会比最近邻过滤少许多，看起来更加平滑。
            //MIP贴图 ：当缩小到一定程度时，使用双线性过滤会失去太多细节，还可能引起噪声以及物体移动过程中的闪烁，为了克服这些问题就有了MIP贴图技术。关于MIP贴图 ，维基上有更详细解释 ：https://en.wikipedia.org/wiki/Mipmap
            //三线性过滤 ：用于消除每个MIP贴图级别之间的过渡，得到一个更为平滑的图像。

            //放大情况：GLES20.GL_TEXTURE_MAG_FILTER
            //GL_NEAREST
            //GL_LINEAR

            //过滤方式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            //解绑
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            return textureIds[0];
        }
        return 0;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置颜色
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(program);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, fragmentBuffer);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

    }

}
