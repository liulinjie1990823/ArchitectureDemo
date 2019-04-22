package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.utils.MatrixHelper;
import com.llj.lib.opengl.utils.ShaderUtil;
import com.llj.lib.opengl.utils.VertexBufferHelper;

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

    private int mProgram;//gl程序

    private int mTime;//矩阵转换


    private List<Integer> mTextureList = new ArrayList<>();
    private float[]       mVertexData  = {
            1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
            -1.0f, -1.0f
    };


    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点


    private MatrixHelper       mMatrixHelper;
    private VertexBufferHelper mVertexBufferHelper;

    public BitmapRendererHandler2(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mAnimParams = new ArrayList<>();

    }


    public void addAnimParam(AnimParam animParam) {
        mAnimParams.add(animParam);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        //创建程序
        mProgram = createProgram(mContext, R.raw.vs_screen_m_two_texture, R.raw.fs_two_texture_fade);

        //创建顶点缓存
        mVertexBufferHelper = new VertexBufferHelper(mVertexData, mProgram, V_POSITION);
        //创建矩阵
        mMatrixHelper = new MatrixHelper(mProgram, U_MATRIX);

        mTextureList.clear();
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE));
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE_1));
        mTime = GLES20.glGetUniformLocation(mProgram, TIME);


        //创建Texture
        int size = mAnimParams.size();
        for (int i = 0; i < size; i++) {
            AnimParam animParam = mAnimParams.get(i);
            mBitmapObjects.add(ShaderUtil.loadBitmapTexture(mContext, animParam.resId, i));
        }
    }

    private List<ShaderUtil.BitmapObject> mBitmapObjects = new ArrayList<>();


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, mTextureWidth, mTextureHeight);
    }


    private ArrayList<AnimParam> mAnimParams;


    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();

        mMatrixHelper.pushMatrix();
        onUseProgram();
        onDraw();
        unbind();
        mMatrixHelper.popMatrix();

    }


    protected void onUseProgram() {
        GLES20.glUseProgram(mProgram);
    }

    protected void onBindTexture(int imgTextureId, int index) {
        Integer texture = mTextureList.get(index);
        if (texture >= 0) {
            GLES20.glActiveTexture(ShaderUtil.getTexture(index));//设置纹理可用
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//将已经处理好的纹理绑定到gl上
            GLES20.glUniform1i(texture, index);//将第x个纹理设置到fragment_shader中进一步处理
        }
    }

    private float mTimePlus;

    protected void onDraw() {
        if (mTime >= 0) {
            mTimePlus += 0.01F;
            GLES20.glUniform1f(mTime, mTimePlus);
        }

        mVertexBufferHelper.useVbo();
        mVertexBufferHelper.bindPosition();

        int size = mBitmapObjects.size();
        for (int i = 0; i < size; i++) {
            ShaderUtil.BitmapObject bitmapObject = mBitmapObjects.get(i);
            transform(mTextureWidth, mTextureHeight, bitmapObject);
            onBindTexture(bitmapObject.imgTextureId, i);
        }

        mMatrixHelper.glUniformMatrix4fv(1, false, 0);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
    }

    protected void unbind() {
        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        //
        mVertexBufferHelper.unbind();
    }

    private int mRatio = 1;
    private int mNear  = 3;
    private int mFar   = 20;

    private void transform(int width, int height, ShaderUtil.BitmapObject bitmapObject) {
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
                mMatrixHelper.orthoM((-width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, (width / ((height / bitmapHeight) * bitmapWidth)) * mRatio, -1f * mRatio, 1f * mRatio, mNear, mFar);
            }
        }
        mMatrixHelper.setLookAtM(0, 0, 10f, 0, 0, 0f, 0f, 1.0f, 0.0f);
    }
}
