package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.utils.ShaderUtil;
import com.llj.lib.opengl.utils.VaryTools;

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
public class BitmapRendererHandler2 implements LGLRenderer {

    public static final String TAG = BitmapRendererHandler2.class.getSimpleName();

    private Context mContext;

    private FloatBuffer mVertexBuffer;

    private int mProgram;//gl程序

    private int mVPosition;//顶点位置
    private int mUMatrix;//矩阵转换
    private int mTime;//矩阵转换

    private int mColorHandle;

    private List<Integer> mTextureList = new ArrayList<>();
    private float[]       mVertexData  = {
            1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
            -1.0f, -1.0f
    };



    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    private int mVboId;

    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 每个顶点的字节数


    private VaryTools  mVaryTools;

    public BitmapRendererHandler2(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mVaryTools = new VaryTools();

        mAnimParams = new ArrayList<>();

    }


    public void addAnimParam(AnimParam animParam) {
        mAnimParams.add(animParam);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        mVertexBuffer = createBuffer(mVertexData);

        //创建程序
        mProgram = createProgram(mContext, R.raw.vertex_shader_screen_m_two_texture, R.raw.fragment_shader_two_texture_step);

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(mProgram, V_POSITION);
        mColorHandle = GLES20.glGetUniformLocation(mProgram, V_COLOR);
        mTextureList.clear();
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE));
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE_1));
        mUMatrix = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
        mTime = GLES20.glGetUniformLocation(mProgram, TIME);

        //创建vbo
        if (mUseVbo) {
            mVboId = createVbo(mVertexData);
        }

        //创建Texture
        int size = mAnimParams.size();
        for (int i = 0; i < size; i++) {
            AnimParam animParam = mAnimParams.get(i);
            mBitmapObjects.add(ShaderUtil.loadBitmapTexture(mContext, animParam.resId, i));
        }
    }

    private List<ShaderUtil.BitmapObject> mBitmapObjects = new ArrayList<>();


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
                mVaryTools.orthoM(-1f * mRatio, 1f * mRatio, (-height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, (height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, mNear, mFar);
            } else {
                mVaryTools.orthoM((-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, (width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, -1f * mRatio, 1f * mRatio, mNear, mFar);
            }
        } else {
            //竖屏
            if (bitmapRatio > surfaceRatio) {
                mVaryTools.orthoM(-1f * mRatio, 1f * mRatio, (-height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, (height / ((width / bitmapWidth) * bitmapHeight)) * mRatio, mNear, mFar);
            } else {
                mLeft = (-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio * 2;
                mRight = (-1) * mLeft;
                mTop = (-1) * 1f * mRatio * 2;
                mBottom = (-1) * mTop;
                mVaryTools.orthoM((-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, (width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, -1f * mRatio, 1f * mRatio, mNear, mFar);
            }
        }
        mVaryTools.setLookAtM(0, 0, 10f, 0, 0, 0f, 0f, 1.0f, 0.0f);

        //设置最大偏移量
        if (animParam != null) {
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


    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();
//        onUseProgram();
//        onBindTexture();
//        onDraw();
//        unbind();


        onUseProgram();

        int size = mBitmapObjects.size();
        for (int i = 0; i < size; i++) {
            ShaderUtil.BitmapObject bitmapObject = mBitmapObjects.get(i);
//            transform(mTextureWidth, mTextureHeight, bitmapObject, null);
            onBindTexture(bitmapObject.imgTextureId, i);
        }
//        mVaryTools.pushMatrix();

        onDraw();
//        unbind();

//        mVaryTools.popMatrix();
    }


    protected void onUseProgram() {
        GLES20.glUseProgram(mProgram);
    }

    protected void onBindTexture(int imgTextureId, int index) {
        Integer texture = mTextureList.get(index);
        if (texture >= 0) {
            GLES20.glActiveTexture(ShaderUtil.getTexture(index));
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);
            GLES20.glUniform1i(texture, index);
        }
    }

    private boolean mUseVbo = false;

    private float mTimePlus;

    protected void onDraw() {
        if (mTime >= 0) {
            mTimePlus += 0.01F;
            GLES20.glUniform1f(mTime, mTimePlus);
        }

        //绑定vbo
        if (mUseVbo) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mVboId);
        }
        //可以从mVertexBuffer中拿数据，如果设置为offset则是偏移量，表示从vbo中获取数据
        GLES20.glEnableVertexAttribArray(mVPosition);

        if (mUseVbo) {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, 0);
        } else {
            GLES20.glVertexAttribPointer(mVPosition, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        }

        if (mUMatrix >= 0) {
            GLES20.glUniformMatrix4fv(mUMatrix, 1, false, mVaryTools.getFinalMatrix(), 0);
        }

        if (mColorHandle >= 0) {
            GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        }

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(mVPosition);
    }

    protected void unbind() {
        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
