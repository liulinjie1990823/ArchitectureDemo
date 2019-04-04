package com.llj.lib.opengl.utils;

import android.opengl.Matrix;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/3
 */
public class VaryTools {

    private float[] mMatrixProjection = new float[16];    //投影矩阵
    private float[] mMatrixCamera     = new float[16];    //相机矩阵


    //设置相机
    public void setLookAtM(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        Matrix.setLookAtM(mMatrixCamera, 0, ex, ey, ez, cx, cy, cz, ux, uy, uz);
    }

    public void frustumM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mMatrixProjection, 0, left, right, bottom, top, near, far);
    }

    //正交投影
    public void orthoM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mMatrixProjection, 0, left, right, bottom, top, near, far);
    }

    public float[] getFinalMatrix(float[] spec) {
        float[] ans = new float[16];
        Matrix.multiplyMM(ans, 0, mMatrixCamera, 0, spec, 0);
        Matrix.multiplyMM(ans, 0, mMatrixProjection, 0, ans, 0);
        return ans;
    }
}
