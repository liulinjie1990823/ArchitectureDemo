package com.llj.architecturedemo.widget;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;

import com.llj.lib.opengl.render.BitmapRenderImpl;
import com.llj.lib.opengl.sv.LGLSurfaceView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/28
 */
public class MyGLSurfaceView extends LGLSurfaceView {

    private BitmapRenderImpl mBitmapRender;

    private int mFboTextureId;

    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setMyRender(BitmapRenderImpl bitmapRender) {
        mBitmapRender = bitmapRender;
        setRenderer(bitmapRender);
//        setRenderMode(LGLSurfaceView.RENDERMODE_WHEN_DIRTY);
//
        mBitmapRender.setOnRenderCreateListener(new BitmapRenderImpl.OnRenderCreateListener() {
            @Override
            public void onCreate(int textureId) {
                mFboTextureId = textureId;
            }
        });
    }

    public void setCurrentImg(@DrawableRes int resId) {
        if (mBitmapRender != null) {
            mBitmapRender.setResId(resId);
            requestRender();
        }
    }

}
