package com.llj.lib.opengl.render;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
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
    private int mTime;//矩阵转换


    private List<Integer> mTextureList = new ArrayList<>();
    private float[]       mVertexData  = {
            1.0f, -1.0f,
            1.0f, 1.0f,
            -1.0f, 1.0f,
            -1.0f, -1.0f
    };

    private int mVboId;

    private int mTextureWidth;
    private int mTextureHeight;


    private static final int COORDINATE_PER_VERTEX = 2;//每个点的组成数量

    private final int vertexCount  = mVertexData.length / COORDINATE_PER_VERTEX;//需要绘制几个顶点
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 每个顶点的字节数


    private VaryTools mVaryTools;

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
        mProgram = createProgram(mContext, R.raw.vs_screen_m_two_texture, R.raw.fs_two_texture_fade);

        //或者对应变量
        mVPosition = GLES20.glGetAttribLocation(mProgram, V_POSITION);
        mTextureList.clear();
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE));
        mTextureList.add(GLES20.glGetUniformLocation(mProgram, S_TEXTURE_1));
        mTime = GLES20.glGetUniformLocation(mProgram, TIME);

        //创建vbo
        if (mUseVbo) {
            mVboId = createVbo(mVertexData, mVertexBuffer);
        }

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


        onDraw();
        unbind();

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

    private boolean mUseVbo = true;

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

        int size = mBitmapObjects.size();
        for (int i = 0; i < size; i++) {
            ShaderUtil.BitmapObject bitmapObject = mBitmapObjects.get(i);
            onBindTexture(bitmapObject.imgTextureId, i);
        }

        //绘制4个点0-4
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
    }

    protected void unbind() {
        //绘制多个纹理需要解绑解绑纹理
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glDisableVertexAttribArray(mVPosition);
        //解绑vbo
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
    }
}
