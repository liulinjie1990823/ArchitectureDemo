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
import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.render.MultiRenderImpl;
import com.llj.lib.opengl.render.TextureRenderImpl;
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

        TextureRenderImpl renderer = new TextureRenderImpl(mContext, DisplayHelper.SCREEN_WIDTH, DisplayHelper.SCREEN_HEIGHT - mLinearLayout.getLayoutParams().height);

        renderer.addAnimParam(new AnimParam(IAnim.LEFT, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.androids));
        renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.img_1));
        renderer.addAnimParam(new AnimParam(IAnim.RIGHT, 3F, 1F, 1.05F, 500L, 1000L, R.drawable.img_2));
        renderer.addAnimParam(new AnimParam(IAnim.BOTTOM, 4F, 1F, 1.05F, 500L, 1000L, R.drawable.img_3));

        renderer.setOnRenderCreateListener(new TextureRenderImpl.OnRenderCreateListener() {
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
        mGLSurfaceView.setMyRender(renderer);
    }

    @Override
    public void initData() {
//        startImgs();
    }

    private void startImgs() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= 10; i++) {
                    int imgSrc = getResources().getIdentifier("img_" + i, "drawable", "com.llj.architecturedemo");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
