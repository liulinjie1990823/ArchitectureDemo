package com.llj.lib.opengl.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.support.annotation.DrawableRes;
import android.util.Log;

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
 * date 2019/4/1
 */
public class BitmapRenderImpl extends LGLRenderer {
    private static final String TAG = BitmapRenderImpl.class.getSimpleName();

    private              Context mContext;
    private @DrawableRes int     mResId;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mProgram;//gl程序
    private int mFboTextureId;//纹理id
    private int mImgTextureId;//纹理id
    private int mSampler;


    private float[] mVertexData = {
            -1f, -1f,//bottom left
            1f, -1f,//bottom right
            -1f, 1f,//top left
            1f, 1f//top right
    };

    //android中的片元坐标系,图片显示沿着x轴旋转了180度
    private float[] mFragmentData = {
            0f, 1f,//bottom left
            1f, 1f,//bottom right
            0f, 0f,//top left
            1f, 0f//top right
    };
    //fbo标准坐标系，显示正常
//    private float[] mFragmentData = {
//            0f, 0f,//bottom left
//            1f, 0f,//bottom right
//            0f, 1f,//top left
//            1f, 1f//top right
//    };

//    private float[] mFragmentData = {
//            0f, 0.5f,
//            0.5f, 0.5f,
//            0f, 0f,
//            0.5f, 0f
//    };


    private int           mVboId;
    private int           mFboId;
    private FboRenderImpl mFboRenderImpl;

    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * 4; // 4 bytes per vertex


    private int     mMatrix;
    private float[] mMatrixF = new float[16];

    private OnRenderCreateListener mOnRenderCreateListener;

    public void setOnRenderCreateListener(OnRenderCreateListener onRenderCreateListener) {
        mOnRenderCreateListener = onRenderCreateListener;
    }

    public interface OnRenderCreateListener {
        void onCreate(int textureId);
    }


    public BitmapRenderImpl(Context context, @DrawableRes int resId) {
        mContext = context;
        mResId = resId;
        mFboRenderImpl = new FboRenderImpl(context);
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

    private int createVbo() {
        //创建vbo
        int[] vbos = new int[1];
        GLES20.glGenBuffers(1, vbos, 0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbos[0]);
        //分配vbo缓存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mVertexData.length * 4 + mFragmentData.length * 4, null, GLES20.GL_STATIC_DRAW);
        //为vbo设置顶点数据,先设置mVertexData后设置mFragmentData
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, mVertexData.length * 4, mVertexBuffer);
        GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, mVertexData.length * 4, mFragmentData.length * 4, mFragmentBuffer);

        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        return vbos[0];
    }

    private int createFbo() {
        int[] fbos = new int[1];
        GLES20.glGenBuffers(1, fbos, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbos[0]);


        //创建一个纹理
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        mFboTextureId = textureIds[0];
        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mFboTextureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniform1i(mSampler, 0);


        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        //分配fbo内存,这里的宽高是竖屏的情况下，如果横屏需要重新设置，最好设置成surfaceView的宽高
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, 1080, 1920, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        //把纹理绑定到fbo
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, mFboTextureId, 0);
        //检查fbo是否绑定成功
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e(TAG, "fbo ERROR");
        } else {
            Log.e(TAG, "fbo success");
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
        return fbos[0];
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFboRenderImpl.onSurfaceCreated(gl, config);

        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader_screen_m);
        String fragmentSource = ShaderUtil.getRawResource(mContext, R.raw.fragment_shader_screen);

        mProgram = ShaderUtil.createProgram(vertexSource, fragmentSource);

        mVPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        mFPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");
        mSampler = GLES20.glGetUniformLocation(mProgram, "sTexture");
        mMatrix = GLES20.glGetUniformLocation(mProgram, "u_Matrix");

        //创建vbo
        mVboId = createVbo();

        //创建fbo
        mFboId = createFbo();

        //创建一个纹理
        mImgTextureId = createTexture(mResId);

        //
//        if (mOnRenderCreateListener != null) {
//            mOnRenderCreateListener.onCreate(mFboTextureId);
//        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        mFboRenderImpl.onSurfaceChanged(gl, width, height);

        float bitmapWidth = mBitmap.getWidth();
        float bitmapHeight = mBitmap.getHeight();

        float bitmapRatio = bitmapWidth / bitmapHeight;
        float surfaceRatio = width / (float) height;
        //Matrix.orthoM设置正交投影，使图片不会被拉伸
        if (width > height) {
            //横屏
            if (bitmapRatio > surfaceRatio) {
                Matrix.orthoM(mMatrixF, 0, -1, 1, -height / ((width / bitmapWidth) * bitmapHeight), height / ((width / bitmapWidth) * bitmapHeight), -1f, 1f);
            } else {
                Matrix.orthoM(mMatrixF, 0, -width / ((height / bitmapHeight) * bitmapWidth), width / ((height / bitmapHeight) * bitmapWidth), -1f, 1f, -1f, 1f);
            }
        } else {
            //竖屏
            if (bitmapRatio > surfaceRatio) {
                Matrix.orthoM(mMatrixF, 0, -1, 1, -height / ((width / bitmapWidth) * bitmapHeight), height / ((width / bitmapWidth) * bitmapHeight), -1f, 1f);
            } else {
                Matrix.orthoM(mMatrixF, 0, -width / ((height / bitmapHeight) * bitmapWidth), width / ((height / bitmapHeight) * bitmapWidth), -1f, 1f, -1f, 1f);
            }
        }
        Matrix.rotateM(mMatrixF, 0, 180, 1, 0, 0);

    }

    private Bitmap mBitmap;

    private int createTexture(@DrawableRes int resId) {
        //526*702
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), resId);
        if (mBitmap != null && !mBitmap.isRecycled()) {

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
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, mBitmap, 0);

            //解绑
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            return textureIds[0];
        }
        return 0;
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFboId);

        //清屏
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //设置颜色
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(mMatrix, 1, false, mMatrixF, 0);

        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mImgTextureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);

        GLES20.glEnableVertexAttribArray(mVPosition);
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        //
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        mFboRenderImpl.onDrawFrame(gl, mFboTextureId);
    }

}
