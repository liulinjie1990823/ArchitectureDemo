package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.model.AnimResult;
import com.llj.lib.opengl.utils.MatrixHelper;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

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

    private Context mContext;

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mFragmentBuffer;

    private int mProgram;//gl程序

    private int mVPosition;//顶点位置
    private int mFPosition;//片元位置

    private int mTexture;
    private int mColorHandle;


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


    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private int mVboId;

    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 每个顶点的字节数


    private MatrixHelper mMatrixHelper;
    private AnimHelper   mAnimHelper;

    public BitmapRendererHandler(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mAnimHelper = new AnimHelper();

        mAnimParams = new ArrayList<>();

    }


    public void addAnimParam(AnimParam animParam) {
        mAnimParams.add(animParam);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        mVertexBuffer = createBuffer(mVertexData);
        mFragmentBuffer = createBuffer(mFragmentData);

        //创建程序
        mProgram = createProgram(mContext, R.raw.vertex_shader_screen_m, R.raw.fragment_shader_screen);

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(mProgram, V_POSITION);
        mFPosition = GLES20.glGetAttribLocation(mProgram, F_POSITION);
        mMatrixHelper = new MatrixHelper(mProgram, U_MATRIX);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, V_COLOR);
        mTexture = GLES20.glGetUniformLocation(mProgram, S_TEXTURE0);

        //创建vbo
        if (mUseVbo) {
            mVboId = createVbo(mVertexData, mFragmentData, mVertexBuffer, mFragmentBuffer);
        }


    }

    protected int getTexture() {
        return mTexture;
    }

    private List<Integer> mTextureList = new ArrayList<>();

    protected List<Integer> getTextureList() {
        return mTextureList;
    }

    private int mRatio = 1;
    private int mNear  = 3;
    private int mFar   = 20;

    private void transform(int width, int height, ShaderUtil.BitmapObject bitmapObject, AnimParam animParam) {
        float bitmapWidth = bitmapObject.width;
        float bitmapHeight = bitmapObject.height;

        float bitmapRatio = bitmapWidth / bitmapHeight;
        float surfaceRatio = width / (float) height;
        //Matrix.orthoM设置正交投影，使图片不会被拉伸
        if (width > height) {
            //横屏
            if (bitmapRatio > surfaceRatio) {
                mMatrixHelper.orthoM(-1f * mRatio, 1f * mRatio, (-height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, (height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, mNear, mFar);
            } else {
                mMatrixHelper.orthoM((-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, (width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, -1f * mRatio, 1f * mRatio, mNear, mFar);
            }
        } else {
            //竖屏
            if (bitmapRatio > surfaceRatio) {
                mMatrixHelper.orthoM(-1f * mRatio, 1f * mRatio, (-height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, (height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, mNear, mFar);
            } else {
                mLeft = (-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio * 2;
                mRight = (-1) * mLeft;
                mTop = (-1) * 1f * mRatio * 2;
                mBottom = (-1) * mTop;
                mMatrixHelper.orthoM((-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, (width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, -1f * mRatio, 1f * mRatio, mNear, mFar);
            }
        }
        mMatrixHelper.setLookAtM(0, 0, 10f, 0, 0, 0f, 0f, 1.0f, 0.0f);

        //设置最大偏移量
        if (animParam.direction == IAnim.LEFT) {
            animParam.maxDistance = mLeft;
        } else if (animParam.direction == IAnim.TOP) {
            animParam.maxDistance = mTop;
        } else if (animParam.direction == IAnim.RIGHT) {
            animParam.maxDistance = mRight;
        } else if (animParam.direction == IAnim.BOTTOM) {
            animParam.maxDistance = mBottom;
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, mTextureWidth, mTextureHeight);
    }

    private float mTop;
    private float mBottom;
    private float mLeft;
    private float mRight;


    private ArrayList<AnimParam> mAnimParams;


    private int mIndex;
    private int mCurrentIndex = -1;

    private AnimParam               mAnimParam    = null;
    private ShaderUtil.BitmapObject mBitmapObject = null;

    @Override
    public void onDrawFrame(GL10 gl) {
//        onClear();
//        onUseProgram();
//        onBindTexture();
//        onDraw();
//        unbind();


        int size = mAnimParams.size();

        if (mCurrentIndex == mIndex) {
            onClear();
        } else {
            mAnimParam = mAnimParams.get(mIndex % size);
            mBitmapObject = ShaderUtil.loadBitmapTexture(mContext, mAnimParam.resId, 0);
            transform(mTextureWidth, mTextureHeight, mBitmapObject, mAnimParam);
            mCurrentIndex = mIndex;
        }


        AnimResult calculate = mAnimHelper.calculate(mAnimParam);

        if (calculate.getScaleX() == mAnimParam.finalScale && calculate.getScaleY() == mAnimParam.finalScale) {
            mIndex++;
            mAnimHelper.reset();
        }


        mMatrixHelper.pushMatrix();
        mMatrixHelper.translate(calculate.getTranslateX(), calculate.getTranslateY(), calculate.getTranslateZ());
        mMatrixHelper.scale(calculate.getScaleX(), calculate.getScaleY(), calculate.getScaleZ());

        onUseProgram();
        onBindTexture(mBitmapObject.imgTextureId);
        onDraw();
        unbind();
        mMatrixHelper.popMatrix();
    }


    protected void onUseProgram() {
        GLES20.glUseProgram(mProgram);
    }

    protected void onBindTexture(int imgTextureId) {
        if (mTexture >= 0) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);
            GLES20.glUniform1i(mTexture, 0);
        }
    }

    private boolean mUseVbo = false;

    protected void onDraw() {
        //绑定vbo
        if (mUseVbo) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);
        }
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glEnableVertexAttribArray(mVPosition);
        GLES20.glEnableVertexAttribArray(mFPosition);

        if (mUseVbo) {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);
            GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexData.length * 4);
        } else {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
            GLES20.glVertexAttribPointer(mFPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mFragmentBuffer);
        }

        mMatrixHelper.glUniformMatrix4fv(1, false, 0);

        if (mColorHandle >= 0) {
            GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        }

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
