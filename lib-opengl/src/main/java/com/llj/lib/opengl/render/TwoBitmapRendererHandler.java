package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.anim.ITransition;
import com.llj.lib.opengl.anim.Wipe;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.utils.MatrixHelper;
import com.llj.lib.opengl.utils.ShaderUtil;
import com.llj.lib.opengl.utils.TextureHelper;
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
public class TwoBitmapRendererHandler implements IFboRender {
    public static final String TAG = TwoBitmapRendererHandler.class.getSimpleName();

    private Context mContext;

    private int mProgram;//gl程序

    private float[] mVertexData = {
            1.0f, -1.0f,//bottom right
            1.0f, 1.0f,//top right
            -1.0f, 1.0f,//top left
            -1.0f, -1.0f,//bottom left
    };

    //点的顺序需要和顶点坐标对应
    private float[] mFragmentData = {
            1f, 1f,//bottom right
            1f, 0f,//top right
            0f, 0f,//top left
            0f, 1f,//bottom left
    };


    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量
    private final        int vertexCount           = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点

    private ArrayList<AnimParam> mAnimParams;

    private MatrixHelper               mMatrixHelper;
    private VertexBufferHelper         mVertexBufferHelper;
    private TextureHelper<ITransition> mTextureHelper;

    public TwoBitmapRendererHandler(Context context, int textureWidth, int textureHeight) {
        mContext = context;
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mAnimParams = new ArrayList<>();

    }


    public void addAnimParam(AnimParam animParam) {
        mAnimParams.add(animParam);
    }

    protected List<Integer> getTextureList() {
        if (mTextureHelper != null) {
            return mTextureHelper.getTextureList();
        }
        return new ArrayList<>();
    }

    private List<ShaderUtil.BitmapObject> mBitmapObjects = new ArrayList<>();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //创建程序
        mTextureHelper = new TextureHelper<>(mContext, Wipe.LEFT());
        mProgram = mTextureHelper.getProgram();

        //创建顶点缓存
        mVertexBufferHelper = new VertexBufferHelper(mVertexData, mFragmentData, mProgram, V_POSITION, F_POSITION);

        //创建矩阵
        mMatrixHelper = new MatrixHelper(mProgram, U_MATRIX);

        //创建TextureId
        int size = mAnimParams.size();
        for (int i = 0; i < size; i++) {
            AnimParam animParam = mAnimParams.get(i);
            mBitmapObjects.add(ShaderUtil.loadBitmapTexture(mContext, animParam.resId, i));
        }

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, mTextureWidth, mTextureHeight);

        if (mTextureHelper != null && mBitmapObjects.size() != 0) {
            transform(mTextureWidth, mTextureHeight, mBitmapObjects.get(0));
        }
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();

        mMatrixHelper.pushMatrix();
        onUseProgram();
        onDraw();
        mMatrixHelper.popMatrix();

        unbind();
    }


    protected void onUseProgram() {
        GLES20.glUseProgram(mProgram);
    }


    protected void onDraw() {
        mTextureHelper.bindProgress();
        mTextureHelper.bindProperties();

        mVertexBufferHelper.useVbo();
        mVertexBufferHelper.bindPosition();

        bindTextures();

        mMatrixHelper.glUniformMatrix4fv(1, false, 0);

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
    }


    public void bindTextures() {
        int size = mBitmapObjects.size();
        for (int i = 0; i < size; i++) {
            ShaderUtil.BitmapObject bitmapObject = mBitmapObjects.get(i);
            mTextureHelper.bindTexture(bitmapObject.imgTextureId, i);
        }
    }

    @Override
    public void unbind() {
        mTextureHelper.unbind();
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
