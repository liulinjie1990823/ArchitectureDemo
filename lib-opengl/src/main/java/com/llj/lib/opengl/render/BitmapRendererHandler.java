package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.support.annotation.DrawableRes;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/4
 */
public class BitmapRendererHandler implements LGLRenderer {

    public static final String TAG = BitmapRendererHandler.class.getSimpleName();

    static final String V_POSITION = "v_Position";
    static final String F_POSITION = "f_Position";
    static final String U_MATRIX   = "u_Matrix";
    static final String S_TEXTURE  = "sTexture";

    private              Context mContext;
    private @DrawableRes int     mResId;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mProgram;//gl程序

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置
    private int mTexture;


    private ShaderUtil.BitmapObject mBitmapObject;


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

    private int mVboId;

    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 每个顶点的字节数


    private int     mMatrix;
    private float[] mMatrixF = new float[16];



    public BitmapRendererHandler(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        init();
    }


    public void setResId(int resId) {
        mResId = resId;
    }

    public void init() {
        mVertexBuffer = createBuffer(mVertexData);
        mFragmentBuffer = createBuffer(mFragmentData);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建程序
        mProgram = createProgram(mContext, R.raw.vertex_shader_screen_m, R.raw.fragment_shader_screen);

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(mProgram, V_POSITION);
        mFPosition = GLES20.glGetAttribLocation(mProgram, F_POSITION);
        mTexture = GLES20.glGetUniformLocation(mProgram, S_TEXTURE);
        mMatrix = GLES20.glGetUniformLocation(mProgram, U_MATRIX);

        //创建vbo
        mVboId = createVbo(mVertexData, mFragmentData, mVertexBuffer, mFragmentBuffer);

        //创建一个纹理
        mBitmapObject = ShaderUtil.loadBitmapTexture(mContext, mResId);
        transform(mTextureWidth, mTextureHeight, mBitmapObject);

    }

    protected int getTexture() {
        return mTexture;
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
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, mTextureWidth, mTextureHeight);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();
        onUseProgram();
        onBindTexture();
        onDraw();
        unbind();
    }

    protected void onClear() {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT); //清屏
        GLES20.glClearColor(1f, 0f, 0f, 1f); //设置颜色
    }

    protected void onUseProgram() {
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(mMatrix, 1, false, mMatrixF, 0);
    }

    protected void onBindTexture() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mBitmapObject.imgTextureId);
        GLES20.glUniform1i(mTexture, 0);
    }

    protected void onDraw() {
        //绑定vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glEnableVertexAttribArray(mVPosition);
        GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);
        GLES20.glEnableVertexAttribArray(mFPosition);
        GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexCount);
    }

    protected void unbind() {
        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
