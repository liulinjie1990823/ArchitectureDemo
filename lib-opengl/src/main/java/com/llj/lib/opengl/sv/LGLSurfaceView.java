package com.llj.lib.opengl.sv;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/1
 */
public class LGLSurfaceView extends GLSurfaceView {

    public LGLSurfaceView(Context context) {
        super(context);
        init();
    }

    public LGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEGLContextClientVersion(2);
    }
}
