package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.widget.MyGLSurfaceView;
import com.llj.component.service.arouter.CRouter;
import com.llj.lib.opengl.render.ColorRender;
import com.llj.lib.opengl.render.ShapeRender;
import com.llj.lib.opengl.render.TextureRender;
import com.llj.lib.opengl.shape.Triangle;

import org.jetbrains.annotations.Nullable;

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

    @Override
    public int layoutId() {
        return R.layout.activity_gl_surfaceview;
    }

    private Triangle mTriangle;

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {
        TextureRender textureRender = new TextureRender(mContext,R.drawable.androids);

        ColorRender colorRender = new ColorRender(mContext);

        mGLSurfaceView.setRenderer(new ShapeRender(mContext));
//        mGLSurfaceView.setRenderer(colorRender);
//        mGLSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//
//                mTriangle = new Triangle();
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//                GLES20.glViewport(0, 0, width, height);
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//                mTriangle.draw();
//            }
//        });

    }

    @Override
    public void initData() {

    }

}
