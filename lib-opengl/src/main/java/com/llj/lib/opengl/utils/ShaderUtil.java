package com.llj.lib.opengl.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/3/29
 */
public class ShaderUtil {
    private static final String TAG = ShaderUtil.class.getSimpleName();

    public static String getRawResource(Context context, int rawId) {
        InputStream inputStream = context.getResources().openRawResource(rawId);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer sb = new StringBuffer();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * createProgram
     *
     * @param vertexSource   顶点着色器代码
     * @param fragmentSource 片元着色器代码
     *
     * @return 着色器程序
     */
    public static int createProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);

        int program = 0;
        if (vertexShader != 0 && fragmentShader != 0) {
            //创建着色器程序
            program = GLES20.glCreateProgram();

            //向程序中加入顶点着色器
            GLES20.glAttachShader(program, vertexShader);
            //向程序中加入片元着色器
            GLES20.glAttachShader(program, fragmentShader);
            //链接程序
            GLES20.glLinkProgram(program);

            // 获取program的链接情况
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
            return program;
        }
        return program;
    }

    /**
     * loadShader
     *
     * @param shaderType GL_VERTEX_SHADER|GL_FRAGMENT_SHADER
     * @param source     shader code
     *
     * @return int value of shader
     */
    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            //加载编译源码并编译shader
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);

            //检查是否编译成功
            int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
            if (compileStatus[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not compile shader: ");
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
            return shader;
        }
        return shader;
    }
}
