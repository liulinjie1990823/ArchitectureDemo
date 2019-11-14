package com.llj.architecturedemo.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * ArchitectureDemo
 * describe:
 * author liulj
 * date 2018/6/6
 */
@Entity(tableName = "mobile",
        indices = [Index("id")])
data class MobileEntity constructor(@field:PrimaryKey
                                    var id: Long) {
    var phone: String? = null
    var province: String? = null
    var city: String? = null
    @field:SerializedName("service_provider")
    var serviceProvider: String? = null
    @field:SerializedName("city_code")
    var cityCode: String? = null
    var postcode: String? = null
    var ret: Int = 0
    var searchStr: String? = null
    var operator: String? = null
    var from: String? = null
    var ip: String? = null
    var ua: String? = null

}
