package com.llj.lib.opengl.render;

import android.content.Context;
import android.util.Log;

import com.llj.lib.opengl.model.AnimParam;

import java.util.ArrayList;
import java.util.List;

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

    private TwoBitmapRendererHandler mBitmapRendererHandler;
    private CommonRenderImpl         mCommonRenderImpl;

    private int mTextureWidth;
    private int mTextureHeight;

    private int mSurfaceWidth;
    private int mSurfaceHeight;


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

        mBitmapRendererHandler = new TwoBitmapRendererHandler(context, textureWidth, textureHeight);
        mCommonRenderImpl = new CommonRenderImpl(context);
    }

    public void addAnimParam(AnimParam animParam) {
        mBitmapRendererHandler.addAnimParam(animParam);
    }

    private List<FrameBuffer> mFrameBuffers = new ArrayList<>();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mCommonRenderImpl.onSurfaceCreated(gl, config);

        mBitmapRendererHandler.onSurfaceCreated(gl, config);

        int size = mBitmapRendererHandler.getTextureList().size();
        for (int i = 0; i < size; i++) {
            FrameBuffer frameBuffer = new FrameBuffer();
            Integer texture = mBitmapRendererHandler.getTextureList().get(i);
            frameBuffer.createFbo(texture, mTextureWidth, mTextureHeight);
            mFrameBuffers.add(frameBuffer);
        }

        //fbo纹理id回调
//        if (mOnRenderCreateListener != null) {
//            mOnRenderCreateListener.onCreate(mFrameBuffer.getFboTextureId());
//        }

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //记录Surface显示区域宽高
        mSurfaceWidth = width;
        mSurfaceHeight = height;

        //
        mBitmapRendererHandler.onSurfaceChanged(gl, mSurfaceWidth, mSurfaceHeight);
        mCommonRenderImpl.onSurfaceChanged(gl, mSurfaceWidth, mSurfaceHeight);

    }


    @Override
    public void onDrawFrame(GL10 gl) {
        Log.e(TAG, "onDrawFrame");
        mBitmapRendererHandler.onDrawFrame(gl);

//        for (int i = 0; i < mFrameBuffers.size(); i++) {
//            FrameBuffer frameBuffer = mFrameBuffers.get(i);
//
//            frameBuffer.beginDrawToFrameBuffer();
//            mBitmapRendererHandler.onDrawFrame(gl);
//            frameBuffer.endDrawToFrameBuffer();
//
//            mCommonRenderImpl.onDrawFrame(gl, frameBuffer.getFboTextureId());
//        }
    }

}
