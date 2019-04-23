package com.llj.lib.opengl.render;

import android.opengl.GLES20;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-23
 */
public interface IFboRender extends LGLRenderer {


    default void onBindTexture(int textureData, int imgTextureId, int index) {
        if (textureData >= 0) {
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + index);//设置纹理可用
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//将已经处理好的纹理绑定到gl上
            GLES20.glUniform1i(textureData, index);//将第x个纹理设置到fragment_shader中进一步处理
        }
    }

}
