package com.llj.architecturedemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.widget.MyGLSurfaceView;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.opengl.render.BitmapRenderImpl;
import com.llj.lib.opengl.utils.EglHelper;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import butterknife.BindView;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/28
 */
@Route(path = CRouter.APP_GLSURFACE_VIEW_ACTIVITY)
public class GLSurfaceViewActivity extends AppMvcBaseActivity {
    @BindView(R.id.surfaceView) MyGLSurfaceView mGLSurfaceView;
    @BindView(R.id.ly_content)  LinearLayout    mLinearLayout;

    private EglHelper mEglHelper = new EglHelper();

    @Override
    public int layoutId() {
        return R.layout.activity_gl_surfaceview;
    }


    private ArrayList<String> mFilters = new ArrayList<>();

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
//        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader1));
//        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader2));
//        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader3));

        BitmapRenderImpl renderer = new BitmapRenderImpl(mContext, R.drawable.androids);
        renderer.setOnRenderCreateListener(new BitmapRenderImpl.OnRenderCreateListener() {
            @Override
            public void onCreate(int textureId) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        if (mLinearLayout.getChildCount() > 0) {
//                            mLinearLayout.removeAllViews();
//                        }
//
//                        for (int i = 0; i < 3; i++) {
//                            MyGLSurfaceView myGLSurfaceView = new MyGLSurfaceView(GLSurfaceViewActivity.this);
//                            myGLSurfaceView.setEGLContextFactory(new ShareGLContextFactory(mEglHelper.getEglContext()));
//                            MultiRenderImpl multiRender = new MultiRenderImpl(GLSurfaceViewActivity.this);
//                            multiRender.setTextureId(textureId);
//                            multiRender.setFragmentSource(mFilters.get(i));
//
//                            myGLSurfaceView.setRenderer(multiRender);
//
//                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                            lp.width = 200;
//                            lp.height = 300;
//                            myGLSurfaceView.setLayoutParams(lp);
//
//                            mLinearLayout.addView(myGLSurfaceView);
//                        }
//                    }
//                });
            }
        });
        mGLSurfaceView.setRenderer(renderer);
//        mGLSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                mEglHelper.initEgl(holder.getSurface(), null);
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//            }
//        });
    }

    @Override
    public void initData() {

    }

    private class ShareGLContextFactory implements GLSurfaceView.EGLContextFactory {
        private int        EGL_CONTEXT_CLIENT_VERSION = 0x3098;
        private EGLContext mShareContext              = null;

        ShareGLContextFactory(EGLContext shareContext) {
            mShareContext = shareContext;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attr_list = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE};
            return egl.eglCreateContext(display, config, mShareContext, attr_list);
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
            }
        }
    }

}
