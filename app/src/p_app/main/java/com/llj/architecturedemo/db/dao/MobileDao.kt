package com.llj.architecturedemo.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.llj.architecturedemo.db.entity.MobileEntity

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Dao
interface MobileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mobileEntity: MobileEntity)

    @Query("SELECT * FROM mobile WHERE id=:id")
    fun selectMobile(id: String): LiveData<MobileEntity>

    @Query("SELECT * FROM mobile WHERE phone=:phone")
    fun selectMobileByPhone(phone: String): LiveData<MobileEntity>
}
