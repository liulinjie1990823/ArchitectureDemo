package com.llj.architecturedemo.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.llj.architecturedemo.db.model.MobileEntity;

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Dao
public interface MobileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MobileEntity mobileEntity);

    @Query("SELECT * FROM mobile WHERE id=:id")
    LiveData<MobileEntity> selectMobile(String id);

    @Query("SELECT * FROM mobile WHERE phone=:phone")
    LiveData<MobileEntity> selectMobileByPhone(String phone);
}
