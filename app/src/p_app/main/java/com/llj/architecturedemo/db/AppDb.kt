package com.llj.architecturedemo.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

import com.llj.architecturedemo.db.dao.MobileDao
import com.llj.architecturedemo.db.entity.MobileEntity

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Database(entities = [MobileEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun mobileDao(): MobileDao
}
