package com.llj.lib.image.select.ui.loader;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;

import com.llj.lib.image.select.ui.MediaPickConfig;

/**
 * ArchitectureDemo.
 * describe:
 *
 * @author llj
 * @date 2019-10-28
 */
public class MediaLoader extends CursorLoader {
    //content://media/external/file
    private static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");

    private static final String COLUMN_BUCKET_ID           = "bucket_id";
    private static final String COLUMN_BUCKET_DISPLAY_NAME = "bucket_display_name";
    private static final String COLUMN_URI                 = "uri";
    private static final String COLUMN_COUNT               = "count";

    private static final String BUCKET_ORDER_BY = "datetaken DESC";

    private static final String[] COLUMNS       = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            COLUMN_URI,
            COLUMN_COUNT};
    private static final String[] PROJECTION    = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE,
            "COUNT(*) AS " + COLUMN_COUNT};
    private static final String[] PROJECTION_29 = {
            MediaStore.Files.FileColumns._ID,
            COLUMN_BUCKET_ID,
            COLUMN_BUCKET_DISPLAY_NAME,
            MediaStore.MediaColumns.MIME_TYPE};


    // === params for showSingleMediaType: false ===
    private static final String   SELECTION      =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";
    private static final String   SELECTION_29   =
            "(" + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?)"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";
    private static final String[] SELECTION_ARGS = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO),
    };
    // =============================================

    // === params for showSingleMediaType: true ===
    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE    =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + ") GROUP BY (bucket_id";
    private static final String SELECTION_FOR_SINGLE_MEDIA_TYPE_29 =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0";

    private static final String[] SELECTION_ARGS_IMAGE = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
    };
    private static final String[] SELECTION_ARGS_VIDEO = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)
    };
    // =============================================

    // === params for showSingleMediaType: true ===
    private static final String SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE    =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + MediaStore.MediaColumns.MIME_TYPE + "=?"
                    + ") GROUP BY (bucket_id";
    private static final String SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE_29 =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND " + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND " + MediaStore.MediaColumns.MIME_TYPE + "=?";

    private static String[] getSelectionArgsForSingleMediaGifType(int mediaType, String mimeType) {
        return new String[]{String.valueOf(mediaType), mimeType};
    }

    private static final String[] SELECTION_ARGS_IMAGE_GIF  = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            "image/gif"
    };
    private static final String[] SELECTION_ARGS_IMAGE_PNG  = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            "image/png"
    };
    private static final String[] SELECTION_ARGS_IMAGE_JPG  = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            "image/jpg"
    };
    private static final String[] SELECTION_ARGS_IMAGE_WEBP = {
            String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE),
            "image/webp"
    };
    // =============================================

    private MediaPickConfig mMediaPickConfig;

    public MediaLoader(@NonNull Context context) {
        super(context);
    }

    public MediaLoader(@NonNull Context context, @Nullable String selection, @Nullable String[] selectionArgs) {
        super(context, QUERY_URI, beforeAndroidTen() ? PROJECTION : PROJECTION_29, selection, selectionArgs, BUCKET_ORDER_BY);
    }

    public static CursorLoader newInstance(Context context, MediaPickConfig config) {
        String selection;
        String[] selectionArgs;
        if (config.showImages()) {
            //image
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_TYPE : SELECTION_FOR_SINGLE_MEDIA_TYPE_29;
            selectionArgs = SELECTION_ARGS_IMAGE;
        } else if (config.showGif()) {
            //image/gif
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE : SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE_29;
            selectionArgs = SELECTION_ARGS_IMAGE_GIF;
        } else if (config.showPng()) {
            //image/png
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE : SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE_29;
            selectionArgs = SELECTION_ARGS_IMAGE_PNG;
        } else if (config.showJpg()) {
            //image/jpg
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE : SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE_29;
            selectionArgs = SELECTION_ARGS_IMAGE_JPG;
        } else if (config.showWebp()) {
            //image/webp
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE : SELECTION_FOR_SINGLE_MEDIA_IMAGE_TYPE_29;
            selectionArgs = SELECTION_ARGS_IMAGE_WEBP;
        } else if (config.showVideos()) {
            //video
            selection = beforeAndroidTen() ? SELECTION_FOR_SINGLE_MEDIA_TYPE : SELECTION_FOR_SINGLE_MEDIA_TYPE_29;
            selectionArgs = SELECTION_ARGS_VIDEO;
        } else {
            //all
            selection = beforeAndroidTen() ? SELECTION : SELECTION_29;
            selectionArgs = SELECTION_ARGS;
        }
        return new MediaLoader(context, selection, selectionArgs);
    }

    private static boolean beforeAndroidTen() {
        return android.os.Build.VERSION.SDK_INT < 29;
    }
}
