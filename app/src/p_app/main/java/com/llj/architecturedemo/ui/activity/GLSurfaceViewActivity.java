package com.llj.architecturedemo.ui.activity;

import android.os.Bundle;

import com.llj.architecturedemo.AppMvcBaseActivity;
import com.llj.architecturedemo.R;
import com.llj.architecturedemo.widget.MyGLSurfaceView;

import org.jetbrains.annotations.Nullable;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/28
 */
public class GLSurfaceViewActivity extends AppMvcBaseActivity {
    private MyGLSurfaceView mGLSurfaceView;

    @Override
    public int layoutId() {
        return R.layout.activity_gl_surfaceview;
    }

    @Override
    public void initViews(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

}
