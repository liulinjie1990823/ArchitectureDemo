package com.llj.lib.utils;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短信工具类
 * Created by liulj on 16/5/3.
 */
public class SmsUtil {

    private static String ACTION_SMS_RECIVER = "android.provider.Telephony.SMS_RECEIVED";
    private SmsReceiver smsReceiver;

    private static final Uri SMS_INBOX = Uri.parse("content://sms/");
    private SmsContentObserver smsContentObserver;


    private static SmsUtil smsUtil;

    public static SmsUtil getInstance() {
        if (smsUtil == null) {
            smsUtil = new SmsUtil();
        }
        return smsUtil;
    }


    /**
     * @param context
     * @param mySmsReceiver
     */
    public void registerSmsReceiver(Context context, MySmsReceiver mySmsReceiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SMS_RECIVER);
        filter.setPriority(1000);// 设置优先级 不然监听不到短信

        smsReceiver = new SmsReceiver(mySmsReceiver);
        LocalBroadcastManager.getInstance(context).registerReceiver(smsReceiver, filter);
    }

    /**
     * @param context
     * @param mySmsReceiver
     */
    public void registerSmsReceiver(Context context, MySmsReceiver mySmsReceiver, String phoneNumber) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_SMS_RECIVER);
        filter.setPriority(1000);// 设置优先级 不然监听不到短信

        smsReceiver = new SmsReceiver(mySmsReceiver, phoneNumber);
        LocalBroadcastManager.getInstance(context).registerReceiver(smsReceiver, filter);
    }

    /**
     * @param context
     */
    public void unRegisterSmsReceiver(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(smsReceiver);
    }

    /**
     * 获取系统短信广播分析内容，通过接口回调处理短信
     *
     * @return
     */

    public interface MySmsReceiver {

        void onReceive(String mobile, String content, Date date);
    }

    /**
     * 短信的广播接收者
     */
    public static class SmsReceiver extends BroadcastReceiver {
        private MySmsReceiver mySmsReceiver;
        private String        phoneNumber;

        public SmsReceiver(MySmsReceiver mySmsReceiver) {
            this.mySmsReceiver = mySmsReceiver;
        }

        public SmsReceiver(MySmsReceiver mySmsReceiver, String phoneNumber) {
            this.mySmsReceiver = mySmsReceiver;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            Object[] object = (Object[]) intent.getExtras().get("pdus");
            for (Object pdus : object) {
                byte[] pdusMsg = (byte[]) pdus;
                SmsMessage sms = SmsMessage.createFromPdu(pdusMsg);
                String mobile = sms.getOriginatingAddress();// 发送短信的手机号
                String content = sms.getMessageBody();// 短信内容
                Date date = new Date(sms.getTimestampMillis());//短信的发送时间
                mySmsReceiver.onReceive(mobile, content, date);
            }
        }
    }


    /**
     * @param context
     * @param mySmsReceiver
     */
    public void registerSmsContentObserver(Context context, String phoneNumber, MySmsReceiver mySmsReceiver) {
        smsContentObserver = new SmsContentObserver(mySmsReceiver, phoneNumber);
        context.getContentResolver().registerContentObserver(SMS_INBOX, true, smsContentObserver);
    }

    /**
     * @param context
     */
    public void unRegisterSmsContentObserver(Context context) {
        context.getContentResolver().unregisterContentObserver(smsContentObserver);
    }

    /**
     * @param context
     * @param phoneNumber
     * @param mySmsReceiver
     */
    public void getSmsFromPhone(Context context, String phoneNumber, MySmsReceiver mySmsReceiver) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] projection = new String[]{"body"};//"_id", "address", "person",, "date", "type
        String where = " address = '" + phoneNumber + "' AND date >  " + (System.currentTimeMillis() - 10 * 60 * 1000);
        Cursor cur = contentResolver.query(SMS_INBOX, projection, where, null, "date desc");
        if (null == cur)
            return;
        if (cur.moveToNext()) {
            String number = cur.getString(cur.getColumnIndex("address"));//手机号
            String name = cur.getString(cur.getColumnIndex("person"));//联系人姓名列表
            String body = cur.getString(cur.getColumnIndex("body"));//短信内容
            long dateLong = cur.getLong(cur.getColumnIndex("date"));//短信时间
            Date date = new Date(dateLong);//短信的发送时间
            //这里我是要获取自己短信服务号码中的验证码~~
            Pattern pattern = Pattern.compile("[a-zA-Z0-9]{6}");
            Matcher matcher = pattern.matcher(body);
            if (matcher.find()) {
                String res = matcher.group().substring(1, 11);
                mySmsReceiver.onReceive(number, body, date);
            }
        }
    }

    /**
     *
     */
    public static class SmsContentObserver extends ContentObserver {
        private MySmsReceiver mySmsReceiver;
        private String        phoneNumber;

        public SmsContentObserver(MySmsReceiver mySmsReceiver) {
            super(null);
            this.mySmsReceiver = mySmsReceiver;
        }

        public SmsContentObserver(MySmsReceiver mySmsReceiver, String phoneNumber) {
            super(null);
            this.mySmsReceiver = mySmsReceiver;
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
        }
    }

}
