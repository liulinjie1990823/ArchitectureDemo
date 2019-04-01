package com.llj.lib.opengl.shape;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.R;
import com.llj.lib.opengl.utils.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class Triangle {

    private Context mContext;

    private FloatBuffer vertexBuffer;
    private  int mProgram;

    // number of coordinates per vertex in this array
    final   int   COORDS_PER_VERTEX = 3;
    private float triangleCoords[]  = {   // in counterclockwise order:
            0.0f, 0.622008459f, 0.0f, // top
            -0.5f, -0.311004243f, 0.0f, // bottom left
            0.5f, -0.311004243f, 0.0f  // bottom right
    };

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};

    public Triangle(Context context) {
        mContext=context;

        vertexBuffer= ByteBuffer.allocateDirect(triangleCoords.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(triangleCoords);
        vertexBuffer.position(0);

        String vertexSource = ShaderUtil.getRawResource(mContext, R.raw.vertex_shader_shape);
        String fragmentSource = ShaderUtil.getRawResource(mContext, R.raw.fragment_shader_shape);

        mProgram = ShaderUtil.createProgram(vertexSource, fragmentSource);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    public void draw() {
        // Add program to OpenGL ES environment
        GLES20.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);


        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        // Set color for drawing the triangle
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);
        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }
}
