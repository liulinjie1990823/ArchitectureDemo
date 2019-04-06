package com.llj.lib.opengl.render;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class TextureRenderImpl implements LGLRenderer {
    private static final String TAG = TextureRenderImpl.class.getSimpleName();

    private FrameBuffer           mFrameBuffer;
    private BitmapRendererHandler mBitmapRendererHandler;
    private CommonRenderImpl      mCommonRenderImpl;

    private int mTextureWidth;
    private int mTextureHeight;

    private int mSurfaceWidth;
    private int mSurfaceHeight;

    @DrawableRes int mResId;

    private OnRenderCreateListener mOnRenderCreateListener;

    public void setOnRenderCreateListener(OnRenderCreateListener onRenderCreateListener) {
        mOnRenderCreateListener = onRenderCreateListener;
    }

    public interface OnRenderCreateListener {
        void onCreate(int textureId);
    }

    public TextureRenderImpl(Context context, int textureWidth, int textureHeight) {
        mTextureWidth = textureWidth;
        mTextureHeight = textureHeight;

        mBitmapRendererHandler = new BitmapRendererHandler(context, textureWidth, textureHeight);
        mFrameBuffer = new FrameBuffer();
        mCommonRenderImpl = new CommonRenderImpl(context);
    }

    public void setResId(int resId) {
        mResId = resId;
        mBitmapRendererHandler.setResId(resId);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCommonRenderImpl.onSurfaceCreated(gl, config);


        mBitmapRendererHandler.onSurfaceCreated(gl, config);

        mFrameBuffer.createFbo(mBitmapRendererHandler.getTexture(), mTextureWidth, mTextureHeight);

        //fbo纹理id回调
        if (mOnRenderCreateListener != null) {
            mOnRenderCreateListener.onCreate(mFrameBuffer.getFboTextureId());
        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //记录Surface显示区域宽高
        mSurfaceWidth = width;
        mSurfaceHeight = height;

        //
        mCommonRenderImpl.onSurfaceChanged(gl, mSurfaceWidth, mSurfaceHeight);

    }


    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e(TAG, "onDrawFrame");

        mFrameBuffer.beginDrawToFrameBuffer();

        mBitmapRendererHandler.onDrawFrame(gl);

        mFrameBuffer.endDrawToFrameBuffer();

        mCommonRenderImpl.onDrawFrame(gl, mFrameBuffer.getFboTextureId());
    }

}
