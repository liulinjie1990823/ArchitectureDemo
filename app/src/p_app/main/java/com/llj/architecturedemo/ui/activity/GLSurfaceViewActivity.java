package com.llj.architecturedemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.widget.MyGLSurfaceView;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.opengl.render.BitmapRenderImpl;
import com.llj.lib.opengl.render.MultiRenderImpl;
import com.llj.lib.opengl.utils.ShaderUtil;

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

    @Override
    public int layoutId() {
        return R.layout.activity_gl_surfaceview;
    }


    private ArrayList<String> mFilters = new ArrayList<>();

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        setTranslucentStatusBar(getWindow(), true);

        mShareGLContextFactory = new ShareGLContextFactory(null);

        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader1));
        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader2));
        mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader3));

        BitmapRenderImpl renderer = new BitmapRenderImpl(mContext, DisplayHelper.SCREEN_WIDTH, DisplayHelper.SCREEN_HEIGHT - mLinearLayout.getLayoutParams().height);
        renderer.setResId(R.drawable.androids);
        renderer.setOnRenderCreateListener(new BitmapRenderImpl.OnRenderCreateListener() {
            @Override
            public void onCreate(int textureId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mLinearLayout.getChildCount() > 0) {
                            mLinearLayout.removeAllViews();
                        }

                        for (int i = 0; i < 3; i++) {
                            MyGLSurfaceView myGLSurfaceView = new MyGLSurfaceView(GLSurfaceViewActivity.this);
                            myGLSurfaceView.setEGLContextFactory(new ShareGLContextFactory(mShareGLContextFactory.getShareContext()));
                            MultiRenderImpl multiRender = new MultiRenderImpl(GLSurfaceViewActivity.this);
                            multiRender.setTextureId(textureId);
                            multiRender.setFragmentSource(mFilters.get(i));

                            myGLSurfaceView.setRenderer(multiRender);

                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                            lp.height = 400;
                            lp.width = lp.height * DisplayHelper.SCREEN_WIDTH / (DisplayHelper.SCREEN_HEIGHT - mLinearLayout.getLayoutParams().height);

                            myGLSurfaceView.setLayoutParams(lp);

                            mLinearLayout.addView(myGLSurfaceView);
                        }
                    }
                });
            }
        });
        mGLSurfaceView.setEGLContextFactory(mShareGLContextFactory);
        mGLSurfaceView.setRenderer(renderer);
    }

    @Override
    public void initData() {

    }

    private ShareGLContextFactory mShareGLContextFactory;

    private class ShareGLContextFactory implements GLSurfaceView.EGLContextFactory {
        private int        EGL_CONTEXT_CLIENT_VERSION = 0x3098;
        private EGLContext mShareContext;

        ShareGLContextFactory(EGLContext shareContext) {
            mShareContext = shareContext == null ? EGL10.EGL_NO_CONTEXT : shareContext;
        }

        public EGLContext getShareContext() {
            return mShareContext;
        }

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] attr_list = {EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE};
            mShareContext = egl.eglCreateContext(display, config, mShareContext, attr_list);
            return mShareContext;
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            if (!egl.eglDestroyContext(display, context)) {
                Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
            }
        }
    }

}
