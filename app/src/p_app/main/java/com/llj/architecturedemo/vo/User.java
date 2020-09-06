package com.llj.architecturedemo.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/5/7
 */
public class User implements Parcelable {
    protected User(Parcel in) {
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
