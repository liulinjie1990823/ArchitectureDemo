package com.llj.architecturedemo.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.render.TextureRenderImpl;
import com.llj.lib.opengl.sv.LGLSurfaceView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/28
 */
public class MyGLSurfaceView extends LGLSurfaceView {

    private TextureRenderImpl mBitmapRender;

    private int mFboTextureId;

    private Context mContext;

    public MyGLSurfaceView(Context context) {
        super(context);
        mContext=context;
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
    }


    public void setMyRender(TextureRenderImpl bitmapRender) {
        mBitmapRender = bitmapRender;
        setRenderer(bitmapRender);
        mBitmapRender.setOnRenderCreateListener(new TextureRenderImpl.OnRenderCreateListener() {
            @Override
            public void onCreate(int textureId) {
                mFboTextureId = textureId;
            }
        });


//        setRenderer(new ShapeRenderImpl(mContext));
//        setRenderMode(LGLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void addAnimParam(AnimParam animParam) {
        if (mBitmapRender != null) {
            mBitmapRender.addAnimParam(animParam);
            requestRender();
        }
    }

}
