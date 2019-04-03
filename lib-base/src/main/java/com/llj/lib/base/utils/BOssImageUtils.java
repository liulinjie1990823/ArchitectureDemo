package com.llj.lib.base.utils;

import com.llj.lib.base.help.DisplayHelper;
import com.llj.lib.utils.AStringUtils;

/**
 * Created by liulj on 16/7/7.
 */

public class BOssImageUtils {
    private static int WIDTH = (int) (DisplayHelper.SCREEN_WIDTH / 3.0);

    /**
     * 获得方形的照片,默认是屏幕的三分之一,质量是60
     *
     * @param path
     * @return
     */
    public static String getSquareImage(String path) {
        return getSquareImage(path, WIDTH, 60);
    }

    /**
     * 获得方形的照片,质量是60
     *
     * @param path
     * @param width
     * @return
     */
    public static String getSquareImage(String path, int width) {
        return getSquareImage(path, width, 60);
    }

    /**
     * 获得方形的照片
     *
     * @param path
     * @param width
     * @param quality
     * @return
     */
    public static String getSquareImage(String path, int width, int quality) {
        String newPath = "";
        if (AStringUtils.isHttpPath(path)) {
            if (AStringUtils.isOssPath(path)) {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path.replace("oss", "img") + "@1e_1c_2o_0l_100sh_" + width + "h_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;
            } else {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path + "@1e_1c_2o_0l_100sh_" + width + "h_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;

            }
        } else {
            newPath = path;
        }
        return newPath;
    }


    /**
     * 获取固定宽高的照片
     *
     * @param path
     * @param width
     * @param height
     * @param quality
     * @return
     */
    public static String getRectangleImage(String path, int width, int height, int quality) {
        String newPath = "";
        if (AStringUtils.isHttpPath(path)) {
            if (AStringUtils.isOssPath(path)) {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path.replace("oss", "img") + "@1e_1c_2o_0l_100sh_" + height + "h_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;
            } else {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path + "@1e_1c_2o_0l_100sh_" + height + "h_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;

            }
        } else {
            newPath = path;
        }
        return newPath;
    }

    /**
     * 获得按照宽比例的,默认质量为60
     *
     * @param path
     * @param width
     * @return
     */
    public static String getFitImage(String path, int width) {
        return getFitImage(path, width, 60);
    }

    /**
     * 获得按照宽比例缩放的照片
     *
     * @param path
     * @param width
     * @param quality
     * @return
     */
    public static String getFitImage(String path, int width, int quality) {
        String newPath = "";
        if (AStringUtils.isHttpPath(path)) {
            if (AStringUtils.isOssPath(path)) {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path.replace("oss", "img") + "@0o_1l_100sh_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;
            } else {
                if (AStringUtils.isGifPath(path) || AStringUtils.isJpgPath(path))
                    newPath = path + "@0o_1l_100sh_" + width + "w_" + quality + "q.jpg";
                else
                    newPath = path;

            }
        } else {
            newPath = path;
        }
        return newPath;
    }


}
