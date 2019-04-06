package com.llj.lib.opengl.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.render.LGLRenderer;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class Triangle implements LGLRenderer {

    private Context mContext;

    private FloatBuffer mVertexBuffer;
    private int         mProgram;

    private float triangleCoords[] = {   // in counterclockwise order:
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    public Triangle(Context context) {
        mContext = context;
        init();
    }



    @Override
    public void init() {
        mVertexBuffer=createBuffer(triangleCoords);
        mProgram= createProgram(mContext,R.raw.vertex_shader_shape,R.raw.fragment_shader_color);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private static final int COORDINATE_PER_VERTEX = 3;//每个点的组成数量

    private final int vertexCount  = triangleCoords.length / COORDINATE_PER_VERTEX;
    private final int vertexStride = COORDINATE_PER_VERTEX * BYTES_PER_FLOAT; // 4 bytes per vertex

    public void onSurfaceCreated() {
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    }

    public void draw() {
        GLES20.glUseProgram(mProgram);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDINATE_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, mVertexBuffer);

        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
