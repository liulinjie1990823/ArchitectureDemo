package com.llj.lib.record;

/**
 * ArchitectureDemo.
 * describe:
 * author llj
 * date 2019/4/11
 */
public class DataUtil {

    public static void YUV420spRotateNegative90(byte[] dst, byte[] src,
                                                int srcWidth, int height) {
        int nWidth = 0, nHeight = 0;
        int wh = 0;
        int uvHeight = 0;
        if (srcWidth != nWidth || height != nHeight) {
            nWidth = srcWidth;
            nHeight = height;
            wh = srcWidth * height;
            uvHeight = height >> 1;// uvHeight = height / 2
        }

        // 旋转Y
        int k = 0;
        for (int i = 0; i < srcWidth; i++) {
            int nPos = srcWidth - 1;
            for (int j = 0; j < height; j++) {
                dst[k] = src[nPos - i];
                k++;
                nPos += srcWidth;
            }
        }

        for (int i = 0; i < srcWidth; i += 2) {
            int nPos = wh + srcWidth - 1;
            for (int j = 0; j < uvHeight; j++) {
                dst[k] = src[nPos - i - 1];
                dst[k + 1] = src[nPos - i];
                k += 2;
                nPos += srcWidth;
            }
        }

        return;
    }

    public static void YUV420spMirrorY(byte[] dst, byte[] src, int srcWidth,
                                       int srcHeight) {
        // 镜像Y
        int k = 0;
        int nPos = -1;
        for (int j = 0; j < srcHeight; j++) {
            nPos += srcWidth;
            for (int i = 0; i < srcWidth; i++) {
                dst[k] = src[nPos - i];
                k++;
            }
        }

        int uvHeight = srcHeight >> 1; // uvHeight = height / 2
        for (int j = 0; j < uvHeight; j++) {
            nPos += srcWidth;
            for (int i = 0; i < srcWidth; i += 2) {
                dst[k] = src[nPos - i - 1];
                dst[k + 1] = src[nPos - i];
                k += 2;
            }
        }
    }

    public static void YUV420pRotate90(byte[] des, byte[] src, int width,
                                       int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;
        // copy y
        for (int j = 0; j < width; j++) {
            for (int i = height - 1; i >= 0; i--) {
                des[n++] = src[width * i + j];
            }
        }

        // copy u
        int uPos = width * height;
        for (int j = 0; j < hw; j++) {
            for (int i = hh - 1; i >= 0; i--) {
                des[n++] = src[uPos + hw * i + j];
            }
        }

        // copy v
        int vPos = uPos + width * height / 4;
        for (int j = 0; j < hw; j++) {
            for (int i = hh - 1; i >= 0; i--) {
                des[n++] = src[vPos + hw * i + j];
            }
        }
    }

    public static void YUV420spRotate90(byte[] des, final byte[] src, int width,
                                        int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;
        // copy y
        for (int j = 0; j < width; j++) {
            for (int i = height - 1; i >= 0; i--) {
                des[n++] = src[width * i + j];
            }
        }

        int pos = width * height;
        for (int j = 0; j < width; j += 2) {
            for (int i = hh - 1; i >= 0; i--) {
                des[n++] = src[pos + width * i + j];        // copy v
                des[n++] = src[pos + width * i + j + 1];    // copy u
            }
        }
    }

    public static void YUV420pRotate180(byte[] des, byte[] src, int width,
                                        int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;
        // copy y
        for (int j = height - 1; j >= 0; j--) {
            for (int i = width; i > 0; i--) {
                des[n++] = src[width * j + i];
            }
        }

        // copy u
        int uPos = width * height;
        for (int j = hh - 1; j >= 0; j--) {
            for (int i = hw; i > 0; i--) {
                des[n++] = src[uPos + hw * i + j];
            }
        }

        // copy v
        int vPos = uPos + width * height / 4;
        for (int j = hh - 1; j >= 0; j--) {
            for (int i = hw; i > 0; i--) {
                des[n++] = src[vPos + hw * i + j];
            }
        }
    }

    public static void YUV420pRotate270(byte[] des, byte[] src, int width,
                                        int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;
        // copy y
        for (int j = width - 1; j >= 0; j--) {
            for (int i = 0; i < height; i++) {
                des[n++] = src[width * i + j];
            }
        }

        // copy u
        int uPos = width * height;
        for (int j = hw - 1; j >= 0; j--) {
            for (int i = 0; i < hh; i++) {
                des[n++] = src[uPos + hw * i + j];
            }
        }

        // copy v
        int vPos = uPos + width * height / 4;
        for (int j = hw - 1; j >= 0; j--) {
            for (int i = 0; i < hh; i++) {
                des[n++] = src[vPos + hw * i + j];
            }
        }
    }

    public static void YUV420pMirrorY(byte[] des, byte[] src, int width,
                                      int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;
        // copy y
        for (int j = 0; j < height; j++) {
            for (int i = width - 1; i >= 0; i--) {
                des[n++] = src[width * j + i];
            }
        }

        // copy u
        int uPos = width * height;
        for (int j = 0; j < hh; j++) {
            for (int i = hw - 1; i >= 0; i--) {
                des[n++] = src[uPos + hw * j + i];
            }
        }

        // copy v
        int vPos = uPos + width * height / 4;
        for (int j = 0; j < hh; j++) {
            for (int i = hw - 1; i >= 0; i--) {
                des[n++] = src[vPos + hw * j + i];
            }
        }
    }

    public static void YUV420pMirrorX(byte[] des, byte[] src, int width, int height) {
        int n = 0;
        int hw = width / 2;
        int hh = height / 2;

        int nPos = width * height;
        for (int j = 0; j < height; j++) {
            nPos -= width;
            for (int i = 0; i < width; i++) {
                des[n++] = src[nPos + i];
            }
        }

        nPos = width * height + width * height / 4;
        for (int j = 0; j < hh; j++) {
            nPos -= hw;
            for (int i = 0; i < hw; i++) {
                des[n++] = src[nPos + i];
            }
        }

        nPos = width * height + width * height / 2;
        for (int j = 0; j < hh; j++) {
            nPos -= hw;
            for (int i = 0; i < hw; i++) {
                des[n++] = src[nPos + i];
            }
        }
    }
}
