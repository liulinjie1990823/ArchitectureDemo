package com.llj.lib.image.select.ui;

import java.util.EnumSet;
import java.util.Set;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-28
 */
public class MediaPickConfig {
    private Set<MimeType> mimeTypeSet;
    private boolean       showSingleMediaType;

    public Set<MimeType> getMimeTypeSet() {
        return mimeTypeSet;
    }

    public void setMimeTypeSet(Set<MimeType> mimeTypeSet) {
        this.mimeTypeSet = mimeTypeSet;
    }

    public boolean isShowSingleMediaType() {
        return showSingleMediaType;
    }

    public void setShowSingleMediaType(boolean showSingleMediaType) {
        this.showSingleMediaType = showSingleMediaType;
    }

    public boolean showImages() {
        return showSingleMediaType && MimeType.ofImage().containsAll(mimeTypeSet);
    }

    public boolean showVideos() {
        return showSingleMediaType && MimeType.ofVideo().containsAll(mimeTypeSet);
    }

    public boolean showGif() {
        return showSingleMediaType && EnumSet.of(MimeType.GIF).equals(mimeTypeSet);
    }

    public boolean showPng() {
        return showSingleMediaType && EnumSet.of(MimeType.PNG).equals(mimeTypeSet);
    }

    public boolean showJpg() {
        return showSingleMediaType && EnumSet.of(MimeType.JPEG).equals(mimeTypeSet);
    }

    public boolean showWebp() {
        return showSingleMediaType && EnumSet.of(MimeType.WEBP).equals(mimeTypeSet);
    }
}
