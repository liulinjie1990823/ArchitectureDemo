package com.llj.lib.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频工具类
 * Created by liulj on 16/5/12.
 */
public class AVideoUtils {

    /**
     * @param path
     * @return
     */
    public static Bitmap getFrameAtTime(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }


    public static int getDuring(File file) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(file.getAbsolutePath());
        long fileSize = file.length();
        long bitRate = Long.parseLong(media.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
        return (int) ((fileSize * 8) / (bitRate));
    }

    /**
     * 单位是微妙,和秒进制是1000000
     *
     * @param path
     * @param timeUs
     * @return
     */
    public static Bitmap getFrameAtTime(String path, long timeUs) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        Bitmap bitmap = media.getFrameAtTime(timeUs);
        return bitmap;
    }

    /**
     * 获取视频的缩略图(实际还是通过MediaMetadataRetriever来获取)
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap createVideoThumbnail(String videoPath, int width, int height, int kind) {
        // 获取视频的缩略图
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * @param context
     * @param id
     * @param kind    MINI_KIND和MICRO_KIND
     * @param options
     * @return
     */
    public static Bitmap getThumbnail(Context context, int id, int kind, BitmapFactory.Options options) {
        return MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, kind, options);

    }

    /**
     * 获取手机本地的视频对象
     *
     * @param context        上下文
     * @param maxVideoDuring 视频最大时长限制（ms）
     * @param videoType      视频类型，默认MP4
     * @return 视频对象列表集合
     */
    public static ArrayList<VideoInfo> getMediaStoreVideos(Context context, long maxVideoDuring, String videoType) {
        if (maxVideoDuring == 0) {
            maxVideoDuring = 60 * 1000;
        }
        long minVideoDuring = 2000;
        if (TextUtils.isEmpty(videoType)) {
            videoType = ".mp4";
        }
        ArrayList<VideoInfo> videoList = new ArrayList<>();

        String[] thumbColumns = new String[]{MediaStore.Video.Thumbnails.DATA, MediaStore.Video.Thumbnails.VIDEO_ID};

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        String[] mediaColumns = new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DATE_TAKEN,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.LONGITUDE,
                MediaStore.Video.Media.LATITUDE,
                MediaStore.Video.Media.RESOLUTION};
        Cursor cursor = MediaStore.Video.query(context.getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns);
        try {
            while (cursor.moveToNext()) {
                VideoInfo info = new VideoInfo();
                //文件路径
                String filePathTemp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                //文件时长
                long during = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

                if (AFileUtils.exists(filePathTemp) && filePathTemp.endsWith(videoType) && during > minVideoDuring && during <= maxVideoDuring) {
                    //文件路径
                    info.originalVideoFilePath = filePathTemp;
                    //文件大小
                    info.originalVideoFileSize = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                    //视频的宽高
                    info.width = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH));
                    info.height = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT));
                    //拿不到视频的宽高,就用下面的参数获取
                    if (info.width == 0 || info.height == 0) {
                        String resolution = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION));
                        if (resolution != null) {
                            String[] split = resolution.split("x");
                            if (split.length == 2) {
                                info.width = AParseUtils.parseInt(split[0]);
                                info.height = AParseUtils.parseInt(split[1]);
                            }
                        }
                    }
                    //经纬度
                    info.longitude = ANumberUtils.doubleStringToInteger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LONGITUDE)));
                    info.latitude = ANumberUtils.doubleStringToInteger(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.LATITUDE)));
                    //文件时长
                    info.duringTime = during;
                    //文件修改时间
                    info.dateTaken = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_TAKEN));
                    //种类
                    info.mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));

                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID));
//                        //获取视频的预览缩略图,这里的图只是用来在列表中展示的,不能用作封面
//                        Bitmap bitmap = getFrameAtTime(info.videoFilePath, 1000000);
////                        Bitmap bitmap = getVideoThumbnail(info.videoFilePath, 60, 60, MediaStore.Images.Thumbnails.MICRO_KIND);
//                        String thumbnailPath = getPath(null, thumbnailPathDir, userId);
//                        info.thumbPreviewPath = ABitmapUtils.bitmapToFile(bitmap, new File(thumbnailPath), 100).getAbsolutePath();
//                        bitmap.recycle();
//                        info.thumbPreviewBitmap = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
                    info.id = id;
                    videoList.add(info);
                }
            }
        } catch (Exception e) {
            cursor.close();
        } finally {
            cursor.close();
        }
        return videoList;
    }

    public static String getPath(String fromPath, String toDir, String userId) {
        if (!AAppUtil.isSDCardAvailable()) {
            return null;
        }
        //获取后缀
        String ext = "";
        if (!TextUtils.isEmpty(fromPath)) {
            ext = AFileUtils.getFileFormat(fromPath);//用于获取原图像格式
        }
        if (TextUtils.isEmpty(ext)) {
            ext = "jpg";
        }

        //创建目标路径文件夹
        File saveDir = new File(toDir);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }
        return toDir + "bbpp_" + getId(userId) + "." + ext;// 照片命名
    }

    /**
     * 生成故事 宝宝 照片通用的id 方法
     * 宝号加上时间毫秒数 1733345_1434324342
     */
    public static String getId(String userId) {
        if (userId == null) {
            return System.currentTimeMillis() + "";            //正常情况不会读取不到user
        } else {
            return userId + "_" + System.currentTimeMillis();
        }
    }

    /**
     * 根据所有图片信息，把图片按日期分组
     *
     * @param videoInfoList 所有图片的信息集合
     * @return 按日期分好组后的map
     */
    public static Map<String, ArrayList<VideoInfo>> getAllDirByVideos(List<VideoInfo> videoInfoList) {
        HashMap<String, ArrayList<VideoInfo>> map = new LinkedHashMap<>();
        map.clear();
        ArrayList<VideoInfo> videoInfoAll = new ArrayList<>();
        videoInfoAll.addAll(videoInfoList);
        map.put("所有视频", videoInfoAll);

        for (VideoInfo videoInfo : videoInfoList) {
            if (videoInfo != null && videoInfo.originalVideoFilePath != null) {
                File file = new File(videoInfo.originalVideoFilePath);
                if (file.exists()) {
                    if (file.getParentFile() != null) {
                        if (map.containsKey(file.getParentFile().getName())) {
                            // 如果key已经存在
                            map.get(file.getParentFile().getName()).add(videoInfo);
                        } else {
                            // 如果key不存在
                            ArrayList<VideoInfo> imageInfos2 = new ArrayList<>();
                            imageInfos2.add(videoInfo);
                            map.put(file.getParentFile().getName(), imageInfos2);
                        }
                    }
                }
            }
        }
        return map;
    }


    public static ArrayList<VideoInfo> getMediaStoreVideos(Context context) {
        return getMediaStoreVideos(context, 0, null);
    }


    public static class VideoInfo implements Parcelable, Comparable<VideoInfo> {

        public VideoInfo() {

        }

        public String originalVideoFilePath;//文件路径
        public long   originalVideoFileSize; //文件大小

        public String thumbVideoFilePath; //压缩后视频文件路径
        public long   thumbVideoFileSize; //压缩后视频文件大小

        public long duringTime; //文件时长 ms
        public long dateTaken; //视频的拍摄时间(毫秒数),更新时间(秒数),添加时间(秒数)

        public String coverImgLocalPath;  //缩略图路径,直接从系统里面拿出可能是MP4格式
        public int    id;  //缩略图路径,直接从系统里面拿出可能是MP4格式

        public String mimeType; //种类
        public int    width; //视频的宽度
        public int    height; //视频的高度

        public Integer longitude;//经度
        public Integer latitude;//纬度

        @Override
        public String toString() {
            return "VideoInfo{" +
                    "originalVideoFilePath='" + originalVideoFilePath + '\'' +
                    ", originalVideoFileSize=" + originalVideoFileSize +
                    ", thumbVideoFilePath='" + thumbVideoFilePath + '\'' +
                    ", thumbVideoFileSize=" + thumbVideoFileSize +
                    ", duringTime=" + duringTime +
                    ", dateTaken=" + dateTaken +
                    ", coverImgLocalPath='" + coverImgLocalPath + '\'' +
                    ", mimeType='" + mimeType + '\'' +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

        /**
         * 降序排列
         *
         * @param another
         * @return
         */
        @Override
        public int compareTo(@NonNull VideoInfo another) {
            return another.dateTaken < dateTaken ? -1 : (dateTaken == another.dateTaken ? 0 : 1);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof VideoInfo) {
                return this.id == ((VideoInfo) obj).id;
            } else {
                return false;
            }
        }

        protected VideoInfo(Parcel in) {
            originalVideoFilePath = in.readString();
            originalVideoFileSize = in.readLong();
            thumbVideoFilePath = in.readString();
            thumbVideoFileSize = in.readLong();
            duringTime = in.readLong();
            dateTaken = in.readLong();
            coverImgLocalPath = in.readString();
            id = in.readInt();
            mimeType = in.readString();
            width = in.readInt();
            height = in.readInt();
        }

        public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
            @Override
            public VideoInfo createFromParcel(Parcel in) {
                return new VideoInfo(in);
            }

            @Override
            public VideoInfo[] newArray(int size) {
                return new VideoInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(originalVideoFilePath);
            dest.writeLong(originalVideoFileSize);
            dest.writeString(thumbVideoFilePath);
            dest.writeLong(thumbVideoFileSize);
            dest.writeLong(duringTime);
            dest.writeLong(dateTaken);
            dest.writeString(coverImgLocalPath);
            dest.writeInt(id);
            dest.writeString(mimeType);
            dest.writeInt(width);
            dest.writeInt(height);
        }
    }
}
