package com.llj.architecturedemo.ui.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import butterknife.BindView;
import butterknife.internal.DebouncingOnClickListener;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.MainMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.R2;
import com.llj.architecturedemo.widget.MyGLSurfaceView;
import com.llj.application.router.CRouter;
import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.opengl.anim.IAnim;
import com.llj.lib.opengl.encoder.WlMediaEncodec;
import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.render.TextureRenderImpl;
import com.llj.lib.opengl.utils.ShaderUtil;
import com.ywl5320.libmusic.WlMusic;
import com.ywl5320.listener.OnPreparedListener;
import com.ywl5320.listener.OnShowPcmDataListener;
import java.util.ArrayList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo. describe: author llj date 2019/3/28
 */
@Route(path = CRouter.APP_GLSURFACE_VIEW_ACTIVITY)
public class GLSurfaceViewActivity extends MainMvcBaseActivity {

  @BindView(R2.id.surfaceView) MyGLSurfaceView mGLSurfaceView;
  @BindView(R2.id.ly_content)  LinearLayout    mLinearLayout;

  private WlMusic        wlMusic;
  private WlMediaEncodec wlMediaEncodec;

  @Override
  public int layoutId() {
    return R.layout.activity_gl_surfaceview;
  }


  private ArrayList<String> mFilters = new ArrayList<>();

  private int mWidth;
  private int mHeight;

  @Override
  public void initViews(@Nullable Bundle savedInstanceState) {
    setTranslucentStatusBar(getWindow(), true);

    mWidth = DisplayHelper.SCREEN_WIDTH;
    mHeight = DisplayHelper.SCREEN_HEIGHT - mLinearLayout.getLayoutParams().height;

    mShareGLContextFactory = new ShareGLContextFactory(null);

    wlMusic = WlMusic.getInstance();
    wlMusic.setCallBackPcmData(true);

    wlMusic.setOnPreparedListener(new OnPreparedListener() {
      @Override
      public void onPrepared() {
        wlMusic.playCutAudio(0, 60);
      }
    });
    wlMusic.setOnShowPcmDataListener(new OnShowPcmDataListener() {
      @Override
      public void onPcmInfo(int samplerate, int bit, int channels) {
        wlMediaEncodec = new WlMediaEncodec(GLSurfaceViewActivity.this,
            mGLSurfaceView.getFboTextureId());
        wlMediaEncodec.initEncodec(mShareGLContextFactory.getShareContext(),
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/wl_image_video.mp4",
            mWidth, mHeight, samplerate, channels);
        wlMediaEncodec.startRecord();
        startImgs();
      }

      @Override
      public void onPcmData(byte[] pcmdata, int size, long clock) {
        if (wlMediaEncodec != null) {
          wlMediaEncodec.putPCMData(pcmdata, size);
        }
      }
    });

    mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader1));
    mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader2));
    mFilters.add(ShaderUtil.getRawResource(mContext, com.llj.lib.opengl.R.raw.fragment_shader3));

    TextureRenderImpl renderer = new TextureRenderImpl(mContext, mWidth, mHeight);

    //        renderer.addAnimParam(new AnimParam(IAnim.LEFT, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.androids));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_1));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_2));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_3));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_4));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_5));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_6));
    renderer.addAnimParam(new AnimParam(IAnim.TOP, 2F, 1F, 1.05F, 500L, 1000L, R.drawable.image_7));

    //        renderer.setOnRenderCreateListener(new TextureRenderImpl.OnRenderCreateListener() {
    //            @Override
    //            public void onCreate(int textureId) {
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
    //                            myGLSurfaceView.setEGLContextFactory(new ShareGLContextFactory(mShareGLContextFactory.getShareContext()));
    //                            MultiRenderImpl multiRender = new MultiRenderImpl(GLSurfaceViewActivity.this);
    //                            multiRender.setTextureId(textureId);
    //                            multiRender.setFragmentSource(mFilters.get(i));
    //
    //                            myGLSurfaceView.setRenderer(multiRender);
    //
    //                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    //                            lp.height = 400;
    //                            lp.width = lp.height * DisplayHelper.SCREEN_WIDTH / (DisplayHelper.SCREEN_HEIGHT - mLinearLayout.getLayoutParams().height);
    //
    //                            myGLSurfaceView.setLayoutParams(lp);
    //
    //                            mLinearLayout.addView(myGLSurfaceView);
    //                        }
    //                    }
    //                });
    //            }
    //        });
    mGLSurfaceView.setEGLContextFactory(mShareGLContextFactory);
    mGLSurfaceView.setMyRender(renderer);

    mLinearLayout.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View v) {
        wlMusic.setSource(
            Environment.getExternalStorageDirectory().getAbsolutePath() + "/the girl.m4a");
        wlMusic.prePared();

      }
    });
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
          int imgSrc = getResources()
              .getIdentifier("img_" + i, "drawable", "com.llj.architecturedemo");
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
