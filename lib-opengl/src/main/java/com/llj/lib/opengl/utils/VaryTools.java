package com.llj.lib.opengl.utils;

import android.opengl.Matrix;

import java.util.Arrays;
import java.util.Stack;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/3
 */
public class VaryTools {
    private boolean mUseMatrixCamera     = false;
    private boolean mUseMatrixProjection = false;
    private boolean mUseMatrixCurrent    = false;

    private float[] mMatrixCamera     = new float[16];    //相机矩阵
    private float[] mMatrixProjection = new float[16];    //投影矩阵
    private float[] mMatrixCurrent    =     //原始矩阵
            {1, 0, 0, 0,
                    0, 1, 0, 0,
                    0, 0, 1, 0,
                    0, 0, 0, 1};

//    private float[] mMatrixCurrent = new float[16];

    private Stack<float[]> mStack;      //变换矩阵堆栈

    public VaryTools() {
        mStack = new Stack<>();
    }

    //保护现场
    public void pushMatrix() {
        mStack.push(Arrays.copyOf(mMatrixCurrent, 16));
    }

    //恢复现场
    public void popMatrix() {
        mMatrixCurrent = mStack.pop();
    }

    public void clearStack() {
        mStack.clear();
    }

    //平移变换
    public void translate(float x, float y, float z) {
        Matrix.translateM(mMatrixCurrent, 0, x, y, z);
        mUseMatrixCurrent = true;
    }

    //旋转变换
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mMatrixCurrent, 0, angle, x, y, z);
        mUseMatrixCurrent = true;
    }

    public void setRotateM(float angle, float x, float y, float z) {
        Matrix.setRotateM(mMatrixCurrent, 0, angle, x, y, z);
        mUseMatrixCurrent = true;
    }

    //缩放变换
    public void scale(float x, float y, float z) {
        Matrix.scaleM(mMatrixCurrent, 0, x, y, z);
        mUseMatrixCurrent = true;
    }

    //设置相机
    public void setLookAtM(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        Matrix.setLookAtM(mMatrixCamera, 0, ex, ey, ez, cx, cy, cz, ux, uy, uz);
        mUseMatrixCamera = true;
    }

    //透视投影
    public void frustumM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mMatrixProjection, 0, left, right, bottom, top, near, far);
        mUseMatrixProjection = true;
    }

    //正交投影
    public void orthoM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mMatrixProjection, 0, left, right, bottom, top, near, far);
        mUseMatrixProjection = true;
    }

    public float[] getFinalMatrix() {
        float[] ans = new float[16];

        if (mUseMatrixProjection && mUseMatrixCurrent && mUseMatrixCamera) {
            Matrix.multiplyMM(ans, 0, mMatrixCamera, 0, mMatrixCurrent, 0);
            Matrix.multiplyMM(ans, 0, mMatrixProjection, 0, ans, 0);
            return ans;
        } else if (mUseMatrixProjection && mUseMatrixCamera) {
            Matrix.multiplyMM(ans, 0, mMatrixProjection, 0, mMatrixCamera, 0);
            return ans;
        } else if (mUseMatrixProjection && mUseMatrixCurrent) {
            Matrix.multiplyMM(ans, 0, mMatrixProjection, 0, mMatrixCurrent, 0);
            return ans;
        } else if (mUseMatrixCurrent && mUseMatrixCamera) {
            Matrix.multiplyMM(ans, 0, mMatrixCamera, 0, mMatrixCurrent, 0);
            return ans;
        } else if (mUseMatrixCamera) {
            return mMatrixCamera;
        } else if (mUseMatrixProjection) {
            return mMatrixProjection;
        } else if (mUseMatrixCurrent) {
            return mMatrixCurrent;
        }
        return mMatrixCurrent;
    }
}
