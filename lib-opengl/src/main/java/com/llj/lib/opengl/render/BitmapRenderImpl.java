package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

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

    public static final String V_POSITION = "v_Position";
    public static final String F_POSITION = "f_Position";
    public static final String U_MATRIX   = "u_Matrix";
    public static final String S_TEXTURE  = "sTexture";

    private              Context mContext;
    private @DrawableRes int     mResId;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mSampler;

    private int mProgram;//gl程序

    private ShaderUtil.BitmapObject mBitmapObject;


    private int mSurfaceWidth;
    private int mSurfaceHeight;


    private float[] mVertexData = {
            -1f, -1f,//bottom left
            1f, -1f,//bottom right
            -1f, 1f,//top left
            1f, 1f//top right
    };

//    private float[] mVertexData = {
//            -0.5f, -0.5f,//bottom left
//            0.5f, -0.5f,//bottom right
//            -0.5f, 0.5f,//top left
//            0.5f, 0.5f//top right
//    };

    //android中的片元坐标系,图片显示沿着x轴旋转了180度
//    private float[] mFragmentData = {
//            0f, 1f,//bottom left
//            1f, 1f,//bottom right
//            0f, 0f,//top left
//            1f, 0f//top right
//    };
    //fbo标准坐标系，显示正常
    private float[] mFragmentData = {
            0f, 0f,//bottom left
            1f, 0f,//bottom right
            0f, 1f,//top left
            1f, 1f//top right
    };

//    private float[] mFragmentData = {
//            0f, 0.5f,
//            0.5f, 0.5f,
//            0f, 0f,
//            0.5f, 0f
//    };

    private int       mVboId;
    private FboObject mFboObject;

    private int mTextureWidth;
    private int mTextureHeight;

    private FboRenderImpl mFboRenderImpl;

    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量
    private static final int BYTES_PER_FLOAT       = 4;//float占用的字节

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 每个顶点的字节数


    private int     mMatrix;
    private float[] mMatrixF = new float[16];

    private OnRenderCreateListener mOnRenderCreateListener;

    public void setOnRenderCreateListener(OnRenderCreateListener onRenderCreateListener) {
        mOnRenderCreateListener = onRenderCreateListener;
    }

    public interface OnRenderCreateListener {
        void onCreate(int textureId);
    }

    public BitmapRenderImpl(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mFboRenderImpl = new FboRenderImpl(context);
        init();
    }

    public void setResId(@DrawableRes int resId) {
        mResId = resId;
    }

    @Override
    public void init() {
        mVertexBuffer = createBuffer(mVertexData);
        mFragmentBuffer = createBuffer(mFragmentData);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFboRenderImpl.onSurfaceCreated(gl, config);

        //创建
        mProgram = createProgram(mContext, R.raw.vertex_shader_screen_m, R.raw.fragment_shader_screen);

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(mProgram, V_POSITION);
        mFPosition = GLES20.glGetAttribLocation(mProgram, F_POSITION);
        mSampler = GLES20.glGetUniformLocation(mProgram, S_TEXTURE);
        mMatrix = GLES20.glGetUniformLocation(mProgram, U_MATRIX);

        //创建vbo
        mVboId = createVbo(mVertexData, mFragmentData, mVertexBuffer, mFragmentBuffer);

        //创建fbo
        mFboObject = createFbo(mSampler, mTextureWidth, mTextureHeight);

        //创建一个纹理
        mBitmapObject = ShaderUtil.loadBitmapTexture(mContext, mResId);

        //fbo纹理id回调
        if (mOnRenderCreateListener != null) {
            mOnRenderCreateListener.onCreate(mFboObject.fboTextureId);
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //记录Surface显示区域宽高
        mSurfaceWidth = width;
        mSurfaceHeight = height;

        //
        mFboRenderImpl.onSurfaceChanged(gl, mSurfaceWidth, mSurfaceHeight);

    }

    private void transform(int width, int height, ShaderUtil.BitmapObject bitmapObject) {
        float bitmapWidth = bitmapObject.width;
        float bitmapHeight = bitmapObject.height;

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
//        Matrix.rotateM(mMatrixF, 0, 180, 1, 0, 0);
    }


    @Override
    public void onDrawFrame(GL10 gl) {

        Log.e(TAG, "onDrawFrame");

        GLES20.glViewport(0, 0, mTextureWidth, mTextureHeight);
        transform(mTextureWidth, mTextureHeight, mBitmapObject);
//        gl.glTranslatef(5, 0, 0);
//        Matrix.translateM(mMatrixF,0,5,0,0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, mFboObject.fboId);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); //清屏
        GLES20.glClearColor(1f, 0f, 0f, 1f); //设置颜色

        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(mMatrix, 1, false, mMatrixF, 0);


        //绑定纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBitmapObject.imgTextureId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);

        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glEnableVertexAttribArray(mVPosition);
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);

        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);

        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        //解绑fbo
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

//        GLES20.glViewport(0, 0, mSurfaceWidth, mSurfaceHeight);
        mFboRenderImpl.onDrawFrame(gl, mFboObject.fboTextureId);
    }

}
