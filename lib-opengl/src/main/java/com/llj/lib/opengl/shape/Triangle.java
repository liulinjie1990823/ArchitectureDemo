package com.llj.lib.opengl.shape;

import android.content.Context;
import android.opengl.GLES20;
import android.os.SystemClock;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.render.LGLRenderer;
import com.llj.lib.opengl.utils.VaryTools;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:https://www.jianshu.com/p/26aa4e17d678
 * author llj
 * date 2019/4/1
 */
public class Triangle implements LGLRenderer {

    private Context mContext;

    private FloatBuffer mVertexBuffer;
    private int         mProgram;

//    private float triangleCoords[] = {   // in counterclockwise order:
//            0.0f, 0.622008459f, 0.0f, // top
//            -0.5f, -0.311004243f, 0.0f, // bottom left
//            0.5f, -0.311004243f, 0.0f  // bottom right
//    };

    private float triangleCoords[] = {   // in counterclockwise order:
            -0.5f, -0.5f, 0.0f,//bottom left
            0.5f, -0.5f, 0.0f,//bottom right
            -0.5f, 0.5f, 0.0f,//top left
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    public Triangle(Context context) {
        mContext = context;
        mVaryTools = new VaryTools();
    }


    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    private VaryTools mVaryTools;

    private static final int COORDINATE_PER_VERTEX = 3;//每个点的组成数量

    private final int vertexCount  = triangleCoords.length / COORDINATE_PER_VERTEX;
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 4 bytes per vertex

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        mVertexBuffer = createBuffer(triangleCoords);
        mProgram = createProgram(mContext, R.raw.vertex_shader_shape, R.raw.fragment_shader_color);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");

        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

    }
    private int mRatio = 6;
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        mVaryTools.frustumM(-ratio*mRatio, ratio*mRatio, -1f*mRatio, 1f*mRatio, 3, 7);
        mVaryTools.setLookAtM(0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        onClear();

        draw();
//
        mVaryTools.pushMatrix();
        mVaryTools.translate(2f, 0, 0);
        draw();
        mVaryTools.popMatrix();
    }

    private void draw() {
        GLES20.glUseProgram(mProgram);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

//        mVaryTools.pushMatrix();
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
//        mVaryTools.setRotateM( angle, 0, 0, -1.0f);
//        mVaryTools.translate(0,1,0);
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mVaryTools.getFinalMatrix(), 0);
//        mVaryTools.popMatrix();

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

//        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
