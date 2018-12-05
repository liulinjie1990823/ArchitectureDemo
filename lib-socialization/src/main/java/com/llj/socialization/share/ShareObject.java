package com.llj.socialization.share;

import android.graphics.Bitmap;

/**
 * ArchitectureDemo.
 * describe:分享对象
 * author llj
 * date 2018/9/4
 */
public class ShareObject {
    private String  title;
    private String  description;
    private Bitmap  imageBitmap;
    private String  imageUrlOrPath;
    private String  targetUrl;
    private boolean isShareEmoji;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String getImageUrlOrPath() {
        return imageUrlOrPath;
    }

    public void setImageUrlOrPath(String imageUrlOrPath) {
        this.imageUrlOrPath = imageUrlOrPath;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public boolean isShareEmoji() {
        return isShareEmoji;
    }

    public void setShareEmoji(boolean shareEmoji) {
        isShareEmoji = shareEmoji;
    }
}
