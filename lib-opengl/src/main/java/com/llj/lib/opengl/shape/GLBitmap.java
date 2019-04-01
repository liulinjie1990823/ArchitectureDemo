package com.llj.lib.opengl.shape;

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

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class GLBitmap {
    private              Context mContext;
    private @DrawableRes int     mResId;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mProgram;//gl程序
    private int mTextureId;//纹理id
    private int mSampler;


    private float[] mVertexData = {
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    private float[] mFragmentData = {
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
//                0f, 0.5f,
//                0.5f, 0.5f,
//                0f, 0f,
//                0.5f, 0f
    };

    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * 4; // 4 bytes per vertex


    public GLBitmap(Context context, @DrawableRes int resId) {
        mContext = context;
        mResId = resId;
        init();
    }

    private void init() {
        mVertexBuffer = (FloatBuffer) ByteBuffer.allocateDirect(mVertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mVertexData)
                .position(0);


        mFragmentBuffer = (FloatBuffer) ByteBuffer.allocateDirect(mFragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(mFragmentData)
                .position(0);
    }

    public void onSurfaceCreated() {
        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader_screen);
        String fragmentSource = ShaderUtil.getRawResource(mContext, R.raw.fragment_shader_screen);

        mProgram = ShaderUtil.createProgram(vertexSource, fragmentSource);

        mVPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        mFPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");
        mSampler = GLES20.glGetUniformLocation(mProgram, "sTexture");

        //创建一个纹理
        mTextureId = createTexture(mResId);
    }

    private int createTexture(@DrawableRes int resId) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
        if (bitmap != null && !bitmap.isRecycled()) {

            //创建一个纹理
            int[] textureIds = new int[1];
            GLES20.glGenTextures(1, textureIds, 0);

            //绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glUniform1i(mSampler, 0);

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
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            //解绑
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            return textureIds[0];
        }
        return 0;
    }

    public void onDrawFrame() {
        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置颜色
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(mProgram);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        GLES20.glEnableVertexAttribArray(mVPosition);
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mFragmentBuffer);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }
}
