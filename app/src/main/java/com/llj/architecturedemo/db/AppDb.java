package com.llj.architecturedemo.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.llj.architecturedemo.db.dao.MobileDao;
import com.llj.architecturedemo.db.model.MobileEntity;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Database(
        entities = {MobileEntity.class},
        version = 1
)
public abstract class AppDb extends RoomDatabase {

    abstract public MobileDao mobileDao();
}
