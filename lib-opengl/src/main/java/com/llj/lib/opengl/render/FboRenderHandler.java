package com.llj.lib.opengl.render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-23
 */
public class FboRenderHandler<T extends IFboRender> implements LGLRenderer {

    private IFboRender mFboRender;

    public FboRenderHandler(IFboRender fboRender) {
        mFboRender = fboRender;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFboRender.onSurfaceCreated(gl,config);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mFboRender.onSurfaceChanged(gl,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
    }
}
