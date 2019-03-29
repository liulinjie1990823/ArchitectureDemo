package com.llj.architecturedemo.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.ywl5320.opengles.WlGlRender;
import com.ywl5320.opengles.WlGlSurfaceView;

import java.nio.FloatBuffer;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/28
 */
public class MyGLSurfaceView extends WlGlSurfaceView {
    public MyGLSurfaceView(Context context) {
        super(context);
    }

    public MyGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static class Renderer extends WlGlRender {
        private       Context     context;
        private       FloatBuffer vertexBuffer;
        private final float[]     vertexData = {
                1f, 1f, 0f,
                -1f, 1f, 0f,
                1f, -1f, 0f,
                -1f, -1f, 0f
        };

        private       FloatBuffer textureBuffer;
        private final float[]     textureVertexData = {
                1f, 0f,
                0f, 0f,
                1f, 1f,
                0f, 1f
        };
        private int program;
        private int vPosition;
        private int fPosition;
        private int textureId;
        private int vboId;
        private int fboId;

        private int imgTextureId;


        public Renderer(Context context) {
            super(context);
        }


    }
}
