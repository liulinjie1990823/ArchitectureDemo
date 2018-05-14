package com.llj.lib.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取手机联系人信息
 *
 * @author liulj
 */
public class AContactUtils {
    public static final  String   TAG                       = AContactUtils.class.getSimpleName();
    public static final  String[] PHONES_PROJECTION         = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_ID, ContactsContract.CommonDataKinds.Phone.CONTACT_ID};
    private static final int      PHONES_DISPLAY_NAME_INDEX = 0;//联系人显示名称
    private static final int      PHONES_NUMBER_INDEX       = 1;//电话号码
    private static final int      PHONES_PHOTO_ID_INDEX     = 2;//头像ID
    private static final int      PHONES_CONTACT_ID_INDEX   = 3;//联系人的ID
    private static String SELF_PHONENUM;

    /**
     * 获得手机中联系人的集合
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getLocalAllContacts(Context context) {
        getSelfPhoneNumber(context);
        List<ContactInfo> contactInfos = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = null;
        try {
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX); //得到手机号码
                    String filterPhone = filter86Mobile(phoneNumber);//还需过滤掉自己的号码
                    //当手机号码为空的或者为空字段,并过滤自己的号码
                    if (!TextUtils.isEmpty(filterPhone) && !SELF_PHONENUM.equals(filterPhone)) {
                        ContactInfo contactInfo = new ContactInfo();
                        String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);//得到联系人名称
                        long contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX); //得到联系人ID
                        long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX); //得到联系人头像ID
                        Bitmap contactPhoto = getContactPhoto(context, resolver, photoid, contactid);//联系人照片
                        contactInfo.setName(contactName);
                        contactInfo.setPhoneNumber(phoneNumber);
                        contactInfo.setPhoto(contactPhoto);
                        contactInfos.add(contactInfo);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("TAG", "get an exception during getLocalContacts");
        } finally {
            if (phoneCursor != null && !phoneCursor.isClosed())
                phoneCursor.close();
        }
        return contactInfos;
    }

    /**
     * 获得sim卡中联系人的集合
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getSimContacts(Context context) {
        if (getSimState(context) != TelephonyManager.SIM_STATE_READY) {
            return null;
        }
        getSelfPhoneNumber(context);
        List<ContactInfo> contactInfos = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor phoneCursor = null;
        Uri uri;
        try {
            // 获取Sims卡联系人，一般用这个uri
            uri = Uri.parse("content://icc/adn");
            if (uri == null) {
                uri = Uri.parse("content://sim/adn");
            }
            phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    getContactInfo(phoneCursor, contactInfos);
                }
            }
        } catch (Exception ex) {
            Log.e("TAG", "get an exception during getSimContacts");
        } finally {
            if (phoneCursor != null && !phoneCursor.isClosed())
                phoneCursor.close();
        }
        return contactInfos;
    }

    /**
     * 获得手机中所有的联系人集合
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getAllContacts(Context context) {
        getSelfPhoneNumber(context);
        List<ContactInfo> contactInfos = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        // 获取手机联系人
        Cursor phoneCursor = null;
        try {
            phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
            if (phoneCursor != null) {
                while (phoneCursor.moveToNext()) {
                    getContactInfo(phoneCursor, contactInfos);
                }
            }
        } catch (Exception ex) {
            Log.e("TAG", "get an exception during getLocalContacts");
        } finally {
            if (phoneCursor != null && !phoneCursor.isClosed())
                phoneCursor.close();
        }
        //存在sim卡
        if (getSimState(context) != TelephonyManager.SIM_STATE_READY) {
            Uri uri;
            try {
                // 获取Sims卡联系人，一般用这个uri
                uri = Uri.parse("content://icc/adn");
                if (uri == null) {
                    uri = Uri.parse("content://sim/adn");
                }
                phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
                if (phoneCursor != null) {
                    while (phoneCursor.moveToNext()) {
                        getContactInfo(phoneCursor, contactInfos);
                    }
                }
            } catch (Exception ex) {
                Log.e("TAG", "get an exception during getSimContacts");
            } finally {
                if (phoneCursor != null && !phoneCursor.isClosed())
                    phoneCursor.close();
            }
        }
        return contactInfos;
    }

    /**
     * @param phoneCursor
     * @param contactInfos
     */
    private static void getContactInfo(Cursor phoneCursor, List<ContactInfo> contactInfos) {
        ContactInfo contactInfo = new ContactInfo();
        String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);// 得到手机号码
        String filterPhone = filter86Mobile(phoneNumber);//还需过滤掉自己的号码
        //还需过滤掉自己的号码
        if (!TextUtils.isEmpty(filterPhone) && !SELF_PHONENUM.equals(filterPhone)) {
            String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);// 得到联系人名称
            // Sim卡中没有联系人头像
            contactInfo.setName(contactName);
            contactInfo.setPhoneNumber(phoneNumber);
            contactInfos.add(contactInfo);
        }
    }

    /**
     * 获得联系人头像bitmap,注：Sim卡中没有联系人头像，只适合手机中的联系人
     *
     * @param context
     * @param resolver
     * @param photoid
     * @param contactid
     * @return
     */
    public static Bitmap getContactPhoto(Context context, ContentResolver resolver, long photoid, long contactid) {
        //得到联系人头像Bitamp
        Bitmap contactPhoto = null;
        //photoid 大于0 表示联系人有头像 如果没有给此人设置头像则给他一个默认的
        if (photoid > 0) {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactid);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);
            contactPhoto = BitmapFactory.decodeStream(input);
        }
        return contactPhoto;
    }

    /**
     * 获得手机sim卡状态
     *
     * @param context
     * @return
     */
    public static int getSimState(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getSimState();
    }

    /**
     * 获得用户自己的手机号码
     *
     * @param context
     * @return 手机号码
     * Requires Permission:
     * {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     */
    public static String getSelfPhoneNumber(Context context) {
        if (SELF_PHONENUM == null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            SELF_PHONENUM = telephonyManager.getLine1Number();
        }
        return SELF_PHONENUM;
    }

    /**
     * 匹配正常的手机号和+86的手机号,有+86就去掉返回去掉后的手机号,不匹配返回null
     *
     * @param phoneNum 选择的手机号
     * @return 如果手机号不匹配, 返回null
     */
    public static String filter86Mobile(String phoneNum) {
        if (!TextUtils.isEmpty(phoneNum)) {
            phoneNum = phoneNum.replaceAll(" ", "").replaceAll("-", "");//先去掉空格和横杠
            Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[34578]{1}[0-9]{9}");
            Matcher m1 = p1.matcher(phoneNum);
            if (m1.matches()) {
                Pattern p2 = Pattern.compile("^((\\+{0,1}86){0,1})");
                Matcher m2 = p2.matcher(phoneNum);
                StringBuffer sb = new StringBuffer();
                while (m2.find()) {
                    m2.appendReplacement(sb, "");
                }
                m2.appendTail(sb);
                return sb.toString();
            }
        }
        return phoneNum;
    }

    /**
     * 从自己的应用跳转到系统的联系人选择界面获取的contactId
     * 再通过查询到对应的phone
     *
     * @param context
     * @param contactId
     * @return
     */
    public static String getContactPhoneNumber(Context context, String contactId) {
        //type:TYPE_MOBILE,TYPE_HOME,TYPE_WORK,这里由于都有可能是手机号码,所以不需要设置
        // int type = ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
        String phoneNumber = null;

        String[] whereArgs = new String[]{String.valueOf(contactId)};
        LogUtil.e(TAG, "Got contact id: " + contactId);
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone._ID + " = ?",
                whereArgs,
                null);

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
            } finally {
                cursor.close();
            }
        }
        LogUtil.e(TAG, "Returning phone number: " + phoneNumber);
        return phoneNumber;
    }

    static class ContactInfo {
        private String name;
        private String phoneNumber;
        private Bitmap photo;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Bitmap getPhoto() {
            return photo;
        }

        public void setPhoto(Bitmap photo) {
            this.photo = photo;
        }
    }
}
