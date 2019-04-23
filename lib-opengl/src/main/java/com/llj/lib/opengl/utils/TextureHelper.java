package com.llj.lib.opengl.utils;

import android.content.Context;
import android.opengl.GLES20;

import com.llj.lib.opengl.model.AnimParam;
import com.llj.lib.opengl.render.LGLRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019-04-23
 */
public class TextureHelper {

    private int mProgram;//gl程序
    private int mTime;//时间参数

    private List<Integer> mTextureDataList = new ArrayList<>();

    private float mTimePlus;

    public TextureHelper(Context context, int vertexId, int fragmentId, ArrayList<AnimParam> mAnimParams, int textureSize) {
        mProgram = createProgram(context, vertexId, fragmentId);

        mTextureDataList.clear();
        //创建textureData
        for (int i = 0; i < textureSize; i++) {
            mTextureDataList.add(GLES20.glGetUniformLocation(mProgram, LGLRenderer.S_TEXTURE + i));
        }
        //创建时间data
        mTime = GLES20.glGetUniformLocation(mProgram, LGLRenderer.TIME);

    }

    public int getProgram() {
        return mProgram;
    }

    public List<Integer> getTextureList() {
        return mTextureDataList;
    }


    private int createProgram(Context context, int vertexId, int fragmentId) {
        String vertexSource = ShaderUtil.getRawResource(context, vertexId);
        String fragmentSource = ShaderUtil.getRawResource(context, fragmentId);

        return ShaderUtil.linkProgram(vertexSource, fragmentSource);
    }

    public void onBindTexture(int imgTextureId, int index) {
        if (mTextureDataList.size() > index) {
            Integer textureData = mTextureDataList.get(index);
            if (textureData >= 0) {
                GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + index);//设置纹理可用
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextureId);//将已经处理好的纹理绑定到gl上
                GLES20.glUniform1i(textureData, index);//将第x个纹理设置到fragment_shader中进一步处理
            }
        }
    }


    public void onSetTime() {
        if (mTime >= 0) {
            mTimePlus += 0.01F;
            GLES20.glUniform1f(mTime, mTimePlus);
        }
    }

    public void unbind() {
        //绘制多个纹理需要解绑解绑纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

}
