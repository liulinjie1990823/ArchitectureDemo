package com.llj.lib.opengl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.support.annotation.DrawableRes;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;

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


    public static Bitmap createTextImage(String text, int textSize, String textColor, String bgColor, int padding) {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor(textColor));
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        float width = paint.measureText(text, 0, text.length());

        float top = paint.getFontMetrics().top;
        float bottom = paint.getFontMetrics().bottom;

        Bitmap bm = Bitmap.createBitmap((int) (width + padding * 2), (int) ((bottom - top) + padding * 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);

        canvas.drawColor(Color.parseColor(bgColor));
        canvas.drawText(text, padding, -top + padding, paint);
        return bm;
    }

    public static int loadBitmapTexture(Bitmap bitmap) {
        int[] textureIds = new int[1];
        GLES20.glGenTextures(1, textureIds, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        ByteBuffer bitmapBuffer = ByteBuffer.allocate(bitmap.getHeight() * bitmap.getWidth() * 4);
        bitmap.copyPixelsToBuffer(bitmapBuffer);
        bitmapBuffer.flip();

        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, bitmap.getWidth(),
                bitmap.getHeight(), 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, bitmapBuffer);
        return textureIds[0];
    }

    public static  BitmapObject loadBitmapTexture(Context context, @DrawableRes int resId) {
        //526*702
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        if (bitmap != null && !bitmap.isRecycled()) {

            //创建一个纹理
            int[] textureIds = new int[1];
            GLES20.glGenTextures(1, textureIds, 0);

            //绑定纹理
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);

            //纹理环绕方式 GLES20.GL_REPEAT
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);


            //边缘拉伸 GLES20.GL_CLAMP_TO_EDGE //https://blog.csdn.net/cassiepython/article/details/51645791
            //GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);


            //过滤方式 https://blog.csdn.net/sharp2wing/article/details/5683379
            //缩小情况：GLES20.GL_TEXTURE_MIN_FILTER
            //GL_NEAREST 最近邻过滤
            //GL_NEAREST_MIPMAP_NEAREST 使用MIP贴图的最近邻过滤
            //GL_NEAREST_MIPMAP_LINEAR 使用MIP贴图级别之间插值的最近邻过滤
            //GL_LINEAR 双线性过滤
            //GL_LINEAR_MIPMAP_NEAREST 使用MIP贴图的双线性过滤
            //GL_LINEAR_MIPMAP_LINEAR 三线性过滤 （使用MIP贴图级别之间插值的双线性过滤 ）

            //最近邻过滤：这个比较简单，每个像素的纹理坐标，并不是刚好对应一个采样点时，按照最接近的采样点进行采样。当放大纹理时，锯齿会比较明显，每个纹理单元都显示为一个小方块。当缩小时，会丢失许多细节。
            //双线性过滤：使用双线性插值平滑像素之间的过渡。放大时，锯齿看起来会比最近邻过滤少许多，看起来更加平滑。
            //MIP贴图 ：当缩小到一定程度时，使用双线性过滤会失去太多细节，还可能引起噪声以及物体移动过程中的闪烁，为了克服这些问题就有了MIP贴图技术。关于MIP贴图 ，维基上有更详细解释 ：https://en.wikipedia.org/wiki/Mipmap
            //三线性过滤 ：用于消除每个MIP贴图级别之间的过渡，得到一个更为平滑的图像。

            //放大情况：GLES20.GL_TEXTURE_MAG_FILTER
            //GL_NEAREST
            //GL_LINEAR

            //过滤方式
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

            //根据以上指定的参数，生成一个2D纹理
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            //解绑
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
            return new BitmapObject(textureIds[0], bitmap.getWidth(), bitmap.getHeight());
        }
        return new BitmapObject(-1, 0, 0);
    }

    public static class BitmapObject {
        public int imgTextureId;
        public int width;
        public int height;

        public BitmapObject(int imgTextureId, int width, int height) {
            this.imgTextureId = imgTextureId;
            this.width = width;
            this.height = height;
        }
    }
}
