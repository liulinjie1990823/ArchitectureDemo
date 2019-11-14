package com.llj.lib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * 手机中图片的操作类
 *
 * @author liulj
 */
public class APhotoUtils {
    public static final String JPG  = "jpg";
    public static final String PNG  = "png";
    public static final String WEBP = "webp";

    /**
     * 通过内容提供者获取保存在系统数据库中的所有存在的图片文件
     *
     * @param context 上下文
     * @return
     */
    public static List<ExternalImageInfo> getMediaStoreImages(Context context) {
        List<ExternalImageInfo> mImageInfos = new ArrayList<>();
        Cursor imagecursor = null;
        try {
            final String[] projection = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_TAKEN};
            final String sortOrder = MediaStore.Images.Media._ID;

            imagecursor = MediaStore.Images.Media.query(context.getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder);
            if (imagecursor != null && imagecursor.getCount() > 0) {
                while (imagecursor.moveToNext()) {
                    ExternalImageInfo imageInfo = new ExternalImageInfo();
                    // 返回data在第几列，并获取地址
                    String path = imagecursor.getString(imagecursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    File file = new File(path);
                    // 该路径下的文件存在则添加到集合中
                    if (file.exists()) {
                        // 添加路径到对象中
                        imageInfo.path = path;
                        // 插入修改时间
                        imageInfo.dateTaken = imagecursor.getLong(imagecursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
                        // 添加名字
                        mImageInfos.add(imageInfo);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            imagecursor.close();
        } finally {
            imagecursor.close();
        }
        // 按降序排
        try {
            Collections.sort(mImageInfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mImageInfos;
    }

    /**
     * 根据所有图片信息，把图片按日期分组
     *
     * @param imageInfos 所有图片的信息集合
     * @return 按日期分好组后的map
     */
    public static Map<String, List<ExternalImageInfo>> getAllDirByImages(List<ExternalImageInfo> imageInfos) {
        HashMap<String, List<ExternalImageInfo>> map = new HashMap<String, List<ExternalImageInfo>>();
        map.clear();
        List<ExternalImageInfo> imageInfoAll = new ArrayList<ExternalImageInfo>();
        imageInfoAll.addAll(imageInfos);
        map.put(ExternalImageInfo.ALL_IMAGE, imageInfoAll);
        for (ExternalImageInfo imageInfo : imageInfos) {
            if (imageInfo != null && imageInfo.path != null) {
                File file = new File(imageInfo.path);
                if (file.exists()) {
                    if (file.getParentFile() != null) {
                        if (map.containsKey(file.getParentFile().getName())) {
                            // 如果key已经存在
                            map.get(file.getParentFile().getName()).add(imageInfo);
                        } else {
                            // 如果key不存在
                            List<ExternalImageInfo> imageInfos2 = new ArrayList<>();
                            imageInfos2.add(imageInfo);
                            map.put(file.getParentFile().getName(), imageInfos2);
                        }
                    }
                }
            }
        }
        return map;
    }

    public static ExternalImageInfo getImageInfoByPath(Context context, String fileName) {
        ExternalImageInfo externalImageInfo = new ExternalImageInfo();
        Cursor cursor = null;
        try {
            final String[] projection = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.LONGITUDE,
                    MediaStore.Images.Media.LATITUDE};
            final String sortOrder = MediaStore.Images.Media._ID;
            cursor = MediaStore.Images.Media.query(context.getContentResolver(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    MediaStore.Images.Media.DATA + " = ?",
                    new String[]{fileName},
                    sortOrder);

            cursor.moveToFirst();
            externalImageInfo.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            externalImageInfo.dateTaken = AParseUtils.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)));
            externalImageInfo.longitude = ANumberUtils.doubleStringToInteger(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LONGITUDE)));
            externalImageInfo.latitude = ANumberUtils.doubleStringToInteger(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.LATITUDE)));

        } catch (Exception e) {
            cursor.close();
        } finally {
            cursor.close();
        }
        return externalImageInfo;
    }


    /**
     * 获取文件中的时间信息
     *
     * @param path 文件路劲
     * @return 毫秒数
     */
    public static long getExifInterfaceDate(String path) {
        ExifInterface exif;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.CHINA); //得到指定模范的时间
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA); //得到指定模范的时间
        try {
            exif = new ExifInterface(path);
        } catch (IOException e) {
            return -1;
        }
        String dateStr = exif.getAttribute(ExifInterface.TAG_DATETIME);
        if (TextUtils.isEmpty(dateStr))
            return -1;
        Date date;
        try {
            date = sdf.parse(dateStr);
            if (date == null) {
                date = sdf2.parse(dateStr);
            }
        } catch (ParseException e) {
            return -1;
        }
        return date.getTime();
    }

    /**
     * 根据指定文件绝对路径
     * 查找ContentResolver中该照片的拍摄时间
     *
     * @param context 上下文
     * @param path    照片的本地路径
     * @return 照片的拍摄时间 没有查到就返回-1
     */
    public static long getImageTakenDateByContentResolver(Context context, String path) {
        long createTime = -1;
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver mContentResolver = context.getApplicationContext().getContentResolver();
        //只查询jpeg和png的图片
        Cursor mCursor = mContentResolver.query(mImageUri,
                new String[]{MediaStore.Images.Media.DATE_TAKEN},
                MediaStore.Images.Media.DATA + "=?",
                new String[]{path}, MediaStore.Images.Media.DATE_TAKEN);
        if (mCursor == null) {
            return -1;
        }
        while (mCursor.moveToNext()) {
            //获取图片的路径
            String str = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
            createTime = Long.parseLong(str);
        }
        mCursor.close();
        return createTime;
    }

    public static class ExternalImageInfo implements Comparable<ExternalImageInfo>, Parcelable {

        public static final String ALL_IMAGE = "所有图片";

        // 设置id为自增长的组件
        public Integer id;//
        public String  path;// 文件地址
        public String  bucketName;// 文件夹
        public int     status;// 0未选中,1选中未插入数据库,||(这边是已经插入数据库的可能状态)2选中插入数据库,3已经上传照片,4完全发布
        public String  name;// 照片名字

        public long dateTaken;//照片拍摄时间

        public Integer longitude;//经度
        public Integer latitude;//纬度


        @Override
        public int compareTo(@NonNull ExternalImageInfo o) {
            if (dateTaken > o.dateTaken) {
                return -1;
            } else if (dateTaken < o.dateTaken) {
                return 1;
            }
            return 0;

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeValue(this.id);
            dest.writeString(this.path);
            dest.writeString(this.bucketName);
            dest.writeInt(this.status);
            dest.writeString(this.name);
            dest.writeLong(this.dateTaken);
            dest.writeValue(this.longitude);
            dest.writeValue(this.latitude);
        }

        public ExternalImageInfo() {
        }

        protected ExternalImageInfo(Parcel in) {
            this.id = (Integer) in.readValue(Integer.class.getClassLoader());
            this.path = in.readString();
            this.bucketName = in.readString();
            this.status = in.readInt();
            this.name = in.readString();
            this.dateTaken = in.readLong();
            this.longitude = (Integer) in.readValue(Integer.class.getClassLoader());
            this.latitude = (Integer) in.readValue(Integer.class.getClassLoader());
        }

        public static final Creator<ExternalImageInfo> CREATOR = new Creator<ExternalImageInfo>() {
            @Override
            public ExternalImageInfo createFromParcel(Parcel source) {
                return new ExternalImageInfo(source);
            }

            @Override
            public ExternalImageInfo[] newArray(int size) {
                return new ExternalImageInfo[size];
            }
        };

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ExternalImageInfo that = (ExternalImageInfo) o;
            return path != null && path.equals(that.path);

        }

        @Override
        public int hashCode() {
            return 10;
        }
    }

}
